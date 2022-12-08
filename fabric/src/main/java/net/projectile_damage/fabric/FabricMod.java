package net.projectile_damage.fabric;

import net.fabricmc.api.ModInitializer;
import net.projectile_damage.ProjectileDamageMod;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ProjectileDamageMod.init();
        ProjectileDamageMod.registerAttributes();
        ProjectileDamageMod.registerStatusEffects();
    }
}