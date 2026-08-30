package teamport.aether.entity.boss;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.command.TextFormatting;
import turniplabs.halplibe.util.deathcause.vanilla.DeathCauseKilledBy;

import java.util.Random;

public class DeathCauseBoss extends DeathCauseKilledBy {
    private static final int MAX_VALUE = 9;
    private static final Random RANDOM = new Random();
    private String bossTile = "";
    private String bossName = "";
    private byte bossColor = 0;
    private byte mesageID = 0;


    public DeathCauseBoss(){
        super();
    }

    public DeathCauseBoss(Mob victim, Mob boss, EnemyBoss enemyBoss){
        super(victim, boss);
        this.bossName = enemyBoss.getBossName();
        this.bossTile = enemyBoss.getBossTitleKey();
        this.bossColor = enemyBoss.getBossColor();
        this.mesageID = (byte)RANDOM.nextInt(MAX_VALUE);
    }


    @Override
    public void serializeAdditional(CompoundTag tag) {
        tag.putString("aether:bossName", this.bossName);
        tag.putString("aether:bossTitle", this.bossTile);
        tag.putByte("aether:bossColor", this.bossColor);
        tag.putByte("aether:messageID", this.mesageID);
    }

    @Override
    public void deserializeAdditional(CompoundTag tag) {
        this.bossName = tag.getString("aether:bossName");
        this.bossTile = tag.getString("aether:bossTitle");
        this.bossColor = tag.getByte("aether:bossColor");
        this.mesageID = tag.getByte("aether:messageID");
    }

    @Override
    public String getQualifiedTranslationKey() {
        return super.getQualifiedTranslationKey() + "_" + this.mesageID;
    }


    @Override
    protected String format(String translatedKey) {
        String bossTitle = I18n.getInstance().translateKey(this.bossTile);
        String formatedBossTile = TextFormatting.get(this.bossColor).toString() + bossTitle.formatted(this.bossName);
        /*
            chat renderer does not copy the formating correctly when a message is a multi-line message
            this results in the format going missing. Until BTA fixes it all messages will need to restore the formating
            manually
        */
        return translatedKey.formatted(
            TextFormatting.scoped(this.victim.getValue()) + this.getTextFormattingBase(),
            TextFormatting.scoped(formatedBossTile) + this.getTextFormattingBase()
        );
    }
}
