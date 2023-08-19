package com.hideout.elementalarsenal.registry.commands;

import com.hideout.elementalarsenal.item.custom.interfaces.ElementalItem;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
import com.hideout.elementalarsenal.util.ElementalType;
import com.hideout.elementalarsenal.util.ElementalUtils;
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
            if (selectedType == null) throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                    () -> "That type does not exist");
            ElementalUtils.addType(player.getMainHandStack(), selectedType);

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
            if (selectedType == null) throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                    () -> "That type does not exist");

            if (ElementalUtils.getAvailableTypes(stack).length - 1 > 0) {
                ArrayList<ElementalType> types = new ArrayList<>(Arrays.stream(ElementalUtils.getAvailableTypes(stack)).toList());
                if (!types.contains(selectedType)) throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                        () -> "This item does not contain that type.");

                ElementalUtils.setType(stack, ElementalUtils.getAvailableTypes(stack)[ElementalUtils.getIndexOfType(stack, selectedType) - 1]);
                types.remove(selectedType);
                ElementalUtils.setAvailableTypes(stack, types);
                return 1;
            } else {
                throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                        () -> "An elemental item must have one type");
            }
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
            if (selectedType == null) throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                    () -> "That type does not exist");
            ElementalUtils.setType(player.getMainHandStack(), selectedType);
            if (item instanceof MultiElementItem multiElementItem) {
                ElementalUtils.updateTypes(player.getMainHandStack());
            }
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
            ElementalType[] types = ElementalUtils.getAvailableTypes(stack);

            for (int i = 0; i < types.length; i++) {
                ElementalType type = types[i];
                if (i == types.length - 1) {
                    msg.append(type.toFormattedText());
                } else {
                    msg.append(type.toFormattedText().append(", "));
                }
            }

            if (!msg.getString().isEmpty())
                source.sendFeedback(() -> msg, false);

            return 1;
        }

        throw new CommandSyntaxException(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument(),
                () -> "You must be holding an Elemental Item");
    }
}
