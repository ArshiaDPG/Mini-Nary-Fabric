package net.narybdaygroup.mininary.common.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.WalkTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;

public class MiniNaryBrain {
    private static void addCoreActivities(Brain<MiniNaryEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new StayAboveWaterTask(0.8F), new WalkTask(2.5F), new LookAroundTask(45, 90), new WanderAroundTask()));
    }
    private static Optional<LookTarget> getLikedLookTarget(LivingEntity allay) {
        return getLikedPlayer(allay).map((player) -> {
            return new EntityLookTarget(player, true);
        });
    }

    public static Optional<ServerPlayerEntity> getLikedPlayer(LivingEntity nary) {
        World world = nary.getWorld();
        if (!world.isClient() && world instanceof ServerWorld serverWorld) {
            Optional<UUID> optional = nary.getBrain().getOptionalMemory(MemoryModuleType.LIKED_PLAYER);
            if (optional.isPresent()) {
                Entity entity = serverWorld.getEntity(optional.get());
                if (entity instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                    if ((serverPlayerEntity.interactionManager.isSurvivalLike() || serverPlayerEntity.interactionManager.isCreative()) && serverPlayerEntity.isInRange(nary, 64.0)) {
                        return Optional.of(serverPlayerEntity);
                    }
                }

                return Optional.empty();
            }
        }

        return Optional.empty();
    }
}
