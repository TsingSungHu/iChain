package com.qs.iChain.chain;

/**
 * Link all processor as a chain.
 *
 * @author TsingSungHu
 */
public abstract class ProcessorChain extends AbstractLinkedProcessor<Object> {

    /**
     * Add a processor to the head of this chain.
     *
     * @param protocolProcessor processor to be added.
     */
    public abstract void addFirst(AbstractLinkedProcessor<?> protocolProcessor);

    /**
     * Add a processor to the tail of this chain.
     *
     * @param protocolProcessor processor to be added.
     */
    public abstract void addLast(AbstractLinkedProcessor<?> protocolProcessor);
}
