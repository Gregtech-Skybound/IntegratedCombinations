package org.cyclops.integratedterminals.proxy.guiprovider;

import org.cyclops.cyclopscore.helper.Helpers;
import org.cyclops.cyclopscore.inventory.IGuiContainerProvider;
import org.cyclops.integratedterminals.IntegratedTerminals;
import org.cyclops.integratedterminals.core.client.gui.ExtendedGuiHandler;

/**
 * @author rubensworks
 */
public class GuiProviders {

    // Part
    public static int ID_GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT;
    public static IGuiContainerProvider GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT;
    public static int ID_GUI_TERMINAL_STORAGE_CRAFTNG_PLAN;
    public static IGuiContainerProvider GUI_TERMINAL_STORAGE_CRAFTNG_PLAN;
    public static int ID_GUI_TERMINAL_CRAFTING_JOBS_PLAN;
    public static IGuiContainerProvider GUI_TERMINAL_CRAFTING_JOBS_PLAN;

    // Item
    public static int ID_GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT_ITEM;
    public static IGuiContainerProvider GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT_ITEM;
    public static int ID_GUI_TERMINAL_STORAGE_CRAFTNG_PLAN_ITEM;
    public static IGuiContainerProvider GUI_TERMINAL_STORAGE_CRAFTNG_PLAN_ITEM;
    public static int ID_GUI_TERMINAL_CRAFTING_JOBS_PLAN_ITEM;
    public static IGuiContainerProvider GUI_TERMINAL_CRAFTING_JOBS_PLAN_ITEM;

    /**
     * This is a variant of the default terminal storage gui constructor (which is register by ID).
     * This alternative allows additional init data to be passed to the constructor.
     */
    public static int ID_GUI_TERMINAL_STORAGE_INIT;
    public static int ID_GUI_TERMINAL_STORAGE_INIT_ITEM;


    public static void register() {
        // Part
        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT = new GuiProviderTerminalStorageCraftingOptionAmount(
                        ID_GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.CRAFTING_OPTION);

        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                GUI_TERMINAL_STORAGE_CRAFTNG_PLAN = new GuiProviderTerminalStorageCraftingPlan(
                        ID_GUI_TERMINAL_STORAGE_CRAFTNG_PLAN = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.CRAFTING_OPTION);

        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                new GuiProviderTerminalStorageInit(
                        ID_GUI_TERMINAL_STORAGE_INIT = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.TERMINAL_STORAGE);

        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                GUI_TERMINAL_CRAFTING_JOBS_PLAN = new GuiProviderTerminalCraftingJobsPlan(
                        ID_GUI_TERMINAL_CRAFTING_JOBS_PLAN = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.CRAFTING_PLAN);

        // Item
        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT_ITEM = new GuiProviderTerminalStorageCraftingOptionAmount(
                        ID_GUI_TERMINAL_STORAGE_CRAFTNG_OPTION_AMOUNT_ITEM = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.CRAFTING_OPTION_ITEM);

        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                GUI_TERMINAL_STORAGE_CRAFTNG_PLAN_ITEM = new GuiProviderTerminalStorageCraftingPlan(
                        ID_GUI_TERMINAL_STORAGE_CRAFTNG_PLAN_ITEM = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.CRAFTING_OPTION_ITEM);

        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                new GuiProviderTerminalStorageInit(
                        ID_GUI_TERMINAL_STORAGE_INIT_ITEM = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.TERMINAL_STORAGE_ITEM);

        IntegratedTerminals._instance.getGuiHandler().registerGUI(
                GUI_TERMINAL_CRAFTING_JOBS_PLAN_ITEM = new GuiProviderTerminalCraftingJobsPlan(
                        ID_GUI_TERMINAL_CRAFTING_JOBS_PLAN_ITEM = Helpers.getNewId(IntegratedTerminals._instance, Helpers.IDType.GUI),
                        IntegratedTerminals._instance), ExtendedGuiHandler.CRAFTING_PLAN_ITEM);

    }

}
