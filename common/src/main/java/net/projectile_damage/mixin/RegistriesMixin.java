package net.projectile_damage.mixin;

import net.minecraft.registry.Registries;
import net.projectile_damage.Platform;
import net.projectile_damage.ProjectileDamageMod;
import net.projectile_damage.internal.RegistryHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Registries.class)
public class RegistriesMixin {
    @Inject(method = "freezeRegistries", at = @At("HEAD"))
    private static void freezeRegistries_HEAD_ProjectileDamage(CallbackInfo ci) {
        RegistryHelper.applyDefaultAttributes();
        if (Platform.Forge) {
            ProjectileDamageMod.registerStatusEffects();
        }
    }
}
