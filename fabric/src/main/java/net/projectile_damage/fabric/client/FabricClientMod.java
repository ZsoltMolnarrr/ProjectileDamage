package net.projectile_damage.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.projectile_damage.client.TooltipHelper;

@Environment(EnvType.CLIENT)
public class FabricClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, context, lines) -> {
            TooltipHelper.updateTooltipText(itemStack, lines);
        });
    }
}
