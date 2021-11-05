package com.qs.iChain.node;

import com.qs.iChain.chain.ResourceWrapper;
import com.qs.iChain.context.Context;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;


/**
 * <p>
 * A {@link Node} used to hold statistics for specific resource name in the specific context.
 * Each distinct resource in each distinct {@link Context} will corresponding to a {@link DefaultNode}.
 * </p>
 * <p>
 * This class may have a list of sub {@link DefaultNode}s. Child nodes will be created when
 * calling {SphU}#entry() or {SphO}@entry() multiple times in the same {@link Context}.
 * </p>
 *
 * @author TsingSungHu
 */
@Slf4j
public class DefaultNode implements Node {

    /**
     * The resource associated with the node.
     */
    private ResourceWrapper id;

    /**
     * The list of all child nodes.
     */
    private volatile Set<Node> childList = new HashSet<>();

    public DefaultNode(ResourceWrapper id) {
        this.id = id;
    }

    public ResourceWrapper getId() {
        return id;
    }

    /**
     * Add child node to current node.
     *
     * @param node valid child node
     */
    public void addChild(Node node) {
        if (node == null) {
            log.warn("Trying to add null child to node <{}>, ignored", id.getName());
            return;
        }
        if (!childList.contains(node)) {
            synchronized (this) {
                if (!childList.contains(node)) {
                    Set<Node> newSet = new HashSet<>(childList.size() + 1);
                    newSet.addAll(childList);
                    newSet.add(node);
                    childList = newSet;
                }
            }
            log.info("Add child <{}> to node <{}>", ((DefaultNode)node).id.getName(), id.getName());
        }
    }

    /**
     * Reset the child node list.
     */
    public void removeChildList() {
        this.childList = new HashSet<>();
    }

    public Set<Node> getChildList() {
        return childList;
    }

    public void printDefaultNode() {
        visitTree(0, this);
    }

    private void visitTree(int level, DefaultNode node) {
        for (int i = 0; i < level; ++i) {
            System.out.print("-");
        }
        if (!(node instanceof EntranceNode)) {
            System.out.println(
                    String.format("%s", node.id.getShowName()));
        } else {
            System.out.println(
                    String.format("Entry-%s", node.id.getShowName()));
        }
        for (Node n : node.getChildList()) {
            DefaultNode dn = (DefaultNode)n;
            visitTree(level + 1, dn);
        }
    }
}
