package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.relation.Needs;

/**
 * The {@linkplain NeedsEventFactory} interface defines the API for constructing {@linkplain NeedsEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface NeedsEventFactory {

    /**
     * Constructs an {@linkplain NeedsEvent}.
     *
     * @param category the {@linkplain EventCategory}.
     * @param needs    the {@linkplain Needs}.
     * @return a {@linkplain NeedsEvent}.
     */
    NeedsEvent build(EventCategory category, Needs needs);

}
