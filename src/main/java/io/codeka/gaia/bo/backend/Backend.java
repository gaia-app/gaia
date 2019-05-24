package io.codeka.gaia.bo.backend;

public class Backend {
    private Http http;

    public Http getHttp() {
        if(http == null){
            http = new Http();
        }
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }
}
