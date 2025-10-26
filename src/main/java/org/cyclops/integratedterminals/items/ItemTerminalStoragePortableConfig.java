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
                "terminal_storage_portable",
                null,
                ItemTerminalStoragePortable.class
        );
    }

}
