package model.organization.relation;

import static model.organization.Checks.checkNotNull;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import model.organization.entity.Agent;
import model.organization.entity.Attribute;
import model.organization.entity.Attribute.Type;

import com.google.inject.assistedinject.Assisted;

class HasRelation implements Has {

	private final Agent agent;
	private final Attribute attribute;
	private double value;
	private transient Integer hashCode = null;

	@Inject
	HasRelation(@NotNull @Assisted final Agent agent, @NotNull @Assisted final Attribute attribute, @NotNull @Assisted final double value) {
		checkNotNull(agent, "agent");
		checkNotNull(attribute, "attribute");
		this.agent = agent;
		this.attribute = attribute;
		setValue(value);
	}

	@Override
	public final Agent getAgent() {
		return agent;
	}

	@Override
	public final Attribute getAttribute() {
		return attribute;
	}

	@Override
	public final double getValue() {
		return value;
	}

	@Override
	public final void setValue(final double value) {
		final Type type = getAttribute().getType();
		switch (type) {
		case POSITIVE_QUALITY:
		case NEGATIVE_QUALITY:
			if (value < QUALITY_MIN_AMOUNT || value > QUALITY_MAX_AMOUNT) {
				throw new IllegalArgumentException(String.format("For atttribute type (%s), score (%f) must be between (%f) and (%f)", type, value,
						QUALITY_MIN_AMOUNT, QUALITY_MAX_AMOUNT));
			}
			break;
		case POSITIVE_QUANTITY:
		case NEGATIVE_QUANTITY:
			if (value < QUANTITY_MIN_AMOUNT) {
				throw new IllegalArgumentException(
						String.format("For atttribute type (%s), score (%f) must be at least (%f)", type, value, QUANTITY_MIN_AMOUNT));
			}
			break;
		case NEGATIVE_UNBOUNDED:
		case POSITIVE_UNBOUNDED:
			break;
		}
		this.value = value;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof Has) {
			final Has hasRelation = (Has) object;
			return getAgent().equals(hasRelation.getAgent()) && getAttribute().equals(hasRelation.getAttribute());
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (hashCode == null) {
			hashCode = getAgent().hashCode() << 16 | getAttribute().hashCode();
		}
		return hashCode;
	}

	@Override
	public String toString() {
		return String.format("%s <-> %s: %f", getAgent().getId(), getAttribute().getId(), getValue());
	}

}