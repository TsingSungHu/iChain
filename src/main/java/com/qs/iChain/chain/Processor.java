package com.qs.iChain.chain;

import com.qs.iChain.context.Context;

/**
 * A container of some process and ways of notification when the process is finished.
 *
 * @author TsingSungHu
 */
public interface Processor<T> {

    /**
     * Entrance.
     *
     * @param context     current {@link Context}
     * @param param       generics parameter
     * @param prioritized whether the entry is prioritized
     * @param args        parameters of the original call
     * @throws Throwable blocked exception or unexpected error
     */
    void entry(Context context, T param, boolean prioritized, Object... args) throws Throwable;

    /**
     * Means finish of {@link #entry(Context, Object, boolean, Object...)}.
     *
     * @param context     current {@link Context}
     * @param obj         relevant object (e.g. Node)
     * @param prioritized whether the entry is prioritized
     * @param args        parameters of the original call
     * @throws Throwable blocked exception or unexpected error
     */
    void fireEntry(Context context, Object obj, boolean prioritized, Object... args) throws Throwable;

    /**
     * Exit.
     *
     * @param context current {@link Context}
     * @param args    parameters of the original call
     */
    void exit(Context context, Object... args);

    /**
     * Means finish of {@link #exit(Context, Object...)}.
     *
     * @param context current {@link Context}
     * @param args    parameters of the original call
     */
    void fireExit(Context context, Object... args);
}
