package org.cyclops.integratedtunnels.part;

import com.google.common.collect.Iterators;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.cyclops.commoncapabilities.IngredientComponents;
import org.cyclops.commoncapabilities.api.capability.itemhandler.ISlotlessItemHandler;
import org.cyclops.cyclopscore.ingredient.collection.FilteredIngredientCollectionIterator;
import org.cyclops.integratedtunnels.Capabilities;
import org.cyclops.integratedtunnels.GeneralConfig;
import org.cyclops.integratedtunnels.api.network.IItemNetwork;
import org.cyclops.integratedtunnels.capability.network.ItemNetworkConfig;
import org.cyclops.integratedtunnels.core.part.PartTypeInterfacePositionedAddon;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * Interface for item handlers.
 * @author rubensworks
 */
public class PartTypeInterfaceItem extends PartTypeInterfacePositionedAddon<IItemNetwork, IItemHandler, PartTypeInterfaceItem, PartTypeInterfaceItem.State> {
    public PartTypeInterfaceItem(String name) {
        super(name);
    }

    @Override
    protected Capability<IItemNetwork> getNetworkCapability() {
        return ItemNetworkConfig.CAPABILITY;
    }

    @Override
    protected Capability<IItemHandler> getTargetCapability() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    protected PartTypeInterfaceItem.State constructDefaultState() {
        return new PartTypeInterfaceItem.State();
    }
    
    @Override
    public int getConsumptionRate(State state) {
        return GeneralConfig.interfaceItemBaseConsumption;
    }

    public static class State extends PartTypeInterfacePositionedAddon.State<PartTypeInterfaceItem, IItemNetwork, IItemHandler> implements IItemHandler, ISlotlessItemHandler {

        @Override
        protected Capability<IItemHandler> getTargetCapability() {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
        }

        protected IItemHandler getItemHandler() {
            return getPositionedAddonsNetwork().getChannelExternal(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, getChannel());
        }

        @Override
        public int getSlots() {
            if (!isNetworkAndPositionValid()) {
                return 0;
            }
            disablePosition();
            int ret = getItemHandler().getSlots();
            enablePosition();
            return ret;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            if (!isNetworkAndPositionValid()) {
                return ItemStack.EMPTY;
            }
            disablePosition();
            ItemStack ret = getItemHandler().getStackInSlot(slot);
            enablePosition();
            return ret;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (!isNetworkAndPositionValid()) {
                return stack;
            }
            disablePosition();
            ItemStack ret = getItemHandler().insertItem(slot, stack, simulate);
            enablePosition();
            return ret;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!isNetworkAndPositionValid()) {
                return ItemStack.EMPTY;
            }
            disablePosition();
            ItemStack ret = getItemHandler().extractItem(slot, amount, simulate);
            enablePosition();
            return ret;
        }

        @Override
        public int getSlotLimit(int slot) {
            if (!isNetworkAndPositionValid()) {
                return 0;
            }
            disablePosition();
            int ret = getItemHandler().getSlotLimit(slot);
            enablePosition();
            return ret;
        }

        @Override
        public Iterator<ItemStack> getItems() {
            if (!isNetworkAndPositionValid()) {
                return Iterators.forArray();
            }
            disablePosition();
            Iterator<ItemStack> ret;
            if (!this.getPositionedAddonsNetwork().getChannel(this.getChannelInterface()).iterator().hasNext()) {
                // If the target is empty, we can safely forward this call to our index.
                ret = this.getPositionedAddonsNetwork().getChannel(this.getChannelInterface()).iterator();
            } else {
                // If the target is not empty, iterate over all positions except for the target to determine items.
                // If we would not do this, this would result in duplication of index contents, see CyclopsMC/IntegratedTerminals#109
                IItemNetwork network = this.getPositionedAddonsNetwork();
                ret = Iterators.concat(network.getPositions(this.getChannel()).stream()
                        .filter(pos -> !network.isPositionDisabled(pos))
                        .map(network::getRawInstances)
                        .toArray(Iterator[]::new));
            }
            enablePosition();
            return ret;
        }

        @Override
        public Iterator<ItemStack> findItems(@Nonnull ItemStack stack, int matchFlags) {
            if (!isNetworkAndPositionValid()) {
                return Iterators.forArray();
            }
            disablePosition();
            Iterator<ItemStack> ret;
            if (!this.getPositionedAddonsNetwork().getChannel(this.getChannelInterface()).iterator(stack, matchFlags).hasNext()) {
                // If the target is empty, we can safely forward this call to our index.
                ret = this.getPositionedAddonsNetwork().getChannel(this.getChannelInterface()).iterator(stack, matchFlags);
            } else {
                // If the target is not empty, iterate over all positions except for the target to determine items.
                // If we would not do this, this would result in duplication of index contents, see CyclopsMC/IntegratedTerminals#109
                ret = new FilteredIngredientCollectionIterator<>(getItems(), IngredientComponents.ITEMSTACK.getMatcher(), stack, matchFlags);
            }
            enablePosition();
            return ret;
        }

        @Override
        public ItemStack insertItem(ItemStack stack, boolean simulate) {
            if (!isNetworkAndPositionValid()) {
                return stack;
            }
            disablePosition();
            ItemStack ret = getPositionedAddonsNetwork().getChannel(getChannelInterface()).insert(stack, simulate);
            enablePosition();
            return ret;
        }

        @Override
        public ItemStack extractItem(int amount, boolean simulate) {
            if (!isNetworkAndPositionValid()) {
                return ItemStack.EMPTY;
            }
            disablePosition();
            ItemStack ret = getPositionedAddonsNetwork().getChannel(getChannelInterface()).extract(amount, simulate);
            enablePosition();
            return ret;
        }

        @Override
        public ItemStack extractItem(ItemStack matchStack, int matchFlags, boolean simulate) {
            if (!isNetworkAndPositionValid()) {
                return ItemStack.EMPTY;
            }
            disablePosition();
            ItemStack ret = getPositionedAddonsNetwork().getChannel(getChannelInterface()).extract(matchStack, matchFlags, simulate);
            enablePosition();
            return ret;
        }

        @Override
        public int getLimit() {
            if (!isNetworkAndPositionValid()) {
                return 0;
            }
            disablePosition();
            int limit = 0;
            IItemHandler itemHandler = getItemHandler();
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                limit += itemHandler.getSlotLimit(i);
            }
            enablePosition();
            return limit;
        }

        @Override
        public boolean hasCapability(Capability<?> capability) {
            return (isNetworkAndPositionValid() && capability == Capabilities.SLOTLESS_ITEMHANDLER)
                    || super.hasCapability(capability);
        }

        @Override
        public <T> T getCapability(Capability<T> capability) {
            if (isNetworkAndPositionValid() && capability == Capabilities.SLOTLESS_ITEMHANDLER) {
                return (T) this;
            }
            return super.getCapability(capability);
        }
    }
}
