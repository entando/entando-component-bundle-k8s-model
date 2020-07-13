package org.entando.kubernetes.model.bundle;

public class EntandoComponentBundleImage {

    public String url;

    public EntandoComponentBundleImage(String url) {
        this.url = url;
    }

    public EntandoComponentBundleImage() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
