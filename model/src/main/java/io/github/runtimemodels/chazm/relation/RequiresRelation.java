package io.github.runtimemodels.chazm.relation;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.message.M;
import lombok.Getter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class RequiresRelation implements Requires {

    @Getter
    private final Role role;
    @Getter
    private final Capability capability;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    RequiresRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Capability capability) {
        this.role = role;
        this.capability = capability;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Requires) {
            final Requires requires = (Requires) object;
            return getRole().equals(requires.getRole()) && getCapability().equals(requires.getCapability());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getRole(), getCapability());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.RELATION.get(getRole().getId(), getCapability().getId());
        }
        return toString;
    }

}
