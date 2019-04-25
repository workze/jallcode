package com.inspection.inner;

import com.inspection.classuml.BaseAlgorithm;
import com.inspection.classuml.InspectItemInstance;

public class ConnectAlgorithm extends BaseAlgorithm {

    ConnectAlgorithmParam param;

    @Override
    public Object execute(final String item) {
        applyParam();
        //item.getInspectResult().setStatus(InspectStatusEnum.GREEN);
        return null;
    }

    void applyParam(){

    }
}
