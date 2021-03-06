package io.github.runtimemodels.chazm.event;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The {@linkplain RoleEvent} class indicates that there is an update about a {@linkplain Role} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class RoleEvent extends AbstractEvent {

    private static final long serialVersionUID = -5258695088891301883L;
    private final UniqueId<Role> id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    RoleEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Role role) {
        super(category);
        id = role.getId();
    }

    /**
     * Returns a {@linkplain UniqueId} that represents a {@linkplain SpecificationGoal}.
     *
     * @return a {@linkplain UniqueId}.
     */
    public UniqueId<Role> getId() {
        return id;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof RoleEvent) {
            final RoleEvent event = (RoleEvent) object;
            return super.equals(event) && getId().equals(event.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == null) {
            hashCode = Objects.hash(getCategory(), getId());
        }
        return hashCode;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = M.EVENT_WITH_1_ID.get(super.toString(), getId());
        }
        return toString;
    }

}
