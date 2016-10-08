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

package dataStructures.tree.traverser;

import java.util.NoSuchElementException;

import dataStructures.Traverser;
import dataStructures.TraversalType;

/**
 * <p>
 * An traverser for trees that allows the programmer to traverse the tree in any
 * direction, and modify the tree during iteration.
 * </p>
 * 
 * <pre>
 *                             |
 *                        Next Parent
 *                             ║
 *                    ┌────────╨────────┐
 * ... Prev Sibling ══╡  current node*  ╞══ Next Sibling ...
 *                    └────────╥────────┘
 *                             ║
 *                         Next Child
 *                       / ... | ... \
 * </pre>
 * 
 * <p>
 * <b>*</b> Note that these methods operate on the current node:
 * {@link #set(Object)}, {@link #add(Object)}, {@link #insert(Object)},
 * {@link #remove()}, and {@link #trim()}. They are defined to operate on the
 * last tree node to returned by a call to {@code nextXXX} or {@code prevXXX}.
 * </p>
 * 
 * @param <T> the type of elements in this tree node
 * @author Drew Reese
 * @version 1.0
 * @since JDK1.8
 */
public interface TreeTraverser<T> extends Traverser<T> {

    public static enum TreeTraversalType implements TraversalType {
        PARENT, SIBLING_PREV, SIBLING_NEXT, CHILD;
    }

    // Query Operations

    /**
     * Returns {@code true} if this tree traverser has more elements when
     * traversing the tree in the specified traversal type direction. (In other
     * words, returns {@code true} if {@link #next(TraversalType)} would return
     * a tree node rather than throwing an exception.)
     *
     * @return {@code true} if the tree traverser has more nodes when traversing
     *         the tree in the specified traversal type direction
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    boolean hasNext(TraversalType type) throws IllegalArgumentException;

    /*
     * (non-Javadoc)
     * 
     * @see dataStructures.Traverser#next(dataStructures.TraversalType)
     */
    @Override
    T next(TraversalType type)
            throws IllegalArgumentException, NoSuchElementException;

    // Modification Operations
    // Note: modification operations DO NOT advance the tree iterator to a new
    // tree node unless the modification causes the removal of the current tree
    // node (i.e. remove() or trim()).

    /**
     * Replaces the data element of the current tree node of this tree traverser
     * with the specified data (optional operation).
     * 
     * @param t - the data element with which to replace the last element
     *            returned by {@code next} or {@code prev}
     * @throws UnsupportedOperationException if the {@code set} operation is not
     *             supported by this tree traverser
     * @throws ClassCastException if the class of the specified data element
     *             prevents it from being added to this tree
     * @throws IllegalArgumentException if some aspect of the specified data
     *             element prevents it from being added to this tree
     * @throws IllegalStateException if the current tree node is null or
     *             otherwise non-existent
     */
    @Override
    <TT> void setData(TT data) throws UnsupportedOperationException,
            ClassCastException, IllegalArgumentException, IllegalStateException;

    /**
     * <p>
     * Adds the specified element into the tree as a child of the current tree
     * node of this tree traverser (optional operation).
     * </p>
     * <p>
     * Example with traverser currently pointing to node {@code n2}:
     * <ul>
     * <li>Tree before addition: ... -> {@code n1} -> [<b>({@code n2})</b> ->
     * [{@code n3} -> ..., {@code n4} -> ...], ...]</li>
     * <li>{@code Traverser.add(n5)}</li>
     * <li>Tree after addition: ... -> {@code n1} -> [<b>({@code n2})</b> ->
     * [{@code n3} -> ..., {@code n4} -> ..., <i><b>{@code n5}</b></i>],
     * ...]</li>
     * <li>Tree traverser still currently points to node {@code n2}</li>
     * </ul>
     * </p>
     * <p>
     * <b>Note:</b> If tree contains no elements, the new element becomes the
     * sole element in the tree.
     * </p>
     * 
     * @param t - the element to add to tree as child of current tree node
     * @throws UnsupportedOperationException if the {@code add} method is not
     *             supported by this tree traverser
     * @throws ClassCastException if the class of the specified element prevents
     *             it from being added to this tree
     * @throws IllegalArgumentException if some aspect of this element prevents
     *             it from being added to this tree
     */
    @Override
    void add(T t) throws UnsupportedOperationException, ClassCastException,
            IllegalArgumentException;

