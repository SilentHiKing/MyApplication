package com.hiking.common.preloader;


import com.hiking.common.preloader.interfaces.DataListener;

/**
 * data load finished, waiting for {@link DataListener}
 */
class StateLoadCompleted extends StateBase {
    StateLoadCompleted(Worker<?> worker) {
        super(worker);
    }

    @Override
    public boolean refresh() {
        super.refresh();
        return worker.doStartLoadWork();
    }

    @Override
    public boolean listenData() {
        super.listenData();
        return worker.doSendLoadedDataToListenerWork();
    }

    @Override
    public boolean listenData(DataListener listener) {
        super.listenData(listener);
        return worker.doSendLoadedDataToListenerWork(listener);
    }

    @Override
    public String name() {
        return "StateLoadCompleted";
    }
}
