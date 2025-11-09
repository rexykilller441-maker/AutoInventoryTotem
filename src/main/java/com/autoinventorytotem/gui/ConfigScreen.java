package com.autoinventorytotem.gui;

import com.autoinventorytotem.ModConfigRegister;
import com.autoinventorytotem.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen {

    public static Screen createScreen(Screen parent) {
        ModConfig cfg = ModConfigRegister.CONFIG;
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("AutoInventoryTotem"));

        ConfigEntryBuilder eb = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));

        general.addEntry(eb.startIntField(Text.literal("Monitored hotbar slot (1-9)"), cfg.hotbarSlot)
                .setDefaultValue(6)
                .setSaveConsumer(v -> cfg.hotbarSlot = v)
                .setMin(1)
                .setMax(9)
                .build());

        general.addEntry(eb.startBooleanToggle(Text.literal("Monitor offhand"), cfg.monitorOffhand)
                .setDefaultValue(true)
                .setSaveConsumer(v -> cfg.monitorOffhand = v)
                .build());

        general.addEntry(eb.startBooleanToggle(Text.literal("Auto-refill totems after pop"), cfg.autoRefill)
                .setDefaultValue(true)
                .setSaveConsumer(v -> cfg.autoRefill = v)
                .build());

        general.addEntry(eb.startBooleanToggle(Text.literal("Switch to configured slot after offhand pop"), cfg.switchToSlotAfterPop)
                .setDefaultValue(true)
                .setSaveConsumer(v -> cfg.switchToSlotAfterPop = v)
                .build());

        builder.setSavingRunnable(() -> AutoConfig.getConfigHolder(ModConfig.class).save());
        return builder.build();
    }
}
