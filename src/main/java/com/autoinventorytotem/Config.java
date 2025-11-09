package com.autoinventorytotem;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "autoinventorytotem")
public class Config implements ConfigData {

    @ConfigEntry.BoundedDiscrete(min = 1, max = 9)
    public int selectedSlot = 6;

    public boolean monitorOffhand = true;
    public boolean autoRefill = true;
    public boolean switchToSlotAfterPop = true;
}
