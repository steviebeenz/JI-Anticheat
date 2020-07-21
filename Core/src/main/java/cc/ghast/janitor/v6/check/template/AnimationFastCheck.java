package cc.ghast.janitor.v6.check.template;

import cc.ghast.janitor.v6.check.Check;
import cc.ghast.janitor.v6.check.CheckInformation;
import cc.ghast.janitor.v6.data.PlayerData;
import cc.ghast.janitor.v6.utils.lists.EvictingArrayList;
import cc.ghast.janitor.v6.utils.position.PlayerPosition;
import com.comphenix.packetwrapper.*;

import java.util.List;

public abstract class AnimationFastCheck extends Check {
    public AnimationFastCheck(CheckInformation info, PlayerData data, int vars) {
        super(info, data);
        this.values = new EvictingArrayList<>(vars);
    }

    public final List<Integer> values;
    public int ticks;

    @Override
    public void handle(AbstractPacket packet) {
        if (packet instanceof WrapperPlayClientFlying) {
            this.ticks++;
        } else if (packet instanceof WrapperPlayClientArmAnimation) {
            this.values.add(ticks);
            this.ticks = 0;
            this.handle(values);
        }
    }

    public abstract void handle(List<Integer> values);
}
