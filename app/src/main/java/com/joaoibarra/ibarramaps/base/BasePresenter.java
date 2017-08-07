package com.joaoibarra.ibarramaps.base;

/**
 * Created by joaoibarra on 29/07/17.
 */

public interface BasePresenter<V> {
    void attach(V view);
    void detach();
}
