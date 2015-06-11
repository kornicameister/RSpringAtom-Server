package org.agatom.springatom.data.service.core;


import java.io.InputStream;

public interface NDataLoaderService<T> {

  T loadData(final InputStream stream) throws Exception;

}
