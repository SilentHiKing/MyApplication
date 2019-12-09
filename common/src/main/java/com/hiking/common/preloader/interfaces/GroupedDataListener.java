package com.hiking.common.preloader.interfaces;


public interface GroupedDataListener<DATA> extends DataListener<DATA> {
    /**
     *
     * @return
     */
    String keyInGroup();
}
