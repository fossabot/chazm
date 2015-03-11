/*
 * Attribute.java
 *
 * Created on Oct 15, 2009
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.entity;

import java.util.Set;

import org.models.organization.Organization;
import org.models.organization.identifier.Identifiable;
import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain Attribute} interface defines the attribute entity of an {@linkplain Organization}.
 *
 * @author Christopher Zhong
 * @since 5.0
 */
public interface Attribute extends Identifiable {
	/**
	 * The {@linkplain Type}> enumerates the various types of {@linkplain Attribute}s.
	 *
	 * @author Christopher Zhong
	 * @since 5.0
	 */
	enum Type {
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>&#8734;</code>), and that higher values are better.
		 */
		POSITIVE_QUANTITY,
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>&#8734;</code>), and that lower values are better.
		 */
		NEGATIVE_QUANTITY,
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>1.0</code>), and that higher values are better.
		 */
		POSITIVE_QUALITY,
		/**
		 * Indicates a quantity-type {@linkplain Attribute} (whose range is from <code>0.0</code> to <code>1.0</code>), and that lower values are better.
		 */
		NEGATIVE_QUALITY,
		/**
		 * Indicates an unbounded-type {@linkplain Attribute} (whose range is from <code>-&#8734;</code> to <code>+&#8734;</code>), and that higher values are
		 * better.
		 */
		POSITIVE_UNBOUNDED,
		/**
		 * Indicates an unbounded-type {@linkplain Attribute} (whose range is from <code>-&#8734;</code> to <code>+&#8734;</code>), and that lower values are
		 * better.
		 */
		NEGATIVE_UNBOUNDED;
	}

	/**
	 * Returns the {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 *
	 * @return the {@linkplain Attribute.Type} of this {@linkplain Attribute}.
	 */
	Type getType();

	/**
	 * Returns the set of <code>Role</code>s that influences this <code>Attribute</code>.
	 *
	 * @return the set of <code>Role</code>s that influences this <code>Attribute</code>.
	 */
	Set<Role> getInfluencedBySet();

	/**
	 * Returns the set of <code>Agent</code> that has this <code>Attribute</code>.
	 *
	 * @return the set of <code>Agent</code> that has this <code>Attribute</code>.
	 */
	Set<Agent> getHadBySet();

	/**
	 * Returns the score of the <code>Agent</code> (from the given <code>UniqueIdentifier</code>) that has this <code>Attribute</code>.
	 *
	 * @param agentIdentifier
	 *            the <code>UniqueIdentifier</code> identifying the <code>Agent</code> to retrieve the score.
	 * @return the score if it exists, <code>null</code> otherwise.
	 */
	Double getHadByValue(UniqueId agentIdentifier);

	/**
	 * Returns the set of <code>PerformanceFunction</code> that moderates this <code>Attribute</code>.
	 *
	 * @return the set of <code>PerformanceFunction</code> that moderates this <code>Attribute</code>.
	 */
	Set<PerformanceFunction> getModeratedBySet();
}
