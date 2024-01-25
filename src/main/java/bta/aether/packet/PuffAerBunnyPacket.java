package bta.aether.packet;

import bta.aether.api.IAetherPacket;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PuffAerBunnyPacket extends Packet {

    //DO not ERASE THIS EMPTY THINGS! OR YOUR GAME WILL CRASH
    public PuffAerBunnyPacket() {
    }

    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {
    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((IAetherPacket) netHandler).aether$handlePuffBunny(this);
    }

    @Override
    public int getPacketSize() {
        return 0;
    }
}