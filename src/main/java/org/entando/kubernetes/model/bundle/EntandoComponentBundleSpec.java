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

import static org.entando.kubernetes.model.Coalescence.coalesce;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.quarkus.runtime.annotations.RegisterForReflection;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize
@JsonDeserialize()
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, isGetterVisibility = Visibility.NONE, getterVisibility = Visibility.NONE,
        setterVisibility = Visibility.NONE)
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntandoComponentBundleSpec implements Serializable {

    private String code;
    private String description;
    private String title;
    private EntandoComponentBundleAuthor author;
    private String organization;
    private String thumbnail;
    private List<EntandoComponentBundleImage> images = new ArrayList<>();
    private String url;
    private List<EntandoComponentBundleVersion> versions = new ArrayList<>();

    public EntandoComponentBundleSpec() {
        super();
    }

    @SuppressWarnings("unchecked")
    public EntandoComponentBundleSpec(String code, String title, String description, EntandoComponentBundleAuthor author,
            String organization, String thumbnail, List<EntandoComponentBundleImage> images, String url,
            List<EntandoComponentBundleVersion> versionList) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.author = author;
        this.organization = organization;
        this.thumbnail = thumbnail;
        this.images = coalesce(images, this.images);
        this.url = url;
        this.versions = coalesce(versionList, this.versions);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public EntandoComponentBundleAuthor getAuthor() {
        return author;
    }

    public String getOrganization() {
        return organization;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public List<EntandoComponentBundleImage> getImages() {
        return images;
    }

    public String getUrl() {
        return url;
    }

    public List<EntandoComponentBundleVersion> getVersions() {
        return this.versions;
    }
}
