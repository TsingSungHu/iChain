package com.qs.iChain.context;

import com.qs.iChain.constants.Constants;
import com.qs.iChain.util.ContextUtil;

/**
 * If total {@link Context} exceed {@link Constants#MAX_CONTEXT_NAME_SIZE}, a
 * {@link NullContext} will get when invoke {@link ContextUtil}.enter(), means
 * no rules checking will do.
 *
 * @author TsingSungHu
 */
public class NullContext extends Context {

    public NullContext() {
        super(null, "null_context_internal");
    }
}
