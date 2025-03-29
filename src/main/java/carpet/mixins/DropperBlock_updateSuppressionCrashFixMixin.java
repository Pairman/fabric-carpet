package carpet.mixins;

import carpet.CarpetSettings;
import carpet.logging.LoggerRegistry;
import carpet.utils.Messenger;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.BaseText;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Credit: https://github.com/OptiJava/OptCarpetAddition
@Mixin(DropperBlock.class)
public class DropperBlock_updateSuppressionCrashFixMixin {

    @Inject(
        method = "dispense",
        at = @At("HEAD"),
        cancellable = true
    )
    public void onDispense(ServerWorld serverWorld, BlockPos pos, CallbackInfo ci) {
        if (!CarpetSettings.updateSuppressionCrashFix || (serverWorld.getBlockEntity(pos) instanceof DropperBlockEntity))
            return;
        CarpetSettings.LOG.info("Server crash prevented from: CCE dropper at: [ " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " ]");
        if (LoggerRegistry.__updateSuppressedCrashes) {
            LoggerRegistry.getLogger("updateSuppressedCrashes").log(() -> {
                return new BaseText[]{Messenger.c(
                    "w Server crash prevented from: ",
                    "m CCE dropper",
                    "w - at: ",
                    "g [ " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " ]"
                )};
            });
        }
        ci.cancel();
    }
}
