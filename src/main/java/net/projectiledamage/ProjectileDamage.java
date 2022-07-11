package net.projectiledamage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.projectiledamage.api.AdditionalEntityAttributes;
import net.projectiledamage.api.IProjectileWeapon;
import net.projectiledamage.internal.Constants;

public class ProjectileDamage implements ModInitializer {
    public static String MODID = "projectiledamage";

    @Override
    public void onInitialize() {
        var ref = AdditionalEntityAttributes.GENERIC_PROJECTILE_DAMAGE;
        ((IProjectileWeapon)Items.BOW).setProjectileDamage(Constants.bowDefaultDamage);
        ((IProjectileWeapon)Items.CROSSBOW).setProjectileDamage(Constants.crossbowDefaultDamage);
    }
}
