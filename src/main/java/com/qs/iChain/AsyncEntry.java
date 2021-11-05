package com.qs.iChain;

import com.qs.iChain.chain.Processor;
import com.qs.iChain.chain.ResourceWrapper;
import com.qs.iChain.context.Context;
import com.qs.iChain.context.NullContext;
import com.qs.iChain.exception.ErrorEntryFreeException;
import lombok.extern.slf4j.Slf4j;

/**
 * The entry for asynchronous resources.
 *
 * @author Eric Zhao
 * @since 0.2.0
 */
@Slf4j
public class AsyncEntry extends CtEntry {

    private Context asyncContext;

    AsyncEntry(ResourceWrapper resourceWrapper, Processor<Object> chain, Context context) {
        super(resourceWrapper, chain, context);
    }

    /**
     * Remove current entry from local context, but does not exit.
     */
    void cleanCurrentEntryInLocal() {
        if (context instanceof NullContext) {
            return;
        }
        Context originalContext = context;
        if (originalContext != null) {
            Entry curEntry = originalContext.getCurEntry();
            if (curEntry == this) {
                Entry parent = this.parent;
                originalContext.setCurEntry(parent);
                if (parent != null) {
                    ((CtEntry)parent).child = null;
                }
            } else {
                String curEntryName = curEntry == null ? "none"
                    : curEntry.resourceWrapper.getName() + "@" + curEntry.hashCode();
                String msg = String.format("Bad async context state, expected entry: %s, but actual: %s",
                    getResourceWrapper().getName() + "@" + hashCode(), curEntryName);
                throw new IllegalStateException(msg);
            }
        }
    }

    public Context getAsyncContext() {
        return asyncContext;
    }

    /**
     * The async context should not be initialized until the node for current resource has been set to current entry.
     */
    void initAsyncContext() {
        if (asyncContext == null) {
            if (context instanceof NullContext) {
                asyncContext = context;
                return;
            }
            this.asyncContext = Context.newAsyncContext(context.getEntranceNode(), context.getName())
                .setOrigin(context.getOrigin())
                .setCurEntry(this);
        } else {
            log.warn(
                "[AsyncEntry] Duplicate initialize of async context for entry: " + resourceWrapper.getName());
        }
    }

    @Override
    protected void clearEntryContext() {
        super.clearEntryContext();
        this.asyncContext = null;
    }

    @Override
    protected Entry trueExit(int count, Object... args) throws ErrorEntryFreeException {
        exitForContext(asyncContext, count, args);

        return parent;
    }
}
