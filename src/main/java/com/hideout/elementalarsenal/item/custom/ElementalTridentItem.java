package com.hideout.elementalarsenal.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.hideout.elementalarsenal.entity.ElementalTridentEntity;
import com.hideout.elementalarsenal.entity.ModEntities;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
import com.hideout.elementalarsenal.item.custom.util.ModTooltipHelper;
import com.hideout.elementalarsenal.util.ElementalOnHitEffects;
import com.hideout.elementalarsenal.util.ElementalType;
import com.hideout.elementalarsenal.util.ElementalUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ElementalTridentItem extends TridentItem implements MultiElementItem {
    private final float attackDamage;
    public ElementalTridentItem(Settings settings, float attackDamage) {
        super(settings);
        this.attackDamage = attackDamage;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int useTime = this.getMaxUseTime(stack) - remainingUseTicks;
            if (useTime >= 10) {
                int riptideLevel = EnchantmentHelper.getRiptide(stack);
                if (riptideLevel <= 0 || playerEntity.isTouchingWaterOrRain()) {
                    if (!world.isClient) {
                        stack.damage(1, playerEntity, (p) -> {
                            p.sendToolBreakStatus(user.getActiveHand());
                        });
                        if (riptideLevel == 0) {
                            ElementalTridentEntity tridentEntity = createTrident(world, user, stack);
                            tridentEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + (float)riptideLevel * 0.5F, 1.0F);
                            if (playerEntity.getAbilities().creativeMode) {
                                tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                            }

                            world.spawnEntity(tridentEntity);
                            world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                            if (!playerEntity.getAbilities().creativeMode) {
                                playerEntity.getInventory().removeOne(stack);
                            }
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                    if (riptideLevel > 0) {
                        float yaw = playerEntity.getYaw();
                        float pitch = playerEntity.getPitch();
                        float h = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                        float k = -MathHelper.sin(pitch * 0.017453292F);
                        float l = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
                        float m = MathHelper.sqrt(h * h + k * k + l * l);
                        float n = 3.0F * ((1.0F + (float)riptideLevel) / 4.0F);
                        h *= n / m;
                        k *= n / m;
                        l *= n / m;
                        playerEntity.addVelocity(h, k, l);
                        playerEntity.useRiptide(20);
                        if (playerEntity.isOnGround()) {
                            float o = 1.1999999F;
                            playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.1999999284744263, 0.0));
                        }

                        SoundEvent soundEvent;
                        if (riptideLevel >= 3) {
                            soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                        } else if (riptideLevel == 2) {
                            soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                        } else {
                            soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    }

                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        ElementalType[] types = ElementalUtils.getAvailableTypes(stack);
        if (types.length > 0) {
            if (!Screen.hasShiftDown()) {
                tooltip.add(Text.literal("Press SHIFT to view elements").formatted(Formatting.YELLOW));
                tooltip.add(Text.literal("Press CTRL + SHIFT to view abilities").formatted(Formatting.AQUA));
            } else if (Screen.hasControlDown()) {
                ElementalType type = ElementalUtils.getType(stack);
                ModTooltipHelper.Trident.INSTANCE.getTooltipForType(type).forEach(text ->
                        tooltip.add(text.setStyle(type.getStyle())));
            } else {
                for (ElementalType type : types) {
                    tooltip.add(type.toFormattedText());
                }
            }
        } else {
            ElementalUtils.addType(stack, ElementalType.BLANK);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getWorld().isClient) return super.postHit(stack, target, attacker);
        ElementalType type = ElementalUtils.getType(stack);

        ElementalOnHitEffects.performOnHitEffect(type, target, attacker);

        return super.postHit(stack, target, attacker);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal(ElementalUtils.getAppendedName(stack)).setStyle(ElementalUtils.getType(stack).getStyle());
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot != EquipmentSlot.MAINHAND) return ImmutableMultimap.of();

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        float damage = attackDamage;

        switch (ElementalUtils.getType(stack)) {
            case BLANK -> damage--; // No element so shouldn't be strong
            case FIRE, LIGHTNING, ICE -> damage++; // 'Offensive' types
            case EARTH -> damage += 3; // Heavy hitter but slow
        }

        float attackSpeed = -2.4f;

        switch (ElementalUtils.getType(stack)) {
            case EARTH -> attackSpeed -= 0.6f;
            case LIGHTNING -> attackSpeed += 0.4f;
        }

        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,
                "Elemental Type Modifier", damage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID,
                "Elemental Type Modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));

        return builder.build();
    }

    public @NotNull ElementalTridentEntity createTrident(World world, LivingEntity user, ItemStack stack) {
        ElementalTridentEntity elementalTridentEntity = Objects.requireNonNull(ModEntities.ELEMENTAL_TRIDENT.create(world));
        elementalTridentEntity.setAttributes(stack);
        elementalTridentEntity.setOwner(user);
        elementalTridentEntity.setTridentStack(stack);
        elementalTridentEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F, 1.0F);
        elementalTridentEntity.updatePosition(user.getX(), user.getEyeY() - 0.1, user.getZ());
        return elementalTridentEntity;
    }
}
