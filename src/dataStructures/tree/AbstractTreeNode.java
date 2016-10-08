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

import dataStructures.HashFolder;

/**
 * This class provides a skeletal implementation of the {@link TreeNode}
 * interface to minimize the effort required to implement this interface.
 * <p>
 * To implement an unmodifiable tree node, the programmer needs only to extend
 * this class and provide implementations for the {@link #size()},
 * {@link #childCount()}, {@link #getChildren()}, {@link #getParent()}, and
 * {@link #hasParent()} methods.
 * </p>
 * <p>
 * To implement a modifiable tree, the programmer must additionally override the
 * {@link #add(TreeNode)}, {@link #remove(Object)}, {@link #setData(Object)},
 * {@link #setParent(TreeNode)} methods (which otherwise throw an
 * {@code UnsupportedOperationException}).
 * </p>
 * <p>
 * The programmer should generally provide a void (no argument) and collection
 * constructor, as per the recommendation in the {@link Collection} interface
 * specification.
 * </p>
 * <p>
 * The programmer does <i>not</i> have to provide an iterator implementation.
 * </p>
 * 
 * @param <T> the type of elements stored in this tree node
 * @author Drew Reese
 * @version 1.0
 * @since JDK1.8
 */
public abstract class AbstractTreeNode<T> implements TreeNode<T> {

    protected T data;

    /**
     * Sole constructor. (For invocation by subclass constructors, typically
     * implicit.)
     */
    public AbstractTreeNode() {
    }

    // Modification Operations

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#add(dataStructures.tree.TreeNode)
     */
    @Override
    public boolean add(TreeNode<T> t) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#remove(java.lang.Object)
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#setData(java.lang.Object)
     */
    @Override
    public T setData(Object data) throws UnsupportedOperationException,
            ClassCastException, NullPointerException, IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#setParent(dataStructures.tree.TreeNode)
     */
    @Override
    public void setParent(TreeNode<T> parent)
            throws UnsupportedOperationException, NullPointerException {
        throw new UnsupportedOperationException();
    }

    // Query Operations

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#size()
     */
    @Override
    public abstract int size();

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#childCount()
     */
    @Override
    public abstract int childCount();

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return getChildren().isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#getChildren()
     */
    @Override
    public abstract Collection<TreeNode<T>> getChildren();

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#getData()
     */
    @Override
    public T getData() {
        return this.data;
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#getParent()
     */
    @Override
    public abstract TreeNode<T> getParent();

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#hasChildren()
     */
    @Override
    public boolean hasChildren() {
        return !isEmpty();
    }

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#hasParent()
     */
    @Override
    public abstract boolean hasParent();

    // Bulk Operations

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#removeChildren()
     */
    @Override
    public abstract Collection<TreeNode<T>> removeChildren()
            throws UnsupportedOperationException;

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.tree.TreeNode#iterator()
     */
    @Override
    public Iterator<TreeNode<T>> iterator() {
        return getChildren().iterator();
    }

    /*
     * Prime number seed used for hashing values 
     */
    static final int PRIME = 31;
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        HashFolder hashFolder = new HashFolder(1, PRIME);
        hashFolder.fold(getParent());
        hashFolder.fold(data);
        hashFolder.bulkFold(getChildren());
        return hashFolder.getHashValue();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractTreeNode)) {
            return false;
        }
        
        AbstractTreeNode<?> other = (AbstractTreeNode<?>) obj;
        
        if (getParent() != other.getParent()) {
            return false;
        }
        
        if (data == null) {
            if (other.data != null) {
                return false;
            }
        } else if (!data.equals(other.data)) {
            return false;
        }
        
        if (!getChildren().equals(other.getChildren())) {
            return false;
        }
        return true;
    }

}
