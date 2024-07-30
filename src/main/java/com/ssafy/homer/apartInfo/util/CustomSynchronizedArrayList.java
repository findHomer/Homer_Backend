package com.ssafy.homer.apartInfo.util;

import java.util.ArrayList;

public class CustomSynchronizedArrayList<E> extends ArrayList<E> {
    @Override
    public synchronized boolean add(E element) {
        return super.add(element);
    }
}
