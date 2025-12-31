package teamport.aether.helper;

import org.lwjgl.opengl.GL11;

import java.util.ArrayDeque;

public class GLManager {
    private static final ArrayDeque<Integer> intStack = new ArrayDeque<>();

    public static void glEnable(int target) {
        intStack.push(target);
        intStack.push(GL11.glGetInteger(target));
        GL11.glEnable(target);
    }

    public static void glDisable(int target) {
        intStack.push(target);
        intStack.push(GL11.glGetInteger(target));
        GL11.glDisable(target);
    }

    public static void restore() {
        while (!intStack.isEmpty()) {
            int state = intStack.pop();
            if (state == 1) {
                GL11.glEnable(intStack.pop());
            } else if (state == 0) {
                GL11.glDisable(intStack.pop());
            }
        }
    }
}
