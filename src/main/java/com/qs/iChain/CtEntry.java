package com.qs.iChain;

import com.qs.iChain.chain.Processor;
import com.qs.iChain.chain.ResourceWrapper;
import com.qs.iChain.context.Context;
import com.qs.iChain.context.NullContext;
import com.qs.iChain.exception.ChainException;
import com.qs.iChain.exception.ErrorEntryFreeException;
import com.qs.iChain.node.Node;
import com.qs.iChain.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.function.BiConsumer;

/**
 * Linked entry within current context.
 *
 * @author TsingSungHu
 */
@Slf4j
class CtEntry extends Entry {

    protected Entry parent = null;
    protected Entry child = null;

    protected Processor<Object> chain;
    protected Context context;
    protected LinkedList<BiConsumer<Context, Entry>> exitHandlers;

    CtEntry(ResourceWrapper resourceWrapper, Processor<Object> chain, Context context) {
        super(resourceWrapper);
        this.chain = chain;
        this.context = context;

        setUpEntryFor(context);
    }

    private void setUpEntryFor(Context context) {
        // The entry should not be associated to NullContext.
        if (context instanceof NullContext) {
            return;
        }
        this.parent = context.getCurEntry();
        if (parent != null) {
            ((CtEntry) parent).child = this;
        }
        context.setCurEntry(this);
    }

    @Override
    public void exit(int count, Object... args) throws ErrorEntryFreeException {
        trueExit(count, args);
    }

    /**
     * Note: the exit handlers will be called AFTER onExit of chain.
     */
    private void callExitHandlersAndCleanUp(Context ctx) {
        if (exitHandlers != null && !exitHandlers.isEmpty()) {
            for (BiConsumer<Context, Entry> handler : this.exitHandlers) {
                try {
                    handler.accept(ctx, this);
                } catch (ChainException e) {
                    log.warn("Error occurred when invoking entry exit handler, current entry: "
                        + resourceWrapper, e);
                }
            }
            exitHandlers = null;
        }
    }

    protected void exitForContext(Context context, int count, Object... args) throws ErrorEntryFreeException {
        if (context != null) {
            // Null context should exit without clean-up.
            if (context instanceof NullContext) {
                return;
            }

            if (context.getCurEntry() != this) {
                String curEntryNameInContext = context.getCurEntry() == null ? null
                    : context.getCurEntry().getResourceWrapper().getName();
                // Clean previous call stack.
                CtEntry e = (CtEntry) context.getCurEntry();
                while (e != null) {
                    e.exit(count, args);
                    e = (CtEntry) e.parent;
                }
                String errorMessage = String.format("The order of entry exit can't be paired with the order of entry"
                        + ", current entry in context: <%s>, but expected: <%s>", curEntryNameInContext,
                        resourceWrapper);
                throw new ErrorEntryFreeException(errorMessage);
            } else {
                // Go through the onExit hook of all processor.
                if (chain != null) {
                    chain.exit(context, resourceWrapper, count, args);
                }
                // Go through the existing terminate handlers (associated to this invocation).
                callExitHandlersAndCleanUp(context);

                // Restore the call stack.
                context.setCurEntry(parent);
                if (parent != null) {
                    ((CtEntry) parent).child = null;
                }
                if (parent == null) {
                    // Default context (auto entered) will be exited automatically.
                    if (ContextUtil.isDefaultContext(context)) {
                        ContextUtil.exit();
                    }
                }
                // Clean the reference of context in current entry to avoid duplicate exit.
                clearEntryContext();
            }
        }
    }

    protected void clearEntryContext() {
        this.context = null;
    }

    @Override
    public void whenTerminate(BiConsumer<Context, Entry> handler) {
        if (this.exitHandlers == null) {
            this.exitHandlers = new LinkedList<>();
        }
        this.exitHandlers.add(handler);
    }

    @Override
    protected Entry trueExit(int count, Object... args) throws ErrorEntryFreeException {
        exitForContext(context, count, args);

        return parent;
    }

    @Override
    public Node getLastNode() {
        return parent == null ? null : parent.getCurNode();
    }
}