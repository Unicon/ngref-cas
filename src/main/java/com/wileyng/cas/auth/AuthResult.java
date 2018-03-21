package com.wileyng.cas.auth;

import java.util.Map;

public class AuthResult {

    private String id;
    private Map<String, Object> attributes;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> gettAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


}
