package com.autoinventorytotem.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "autoinventorytotem")
public class ModConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 1, max = 9)
    public int hotbarSlot = 6;

    public boolean monitorOffhand = true;
    public boolean autoRefill = true;
    public boolean switchToSlotAfterPop = true;
}
