package com.hideout.elementalarsenal.registry.commands;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.util.ElementalType;
import com.hideout.elementalarsenal.util.ElementalUtils;
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
        ElementalUtils.setAvailableTypes(stack, ElementalType.values());
        sender.giveItemStack(stack);
        return 1;
    }

    public static int allGems(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        final ServerCommandSource source = context.getSource();
        final ServerPlayerEntity sender = source.getPlayerOrThrow();
        for (ElementalType type : ElementalType.values()) {
            ItemStack stack = new ItemStack(ModItems.ELEMENTAL_GEM);
            ElementalUtils.setType(stack, type);
            sender.giveItemStack(stack);
        }

        return 1;
    }

}
