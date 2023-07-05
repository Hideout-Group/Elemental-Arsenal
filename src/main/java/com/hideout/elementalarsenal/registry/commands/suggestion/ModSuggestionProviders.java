package com.hideout.elementalarsenal.registry.commands.suggestion;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.server.command.ServerCommandSource;

public class ModSuggestionProviders {
    public static final SuggestionProvider<ServerCommandSource> ELEMENTAL_TYPE = new ElementalTypeSuggestionProvider();
}
