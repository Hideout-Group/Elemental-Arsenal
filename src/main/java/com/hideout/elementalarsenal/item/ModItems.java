package com.hideout.elementalarsenal.item;

import com.hideout.elementalarsenal.ElementalArsenal;
import com.hideout.elementalarsenal.entity.ElementalTridentEntity;
import com.hideout.elementalarsenal.entity.ModEntities;
import com.hideout.elementalarsenal.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

import java.util.Objects;

public class ModItems {
    public static final Item ELEMENTAL_SWORD = registerItem("elemental_sword",
            new ElementalSwordItem(ElementalToolMaterial.INSTANCE, 3, -2.4f,
                    new FabricItemSettings().fireproof()));
    public static final Item ELEMENTAL_TRIDENT = registerItem("elemental_trident",
            new ElementalTridentItem(new FabricItemSettings().fireproof(), 8));

    public static final Item ELEMENTAL_GEM = registerItem("elemental_gem",
            new ElementalGemItem(new FabricItemSettings().fireproof().maxCount(1)));

    public static final Item SCORCHED_IRON = registerItem("scorched_iron",
            new Item(new FabricItemSettings().fireproof()));

    public static final Item ELEMENTAL_INFUSION_TEMPLATE = registerItem("elemental_infusion_template",
            new ElementalInfusionTemplate());

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ElementalArsenal.MOD_ID, name), item);
    }
    public static void register() {
        DispenserBlock.registerBehavior(ModItems.ELEMENTAL_TRIDENT, new ProjectileDispenserBehavior() {
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                ElementalTridentEntity entity = Objects.requireNonNull(ModEntities.ELEMENTAL_TRIDENT.create(world));
                entity.setPos(position.getX(), position.getY(), position.getZ());
                stack.decrement(1);
                return entity;
            }
        });
    }
}
