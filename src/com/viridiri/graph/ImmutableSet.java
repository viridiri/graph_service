package com.viridiri.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ImmutableSet<E> implements Set<E> {

    private final Set<E> set;

    ImmutableSet(Set<E> set) {
        this.set = set;
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }

    @Override
    public Object[] toArray() {
        return set.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return set.toArray(a);
    }

    @Override
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.contains(c);
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
}
