package teamport.aether.helper;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.renderer.BlendFactor;
import net.minecraft.client.render.renderer.DrawMode;
import net.minecraft.client.render.renderer.GLRenderer;
import net.minecraft.client.render.renderer.State;
import net.minecraft.client.render.tessellator.TessellatorGeneral;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobWolf;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.IArmorItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.item.material.ArmorMaterial;
import net.minecraft.core.player.inventory.container.ContainerInventory;
import net.minecraft.core.util.helper.Color;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pos.TilePos;
import org.joml.primitives.AABBdc;
import org.jspecify.annotations.NonNull;
import teamport.aether.block.AetherBlocks;
import teamport.aether.entity.player.PlayerUtil;
import teamport.aether.item.AetherArmorMaterial;
import teamport.aether.item.AetherItems;
import teamport.aether.mixins.mixin.accessors.EntityAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamport.aether.AetherMod.LOGGER;

public class MixinHelper {
    private MixinHelper() {
    }

    public static final int ANIMATION_LENGTH = 30;
    public static final Map<Integer, Integer> BLOCK_TO_BECOME = new HashMap<>();

    static {
        BLOCK_TO_BECOME.put(Blocks.PUMPKIN_CARVED_ACTIVE.id(), Blocks.PUMPKIN_CARVED_IDLE.id());
        BLOCK_TO_BECOME.put(Blocks.BRAZIER_ACTIVE.id(), Blocks.BRAZIER_INACTIVE.id());
        BLOCK_TO_BECOME.put(Blocks.PUMICE_WET.id(), Blocks.PUMICE_DRY.id());
        BLOCK_TO_BECOME.put(Blocks.FLUID_LAVA_FLOWING.id(), AetherBlocks.AEROGEL.id());
        BLOCK_TO_BECOME.put(Blocks.FLUID_LAVA_STILL.id(), AetherBlocks.AEROGEL.id());
    }

    public static final Map<Item, String> TRINKET_TEXTURES = new HashMap<>();

    static {
        TRINKET_TEXTURES.put(Items.TOOL_COMPASS, "compass_trinket");
        TRINKET_TEXTURES.put(Items.TOOL_CLOCK, "clock_trinket");
        TRINKET_TEXTURES.put(Items.TOOL_CALENDAR, "calendar_trinket");
        TRINKET_TEXTURES.put(AetherItems.TOOL_DUNGEON_COMPASS, "compass_dungeon_trinket");
    }

    public static int fireResistanceCount(ContainerInventory inventory) {
        return PlayerUtil.countArmorPiecesOfMaterial(inventory, AetherArmorMaterial.PHOENIX);
    }

    public static void damageArmourWithEffect(int damage, Player player, double x, double y, double z, float bbHeight, float bbWidth) {
        if (((EntityAccessor) player).getRandom().nextFloat() < (double) 0.05F) {
            player.damageArmor(damage);
            if (((EntityAccessor) player).getRandom().nextInt(6) == 0) {
                player.world.playSoundAtEntity(null, player, "random.fizz", 0.5F, 0.8F / (((EntityAccessor) player).getRandom().nextFloat() * 0.2F + 0.9F));
            }
        }
        ParticleMaker.spawnSmokeParticles(player.world, x, y, z, bbHeight, bbWidth);
    }

    public static boolean isImmuneToFire(@NonNull MobWolf mobWolf) {
        ItemStack armor = mobWolf.getArmorItem();
        if (armor == null || !(armor.getItem() instanceof IArmorItem)) return false;
        ArmorMaterial armorMaterial = ((IArmorItem<?>) armor.getItem()).getArmorMaterial();
        return armorMaterial != null && armorMaterial.equals(AetherArmorMaterial.PHOENIX);
    }

    public static boolean isBrokenAABB(@NonNull AABBdc aabb) {
        double diffX = Math.abs(aabb.maxX() - aabb.minX());
        double diffY = Math.abs(aabb.maxY() - aabb.minY());
        double diffZ = Math.abs(aabb.maxZ() - aabb.minZ());

        return diffX > 1_000_000
            || diffY > 1_000_000
            || diffZ > 1_000_000
            || Double.isNaN(diffX)
            || Double.isNaN(diffY)
            || Double.isNaN(diffZ);
    }

    public static <T> List<T> preventStupidShit(World world, Class<T> ofClass, Entity entity, AABBdc aabb, Operation<List<T>> original) {
        if (MixinHelper.isBrokenAABB(aabb)) {
            if (entity != null) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("{} is moving too fast. Entity at {} {} {} with speed {}! Please send this to a developer!",
                        Entity.getNameFromEntity(entity, true),
                        entity.x, entity.y, entity.z,
                        Math.sqrt(entity.xd * entity.xd + entity.yd * entity.yd + entity.zd * entity.zd)
                    );
                }
                Block<?> block = world.getBlockType(new TilePos((int) Math.round(entity.x), (int) Math.round(entity.y - 1), (int) Math.round(entity.z)));
                String name = block.getLanguageKey(0);
                LOGGER.error("Currently standing on: {} at ", name);
                LOGGER.error("Please send this log to a BWA developer!");
                Thread.dumpStack();
                entity.absMoveTo(0, 255, 0, 0f, 0f);
                entity.xo = 0;
                entity.yo = 0;
                entity.zo = 0;
            } else {
                LOGGER.error("Something is moving too fast! Please send this to a developer!");
                Thread.dumpStack();
            }
            return new ArrayList<>();
        }
        return entity != null ? original.call(entity, aabb) : original.call(ofClass, aabb);
    }

    public static int mixColor(int colorA, int colorB, float ratio) {
        int alphaA = Color.alphaFromInt(colorA);
        int redA = Color.redFromInt(colorA);
        int blueA = Color.blueFromInt(colorA);
        int greenA = Color.greenFromInt(colorA);
        int alphaB = Color.alphaFromInt(colorB);
        int redB = Color.redFromInt(colorB);
        int blueB = Color.blueFromInt(colorB);
        int greenB = Color.greenFromInt(colorB);

        int alphaRes = (int) (alphaA * ratio + alphaB * (1 - ratio));
        int redRes = (int) (redA * ratio + redB * (1 - ratio));
        int blueRes = (int) (blueA * ratio + blueB * (1 - ratio));
        int greenRes = (int) (greenA * ratio + greenB * (1 - ratio));

        return Color.intToIntARGB(alphaRes, redRes, blueRes, greenRes);
    }

    @Environment(EnvType.CLIENT)
    public static void renderShieldVignette(@NonNull TextureManager textureManager, int xSize, int ySize) {
        GLRenderer.pushFrame();
        try {
            GLRenderer.enableState(State.BLEND);
            GLRenderer.disableState(State.DEPTH_TEST);
            GLRenderer.setDepthMask(false);
            GLRenderer.setBlendFunc(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA);
            GLRenderer.setColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GLRenderer.setAlphaTest(0.0F);

            textureManager.loadTexture("/assets/aether/textures/other/shieldvignette.png").bind();

            TessellatorGeneral tessellator = GLRenderer.getTessellator();
            tessellator.startDrawing(DrawMode.QUADS);
            tessellator.addVertexWithUV(0.0, ySize, -90.0, 0.0, 1.0);
            tessellator.addVertexWithUV(xSize, ySize, -90.0, 1.0, 1.0);
            tessellator.addVertexWithUV(xSize, 0.0, -90.0, 1.0, 0.0);
            tessellator.addVertexWithUV(0.0, 0.0, -90.0, 0.0, 0.0);
            tessellator.draw();
        } finally {
            GLRenderer.popFrame();
        }
    }
}
