package com.hideout.elementalarsenal.item;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.item.custom.ElementalGemItem;
import com.hideout.elementalarsenal.item.custom.ElementalSwordItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static Item ELEMENTAL_SWORD = registerItem("elemental_sword",
            new ElementalSwordItem(ToolMaterials.IRON, 7, 1.6f, new FabricItemSettings()));
    public static Item ELEMENTAL_GEM = registerItem("elemental_gem",
            new ElementalGemItem(new FabricItemSettings().fireproof().maxCount(1).rarity(Rarity.COMMON)));
    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ElementalArsenal.MOD_ID, name), item);
    }
    public static void registerItems() {
        
    }
}
