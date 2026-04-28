package org.cyclops.integrateddynamicscompat.modcompat.jei.logicprogrammer;

import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.cyclops.cyclopscore.inventory.SimpleInventory;
import org.cyclops.integrateddynamics.client.gui.GuiLogicProgrammerBase;
import org.cyclops.integrateddynamicscompat.IntegratedDynamicsCompat;
import org.cyclops.integrateddynamicscompat.network.packet.CPacketJEIDraggingLP;
import org.cyclops.integrateddynamicscompat.network.packet.CPacketSetSlot;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LogicProgrammerGhostIngredientHandler<T extends GuiLogicProgrammerBase> implements IGhostIngredientHandler<T> {

    @Override
    public <I> @Nonnull List<Target<I>> getTargets(@Nonnull T gui, @Nonnull I ingredient, boolean doStart) {
        List<Target<I>> targets = new ArrayList<>();
        if (ingredient instanceof ItemStack || ingredient instanceof FluidStack) {
            int size = gui.getContainer().inventorySlots.size();
            for (int i = 4; i < size; i++) {
                Slot slot = gui.getContainer().inventorySlots.get(i);
                if (slot.inventory instanceof SimpleInventory) {
                    targets.add(new IGhostIngredientHandler.Target<>() {
                        @Override
                        public @Nonnull Rectangle getArea() {
                            return new Rectangle(gui.getGuiLeft() + slot.xPos, gui.getGuiTop() + slot.yPos, 16, 16);
                        }

                        @Override
                        public void accept(@Nonnull I ingredient) {
                            if (ingredient instanceof ItemStack) {
                                IntegratedDynamicsCompat._instance.getPacketHandler().sendToServer(
                                        new CPacketJEIDraggingLP(slot.getSlotIndex(), (ItemStack) ingredient));
                            } else if (ingredient instanceof FluidStack) {
                                ItemStack s = FluidUtil.getFilledBucket((FluidStack) ingredient);
                                IntegratedDynamicsCompat._instance.getPacketHandler().sendToServer(
                                        new CPacketJEIDraggingLP(slot.getSlotIndex(), s));
                            }
                        }
                    });
                }
            }
        }

        return targets;
    }

    @Override
    public void onComplete() {

    }
}
