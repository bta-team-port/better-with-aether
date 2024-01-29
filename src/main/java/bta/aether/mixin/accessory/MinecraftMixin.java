package bta.aether.mixin.accessory;

import bta.aether.accessory.API.HealthHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.world.chunk.ChunkCoordinates;
import net.minecraft.core.world.chunk.provider.IChunkProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = Minecraft.class, remap = false)
public class MinecraftMixin {

	@Inject(method = "respawn",at=@At("TAIL"),locals = LocalCapture.CAPTURE_FAILHARD)
	public void respawn(boolean flag, int i, CallbackInfo ci, EntityPlayer previousPlayer, ChunkCoordinates spawnCoordinates, ChunkCoordinates bedSpawnCoordinates, ChunkCoordinates lastDeathCoordinates, boolean canRespawnAtBed, IChunkProvider ichunkprovider, int j, Gamemode playerGamemode) {
		Minecraft thisAs = (Minecraft) (Object) this;
		HealthHelper.setExtraHealth(thisAs.thePlayer, HealthHelper.getExtraHealth(previousPlayer));
		thisAs.thePlayer.health = HealthHelper.getTotalHealth(thisAs.thePlayer);
	}
}
