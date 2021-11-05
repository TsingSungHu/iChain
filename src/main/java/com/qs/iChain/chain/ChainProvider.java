package com.qs.iChain.chain;

import com.qs.iChain.spi.SpiLoader;
import lombok.extern.slf4j.Slf4j;

/**
 * A provider for creating chains via resolved chain builder SPI.
 *
 * @author TsingSungHu
 */
@Slf4j
public final class ChainProvider {

    private static volatile ChainBuilder ChainBuilder = null;

    /**
     * @return new created processor chain
     */
    public static ProcessorChain newProcessorChain() {
        if (ChainBuilder != null) {
            return ChainBuilder.build();
        }

        // Resolve the chain builder SPI.
        ChainBuilder = SpiLoader.of(ChainBuilder.class).loadFirstInstanceOrDefault();

        if (ChainBuilder == null) {
            // Should not go through here.
            log.warn("[ChainProvider] Wrong state when resolving chain builder, using default");
            ChainBuilder = new DefaultChainBuilder();
        } else {
            log.info("[ChainProvider] Global chain builder resolved: {}",
                ChainBuilder.getClass().getCanonicalName());
        }
        return ChainBuilder.build();
    }

    private ChainProvider() {}
}
