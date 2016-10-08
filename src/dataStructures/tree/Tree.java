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

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import dataStructures.Traversable;
import dataStructures.TraversalOrder;
import dataStructures.Traverser;

/**
 * <p>
 * An ordered collection of tree nodes (also known as a tree). This interface
 * allows users to reason about a collection of tree nodes as a whole; in other
 * words, it allows a user to abstractly use a tree without worrying about the
 * underlying node structure as much.
 * </p>
 * <p>
 * The {@code Tree} interface places additional stipulations, beyond those
 * specified in the Collection interface, on the contracts of the iterator, add,
 * remove, equals, and hashCode methods. Declarations for other inherited
 * methods are also included here for convenience.
 * </p>
 * <p>
 * The {@code Tree} interface provides a special iterator, called a
 * TreeTraverser, that allows element insertion, replacement, and removal, and
 * unidirectional access in exchange of the normal operations that the Iterator
 * interface provides. A method is provided to obtain a tree iterator that
 * starts at a specified position in the tree.
 * </p>
 * <p>
 * <b>Note</b>: While it is permissible for trees to contain themselves as
 * elements, extreme caution is advised: the equals and hashCode methods are no
 * longer well defined on such a tree.
 * </p>
 * <p>
 * Some tree implementations have restrictions on the elements that they may
 * contain. For example, some implementations prohibit null elements, and some
 * have restrictions on the types of their elements. Attempting to add an
 * ineligible element throws an unchecked exception, typically
 * NullPointerException or ClassCastException. Attempting to query the presence
 * of an ineligible element may throw an exception, or it may simply return
 * false; some implementations will exhibit the former behavior and some will
 * exhibit the latter. More generally, attempting an operation on an ineligible
 * element whose completion would not result in the insertion of an ineligible
 * element into the list may throw an exception or it may succeed, at the option
 * of the implementation. Such exceptions are marked as "optional" in the
 * specification for this interface.
 * </p>
 * 
 * @param <T> the type of elements in this tree
 * @author Drew Reese
 * @version 1.0
 * @since JDK1.8
 * @see java.util.Collection
 * @see dataStructures.Traversable
 * @see dataStructures.tree.TreeNode
 */
