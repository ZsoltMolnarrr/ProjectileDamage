package net.projectile_damage;

import net.minecraft.util.registry.Registry;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.StatusEffects_ProjectileDamage;
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

    public static void registerAttributes() {
        var ref = EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE;
    }

    public static void registerStatusEffects() {
        Registry.register(Registry.STATUS_EFFECT,
                StatusEffects_ProjectileDamage.impactId,
                StatusEffects_ProjectileDamage.IMPACT);
    }
}
