package com.qs.iChain.chain;

import com.qs.iChain.spi.Spi;
import com.qs.iChain.spi.SpiLoader;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Builder for a default {@link ProcessorChain}.
 *
 * @author TsingSungHu
 */
@Slf4j
@Spi(isDefault = true)
public class DefaultChainBuilder implements ChainBuilder {

    @Override
    public ProcessorChain build() {
        ProcessorChain chain = new DefaultProcessorChain();

        List<Processor> sortedProcessorList = SpiLoader.of(Processor.class).loadInstanceListSorted();
        for (Processor processor : sortedProcessorList) {
            if (!(processor instanceof AbstractLinkedProcessor)) {
                log.warn("The Processor(" + processor.getClass().getCanonicalName() + ") is not an instance of AbstractLinkedProcessor, can't be added into ProcessorChain");
                continue;
            }

            chain.addLast((AbstractLinkedProcessor<?>) processor);
        }

        return chain;
    }
}
