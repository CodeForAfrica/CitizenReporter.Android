package org.codeforafrica.citizenreporterandroid.data.models;

import java.io.Serializable;

/**
 * Created by edwinkato on 9/28/17.
 */

public class Attachment implements Serializable{

    private String name;
    private String url;

    public Attachment(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
