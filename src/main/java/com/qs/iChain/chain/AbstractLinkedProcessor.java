package com.qs.iChain.chain;

import com.qs.iChain.context.Context;

/**
 *  处理器链表抽象类
 * @author TsingSungHu
 */
public abstract class AbstractLinkedProcessor<T> implements Processor<T> {

    private AbstractLinkedProcessor<?> next = null;

    @Override
    public void fireEntry(Context context, Object obj, boolean prioritized, Object... args)
        throws Throwable {
        if (next != null) {
            next.transformEntry(context, obj, prioritized, args);
        }
    }

    @SuppressWarnings("unchecked")
    void transformEntry(Context context, Object o, boolean prioritized, Object... args)
        throws Throwable {
        T t = (T)o;
        entry(context, t, prioritized, args);
    }

    @Override
    public void fireExit(Context context, Object... args) {
        if (next != null) {
            next.exit(context, args);
        }
    }

    public AbstractLinkedProcessor<?> getNext() {
        return next;
    }

    public void setNext(AbstractLinkedProcessor<?> next) {
        this.next = next;
    }

}
