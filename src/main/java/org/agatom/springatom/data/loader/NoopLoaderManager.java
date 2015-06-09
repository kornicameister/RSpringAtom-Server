package org.agatom.springatom.data.loader;

import org.agatom.springatom.data.loader.mgr.DataLoaderManager;

class NoopLoaderManager
        implements DataLoaderManager {
    @Override
    public void doLoad() {
        // do nothing
    }
}
