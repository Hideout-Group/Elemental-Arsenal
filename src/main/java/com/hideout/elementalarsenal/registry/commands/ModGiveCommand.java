package com.hideout.elementalarsenal.registry.commands;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.item.custom.interfaces.IElementalItem;
import com.hideout.elementalarsenal.item.custom.interfaces.IMultiElementItem;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class ModGiveCommand {
    public static int maxedSword(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        final ServerCommandSource source = context.getSource();
        final ServerPlayerEntity sender = source.getPlayerOrThrow();
        ItemStack stack = new ItemStack(ModItems.ELEMENTAL_SWORD);
        stack.getOrCreateNbt().putIntArray(IMultiElementItem.AVAILABLE_TYPES,
                new int[] {0, 1, 2, 3, 4, 5, 6, 7});
        sender.giveItemStack(stack);
        return 1;
    }

    public static int allGems(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        final ServerCommandSource source = context.getSource();
        final ServerPlayerEntity sender = source.getPlayerOrThrow();
        for (int i = 0; i < 8; i++) {
            ItemStack stack = new ItemStack(ModItems.ELEMENTAL_GEM);
            stack.getOrCreateNbt().putInt(IElementalItem.TYPE, i);
            sender.giveItemStack(stack);
        }

        return 1;
    }

}
