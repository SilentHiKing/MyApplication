package com.hiking.common.preloader.interfaces;

/**
 * grouped version of {@link DataLoader}
 */
public interface GroupedDataLoader<DATA> extends DataLoader<DATA> {
    /**
     * key of this data-loader in the group
     * should be unique
     * @return unique key in the group
     */
    String keyInGroup();
}
