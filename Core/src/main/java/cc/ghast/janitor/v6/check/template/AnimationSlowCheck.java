package cc.ghast.janitor.v6.check.template;

import cc.ghast.janitor.v6.check.Check;
import cc.ghast.janitor.v6.check.CheckInformation;
import cc.ghast.janitor.v6.data.PlayerData;
import cc.ghast.janitor.v6.utils.lists.EvictingArrayList;
import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.packetwrapper.WrapperPlayClientArmAnimation;
import com.comphenix.packetwrapper.WrapperPlayClientFlying;

import java.util.List;

public abstract class AnimationSlowCheck extends Check {
    public AnimationSlowCheck(CheckInformation info, PlayerData data, int vars) {
        super(info, data);
        this.values = new EvictingArrayList<>(vars);
    }

    public final List<Integer> values;
    public int ticks, arm;

    @Override
    public void handle(AbstractPacket packet) {
        if (packet instanceof WrapperPlayClientFlying) {
            this.ticks++;
        } else if (packet instanceof WrapperPlayClientArmAnimation) {
            this.arm++;

            if (ticks >= 20) {
                this.values.add(arm);
                this.handle(values);
                this.arm = this.ticks = 0;
            }
        }
    }

    public abstract void handle(List<Integer> values);
}
