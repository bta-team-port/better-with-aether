package bta.aether.api;

import bta.aether.packet.PuffAerBunnyPacket;

public interface IAetherPacket {
    void aether$handlePuffBunny(PuffAerBunnyPacket packet);
}
