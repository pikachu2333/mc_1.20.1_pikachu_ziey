package com.example.pikachu_ziey.Player;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PiggyBackMod implements ModInitializer {

    @Override
    public void onInitialize() {
        /* 右键玩家事件 */
        UseEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (world.isClient || hand != Hand.MAIN_HAND) return ActionResult.PASS;
            if (!(entity instanceof PlayerEntity target)) return ActionResult.PASS;
            if (player.isSpectator() || !player.getStackInHand(hand).isEmpty()) return ActionResult.PASS;

            if (player.isSneaking()) {
                PlayerEntity vehicle = (PlayerEntity) player.getVehicle();
                player.stopRiding();
                if (vehicle != null) {
                    syncPassengers(vehicle);        // 全员第一时间收到
                    // 底座玩家再补发两帧
                    if (vehicle instanceof ServerPlayerEntity svc) {
                        svc.networkHandler.sendPacket(new EntityPassengersSetS2CPacket(vehicle));
                        svc.getServer().execute(() ->
                                svc.networkHandler.sendPacket(new EntityPassengersSetS2CPacket(vehicle))
                        );
                    }
                }
                return ActionResult.SUCCESS;
            }

            /* 挂载 */
            if (target.getVehicle() == player) return ActionResult.FAIL; // 防止互相骑
            player.stopRiding();                // 脱离旧载具
            player.startRiding(target, true);   // 正式骑上
            syncPassengers(target);
            return ActionResult.SUCCESS;
        });
    }

    /* 广播乘客列表 + 打/清 tag */
    /* 广播乘客列表 + 打/清 tag */
    private static void syncPassengers(PlayerEntity vehicle) {
        EntityPassengersSetS2CPacket packet = new EntityPassengersSetS2CPacket(vehicle);
        ServerWorld sw = (ServerWorld) vehicle.getWorld();
        sw.getServer().getPlayerManager().getPlayerList()
                .forEach(p -> p.networkHandler.sendPacket(packet));
        // 局域网/单人主机有时不在列表里，再强行发一次
        if (vehicle instanceof ServerPlayerEntity svc) {
            svc.networkHandler.sendPacket(packet);
        }

        if (vehicle.getPassengerList().isEmpty()) {
            vehicle.getCommandTags().remove("piggyback_base");
        } else {
            if (!vehicle.getCommandTags().contains("piggyback_base"))
                vehicle.getCommandTags().add("piggyback_base");
        }
    }
}