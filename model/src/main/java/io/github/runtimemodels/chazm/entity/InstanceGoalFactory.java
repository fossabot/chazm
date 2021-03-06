package io.github.runtimemodels.chazm.entity;

import io.github.runtimemodels.chazm.entity.InstanceGoal.Parameter;
import io.github.runtimemodels.chazm.id.UniqueId;

/**
 * The {@linkplain InstanceGoalFactory} interface defines the APIs for constructing {@linkplain InstanceGoal}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
@FunctionalInterface
public interface InstanceGoalFactory {

    /**
     * Constructs an {@linkplain InstanceGoal}.
     *
     * @param id        the {@linkplain UniqueId} that represents the {@linkplain InstanceGoal}.
     * @param goal      the {@linkplain SpecificationGoal} of the {@linkplain InstanceGoal}.
     * @param parameter the {@linkplain Parameter} of the {@linkplain InstanceGoal}.
     * @return an {@linkplain InstanceGoal}.
     */
    InstanceGoal buildInstanceGoal(UniqueId<InstanceGoal> id, SpecificationGoal goal, Parameter parameter);

}
