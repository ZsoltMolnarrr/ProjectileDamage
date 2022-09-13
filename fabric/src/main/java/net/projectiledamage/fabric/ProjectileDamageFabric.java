package net.projectiledamage.fabric;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.projectiledamage.ProjectileDamage;
import net.fabricmc.api.ModInitializer;
import net.projectiledamage.api.EntityAttributes_ProjectileDamage;
import net.projectiledamage.api.StatusEffects_ProjectileDamage;

public class ProjectileDamageFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ProjectileDamage.init();
        var ref = EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE;
        Registry.register(Registry.STATUS_EFFECT,
                new Identifier(ProjectileDamage.MODID, StatusEffects_ProjectileDamage.impactId),
                StatusEffects_ProjectileDamage.IMPACT);
    }
}