package com.autoinventorytotem.gui;

import com.autoinventorytotem.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreen {

    public static Screen createScreen(Screen parent) {
        ModConfig cfg = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("AutoInventoryTotem Configuration"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));

        general.addEntry(entryBuilder.startIntField(Text.literal("Monitored hotbar slot (1-9)"), cfg.hotbarSlot)
                .setDefaultValue(6)
                .setSaveConsumer(newVal -> cfg.hotbarSlot = newVal)
                .setMin(1)
                .setMax(9)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Monitor offhand"), cfg.monitorOffhand)
                .setDefaultValue(true)
                .setSaveConsumer(newVal -> cfg.monitorOffhand = newVal)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Auto-refill totems after pop"), cfg.autoRefill)
                .setDefaultValue(true)
                .setSaveConsumer(newVal -> cfg.autoRefill = newVal)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Switch to configured slot after offhand pop"), cfg.switchToSlotAfterPop)
                .setDefaultValue(true)
                .setSaveConsumer(newVal -> cfg.switchToSlotAfterPop = newVal)
                .build());

        builder.setSavingRunnable(() -> AutoConfig.getConfigHolder(ModConfig.class).save());
        return builder.build();
    }
}