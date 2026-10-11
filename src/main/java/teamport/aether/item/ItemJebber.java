package teamport.aether.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityActivator;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.material.ToolMaterial;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.LevelListener;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import net.minecraft.core.world.pos.TilePosc;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import teamport.aether.block.AetherBlockTags;
import teamport.aether.entity.DamageInstance;
import teamport.aether.entity.projectile.ProjectileRainBow;
import teamport.aether.mixin.accessors.EntityAccessor;

import java.util.List;
import java.util.Random;

import static teamport.aether.entity.DamageInstance.inst;

public class ItemJebber extends Item {
	private static final int MAXDAMAGE = 19;

	public ItemJebber(String translationKey, String namespaceId, int id, ToolMaterial material) {
		super(translationKey, namespaceId, id);
		this.maxStackSize = 1;
		this.setMaxDamage(material.getDurability());
	}

    @Override
	public float getStrVsBlock(@NotNull ItemStack itemstack, Block<?> block) {
		return block.hasTag(AetherBlockTags.BRIDLE) ? 100.0F : 1.5F;
	}

    @Override
    public @Nullable ItemStack onUse(@NotNull ItemStack selfStack, @NotNull World world, @NotNull Player player) {
        if (player.attackTime <= 0) {
            player.attackTime = 20;
            player.swingItem();
            player.world.playSoundAtEntity(player, player, "aether_battle:wand.laser.shot",
                0.4F + ((EntityAccessor)player).getRandom().nextFloat() * 0.2f,
                0.9F + ((EntityAccessor)player).getRandom().nextFloat() * 0.9f
            );
            return this.shootBeam(selfStack, world, player);
        }
        return selfStack;
    }

	private ItemStack shootBeam(ItemStack itemstack, World world, Player player) {
		Random random = world.rand;
		DamageInstance[] instances = this.setUpDamage(random);
		DamageInstance instance = instances[0];
        ProjectileRainBow rainBow = new ProjectileRainBow(world, player, instance);
        rainBow.setName(itemstack.getCustomName(), itemstack.getCustomColor());
        if (instances.length == 1) {
            world.entityJoinedWorld(rainBow);
			itemstack.damageItem(instance.getDamage(), player);
			return itemstack;
		}
        world.entityJoinedWorld(rainBow);
		itemstack.damageItem(1, player);
		return itemstack;
	}

	public DamageInstance[] setUpDamage(Random random) {
		int[] rolls = new int[5];
		int count = 1;
		rolls[0] = ItemJebber.rollDice(random);
		while (rolls[count - 1] > 19) {
			rolls[count] = ItemJebber.rollDice(random);
			count++;
		}
		DamageInstance[] instances = new DamageInstance[count];
		List<DamageType> damageTypes = DamageType.values();
		for (int c = 0; c < count; c++) {
			instances[c] = inst(rolls[c], damageTypes.get(random.nextInt(damageTypes.size())));
		}
		return instances;
	}

	public static int rollDice(Random random) {
		return random.nextInt(MAXDAMAGE) + 1;
	}

    @Override
    public void onUseByActivator(
        @NotNull ItemStack selfStack,
        @NotNull World world, @NotNull TileEntityActivator activator,
        @NotNull Random random,
        @NotNull TilePosc blockPos, @NotNull Direction direction,
        double offX, double offY, double offZ
    ) {
        TilePosc nTilePos = blockPos.add(direction, new TilePos());
        Block<?> block = world.getBlockType(nTilePos);
        if (block.hasTag(AetherBlockTags.BRIDLE)) {
            world.setBlockTypeNotify(nTilePos, Blocks.AIR);
            world.playBlockEvent(nTilePos, LevelListener.EVENT_BLOCK_BREAK, block.id());
        }
    }

    @Override
    public boolean canHarvestBlock(@NotNull ItemStack selfStack, @NotNull Mob mob, @NotNull Block<?> block) {
        return false;
    }
}
