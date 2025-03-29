package carpet.mixins;

import carpet.CarpetSettings;
import carpet.helpers.ThrowableSuppression;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Credit: https://github.com/OptiJava/OptCarpetAddition
@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlock_updateSuppressionCrashFixMixin {

    @Inject(
        method = "getComparatorOutput",
        at = @At("HEAD"),
        cancellable = true
    )
    public void onGetComparatorOutput(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (!CarpetSettings.updateSuppressionCrashFix)
            return;
        try {
            cir.setReturnValue(ScreenHandler.calculateComparatorOutput((Inventory)world.getBlockEntity(pos)));
        }
        catch (ClassCastException e)
        {
            throw new ThrowableSuppression("CCE suppression", pos);
        }
    }
}
