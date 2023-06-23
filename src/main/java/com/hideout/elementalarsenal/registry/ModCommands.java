package com.hideout.elementalarsenal.registry;

import com.hideout.elementalarsenal.item.ModItems;
import com.hideout.elementalarsenal.item.custom.interfaces.IElementalItem;
import com.hideout.elementalarsenal.item.custom.interfaces.IMultiElementItem;
import com.hideout.elementalarsenal.registry.suggestion.ModSuggestionProviders;
import com.hideout.elementalarsenal.util.ElementalType;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            final LiteralCommandNode<ServerCommandSource> ELEMENTAL_ARSENAL = dispatcher.register(literal("elementalarsenal")
                    .then(literal("give").requires(HAS_OP)
                            .then(literal("maxed_sword").executes(MAXED_SWORD))
                            .then(literal("all_gems").executes(ALL_GEMS)))
                    .then(literal("element").requires(HAS_OP)
                            .then(literal("add").then(argument("type", StringArgumentType.word())
                                    .suggests(ModSuggestionProviders.ELEMENTAL_TYPE)
                                    .executes(ADD_ELEMENT)))
                            .then(literal("remove").then(argument("type", StringArgumentType.word())
                                    .suggests(ModSuggestionProviders.ELEMENTAL_TYPE)
                                    .executes(REMOVE_ELEMENT)))
                            .then(literal("set").then(argument("type", StringArgumentType.word())
                                    .suggests(ModSuggestionProviders.ELEMENTAL_TYPE)
                                    .executes(SET_ELEMENT)))
                            .then(literal("list")
                                    .executes(LIST_ELEMENTS))
                    )
            );

            dispatcher.register(literal("ea").redirect(ELEMENTAL_ARSENAL));
        }));
    }

    private static final Predicate<ServerCommandSource> HAS_OP = serverCommandSource -> serverCommandSource.hasPermissionLevel(4);
    private static final Command<ServerCommandSource> MAXED_SWORD = context -> {
        final ServerCommandSource source = context.getSource();
        final ServerPlayerEntity sender = source.getPlayerOrThrow();
        ItemStack stack = new ItemStack(ModItems.ELEMENTAL_SWORD);
        stack.getOrCreateNbt().putIntArray(IMultiElementItem.AVAILABLE_TYPES,
                new int[] {0, 1, 2, 3, 4, 5, 6, 7});
        sender.giveItemStack(stack);
        return 1;
    };

    private static final Command<ServerCommandSource> ALL_GEMS = context -> {
        final ServerCommandSource source = context.getSource();
        final ServerPlayerEntity sender = source.getPlayerOrThrow();
        for (int i = 0; i < 8; i++) {
            ItemStack stack = new ItemStack(ModItems.ELEMENTAL_GEM);
            stack.getOrCreateNbt().putInt(IElementalItem.TYPE, i);
            sender.giveItemStack(stack);
        }

        return 1;
    };

    private static final Command<ServerCommandSource> ADD_ELEMENT = context -> {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        String type = context.getArgument("type", String.class);

        if (player.getMainHandStack().getItem() instanceof IMultiElementItem item) {
            ElementalType selectedType = ElementalType.fromString(type);
            if (selectedType == null) throw new RuntimeException("No type with name: " + type);
            item.addType(player.getMainHandStack(), selectedType);
        }

        return 1;
    };

    private static final Command<ServerCommandSource> REMOVE_ELEMENT = context -> {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        String type = context.getArgument("type", String.class);
        ItemStack stack = player.getMainHandStack();

        if (stack.getItem() instanceof IMultiElementItem item) {
            ElementalType selectedType = ElementalType.fromString(type);
            if (selectedType == null) throw new RuntimeException("No type with name: " + type);
            if (item.getAvailableTypes(stack).length > 0) {
                ArrayList<ElementalType> types = new ArrayList<>(Arrays.stream(item.getAvailableTypes(stack)).toList());
                types.remove(selectedType);
                stack.getOrCreateNbt().putIntArray(IMultiElementItem.AVAILABLE_TYPES, types.stream().map(ElementalType::getId).toList());
            }
        }

        return 1;
    };

    private static final Command<ServerCommandSource> SET_ELEMENT = context -> {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();
        String type = context.getArgument("type", String.class);

        if (player.getMainHandStack().getItem() instanceof IElementalItem item) {
            ElementalType selectedType = ElementalType.fromString(type);
            if (selectedType == null) throw new RuntimeException("No type with name: " + type);
            item.setType(player.getMainHandStack(), selectedType);
        }

        return 1;
    };

    private static final Command<ServerCommandSource> LIST_ELEMENTS = context -> {
        ServerCommandSource source = context.getSource();
        ServerPlayerEntity player = source.getPlayerOrThrow();

        if (player.getMainHandStack().getItem() instanceof IMultiElementItem item) {
            source.sendFeedback(() -> Text.literal(Arrays.stream(item.getAvailableTypes(player.getMainHandStack()))
                    .map(ElementalType::toCasedString).toList().toString()
                    .replace('[', ' ').replace(']', ' ').trim()),
                    false);
        }

        return 1;
    };
}
