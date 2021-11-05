package com.qs.iChain.Handler;

import com.qs.iChain.chain.AbstractLinkedProcessor;
import com.qs.iChain.constants.Constants;
import com.qs.iChain.context.Context;
import com.qs.iChain.spi.Spi;
import lombok.extern.slf4j.Slf4j;

/**
 * @author TsingSungHu
 */
@Slf4j
@Spi(order = Constants.ORDER_SECOND)
public class SecondHandler extends AbstractLinkedProcessor<Object> {

    @Override
    public void entry(Context context, Object o, boolean prioritized, Object... args) throws Throwable {
        log.info("-----------SecondHandler entry-----------");
        fireEntry(context, o, prioritized, args);
    }

    @Override
    public void exit(Context context, Object... args) {
        log.info("-----------SecondHandler exit-----------");
        fireExit(context, args);
    }
}
