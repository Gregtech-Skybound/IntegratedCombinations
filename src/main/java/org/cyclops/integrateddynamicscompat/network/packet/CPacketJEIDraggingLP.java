package org.cyclops.integrateddynamicscompat.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.cyclops.cyclopscore.network.CodecField;
import org.cyclops.cyclopscore.network.PacketCodec;
import org.cyclops.integrateddynamics.inventory.container.ContainerLogicProgrammerBase;

public class CPacketJEIDraggingLP extends PacketCodec {
    @CodecField
    private int slotIndex;
    @CodecField
    private ItemStack itemStack;

    public CPacketJEIDraggingLP() {
    }

    public CPacketJEIDraggingLP(int slotIndex, ItemStack itemStack) {
        this.slotIndex = slotIndex;
        this.itemStack = itemStack;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public void actionClient(World world, EntityPlayer entityPlayer) {

    }

    @Override
    public void actionServer(World world, EntityPlayerMP player) {
        if (player.openContainer instanceof ContainerLogicProgrammerBase) {
            IInventory temporaryInputSlots = ((ContainerLogicProgrammerBase) player.openContainer).getTemporaryInputSlots();
            temporaryInputSlots.setInventorySlotContents(this.slotIndex, this.itemStack);
        }
    }
}
