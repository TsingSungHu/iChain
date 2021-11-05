package com.qs.iChain;

import com.qs.iChain.chain.ResourceWrapper;
import com.qs.iChain.context.Context;
import com.qs.iChain.exception.ErrorEntryFreeException;
import com.qs.iChain.node.Node;
import com.qs.iChain.util.ContextUtil;

import java.util.function.BiConsumer;

/**
 * Each entry will return an {@link Entry}. This class holds information of current invocation
 *
 * @author TsingSungHu
 */
public abstract class Entry implements AutoCloseable {

    private static final Object[] OBJECTS0 = new Object[0];

    protected final ResourceWrapper resourceWrapper;
    private final long createTimestamp;
    private long completeTimestamp;

    private Node curNode;
    /**
     * {@link Node} of the specific origin, Usually the origin is the Service Consumer.
     */
    private Node originNode;

    private Throwable error;

    public Entry(ResourceWrapper resourceWrapper) {
        this.resourceWrapper = resourceWrapper;
        this.createTimestamp = System.currentTimeMillis();
    }

    /**
     * Complete the current resource entry and restore the entry stack in context.
     *
     * @throws ErrorEntryFreeException if entry in current context does not match current entry
     */
    public void exit() throws ErrorEntryFreeException {
        exit(1, OBJECTS0);
    }

    public void exit(int count) throws ErrorEntryFreeException {
        exit(count, OBJECTS0);
    }

    /**
     * Equivalent to {@link #exit()}. Support try-with-resources since JDK 1.7.
     *
     * @since 1.5.0
     */
    @Override
    public void close() {
        exit();
    }

    /**
     * Exit this entry. This method should invoke if and only if once at the end of the resource protection.
     *
     * @param count tokens to release.
     * @param args extra parameters
     * @throws ErrorEntryFreeException, if {@link Context#getCurEntry()} is not this entry.
     */
    public abstract void exit(int count, Object... args) throws ErrorEntryFreeException;

    /**
     * Exit this entry.
     *
     * @param count tokens to release.
     * @param args extra parameters
     * @return next available entry after exit, that is the parent entry.
     * @throws ErrorEntryFreeException, if {@link Context#getCurEntry()} is not this entry.
     */
    protected abstract Entry trueExit(int count, Object... args) throws ErrorEntryFreeException;

    /**
     * Get related {@link Node} of the parent {@link Entry}.
     *
     * @return
     */
    public abstract Node getLastNode();

    public ResourceWrapper getResourceWrapper() {
        return resourceWrapper;
    }
    public long getCreateTimestamp() {
        return createTimestamp;
    }

    public long getCompleteTimestamp() {
        return completeTimestamp;
    }

    public Entry setCompleteTimestamp(long completeTimestamp) {
        this.completeTimestamp = completeTimestamp;
        return this;
    }

    public Node getCurNode() {
        return curNode;
    }

    public void setCurNode(Node node) {
        this.curNode = node;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    /**
     * Get origin {@link Node} of the this {@link Entry}.
     *
     * @return origin {@link Node} of the this {@link Entry}, may be null if no origin specified by
     * {@link ContextUtil#enter(String name, String origin)}.
     */
    public Node getOriginNode() {
        return originNode;
    }

    public void setOriginNode(Node originNode) {
        this.originNode = originNode;
    }

    /**
     * Like {@code CompletableFuture} since JDK 8, it guarantees specified handler
     * is invoked when this entry terminated (exited), no matter it's blocked or permitted.
     * Use it when you did some STATEFUL operations on entries.
     * 
     * @param handler handler function on the invocation terminates
     * @since 1.8.0
     */
    public abstract void whenTerminate(BiConsumer<Context, Entry> handler);

}
