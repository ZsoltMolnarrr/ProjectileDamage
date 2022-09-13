package net.projectiledamage.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.projectiledamage.api.EntityAttributes_ProjectileDamage;
import net.projectiledamage.api.IProjectileWeapon;
import net.projectiledamage.internal.Constants;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

@Mixin(RangedWeaponItem.class)
abstract class RangedWeaponItemMixin extends Item implements IProjectileWeapon {
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers = null;
    private List<EquipmentSlot> allowedSlots = List.of(EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
    private Double projectileVelocity = null;

    // Helper, not actual source of truth
    private double projectileDamage = 0;

    RangedWeaponItemMixin(Settings settings) {
        super(settings);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return (allowedSlots.contains(slot) && this.attributeModifiers != null) ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    public void setProjectileDamage(double value, boolean mainHand, boolean offHand) {
        allowedSlots = new ArrayList<>();
        if (mainHand) {
            allowedSlots.add(EquipmentSlot.MAINHAND);
        }
        if (offHand) {
            allowedSlots.add(EquipmentSlot.OFFHAND);
        }
        projectileDamage = value;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE, new EntityAttributeModifier(Constants.GENERIC_PROJECTILE_MODIFIER_ID, "Projectile damage", (double)value, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public double getProjectileDamage() {
        return projectileDamage;
    }

    @Override
    public void setMaxProjectileVelocity(Double value) {
        projectileVelocity = value;
    }

    @Override
    public Double getMaxProjectileVelocity() {
        return projectileVelocity;
    }
}
