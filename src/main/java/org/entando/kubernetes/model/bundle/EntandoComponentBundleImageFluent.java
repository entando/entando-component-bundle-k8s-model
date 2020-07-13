package org.entando.kubernetes.model.bundle;

public abstract class EntandoComponentBundleImageFluent<N extends EntandoComponentBundleImageFluent<N>> {

    private String url;

    public EntandoComponentBundleImageFluent(String url) {
        this.url = url;
    }

    public EntandoComponentBundleImageFluent(EntandoComponentBundleImage image) {
        this.url = image.getUrl();
    }

    public EntandoComponentBundleImageFluent() {
    }

    public EntandoComponentBundleImage build() {
        return new EntandoComponentBundleImage(this.url);
    }

    public N withUrl(String url) {
        this.url = url;
        return (N) this;
    }


}
