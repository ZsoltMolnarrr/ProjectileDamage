package net.projectile_damage.internal;

import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.projectile_damage.ProjectileDamageMod;
import net.projectile_damage.api.IProjectileWeapon;

public class RegistryHelper {
    public static void applyDefaultAttributes() {
        var config = ProjectileDamageMod.configManager.value;

        ((IProjectileWeapon) Items.BOW).setProjectileDamage(config.default_bow_damage);
        ((IProjectileWeapon)Items.CROSSBOW).setProjectileDamage(config.default_crossbow_damage);

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
