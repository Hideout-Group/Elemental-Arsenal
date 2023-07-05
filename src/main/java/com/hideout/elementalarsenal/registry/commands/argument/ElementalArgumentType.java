package com.hideout.elementalarsenal.registry.commands.argument;

import com.hideout.elementalarsenal.util.ElementalType;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.text.Text;

public class ElementalArgumentType implements ArgumentType<ElementalType> {


    // Static method for conformity purposes
    public static ElementalArgumentType type() {
        return new ElementalArgumentType();
    }
    public static <S> ElementalType getType(String name, CommandContext<S> ctx) {
        return ctx.getArgument(name, ElementalType.class);
    }

    /** Courtesy of the Fabric Tutorial Wiki <br>
     * <a href="https://fabricmc.net/wiki/tutorial:command_argument_types">@Link</a>
     * 6/7/2023
     * */
    @Override
    public ElementalType parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor(); // The starting position of the cursor is at the beginning of the argument.
        if (!reader.canRead()) {
            reader.skip();
        }

        while (reader.canRead() && reader.peek() != ' ') { // "peek" provides the character at the current cursor position.
            reader.skip(); // Tells the StringReader to move its cursor to the next position.
        }

        String argument = reader.getString().substring(argBeginning, reader.getCursor());

        ElementalType type = ElementalType.fromString(argument);

        if (type != null) {
            return type;
        } else {
            throw new SimpleCommandExceptionType(Text.literal("Elemental Type does not exist")).createWithContext(reader);
        }
    }
}
