package cc.ghast.janitor.v6.data;

import cc.ghast.janitor.v6.data.types.impl.*;

/**
 * @author Ghast
 * @since 17/07/2020
 * Ghast Holdings LLC / JIv6 © 2020
 */
public final class PlayerData {



    public final Combat combat = new Combat(this);
    public final Collision collision = new Collision(this);
    public final Connection connection = new Connection(this);
    public final Movement movement = new Movement(this);
    public final User user = new User(this);
    public final World world = new World(this);
}

