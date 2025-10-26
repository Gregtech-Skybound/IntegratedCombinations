package org.cyclops.integratedcrafting;

import org.cyclops.integrateddynamics.Tags;

/**
 * Class that can hold basic static things that are better not hard-coded
 * like mod details, texture paths, ID's...
 * @author rubensworks (aka kroeserr)
 *
 */
@SuppressWarnings("javadoc")
public class Reference {
	
    // Mod info
    public static final String MOD_ID = "integratedcrafting";
    public static final String MOD_NAME = "Integrated Crafting";
    public static final String MOD_VERSION = Tags.VERSION;
    
    // Paths
    public static final String TEXTURE_PATH_GUI = "textures/gui/";
    public static final String TEXTURE_PATH_SKINS = "textures/skins/";
    public static final String TEXTURE_PATH_MODELS = "textures/models/";
    public static final String TEXTURE_PATH_ENTITIES = "textures/entities/";
    public static final String TEXTURE_PATH_GUIBACKGROUNDS = "textures/gui/title/background/";
    public static final String TEXTURE_PATH_ITEMS = "textures/items/";
    public static final String TEXTURE_PATH_PARTICLES = "textures/particles/";
    public static final String MODEL_PATH = "models/";
    
    // MOD ID's
    public static final String MOD_FORGE = "forge";
    public static final String MOD_FORGE_VERSION_MIN = "14.23.5.2768";
    public static final String MOD_CYCLOPSCORE = "cyclopscore";
    public static final String MOD_CYCLOPSCORE_VERSION_MIN = "1.6.2";
    public static final String MOD_INTEGRATEDDYNAMICS = "integrateddynamics";
    public static final String MOD_INTEGRATEDDYNAMICS_VERSION_MIN = "1.0.9";
    
    // Dependencies
    public static final String MOD_DEPENDENCIES =
            "required-after:" + MOD_FORGE              + "@[" + MOD_FORGE_VERSION_MIN       + ",);" +
            "required-after:" + MOD_CYCLOPSCORE        + "@[" + MOD_CYCLOPSCORE_VERSION_MIN + ",);" +
            "required-after:" + MOD_INTEGRATEDDYNAMICS + "@[" + MOD_INTEGRATEDDYNAMICS_VERSION_MIN + ",);";
}
