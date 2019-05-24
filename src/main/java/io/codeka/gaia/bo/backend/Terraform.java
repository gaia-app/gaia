package io.codeka.gaia.bo.backend;

public class Terraform {
    private Backend backend;

    public Backend getBackend() {
        if(backend == null){
            backend = new Backend();
        }
        return backend;
    }

    public void setBackend(Backend backend) {
        this.backend = backend;
    }
}
