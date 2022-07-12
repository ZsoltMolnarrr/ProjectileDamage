package net.projectiledamage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.projectiledamage.api.AdditionalEntityAttributes;
import net.projectiledamage.api.IProjectileWeapon;
import net.projectiledamage.config.ConfigManager;

public class ProjectileDamage implements ModInitializer {
    public static String MODID = "projectiledamage";

    @Override
    public void onInitialize() {
        ConfigManager.initialize();
        var ref = AdditionalEntityAttributes.GENERIC_PROJECTILE_DAMAGE;
        var config = ConfigManager.currentConfig;
        ((IProjectileWeapon)Items.BOW).setProjectileDamage(config.default_bow_damage);
        ((IProjectileWeapon)Items.CROSSBOW).setProjectileDamage(config.default_crowssbow_damage);
    }
}
