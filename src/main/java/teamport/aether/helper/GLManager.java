package teamport.aether.helper;

import org.lwjgl.opengl.GL11;

import java.util.Stack;

public class GLManager {
    static Stack<Integer> intStack = new Stack<>();

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
            switch (intStack.pop()) {
                case 1: {
                    GL11.glEnable(intStack.pop());
                    break;
                }
                case 0: {
                    GL11.glDisable(intStack.pop());
                    break;
                }
            }
        }
    }
}
