package teamport.aether.entity.boss;

import net.minecraft.core.entity.Mob;

import java.util.List;

public interface AetherBossList {
    List<Mob> aether$getBossList();

    void aether$TryAddBossList(Mob mob);
}
