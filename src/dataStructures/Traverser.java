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

package dataStructures;

import java.util.NoSuchElementException;

/**
 * <p>
 * A traverser over/through a collection.
 * </p>
 * 
 * Traversers differ from iterators:
 * <ul>
 * <li>Traversers allow the caller to add, set, and remove elements from the
 * underlying collection</li>
 * <li>Classes implementing this interface define how to traverse over or
 * through a collection (i.e. {@link java.util.ListIterator} produces traverser
 * behavior)</li>
 * <li>Can not (and should not) be used in for-each-loops (use iterators!)</li>
 * </ul>
 * 
 * @param <T> the type of elements returned by this traverser
 * @author Drew Reese
 * @version 1.0
 * @since JDK1.8
 */
public interface Traverser<T> {

    // Query Operations
    /**
     * Returns {@code true} if this traverser has more elements when traversing
     * the collection in the specified traversal type direction. (In other
     * words, returns {@code true} if {@link #next(TraversalType)} would return
     * an element rather than throwing an exception.)
     * 
     * @param type - the traversal direction type
     * @return {@code true} if the traverser has more elements when traversing
     *         the collection in the specified traversal type direction
     * @throws IllegalArgumentException if the specified traversal type is
     *             incompatible with this traverser
     */
    public boolean hasNext(TraversalType type) throws IllegalArgumentException;

    /**
     * Returns the next element in the specified traversal type direction in the
     * collection and advances the current element.
     * 
     * @param type - the traversal direction type
     * @return the next element in the specified traversal type direction from
     *         the collection
     * @throws IllegalArgumentException if the specified traversal type is
     *             incompatible with this traverser
     * @throws NoSuchElementException if the traverser has no next element
     */
    public T next(TraversalType type)
            throws IllegalArgumentException, NoSuchElementException;

    // Modification Operations
    // Note: modification operations DO NOT advance the traverser to a new
    // element unless the modification causes the removal of the current element
    // (i.e. remove()).

    /**
     * Replaces the element of the current position of this traverser with the
     * specified element (optional operation).
     * 
     * @param t - the element with which to replace the element of the current
     *            position of traverser
     * @throws UnsupportedOperationException if the {@code set} operation is not
     *             supported by this traverser or underlying collection
     * @throws ClassCastException if the class of the specified element prevents
     *             it from being added to the underlying collection
     * @throws IllegalArgumentException if some aspect of the specified element
     *             prevents it from being added to the underlying collection
     * @throws IllegalStateException if the current position object is null or
     *             otherwise non-existent
     */
    <TT> void setData(TT data) throws UnsupportedOperationException, ClassCastException,
            IllegalArgumentException, IllegalStateException;

    /**
     * Adds the specified element into the underlying collection (optional
     * operation).
     * 
     * If collection contains no elements, the new element becomes the sole
     * element in the underlying collection.
     * 
     * @param t - the element to add to collection
     * @throws UnsupportedOperationException if the {@code add} method is not
     *             supported by this traverser
     * @throws ClassCastException if the class of the specified element prevents
     *             it from being added to the underlying collection
     * @throws IllegalArgumentException if some aspect of this element prevents
     *             it from being added to the underlying collection
     */
    void add(T t) throws UnsupportedOperationException, ClassCastException,
            IllegalArgumentException, IllegalStateException;

    /**
     * Removes from the collection the element at the current position of this
     * traverser (optional operation).
     * 
     * @throws UnsupportedOperationException if the {@code remove} operation is
     *             not supported by this traverser
     * @throws IllegalStateException if the current position object is null or
     *             otherwise non-existent
     */
    void remove() throws UnsupportedOperationException, IllegalStateException;
}
