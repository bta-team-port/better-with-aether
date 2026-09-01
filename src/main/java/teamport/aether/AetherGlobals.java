package teamport.aether;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Important, do not reference game classes here!
 * Keep class specific constant where they belong.
 *
 *<p> Also yes the LOGGER belong here, no do not move it back!
 * The logger need to be accessible at any point in the initialization including before
 * the game classes are loaded such as blocks or items.
 * The game will crash if the logger load any classes before they are properly initialized!
 * */
public class AetherGlobals {
    // DO NOT EVEN THINK OF MOVING IT
    public static final Logger LOGGER = LoggerFactory.getLogger(AetherMod.MOD_ID);

    public static final String UUID_LUKEISSTUFF = "db7db941-6923-4855-a879-1ae655c16122";
    public static final String UUID_OLYPOLYU = "d561a5ee-57df-491d-80ea-784251df4bef";
    public static final String UUID_TOCININ = "4f419f3d-c2b0-41de-92bb-9740e43b640d";
    public static final String UUID_REDART15 = "3da8c87f-1845-455c-b91f-7e9ee8f4c0ec";
    public static final String UUID_SMUSHYTACO = "c6d2219b-c8a5-4ccd-a816-5328b2b32653";
    public static final String UUID_TOUFOUMASTER = "61ca7ec2-322f-4f95-b0d5-a47d608b4934";
    public static final String UUID_RIN = "337409a3-79c1-442f-913b-7e5b54d1ee9d";


    private AetherGlobals(){}
}
