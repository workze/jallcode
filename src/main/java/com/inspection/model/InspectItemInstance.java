package com.inspection.model;

import lombok.Data;


@Data
public class InspectItemInstance {
    String inspectItemId;

    String instanceId;
    String instanceIp;

    String value;
    String healthStatus;

    InspectResult result;

    public boolean isFinished(){
        return true;
    }
}
