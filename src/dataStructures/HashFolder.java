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

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * This class provides a convenient way to calculate a hash value over
 * complicated collections. The {@link #fold(Object)} and
 * {@link #bulkFold(Iterator)} methods allow users to fold in objects or
 * elements of a collection to calculate an overall hash value. The calculated
 * hash value is retrieved by calling the {@link #getHashValue()} method.
 * <p>
 * The basic equation used in folding is as follows:
 * <ul>
 * {@code value = 
 *      prime * value + ((object == null) ? 0 : object.hashCode())}
 * </ul>
 * </p>
 * 
 * @author Drew Reese
 * @version 1.0
 * @since JDK1.8
 */
public final class HashFolder {

    // Cache of first 100-ish prime numbers greater than 30 for constructor use
    protected static final int[] PRIMES = { 31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139,
            149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211,
            223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281,
            283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367,
            373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443,
            449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523,
            541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601 };

    protected final int prime; // Prime value used in hashing
    protected int       value; // Current value of this hash folder

    /**
     * Constructs new HashFolder with a random seed and initial value of 1.
     */
    public HashFolder() {
        this(1);
    }

    /**
     * Constructs new HashFolder with specified initial value and random seed.
     * 
     * @param initialValue - initial value of this hash folder
     * @throws IllegalArgumentException if specified initial value is less than
     *             1
     */
    public HashFolder(int initialValue) throws IllegalArgumentException {
        this.value = checkPositive(initialValue);
        this.prime = PRIMES[(new Random()).nextInt(PRIMES.length)];
    }

    /**
     * Constructs new HashFolder with specified initial value and specified
     * seed.
     * 
     * @param initialValue - initial value of this hash folder
     * @param seed - prime hash seed value
     * @throws IllegalArgumentException if specified initial value is less than
     *             1, or if the specified seed is not a prime number
     */
    public HashFolder(int initialValue, int seed)
            throws IllegalArgumentException {
        this.value = checkPositive(initialValue);
        this.prime = checkPrime(seed);
    }

    /**
     * Checks and validates specified value is a prime number.
     * 
     * @param value - value to check for prime-ness
     * @return specified value if prime
     * @throws IllegalArgumentException if specified value is not a prime number
     */
    private int checkPrime(int value) throws IllegalArgumentException {
        // check if value < 2 or is a multiple of 2 other than 2
        if (value < 2 || (value != 2 && value % 2 == 0)) {
            throw new IllegalArgumentException(value + " is not a valid prime");
        } else {
            // if not, then just check the odds
            for (int i = 3; i <= value / 2; i += 2) {
                if (value % i == 0) {
                    throw new IllegalArgumentException(
                            value + " is not a valid prime");
                }
            }
        }
        return value;
    }

    /**
     * Checks and validates specified value is a positive integer
     * <code>[1, {@link Integer#MAX_VALUE}]</code>.
     * 
     * @param value - value to check if positive
     * @return specified value if positive
     * @throws IllegalArgumentException if specified value is not positive, i.e.
     *             ({@code value <= 0})
     */
    private int checkPositive(int value) throws IllegalArgumentException {
        if (value <= 0) {
            throw new IllegalArgumentException(value + " is not positive");
        }
        return value;
    }

    /**
     * Folds object into this hash folder. Extracts objects hashCode or
     * {@code 0} if {@code object == null}.
     * 
     * @param object - object to be folded into this hash folder
     */
    public void fold(Object object) {
        value = prime * value + ((object == null) ? 0 : object.hashCode());
    }

    /**
     * Folds a collection's elements into this hash folder.
     * 
     * @param c - the collection of elements to fold into this hash folder
     */
    public void bulkFold(Collection<?> c) {
        if (c != null) {
            for (Object o : c) {
                fold(o);
            }
        }
    }

    /**
     * Retuns this hash folder's current calculated hash value.
     * 
     * @return this hash folder's current calculated hash value
     */
    public int getHashValue() {
        return this.value;
    }

}