public interface Tree<T>
        extends Collection<TreeNode<T>>, Traversable<TreeNode<T>> {
    // Query Operations

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#size()
     */
    @Override
    int size();

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#isEmpty()
     */
    @Override
    boolean isEmpty();

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#contains(java.lang.Object)
     */
    @Override
    boolean contains(Object o);

    /**
     * Returns tree node containing specified data, null if not found.
     * 
     * @param data - element data to search for
     * @return tree node element containing data, null if not found
     * @throws ClassCastException if the type of the specified element data is
     *             incompatible with this tree (optional)
     * @throws NullPointerException if the specified element data is null and
     *             this tree does not permit null element data (optional)
     */
    TreeNode<T> getNode(Object data)
            throws ClassCastException, NullPointerException;

    /**
     * Returns the root of this tree.
     * 
     * @return the root of this tree, null if tree is empty
     */
    TreeNode<T> getRoot();

    /**
     * Returns sub-tree view rooted at the specified parent node.
     * 
     * @param parent - node in this tree to return sub-tree from
     * @return tree view rooted at specified parent node, null if parent node
     *         non-existent in this tree
     */
    Tree<T> subTree(TreeNode<T> parent);

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.Traversable#iterator()
     */
    @Override
    Iterator<TreeNode<T>> iterator();

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.Traversable#iterator(dataStructures.TraversalOrder)
     */
    @Override
    Iterator<TreeNode<T>> iterator(TraversalOrder order);

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.Traversable#traverser()
     */
    @Override
    Traverser<TreeNode<T>> traverser();

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.Traversable#traverser(java.lang.Object)
     */
    @Override
    Traverser<TreeNode<T>> traverser(TreeNode<T> t)
            throws NoSuchElementException;

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#toArray()
     */
    @Override
    Object[] toArray();

    /*
     * (non-Javadoc)
     *
     * @see java.util.Collection#toArray(java.lang.Object[])
     */
    @Override
    <TT> TT[] toArray(TT[] a);

    // Modification Operations

    /**
     * Appends the specified node to this tree as a child this node specified as
     * its parent node (optional operation).
     * 
     * @param t - the tree node being added
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the {@code add} operation is not
     *             supported by this tree
     * @throws ClassCastException if the class of the specified element prevents
     *             it from being added to this tree
     * @throws NullPointerException if the specified element is null and this
     *             tree does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *             prevents it from being added to this tree
     */
    @Override
    boolean add(TreeNode<T> t);

    /**
     * Adds child node element to the specified parent node element in this tree
     * (optional operation).
     * 
     * @param parent - the parent node to add this child node to
     * @param child - the child node being added
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the {@code add} operation is not
     *             supported by this tree
     * @throws ClassCastException if the class of the specified element prevents
     *             it from being added to this tree
     * @throws NullPointerException if the specified element is null and this
     *             tree does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *             prevents it from being added to this tree
     */
    boolean add(TreeNode<T> parent, TreeNode<T> child)
            throws UnsupportedOperationException, ClassCastException,
            NullPointerException, IllegalArgumentException;

    /**
     * Inserts parent node element as the parent to the specified node element
     * in this tree (optional operation).
     * 
     * @param node - the node to insert this parent node to
     * @param parent - the new parent node being inserted
     * @return {@code true} if this tree changed as a result of the call
     * @throws UnsupportedOperationException if the {@code insert} operation is
     *             not supported by this tree
     * @throws ClassCastException if the class of the specified element prevents
     *             it from being inserted into this tree
     * @throws NullPointerException if the specified element is null and this
     *             tree does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *             prevents it from being inserted into this tree
     */
    boolean insert(TreeNode<T> node, TreeNode<T> newNode)
            throws UnsupportedOperationException, ClassCastException,
            NullPointerException, IllegalArgumentException;

    /**
     * Removes the first occurrence of the specified element from this tree, if
     * it is present (optional operation). If this tree does not contain the
     * element, it is unchanged. More formally, removes the element {@code t}
     * found first with a depth first traversal such that
     * {@code (o==null ? t==null : o.equals(t))} (if such an element exists).
     * Returns {@code true} if this tree contained the specified element (or
     * equivalently, if this tree changed as a result of the call).
     *
     * @param o element to be removed from this tree, if present
     * @return {@code true} if this tree contained the specified element
     * @throws UnsupportedOperationException if the {@code remove} operation is
     *             not supported by this tree
     * @throws ClassCastException if the type of the specified element is
     *             incompatible with this tree (optional)
     * @throws NullPointerException if the specified element is null and this
     *             tree does not permit null elements (optional)
     */
    @Override
    boolean remove(Object o) throws UnsupportedOperationException,
            ClassCastException, NullPointerException;

    /**
     * Sets the root of this tree.
     * 
     * @param root the root value to set for the tree
     */
    void setRoot(TreeNode<T> root);

    // Bulk Modification Operations

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#containsAll(java.util.Collection)
     */
    @Override
    boolean containsAll(Collection<?> c);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#addAll(java.util.Collection)
     */
    @Override
    boolean addAll(Collection<? extends TreeNode<T>> c);

    /**
     * Adds a sub-tree to this tree at the specified parent node.
     * 
     * @param parent - node in this tree to add sub-tree to
     * @param subTree - Tree to add to this tree
     */
    void addSubtree(TreeNode<T> parent, Tree<T> subTree);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#removeAll(java.util.Collection)
     */
    @Override
    boolean removeAll(Collection<?> c);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#retainAll(java.util.Collection)
     */
    @Override
    boolean retainAll(Collection<?> c);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#clear()
     */
    @Override
    void clear();

    /**
     * Removes from this tree the sub-tree rooted at the specified parent node.
     * 
     * @param parent - node in this tree to remove sub-tree from
     * @return {@code true} if this collection changed as a result of the call
     */
    boolean trim(TreeNode<T> parent);

    // Comparison and Hashing

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#equals(java.lang.Object)
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns the hash code value for this tree. The hash code of a tree is
     * defined to be the result of the following calculation:
     * 
     * <code>
     * <pre>
     *  final int prime = 43;
     *  int hashCode = 1;
     *  for (TreeNode<T> node : this) {
     *      hashCode = prime * hashCode + (node == null ? 0 : node.hashCode());
     *  }</pre>
     *  </code>
     * 
     * @return the hash code value for this tree
     */
    @Override
    int hashCode();

}
