package net.projectiledamage.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.projectiledamage.client.TooltipHelper;

@Environment(EnvType.CLIENT)
public class ProjectileDamageClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((itemStack, context, lines) -> {
            TooltipHelper.updateTooltipText(itemStack, lines);
        });
    }
}
