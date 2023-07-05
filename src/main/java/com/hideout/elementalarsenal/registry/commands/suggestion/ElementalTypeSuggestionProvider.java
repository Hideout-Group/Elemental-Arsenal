package com.hideout.elementalarsenal.registry.commands.suggestion;

import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
import com.hideout.elementalarsenal.registry.commands.ElementCommand;
import com.hideout.elementalarsenal.util.ElementalType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("RedundantThrows")
public class ElementalTypeSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        builder.suggest(ElementalType.BLANK.toString());
        builder.suggest(ElementalType.AIR.toString());
        builder.suggest(ElementalType.EARTH.toString());
        builder.suggest(ElementalType.WATER.toString());
        builder.suggest(ElementalType.FIRE.toString());
        builder.suggest(ElementalType.LIGHTNING.toString());
        builder.suggest(ElementalType.NATURE.toString());
        builder.suggest(ElementalType.ICE.toString());

        return builder.buildFuture();
    }
}
