package net.projectiledamage;

import net.projectiledamage.config.Config;
import net.projectiledamage.config.Default;
import net.tinyconfig.ConfigManager;

public class ProjectileDamage {
    public static final String MODID = "projectiledamage";

    public static ConfigManager<Config> configManager = new ConfigManager<Config>
            (MODID, Default.config)
            .builder()
            .sanitize(true)
            .build();

    public static void init() {
        configManager.refresh();
    }
}
