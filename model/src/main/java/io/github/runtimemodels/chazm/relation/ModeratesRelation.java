package io.github.runtimemodels.chazm.relation;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.message.M;
import lombok.Getter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class ModeratesRelation implements Moderates {

    @Getter
    private final Pmf pmf;
    @Getter
    private final Attribute attribute;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    ModeratesRelation(@NotNull @Assisted final Pmf pmf, @NotNull @Assisted final Attribute attribute) {
        this.pmf = pmf;
        this.attribute = attribute;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Moderates) {
            final Moderates moderates = (Moderates) object;
            return getPmf().equals(moderates.getPmf()) && getAttribute().equals(moderates.getAttribute());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getPmf(), getAttribute());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.RELATION.get(getPmf().getId(), getAttribute().getId());
        }
        return toString;
    }

}
