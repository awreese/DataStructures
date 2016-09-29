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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Implementing this interface allows an object to be iterable and traversable
 * using user-defined traversals.
 * </p>
 * <p>
 * The traversable interface extends the iterable interface to force traversable
 * collections to provide an {@code iterator} that takes a traversal order as a
 * parameter and to also provide a traverser that allows programmatically
 * controlled traversing over and through collections.
 * </p>
 * 
 * @param <T> - the type of elements returned by the iterator and traverser
 * @author Drew Reese
 * @version 1.0
 */
public interface Traversable<T> extends Iterable<T> {

    /**
     * Returns an iterator over the elements in this collection, guaranteed to
     * be in the default order specified by a collection's traversable order
     * type.
     * 
     * @return an {@code Iterator} over the elements in this collection in the
     *         default traversal order
     * @see java.util.Collection#iterator()
     * @see dataStructures.TraversalOrder
     */
    @Override
    Iterator<T> iterator();

    /**
     * Returns an iterator over the elements in this collection, guaranteed to
     * be in the order specified by a traversal order.
     * 
     * @param order - the traversal order of this iterator
     * @return an {@code Iterator} over the elements in this collection in the
     *         specified traversal order
     * @see java.util.Collection#iterator()
     * @see dataStructures.TraversalOrder
     */
    Iterator<T> iterator(TraversalOrder order);

    /**
     * Returns a traverser over the elements in this collection, starting at a
     * default element within the collection.
     * 
     * @return a {@code Traverser} over the elements in this collection starting
     *         at a default element
     */
    Traverser<T> traverser();

    /**
     * Returns a traverser over the elements in this collection, starting at the
     * specified element within the collection.
     * 
     * @param t - the starting element of this traverser
     * @return a {@code Traverser} over the elements in this collection starting
     *         at the specified element
     * @throws NoSuchElementException if element t doesn't exist in collection
     */
    Traverser<T> traverser(T t) throws NoSuchElementException;

}
