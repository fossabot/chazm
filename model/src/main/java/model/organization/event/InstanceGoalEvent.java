package model.organization.event;

import static model.organization.validation.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.InstanceGoal;
import model.organization.entity.InstanceGoal.Parameter;
import model.organization.entity.SpecificationGoal;
import model.organization.id.UniqueId;

import com.google.inject.assistedinject.Assisted;

/**
 * The {@linkplain InstanceGoalEvent} class indicates that there is an update about an {@linkplain InstanceGoal} entity.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public class InstanceGoalEvent extends AbstractEvent {

	private static final long serialVersionUID = -7178745959528744216L;
	private final UniqueId<InstanceGoal> id;
	private final UniqueId<SpecificationGoal> specificationGoalId;
	private final Parameter parameter;
	private transient Integer hashCode = null;
	private transient String toString = null;

	@Inject
	InstanceGoalEvent(@NotNull @Assisted final EventCategory category, @NotNull @Assisted final InstanceGoal goal) {
		super(category);
		checkNotNull(goal, "goal");
		id = goal.getId();
		specificationGoalId = goal.getGoal().getId();
		parameter = goal.getParameter();
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain InstanceGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<InstanceGoal> getId() {
		return id;
	}

	/**
	 * Returns a {@linkplain UniqueId} that represents a {@linkplain SpecificationGoal}.
	 *
	 * @return a {@linkplain UniqueId}.
	 */
	public UniqueId<SpecificationGoal> getSpecificationGoalId() {
		return specificationGoalId;
	}

	/**
	 * Returns a {@linkplain Parameter}.
	 *
	 * @return a {@linkplain Parameter}.
	 */
	public Parameter getParameter() {
		return parameter;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof InstanceGoalEvent) {
			final InstanceGoalEvent event = (InstanceGoalEvent) object;
			return super.equals(event) && getId().equals(event.getId()) && getSpecificationGoalId().equals(event.getSpecificationGoalId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			final int prime = 31;
			hashCode = super.hashCode();
			hashCode = prime * hashCode + getId().hashCode();
			hashCode = prime * hashCode + getSpecificationGoalId().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = String.format("%s(%s, %s, %s, %s)", getClass().getSimpleName(), getCategory(), getId(), getSpecificationGoalId(), getParameter());
		}
		return toString;
	}

}