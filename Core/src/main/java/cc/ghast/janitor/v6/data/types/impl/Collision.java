package cc.ghast.janitor.v6.data.types.impl;

import cc.ghast.janitor.v6.data.PlayerData;
import cc.ghast.janitor.v6.data.types.AbstractType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ghast
 * @since 17/07/2020
 * Ghast Holdings LLC / JIv6 © 2020
 */

@Getter
@Setter
public class Collision extends AbstractType {
    public Collision(PlayerData data) {
        super(data);
    }

    public boolean collidesWater;

    public boolean collidesLava;
}
