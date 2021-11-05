package com.qs.iChain.chain;

import com.qs.iChain.constants.ResourceTypeConstants;
import com.qs.iChain.constants.enums.EntryType;
import com.qs.iChain.util.MethodUtil;

import java.lang.reflect.Method;

/**
 * Resource wrapper for method invocation.
 *
 * @author TsingSungHu
 */
public class MethodResourceWrapper extends ResourceWrapper {

    private final transient Method method;

    public MethodResourceWrapper(Method method, EntryType e) {
        this(method, e, ResourceTypeConstants.COMMON);
    }

    public MethodResourceWrapper(Method method, EntryType e, int resType) {
        super(MethodUtil.resolveMethodName(method), e, resType);
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public String getShowName() {
        return name;
    }

    @Override
    public String toString() {
        return "MethodResourceWrapper{" +
            "name='" + name + '\'' +
            ", entryType=" + entryType +
            ", resourceType=" + resourceType +
            '}';
    }
}
