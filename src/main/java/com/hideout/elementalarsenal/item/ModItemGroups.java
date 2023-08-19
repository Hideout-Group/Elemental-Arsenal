package com.hideout.elementalarsenal.item;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static ItemGroup ELEMENTAL_ARSENAL_GROUP;
    public static void registerItemGroups() {
        ELEMENTAL_ARSENAL_GROUP = Registry.register(Registries.ITEM_GROUP,new Identifier(ElementalArsenal.MOD_ID,"elemental_arsenal_group"),
                FabricItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.elementalarsenal.group"))
                        .icon(()->new ItemStack(ModItems.ELEMENTAL_GEM))
                        .entries((displayContext, entries) ->
                        {
                            entries.add(ModItems.ELEMENTAL_SWORD);
                            entries.add(ModItems.ELEMENTAL_TRIDENT);
                            entries.add(ModItems.ELEMENTAL_GEM);
                            entries.add(ModItems.ELEMENTAL_INFUSION_TEMPLATE);
                            entries.add(ModItems.SCORCHED_IRON);
                        })
                        .build());

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ModItems.SCORCHED_IRON);
            entries.add(ModItems.ELEMENTAL_GEM);
            entries.add(ModItems.ELEMENTAL_INFUSION_TEMPLATE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(ModItems.ELEMENTAL_SWORD);
            entries.add(ModItems.ELEMENTAL_TRIDENT);
        });
    }
}


