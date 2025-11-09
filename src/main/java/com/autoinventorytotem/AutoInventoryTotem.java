package com.autoinventorytotem;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class AutoInventoryTotem implements ClientModInitializer {
    public static final String MOD_ID = "autoinventorytotem";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ModConfigRegister.register();
        TickHandler.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            try {
                if (client.player != null && client.world != null) {
                    TickHandler.onTick(client);
                }
            } catch (Throwable t) {
                LOGGER.error("Error during client tick", t);
            }
        });

        LOGGER.info("AutoInventoryTotem initialized");
    }

    public static MinecraftClient getClient() {
        return MinecraftClient.getInstance();
    }
}
