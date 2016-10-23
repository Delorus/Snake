package ru.sherb.Snake.util;

public class List<E> {
    private E value;
    private List<E> next;

    public List() {};

    public boolean setValue(E value) {
        this.value = value;
        return true;
    }

    public E getValue() {
        return value;
    }

    public boolean isNext() {
        return (next != null && value != null);
    }

    public List<E> next() {
        if (next == null) {
            grow();
        }
        return next;
    }

    private void grow() {
        next = new List<>();
    }

}