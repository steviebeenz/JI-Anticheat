package cc.ghast.janitor.v6.data.types.impl;

import cc.ghast.janitor.v6.data.PlayerData;
import cc.ghast.janitor.v6.data.types.AbstractType;
import cc.ghast.janitor.v6.utils.lists.EvictingArrayList;
import cc.ghast.janitor.v6.utils.position.PlayerMovement;
import cc.ghast.janitor.v6.utils.position.PlayerPosition;
import cc.ghast.janitor.v6.utils.position.PlayerRotation;
import cc.ghast.janitor.v6.utils.position.Velocity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ghast
 * @since 17/07/2020
 * Ghast Holdings LLC / JIv6 © 2020
 */

@Getter
public class Movement extends AbstractType {
    public Movement(PlayerData data) {
        super(data);
    }

    public PlayerPosition from, to;
    public PlayerRotation was, now;
    public Velocity previousMotion, currentMotion;
    public final List<Velocity> motions = new EvictingArrayList<>(10);

    public boolean onGround;

    public double getHighestHorizontalMotion() {
        return motions.stream().mapToDouble(Velocity::getSquaredHorizontal).max().orElse(0.0);
    }

    public double getLowestHorizontalMotion() {
        return motions.stream().mapToDouble(Velocity::getSquaredHorizontal).min().orElse(0.0);
    }
}