    /**
     * <p>
     * Removes from the tree the element at the current position of this
     * traverser and moves current position to node parent (optional operation).
     * </p>
     * <p>
     * The parent node of the current node of this tree traverser inherits the
     * children of the removed node.
     * </p>
     * <p>
     * Example with traverser currently pointing to node {@code n2}:
     * <ul>
     * <li>Tree before removal: ... -> {@code n1} -> [<b>({@code n2})</b> ->
     * [{@code n3} -> ..., {@code n4} -> ...], {@code n5}, ...]</li>
     * <li>{@code Traverser.remove()}</li>
     * <li>Tree after removal: ... -> <i><b>({@code n1})</b></i> -> [{@code n3}
     * -> ..., {@code n4} -> ..., {@code n5}, ...]</li>
     * <li>Tree traverser currently points to node {@code n1}</li>
     * </ul>
     * </p>
     * 
     * @throws UnsupportedOperationException if the {@code remove} operation is
     *             not supported by this tree traverser
     * @throws IllegalStateException if the current position object is null or
     *             otherwise non-existent
     */
    @Override
    void remove() throws UnsupportedOperationException, IllegalStateException;

    /**
     * <p>
     * Inserts the specified element into the tree as the parent of the current
     * tree node of this tree traverser (optional operation).
     * </p>
     * <p>
     * Example with traverser currently pointing to node {@code n2}:
     * <ul>
     * <li>Tree before insertion: ... -> {@code n1} -> <b>({@code n2})</b> ->
     * {@code n3} -> ...</li>
     * <li>{@code Traverser.insert(n4)}</li>
     * <li>Tree after insertion: ... -> {@code n1} -> <i><b>{@code n4}</b></i>
     * -> <b>({@code n2})</b> -> {@code n3} -> ...</li>
     * <li>Tree traverser still currently points to node {@code n2}</li>
     * </ul>
     * </p>
     * <p>
     * <b>Note:</b> If tree contains no elements, the new element becomes the
     * sole element in the tree.
     * </p>
     * 
     * @param t - the element to insert into tree as parent of current tree node
     * @throws UnsupportedOperationException if the {@code insert} operation is
     *             not supported by this tree traverser
     * @throws ClassCastException if the class of the specified element prevents
     *             it from being added to this tree
     * @throws IllegalArgumentException if some aspect of this element prevents
     *             it from being added to this tree
     */
    void insert(T t) throws UnsupportedOperationException, ClassCastException,
            IllegalArgumentException;

    /**
     * <p>
     * Removes from the tree the element at the current position of this
     * traverser and all descendants and moves current position to node parent
     * (optional operation).
     * </p>
     * <p>
     * Example with traverser currently pointing to node {@code n2}:
     * <ul>
     * <li>Tree before trim: ... -> {@code n1} -> [<b>({@code n2})</b> ->
     * [{@code n3} -> ..., {@code n4} -> ...], {@code n5}, ...]</li>
     * <li>{@code Traverser.trim()}</li>
     * <li>Tree after trim: ... -> <i><b>({@code n1})</b></i> -> [{@code n5},
     * ...]</li>
     * <li>Tree traverser currently points to node {@code n1}</li>
     * </ul>
     * </p>
     * 
     * @throws UnsupportedOperationException if the {@code trim} operation is
     *             not supported by this tree traverser
     * @throws IllegalStateException if the current position object is null or
     *             otherwise non-existent
     */
    void trim() throws UnsupportedOperationException, IllegalStateException;

}
