package org.agatom.springatom.data.loader.event;

import org.springframework.data.rest.core.event.BeforeCreateEvent;

public class PreDataLoadEvent
        extends BeforeCreateEvent {
    private static final long serialVersionUID = -5614627089517185377L;
    private final String loader;

    public PreDataLoadEvent(final String loader, final Object source) {
        super(source);
        this.loader = loader;
    }

    public String getLoader() {
        return this.loader;
    }
}
