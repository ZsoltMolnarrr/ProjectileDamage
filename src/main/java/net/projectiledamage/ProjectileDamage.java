package net.projectiledamage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.projectiledamage.api.AdditionalEntityAttributes;
import net.projectiledamage.api.IProjectileWeapon;
import net.projectiledamage.config.Config;
import net.projectiledamage.config.Default;
import net.tinyconfig.ConfigManager;

public class ProjectileDamage implements ModInitializer {
    public static String MODID = "projectiledamage";

    public static ConfigManager<Config> configManager = new ConfigManager<Config>
            (MODID, Default.config)
            .builder()
            .sanitize(true)
            .build();


    @Override
    public void onInitialize() {
        configManager.refresh();
        var ref = AdditionalEntityAttributes.GENERIC_PROJECTILE_DAMAGE;
        var config = configManager.currentConfig;
        ((IProjectileWeapon)Items.BOW).setProjectileDamage(config.default_bow_damage);
        ((IProjectileWeapon)Items.CROSSBOW).setProjectileDamage(config.default_crowssbow_damage);
    }
}
