package net.projectiledamage.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.projectiledamage.api.AdditionalEntityAttributes;
import net.projectiledamage.internal.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BowItem.class)
public class BowItemMixin {
    @Redirect(method = "onStoppedUsing",at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"))
    private void redirectStuff(PersistentProjectileEntity instance, Entity entity, float pitch, float yaw, float roll, float speed, float divergence) {
        instance.setVelocity(entity, pitch, yaw, roll, speed, divergence);
        if(entity instanceof LivingEntity) {
            var livingEntity = (LivingEntity)entity;
            var projectileDamage= livingEntity.getAttributeValue(AdditionalEntityAttributes.GENERIC_PROJECTILE_DAMAGE);
            if (projectileDamage > 0) {
                // System.out.println("Arrow default damage: " + instance.getDamage());
                instance.setDamage((projectileDamage / Constants.bowDefaultDamage) * instance.getDamage());
            }
        }
    }
}
