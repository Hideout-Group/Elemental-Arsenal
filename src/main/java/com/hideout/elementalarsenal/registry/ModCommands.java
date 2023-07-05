package com.hideout.elementalarsenal.registry;

import com.hideout.elementalarsenal.registry.commands.ElementCommand;
import com.hideout.elementalarsenal.registry.commands.ModGiveCommand;
import com.hideout.elementalarsenal.registry.commands.argument.ElementalArgumentType;
import com.hideout.elementalarsenal.registry.commands.suggestion.ModSuggestionProviders;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Predicate;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            // Root Command
            final LiteralCommandNode<ServerCommandSource> ELEMENTAL_ARSENAL =
                    literal("elementalarsenal")
                    .build();

            // Give Commands
            final LiteralCommandNode<ServerCommandSource> GIVE =
                    literal("give")
                            .requires(HAS_OP)
                            .build();
            final LiteralCommandNode<ServerCommandSource> GIVE_MAXED_SWORD =
                    literal("maxed_sword")
                            .executes(ModGiveCommand::maxedSword)
                            .build();
            final LiteralCommandNode<ServerCommandSource> GIVE_ALL_GEMS =
                    literal("all_gems")
                            .executes(ModGiveCommand::allGems)
                            .build();

            // Element Commands
            final LiteralCommandNode<ServerCommandSource> ELEMENT =
                    literal("element")
                            .requires(HAS_OP)
                            .build();

            final LiteralCommandNode<ServerCommandSource> ELEMENT_SET =
                    literal("set")
                            .then(argument("type", ElementalArgumentType.type())
                                    .suggests(ModSuggestionProviders.ELEMENTAL_TYPE)
                            .executes(ElementCommand::set))
                            .build();

            final LiteralCommandNode<ServerCommandSource> ELEMENT_ADD =
                    literal("add")
                            .then(argument("type", ElementalArgumentType.type())
                                    .suggests(ModSuggestionProviders.ELEMENTAL_TYPE)
                            .executes(ElementCommand::add))
                            .build();

            final LiteralCommandNode<ServerCommandSource> ELEMENT_REMOVE =
                    literal("remove")
                            .then(argument("type", ElementalArgumentType.type())
                                    .suggests(ModSuggestionProviders.ELEMENTAL_TYPE)
                            .executes(ElementCommand::remove))
                            .build();

            final LiteralCommandNode<ServerCommandSource> ELEMENT_LIST =
                    literal("list")
                            .executes(ElementCommand::list)
                            .build();



            // Tree
            dispatcher.getRoot().addChild(ELEMENTAL_ARSENAL);

            // Give
            ELEMENTAL_ARSENAL.addChild(GIVE);
            GIVE.addChild(GIVE_MAXED_SWORD);
            GIVE.addChild(GIVE_ALL_GEMS);

            // Element
            ELEMENTAL_ARSENAL.addChild(ELEMENT);
            ELEMENT.addChild(ELEMENT_SET);
            ELEMENT.addChild(ELEMENT_ADD);
            ELEMENT.addChild(ELEMENT_REMOVE);
            ELEMENT.addChild(ELEMENT_LIST);

            // Redirects
            dispatcher.register(literal("ea").redirect(ELEMENTAL_ARSENAL));
        }));
    }

    private static final Predicate<ServerCommandSource> HAS_OP = serverCommandSource -> serverCommandSource.hasPermissionLevel(4);
}
