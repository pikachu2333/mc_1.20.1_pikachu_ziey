package com.example.pikachu_ziey;

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

            if (player.isSneaking()) {          // 脱离
                player.stopRiding();
                syncPassengers(target);
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
    private static void syncPassengers(PlayerEntity base) {
        EntityPassengersSetS2CPacket packet = new EntityPassengersSetS2CPacket(base);
        ServerWorld sw = (ServerWorld) base.getWorld();
        sw.getServer().getPlayerManager().getPlayerList()
                .forEach(p -> p.networkHandler.sendPacket(packet));

        /* 1.20.1 只有 getScoreboardTags()，没有 add/remove 方法 */
        if (base.getPassengerList().isEmpty())
            base.getCommandTags().remove("piggyback_base");
        else
            base.getCommandTags().add("piggyback_base");
    }
}