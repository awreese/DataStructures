/**
 * Copyright © 2016, Andrew W. Reese. All rights reserved.
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This code is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package dataStructures.tree;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import dataStructures.TraversalOrder;
import dataStructures.TraversalType;
import dataStructures.Traverser;
import dataStructures.tree.traverser.TreeTraversalOrder;
import dataStructures.tree.traverser.TreeTraverser;
import dataStructures.tree.treeNode.TreeNode;

/**
 * This class provides a skeletal implementation of the {@link Tree} interface
 * to minimize the effort required to implement this interface.
 * <p>
 * To implement an unmodifiable tree, the programmer needs only to extend this
 * class and provide implementations for the {@link #getRoot()} and
 * {@link #getNode(Object)} methods.
 * </p>
 * <p>
 * To implement a modifiable tree, the programmer must additionally override the
 * {@link #add(TreeNode, TreeNode)}, {@link #remove(Object)},
 * {@link #setRoot(TreeNode)} methods (which otherwise throw an
 * {@code UnsupportedOperationException}).
 * </p>
 * <p>
 * The programmer should generally provide a void (no argument) and collection
 * constructor, as per the recommendation in the {@link Collection} interface
 * specification.
 * </p>
 * <p>
 * The programmer does <i>not</i> have to provide an iterator or traverser
 * implementation; the iterator and tree traverser are implemented by this
 * class.
 * </p>
 * <p>
 * A generic {@link #size()} method has already been implemented, but depending
 * on extended implementations this method should also be overridden to provide
 * more efficient implementations.
 * </p>
 * 
 * @param <T> the type of elements in this tree
 * @author Drew Reese
 * @version 1.0
 * @since JDK1.8
 */
