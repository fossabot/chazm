package io.github.runtimemodels.chazm.event;

import com.google.inject.assistedinject.Assisted;
import io.github.runtimemodels.chazm.entity.Policy;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.M;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The {@linkplain PolicyEvent} class indicates that there is an update about a {@linkplain Policy} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class PolicyEvent extends AbstractEvent {

    private static final long serialVersionUID = 1100497597698058236L;
    private final UniqueId<Policy> id;
    private transient Integer hashCode = null;
    private transient String toString = null;

    @Inject
    PolicyEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Policy policy) {
        super(category);
        id = policy.getId();
    }

    /**
     * Returns a {@linkplain UniqueId} that represents a {@linkplain Policy}.
     *
     * @return a {@linkplain UniqueId}.
     */
    public UniqueId<Policy> getId() {
        return id;
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof PolicyEvent) {
            final PolicyEvent event = (PolicyEvent) object;
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
