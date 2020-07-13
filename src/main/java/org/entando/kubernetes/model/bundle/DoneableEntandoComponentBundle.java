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

package org.entando.kubernetes.model.bundle;

import io.fabric8.kubernetes.api.builder.Function;
import io.fabric8.kubernetes.api.model.Doneable;

public class DoneableEntandoComponentBundle extends EntandoComponentBundleFluent<DoneableEntandoComponentBundle>
        implements Doneable<EntandoComponentBundle> {

    private final Function<EntandoComponentBundle, EntandoComponentBundle> function;

    public DoneableEntandoComponentBundle(Function<EntandoComponentBundle, EntandoComponentBundle> function) {
        this.function = function;
    }

    public DoneableEntandoComponentBundle(EntandoComponentBundle resource, Function<EntandoComponentBundle, EntandoComponentBundle> function) {
        super(resource.getSpec(), resource.getMetadata());
        this.function = function;
    }


    @Override
    public EntandoComponentBundle done() {
        return function.apply(new EntandoComponentBundle(metadata.build(), spec.build()));
    }
}
