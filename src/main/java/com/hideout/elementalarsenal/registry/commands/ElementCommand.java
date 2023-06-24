package com.hideout.elementalarsenal.registry.commands;

import com.hideout.elementalarsenal.item.custom.interfaces.ElementalItem;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
import com.hideout.elementalarsenal.util.ElementalType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class ElementCommand {
    public static int add(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        String type = context.getArgument("type", String.class);

        if (player.getMainHandStack().getItem() instanceof MultiElementItem item) {
            ElementalType selectedType = ElementalType.fromString(type);
            if (selectedType == null) throw new RuntimeException("No type with name: " + type);
            item.addType(player.getMainHandStack(), selectedType);

            return 1;
        }

        throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                () -> "You must be holding an Elemental Item");
    }
    public static int remove(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        String type = context.getArgument("type", String.class);
        ItemStack stack = player.getMainHandStack();

        if (stack.getItem() instanceof MultiElementItem item) {
            ElementalType selectedType = ElementalType.fromString(type);
            if (selectedType == null) throw new RuntimeException("No type with name: " + type);
            if (item.getAvailableTypes(stack).length > 0) {
                ArrayList<ElementalType> types = new ArrayList<>(Arrays.stream(item.getAvailableTypes(stack)).toList());
                types.remove(selectedType);
                stack.getOrCreateNbt().putIntArray(MultiElementItem.AVAILABLE_TYPES, types.stream().map(ElementalType::getId).toList());
                return 1;
            }

            return 0;
        }

        throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                () -> "You must be holding an Elemental Item");
    }
    public static int set(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        String type = context.getArgument("type", String.class);

        if (player.getMainHandStack().getItem() instanceof ElementalItem item) {
            ElementalType selectedType = ElementalType.fromString(type);
            if (selectedType == null) throw new RuntimeException("No type with name: " + type);
            item.setType(player.getMainHandStack(), selectedType);
            return 1;
        }

        throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                () -> "You must be holding an Elemental Item");
    }

    public static int list(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        ItemStack stack = player.getMainHandStack();

        if (stack.getItem() instanceof MultiElementItem item) {
            MutableText msg = Text.literal("");
            ElementalType[] types = item.getAvailableTypes(stack);

            for (int i = 0; i < types.length; i++) {
                ElementalType type = types[i];
                if (i == types.length - 1) {
                    msg.append(type.toFormattedText());
                } else {
                    msg.append(type.toFormattedText().append(", "));
                }
            }
            if (msg.getString().length() > 0)
                source.sendFeedback(() -> msg, false);
            return 1;
        }

        throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                () -> "You must be holding an Elemental Item");
    }
}
