package com.hideout.elementalarsenal.item.custom.util;

import com.hideout.elementalarsenal.sound.ModSounds;
import com.hideout.elementalarsenal.util.ElementalType;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class ElementalSwordRightClickEffects {
    private static final int DEFAULT_COOLDOWN = 20;

    public static int performRightClickEffect(ElementalType type, World world, PlayerEntity player) {
        switch (type) {
            case AIR -> { return air(world, player); }
            case EARTH -> { return earth(world, player); }
            case WATER -> { return water(world, player); }
            case FIRE -> { return fire(world, player); }
            case LIGHTNING -> { return lightning(world, player); }
            case NATURE -> { return nature(world, player); }
            case ICE -> { return ice(world, player); }
        }
        return 0;
    }

    private static int air(World world, PlayerEntity player) {
        int cooldown = 0;
        if (player.isSneaking()) {
            Vec3d rotVec = player.getRotationVector();
            Box box = new Box(player.getBlockPos()).expand(3);
            List<Entity> entities = world.getOtherEntities(player, box);

            entities.forEach(entity -> {
                if (player.canSee(entity)) {
                    entity.addVelocity(rotVec.multiply(2));
                    if (entity instanceof PlayerEntity playerEntity)
                        playerEntity.velocityModified = true;
                }
            });

            cooldown = (int) (DEFAULT_COOLDOWN * 0.75f);
        } else if (player.isFallFlying()) {
            Vec3d rotVec = player.getRotationVector();
            player.addVelocity(rotVec.multiply(1.5, 1.5, 1.5));
            player.velocityModified = true;
            cooldown = DEFAULT_COOLDOWN * 12;
        } else {
            Vec3d rotVec = player.getRotationVector();
            Vec3d offsetVec = player.getEyePos().add(rotVec.multiply(2));
            Box box = new Box(player.getBlockPos()).expand(3);
            List<Entity> entities = world.getOtherEntities(player, box);

            entities.forEach(entity -> {
                if (player.canSee(entity)) {
                    entity.addVelocity(rotVec.multiply(2));
                    if (entity instanceof PlayerEntity playerEntity)
                        playerEntity.velocityModified = true;
                }
            });

            world.addParticle(ParticleTypes.CLOUD,
                    offsetVec.x, offsetVec.y, offsetVec.z,
                    rotVec.x * 0.3, rotVec.y * 0.3, rotVec.z * 0.3);

            if (player.isOnGround()) {
                player.addVelocity(rotVec.multiply(-0.4, -3, -0.4));
            } else {
                player.addVelocity(rotVec.multiply(-4, -0.6, -4));
            }
            player.velocityModified = true;
            cooldown = player.isOnGround() ? DEFAULT_COOLDOWN / 2 : DEFAULT_COOLDOWN;
        }

        world.playSoundFromEntity(null, player, SoundEvents.BLOCK_POWDER_SNOW_STEP,
                SoundCategory.PLAYERS, 1f, 1.5f);

        return cooldown;
    }
    private static int earth(World world, PlayerEntity player) {

        return DEFAULT_COOLDOWN;
    }
    private static int water(World world, PlayerEntity player) {
        final int MAX_AMOUNT_OF_MOBS = 10;
        final int RAIN_HEIGHT = 10;
        for (int i = 0; i < MAX_AMOUNT_OF_MOBS; i++) {
            final int CHOSEN_MOB_INDEX = player.getWorld().random.nextInt(3);
            switch (CHOSEN_MOB_INDEX) {
                case 0 -> {
                    DrownedEntity drowned = new DrownedEntity(EntityType.DROWNED, world);
                    drowned.setPosition(player.getPos().add(0, RAIN_HEIGHT, 0).addRandom(world.random, 3));
                    drowned.equipStack(EquipmentSlot.HEAD, new ItemStack(Blocks.GLASS));
                    world.spawnEntity(drowned);
                }
                case 1 -> {
                    GuardianEntity guardian = new GuardianEntity(EntityType.GUARDIAN, world);
                    guardian.setPosition(player.getPos().add(0, RAIN_HEIGHT, 0).addRandom(world.random, 3));
                    world.spawnEntity(guardian);
                }
                case 2 -> {
                    SalmonEntity salmon = new SalmonEntity(EntityType.SALMON, world);
                    salmon.setPosition(player.getPos().add(0, RAIN_HEIGHT, 0).addRandom(world.random, 3));
                    world.spawnEntity(salmon);
                }
            }
        }

        return DEFAULT_COOLDOWN * 3;
    }
    private static int fire(World world, PlayerEntity player) {
        return DEFAULT_COOLDOWN;
    }
    private static int lightning(World world, PlayerEntity player) {
        Vec3d eyePos = player.getEyePos();
        Vec3d endPos = eyePos.add(player.getRotationVector().multiply(10));
        BlockHitResult result = world.raycast(new RaycastContext(eyePos,endPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE,player));
        if (result.getType() == HitResult.Type.BLOCK) {
            LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT,world);
            lightning.setPosition(result.getPos());
            world.spawnEntity(lightning);
            return DEFAULT_COOLDOWN * 2;
        }
        return 0;
    }
    private static int nature(World world, PlayerEntity player) {
        return DEFAULT_COOLDOWN;
    }
    private static int ice(World world, PlayerEntity player) {
        return DEFAULT_COOLDOWN;
    }
}
