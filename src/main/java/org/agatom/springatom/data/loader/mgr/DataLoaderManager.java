package org.agatom.springatom.data.loader.mgr;

import org.springframework.scheduling.annotation.Async;


public interface DataLoaderManager {
    @Async
    void doLoad();
}
