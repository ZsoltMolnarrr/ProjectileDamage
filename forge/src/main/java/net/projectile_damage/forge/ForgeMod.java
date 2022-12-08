package net.projectile_damage.forge;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.projectile_damage.ProjectileDamageMod;
import net.projectile_damage.api.EntityAttributes_ProjectileDamage;
import net.projectile_damage.api.StatusEffects_ProjectileDamage;

@Mod(ProjectileDamageMod.ID)
public class ForgeMod {
    public ForgeMod() {
        // Submit our event bus to let architectury register our content on the right time
        // EventBuses.registerModEventBus(ProjectileDamage.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ProjectileDamageMod.init();
    }
    @SubscribeEvent
    public void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.ATTRIBUTES,
                helper -> {
                    var asd = EntityAttributes_ProjectileDamage.GENERIC_PROJECTILE_DAMAGE;
                }
        );
        event.register(ForgeRegistries.Keys.MOB_EFFECTS,
                helper -> {
                    helper.register(
                            StatusEffects_ProjectileDamage.impactId,
                            StatusEffects_ProjectileDamage.IMPACT);
                }
        );
    }
}