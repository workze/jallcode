package com.inspection.classuml2;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class INspectService {

    ExecutorService itemService = Executors.newCachedThreadPool();

    public void inspect(){
        List<String> itemIds = null;
        for(String itemId: itemIds){
            Item item = new Factory().getItem();
            itemService.execute(new ItemRunnable(item));
        }
    }
}
