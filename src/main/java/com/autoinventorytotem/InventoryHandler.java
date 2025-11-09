package com.autoinventorytotem.handler;

import com.autoinventorytotem.AutoInventoryTotem;
import com.autoinventorytotem.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class InventoryHandler {

    private static final int TICKS_BETWEEN_ACTIONS = 2;
    private static int tickCounter = 0;

    public static void init() {}

    public static void tick(MinecraftClient client, ModConfig config) {
        tickCounter = (tickCounter + 1) % Math.max(1, TICKS_BETWEEN_ACTIONS);
        if (tickCounter != 0) return;

        ClientPlayerEntity player = client.player;
        if (player == null) return;

        boolean monitorOffhand = config.monitorOffhand;
        int hotbarIndexZeroBased = Math.max(1, Math.min(9, config.hotbarSlot)) - 1;

        ItemStack offhand = player.getOffHandStack();
        ItemStack hotbarStack = player.getInventory().getStack(hotbarIndexZeroBased);

        boolean offhandHasTotem = offhand.getItem() == Items.TOTEM_OF_UNDYING;
        boolean hotbarHasTotem = hotbarStack.getItem() == Items.TOTEM_OF_UNDYING;

        if ((!monitorOffhand || offhandHasTotem) && hotbarHasTotem) return;

        if (monitorOffhand && !offhandHasTotem && config.switchToSlotAfterPop) {
            player.getInventory().selectedSlot = hotbarIndexZeroBased;
        }

        if (!config.autoRefill) return;

        try {
            openInventoryAndRefill(client, player, hotbarIndexZeroBased, monitorOffhand);
        } catch (Exception e) {
            AutoInventoryTotem.LOGGER.error("Refill failed", e);
        }
    }

    private static void openInventoryAndRefill(MinecraftClient client, ClientPlayerEntity player, int hotbarIndex, boolean monitorOffhand) {
        client.execute(() -> {
            client.setScreen(new InventoryScreen(player));

            int totemsNeeded = 0;
            if (monitorOffhand && player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) totemsNeeded++;
            if (player.getInventory().getStack(hotbarIndex).getItem() != Items.TOTEM_OF_UNDYING) totemsNeeded++;
            if (totemsNeeded == 0) {
                client.setScreen(null);
                return;
            }

            for (int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    if (monitorOffhand && player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                        ItemStack one = stack.copy();
                        one.setCount(1);
                        stack.decrement(1);
                        player.getInventory().offHand.set(0, one);
                    } else if (player.getInventory().getStack(hotbarIndex).getItem() != Items.TOTEM_OF_UNDYING) {
                        ItemStack one = stack.copy();
                        one.setCount(1);
                        stack.decrement(1);
                        player.getInventory().setStack(hotbarIndex, one);
                    }
                    if (--totemsNeeded <= 0) break;
                }
            }

            client.setScreen(null);
        });
    }
}