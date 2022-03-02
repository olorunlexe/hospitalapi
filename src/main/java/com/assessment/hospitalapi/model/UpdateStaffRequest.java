package com.assessment.hospitalapi.model;

public class UpdateStaffRequest {
    private String updateName;
    private String uuid;

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
