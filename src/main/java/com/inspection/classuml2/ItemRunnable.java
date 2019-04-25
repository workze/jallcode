package com.inspection.classuml2;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemRunnable implements Runnable {

    private Item item;

    @Override
    public void run() {
        Inspector inspector = new Factory().getInspector();
        Algorm algorm = new Factory().getAlgom();
        inspector.inspec(algorm);
    }
}
