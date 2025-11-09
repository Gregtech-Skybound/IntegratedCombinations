package org.cyclops.integratedterminals.core.client.gui;

import lombok.Getter;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.cyclops.commoncapabilities.api.ingredient.IngredientComponent;
import org.cyclops.integratedterminals.core.terminalstorage.crafting.HandlerWrappedTerminalCraftingOption;
import org.cyclops.integratedterminals.core.terminalstorage.crafting.HandlerWrappedTerminalCraftingPlan;

import javax.annotation.Nullable;

/**
 * @author rubensworks
 */
@Getter
public class CraftingOptionGuiData<T, M> {

    private final BlockPos pos;
    private final EnumFacing side;
    private final IngredientComponent<T, M> component;
    private final String tabName;
    private final int channel;
    @Nullable
    private final HandlerWrappedTerminalCraftingOption<T> craftingOption;
    private final int amount;
    @Nullable
    private final HandlerWrappedTerminalCraftingPlan craftingPlan;
    private final int itemIndex;
    private final boolean isItem;

    public CraftingOptionGuiData(BlockPos pos, EnumFacing side, IngredientComponent<T, M> component, String tabName,
                                 int channel, @Nullable HandlerWrappedTerminalCraftingOption<T> craftingOption,
                                 int amount, HandlerWrappedTerminalCraftingPlan craftingPlan) {
        this.pos = pos;
        this.side = side;
        this.component = component;
        this.tabName = tabName;
        this.channel = channel;
        this.craftingOption = craftingOption;
        this.amount = amount;
        this.craftingPlan = craftingPlan;
        this.itemIndex = -1;
        this.isItem = false;
    }

    public CraftingOptionGuiData(int itemIndex, IngredientComponent<T, M> component, String tabName,
                                 int channel, @Nullable HandlerWrappedTerminalCraftingOption<T> craftingOption,
                                 int amount, HandlerWrappedTerminalCraftingPlan craftingPlan) {
        this.pos = null;
        this.side = null;
        this.component = component;
        this.tabName = tabName;
        this.channel = channel;
        this.craftingOption = craftingOption;
        this.amount = amount;
        this.craftingPlan = craftingPlan;
        this.itemIndex = itemIndex;
        this.isItem = true;
    }

    public static <T, M> CraftingOptionGuiData<T, M> copyWithAmount(CraftingOptionGuiData<T, M> craftingOptionGuiData, int amount) {
        if (craftingOptionGuiData.isItem()) {
            return new CraftingOptionGuiData<>(
                    craftingOptionGuiData.getItemIndex(),
                    craftingOptionGuiData.getComponent(),
                    craftingOptionGuiData.getTabName(),
                    craftingOptionGuiData.getChannel(),
                    craftingOptionGuiData.getCraftingOption(),
                    amount,
                    craftingOptionGuiData.getCraftingPlan()
            );
        }
        return new CraftingOptionGuiData<>(
                craftingOptionGuiData.getPos(),
                craftingOptionGuiData.getSide(),
                craftingOptionGuiData.getComponent(),
                craftingOptionGuiData.getTabName(),
                craftingOptionGuiData.getChannel(),
                craftingOptionGuiData.getCraftingOption(),
                amount,
                craftingOptionGuiData.getCraftingPlan()
        );
    }
}
