package com.mobile.inject;

/**
 * @author tony.wang
 * @time 2020/5/44
 * @description IBinder
 */
public interface IBinder<T> {

    /**
     * 绑定activity
     *
     * @param t
     */
    void bind(T t);

}
