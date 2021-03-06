package io.github.runtimemodels.chazm.relation;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.message.M;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

class ContainsRelation implements Contains {

    @Getter
    private final Role role;
    @Getter
    private final Characteristic characteristic;
    @Getter
    @Setter
    private double value;
    private transient Integer hashCode = null;

    @Inject
    ContainsRelation(@NotNull @Assisted final Role role, @NotNull @Assisted final Characteristic characteristic, @NotNull @Assisted final double value) {
        this.role = role;
        this.characteristic = characteristic;
        this.value = value;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Contains) {
            final Contains contains = (Contains) object;
            return getRole().equals(contains.getRole()) && getCharacteristic().equals(contains.getCharacteristic());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getRole(), getCharacteristic());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return M.RELATION_WITH_VALUE.get(getRole().getId(), getCharacteristic().getId(), getValue());
    }

}
