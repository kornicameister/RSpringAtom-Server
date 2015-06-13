package org.agatom.springatom.data.loader.srv;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;


abstract public class AbstractDataLoaderService
        implements DataLoaderService {

  @Override
  public int getOrder() {
    return 0;
  }

  protected InputStream getStream(final String path) throws IOException {
        final Resource file = new FileSystemResourceLoader().getResource(path);
        return file.getInputStream();
    }

}
