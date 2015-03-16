package model.organization.event;

import model.organization.event.Publisher.Type;
import model.organization.id.UniqueId;

/**
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface Mediator {
	/**
	 * @param publisher
	 * @param type
	 * @param clazz
	 * @param id
	 */
	<T> void publish(Publisher publisher, Type type, Class<T> clazz, UniqueId<? extends T> id);

	/**
	 * @param publisher
	 * @param type
	 * @param clazz
	 * @param ids
	 */
	<T> void publish(Publisher publisher, Type type, Class<T> clazz, UniqueId<?>... ids);
}