package org.cyclops.integratedterminals.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.cyclops.cyclopscore.config.extendedconfig.ExtendedConfig;
import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.cyclopscore.helper.InventoryHelpers;
import org.cyclops.cyclopscore.helper.ItemStackHelpers;
import org.cyclops.cyclopscore.helper.MinecraftHelpers;
import org.cyclops.cyclopscore.item.ItemGui;
import org.cyclops.integrateddynamics.api.network.INetwork;
import org.cyclops.integrateddynamics.api.part.PartPos;
import org.cyclops.integrateddynamics.block.BlockCable;
import org.cyclops.integrateddynamics.core.helper.NetworkHelpers;
import org.cyclops.integrateddynamics.core.helper.PartHelpers;
import org.cyclops.integrateddynamics.core.part.PartTypes;
import org.cyclops.integrateddynamics.part.PartTypeConnectorOmniDirectional;
import org.cyclops.integratedterminals.client.gui.container.GuiTerminalStorage;
import org.cyclops.integratedterminals.inventory.container.ContainerTerminalStorage;

import javax.annotation.Nullable;

public class ItemTerminalStoragePortable extends ItemGui {


    public static ItemTerminalStoragePortable _instance;

    public static ItemTerminalStoragePortable getInstance() {
        return _instance;
    }

    public ItemTerminalStoragePortable(ExtendedConfig<ItemConfig> eConfig) {
        super(eConfig);
    }

    @Override
    public void openGuiForItemIndex(World world, EntityPlayer player, int itemIndex, EnumHand hand) {
        if (world.isRemote) {
            super.openGuiForItemIndex(world, player, itemIndex, hand);
        } else {
            ItemStack itemStack = InventoryHelpers.getItemFromIndex(player, itemIndex, this);

            int groupId = getGroupId(itemStack);
            if (groupId >= 0) {
                INetwork network = getNetworkFromItem(itemStack);
                if (network != null) {
                    super.openGuiForItemIndex(world, player, itemIndex, hand);
                } else {
                    player.sendMessage(new TextComponentTranslation("item.integratedterminals.terminal_storage_portable.status.invalid_network"));
                }
            } else {
                player.sendMessage(new TextComponentTranslation("item.integratedterminals.terminal_storage_portable.status.no_network"));
            }
        }
    }

    @Override
    public Class<? extends Container> getContainer() {
        return ContainerTerminalStorage.class;
    }

    @Override
    public Class<? extends GuiScreen> getGui() {
        return GuiTerminalStorage.class;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (!world.isRemote && player != null) {
            IBlockState blockState = world.getBlockState(pos);
            if (blockState.getBlock() instanceof BlockCable) {
                PartPos partPos = PartPos.of(world, pos, side);
                PartHelpers.PartStateHolder<?, ?> partStateHolder = PartHelpers.getPart(partPos);
                if (partStateHolder != null && partStateHolder.getPart() == PartTypes.CONNECTOR_OMNI) {
                    PartTypeConnectorOmniDirectional.State state = (PartTypeConnectorOmniDirectional.State) partStateHolder.getState();
                    setGroupId(player.getHeldItem(hand), state.getGroupId());
                    player.sendMessage(new TextComponentTranslation("item.integratedterminals.terminal_storage_portable.status.linked"));
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    public static void loadNamedInventory(ItemStack stack, String name, IInventory inventory) {
        if (stack.getTagCompound().hasKey("namedInventories")) {
            for (NBTBase listEntry : stack.getTagCompound().getTagList("namedInventories", Constants.NBT.TAG_COMPOUND)) {
                if (((NBTTagCompound) listEntry).getString("tabName").equals(name)) {
                    NonNullList<ItemStack> list = NonNullList.withSize(((NBTTagCompound) listEntry).getInteger("itemCount"), ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems((NBTTagCompound) listEntry, list);
                    for (int i = 0; i < list.size(); i++) {
                        inventory.setInventorySlotContents(i, list.get(i));
                    }
                }
            }
        }
    }

    @Nullable
    public static NonNullList<ItemStack> getNamedInventory(ItemStack stack, String name) {
        if (stack.getTagCompound().hasKey("namedInventories")) {
            for (NBTBase listEntry : stack.getTagCompound().getTagList("namedInventories", Constants.NBT.TAG_COMPOUND)) {
                if (((NBTTagCompound) listEntry).getString("tabName").equals(name)) {
                    NonNullList<ItemStack> list = NonNullList.withSize(((NBTTagCompound) listEntry).getInteger("itemCount"), ItemStack.EMPTY);
                    ItemStackHelper.loadAllItems((NBTTagCompound) listEntry, list);
                    return list;
                }
            }
        }
        return null;
    }

    public static void setNamedInventory(ItemStack stack, String name, NonNullList<ItemStack> inventory) {
        NBTTagList list = new NBTTagList();
        NBTTagCompound listEntry = new NBTTagCompound();
        listEntry.setString("tabName", name);
        listEntry.setInteger("itemCount", inventory.size());
        ItemStackHelper.saveAllItems(listEntry, inventory);
        list.appendTag(listEntry);
        stack.getTagCompound().setTag("namedInventories", list);
    }

    public static void saveNamedInventory(ItemStack stack, String name, IInventory inventory) {
        NonNullList<ItemStack> latestItems = NonNullList.create();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            latestItems.add(inventory.getStackInSlot(i));
        }
        NBTTagList list = new NBTTagList();
        NBTTagCompound listEntry = new NBTTagCompound();
        listEntry.setString("tabName", name);
        listEntry.setInteger("itemCount", latestItems.size());
        ItemStackHelper.saveAllItems(listEntry, latestItems);
        list.appendTag(listEntry);
        stack.getTagCompound().setTag("namedInventories", list);
    }

    public static INetwork getNetworkFromItem(ItemStack itemStack) {
        if (MinecraftHelpers.isClientSide()) {
            return null;
        }
        int groupId = getGroupId(itemStack);
        if (groupId < 0) {
            return null;
        }
        for (PartPos pos : PartTypeConnectorOmniDirectional.LOADED_GROUPS.getPositions(groupId)) {
            INetwork network = NetworkHelpers.getNetwork(pos);
            if (network != null) {
                return network;
            }
        }
        return null;
    }

    public static void setGroupId(ItemStack itemStack, int groupId) {
        itemStack.setTagInfo("omnidir-group-key", new NBTTagInt(groupId));
    }

    public static int getGroupId(ItemStack itemStack) {
        if (itemStack != null && itemStack.hasTagCompound()) {
            if (itemStack.getTagCompound().hasKey("omnidir-group-key")) {
                return itemStack.getTagCompound().getInteger("omnidir-group-key");
            }
        }
        return -1;
    }
}
