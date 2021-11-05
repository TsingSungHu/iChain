package com.qs.iChain.context;

import com.qs.iChain.Entry;
import com.qs.iChain.node.DefaultNode;
import com.qs.iChain.node.EntranceNode;
import com.qs.iChain.node.Node;
import com.qs.iChain.util.ContextUtil;

/**
 * This class holds metadata of current invocation:<br/>
 *
 * <ul>
 * <li>the {@link EntranceNode}: the root of the current invocation
 * tree.</li>
 * <li>the current {@link Entry}: the current invocation point.</li>
 * <li>the current {@link Node}: the statistics related to the
 * {@link Entry}.</li>
 * <li>the origin: The origin is useful when we want to control different
 * invoker/consumer separately. Usually the origin could be the Service Consumer's app name
 * or origin IP. </li>
 * </ul>
 * <p>
 * Each {SphU}#entry() or {SphO}#entry() should be in a {@link Context},
 * if we don't invoke {@link ContextUtil}#enter() explicitly, DEFAULT context will be used.
 * </p>
 * <p>
 * A invocation tree will be created if we invoke {SphU}#entry() multi times in
 * the same context.
 * </p>
 * <p>
 * Same resource in different context will count separately.
 * </p>
 *
 * @author TsingSungHu
 * @see ContextUtil
 */
public class Context {

    /**
     * Context name.
     */
    private final String name;

    /**
     * The entrance node of current invocation tree.
     */
    private DefaultNode entranceNode;

    /**
     * Current processing entry.
     */
    private Entry curEntry;

    /**
     * The origin of this context (usually indicate different invokers, e.g. service consumer name or origin IP).
     */
    private String origin = "";

    private final boolean async;

    /**
     * Create a new async context.
     *
     * @param entranceNode entrance node of the context
     * @param name context name
     * @return the new created context
     * @since 0.2.0
     */
    public static Context newAsyncContext(DefaultNode entranceNode, String name) {
        return new Context(name, entranceNode, true);
    }

    public Context(DefaultNode entranceNode, String name) {
        this(name, entranceNode, false);
    }

    public Context(String name, DefaultNode entranceNode, boolean async) {
        this.name = name;
        this.entranceNode = entranceNode;
        this.async = async;
    }

    public boolean isAsync() {
        return async;
    }

    public String getName() {
        return name;
    }

    public Node getCurNode() {
        return curEntry.getCurNode();
    }

    public Context setCurNode(Node node) {
        this.curEntry.setCurNode(node);
        return this;
    }

    public Entry getCurEntry() {
        return curEntry;
    }

    public Context setCurEntry(Entry curEntry) {
        this.curEntry = curEntry;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public Context setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public DefaultNode getEntranceNode() {
        return entranceNode;
    }

    /**
     * Get the parent {@link Node} of the current.
     *
     * @return the parent node of the current.
     */
    public Node getLastNode() {
        if (curEntry != null && curEntry.getLastNode() != null) {
            return curEntry.getLastNode();
        } else {
            return entranceNode;
        }
    }

    public Node getOriginNode() {
        return curEntry == null ? null : curEntry.getOriginNode();
    }

    @Override
    public String toString() {
        return "Context{" +
            "name='" + name + '\'' +
            ", entranceNode=" + entranceNode +
            ", curEntry=" + curEntry +
            ", origin='" + origin + '\'' +
            ", async=" + async +
            '}';
    }
}
