package net.projectiledamage.forge;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.projectiledamage.ProjectileDamage;
import net.projectiledamage.api.EntityAttributes_ProjectileDamage;
import net.projectiledamage.api.StatusEffects_ProjectileDamage;

@Mod(ProjectileDamage.MODID)
public class ProjectileDamageForge {
    private static final DeferredRegister<StatusEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ProjectileDamage.MODID);
    private static final RegistryObject<StatusEffect> impact = STATUS_EFFECTS.register(StatusEffects_ProjectileDamage.impactName, () -> StatusEffects_ProjectileDamage.IMPACT);

    public ProjectileDamageForge() {
        ProjectileDamage.init();
        STATUS_EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void registerAttributes(RegistryEvent.Register<EntityAttribute> event) {
        var asd = EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE; // :D
    }
}