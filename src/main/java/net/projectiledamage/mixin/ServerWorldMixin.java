package net.projectiledamage.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.projectiledamage.api.EntityAttributes_ProjectileDamage;
import net.projectiledamage.internal.Constants;
import net.projectiledamage.internal.RangedWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ServerWorld.class)
class ServerWorldMixin {
    @Inject(method = "spawnEntity", at = @At("HEAD"))
    private void pre_spawnEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof PersistentProjectileEntity) {
            var projectile = (PersistentProjectileEntity)entity;
            if (projectile.getOwner() != null && projectile.getOwner() instanceof LivingEntity) {
                var owner = (LivingEntity)projectile.getOwner();

                Hand usedHand = null;
                ItemStack usedStack = null;
                RangedWeapon usedWeapon = getWeaponType(owner.getMainHandStack());
                if (usedWeapon != null) {
                    // Firing from main-hand stack
                    usedStack = owner.getMainHandStack();
                    usedHand = Hand.MAIN_HAND;
                } else {
                    usedWeapon = getWeaponType(owner.getOffHandStack());
                    if (usedWeapon != null) {
                        // Firing from off-hand stack
                        usedStack = owner.getOffHandStack();
                        usedHand = Hand.OFF_HAND;
                    }
                }

                if (usedStack != null) {
                    // There is an issue inside Minecraft's attribute caching, we need to add the used attribute again.
                    // (The same attribute instance will never count twice, hence this solution is okay)
                    owner.getAttributes().addTemporaryModifiers(usedStack.getAttributeModifiers(
                            usedHand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND
                    ));
                }
                var projectileDamage = owner.getAttributeValue(EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE);
                // System.out.println("Firing hand: " + usedHand + ", weapon:" + usedWeapon + ", damage: " + projectileDamage);

                if (usedWeapon != null && projectileDamage > 0) {
                    var defaultDamage = 1.0;
                    switch (usedWeapon) {
                        case BOW -> {
                            defaultDamage = Constants.bowDefaultDamage;
                        }
                        case CROSSBOW -> {
                            defaultDamage = Constants.crossbowDefaultDamage;
                        }
                    }
                    projectile.setDamage((projectileDamage / defaultDamage) * projectile.getDamage());
                }
            }
        }
    }

    @Nullable
    private RangedWeapon getWeaponType(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        var item = itemStack.getItem();
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
