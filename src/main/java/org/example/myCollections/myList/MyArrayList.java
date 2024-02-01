package org.example.myCollections.myList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements MyList<T>{
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size;

    public MyArrayList(){
        this.array = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public MyArrayList(int initialCapacity){
        if(initialCapacity < 0){
            throw new IllegalArgumentException("Illegal capacity: " + initialCapacity);
        }
        this.array = new Object[initialCapacity];
        this.size = 0;
    }


    // Inner methods for ArrayList
    private void ensureCapacity(int minCapacity){
        if(minCapacity - array.length > 0){
            grow(minCapacity);
        }
    }
    private void grow(int minCapacity){
        int newCapacity = array.length * 2;
        if(newCapacity - minCapacity < 0){
            newCapacity = minCapacity;
        }
        array = Arrays.copyOf(array, newCapacity);
    }
    private void fastRemove(int index){
        int numberMoved = size - index - 1;
        if(numberMoved > 0){
            System.arraycopy(array, index + 1, array, index, numberMoved);
        }
        array[--size] = null;
    }

    public Object[] toArray(){
        return Arrays.copyOf(array, size);
    }

    public <T> T[] toArray(T[] values){
        if(values.length < size){
            return ((T[])Arrays.copyOf(array, size, values.getClass()));
        }
        System.arraycopy(array, 0, values, 0, size);
        if(values.length > size){
            values[size] = null;
        }
        return values;
    }
    //


    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean contains(Object element){
        for(Object value : array){
            if(value.equals(element)) return true;
        }
        return false;
    }

    @Override
    public int indexOf(T element){
        if(element == null){
            for(int i = 0; i < size; i++){
                if(array[i] == null){
                    return i;
                }
            }
        }else{
            for(int i = 0; i < size; i++){
                if(element.equals(array[i])){
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public T get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + " size: " + size);
        }
        return (T)array[index];
    }

    @Override
    public boolean add(T element){
        ensureCapacity(size + 1);
        array[size++] = element;
        return true;
    }
    @Override
    public void add(int index, T element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
        ensureCapacity(size + 1);
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }
    @Override
    public T remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
        T oldValue = (T) array[index];
        int numMoved = size - index - 1;
        if(numMoved > 0){
            System.arraycopy(array, index + 1, array, index, numMoved);
        }
        array[--size] = null;
        return oldValue;
    }

    @Override
    public boolean remove(T element){
       if(element == null){
           for(int i = 0; i < size; i++){
                if(array[i] == null){
                    fastRemove(i);
                    return true;
                }
           }
       }else{
           for(int i = 0; i < size; i++){
               if(element.equals(null)){
                   fastRemove(i);
                   return true;
               }
           }
       }
       return false;
    }

    @Override
    public void clear(){
        for(Object element : array){
            element = null;
        }
        size = 0;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection){
        return addAll(size, collection);
    }
    @Override
    public boolean addAll(int index, Collection<? extends T> collection){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
        Object[] a = collection.toArray();
        int numNew = a.length;
        ensureCapacity(size + numNew);
        int numMoved = size - index;
        if(numMoved > 0){
            System.arraycopy(array, index, array, index + numNew, numMoved);
        }
        System.arraycopy(a, 0, array, index, numNew);
        size += numNew;
        return numNew != 0;
    }

    @Override
    public boolean removeAll(Collection<T> collection){
        boolean modified = false;
        for(int i = 0; i < size; i++){
            if(collection.contains(array[i])){
                fastRemove(i);
                i--;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<T> collection){
        boolean modified = false;
        for(int i = 0; i < size; i++){
            if(!collection.contains(array[i])){
                fastRemove(i);
                i--;
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean containsAll(Collection<T> collection){
        for(Object element : collection){
            if(!contains(element)){
                return false;
            }
        }
        return true;
    }

    @Override
    public T set(int index, T element){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
        T oldValue = (T) array[index];
        array[index] = element;
        return oldValue;
    }

    @Override
    public MyList<T> subList(int formIndex, int toIndex){
        throw new UnsupportedOperationException("subList");
    }

    @Override
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor != 0;
            }

            @Override
            public T next() {
                if(cursor >= size){
                    throw new NoSuchElementException();
                }
                return (T)array[cursor++];
            }
        };
    }



}
