package io.github.runtimemodels.chazm.function;

import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.relation.Assignment;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Singleton
class DefaultEffectiveness implements Effectiveness {

    @Override
    public double compute(@NotNull final Organization organization, @NotNull final Collection<Assignment> assignments) {
        return assignments
                .parallelStream()
                .mapToDouble(
                        m -> organization.getGoodness(m.getRole().getId()).compute(organization, m.getAgent(), m.getRole(), m.getGoal(),
                                organization.getAssignments())).sum();
    }

}
