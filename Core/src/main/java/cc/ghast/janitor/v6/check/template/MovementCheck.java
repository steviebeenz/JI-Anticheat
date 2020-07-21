package cc.ghast.janitor.v6.check.template;

import cc.ghast.janitor.v6.check.Check;
import cc.ghast.janitor.v6.check.CheckInformation;
import cc.ghast.janitor.v6.data.PlayerData;
import cc.ghast.janitor.v6.utils.position.PlayerPosition;
import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.packetwrapper.WrapperPlayClientPosition;
import com.comphenix.packetwrapper.WrapperPlayClientPositionLook;

public abstract class MovementCheck extends Check {
    public MovementCheck(CheckInformation info, PlayerData data) {
        super(info, data);
    }

    @Override
    public void handle(AbstractPacket packet) {
        if (packet instanceof WrapperPlayClientPosition
            || packet instanceof WrapperPlayClientPositionLook) {
            this.handle(data.movement.from, data.movement.to);
        }
    }

    public abstract void handle(PlayerPosition from, PlayerPosition to);
}
