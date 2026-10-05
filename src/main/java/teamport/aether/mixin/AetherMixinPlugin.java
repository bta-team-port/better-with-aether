package teamport.aether.mixin;

import org.jspecify.annotations.NonNull;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class AetherMixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage){/* no need */}

    @Override
    public String getRefMapperConfig(){return null;}

    @Override
    public boolean shouldApplyMixin(
        String targetClassName, String mixinClassName
    ){return true;}

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {/* no need */}

    @Override
    public List<String> getMixins() {return Collections.emptyList();}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {/* no need */}

    @Override
    public void preApply(@NonNull String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if (targetClassName.replace('.', '/').equals("turniplabs/halplibe/helper/network/NetworkHandler")
                && mixinClassName.endsWith("NetworkHandlerMixinFixNativePackets")) {
            addCanReceiveNativePackets(targetClass);
        }
    }

    private static void addCanReceiveNativePackets(@NonNull ClassNode classNode) {
        for (MethodNode m : classNode.methods) {
            if ("canReceiveNativePackets".equals(m.name)) return;
        }
        MethodNode method = new MethodNode(
            Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,
            "canReceiveNativePackets",
            "(Lnet/minecraft/core/entity/player/Player;)Z",
            null,
            null
        );
        InsnList insns = new InsnList();
        insns.add(new InsnNode(Opcodes.ICONST_0));
        insns.add(new InsnNode(Opcodes.IRETURN));
        method.instructions = insns;
        method.maxStack = 1;
        method.maxLocals = 1;
        classNode.methods.add(method);
    }
}
