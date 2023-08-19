package com.hideout.elementalarsenal.entity;

import com.hideout.elementalarsenal.ElementalArsenal;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<EarthWallEntity> EARTH_WALL = registerEntity("earth_wall",
            FabricEntityTypeBuilder.create().entityFactory(EarthWallEntity::new).fireImmune().dimensions(EntityDimensions.fixed(3f, 3f)).build());
    public static final EntityType<ElementalTridentEntity> ELEMENTAL_TRIDENT = registerEntity("elemental_trident",
            FabricEntityTypeBuilder.create().spawnGroup(SpawnGroup.MISC).entityFactory(ElementalTridentEntity::new).fireImmune()
                    .dimensions(EntityDimensions.changing(0.5f, 0.5f))
                    .trackedUpdateRate(20).trackRangeBlocks(4).build());
    public static void register() {

    }

    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType<T> entity) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(ElementalArsenal.MOD_ID, name), entity);
    }
}
