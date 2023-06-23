package com.hideout.elementalarsenal.registry;

import com.hideout.elementalarsenal.item.ModItems;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.literal;

public class ModCommands {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("ea")
                    .then(literal("give").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                            .then(literal("maxed_sword")
                                    .executes(context -> {
                                        final ServerCommandSource source = context.getSource();
                                        final ServerPlayerEntity sender = source.getPlayerOrThrow();
                                        ItemStack stack = new ItemStack(ModItems.ELEMENTAL_SWORD);
                                        stack.getOrCreateNbt().putIntArray("available_types",
                                                new int[] {0, 1, 2, 3, 4, 5, 6, 7});
                                        sender.giveItemStack(stack);
                                        return 1;
                                    }))
                            .then(literal("all_gems")
                                    .executes(context -> {
                                        final ServerCommandSource source = context.getSource();
                                        final ServerPlayerEntity sender = source.getPlayerOrThrow();
                                        for (int i = 0; i < 8; i++) {
                                            ItemStack stack = new ItemStack(ModItems.ELEMENTAL_GEM);
                                            stack.getOrCreateNbt().putInt("type", i);
                                            sender.giveItemStack(stack);
                                        }

                                        return 1;
                            }))));
        }));
    }
}
