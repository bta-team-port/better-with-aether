package bta.aether.util;

import bta.aether.entity.EntityAerbunny;
import net.minecraft.core.entity.player.EntityPlayer;

public class PacketHandler {

    public static void handleAerBunnyPuffJump(EntityPlayer player, EntityAerbunny aerbunny) {
        float puffiness = aerbunny.puffiness;
        player.yd = 0.25;
    }
}
