package com.hideout.elementalarsenal.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.hideout.elementalarsenal.item.custom.interfaces.MultiElementItem;
import com.hideout.elementalarsenal.item.custom.util.ElementalSwordRightClickEffects;
import com.hideout.elementalarsenal.item.custom.util.ModTooltipHelper;
import com.hideout.elementalarsenal.util.ElementalOnHitEffects;
import com.hideout.elementalarsenal.util.ElementalType;
import com.hideout.elementalarsenal.util.ElementalUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElementalSwordItem extends SwordItem implements MultiElementItem {
    public ElementalSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        ElementalUtils.addType(stack, ElementalType.BLANK);
        return stack;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.getWorld().isClient) return super.postHit(stack, target, attacker);
        ElementalType type = ElementalUtils.getType(stack);

        ElementalOnHitEffects.performOnHitEffect(type, target, attacker);

        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient || hand.equals(Hand.OFF_HAND)) {
            return super.use(world, user, hand);
        }

        ItemStack stack = user.getMainHandStack();
        ElementalType type = ElementalUtils.getType(stack);

        int cooldown = ElementalSwordRightClickEffects.performRightClickEffect(type, world, user);

        if (cooldown > 0) {
            user.getItemCooldownManager().set(this, cooldown);
        }

        return super.use(world, user, hand);
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
                ModTooltipHelper.Sword.INSTANCE.getTooltipForType(type).forEach(text ->
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
    public Text getName(ItemStack stack) {
        return Text.literal(ElementalUtils.getAppendedName(stack))
                .setStyle(ElementalUtils.getType(stack).getStyle());
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        if (slot != EquipmentSlot.MAINHAND) return ImmutableMultimap.of();

        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        float damage = getAttackDamage();

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
}
