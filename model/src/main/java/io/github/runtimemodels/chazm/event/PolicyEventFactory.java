package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.Policy;

/**
 * The {@linkplain PolicyEventFactory} interface defines the API for constructing {@linkplain PolicyEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface PolicyEventFactory {

    /**
     * Constructs an {@linkplain PolicyEvent}.
     *
     * @param category the {@linkplain EventCategory}.
     * @param policy   the {@linkplain Policy}.
     * @return a {@linkplain PolicyEvent}.
     */
    PolicyEvent build(EventCategory category, Policy policy);

}
