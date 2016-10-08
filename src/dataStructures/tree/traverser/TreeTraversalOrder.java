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

import dataStructures.TraversalOrder;

/**
 * Common tree traversal orders.
 * 
 * <ul>
 * <li>Depth-first</li>
 * <ul>
 * <li>{@link #PRE_ORDER}</li>
 * <li>{@link #IN_ORDER}</li>
 * <li>{@link #POST_ORDER}</li>
 * </ul>
 * <li>Breadth-first</li>
 * <ul>
 * <li>{@link #LEVEL_ORDER}</li>
 * </ul>
 * </ul>
 * 
 * @author Drew Reese
 * @version 1.0
 * @since JDK1.8
 * @see TraversalOrder
 * @see <a href= "https://en.wikipedia.org/wiki/Tree_traversal">
 *      https://en.wikipedia.org/wiki/Tree_traversal</a>
 */
public enum TreeTraversalOrder implements TraversalOrder{
    /**
     * Pre-order
     * 
     * <ol>
     * <li>Display node data</li>
     * <li>Traverse sub-trees (typically left-to-right by convention)</li>
     * </ol>
     * 
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Tree_traversal#Depth-first_search">
     *      https://en.wikipedia.org/wiki/Tree_traversal#Depth-first_search</a>
     */
    PRE_ORDER,

    /**
     * In-order
     * 
     * <ol>
     * <li>Traverse left sub-tree(s) (typically left-to-right by
     * convention)</li>
     * <li>Display node data</li>
     * <li>Traverse right sub-tree(s) (typically left-to-right by
     * convention)</li>
     * </ol>
     * 
     * Note: In-order traversals generally work well for tree structures where
     * nodes have an even number of sub-trees (i.e. Binary trees), but is not
     * defined well for trees consisting of an odd number of sub-trees (i.e.
     * Trinary Tree). As such, some tree implementations may choose to not
     * implement an In-order traversal at all, or throw an exception (i.e.
     * UnsupportedOperationException).
     * 
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Tree_traversal#Depth-first_search">
     *      https://en.wikipedia.org/wiki/Tree_traversal#Depth-first_search</a>
     */
    IN_ORDER,

    /**
     * Post-order
     * 
     * <ol>
     * <li>Traverse sub-trees (typically left-to-right by convention)</li>
     * <li>Display node data</li>
     * </ol>
     * 
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Tree_traversal#Depth-first_search">
     *      https://en.wikipedia.org/wiki/Tree_traversal#Depth-first_search</a>
     */
    POST_ORDER,

    /**
     * Level-order
     * 
     * <ol>
     * <li>Traverse nodes at an equal depth (typically left-to-right by
     * convention) before traversing to a deeper depth</li>
     * </ol>
     * 
     * @see <a href=
     *      "https://en.wikipedia.org/wiki/Tree_traversal#Breadth-first_search">
     *      https://en.wikipedia.org/wiki/Tree_traversal#
     *      Breadth-first_search</a>
     */
    LEVEL_ORDER;
    
    /**
     * Returns a default traversal order for trees.
     * 
     * @return default tree traversal order
     */
    public static TraversalOrder defaultOrder() {
        return TreeTraversalOrder.LEVEL_ORDER;
    }

}
