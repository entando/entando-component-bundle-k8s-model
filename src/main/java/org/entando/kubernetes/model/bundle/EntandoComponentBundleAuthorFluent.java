package org.entando.kubernetes.model.bundle;

public abstract class EntandoComponentBundleAuthorFluent<N extends EntandoComponentBundleAuthorFluent<N>> {

    private String name;
    private String email;

    public EntandoComponentBundleAuthorFluent() {
    }

    public EntandoComponentBundleAuthorFluent(EntandoComponentBundleAuthor author) {
        this.name = author.getName();
        this.email = author.getEmail();
    }

    public EntandoComponentBundleAuthor build() {
        return new EntandoComponentBundleAuthor(this.name, this.email);
    }

    public N withName(String name) {
        this.name = name;
        return thisAsN();
    }

    public N withEmail(String email){
        this.email = email;
        return thisAsN();
    }

    private N thisAsN() {
        return (N) this;
    }
}
