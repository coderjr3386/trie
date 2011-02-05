/*
 * Trie - A trie implementation in Java
 * Copyright (C) 2011 André Costa
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.decs.trie;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A <b>trie</b> is an ordered tree data structure that is used to
 * store an associative array where the keys are usually strings. Unlike
 * a binary search tree, no node in the tree stores the key associated
 * with that node; instead, its position in the tree shows what key
 * it is associated with. All the descendants of a node have a common
 * prefix of the string associated with that node, and the root is
 * associated with the empty string. Values are normally not associated
 * with every node, only with leaves and some inner nodes that correspond
 * to keys of interest.
 * 
 * @author André Costa
 *
 * @param <T> Value type.
 */
public class TrieMap<T> implements Map<String, T> {
	private Map<Character, T> values;
	private Map<Character, TrieMap<T>> children;
	
	/**
	 * Constructs an empty <code>TrieMap</code>.
	 */
	public TrieMap() {
		this.values = new HashMap<Character, T>();
		this.children = new HashMap<Character, TrieMap<T>>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		this.values.clear();
		this.children.clear();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsKey(Object key) {
		return this.get(key) != null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean containsValue(Object value) {
		if (value == null)
			throw new NullPointerException();
		if (this.values.containsValue(value))
			return true;
		for (Entry<Character, TrieMap<T>> trie : this.children.entrySet())
			if (trie.getValue().containsValue(value))
				return true;
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException this operation isn't implemented.
	 */
	@Override
	public Set<Map.Entry<String, T>> entrySet() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T get(Object key) {
		if (key == null)
			throw new NullPointerException();
		String word = key.toString();
		if (word == null || word.isEmpty())
			throw new NullPointerException();
		char initial = word.charAt(0);
		if (word.length() == 1)
			return this.values.get(initial);
		if (this.children.containsKey(initial))
			return this.children.get(initial).get(word.substring(1));
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		for (Entry<Character, TrieMap<T>> trie : this.children.entrySet())
			if (!trie.getValue().isEmpty())
				return false;
		return this.values.isEmpty();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> keySet() {
		return this.keySet("");
	}
	
	private Set<String> keySet(String prefix) {
		Set<String> entries = new HashSet<String>();
		for (Character initial : this.values.keySet())
			entries.add(prefix + initial);
		for (Entry<Character, TrieMap<T>> trie : this.children.entrySet())
			entries.addAll(trie.getValue().keySet(prefix + trie.getKey()));
		return entries;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T put(String key, T value) {
		if (key == null || key.isEmpty())
			throw new NullPointerException();
		char initial = key.charAt(0);
		if (key.length() == 1)
			return this.values.put(initial, value);
		if (!this.children.containsKey(initial))
			this.children.put(initial, new TrieMap<T>());
		return this.children.get(initial).put(key.substring(1), value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putAll(Map<? extends String, ? extends T> map) {
		for (Entry<? extends String, ? extends T> entry : map.entrySet())
			this.put(entry.getKey(), entry.getValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public T remove(Object key) {
		if (key == null)
			throw new NullPointerException();
		String word = key.toString();
		if (word == null || word.isEmpty())
			throw new NullPointerException();
		char initial = word.charAt(0);
		if (word.length() > 1)
			return this.children.get(initial).remove(word.substring(1));
		T previous = this.values.remove(initial);
		if (this.isEmpty())
			this.clear();
		return previous;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		int size = this.values.size();
		for (Entry<Character, TrieMap<T>> trie : this.children.entrySet())
			size += trie.getValue().size();
		return size;
	}
	
	/**
	 * {@inheritDoc}
	 * @throws UnsupportedOperationException this operation isn't implemented.
	 */
	@Override
	public Collection<T> values() {
		throw new UnsupportedOperationException();
	}
}
