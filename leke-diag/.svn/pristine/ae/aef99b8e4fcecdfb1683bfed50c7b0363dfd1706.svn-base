package cn.strong.leke.diag.report.stat;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bucket<K, V> {

	private Map<K, Set<V>> items = new HashMap<K, Set<V>>();

	public Set<V> get(K key) {
		return this.items.get(key);
	}

	public Set<V> getOrDefault(K key, Set<V> defaultValue) {
		return this.items.getOrDefault(key, defaultValue);
	}

	public void put(K key, V value) {
		Set<V> list = this.items.get(key);
		if (list == null) {
			list = new HashSet<V>();
			this.items.put(key, list);
		}
		list.add(value);
	}

	public void remove(K key) {
		this.items.remove(key);
	}

	public void remote(K key, V value) {
		Set<V> list = this.items.getOrDefault(key, new HashSet<V>());
		list.remove(value);
	}

	public int size() {
		return this.items.size();
	}

	public int size(String key) {
		Set<V> values = this.items.get(key);
		return values != null ? values.size() : 0;
	}

	public int exSize() {
		return this.items.values().stream().map(Set::size).reduce(0, (a, b) -> a + b);
	}

	public Set<K> keySet() {
		return this.items.keySet();
	}

	public Collection<Set<V>> values() {
		return this.items.values();
	}

	public String toString() {
		return this.items.toString();
	}

	public static void main(String[] args) {
		Bucket<Integer, Long> bucket = new Bucket<Integer, Long>();

		bucket.put(1, 1121L);
		bucket.put(1, 1123L);
		bucket.put(2, 1124L);
		System.out.println(bucket);
		System.out.println(bucket.size());
		System.out.println(bucket.exSize());
	}
}
