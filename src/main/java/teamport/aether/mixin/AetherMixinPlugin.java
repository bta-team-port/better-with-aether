package teamport.aether.mixin;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class AetherMixinPlugin implements IMixinConfigPlugin {

    @Override public void onLoad(String mixinPackage) {}
    @Override public String getRefMapperConfig() { return null; }
    @Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) { return true; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
    @Override public List<String> getMixins() { return null; }
    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if (targetClassName.replace('.', '/').equals("turniplabs/halplibe/helper/network/NetworkHandler")
                && mixinClassName.endsWith("NetworkHandlerMixinFixNativePackets")) {
            addCanReceiveNativePackets(targetClass);
        }
    }

    private static void addCanReceiveNativePackets(ClassNode classNode) {
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
