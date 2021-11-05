package com.qs.iChain.chain;

import com.qs.iChain.constants.ResourceTypeConstants;
import com.qs.iChain.constants.enums.EntryType;

/**
 * Common string resource wrapper.
 *
 * @author TsingSungHu
 */
public class StringResourceWrapper extends ResourceWrapper {

    public StringResourceWrapper(String name, EntryType e, int resType) {
        super(name, e, resType);
    }

    public StringResourceWrapper(String name, EntryType e) {
        super(name, e, ResourceTypeConstants.COMMON);
    }

    @Override
    public String getShowName() {
        return name;
    }

    @Override
    public String toString() {
        return "StringResourceWrapper{" +
            "name='" + name + '\'' +
            ", entryType=" + entryType +
            ", resourceType=" + resourceType +
            '}';
    }
}
