package org.agatom.springatom.data.loader.event;


import org.springframework.data.rest.core.event.AfterCreateEvent;


public class PostDataLoadEvent
        extends AfterCreateEvent {
    private static final long serialVersionUID = -5614627089517185377L;
    private final String loader;

    public PostDataLoadEvent(final String loader, final Object source) {
        super(source);
        this.loader = loader;
    }

    public String getLoader() {
        return this.loader;
    }
}
