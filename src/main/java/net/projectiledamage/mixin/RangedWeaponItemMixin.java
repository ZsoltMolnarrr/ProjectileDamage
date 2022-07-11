package net.projectiledamage.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.projectiledamage.api.AdditionalEntityAttributes;
import net.projectiledamage.api.IProjectileWeapon;
import net.projectiledamage.internal.Constants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RangedWeaponItem.class)
public abstract class RangedWeaponItemMixin extends Item implements IProjectileWeapon {
    private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers = null;

    public RangedWeaponItemMixin(Settings settings) {
        super(settings);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return (slot == EquipmentSlot.MAINHAND && this.attributeModifiers != null) ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    public void setProjectileDamage(double value) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(AdditionalEntityAttributes.GENERIC_PROJECTILE_DAMAGE, new EntityAttributeModifier(Constants.GENERIC_PROJECTILE_MODIFIER_ID, "Projectile damage", (double)value, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }
}
