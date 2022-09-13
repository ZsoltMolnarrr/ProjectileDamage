package net.projectiledamage.forge;

import net.minecraft.util.Identifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.projectiledamage.ProjectileDamage;
import net.projectiledamage.api.EntityAttributes_ProjectileDamage;
import net.projectiledamage.api.StatusEffects_ProjectileDamage;

@Mod(ProjectileDamage.MODID)
public class ProjectileDamageForge {
    public ProjectileDamageForge() {
        // Submit our event bus to let architectury register our content on the right time
        // EventBuses.registerModEventBus(ProjectileDamage.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ProjectileDamage.init();
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
                    helper.register(new Identifier(ProjectileDamage.MODID, StatusEffects_ProjectileDamage.impactId),
                            StatusEffects_ProjectileDamage.IMPACT);
                }
        );
    }
}