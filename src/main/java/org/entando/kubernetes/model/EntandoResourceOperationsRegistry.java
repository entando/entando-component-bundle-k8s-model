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
import org.entando.kubernetes.model.bundle.EntandoComponentBundle;
import org.entando.kubernetes.model.bundle.EntandoComponentBundleOperationFactory;

public class EntandoResourceOperationsRegistry {

    private static final Map<Class<? extends EntandoBaseCustomResource>, OperationsSupplier> OPERATION_SUPPLIERS = getOperationSuppliers();
    private final KubernetesClient client;
    @SuppressWarnings("unchecked")
    private final Map<Class, CustomResourceOperationsImpl> customResourceOperations =
            new ConcurrentHashMap<>();

    public EntandoResourceOperationsRegistry(KubernetesClient client) {
        this.client = client;
    }

    private static Map<Class<? extends EntandoBaseCustomResource>, OperationsSupplier> getOperationSuppliers() {
        Map<Class<? extends EntandoBaseCustomResource>, OperationsSupplier> operationSuppliers = new ConcurrentHashMap<>();
        operationSuppliers.put(EntandoComponentBundle.class, EntandoComponentBundleOperationFactory::produceAllEntandoComponentBundles);
        return Collections.unmodifiableMap(operationSuppliers);
    }

    @SuppressWarnings("unchecked")
    public <T extends EntandoCustomResource, D extends Doneable<T>> CustomResourceOperationsImpl<T,
            CustomResourceList<T>, D> getOperations(Class<T> c) {
        return this.customResourceOperations.computeIfAbsent(c, aClass -> OPERATION_SUPPLIERS.get(aClass).get(client));
    }

    private interface OperationsSupplier {

        CustomResourceOperationsImpl get(KubernetesClient client);
    }

}
