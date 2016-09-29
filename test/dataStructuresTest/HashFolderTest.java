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

package dataStructuresTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import dataStructures.HashFolder;

/**
 * HashFolder Unit Testing
 * 
 * @author Drew
 */
public class HashFolderTest {

    /*
     * Lists of the first 100-ish prime and non-prime numbers used in testing.
     */
    private static final int[] PRIMES = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
            31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101,
            103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167,
            173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239,
            241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313,
            317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397,
            401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467,
            479, 487, 491, 499, 503, 509, 521, 523, 541 };

    private static final int[] NON_PRIMES = { 0, 1, 4, 6, 8, 9, 10, 12, 14, 15,
            16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36, 38,
            39, 40, 42, 44, 45, 46, 48, 49, 50, 51, 52, 54, 55, 56, 57, 58, 60,
            62, 63, 64, 65, 66, 68, 69, 70, 72, 74, 75, 76, 77, 78, 80, 81, 82,
            84, 85, 86, 87, 88, 90, 91, 92, 93, 94, 95, 96, 98, 99, 100, 102,
            104, 105, 106, 108, 110, 111, 112, 114, 115, 116, 117, 118, 119,
            120, 121, 122, 123, 124, 125, 126, 128, 129, 130, 132, 133, 134,
            135, 136, 138, 140, 141, 142, 143, 144, 145, 146, 147, 148, 150 };

    /**
     * Tests constructor given valid input parameters. No exceptions should be
     * thrown. Input validation occurs in other tests.
     */
    @Test
    public void constructor() {
        new HashFolder();
        new HashFolder(2);
        new HashFolder(2, 3);
    }

    /**
     * Tests that illegal argument exceptions are thrown for invalid initial
     * values provided to the constructor. Valid input is all positive integers
     * in [1, {@link Integer#MAX_VALUE}]; invalid input is all integers in
     * [{@link Integer#MIN_VALUE}, 0]. Both the single and double argument
     * constructors are tested.
     */
    @Test
    public void checkPositiveInitialValueTest() {
        // test exception is thrown for non-positive integers (1-arg)
        try {
            new HashFolder(-1);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
        try {
            new HashFolder(0);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
        try {
            new HashFolder(Integer.MIN_VALUE);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }

        // test exception is thrown for non-positive integers (2-arg)
        try {
            new HashFolder(-1, 2);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
        try {
            new HashFolder(0, 2);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }
        try {
            new HashFolder(Integer.MIN_VALUE, 2);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }

        // test exception not thrown on positive integer input (1-arg)
        new HashFolder(1);
        new HashFolder(Integer.MAX_VALUE);

        // test exception not thrown on positive integer input (2-arg)
        new HashFolder(1, 2);
        new HashFolder(Integer.MAX_VALUE, 2);
    }

    /**
     * Tests that illegal argument exceptions are thrown for invalid prime value
     * seeds provided to the constructor. Valid input is the set of all prime
     * numbers (Integers); invalid input is the difference of the set of all
     * Integers and the set of all Prime Integers. Testing is achieved by using
     * {@link #PRIMES} and {@link #NON_PRIMES} lists and the max usable prime
     * ({@link Integer#MAX_VALUE}) and non-prime
     * (<code>{@link Integer#MAX_VALUE} - 1</code>) integers.
     */
    @Test
    public void checkPrimeSeedValueTest() {
        // test exception is thrown for non-positive non-prime seed
        for (int i : NON_PRIMES) {
            try {
                new HashFolder(1, -i);
                fail("expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // success
            }
        }
        try {
            new HashFolder(1, Integer.MIN_VALUE);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }

        // test exception is thrown for negated prime seed
        // i.e. 3 is prime, try the negation -3
        for (int i : PRIMES) {
            try {
                new HashFolder(1, -i);
                fail("expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // success
            }
        }
        try {
            new HashFolder(1, -Integer.MAX_VALUE);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }

        // test exception is thrown for positive non-prime seed
        // i.e. 4, 6, ..., 15, ...
        for (int i : NON_PRIMES) {
            try {
                new HashFolder(1, i);
                fail("expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // success
            }
        }
        try {
            new HashFolder(1, Integer.MAX_VALUE - 1);
            fail("expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // success
        }

        // test exception not thrown on positive prime seed
        for (int i : PRIMES) {
            try {
                new HashFolder(1, i);
                // success
            } catch (IllegalArgumentException e) {
                fail("unexpected IllegalArgumentException");
            }
        }
        try {
            new HashFolder(1, Integer.MAX_VALUE);
            // success
        } catch (IllegalArgumentException e) {
            fail("unexpected IllegalArgumentException");
        }

    }

    /**
     * Tests that objects are folded into hash folder and calculates accurate
     * hash codes.
     * <p>
     * The hash folder uses the following equation for folding:
     * <ul>
     * {@code value = 
     *      prime * value + ((object == null) ? 0 : object.hashCode())}
     * </ul>
     * </p>
     * <p>
     * For testing purposes, a specialized {@code TestObject} is used which
     * provides its own overridden {@link TestObject#hashCode() hashCode} method
     * that just returns the hash code value {@code 1}.
     * </p>
     * Additional tests will test that
     */
    @Test
    public void foldingTest() {
        // Value calculation:
        // value = prime * value + ((object == null) ? 0 : object.hashCode());

        /*
         * create new hash folder and fold in predictable objects (null or
         * TestObject)
         */
        HashFolder hf1 = new HashFolder(1, 31);
        assertEquals(1, hf1.getHashValue());

        // current value = 1, fold in null, value should now be 31
        // value = (31)(1) + 0 = 31
        hf1.fold(null);
        assertEquals(31, hf1.getHashValue());

        // current value = 31, fold in TestObject, value should be 962
        // value = (31)(31) + 1 = 962
        hf1.fold(new TestObject());
        assertEquals(962, hf1.getHashValue());

        // current value = 962, fold in null, value should now be 29822
        // value = (31)(962) + 0 = 29822
        hf1.fold(null);
        assertEquals(29822, hf1.getHashValue());

        // current value = 29822, fold in TestObject, value should be 924483
        // value = (31)(29822) + 1 = 924483
        hf1.fold(new TestObject());
        assertEquals(924483, hf1.getHashValue());

        /*
         * create new hash folder like above but just add a bunch of TestObjects
         */
        int prime = 31;
        int expected = 1;
        HashFolder hf2 = new HashFolder(1, prime);

        for (int i = 0; i < Integer.MAX_VALUE; i++) { // overkill, but thourough
            // fold in new TestObject
            hf2.fold(new TestObject());

            // manually calculate expected hash value
            expected = prime * expected + 1;

            assertEquals(expected, hf2.getHashValue());
        }

        /*
         * create two collections and hash folders and test two equal
         * collections' values have equal hash folder values
         */
        String[] list1 = { "A", "B", "C", "D", "E", "F" };
        String[] list2 = { "A", "B", "C", "D", "E", "F" };
        assertFalse(list1 == list2); // not reference to same object
        assertTrue(Arrays.equals(list1, list2)); // but lists are equal!

        // create hash folders and fold both collections in
        hf1 = new HashFolder(1, 2);
        hf2 = new HashFolder(1, 2);
        for (int i = 0; i < list1.length; i++) {
            hf1.fold(list1[i]);
            hf2.fold(list2[i]);
        }
        assertTrue(hf1.getHashValue() == hf2.getHashValue());

        // fold 1 more value into only one hash folder, hash values should be
        // different
        hf1.fold(list1[0]);
        assertFalse(hf1.getHashValue() == hf2.getHashValue());

        /*
         * create two collections (second references the first) and hash folders
         * and test two equal hash folder values
         */
        list1 = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };
        list2 = list1;
        assertTrue(list1 == list2); // reference to same object
        assertTrue(Arrays.equals(list1, list2)); // and lists are equal!

        // create hash folders and fold both collections in
        hf1 = new HashFolder(1, 2);
        hf2 = new HashFolder(1, 2);
        for (int i = 0; i < list1.length; i++) {
            hf1.fold(list1[i]);
            hf2.fold(list2[i]);
        }
        assertTrue(hf1.getHashValue() == hf2.getHashValue());

        // fold 1 more value into only one hash folder, hash values should be
        // different
        hf1.fold(list1[0]);
        assertFalse(hf1.getHashValue() == hf2.getHashValue());

        /*
         * create two collections and hash folders and test two equal
         * collections have equal hash folder values
         */
        List<Integer> intList1 = new ArrayList<Integer>();
        intList1.add(1);
        intList1.add(2);
        intList1.add(3);
        intList1.add(4);
        intList1.add(5);
        intList1.add(6);

        List<Integer> intList2 = new ArrayList<Integer>();
        intList2.add(1);
        intList2.add(2);
        intList2.add(3);
        intList2.add(4);
        intList2.add(5);
        intList2.add(6);

        assertFalse(intList1 == intList2); // not reference to same object
        assertTrue(intList1.equals(intList2)); // but lists are equal!

        // create hash folders and fold both collections in
        hf1 = new HashFolder(1, 2);
        hf2 = new HashFolder(1, 2);
        hf1.fold(list1);
        hf2.fold(list2);
        assertTrue(hf1.getHashValue() == hf2.getHashValue());

        /*
         * create two collections and hash folders (second references the first)
         * and test two equal hash folder values
         */
        list1 = new String[] { "A", "B", "C", "D", "E", "F" };
        list2 = new String[] { "A", "B", "C", "D", "E", "F" };
        assertFalse(list1 == list2); // not reference to same object
        assertTrue(Arrays.equals(list1, list2)); // but lists are equal!

        // create hash folders and fold both collections into hash folder 1
        hf1 = new HashFolder();
        hf2 = hf1;
        for (int i = 0; i < list1.length; i++) {
            hf1.fold(list1[i]);
        }
        assertTrue(hf1.getHashValue() == hf2.getHashValue());

        // create hash folders and fold both collections into hash folder 2
        hf1 = new HashFolder();
        hf2 = hf1;
        for (int i = 0; i < list1.length; i++) {
            hf2.fold(list1[i]);
        }
        assertTrue(hf1.getHashValue() == hf2.getHashValue());

        /*
         * Test fold(iterator) method
         */
        List<String> stringList = new ArrayList<String>();
        stringList.add("A");
        stringList.add("B");
        stringList.add("C");
        stringList.add("D");
        stringList.add("E");
        stringList.add("F");
        stringList.add("G");
        stringList.add("H");

        // create hash folders and fold both collections in
        hf1 = new HashFolder(1, 2);
        hf2 = new HashFolder(1, 2);
        
        // fold in iterator into hash folder 1
        hf1.bulkFold(stringList.iterator());
        
        // fold in strings into hash folder 2
        for (String s : stringList) {
            hf2.fold(s);
        }
        assertTrue(hf1.getHashValue() == hf2.getHashValue());
        
        /*
         * Test exception thrown if bulk fold method is passed null iterator
         */
        try {
            hf1 = new HashFolder(1, 2);
            hf1.bulkFold(null);
            fail("expected IllegalArgumentException");
        } catch (NullPointerException e) {
            // success
        }

    }

    /**
     * Basic object with overridden hashCode method to return predictable hash
     * code values for testing.
     */
    class TestObject extends Object {

        /**
         * <p>
         * This specialized test object only returns hashcode value 1.
         * </p>
         * 
         * @return 1
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return 1;
        }

    }
}
