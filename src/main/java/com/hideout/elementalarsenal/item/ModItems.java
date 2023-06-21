package com.hideout.elementalarsenal.item;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item BASE_ELEMENTAL_SWORD = registerItem("elemental_sword",
            new ElementalSwordItem(ToolMaterials.IRON, 7, 1.6f, new FabricItemSettings()));

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ElementalArsenal.MOD_ID, name), item);
    }
    public static void registerItems() {

    }
}
