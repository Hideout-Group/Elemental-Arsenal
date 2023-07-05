package com.hideout.elementalarsenal.datagen;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.datagen.jsonbuilder.SmithingInfusionJsonBuilder;
import com.hideout.elementalarsenal.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        SmithingInfusionJsonBuilder.create(ModItems.ELEMENTAL_INFUSION_TEMPLATE, ModItems.ELEMENTAL_SWORD,
                ModItems.ELEMENTAL_GEM).offerTo(exporter, new Identifier(ElementalArsenal.MOD_ID, "elemental_sword_smithing_infusion"));
    }
}
