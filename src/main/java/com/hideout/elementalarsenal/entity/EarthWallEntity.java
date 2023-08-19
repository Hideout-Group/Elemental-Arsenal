package com.hideout.elementalarsenal.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class EarthWallEntity extends Entity {
    private final BlockState blockState;
    public EarthWallEntity(EntityType<?> type, World world) {
        super(type, world);
        this.blockState = Blocks.DIRT.getDefaultState();
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if (damageSource.getSource() instanceof PlayerEntity player) {
            this.addVelocity(player.getRotationVector().multiply(3, 0, 3));
            this.setRotation(player.getYaw(), this.getPitch());
        }
    }

    public BlockState getBlockState() {
        return blockState;
    }
}
