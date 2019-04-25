package com.inspection.inner;

import com.inspection.classuml.BaseAlgorithm;
import com.inspection.classuml.InspectItemInstance;

public class InspectItemInstanceRunnable implements Runnable{

    InspectItemInstance inspectItem;
    BaseAlgorithm algorithm;

    InspectItemInstanceRunnable(final InspectItemInstance inspectItem){
        this.inspectItem = inspectItem;
    }

    @Override
    public void run() {
        try {
            String algorithmName = "";
            algorithm = (BaseAlgorithm) Class.forName(algorithmName).newInstance();
            algorithm.execute("");

        } catch (Exception e) {
            //
        }
    }
}
