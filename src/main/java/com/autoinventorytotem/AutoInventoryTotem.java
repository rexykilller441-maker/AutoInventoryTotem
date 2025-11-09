package com.autoinventorytotem;

import com.autoinventorytotem.config.ModConfig;
import com.autoinventorytotem.handler.InventoryHandler;
import com.autoinventorytotem.handler.TickHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoInventoryTotem implements ClientModInitializer {
    public static final String MODID = "autoinventorytotem";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        InventoryHandler.init();
        TickHandler.register();
        LOGGER.info("AutoInventoryTotem initialized");
    }
}