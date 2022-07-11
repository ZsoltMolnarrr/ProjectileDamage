package net.projectiledamage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
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
        for(var entry: Registry.ITEM.getEntrySet()) {
            var item = entry.getValue();
            if (item instanceof IProjectileWeapon) {
                var rangedWeapon = ((IProjectileWeapon) item);
                if (rangedWeapon.getProjectileDamage() == 0) {
                    if (config.add_default_attributes_for_unspecified_bows) {
                        if (item instanceof BowItem) {
                            rangedWeapon.setProjectileDamage(config.default_bow_damage());
                        }
                    }
                    if (config.add_default_attributes_for_unspecified_crossbows) {
                        if (item instanceof CrossbowItem) {
                            rangedWeapon.setProjectileDamage(config.default_crowssbow_damage());
                        }
                    }
                }
            }
        }
    }
}
