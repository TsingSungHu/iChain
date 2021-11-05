package com.qs.iChain.node;

import com.qs.iChain.chain.StringResourceWrapper;
import com.qs.iChain.constants.Constants;
import com.qs.iChain.constants.ResourceTypeConstants;
import com.qs.iChain.constants.enums.EntryType;
import com.qs.iChain.context.Context;
import com.qs.iChain.util.ContextUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * A {@link Node} represents the entrance of the invocation tree.
 * </p>
 * <p>
 * One {@link Context} will related to a {@link EntranceNode},
 * which represents the entrance of the invocation tree. New {@link EntranceNode} will be created if
 * current context does't have one. Note that same context name will share same {@link EntranceNode}
 * globally.
 * </p>
 *
 * @author TsingSungHu
 * @see ContextUtil
 * @see ContextUtil#enter(String, String)
 */
public class EntranceNode extends DefaultNode {

    public EntranceNode(String name) {
        super(new StringResourceWrapper(name, EntryType.IN, ResourceTypeConstants.COMMON));
    }

    public EntranceNode(String name, int resType) {
        super(new StringResourceWrapper(name, EntryType.IN, resType));
    }

    public EntranceNode(String name, EntryType entryType, int resourceType) {
        super(new StringResourceWrapper(name, entryType, resourceType));
    }

    /**
     * <p>The origin map holds the pair: (origin, originNode) for one specific resource.</p>
     * <p>
     * The longer the application runs, the more stable this mapping will become.
     * So we didn't use concurrent map here, but a lock, as this lock only happens
     * at the very beginning while concurrent map will hold the lock all the time.
     * </p>
     */
    private Map<String, EntranceNode> originCountMap = new HashMap<>();

    private final ReentrantLock lock = new ReentrantLock();

    public Node getOrCreateOriginNode(String origin) {
        EntranceNode entranceNode = originCountMap.get(origin);
        if (entranceNode == null) {
            lock.lock();
            try {
                entranceNode = originCountMap.get(origin);
                if (entranceNode == null) {
                    // The node is absent, create a new node for the origin.
                    entranceNode = new EntranceNode(Constants.CONTEXT_DEFAULT_NAME);
                    HashMap<String, EntranceNode> newMap = new HashMap<>(originCountMap.size() + 1);
                    newMap.putAll(originCountMap);
                    newMap.put(origin, entranceNode);
                    originCountMap = newMap;
                }
            } finally {
                lock.unlock();
            }
        }
        return entranceNode;
    }

    public Map<String, EntranceNode> getOriginCountMap() {
        return originCountMap;
    }
}
