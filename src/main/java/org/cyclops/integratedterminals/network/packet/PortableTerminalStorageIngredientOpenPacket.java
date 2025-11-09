package org.cyclops.integratedterminals.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.integratedterminals.IntegratedTerminals;
import org.cyclops.integratedterminals.core.client.gui.ExtendedGuiHandler;
import org.cyclops.integratedterminals.inventory.container.ContainerTerminalStorage;
import org.cyclops.integratedterminals.proxy.guiprovider.GuiProviders;

public class PortableTerminalStorageIngredientOpenPacket extends PacketCodec {

    @CodecField
    private int itemIndex;
    @CodecField
    private String tabName;
    @CodecField
    private int channel;

    public PortableTerminalStorageIngredientOpenPacket() {

    }

    public PortableTerminalStorageIngredientOpenPacket(int itemIndex, String tabName, int channel) {
        this.itemIndex = itemIndex;
        this.tabName = tabName;
        this.channel = channel;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void actionClient(World world, EntityPlayer player) {

    }

    @Override
    public void actionServer(World world, EntityPlayerMP player) {
        IntegratedTerminals._instance.getGuiHandler().setTemporaryData(ExtendedGuiHandler.TERMINAL_STORAGE_ITEM,
                Pair.of(itemIndex, new ContainerTerminalStorage.InitTabData(tabName, channel)));
        player.openGui(IntegratedTerminals._instance, GuiProviders.ID_GUI_TERMINAL_STORAGE_INIT_ITEM,
                world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
    }

    public static void send(int itemIndex, String tabName, int channel) {
        IntegratedTerminals._instance.getGuiHandler().setTemporaryData(ExtendedGuiHandler.TERMINAL_STORAGE_ITEM,
                Pair.of(itemIndex, new ContainerTerminalStorage.InitTabData(tabName, channel)));
        IntegratedTerminals._instance.getPacketHandler().sendToServer(
                new PortableTerminalStorageIngredientOpenPacket(itemIndex, tabName, channel));
    }
}
