package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Attribute;
import model.organization.entity.Role;
import model.organization.id.UniqueId;
import model.organization.relation.Needs;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain NeedsEvent} class indicates that there is an update about a {@linkplain Needs} relation.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class NeedsEvent extends AbstractEvent {

	private static final long serialVersionUID = -2368504360543452399L;
	private final UniqueId<Role> roleId;
	private final UniqueId<Attribute> attributeId;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	NeedsEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final Needs needs) {
		super(category);
		checkNotNull(needs, "needs");
		roleId = needs.getRole().getId();
		attributeId = needs.getAttribute().getId();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Role}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Role> getRoleId() {
		return roleId;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain Attribute}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<Attribute> getAttributeId() {
		return attributeId;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof NeedsEvent) {
			final NeedsEvent event = (NeedsEvent) object;
			return super.equals(event) && getRoleId().equals(event.getRoleId()) && getAttributeId().equals(event.getAttributeId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getRoleId().hashCode();
			hashCode = prime * hashCode + getAttributeId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s, %s, %s)", getClass().getSimpleName(), getCategory(), getRoleId(), getAttributeId());
		}
		return toString;
	}

}