package bta.aether.entity;

import java.util.List;

public interface IAetherPlayer {
    List<EntityAetherBossBase> aether$getBossList();

    int aether$getJumpMaxAmount() ;
    int aether$getJumpAmount();

    void aether$setJumpMaxAmount(int jumpMaxAmount);
    void aether$setJumpAmount(int jumpAmount);

}
