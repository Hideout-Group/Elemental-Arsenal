package com.hideout.elementalarsenal.registry.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("RedundantThrows")
public class ElementalTypeSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        builder.suggest("Blank");
        builder.suggest("Air");
        builder.suggest("Earth");
        builder.suggest("Fire");
        builder.suggest("Lightning");
        builder.suggest("Nature");
        builder.suggest("Ice");
        return builder.buildFuture();
    }
}
