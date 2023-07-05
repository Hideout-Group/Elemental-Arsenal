package com.hideout.elementalarsenal.registry;

import com.hideout.elementalarsenal.registry.commands.argument.ModArguments;

public class ModRegistries {
    public static void registerRegistries() {
        ModArguments.register();
        ModCommands.registerCommands();
    }
}
