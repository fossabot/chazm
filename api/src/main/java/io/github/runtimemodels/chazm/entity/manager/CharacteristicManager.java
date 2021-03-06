package io.github.runtimemodels.chazm.entity.manager;

import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.id.UniqueId;

import java.util.Collection;
import java.util.Set;

/**
 * The @linkplain CharacteristicManager} interface defines the necessary APIs for managing {@linkplain Characteristic}s.
 *
 * @author Christopher Zhong
 * @since 7.0.0
 */
public interface CharacteristicManager {
    /**
     * Adds a {@linkplain Characteristic} to this {@linkplain CharacteristicManager}.
     *
     * @param characteristic the {@linkplain Characteristic} to add.
     */
    void addCharacteristic(Characteristic characteristic);

    /**
     * Adds a set of {@linkplain Characteristic}s to this {@linkplain CharacteristicManager}.
     *
     * @param characteristics the set of {@linkplain Characteristic} to add.
     */
    void addCharacteristics(Collection<Characteristic> characteristics);

    /**
     * Returns a {@linkplain Characteristic} from this {@linkplain CharacteristicManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Characteristic} to retrieve.
     * @return the {@linkplain Characteristic} if it exists, <code>null</code> otherwise.
     */
    Characteristic getCharacteristic(UniqueId<Characteristic> id);

    /**
     * Returns a set of {@linkplain Characteristic}s from this {@linkplain CharacteristicManager}.
     *
     * @return the set of {@linkplain Characteristic}s.
     */
    Set<Characteristic> getCharacteristics();

    /**
     * Removes a {@linkplain Characteristic} from this {@linkplain CharacteristicManager}.
     *
     * @param id the {@linkplain UniqueId} that represents the {@linkplain Characteristic} to remove.
     */
    void removeCharacteristic(UniqueId<Characteristic> id);

    /**
     * Removes a set of {@linkplain Characteristic}s from this {@linkplain CharacteristicManager}.
     *
     * @param ids the set of {@linkplain UniqueId}s that represents the {@linkplain Characteristic}s to remove.
     */
    void removeCharacteristics(Collection<UniqueId<Characteristic>> ids);

    /**
     * Removes all {@linkplain Characteristic}s from this {@linkplain CharacteristicManager}.
     */
    void removeAllCharacteristic();
}
