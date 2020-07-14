/*
 *
 * Copyright 2015-Present Entando Inc. (http://www.entando.com) All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 *  This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 */

package org.entando.kubernetes.model;

import io.fabric8.kubernetes.api.model.Doneable;
import io.fabric8.kubernetes.client.CustomResourceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.internal.CustomResourceOperationsImpl;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.entando.kubernetes.model.bundle.DoneableEntandoComponentBundle;
import org.entando.kubernetes.model.bundle.EntandoComponentBundle;
import org.entando.kubernetes.model.bundle.EntandoComponentBundleOperationFactory;

public class EntandoComponentBundleOperationRegistry {

    private static final OperationsSupplier OPERATION_SUPPLIER = getOperationSupplier();
    private final KubernetesClient client;
    @SuppressWarnings("unchecked")
    private final Map<Class, CustomResourceOperationsImpl> customResourceOperations =
            new ConcurrentHashMap<>();

    public EntandoComponentBundleOperationRegistry(KubernetesClient client) {
        this.client = client;
    }

    private static OperationsSupplier getOperationSupplier() {
        return EntandoComponentBundleOperationFactory::produceAllEntandoComponentBundles;
    }

    @SuppressWarnings("unchecked")
    public CustomResourceOperationsImpl<EntandoComponentBundle, CustomResourceList<EntandoComponentBundle>, DoneableEntandoComponentBundle> getOperations() {
        return OPERATION_SUPPLIER.get(client);
    }

    private interface OperationsSupplier {
        CustomResourceOperationsImpl get(KubernetesClient client);
    }

}
