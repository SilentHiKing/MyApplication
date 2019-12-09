package com.hiking.common.preloader;


/**
 * initial state
 */
class StatusInitialed extends StateBase {

    StatusInitialed(Worker<?> worker) {
        super(worker);
    }

    @Override
    public boolean startLoad() {
        super.startLoad();
        return worker.doStartLoadWork();
    }

    @Override
    public String name() {
        return "StatusInitialed";
    }
}
