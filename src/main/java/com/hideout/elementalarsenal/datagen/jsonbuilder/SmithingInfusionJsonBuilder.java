package com.hideout.elementalarsenal.datagen.jsonbuilder;

import com.google.gson.JsonObject;
import com.hideout.elementalarsenal.recipe.SmithingInfusionRecipe;
import net.minecraft.data.server.recipe.RecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SmithingInfusionJsonBuilder extends RecipeJsonBuilder {
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final String baseNBTKey;
    private final String additionNBTKey;

    public SmithingInfusionJsonBuilder(Ingredient template, Ingredient base, Ingredient addition, String baseNBTKey, String additionNBTKey) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.baseNBTKey = baseNBTKey;
        this.additionNBTKey = additionNBTKey;
    }

    public static SmithingInfusionJsonBuilder create(Ingredient template, Ingredient base, Ingredient addition, String baseNBTKey, String additionNBTKey) {
        return new SmithingInfusionJsonBuilder(template, base, addition, baseNBTKey, additionNBTKey);
    }

    public static SmithingInfusionJsonBuilder create(ItemConvertible template, ItemConvertible base, ItemConvertible addition, String baseNBTKey, String additionNBTKey) {
        return create(Ingredient.ofItems(template), Ingredient.ofItems(base), Ingredient.ofItems(addition), baseNBTKey, additionNBTKey);
    }
    public static SmithingInfusionJsonBuilder create(ItemConvertible template, ItemConvertible base, ItemConvertible addition) {
        return create(Ingredient.ofItems(template), Ingredient.ofItems(base), Ingredient.ofItems(addition), "type", "type");
    }

    public static SmithingInfusionJsonBuilder create(TagKey<Item> template, TagKey<Item> base, TagKey<Item> addition, String baseNBTKey, String additionNBTKey) {
        return create(Ingredient.fromTag(template), Ingredient.fromTag(base), Ingredient.fromTag(addition), baseNBTKey, additionNBTKey);
    }



    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        exporter.accept(new SmithingInfusionJsonProvider(recipeId, template, base, addition, baseNBTKey, additionNBTKey));
    }

    private record SmithingInfusionJsonProvider(Identifier id, Ingredient template, Ingredient base,
                                                Ingredient addition, String baseNBTKey,
                                                String additionNBTKey) implements RecipeJsonProvider {
        @Override
        public void serialize(JsonObject json) {
            JsonObject addition = this.addition.toJson().getAsJsonObject();
            addition.addProperty("key", additionNBTKey);
            json.add("addition", addition);

            JsonObject base = this.base.toJson().getAsJsonObject();
            base.addProperty("key", baseNBTKey);
            json.add("base", base);

            json.add("template", template.toJson());

        }

        @Override
        public Identifier getRecipeId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return SmithingInfusionRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public Identifier getAdvancementId() {
            return null;
        }
        }
}
