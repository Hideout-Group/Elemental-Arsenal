package com.hideout.elementalarsenal.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
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

import java.util.Objects;

/**
 * <code>SmithingRecipe + RecipeSerializer</code> for use in a Smithing Table. <br>
 * Takes an NBT key from the addition, grabs the value, and places it under a new name on the base item. <br>
 * Uses typical <code>JSONs.</code> ID: <code>elementalarsenal:smithing_infusion</code> <br>
 * base and addition <code>JSON</code> objects should have a string "key" with the NBT key to write to and read from respectively.
 * @author Overcontrol1
 **/
public class SmithingInfusionRecipe implements SmithingRecipe {
    private final Identifier id;
    private final Ingredient template;
    private final Ingredient base;
    private final Ingredient addition;
    private final ItemStack result;
    private final String baseNBTKey; // Key for the NBT on the base item to be written to
    private final String additionNBTKey; // Key for the NBT on the addition item to be read from


    public SmithingInfusionRecipe(Identifier id, Ingredient template, Ingredient base, Ingredient addition, ItemStack result, String baseNBTKey, String additionNBTKey) {
        this.id = id;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
        this.baseNBTKey = baseNBTKey;
        this.additionNBTKey = additionNBTKey;
    }

    @Override
    public boolean testTemplate(ItemStack stack) {
        return template.test(stack);
    }

    @Override
    public boolean testBase(ItemStack stack) {
        System.out.println(stack.getName().getString());
        return base.test(stack);
    }

    @Override
    public boolean testAddition(ItemStack stack) {
        System.out.println(stack.getName().getString());
        return addition.test(stack);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        ItemStack baseStack = inventory.getStack(1);
        ItemStack additionStack = inventory.getStack(2);

        if (baseStack.hasNbt() && additionStack.hasNbt()) {
            if (Objects.equals(baseStack.getOrCreateNbt().get(baseNBTKey), additionStack.getOrCreateNbt().get(additionNBTKey))) {
                return false;
            }
        } else {
            return false;
        }

        return template.test(inventory.getStack(0)) && base.test(baseStack) && addition.test(additionStack);
    }

    @Override
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        ItemStack base = inventory.getStack(1);
        ItemStack addition = inventory.getStack(2);

        ItemStack stack = base.copy();

        stack.getOrCreateNbt().put(baseNBTKey, addition.getOrCreateNbt().get(additionNBTKey));
        if (stack.getItem() instanceof MultiElementItem item) {
            item.updateTypes(stack);
        }
        return stack;
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

    public static class Serializer implements RecipeSerializer<SmithingInfusionRecipe> {
        private Serializer() {}
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "smithing_infusion";

        @Override
        public SmithingInfusionRecipe read(Identifier id, JsonObject json) {
            JsonElement templateElement = JsonHelper.getElement(json, "template");
            JsonElement baseElement = JsonHelper.getElement(json, "base");
            JsonElement additionElement = JsonHelper.getElement(json, "addition");

            String baseNBTKey = JsonHelper.getString(JsonHelper.asObject(baseElement, "base"), "key");
            String additionNBTKey = JsonHelper.getString(JsonHelper.asObject(additionElement, "addition"), "key");

            Ingredient template = Ingredient.fromJson(templateElement);
            Ingredient base = Ingredient.fromJson(baseElement);
            Ingredient addition = Ingredient.fromJson(additionElement);

            ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

            return new SmithingInfusionRecipe(id, template, base, addition, result, baseNBTKey, additionNBTKey);
        }

        @Override
        public SmithingInfusionRecipe read(Identifier id, PacketByteBuf buf) {
            String baseNBTKey = buf.readString();
            String additionNBTKey = buf.readString();

            Ingredient template = Ingredient.fromPacket(buf);
            Ingredient base = Ingredient.fromPacket(buf);
            Ingredient addition = Ingredient.fromPacket(buf);

            ItemStack result = buf.readItemStack();

            return new SmithingInfusionRecipe(id, template, base, addition, result, baseNBTKey, additionNBTKey);
        }

        @Override
        public void write(PacketByteBuf buf, SmithingInfusionRecipe recipe) {
            recipe.base.write(buf);
            recipe.addition.write(buf);

            buf.writeString(recipe.baseNBTKey);
            buf.writeString(recipe.additionNBTKey);

            buf.writeItemStack(recipe.result);
        }
    }
}
