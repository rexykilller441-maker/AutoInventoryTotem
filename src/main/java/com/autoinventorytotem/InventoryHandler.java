package com.autoinventorytotem.handler;

import com.autoinventorytotem.config.ModConfig;
import com.autoinventorytotem.AutoInventoryTotem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class InventoryHandler {

    public static void tick(MinecraftClient client, ModConfig config) {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        boolean monitorOffhand = config.monitorOffhand;
        int hotbarSlotUser = Math.max(1, Math.min(9, config.hotbarSlot));
        int hotbarIndex = hotbarSlotUser - 1;

        ItemStack offhand = player.getOffHandStack();
        ItemStack hotbarStack = player.getInventory().getStack(hotbarIndex);

        boolean offhandHasTotem = offhand.getItem() == Items.TOTEM_OF_UNDYING;
        boolean hotbarHasTotem = hotbarStack.getItem() == Items.TOTEM_OF_UNDYING;

        if ((!monitorOffhand || offhandHasTotem) && hotbarHasTotem) return;

        if (monitorOffhand && !offhandHasTotem && config.switchToSlotAfterPop) {
            player.getInventory().selectedSlot = hotbarIndex;
        }

        if (!config.autoRefill) return;

        client.execute(() -> {
            InventoryScreen invScreen = new InventoryScreen(player);
            client.setScreen(invScreen);

            int totemsNeeded = 0;
            if (monitorOffhand && player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) totemsNeeded++;
            if (player.getInventory().getStack(hotbarIndex).getItem() != Items.TOTEM_OF_UNDYING) totemsNeeded++;
            if (totemsNeeded == 0) {
                client.setScreen(null);
                return;
            }

            for (int i = 0; i < player.getInventory().size(); i++) {
                if (totemsNeeded <= 0) break;
                ItemStack stack = player.getInventory().getStack(i);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING && stack.getCount() > 0) {
                    if (monitorOffhand && player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                        ItemStack one = new ItemStack(Items.TOTEM_OF_UNDYING, 1);
                        stack.decrement(1);
                        if (stack.isEmpty()) player.getInventory().setStack(i, ItemStack.EMPTY);
                        player.getOffHandStack().increment(1);
                        player.getInventory().markDirty();
                        totemsNeeded--;
                    } else if (player.getInventory().getStack(hotbarIndex).getItem() != Items.TOTEM_OF_UNDYING) {
                        ItemStack one = new ItemStack(Items.TOTEM_OF_UNDYING, 1);
                        stack.decrement(1);
                        if (stack.isEmpty()) player.getInventory().setStack(i, ItemStack.EMPTY);
                        player.getInventory().setStack(hotbarIndex, one);
                        player.getInventory().markDirty();
                        totemsNeeded--;
                    }
                }
            }

            client.setScreen(null);
        });
    }
}
