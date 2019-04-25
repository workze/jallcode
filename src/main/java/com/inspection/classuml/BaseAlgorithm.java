package com.inspection.classuml;

public abstract class BaseAlgorithm implements IAlgorithm {

    String param;

    public Object algorithm(String param){
        return execute(param);
    }

    @Override
    public abstract Object execute(String param);
}
