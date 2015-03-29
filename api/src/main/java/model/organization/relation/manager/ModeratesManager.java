package model.organization.relation.manager;

import java.util.Set;

import model.organization.entity.Attribute;
import model.organization.entity.Pmf;
import model.organization.id.UniqueId;
import model.organization.relation.Moderates;

/**
 * The {@linkplain ModeratesManager} interface defines the APIs for managing {@linkplain Moderates}.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface ModeratesManager {
	/**
	 * Creates a moderates relation between a {@linkplain Pmf} and an {@linkplain Attribute} in this {@linkplain ModeratesManager}.
	 *
	 * @param pmfId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 */
	void addModerates(UniqueId<Pmf> pmfId, UniqueId<Attribute> attributeId);

	/**
	 * Returns an {@linkplain Attribute}, if it exists, that is moderated by a {@linkplain Pmf} in this {@linkplain ModeratesManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Pmf}
	 * @return an {@linkplain Attribute} if it exists, <code>null</code> otherwise.
	 */
	Attribute getModerates(UniqueId<Pmf> id);

	/**
	 * Returns a set of {@linkplain Pmf}s that moderates an {@linkplain Attribute} in this {@linkplain ModeratesManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 * @return a set of {@linkplain Pmf}s.
	 */
	Set<Pmf> getModeratedBy(UniqueId<Attribute> id);

	/**
	 * Removes a moderates relation between a {@linkplain Pmf} and ad {@linkplain Attribute} in this {@linkplain ModeratesManager}.
	 *
	 * @param pmfId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Pmf}.
	 * @param attributeId
	 *            the {@linkplain UniqueId} that represents the {@linkplain Attribute}.
	 */
	void removeModerates(UniqueId<Pmf> pmfId, UniqueId<Attribute> attributeId);

	/**
	 * Removes all moderates relations from this {@linkplain ModeratesManager}.
	 */
	void removeAllModerates();
}