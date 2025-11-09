package com.autoinventorytotem.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "autoinventorytotem")
public class ModConfig implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 1, max = 9)
    @ConfigEntry.Gui.Tooltip
    public int hotbarSlot = 6;

    @ConfigEntry.Gui.Tooltip
    public boolean monitorOffhand = true;

    @ConfigEntry.Gui.Tooltip
    public boolean autoRefill = true;

    @ConfigEntry.Gui.Tooltip
    public boolean switchToSlotAfterPop = true;
}