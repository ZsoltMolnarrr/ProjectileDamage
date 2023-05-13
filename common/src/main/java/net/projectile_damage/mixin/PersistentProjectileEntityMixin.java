package net.projectile_damage.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin {
    private static final Random CRIT_RANDOM = new Random();
    @Shadow private double damage;
    @Shadow public abstract boolean isCritical();

    @ModifyVariable(method = "onEntityHit", at = @At("STORE"), ordinal = 0)
    private int modifyCritDamage(int value) {
        if (!isCritical()) { return value; }
        var projectile = (PersistentProjectileEntity) ((Object) this);
        var velocity = projectile.getVelocity().length();
        var critMultiplier = 1F + (0.05F + CRIT_RANDOM.nextFloat() * 0.45F);
        // System.out.println("Critical strike! Damage: " + (velocity * this.damage) + " critMultiplier: " + critMultiplier);
        return (int) Math.round(MathHelper.clamp(velocity * this.damage * critMultiplier, 0.0, 2.147483647E9));
    }
}
