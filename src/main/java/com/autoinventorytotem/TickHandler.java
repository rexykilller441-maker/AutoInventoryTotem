package com.autoinventorytotem.handler;

import com.autoinventorytotem.AutoInventoryTotem;
import com.autoinventorytotem.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class TickHandler {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            try {
                onTick(client);
            } catch (Throwable t) {
                AutoInventoryTotem.LOGGER.error("Error in TickHandler", t);
            }
        });
    }

    private static void onTick(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        InventoryHandler.tick(client, config);
    }
}