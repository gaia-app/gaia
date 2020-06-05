package io.gaia_app.stacks.bo;

import java.util.Map;

public class TerraformState {

    private String id;

    private Map<String, Object> value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getValue() {
        return value;
    }

    public void setValue(Map<String, Object> value) {
        this.value = value;
    }
}
