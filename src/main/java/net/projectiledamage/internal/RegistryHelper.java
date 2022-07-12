package net.projectiledamage.internal;

import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.registry.Registry;
import net.projectiledamage.api.IProjectileWeapon;
import net.projectiledamage.config.ConfigManager;

public class RegistryHelper {
    public static void applyDefaultAttributes() {
        var config = ConfigManager.currentConfig;
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
