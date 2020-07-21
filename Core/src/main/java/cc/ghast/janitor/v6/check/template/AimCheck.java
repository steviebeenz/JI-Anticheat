package cc.ghast.janitor.v6.check.template;

import cc.ghast.janitor.v6.check.Check;
import cc.ghast.janitor.v6.check.CheckInformation;
import cc.ghast.janitor.v6.data.PlayerData;
import cc.ghast.janitor.v6.utils.position.PlayerPosition;
import cc.ghast.janitor.v6.utils.position.PlayerRotation;
import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.packetwrapper.WrapperPlayClientLook;
import com.comphenix.packetwrapper.WrapperPlayClientPosition;
import com.comphenix.packetwrapper.WrapperPlayClientPositionLook;

public abstract class AimCheck extends Check {
    public AimCheck(CheckInformation info, PlayerData data) {
        super(info, data);
    }

    @Override
    public void handle(AbstractPacket packet) {
        if (packet instanceof WrapperPlayClientLook
            || packet instanceof WrapperPlayClientPositionLook) {
            this.handle(data.movement.was, data.movement.now);
        }
    }

    public abstract void handle(PlayerRotation from, PlayerRotation to);
}
