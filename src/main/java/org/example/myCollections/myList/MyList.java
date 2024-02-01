package org.example.myCollections.myList;

import java.util.Collection;
import java.util.Iterator;

public interface MyList<E> {
    int size();
    boolean isEmpty();
    boolean contains(E element);
    int indexOf(E element);
    E get(int index);
    boolean add(E element);
    void add(int index, E element);
    E remove(int index);
    boolean remove(E element);
    void clear();
    boolean addAll(Collection<? extends  E> collection);
    boolean addAll(int index, Collection<? extends E> collection);
    boolean removeAll(Collection<E> collection);
    boolean retainAll(Collection<E> collection);
    boolean containsAll(Collection<E> collection);
    E set(int index, E element);
    MyList<E> subList(int fromIndex, int toIndex);
    Iterator<E> iterator();

}
