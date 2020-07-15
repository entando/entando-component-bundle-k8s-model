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

import io.fabric8.kubernetes.api.builder.Nested;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EntandoComponentBundleSpecFluent<A extends EntandoComponentBundleSpecFluent> {

    protected String code;
    protected String title;
    protected String description;
    protected EntandoComponentBundleAuthor author;
    protected String organization;
    protected String thumbnail;
    protected String url;
    protected List<EntandoComponentBundleVersionBuilder> versions = new ArrayList<>();

    public EntandoComponentBundleSpecFluent(EntandoComponentBundleSpec spec) {
        this.code = spec.getCode();
        this.title = spec.getTitle();
        this.description = spec.getDescription();
        this.author = spec.getAuthor();
        this.organization = spec.getOrganization();
        this.thumbnail = spec.getThumbnail();
        this.url = spec.getUrl();
        this.versions = createTagBuilders(spec.getVersions());
    }

    public EntandoComponentBundleSpecFluent() {
    }

    private List<EntandoComponentBundleVersionBuilder> createTagBuilders(List<EntandoComponentBundleVersion> versions) {
        return versions.stream().map(EntandoComponentBundleVersionBuilder::new).collect(Collectors.toList());
    }

    public A withCode(String code) {
        this.code = code;
        return thisAsA();
    }

    public A withTitle(String title){
       this.title = title;
       return thisAsA();
    }

    public A withDescription(String description) {
        this.description = description;
        return thisAsA();
    }

    public A withAuthor(EntandoComponentBundleAuthor author) {
        this.author = author;
        return thisAsA();
    }
    
    public AuthorNested<A> withNewAuthor() {
        return new AuthorNested<>(thisAsA());
    }
    
    public A withOrganization(String organization){
       this.organization = organization;
       return thisAsA();
    }

    public A withThumbnail(String thumbnail){
       this.thumbnail = thumbnail;
       return thisAsA();
    }

    public A withUrl(String url){
       this.url = url;
       return thisAsA();
    }

    public A withVersions(List<EntandoComponentBundleVersion> versionList) {
        this.versions = versionList.stream().map(EntandoComponentBundleVersionBuilder::new).collect(Collectors.toList());
        return thisAsA();
    }

    public A withNoVersions() {
        this.versions = new ArrayList<>();
        return thisAsA();
    }

    public VersionNested<A> addNewVersion() {
        return new VersionNested<>(thisAsA());
    }

    public A addToVersions(EntandoComponentBundleVersion tag) {
        this.versions.add(new EntandoComponentBundleVersionBuilder(tag));
        return thisAsA();
    }

    @SuppressWarnings("unchecked")
    protected A thisAsA() {
        return (A) this;
    }

    public EntandoComponentBundleSpec build() {
        return new EntandoComponentBundleSpec(
                this.code,
                this.title,
                this.description,
                this.author,
                this.organization,
                this.thumbnail,
                this.url,
                this.versions.stream().map(EntandoComponentBundleVersionFluent::build).collect(Collectors.toList()));
    }

    public static class VersionNested<N extends EntandoComponentBundleSpecFluent> extends
            EntandoComponentBundleVersionFluent<VersionNested<N>> implements Nested<N> {

        private final N parentBuilder;

        public VersionNested(N parentBuilder) {
            super();
            this.parentBuilder = parentBuilder;
        }

        @SuppressWarnings("unchecked")
        public N and() {
            return (N) parentBuilder.addToVersions(super.build());
        }

        public N endVersion() {
            return and();
        }
    }

    public static class AuthorNested<N extends EntandoComponentBundleSpecFluent>
            extends EntandoComponentBundleAuthorFluent<AuthorNested<N>> implements Nested<N> {

        private final N parentBuilder;

        public AuthorNested(N parentBuilder) {
            super();
            this.parentBuilder = parentBuilder;
        }

        @SuppressWarnings("unchecked")
        public N and() {
            return (N) parentBuilder.withAuthor(super.build());
        }

        public N endAuthor() {
            return and();
        }

    }

}
