package org.cyclops.integratedterminals.items;

import org.cyclops.cyclopscore.config.extendedconfig.ItemConfig;
import org.cyclops.integratedterminals.IntegratedTerminals;

public class ItemTerminalStoragePortableConfig extends ItemConfig {

    /**
     * The unique instance.
     */
    public static ItemTerminalStoragePortableConfig _instance;

    /**
     * Make a new instance.
     */
    public ItemTerminalStoragePortableConfig() {
        super(
                IntegratedTerminals._instance,
                true,
                "portable_storage_terminal",
                null,
                ItemTerminalStoragePortable.class
        );
    }

}
