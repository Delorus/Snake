package ru.sherb.Snake.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * Реализация структуры "очередь", базирующаяся на односвязном списке
 * Created by sherb on 12.10.2016.
 */
//TODO Доделать класс
public class SimpleQueue<Value> extends RelateList<Value> implements java.util.Queue {

    @Override
    public boolean offer(Object o) {
        return false;
    }

    @Override
    public Value remove() {
        return null;
    }

    @Override
    public Value poll() {
        return null;
    }

    @Override
    public Value element() {
        return null;
    }

    @Override
    public Value peek() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    @Override
    public boolean containsAll(Collection c) {
        return false;
    }
}
