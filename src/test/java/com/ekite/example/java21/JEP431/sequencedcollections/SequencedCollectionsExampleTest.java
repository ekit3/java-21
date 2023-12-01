package com.ekite.example.java21.JEP431.sequencedcollections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/**
 * This class provides test methods to verify the functionality of different types of collections that support
 * sequencing operations. It includes tests for SequencedCollection, SequencedSet, and SequencedMap.
 */
class SequencedCollectionsExampleTest {


	/**
	 * Method to test the functionality of a collection that supports sequencing operations. The collection is initially
	 * empty and is of type 'SequencedCollection<Integer>'.
	 * The following operations are performed and tested:
	 * <ul>
	 * <li>Adding an element to the beginning of the collection using the addFirst() method.</li>
	 * <li>Adding an element to the end of the collection using the addLast() method.</li>
	 * <li>Reversing the order of elements in the collection using the reversed() method.</li>
	 * <li>Removing the first element from the collection using the removeFirst() method.</li>
	 * <li>Removing the last element from the collection using the removeLast() method.</li>
	 * <li>Retrieving the first element in the collection using the getFirst() method.</li>
	 * <li>Retrieving the last element in the collection using the getLast() method.</li>
	 * </ul>
	 */
	@Test
	void testCollection() {
		SequencedCollection<Integer> collection = new ArrayList<>();

		assertThat(collection).isEmpty();

		collection.add(1);  //[1]
		assertThat(collection).containsExactly(1);

		collection.addFirst(0); // [0,1]
		assertThat(collection).containsExactly(0, 1);

		collection.add(2); // [0,1,2]
		assertThat(collection).containsExactly(0, 1, 2);

		collection.addLast(3); // [0,1,2,3]
		assertThat(collection).containsExactly(0, 1, 2, 3);

		collection = collection.reversed();// [3,2,1,0]
		assertThat(collection).containsExactly(3, 2, 1, 0);

		collection.removeFirst();// [2,1,0]
		assertThat(collection).containsExactly(2, 1, 0);

		collection = collection.reversed();// [0,1,2]
		assertThat(collection).containsExactly(0, 1, 2);

		collection.removeLast();// [0,1]
		assertThat(collection).containsExactly(0, 1);

		assertThat(collection.getFirst()).isZero();
		assertThat(collection.getLast()).isEqualTo(1);

	}

	/**
	 * Method to test the functionality of a set that supports sequencing operations. The set is initially populated with
	 * the elements [1, 2, 3] and is of type 'SequencedSet<Integer>'.
	 * The following operations are performed and tested:
	 * <ul>
	 * <li>Adding an element to the beginning of the set using the addFirst() method.</li>
	 * <li>Adding an element to the end of the set using the addLast() method.</li>
	 * <li>Reversing the order of elements in the set using the reversed() method.</li>
	 * <li>Removing the first element from the set using the removeFirst() method.</li>
	 * <li>Removing the last element from the set using the removeLast() method.</li>
	 * <li>Retrieving the first element in the set using the getFirst() method.</li>
	 * <li>Retrieving the last element in the set using the getLast() method.</li>
	 * </ul>
	 */
	@Test
	void testSet() {
		SequencedSet<Integer> set = new LinkedHashSet<>(List.of(1, 2, 3));

		assertThat(set).containsExactly(1, 2, 3);

		set.addFirst(0);

		assertThat(set).containsExactly(0, 1, 2, 3);

		set.add(2); // already exist won't be added to the set
		set.add(3); // already exist won't be added to the set
		set.addLast(4);

		//[0, 1, 2, 3, 4]
		assertThat(set).hasSize(5).containsExactly(0, 1, 2, 3, 4);

		assertThat(set.getFirst()).isZero();
		assertThat(set.getLast()).isEqualTo(4);

		set.removeFirst();
		assertThat(set.getFirst()).isEqualTo(1);

		set.removeLast();
		assertThat(set.getLast()).isEqualTo(3);

		set = set.reversed();
		assertThat(set).containsExactly(3, 2, 1);

	}

