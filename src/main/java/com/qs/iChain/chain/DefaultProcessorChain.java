package com.qs.iChain.chain;

import com.qs.iChain.context.Context;

/**
 * @author TsingSungHu
 */
public class DefaultProcessorChain extends ProcessorChain {

    AbstractLinkedProcessor<?> first = new AbstractLinkedProcessor<Object>() {

        @Override
        public void entry(Context context, Object t, boolean prioritized, Object... args)
                throws Throwable {
            super.fireEntry(context, t, prioritized, args);
        }

        @Override
        public void exit(Context context, Object... args) {
            super.fireExit(context, args);
        }

    };
    AbstractLinkedProcessor<?> end = first;

    @Override
    public void addFirst(AbstractLinkedProcessor<?> protocolProcessor) {
        protocolProcessor.setNext(first.getNext());
        first.setNext(protocolProcessor);
        if (end == first) {
            end = protocolProcessor;
        }
    }

    @Override
    public void addLast(AbstractLinkedProcessor<?> protocolProcessor) {
        end.setNext(protocolProcessor);
        end = protocolProcessor;
    }

    /**
     * Same as {@link #addLast(AbstractLinkedProcessor)}.
     *
     * @param next processor to be added.
     */
    @Override
    public void setNext(AbstractLinkedProcessor<?> next) {
        addLast(next);
    }

    @Override
    public AbstractLinkedProcessor<?> getNext() {
        return first.getNext();
    }

    @Override
    public void entry(Context context, Object param, boolean prioritized, Object... args) throws Throwable {
            first.transformEntry(context, param, prioritized, args);
    }

    @Override
    public void exit(Context context, Object... args) {
        first.exit(context, args);
    }
}
