package net.projectile_damage.internal;

import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;

public enum RangedWeapon {
    BOW, CROSSBOW;

    @Nullable
    public static RangedWeapon fromItem(Item item) {
        if (item == null) {
            return null;
        }
        if (item instanceof BowItem) {
            return RangedWeapon.BOW;
        }
        if (item instanceof CrossbowItem) {
            return RangedWeapon.CROSSBOW;
        }
        return null;
    }
}
