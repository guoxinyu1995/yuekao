package com.example.yuekaomoni01.view;

public interface Iview<E> {
    void requestData(E e);
    void requestFail(E e);
}
