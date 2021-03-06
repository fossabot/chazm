package io.github.runtimemodels.chazm.event;

import io.github.runtimemodels.chazm.entity.InstanceGoal;

/**
 * The {@linkplain InstanceGoalEventFactory} interface defines the API for constructing {@linkplain InstanceGoalEvent}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface InstanceGoalEventFactory {

    /**
     * Constructs an {@linkplain InstanceGoalEvent}.
     *
     * @param category the {@linkplain EventCategory}.
     * @param goal     the {@linkplain InstanceGoal}.
     * @return a {@linkplain InstanceGoalEvent}.
     */
    InstanceGoalEvent build(EventCategory category, InstanceGoal goal);

}
