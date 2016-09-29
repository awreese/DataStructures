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

/**
 * Common data structure traversal order interface. Declared solely for the
 * purpose of extensibility.
 * 
 * <p>
 * Data structures provide their own traversal order ENUM type definitions
 * for use with iterators, but this allows programmers to override and
 * define their own traversal types.
 * </p>
 * <b>NOTE:</b> When implementing new traversal order enums
 * <ol>
 * <li>The underlying data structure's iterator's
 * {@link java.util.Iterator#hasNext() hasNext()} and
 * {@link java.util.Iterator#next() next()} methods <i>must</i> be
 * overridden to extend or replace the traversal order types.</li>
 * <li>Although not a requirement, new traversal order enums should by
 * convention include a static {@code defaultOrder()} method.</li>
 * </ol>
 * 
 * <p>
 * Example Usage:
 * 
 * <pre>
 * <code>public enum MazeTraversalOrder implements TraversalOrder {
 *      // Always make right turns in a maze to find exit
 *      RIGHT_TURN,
 *      
 *      // Always make left turns in a maze to find exit
 *      LEFT_TURN;
 *      
 *      // Returns default maze traversal.
 *      public static TraversalOrder defaultOrder() {
 *          return MazeTraversalOrder.RIGHT_TURN;
 *      }
 * }</code>
 * </pre>
 * </p>
 * 
 * @author Drew Reese
 * @version 1.0
 */
public interface TraversalOrder {
}
