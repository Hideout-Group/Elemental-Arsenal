package com.hideout.elementalarsenal.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

/**
A class I was going to use to extend from but that was a bit messy. <br>
Debating whether to keep it in as you could probably use Ingredient.EMPTY as a template.
 */

public class TemplatelessSmithingRecipe implements SmithingRecipe {
    protected final Identifier id;
    protected final Ingredient base;
    protected final Ingredient addition;
    protected final ItemStack result;


    public TemplatelessSmithingRecipe(Identifier id, Ingredient base, Ingredient addition, ItemStack result) {
        this.id = id;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    @Override
    public boolean testTemplate(ItemStack stack) {
        return true;
    }

    @Override
    public boolean testBase(ItemStack stack) {
        return base.test(stack);
    }

    @Override
    public boolean testAddition(ItemStack stack) {
        return addition.test(stack);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        ItemStack baseStack = inventory.getStack(1);
        ItemStack additionStack = inventory.getStack(2);

        return base.test(baseStack) && addition.test(additionStack);
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        return result.copy();
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return result;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<TemplatelessSmithingRecipe> {
        private Serializer() {}
        public static Serializer INSTANCE = new Serializer();
        public static final String ID = "templateless_smithing";

        @Override
        public TemplatelessSmithingRecipe read(Identifier id, JsonObject json) {
            JsonElement baseElement = JsonHelper.getElement(json, "base");
            JsonElement additionElement = JsonHelper.getElement(json, "addition");
            Ingredient base = Ingredient.fromJson(baseElement);
            Ingredient addition = Ingredient.fromJson(additionElement);
            ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            System.out.println(result.getName().getString());
            return new TemplatelessSmithingRecipe(id, base, addition, result);
        }

        @Override
        public TemplatelessSmithingRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient base = Ingredient.fromPacket(buf);
            Ingredient addition = Ingredient.fromPacket(buf);
            ItemStack result = buf.readItemStack();
            return new TemplatelessSmithingRecipe(id, base, addition, result);
        }

        @Override
        public void write(PacketByteBuf buf, TemplatelessSmithingRecipe recipe) {
            recipe.base.write(buf);
            recipe.addition.write(buf);
            buf.writeItemStack(recipe.result);
        }
    }
}
