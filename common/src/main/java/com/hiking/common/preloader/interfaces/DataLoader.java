package com.hiking.common.preloader.interfaces;


public interface DataLoader<DATA> {

    /**
     * pre-load loaded data
     * Note: this method will runs in thread pool,
     * @return load result data (maybe null when load failed)
     */
    DATA loadData();
}
