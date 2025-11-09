package com.autoinventorytotem.handler;

import com.autoinventorytotem.ModConfigRegister;
import com.autoinventorytotem.AutoInventoryTotem;
import com.autoinventorytotem.config.ModConfig;
import net.minecraft.client.MinecraftClient;

public class TickHandler {

    private static int throttle = 0;
    private static final int TICK_DELAY = 3;

    public static void register() {
    }

    public static void onTick(MinecraftClient client) {
        if (client.player == null) return;
        throttle = (throttle + 1) % TICK_DELAY;
        if (throttle != 0) return;

        ModConfig cfg = ModConfigRegister.CONFIG;
        InventoryHandler.tick(client, cfg);
    }
}
