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

public abstract class EntandoComponentBundleVersionFluent<N extends EntandoComponentBundleVersionFluent<N>> {

    private String version;
    private String integrity;
    private String timestamp;

    public EntandoComponentBundleVersionFluent(EntandoComponentBundleVersion tag) {
        this.version = tag.getVersion();
        this.integrity = tag.getIntegrity();
        this.timestamp = tag.getTimestamp();
    }

    public EntandoComponentBundleVersionFluent() {
    }

    public EntandoComponentBundleVersion build() {
        return new EntandoComponentBundleVersion(version, integrity, timestamp);
    }

    public N withVersion(String version) {
        this.version = version;
        return thisAsN();
    }

    public N withIntegrity(String integrity) {
        this.integrity = integrity;
        return thisAsN();
    }

    public N withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return thisAsN();
    }

    @SuppressWarnings("unchecked")
    protected N thisAsN() {
        return (N) this;
    }
}
