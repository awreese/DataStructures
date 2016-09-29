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

/**
 * <p>
 * An individual tree node. Tree nodes connect and form an ordered collection of
 * tree nodes (also known as a tree).
 * </p>
 * <p>
 * A single {@code TreeNode} is collectively defined as consisting of a single
 * data element, a single parent {@code TreeNode}, and zero or more children
 * {@code TreeNodes} (a child collection of tree nodes).
 * </p>
 * <p>
 * The {@code TreeNode} interface places additional stipulations, beyond those
 * specified in the Collection interface, on the contracts of the iterator, add,
 * remove, equals, and hashCode methods. Declarations for other inherited
 * methods are also included here for convenience.
 * </p>
 * <p>
 * <b>Note</b>: While it is permissible for tree nodes to contain themselves as
 * elements, extreme caution is advised: the equals and hashCode methods are no
 * longer well defined on such a tree node.
 * </p>
 * <p>
 * Some tree node implementations have restrictions on the elements that they
 * may contain. For example, some implementations prohibit null elements, and
 * some have restrictions on the types of their elements. Attempting to add an
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
 * @param <T> the type of elements in this tree node
 * @author Drew Reese
 * @version 1.0
 * @see java.util.Collection
 */
public interface TreeNode<T> extends Collection<TreeNode<T>> {
    // Query Operations

    /**
     * Returns the total count of this tree node plus all descendant elements in
     * this tree node collection. If this collection contains more than
     * {@code Integer.MAX_VALUE} elements, returns {@code Integer.MAX_VALUE}.
     * 
     * @return the count of this node plus all descendant elements in this
     *         collection
     * @see java.util.Collection#size()
     */
    @Override
    int size();

    /**
     * Returns the count of children of this tree node.
     * 
     * @return the count of children of this tree node
     */
    int childCount();

    /**
     * Returns {@code true} if this collection contains no children elements.
     * 
     * @return {@code true} if this collection contains no children elements
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

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#iterator()
     */
    @Override
    Iterator<TreeNode<T>> iterator();

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

    /**
     * Returns a collection of children tree nodes of this tree node.
     * 
     * @return a collection of children tree nodes of this tree node
     */
    Collection<TreeNode<T>> getChildren();

    /**
     * Returns the data stored at this tree node.
     * 
     * @return the data stored at this tree node
     */
    T getData();

    /**
     * Returns the parent tree node of this tree node.
     * 
     * @return the parent tree node of this tree node
     */
    TreeNode<T> getParent();

    /**
     * Returns {@code true} if this tree node has any children tree nodes.
     * 
     * @return {@code true} if this tree node has any children tree nodes
     */
    boolean hasChildren();

    /**
     * Returns {@code true} if this tree node has parent tree node.
     * 
     * @return {@code true} if this tree node has parent tree node
     */
    boolean hasParent();

    // Modification Operations

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#add(java.lang.Object)
     */
    @Override
    boolean add(TreeNode<T> e);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#remove(java.lang.Object)
     */
    @Override
    boolean remove(Object o);

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

    /**
     * Removes all of the child elements from this collection (optional
     * operation). The child collection will be empty after this method returns.
     * 
     * @see java.util.Collection#clear()
     */
    @Override
    void clear();

    /**
     * Replaces the data stored in this tree node with the specified data
     * (optional operation).
     * 
     * @param data - data to be stored in this tree node
     * @return data previously stored in this tree node
     * @throws UnsupportedOperationException - if the setData operation is not
     *             supported by this tree node
     * @throws ClassCastException - if the class of the specified data prevents
     *             it from being added to this tree node
     * @throws NullPointerException - if the specified data is null and this
     *             tree node does not permit null data
     * @throws IllegalArgumentException - if some property of the specified data
     *             prevents it from being added to this tree node
     */
    T setData(Object data) throws UnsupportedOperationException,
            ClassCastException, NullPointerException, IllegalArgumentException;

    /**
     * Sets the parent tree node of this tree node (optional operation).
     * 
     * @param parent - the new parent tree node to set to
     * @throws UnsupportedOperationException - if the setParent operation is not
     *             supported by this tree node
     * @throws NullPointerException - if the specified parent is null
     */
    void setParent(TreeNode<T> parent)
            throws UnsupportedOperationException, NullPointerException;

    /**
     * Removes and returns a collection of children tree nodes of this tree
     * node.
     * 
     * @return a collection of children tree nodes of this tree node
     * @throws UnsupportedOperationException - if the removeChildren operation
     *             is not supported by this tree node
     */
    Collection<TreeNode<T>> removeChildren()
            throws UnsupportedOperationException;

    // Comparison and Hashing

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#equals(java.lang.Object)
     */
    @Override
    boolean equals(Object o);

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Collection#hashCode()
     */
    @Override
    int hashCode();

}
