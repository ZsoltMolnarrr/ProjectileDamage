package net.projectile_damage.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.IProjectileWeapon;
import net.projectile_damage.api.RangedWeaponKind;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
                var weaponKind = getWeaponKind(owner.getMainHandStack());
                if (weaponKind != null) {
                    // Firing from main-hand stack
                    usedStack = owner.getMainHandStack();
                    usedHand = Hand.MAIN_HAND;
                } else {
                    weaponKind = getWeaponKind(owner.getOffHandStack());
                    if (weaponKind != null) {
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
                // System.out.println("Firing hand: " + usedHand + ", weapon:" + weaponKind + ", damage: " + projectileDamage);
                // System.out.println("Shooting projectile, initial velocity: " + projectile.getVelocity().length());

                if (weaponKind != null && projectileDamage > 0) {
                    var defaultDamage = weaponKind.damage();
                    var defaultVelocity = weaponKind.launchVelocity();
                    // var currentVelocity = projectile.getVelocity().length();
                    var velocityMultiplier = 1.0;
                    if (usedStack != null && usedStack.getItem() instanceof IProjectileWeapon projectileWeapon) {
                        var customLaunchVelocity = projectileWeapon.getCustomLaunchVelocity();
                        if (customLaunchVelocity != null) {
                            velocityMultiplier = defaultVelocity / customLaunchVelocity;
                        }
                    }
                    // System.out.println("Launching modified arrow, damage multiplier: " + (projectileDamage / defaultDamage) + " velocityMultiplier: " + velocityMultiplier);
                    projectile.setDamage(projectile.getDamage()
                            * (projectileDamage / defaultDamage)
                            * velocityMultiplier
                            * weaponKind.adjustingMultiplier());
                    // System.out.println(" - Damage: " + projectile.getDamage());
                }
            }
        }
    }

    @Nullable
    private RangedWeaponKind getWeaponKind(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        }
        var item = itemStack.getItem();
        if (item == null) {
            return null;
        }
        if (item instanceof IProjectileWeapon weapon) {
            return weapon.getRangeWeaponKind();
        }
        return null;
    }
}
