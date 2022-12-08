package net.projectile_damage.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.projectile_damage.ProjectileDamageMod;
import net.projectile_damage.client.TooltipHelper;

@Mod.EventBusSubscriber(modid = ProjectileDamageMod.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onToolTip(ItemTooltipEvent event) {
        TooltipHelper.updateTooltipText(event.getItemStack(), event.getToolTip());
    }
}
