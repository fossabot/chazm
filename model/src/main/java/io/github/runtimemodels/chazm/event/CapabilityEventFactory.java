package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.Capability;

/**
 * The {@linkplain CapabilityEventFactory} interface defines the API for constructing {@linkplain CapabilityEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface CapabilityEventFactory {

    /**
     * Constructs an {@linkplain CapabilityEvent}.
     *
     * @param category   the {@linkplain EventCategory}.
     * @param capability the {@linkplain Capability}.
     * @return a {@linkplain CapabilityEvent}.
     */
    CapabilityEvent build(EventCategory category, Capability capability);

}