public abstract class AbstractTree<T> extends AbstractCollection<TreeNode<T>>
        implements Tree<T> {

    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     */
    protected AbstractTree() {
    }

    // Modification Operations

    /**
     * {@inheritDoc}
     * 
     * <p>
     * Trees that support this operation may place limitations on what elements
     * may be added to this tree. In particular, some trees will refuse to add
     * null elements, and others will impose restrictions on the type of
     * elements that may be added. Tree classes should clearly specify in their
     * documentation any restrictions on what elements may be added.
     * </p>
     * <p>
     * Note that this implementation throws an
     * {@code UnsupportedOperationException} unless
     * {@link #add(TreeNode, TreeNode)} is overridden.
     * </p>
     */
    /*
     * This implementation calls {@code add(t.getParent(), t)}
     */
    @Override
    public boolean add(TreeNode<T> t) {
        return add(t.getParent(), t);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This implementation always throws an UnsupportedOperationException.
     * </p>
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public boolean add(TreeNode<T> parent, TreeNode<T> child) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This implementation calls {@code add(TreeNode, TreeNode)}
     * </p>
     * <p>
     * Note that this implementation throws an
     * {@code UnsupportedOperationException} unless
     * {@link #add(TreeNode, TreeNode)} is overridden.
     * </p>
     * 
     * @see dataStructures.tree.Tree#insert(dataStructures.tree.treeNode.TreeNode,
     *      dataStructures.tree.treeNode.TreeNode)
     */
    /*
     * This implementation saves the parent node of the node having a new node
     * inserted before it and tries to add the new node to the old parent. If
     * successful, it continues to decouple the old node from the old parent and
     * set as new child of the inserted node.
     */
    @Override
    public boolean insert(TreeNode<T> node, TreeNode<T> newNode)
            throws UnsupportedOperationException, ClassCastException,
            NullPointerException, IllegalArgumentException {

        // save old parent
        TreeNode<T> oldParent = node.getParent();

        // optimistically add new node to parent
        if (add(oldParent, newNode)) {
            // success, continue insertion
            // this saves having to do any UNDO actions

            // decouple node from old parent
            oldParent.remove(node);
            // set current node parent to newNode and add as child
            node.setParent(newNode);
            newNode.add(node);

            return true;
        }

        // failed
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) throws UnsupportedOperationException,
            ClassCastException, NullPointerException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#setRoot(dataStructures.tree.TreeNode)
     */
    @Override
    public void setRoot(TreeNode<T> root) {
        throw new UnsupportedOperationException();
    }

    // Query Operations

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#getRoot()
     */
    @Override
    public abstract TreeNode<T> getRoot();

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#getNode(java.lang.Object)
     */
    @Override
    public abstract TreeNode<T> getNode(Object data)
            throws ClassCastException, NullPointerException;

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#size()
     */
    @Override
    public int size() {
        int size = getRoot() == null ? 0 : size(getRoot()) + 1;
        return size < 0 ? Integer.MAX_VALUE : size;
    }

    int size(TreeNode<T> node) {
        int size = node.childCount();

        for (TreeNode<T> child : node.getChildren()) {
            size += size(child);
        }

        return size < 0 ? Integer.MAX_VALUE : size;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return getRoot() == null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#subTree(dataStructures.tree.TreeNode)
     */
    @Override
    public Tree<T> subTree(TreeNode<T> parent) {
        return new SubTree<T>(this, parent);
    }

    // Bulk Operations

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#clear()
     */
    @Override
    public void clear() {
        trim(getRoot());
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#addAll(dataStructures.tree.TreeNode,
     * dataStructures.tree.Tree)
     */
    @Override
    public void addSubtree(TreeNode<T> parent, Tree<T> subTree) {
        add(parent, subTree.getRoot());
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#trim(dataStructures.tree.TreeNode)
     */
    @Override
    public boolean trim(TreeNode<T> parent) {
        Iterator<TreeNode<T>> itr = new Itr(parent);
        boolean modified = false;
        while (itr.hasNext()) {
            itr.next();
            itr.remove();
        }

        return modified;
    }

    // Iterators & Traversers

    /*
     * (non-Javadoc)
     * 
     * @see java.util.AbstractCollection#iterator()
     */
    @Override
    public Iterator<TreeNode<T>> iterator() {
        return new Itr();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#iterator(dataStructures.TraversalOrder)
     */
    @Override
    public Iterator<TreeNode<T>> iterator(TraversalOrder order) {
        return new Itr(order);
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#traverser()
     */
    @Override
    public Traverser<TreeNode<T>> traverser() {
        return new traverser();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.Tree#traverser(dataStructures.tree.TreeNode)
     */
    @Override
    public Traverser<TreeNode<T>> traverser(TreeNode<T> t)
            throws NoSuchElementException {
        if (!contains(t)) {
            throw new NoSuchElementException();
        }
        return new traverser(t);
    }

    /**
     * The number of times this tree has been <i>structurally modified</i>.
     * Structural modifications are those that change the size of the tree, or
     * otherwise perturb it in such a fashion that iterations in progress may
     * yield incorrect results.
     *
     * <p>
     * This field is used by the iterator and tree traverser implementation
     * returned by the {@code iterator} and {@code traverser} methods. If the
     * value of this field changes unexpectedly, the iterator (or traverser)
     * will throw a {@code ConcurrentModificationException} in response to the
     * {@code next}, {@code remove}, {@code previous}, {@code set} or
     * {@code add} operations. This provides <i>fail-fast</i> behavior, rather
     * than non-deterministic behavior in the face of concurrent modification
     * during iteration.
     *
     * <p>
     * <b>Use of this field by subclasses is optional.</b> If a subclass wishes
     * to provide fail-fast iterators (and tree traversers), then it merely has
     * to increment this field in its {@code add(TreeNode)} and
     * {@code remove(TreeNode)} methods (and any other methods that it overrides
     * that result in structural modifications to the tree). A single call to
     * {@code add(TreeNode)} or {@code remove(TreeNode)} must add no more than
     * one to this field, or the iterators (and tree traversers) will throw
     * bogus {@code ConcurrentModificationExceptions}. If an implementation does
     * not wish to provide fail-fast iterators, this field may be ignored.
     */
    protected transient int modCount = 0;

    // Comparison and Hashing

    /**
     * Compares the specified object with this tree for equality. Returns
     * {@code true} if and only if the specified object is also a tree, both
     * trees have the same size, and all corresponding pairs of elements in the
     * two trees are <i>equal</i>. (Two elements {@code e1} and {@code e2} are
     * <i>equal</i> if {@code (e1==null ? e2==null :
     * e1.equals(e2))}.) In other words, two trees are defined to be equal if
     * they contain the same elements in the same order.
     * <p>
     *
     * This implementation first checks if the specified object is this tree. If
     * so, it returns {@code true}; if not, it checks if the specified object is
     * a tree. If not, it returns {@code false}; if so, it iterates over both
     * trees, comparing corresponding pairs of elements. If any comparison
     * returns {@code false}, this method returns {@code false}. If either
     * iterator runs out of elements before the other it returns {@code false}
     * (as the lists are of unequal length); otherwise it returns {@code true}
     * when the iterations complete.
     *
     * @param o the object to be compared for equality with this tree
     * @return {@code true} if the specified object is equal to this tree
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tree)) {
            return false;
        }

        Iterator<TreeNode<T>> t1 = iterator();
        Iterator<?> t2 = ((Tree<?>) obj).iterator();

        while (t1.hasNext() && t2.hasNext()) {
            TreeNode<T> o1 = t1.next();
            Object o2 = t2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2))) {
                return false;
            }
        }

        return !(t1.hasNext() || t2.hasNext());
    }

    /**
     * Returns the hash code value for this tree.
     * 
     * <p>
     * This implementation uses exactly the code that is used to define the tree
     * hash function in the documentation for the {@link Tree#hashCode} method.
     * </p>
     * 
     * @return the hash code value for this tree
     */
    @Override
    public int hashCode() {
        final int prime = 43;
        int hashCode = 1;
        for (TreeNode<T> node : this) {
            hashCode = prime * hashCode + (node == null ? 0 : node.hashCode());
        }
        return hashCode;
    }

    /*
     * 
     */
    private class Itr implements Iterator<TreeNode<T>> {

        /**
         * Internal list of the elements the underlying tree
         */
        ArrayList<TreeNode<T>> nodeList;

        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;

        /**
         * Index of element returned by most recent call to next or previous.
         * Reset to -1 if this element is deleted by a call to remove.
         */
        int lastRet = -1;

        /**
         * The modCount value that the iterator believes that the backing Tree
         * should have. If this expectation is violated, the iterator has
         * detected concurrent modification.
         */
        int expectedModCount = modCount;

        public Itr() {
            this(TreeTraversalOrder.defaultOrder());
        }

        /**
         * @param order
         */
        public Itr(TraversalOrder order) {
            this(order, getRoot());
        }

        public Itr(TreeNode<T> node) {
            this(TreeTraversalOrder.defaultOrder(), node);
        }

        public Itr(TraversalOrder order, TreeNode<T> node) {
            super();
            this.nodeList = build((TreeTraversalOrder) order, node);
        }

        private ArrayList<TreeNode<T>> build(TreeTraversalOrder order,
                TreeNode<T> node) {
            // Use LinkedList for stack/queue behavior while building
            LinkedList<TreeNode<T>> temp = new LinkedList<TreeNode<T>>();

            build(order, node, temp);

            switch (order) {
            case LEVEL_ORDER:
                temp.push(node); // places original node at front
            case IN_ORDER:
            case POST_ORDER:
            case PRE_ORDER:
            default:
            }

            // Convert to ArrayList for O(1) access time while iterating
            return new ArrayList<TreeNode<T>>(temp);
        }

        private void build(TreeTraversalOrder order, TreeNode<T> node,
                LinkedList<TreeNode<T>> list) {
            switch (order) {
            case PRE_ORDER:
                list.add(node);
                for (TreeNode<T> child : node.getChildren()) {
                    build(order, child, list);
                }
                break;
            default:
            case IN_ORDER:
            case LEVEL_ORDER:
                list.addAll(node.getChildren());
                for (TreeNode<T> child : node.getChildren()) {
                    build(order, child, list);
                }
                break;
            case POST_ORDER:
                for (TreeNode<T> child : node.getChildren()) {
                    build(order, child, list);
                }
                list.add(node);
                break;
            }
        }

        @Override
        public boolean hasNext() {
            // return iterator.hasNext();
            return cursor != nodeList.size();
        }

        @Override
        public TreeNode<T> next() {
            checkForComodification();
            // lastRet = iterator.next();
            // return lastRet;
            try {
                int i = cursor;
                TreeNode<T> next = nodeList.get(i);
                lastRet = i;
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                checkForComodification();
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            // if (lastRet == null) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            checkForComodification();

            // TODO: move actual remove implementation to non-abstract class
            // // let parent inherit grand-children
            // lastRet.getParent().addAll(lastRet.getChildren());
            //
            // // remove references to/from this node so gc can do its job
            // lastRet.getParent().getChildren().remove(lastRet);
            // lastRet.setParent(null);
            // lastRet = null;

            // AbstractTree.this.remove(lastRet);
            // iterator.remove();
            // expectedModCount = modCount;

            try {
                AbstractTree.this.remove(nodeList.get(lastRet));
                nodeList.remove(lastRet);
                if (lastRet < cursor) {
                    cursor--;
                }
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

    }

    /**
     * Traverser for this abstract tree.
     */
    private class traverser implements TreeTraverser<TreeNode<T>> {

        TreeStack traverserTreeStack = null;

        /**
         * The modCount value that the iterator believes that the backing Tree
         * should have. If this expectation is violated, the iterator has
         * detected concurrent modification.
         */
        int expectedModCount = modCount;

        public traverser() {
            this(getRoot());
        }

        public traverser(TreeNode<T> node) {
            this.traverserTreeStack = new TreeStack(node);
        }

        /**
         * Stack represents a "path" down a tree. The the current node of the
         * head of this stack is the current node of the tree traverser.
         * 
         * <p>
         * <code>
         * <pre>           previous       current        next
         *                     { }  <b>(n_41)</b>   { }
         *     { n_31, n_32, ... }  <b>(n_3N)</b>   { ... }
         *                     { }  <b>(n_21)</b>   { n_22, n_23, ... }
         *<b>head</b> { n_11, n_12, ... } <b>(current)</b> { ... }</pre>
         * </code>
         * </p>
         */
        private class TreeStack {

            LinkedList<TreeStackNode> treeStack;

            public TreeStack(TreeNode<T> t) {
                this.treeStack = new LinkedList<TreeStackNode>();
                if (t != null) {
                    this.treeStack.push(new TreeStackNode(t));
                }
            }

            public boolean hasParent() {
                return (treeStack.isEmpty()) ? false
                        : treeStack.peek().current.hasParent();
            }

            public boolean hasNext() {
                return (treeStack.isEmpty()) ? false
                        : !treeStack.peek().nextStack.isEmpty();
            }

            public boolean hasPrev() {
                return (treeStack.isEmpty()) ? false
                        : !treeStack.peek().prevStack.isEmpty();
            }

            public boolean hasChild() {
                return (treeStack.isEmpty()) ? false
                        : treeStack.peek().current.hasParent();
            }

            public void advanceParent() {
                if (!this.hasParent()) {
                    throw new IllegalArgumentException("No parent node");
                }

                // special case going up tree, at this point stack has only one
                // element, so parent (and siblings) need to be inserted before
                // the current element as if we traversed from it before. This
                // implementation pops the current element from the stack, and
                // pushes it's parent group into the stack.
                treeStack.push(
                        new TreeStackNode(treeStack.pop().current.getParent()));
            }

            public void advanceNext() {
                if (!this.hasNext()) {
                    throw new IllegalArgumentException("No next node");
                }
                treeStack.peek().advanceNext();
            }

            public void advancePrev() {
                if (!this.hasPrev()) {
                    throw new IllegalArgumentException("No previous node");
                }
                treeStack.peek().advancePrev();
            }

            public void advanceChildren() {
                if (!this.hasChild()) {
                    throw new IllegalArgumentException("No child node");
                }
                treeStack.push(new TreeStackNode(
                        treeStack.peek().current.getChildren()));
            }

            public TreeNode<T> getCurrent() {
                return (treeStack.isEmpty()) ? null : treeStack.peek().current;
            }

            /**
             * Node containing a list of previous and next nodes and a current
             * node.
             * 
             * <p>
             * <code>
             * <pre>      previous       current        next
             * { n_1, n_2, ... } (n_current) { n_current+1, n_current+2, ... }
             * </pre>
             * </code>
             * </p>
             */
            private class TreeStackNode {

                private LinkedList<TreeNode<T>> prevStack;
                private TreeNode<T>             current;
                private LinkedList<TreeNode<T>> nextStack;

                /**
                 * Use this constructor when traversing to children.
                 * 
                 * @param c - collection of siblings
                 */
                public TreeStackNode(Collection<? extends TreeNode<T>> c) {
                    this.prevStack = new LinkedList<TreeNode<T>>();
                    this.nextStack = new LinkedList<TreeNode<T>>(c);
                    this.current = nextStack.pop();
                }

                /**
                 * Use this constructor when traversing to parent.
                 * 
                 * @param t - single tree node, usually parent node
                 */
                public TreeStackNode(TreeNode<T> t) {
                    this.prevStack = new LinkedList<TreeNode<T>>();
                    this.current = t;
                    this.nextStack = new LinkedList<TreeNode<T>>();
                    build(t);
                }

                /**
                 * Gathers up and divvies sibling tree nodes of node t into the
                 * prev and next stacks.
                 * 
                 * @param t - node to "center" on
                 */
                private void build(TreeNode<T> t) {
                    if (t.hasParent()) {
                        boolean found = false;
                        for (TreeNode<T> child : t.getParent().getChildren()) {
                            if (!found) { // build prev list
                                if (child.equals(t)) { // found, do skip!
                                    found = true;
                                    break;
                                }
                                this.prevStack.push(child);
                            } else { // build next list
                                this.nextStack.push(child);
                            }
                        }
                    }
                }

                /**
                 * Shifts current node to next sibling.
                 */
                public void advanceNext() {
                    prevStack.push(current);
                    current = nextStack.pop();
                }

                /**
                 * Shifts current node to previous sibling.
                 */
                public void advancePrev() {
                    nextStack.push(current);
                    current = prevStack.pop();
                }
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see dataStructures.tree.TreeTraverser#hasNext(dataStructures.
         * TraversalType)
         */
        @Override
        public boolean hasNext(TraversalType type) {
            if (traverserTreeStack.treeStack.isEmpty()) {
                return false;
            }

            switch ((TreeTraversalType) type) {
            case CHILD:
                // return currentNode.hasChildren();
                return traverserTreeStack.hasChild();

            case PARENT:
                // return currentNode.hasParent();
                return traverserTreeStack.hasParent();

            case SIBLING_PREV:
                // if (currentNode.hasParent()) {
                // LinkedList<TreeNode<T>> siblings = new
                // LinkedList<TreeNode<T>>(
                // currentNode.getParent().getChildren());
                // if (currentNode != siblings.getLast()) {
                // return true;
                // }
                // }
                // return false;
                return traverserTreeStack.hasPrev();

            case SIBLING_NEXT:
                // if (currentNode.hasParent()) {
                // LinkedList<TreeNode<T>> siblings = new
                // LinkedList<TreeNode<T>>(
                // currentNode.getParent().getChildren());
                // if (currentNode != siblings.getFirst()) {
                // return true;
                // }
                // }
                // return false;
                return traverserTreeStack.hasNext();

            default:
                // invalid tree traversal type
                throw new IllegalArgumentException();

            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * dataStructures.tree.TreeTraverser#next(dataStructures.TraversalType)
         */
        @Override
        public TreeNode<T> next(TraversalType type) {
            if (!hasNext(type)) {
                throw new NoSuchElementException();
            }
            checkForComodification();

            switch ((TreeTraversalType) type) {
            case CHILD:
                // currentNode = (TreeNode<T>) currentNode.getChildren()
                // .toArray()[0];
                // return currentNode;
                traverserTreeStack.advanceChildren();
                // return traverserTreeStack.getCurrent();
                break;

            case PARENT:
                // currentNode = currentNode.getParent();
                // return currentNode;
                traverserTreeStack.advanceParent();
                // return traverserTreeStack.getCurrent();
                break;

            case SIBLING_PREV:
                // throw new UnsupportedOperationException();
                traverserTreeStack.advancePrev();
                // return traverserTreeStack.getCurrent();
                break;

            case SIBLING_NEXT:
                // throw new UnsupportedOperationException();
                traverserTreeStack.advanceNext();
                // return traverserTreeStack.getCurrent();
                break;

            default:
                throw new IllegalArgumentException();

            }

            return traverserTreeStack.getCurrent();
        }

        /*
         * (non-Javadoc)
         * 
         * @see dataStructures.tree.TreeTraverser#setData(java.lang.Object)
         */
        @Override
        public <TT> void setData(TT data)
                throws UnsupportedOperationException, ClassCastException,
                IllegalArgumentException, IllegalStateException {

            // if (currentNode == null) {
            // throw new IllegalStateException();
            // }
            if (traverserTreeStack.treeStack.isEmpty()) {
                throw new IllegalStateException();
            }
            checkForComodification();

            // TreeNode<T> node = currentNode;
            // node.setData(data);

            traverserTreeStack.getCurrent().setData(data);
            expectedModCount = modCount; // probably not needed here

        }

        /*
         * (non-Javadoc)
         * 
         * @see dataStructures.tree.TreeTraverser#add(java.lang.Object)
         */
        @Override
        public void add(TreeNode<T> t) throws UnsupportedOperationException,
                ClassCastException, IllegalArgumentException {
            checkForComodification();

            // TreeNode<T> node = currentNode;
            // AbstractTree.this.add(node, t);
            if (traverserTreeStack.treeStack.isEmpty()) {
                this.traverserTreeStack = new TreeStack(t);
            }
            AbstractTree.this.add(traverserTreeStack.getCurrent(), t);
            expectedModCount = modCount;

        }

        /*
         * (non-Javadoc)
         * 
         * @see dataStructures.tree.TreeTraverser#remove()
         */
        @Override
        public void remove()
                throws UnsupportedOperationException, IllegalStateException {
            // if (currentNode == null) {
            if (traverserTreeStack.treeStack.isEmpty()) {
                throw new IllegalStateException();
            }
            checkForComodification();

            // TreeNode<T> node = currentNode;
            TreeNode<T> node = traverserTreeStack.getCurrent();
            // TreeNode<T> parent = currentNode.getParent();
            // TreeNode<T> parent = traverserTreeStack.getCurrent().getParent();
            // // don't think I need this either

            if (traverserTreeStack.hasParent()) {
                traverserTreeStack.advanceParent();
            } else {
                // no parent == root, we're removing root
                traverserTreeStack.treeStack.clear();
                // any further calls should throw exception until stack is no
                // longer empty
            }
            AbstractTree.this.remove(node);
            // currentNode = parent;
            expectedModCount = modCount;

        }

        /*
         * (non-Javadoc)
         * 
         * @see dataStructures.tree.TreeTraverser#insert(java.lang.Object)
         */
        @Override
        public void insert(TreeNode<T> t) throws UnsupportedOperationException,
                ClassCastException, IllegalArgumentException {
            checkForComodification();

            // TreeNode<T> node = currentNode;
            // AbstractTree.this.insert(node, t);
            if (traverserTreeStack.treeStack.isEmpty()) {
                // this.traverserTreeStack = new TreeStack(t);
                add(t);
            } else {
                AbstractTree.this.insert(traverserTreeStack.getCurrent(), t);
                expectedModCount = modCount;
            }

        }

        /*
         * (non-Javadoc)
         * 
         * @see dataStructures.tree.TreeTraverser#trim()
         */
        @Override
        public void trim()
                throws UnsupportedOperationException, IllegalStateException {
            // if (currentNode == null) {
            if (traverserTreeStack.treeStack.isEmpty()) {
                throw new IllegalStateException();
            }
            checkForComodification();

            // TreeNode<T> node = currentNode;
            TreeNode<T> node = traverserTreeStack.getCurrent();
            // TreeNode<T> parent = currentNode.getParent();

            if (traverserTreeStack.hasParent()) {
                traverserTreeStack.advanceParent();
            } else {
                // no parent == root, we're removing root
                traverserTreeStack.treeStack.clear();
                // any further calls should throw exception until stack is no
                // longer empty
            }

            AbstractTree.this.trim(node);
            // currentNode = parent;
            expectedModCount = modCount;

        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

    }

}

class SubTree<T> extends AbstractTree<T> {

    private final AbstractTree<T>   tree;
    protected transient TreeNode<T> root;

    SubTree(AbstractTree<T> tree, TreeNode<T> root) {
        if (!tree.contains(root)) {
            throw new NoSuchElementException();
        }
        this.tree = tree;
        this.root = tree.getRoot(); // is this right? shouldn't be same root as
                                    // parent tree
        this.modCount = tree.modCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.AbstractTree#getRoot()
     */
    @Override
    public TreeNode<T> getRoot() {
        return this.root;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.AbstractTree#getNode(java.lang.Object)
     */
    @Override
    public TreeNode<T> getNode(Object data)
            throws ClassCastException, NullPointerException {
        checkForComodification();
        return tree.getNode(data);
    }

    /**
     * @return
     * @see dataStructures.tree.AbstractTree#size()
     */
    public int size() {
        checkForComodification();
        return tree.size(this.root);
    }

    /**
     * @param t
     * @return
     * @see dataStructures.tree.AbstractTree#add(dataStructures.tree.treeNode.TreeNode)
     */
    public boolean add(TreeNode<T> t) {
        return add(t.getParent(), t);
    }

    /**
     * @param parent
     * @param child
     * @return
     * @see dataStructures.tree.AbstractTree#add(dataStructures.tree.treeNode.TreeNode,
     *      dataStructures.tree.treeNode.TreeNode)
     */
    public boolean add(TreeNode<T> parent, TreeNode<T> child) {
        checkForComodification();
        boolean modified = tree.add(parent, child);
        this.modCount = tree.modCount;
        return modified;
    }

    /**
     * @param node
     * @param newNode
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @see dataStructures.tree.AbstractTree#insert(dataStructures.tree.treeNode.TreeNode,
     *      dataStructures.tree.treeNode.TreeNode)
     */
    public boolean insert(TreeNode<T> node, TreeNode<T> newNode)
            throws UnsupportedOperationException, ClassCastException,
            NullPointerException, IllegalArgumentException {
        checkForComodification();
        boolean modified = tree.insert(node, newNode);
        this.modCount = tree.modCount;
        return modified;
    }

    /**
     * @param o
     * @return
     * @throws UnsupportedOperationException
     * @throws ClassCastException
     * @throws NullPointerException
     * @see dataStructures.tree.AbstractTree#remove(java.lang.Object)
     */
    public boolean remove(Object o) throws UnsupportedOperationException,
            ClassCastException, NullPointerException {
        checkForComodification();
        boolean modified = tree.remove(o);
        this.modCount = tree.modCount;
        return modified;
    }

    /**
     * @param parent
     * @return
     * @see dataStructures.tree.AbstractTree#subTree(dataStructures.tree.treeNode.TreeNode)
     */
    public Tree<T> subTree(TreeNode<T> parent) {
        return new SubTree<T>(this, parent);
    }

    /**
     * @param c
     * @return
     * @see java.util.AbstractCollection#addAll(java.util.Collection)
     */
    public boolean addAll(Collection<? extends TreeNode<T>> c) {
        checkForComodification();
        boolean modified = tree.addAll(c);
        this.modCount = tree.modCount;
        return modified;
    }

    /**
     * @param parent
     * @return
     * @see dataStructures.tree.AbstractTree#trim(dataStructures.tree.treeNode.TreeNode)
     */
    public boolean trim(TreeNode<T> parent) {
        checkForComodification();
        boolean modified = tree.trim(parent);
        this.modCount = tree.modCount;
        return modified;
    }

    private void checkForComodification() {
        if (this.modCount != tree.modCount) {
            throw new ConcurrentModificationException();
        }
    }
}