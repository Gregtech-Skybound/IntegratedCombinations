package org.cyclops.integratedterminals.core.client.gui;

import lombok.Getter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.cyclops.integratedterminals.api.terminalstorage.crafting.ITerminalStorageTabIngredientCraftingHandler;

/**
 * @author rubensworks
 */
@Getter
public class CraftingJobGuiData {

    private final BlockPos pos;
    private final EnumFacing side;
    private final int channel;
    private final ITerminalStorageTabIngredientCraftingHandler handler;
    private final Object craftingJob;
    private final int itemIndex;

    public CraftingJobGuiData(BlockPos pos, EnumFacing side, int channel,
                              ITerminalStorageTabIngredientCraftingHandler handler, Object craftingJob) {
        this.pos = pos;
        this.side = side;
        this.channel = channel;
        this.handler = handler;
        this.craftingJob = craftingJob;
        this.itemIndex = -1; // Used to check if it's item or not
    }

    public CraftingJobGuiData(int itemIndex, int channel, ITerminalStorageTabIngredientCraftingHandler handler, Object craftingJob) {
        this.pos = null;
        this.side = null;
        this.channel = channel;
        this.handler = handler;
        this.craftingJob = craftingJob;
        this.itemIndex = itemIndex;
    }
}
