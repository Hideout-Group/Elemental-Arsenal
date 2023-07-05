package com.hideout.elementalarsenal.registry.commands.argument;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

public class ModArguments {
    public static void register() {
        ArgumentTypeRegistry.registerArgumentType(new Identifier(ElementalArsenal.MOD_ID, "elemental_type"),
                ElementalArgumentType.class, ConstantArgumentSerializer.of(ElementalArgumentType::type));
    }
}
