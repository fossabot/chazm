package org.models.organization.manager;

import java.util.Collection;
import java.util.Set;

import org.models.organization.entity.Role;
import org.models.organization.identifier.UniqueId;

/**
 * The {@linkplain RoleManager} interface defines the necessary APIs for managing {@linkplain Role}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface RoleManager {
	/**
	 * Adds a {@linkplain Role} to this {@linkplain RoleManager}.
	 *
	 * @param role
	 *            the {@linkplain Role} to add.
	 */
	void addRole(Role role);

	/**
	 * Adds a set of {@linkplain Role}s to this {@linkplain RoleManager}.
	 *
	 * @param roles
	 *            the set of {@linkplain Role} to add.
	 */
	void addRoles(Collection<Role> roles);

	/**
	 * Adds a set of {@linkplain Role}s to this {@linkplain RoleManager}.
	 *
	 * @param roles
	 *            the set of {@linkplain Role} to add.
	 */
	void addRoles(Role... roles);

	/**
	 * Returns a {@linkplain Role} from this {@linkplain RoleManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role} to retrieve.
	 * @return the {@linkplain Role} if it exists, <code>null</code> otherwise.
	 */
	Role getRole(UniqueId id);

	/**
	 * Returns a set of {@linkplain Role}s from this {@linkplain RoleManager}.
	 *
	 * @return the set of {@linkplain Role}.
	 */
	Set<Role> getRoles();

	/**
	 * Removes a {@linkplain Role} from this {@linkplain RoleManager}.
	 *
	 * @param id
	 *            the {@linkplain UniqueId} that represents the {@linkplain Role} to remove.
	 */
	void removeRole(UniqueId id);

	/**
	 * Removes a set of {@linkplain Role}s from this {@linkplain RoleManager}.
	 *
	 * @param ids
	 *            the set of {@linkplain UniqueId}s that represents the {@linkplain Role}s to remove.
	 */
	void removeRoles(Collection<UniqueId> ids);

	/**
	 * Removes a set of {@linkplain Role}s from this {@linkplain RoleManager}.
	 *
	 * @param roleIds
	 *            the set of {@linkplain UniqueId}s that represents the {@linkplain Role}s to remove.
	 */
	void removeRoles(UniqueId... roleIds);

	/**
	 * Removes all {@linkplain Role}s from this {@linkplain RoleManager}.
	 */
	void removeAllRoles();
}
