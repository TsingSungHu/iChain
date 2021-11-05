package com.qs.iChain.chain;

/**
 * @author TsingSungHu
 */
public interface ChainBuilder {

    /**
     * Build the processor chain.
     *
     * @return a processor that chain some processor together
     */
    ProcessorChain build();
}
