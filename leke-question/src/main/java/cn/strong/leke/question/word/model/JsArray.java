/*
 * Copyright (c) 2014 Strong Group - 版权所有
 * 
 * This software is the confidential and proprietary information of
 * Strong Group. You shall not disclose such confidential information 
 * and shall use it only in accordance with the terms of the license 
 * agreement you entered into with www.cnstrong.cn.
 */
package cn.strong.leke.question.word.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Js 数组
 * 
 * @author liulongbiao
 * @created 2014年12月9日 下午4:10:20
 * @since v3.2
 */
public class JsArray<E> implements Iterable<E> {

	private Map<Integer, E> map;
	private int size;

	public JsArray() {
		super();
		map = new HashMap<Integer, E>();
		size = 0;
	}

	public void push(E element) {
		set(size, element);
	}

	public E pop() {
		if (size == 0) {
			throw new RuntimeException("there is nothing to pop up.");
		}
		size--;
		E result = map.get(size);
		map.remove(size);
		return result;
	}

	public E peek() {
		return size == 0 ? null : map.get(size - 1);
	}

	public void set(int index, E element) {
		if (index < 0) {
			index = size + index;
		}
		if (index < 0) {
			throw new RuntimeException("negative index is large than size.");
		}

		if (index > size - 1) {
			size = index + 1;
		}
		map.put(index, element);
	}

	public E get(int index) {
		if (index < 0) {
			index = size + index;
		}
		if (index < 0) {
			throw new RuntimeException("negative index is large than size.");
		}
		return map.get(index);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder('[');
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(map.get(i));
		}
		sb.append(']');
		return sb.toString();
	}

	@Override
	public Iterator<E> iterator() {
		return new Itr();
	}

	private class Itr implements Iterator<E> {
		int cursor;

		public boolean hasNext() {
			return cursor != size;
		}

		public E next() {
			int i = cursor;
			if (i >= size) {
				throw new NoSuchElementException();
			}
			cursor++;
			return JsArray.this.get(i);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(
					"can not remove element while iterate JsArray.");
		}
	}
}