	/**
	 * Method to test the functionality of a map that supports sequencing operations. The map is initially populated with
	 * the entries [0->"0", 1->"1", 2->"2", 3->"3", 4->"4", 5->"5"] and is of type 'SequencedMap<Integer, String>'.
	 * The following operations are performed and tested:
	 * <ul>
	 * <li>Retrieving the keys in the order they were added using the sequencedKeySet() method.</li>
	 * <li>Retrieving the values in the order they were added using the sequencedValues() method.</li>
	 * <li>Retrieving the first key-value entry using the firstEntry() method.</li>
	 * <li>Retrieving the last key-value entry using the lastEntry() method.</li>
	 * <li>Removing and retrieving the first key-value entry using the pollFirstEntry() method.</li>
	 * <li>Removing and retrieving the last key-value entry using the pollLastEntry() method.</li>
	 * <li>Adding a key-value entry to the beginning of the map using the putFirst() method.</li>
	 * <li>Adding a key-value entry to the end of the map using the putLast() method.</li>
	 * <li>Reversing the order of keys and values in the map using the reversed() method.</li>
	 * </ul>
	 */
	@Test
	void testInitMap() {
		SequencedMap<Integer, String> map = new LinkedHashMap<>(IntStream.rangeClosed(0, 5).boxed()
				.collect(Collectors.toMap(Function.identity(), Object::toString)));

		assertThat(map.sequencedKeySet().stream().toList()).containsExactly(0, 1, 2, 3, 4, 5);
		assertThat(map.sequencedValues().stream().toList()).containsExactly("0", "1", "2", "3", "4", "5");

		Entry<Integer, String> firstEntry = map.firstEntry();
		assertThat(firstEntry.getKey()).isZero();
		assertThat(firstEntry.getValue()).isEqualTo("0");

		Entry<Integer, String> lastEntry = map.lastEntry();
		assertThat(lastEntry.getKey()).isEqualTo(5);
		assertThat(lastEntry.getValue()).isEqualTo("5");

		// pollFirstEntry and pollLastEntry  Remove and retrieve the entry from the map
		Entry<Integer, String> firstPolledEntry = map.pollFirstEntry();
		assertThat(firstPolledEntry.getKey()).isZero();
		assertThat(firstPolledEntry.getValue()).isEqualTo("0");

		Entry<Integer, String> lastPolledEntry = map.pollLastEntry();
		assertThat(lastPolledEntry.getKey()).isEqualTo(5);
		assertThat(lastPolledEntry.getValue()).isEqualTo("5");

		assertThat(map.sequencedKeySet().stream().toList()).containsExactly(1, 2, 3, 4);
		assertThat(map.sequencedValues().stream().toList()).containsExactly("1", "2", "3", "4");

		map.putFirst(0, "0");
		map.putLast(5, "5");
		assertThat(map.sequencedKeySet().stream().toList()).containsExactly(0, 1, 2, 3, 4, 5);
		assertThat(map.sequencedValues().stream().toList()).containsExactly("0", "1", "2", "3", "4", "5");

		map = map.reversed();
		assertThat(map.sequencedKeySet().stream().toList()).containsExactly(5, 4, 3, 2, 1, 0);
		assertThat(map.sequencedValues().stream().toList()).containsExactly("5", "4", "3", "2", "1", "0");

	}


	/**
	 * Method to test the functionality of the utility classes in the Collections class. The following utility classes are
	 * tested: <li>UnmodifiableSequencedCollection <li>UnmodifiableSequencedSet <li>UnmodifiableSequencedMap
	 * <p>
	 * The utility classes create unmodifiable versions of the original collections. The methods addLast, addFirst, putLast,
	 * and putFirst will throw UnsupportedOperationException when called on the unmodifiable
	 * collections.
	 * </p>
	 * Additionally, the methods getLast, getFirst, firstEntry, and lastEntry will throw a
	 * NoSuchElementException when called on an empty SequencedCollection.
	 */
	@Test
	void utilityClasses() {
		SequencedCollection<Integer> collection = Collections.unmodifiableSequencedCollection(new ArrayList<>());
		SequencedSet<Integer> set = Collections.unmodifiableSequencedSet(new LinkedHashSet<>());
		SequencedMap<Integer, String> map = Collections.unmodifiableSequencedMap(new LinkedHashMap<>());

		assertThatThrownBy(() -> collection.addLast(2)).isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> collection.addFirst(2)).isInstanceOf(UnsupportedOperationException.class);

		assertThatThrownBy(() -> set.addLast(2)).isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> set.addFirst(2)).isInstanceOf(UnsupportedOperationException.class);

		assertThatThrownBy(() -> map.putLast(2, "2")).isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> map.putFirst(2, "2")).isInstanceOf(UnsupportedOperationException.class);

		assertThatThrownBy(collection::getLast).isInstanceOf(NoSuchElementException.class);
		assertThatThrownBy(collection::getFirst).isInstanceOf(NoSuchElementException.class);

		assertThatThrownBy(set::getLast).isInstanceOf(NoSuchElementException.class);
		assertThatThrownBy(set::getFirst).isInstanceOf(NoSuchElementException.class);

		assertThatThrownBy(map::firstEntry).isInstanceOf(NoSuchElementException.class);
		assertThatThrownBy(map::lastEntry).isInstanceOf(NoSuchElementException.class);

	}

}