package io.github.runtimemodels.notification;

import com.google.inject.AbstractModule;
import io.github.runtimemodels.aop.validation.ValidationModule;

/**
 * The {@linkplain NotificationModule} class provides a Guice binding module for events (such as pub/sub event notifications).
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class NotificationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Mediator.class).to(DefaultMediator.class);
        bind(Publisher.class).to(DefaultPublisher.class);
        bind(Subscriber.class).to(DefaultSubscriber.class);
        install(new ValidationModule());
    }

}
