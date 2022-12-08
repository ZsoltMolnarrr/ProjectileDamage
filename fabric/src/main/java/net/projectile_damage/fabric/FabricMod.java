package net.projectile_damage.fabric;

import net.minecraft.util.registry.Registry;
import net.projectile_damage.ProjectileDamageMod;
import net.fabricmc.api.ModInitializer;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.StatusEffects_ProjectileDamage;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ProjectileDamageMod.init();
        ProjectileDamageMod.registerAttributes();
        ProjectileDamageMod.registerStatusEffects();
    }
}