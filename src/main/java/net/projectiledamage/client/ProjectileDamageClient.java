package net.projectiledamage.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ProjectileDamageClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TooltipHelper.initialize();
    }
}
