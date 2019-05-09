package com.admin.auris_updated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InsertPojo {
    @SerializedName("error")
    @Expose
    private Boolean error;

    public Boolean getError() {
        return error;
    }
    public void setError(Boolean error) {
        this.error = error;
    }

}
