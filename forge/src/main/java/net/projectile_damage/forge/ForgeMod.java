package net.projectile_damage.forge;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.projectile_damage.ProjectileDamageMod;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.StatusEffects_ProjectileDamage;

@Mod(ProjectileDamageMod.ID)
public class ForgeMod {
//    private static final DeferredRegister<StatusEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ProjectileDamageMod.ID);
//    private static final RegistryObject<StatusEffect> impact = STATUS_EFFECTS.register(StatusEffects_ProjectileDamage.impactName, () -> StatusEffects_ProjectileDamage.IMPACT);

    public ForgeMod() {
        ProjectileDamageMod.init();
//        STATUS_EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void registerAttributes(RegistryEvent.Register<EntityAttribute> event) {
        ProjectileDamageMod.registerAttributes();
    }
}