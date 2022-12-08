package net.projectile_damage;

import net.projectile_damage.config.Config;
import net.projectile_damage.config.Default;
import net.tinyconfig.ConfigManager;

public class ProjectileDamageMod {
    public static final String ID = "projectile_damage";

    public static ConfigManager<Config> configManager = new ConfigManager<Config>
            (ID, Default.config)
            .builder()
            .sanitize(true)
            .build();

    public static void init() {
        configManager.refresh();
    }
}
