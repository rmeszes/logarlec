package com.redvas.app.items;

public class FFP2 extends Item {
    @Override
    public void use() {
        owner().useFFP2();
        destroy();
    }
}
