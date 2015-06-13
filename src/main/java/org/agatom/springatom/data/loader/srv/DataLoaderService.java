package org.agatom.springatom.data.loader.srv;


import org.springframework.core.Ordered;

import javax.annotation.Nonnull;

public interface DataLoaderService
  extends Ordered, Comparable<DataLoaderService> {
  /**
   * Method executes actual installation.
   * It should return an InstallationMarker
   *
   * @return InstallationMarker
   */
  InstallationMarker loadData();

  @Override
  default int compareTo(@Nonnull final DataLoaderService o) {
    return Integer.compare(this.getOrder(), o.getOrder());
  }

  final class InstallationMarker {
    private long      hash;
    private String    path;
    private Throwable error;

    public long getHash() {
      return hash;
    }

    public InstallationMarker setHash(final long hash) {
      this.hash = hash;
      return this;
    }

    public String getPath() {
      return path;
    }

    public InstallationMarker setPath(final String path) {
      this.path = path;
      return this;
    }

    public Throwable getError() {
      return error;
    }

    public InstallationMarker setError(final Throwable error) {
      this.error = error;
      return this;
    }
  }
}
