package cc.ghast.janitor;

import org.bukkit.plugin.java.JavaPlugin;

public enum Janitor {
    INSTANCE;

    private JavaPlugin plugin;


    public void start(JavaPlugin plugin) {
        if (plugin != null) disinit();
        this.plugin = plugin;
        this.init();
    }

    public void stop() {
        this.disinit();
        this.plugin = null;
    }

    private void init() {

    }

    private void disinit() {

    }
}
