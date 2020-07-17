package cc.ghast.janitor.api;

import cc.ghast.janitor.v6.check.CheckInformation;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/**
 * @author Ghast
 * @since 17/07/2020
 * Ghast Holdings LLC / JIv6 © 2020
 */

@Getter
public class JanitorViolationEvent extends Event implements Cancellable {
    public JanitorViolationEvent(boolean isAsync, int count, Player player, CheckInformation check) {
        super(isAsync);
        this.player = player;
        this.count = count;
        this.check = check;
    }

    private boolean cancelled;
    private final Player player;
    private final int count;
    private final CheckInformation check;


    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
