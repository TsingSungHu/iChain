package com.qs.iChain;

import com.qs.iChain.constants.enums.EntryType;
import com.qs.iChain.exception.ChainException;
import java.lang.reflect.Method;

/**
 * The basic interface for process
 *
 * @author TsingSungHu
 */
public interface SphChain {

    /**
     * customize process for the given resource.
     *
     * @param name the unique name of the protected resource
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(String name) throws ChainException;

    /**
     * customize process for the given method.
     *
     * @param method the protected method
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(Method method) throws ChainException;

    /**
     * customize process for the given method.
     *
     * @param method     the protected method
     * @param batchCount the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(Method method, int batchCount) throws ChainException;

    /**
     * customize process for the given resource.
     *
     * @param name       the unique string for the resource
     * @param batchCount the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(String name, int batchCount) throws ChainException;

    /**
     * customize process for the given method.
     *
     * @param method      the protected method
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(Method method, EntryType trafficType) throws ChainException;

    /**
     * customize process for the given resource.
     *
     * @param name        the unique name for the protected resource
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(String name, EntryType trafficType) throws ChainException;

    /**
     * customize process for the given method.
     *
     * @param method      the protected method
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @param batchCount  the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(Method method, EntryType trafficType, int batchCount) throws ChainException;

    /**
     * customize process for the given resource.
     *
     * @param name        the unique name for the protected resource
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @param batchCount  the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(String name, EntryType trafficType, int batchCount) throws ChainException;

    /**
     * Record statistics and perform rule checking for the given resource.
     *
     * @param method      the protected method
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @param batchCount  the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @param args        parameters of the method for flow control or customize processor
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data).
     * @throws ChainException if the block criteria is met
     */
    Entry entry(Method method, EntryType trafficType, int batchCount, Object... args) throws ChainException;

    /**
     * Record statistics and perform rule checking for the given resource.
     *
     * @param name        the unique name for the protected resource
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @param batchCount  the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @param args        args for parameter flow control or customize processor
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data)
     * @throws ChainException if the block criteria is met
     */
    Entry entry(String name, EntryType trafficType, int batchCount, Object... args) throws ChainException;

    /**
     * Create a protected asynchronous resource.
     *
     * @param name        the unique name for the protected resource
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @param batchCount  the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @param args        args for parameter flow control or customize processor
     * @return created asynchronous entry
     * @throws ChainException if the block criteria is met
     * @since 0.2.0
     */
    AsyncEntry asyncEntry(String name, EntryType trafficType, int batchCount, Object... args) throws ChainException;

    /**
     * Create a protected resource with priority.
     *
     * @param name        the unique name for the protected resource
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @param batchCount  the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @param prioritized whether the entry is prioritized
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data)
     * @throws ChainException if the block criteria is met
     * @since 1.4.0
     */
    Entry entryWithPriority(String name, EntryType trafficType, int batchCount, boolean prioritized)
            throws ChainException;

    /**
     * Create a protected resource with priority.
     *
     * @param name        the unique name for the protected resource
     * @param trafficType the traffic type (inbound, outbound or internal)
     * @param batchCount  the amount of calls within the invocation (e.g. batchCount=2 means request for 2 tokens)
     * @param prioritized whether the entry is prioritized
     * @param args        args for parameter flow control or customize processor
     * @return the {Entry} of this invocation (used for mark the invocation complete and get context data)
     * @throws ChainException if the block criteria is met
     * @since 1.5.0
     */
    Entry entryWithPriority(String name, EntryType trafficType, int batchCount, boolean prioritized, Object... args)
            throws ChainException;
}
