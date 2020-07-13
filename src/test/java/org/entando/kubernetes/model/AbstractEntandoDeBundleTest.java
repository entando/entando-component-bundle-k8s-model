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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import io.fabric8.kubernetes.client.CustomResourceList;
import io.fabric8.kubernetes.client.dsl.internal.CustomResourceOperationsImpl;
import org.entando.kubernetes.model.bundle.DoneableEntandoComponentBundle;
import org.entando.kubernetes.model.bundle.EntandoComponentBundle;
import org.entando.kubernetes.model.bundle.EntandoComponentBundleBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractEntandoDeBundleTest implements CustomResourceTestUtil {

    public static final String CODE = "my-bundle";
    public static final String TITLE = "This is the bundle title";
    public static final String DESCRIPTION = "This is the bundle description";
    public static final String AUTHOR_NAME = "Jack Bauer";
    public static final String AUTHOR_EMAIL = "jbauer@24.com";
    public static final String ORGANIZATION = "ctu";
    public static final String REPO_URL = "http://inexistent.entando.com/my-repo.git";
    public static final String VERSION_1_VERSION = "0.0.1";
    public static final String VERSION_1_INTEGRITY = "asdfasdf";
    public static final String VERSION_1_TIMESTAMP = "2020-07-13T09:00:00Z";
    public static final String VERSION_2_VERSION = "0.0.2";
    public static final String VERSION_2_INTEGRITY = "";
    public static final String VERSION_2_TIMESTAMP = "2020-07-15T08:00:00Z";
    public static final String THUMBNAIL = "Jyt6tAV2CLeDid2LiT34tA";
    public static final String IMAGE_1_URL = "http://example.com/images/1";
    public static final String IMAGE_2_URL = "http://example.com/images/2";
    protected static final String NAMESPACE = TestConfig.calculateNameSpace("my-namespace");
    private EntandoResourceOperationsRegistry registry;

    @BeforeEach
    public void deleteAllEntandoComponentBundles() {
        this.registry = new EntandoResourceOperationsRegistry(getClient());
        prepareNamespace(entandoComponentBundles(), NAMESPACE);
    }

    @Test
    public void testCreateEntandoComponentBundle() {
        //Given
        EntandoComponentBundle ecb = new EntandoComponentBundleBuilder()
                .withNewMetadata().withName(CODE)
                .withNamespace(NAMESPACE)
                .endMetadata()
                .withNewSpec()
                .withCode(CODE)
                .withTitle(TITLE)
                .withDescription(DESCRIPTION)
                .withThumbnail(THUMBNAIL)
                .withNewAuthor()
                .withName(AUTHOR_NAME)
                .withEmail(AUTHOR_EMAIL)
                .endAuthor()
                .withNewImages()
                .addImageUrl(IMAGE_1_URL)
                .addImageUrl(IMAGE_2_URL)
                .endImages()
                .withOrganization(ORGANIZATION)
                .withUrl(REPO_URL)
                .addNewVersion()
                .withTimestamp(VERSION_1_TIMESTAMP)
                .withIntegrity(VERSION_1_INTEGRITY)
                .withVersion(VERSION_1_VERSION)
                .endVersion()
                .addNewVersion()
                .withVersion(VERSION_2_VERSION)
                .withIntegrity(VERSION_2_INTEGRITY)
                .withTimestamp(VERSION_2_TIMESTAMP)
                .endVersion()
                .endSpec()
                .build();
        entandoComponentBundles().inNamespace(NAMESPACE).createNew().withMetadata(ecb.getMetadata())
                .withSpec(ecb.getSpec()).done();
        //When
        EntandoComponentBundle actual = entandoComponentBundles().inNamespace(NAMESPACE).withName(CODE).get();

        //Then
        assertThat(actual.getMetadata().getName(), is(CODE));
        assertThat(actual.getSpec().getCode(), is(CODE));
        assertThat(actual.getSpec().getDescription(), is(DESCRIPTION));
        assertThat(actual.getSpec().getTitle(), is(TITLE));
        assertThat(actual.getSpec().getAuthor().getName(), is(AUTHOR_NAME));
        assertThat(actual.getSpec().getAuthor().getEmail(), is(AUTHOR_EMAIL));
        assertThat(actual.getSpec().getOrganization(), is(ORGANIZATION));
        assertThat(actual.getSpec().getUrl(), is(REPO_URL));
        assertThat(actual.getSpec().getThumbnail(), is(THUMBNAIL));
        assertThat(actual.getSpec().getImages(), hasSize(2));
        assertThat(actual.getSpec().getImages().get(0).getUrl(), is(IMAGE_1_URL));
        assertThat(actual.getSpec().getImages().get(1).getUrl(), is(IMAGE_2_URL));
        assertThat(actual.getSpec().getVersions(), hasSize(2));
        assertThat(actual.getSpec().getVersions().get(0).getVersion(), is(VERSION_1_VERSION));
        assertThat(actual.getSpec().getVersions().get(0).getIntegrity(), is(VERSION_1_INTEGRITY));
        assertThat(actual.getSpec().getVersions().get(0).getTimestamp(), is(VERSION_1_TIMESTAMP));
        assertThat(actual.getSpec().getVersions().get(1).getVersion(), is(VERSION_2_VERSION));
        assertThat(actual.getSpec().getVersions().get(1).getIntegrity(), is(VERSION_2_INTEGRITY));
        assertThat(actual.getSpec().getVersions().get(1).getTimestamp(), is(VERSION_2_TIMESTAMP));
    }

    @Test
    public void testEdit() {
        //Given
        EntandoComponentBundle ecb = new EntandoComponentBundleBuilder()
                .withNewMetadata()
                .withName(CODE)
                .withNamespace(NAMESPACE)
                .endMetadata()
                .withNewSpec()
                .withCode(CODE)
                .withTitle(TITLE)
                .withDescription(DESCRIPTION)
                .withOrganization(ORGANIZATION)
                .withThumbnail("H0cFRNTEJt8EZBcL17_iww")
                .withNewImages()
                .addImageUrl(IMAGE_1_URL)
                .addImageUrl(IMAGE_2_URL)
                .endImages()
                .addNewVersion()
                .withVersion(VERSION_1_VERSION)
                .withTimestamp(VERSION_1_TIMESTAMP)
                .withIntegrity(VERSION_1_INTEGRITY)
                .endVersion()
                .endSpec()
                .build();
        //When
        //We are not using the mock server here because of a known bug
        EntandoComponentBundle actual = editEntandoComponentBundle(ecb)
                .editMetadata()
                .endMetadata()
                .editSpec()
                .withTitle("My new title")
                .withDescription("My new description")
                .withNewAuthor()
                .withName(AUTHOR_NAME)
                .withEmail(AUTHOR_EMAIL)
                .endAuthor()
                .withThumbnail(THUMBNAIL)
                .withNoVersions()
                .addNewVersion()
                .withVersion(VERSION_2_VERSION)
                .withIntegrity(VERSION_2_INTEGRITY)
                .withTimestamp(VERSION_2_TIMESTAMP)
                .endVersion()
                .endSpec()
                .done();
        //Then
        assertThat(actual.getSpec().getCode(), is(CODE));
        assertThat(actual.getSpec().getTitle(), is("My new title"));
        assertThat(actual.getSpec().getDescription(), is("My new description"));
        assertThat(actual.getSpec().getThumbnail(), is(THUMBNAIL));
        assertThat(actual.getSpec().getAuthor().getName(), is(AUTHOR_NAME));
        assertThat(actual.getSpec().getAuthor().getEmail(), is(AUTHOR_EMAIL));
        assertThat(actual.getMetadata().getName(), is(CODE));
    }

    protected DoneableEntandoComponentBundle editEntandoComponentBundle(EntandoComponentBundle cb) {
        entandoComponentBundles().inNamespace(NAMESPACE).create(cb);
        return entandoComponentBundles().inNamespace(NAMESPACE).withName(CODE).edit();
    }

    protected CustomResourceOperationsImpl<EntandoComponentBundle, CustomResourceList<EntandoComponentBundle>,
            DoneableEntandoComponentBundle> entandoComponentBundles() {
        return registry.getOperations(EntandoComponentBundle.class);
    }

}
