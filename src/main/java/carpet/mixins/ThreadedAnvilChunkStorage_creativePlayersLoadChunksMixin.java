package carpet.mixins;

import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.server.network.ServerPlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorage_creativePlayersLoadChunksMixin
{
@Inject(method = "Lnet/minecraft/server/world/ThreadedAnvilChunkStorage;doesNotGenerateChunks(Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void startProfilerSection(ServerPlayerEntity serverPlayer, CallbackInfoReturnable<Boolean> cir)
    {
        if (!CarpetSettings.creativePlayersLoadChunks && serverPlayer.isCreative()) {
            cir.setReturnValue(true);
        }
    }
}
