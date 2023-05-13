package net.projectile_damage.api;

import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.projectile_damage.internal.Constants;
import org.jetbrains.annotations.Nullable;

public record RangedWeaponKind(double damage, double launchVelocity, double adjustingMultiplier) {
    public static final RangedWeaponKind bow = new RangedWeaponKind(Constants.bowDefaultDamage, Constants.bowDefaultVelocity, 1);
    public static final RangedWeaponKind crossbow = new RangedWeaponKind(
            Constants.crossbowDefaultDamage,
            Constants.crossbowDefaultVelocity,
            1.15);

    /**
     * Create custom ranged weapon kind
     * @param damage - amount of damage dealt by a fully charged, non-critical strike, of the default item of this kind
     * @param launchVelocity - launch velocity of the projectile, that is typical to this kind
     * @return
     */
    public static RangedWeaponKind custom(double damage, double launchVelocity) {
        return new RangedWeaponKind(damage, launchVelocity, 1);
    }

    public static RangedWeaponKind custom(double damage, double launchVelocity, double adjustingMultiplier) {
        return new RangedWeaponKind(damage, launchVelocity, adjustingMultiplier);
    }

    @Nullable
    public static RangedWeaponKind from(Item item) {
        if (item instanceof BowItem) {
            return bow;
        }
        if (item instanceof CrossbowItem) {
            return crossbow;
        }
        return null;
    }
}
