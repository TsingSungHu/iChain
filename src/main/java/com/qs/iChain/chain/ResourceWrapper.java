package com.qs.iChain.chain;

import com.qs.iChain.constants.enums.EntryType;
import com.qs.iChain.util.AssertUtil;

/**
 * A wrapper of resource name and type.
 *
 * @author TsingSungHu
 */
public abstract class ResourceWrapper {

    protected final String name;
    protected final EntryType entryType;
    protected final int resourceType;

    public ResourceWrapper(String name, EntryType entryType, int resourceType) {
        AssertUtil.notEmpty(name, "resource name cannot be empty");
        AssertUtil.notNull(entryType, "entryType cannot be null");
        this.name = name;
        this.entryType = entryType;
        this.resourceType = resourceType;
    }

    /**
     * Get the resource name.
     *
     * @return the resource name
     */
    public String getName() {
        return name;
    }

    /**
     * Get {@link EntryType} of this wrapper.
     *
     * @return {@link EntryType} of this wrapper.
     */
    public EntryType getEntryType() {
        return entryType;
    }

    /**
     * Get the classification of this resource.
     *
     * @return the classification of this resource
     * @since 1.7.0
     */
    public int getResourceType() {
        return resourceType;
    }

    /**
     * Get the beautified resource name to be showed.
     *
     * @return the beautified resource name
     */
    public abstract String getShowName();

    /**
     * Only {@link #getName()} is considered.
     */
    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    /**
     * Only {@link #getName()} is considered.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceWrapper) {
            ResourceWrapper rw = (ResourceWrapper)obj;
            return rw.getName().equals(getName());
        }
        return false;
    }
}
