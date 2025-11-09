package org.cyclops.integratedterminals.network.packet;

import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.integratedterminals.core.client.gui.CraftingOptionGuiData;
import org.cyclops.integratedterminals.core.terminalstorage.crafting.HandlerWrappedTerminalCraftingOption;
import org.cyclops.integratedterminals.core.terminalstorage.crafting.HandlerWrappedTerminalCraftingPlan;

import javax.annotation.Nullable;

/**
 * Packet for sending a storage slot click event from client to server.
 * @author rubensworks
 *
 */
public abstract class TerminalStorageIngredientCraftingOptionDataPacketAbstract<T, M> extends PacketCodec {

    @CodecField
    private String ingredientComponent;
    @CodecField
    private BlockPos pos;
    @CodecField
    private EnumFacing side;
    @Getter
    @CodecField
    private String tabName;
    @Getter
    @CodecField
    private int channel;
    @CodecField
    private NBTTagCompound craftingOption;
    @Getter
    @CodecField
    private int amount;
    @CodecField
    private NBTTagCompound craftingPlan;
    @CodecField
    private int itemIndex;
    @CodecField
    private boolean isItem;

    public TerminalStorageIngredientCraftingOptionDataPacketAbstract() {

    }

    public TerminalStorageIngredientCraftingOptionDataPacketAbstract(CraftingOptionGuiData<T, M> craftingOptionData) {
        if (craftingOptionData.isItem()) {
            this.pos = new BlockPos(1, 1, 1);
            this.side = EnumFacing.NORTH;
        } else {
            this.pos = craftingOptionData.getPos();
            this.side = craftingOptionData.getSide();
        }
        this.isItem = craftingOptionData.isItem();
        this.itemIndex = craftingOptionData.getItemIndex();
        this.ingredientComponent = craftingOptionData.getComponent().getName().toString();
        this.tabName = craftingOptionData.getTabName();
        this.channel = craftingOptionData.getChannel();
        this.craftingOption = craftingOptionData.getCraftingOption() != null
                ? HandlerWrappedTerminalCraftingOption.serialize(craftingOptionData.getCraftingOption())
                : new NBTTagCompound();
        this.amount = craftingOptionData.getAmount();
        this.craftingPlan = craftingOptionData.getCraftingPlan() != null
                ? HandlerWrappedTerminalCraftingPlan.serialize(craftingOptionData.getCraftingPlan())
                : new NBTTagCompound();
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void actionClient(World world, EntityPlayer player) {

    }

    @Nullable
    protected HandlerWrappedTerminalCraftingOption<T> getCraftingOption(IngredientComponent<T, M> ingredientComponent) {
        try {
            return HandlerWrappedTerminalCraftingOption.deserialize(ingredientComponent, this.craftingOption);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Nullable
    protected HandlerWrappedTerminalCraftingPlan getCraftingPlan() {
        try {
            return HandlerWrappedTerminalCraftingPlan.deserialize(this.craftingPlan);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public IngredientComponent<T, M> getIngredientComponent() {
        IngredientComponent<?, ?> component = IngredientComponent.REGISTRY.getValue(new ResourceLocation(ingredientComponent));
        if (component == null) {
            throw new IllegalArgumentException("Could not find the ingredient component type " + ingredientComponent);
        }
        return (IngredientComponent<T, M>) component;
    }

    public CraftingOptionGuiData<T, M> getCraftingOptionData() {
        IngredientComponent<T, M> ingredientComponent = getIngredientComponent();
        if (this.isItem) {
            return new CraftingOptionGuiData<>(itemIndex, ingredientComponent, tabName, channel,
                    getCraftingOption(ingredientComponent), amount, getCraftingPlan());
        }
        return new CraftingOptionGuiData<>(pos, side, ingredientComponent, tabName, channel,
                getCraftingOption(ingredientComponent), amount, getCraftingPlan());
    }
}