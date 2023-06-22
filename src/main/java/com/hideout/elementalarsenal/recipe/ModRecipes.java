package com.hideout.elementalarsenal.recipe;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(ElementalArsenal.MOD_ID, SmithingInfusionRecipe.Serializer.ID),
                SmithingInfusionRecipe.Serializer.INSTANCE);
    }
}
