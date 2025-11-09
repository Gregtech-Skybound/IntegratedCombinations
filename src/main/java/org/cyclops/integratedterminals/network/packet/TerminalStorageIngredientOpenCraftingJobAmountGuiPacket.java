package org.cyclops.integratedterminals.network.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.integratedterminals.IntegratedTerminals;
import org.cyclops.integratedterminals.core.client.gui.CraftingOptionGuiData;
import org.cyclops.integratedterminals.core.client.gui.ExtendedGuiHandler;
import org.cyclops.integratedterminals.proxy.guiprovider.GuiProviders;

/**
 * Packet for opening the crafting job amount gui.
 * @author rubensworks
 *
 */
public class TerminalStorageIngredientOpenCraftingJobAmountGuiPacket<T, M> extends TerminalStorageIngredientCraftingOptionDataPacketAbstract<T, M> {

    public TerminalStorageIngredientOpenCraftingJobAmountGuiPacket() {

    }

    public TerminalStorageIngredientOpenCraftingJobAmountGuiPacket(CraftingOptionGuiData<T, M> craftingOptionData) {
        super(craftingOptionData);
    }

    @Override
    public void actionServer(World world, EntityPlayerMP player) {
        CraftingOptionGuiData<T, M> data = getCraftingOptionData();
        if (!data.isItem()) {
            IntegratedTerminals._instance.getGuiHandler().setTemporaryData(ExtendedGuiHandler.CRAFTING_OPTION,
                    Pair.of(data.getSide(), data)); // Pass the side as extra data to the gui
            BlockPos cPos = data.getPos();
            player.openGui(IntegratedTerminals._instance, GuiProviders.ID_GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT,
                    world, cPos.getX(), cPos.getY(), cPos.getZ());
        } else {
            IntegratedTerminals._instance.getGuiHandler().setTemporaryData(ExtendedGuiHandler.CRAFTING_OPTION_ITEM,
                    Pair.of(data.getItemIndex(), data)); // Pass the itemIndex as extra data to the gui
            player.openGui(IntegratedTerminals._instance, GuiProviders.ID_GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT_ITEM,
                    player.world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        }
    }
}