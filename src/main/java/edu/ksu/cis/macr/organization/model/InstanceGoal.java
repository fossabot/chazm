/*
 * InstanceGoal.java
 * 
 * Created on Jul 30, 2007
 * 
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model;

import java.util.Set;

import edu.ksu.cis.macr.organization.model.identifiers.UniqueIdentifier;
import edu.ksu.cis.macr.organization.model.simple.SimpleGoal;

/**
 * The <code>InstanceGoal</code> interface defines the concrete instance of a
 * {@link SpecificationGoal} that belongs in the active goal set.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.4.2.1 $, $Date: 2010/07/30 19:19:36 $
 * @param <ParameterType>
 *            the sub-type of the parameter for the <code>InstanceGoal</code>.
 * @since 3.4
 */
public interface InstanceGoal<ParameterType> extends SimpleGoal {

	/**
	 * Returns the <code>SpecificationGoal</code> that this
	 * <code>InstanceGoal</code> is based on.
	 * 
	 * @return the <code>SpecificationGoal</code> that this
	 *         <code>InstanceGoal</code> is based on.
	 */
	SpecificationGoal getSpecificationGoal();

	/**
	 * Returns the <code>UniqueIdentifier</code> identifying the
	 * <code>SpecificationGoal</code> that this <code>InstanceGoal</code> is
	 * based on.
	 * 
	 * @return the <code>UniqueIdentifier</code> identifying the
	 *         <code>SpecificationGoal</code> that this
	 *         <code>InstanceGoal</code> is based on.
	 */
	UniqueIdentifier getSpecificationIdentifier();

	/**
	 * Returns the <code>UniqueIdentifier</code> identifying this
	 * <code>InstanceGoal</code> with respect to the
	 * <code>SpecificationGoal</code>.
	 * 
	 * @return the <code>UniqueIdentifier</code> identifying this
	 *         <code>InstanceGoal</code> with respect the
	 *         <code>SpecificationGoal</code> that this
	 *         <code>InstanceGoal</code> is based on.
	 */
	UniqueIdentifier getInstanceIdentifier();

	/**
	 * Returns the parameter(s) of this <code>InstanceGoal</code>.
	 * 
	 * @return the parameter(s) of this <code>InstanceGoal</code>.
	 */
	ParameterType getParameter();

	/**
	 * Returns the set of <code>Role</code> that achieves this
	 * <code>SpecificationGoal</code>.
	 * 
	 * @return the set of <code>Role</code> that achieves this
	 *         <code>SpecificationGoal</code>.
	 * @see SpecificationGoal#getAchievedBySet()
	 */
	Set<Role> getAchievedBySet();

}