package com.qs.iChain.constants;

import com.qs.iChain.node.DefaultNode;
import com.qs.iChain.node.EntranceNode;

/**
 * Universal constants.
 *
 * @author TsingSungHu
 */
public final class Constants {

    public final static int MAX_CONTEXT_NAME_SIZE = 2000;
    public final static int MAX_CHAIN_SIZE = 6000;

    public final static String ROOT_ID = "machine-root";
    public final static String CONTEXT_DEFAULT_NAME = "default_context";

    /**
     * A virtual resource identifier for total inbound statistics (since 1.5.0).
     */
    public final static String TOTAL_IN_RESOURCE_NAME = "__total_inbound_traffic__";

    /**
     * A virtual resource identifier for cpu usage statistics (since 1.6.1).
     */
    public final static String CPU_USAGE_RESOURCE_NAME = "__cpu_usage__";

    /**
     * A virtual resource identifier for system load statistics (since 1.6.1).
     */
    public final static String SYSTEM_LOAD_RESOURCE_NAME = "__system_load__";

    /**
     * Global ROOT statistic node that represents the universal parent node.
     */
    public final static DefaultNode ROOT = new EntranceNode(ROOT_ID);

    /**
     * Global statistic node for inbound traffic.
     */
    public final static DefaultNode ENTRY_NODE = new EntranceNode((TOTAL_IN_RESOURCE_NAME));

    /**
     * The global switch .
     */
    public static volatile boolean ON = true;

    /**
     * Order of default processor
     */
    public static final int ORDER_FIRST = -10000;
    public static final int ORDER_SECOND = -9000;
    public static final int ORDER_THIRD = -8000;
    public static final int ORDER_FOUR = -7000;
    public static final int ORDER_FIVE = -6000;
    public static final int ORDER_SIX = -5000;
    public static final int ORDER_SEVEN = -2000;
    public static final int ORDER_EIGHT = -1000;

    private Constants() {}
}
