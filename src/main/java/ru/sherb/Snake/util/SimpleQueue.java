package ru.sherb.Snake.util;

/**
 * Created by sherb on 12.10.2016.
 */
//TODO добавить в очередь метод contains();
public class SimpleQueue<Value> {
    private List<Value> start;
    private List<Value> item;
    private int size;

    public SimpleQueue() {
        item = new List<>();
        start = item;
        size = 1;
    }


    public Value remove() {
        List<Value> deletedItem = start;
        start = start.next();
        size--;
        return deletedItem.getValue();
    }

    /**
     *
     * @return возвращает значение элемента в голове очереди
     */
    public Value peek() {
        return start.getValue();
    }

    public boolean add(Value o) {
        if (item.getValue() == null) {
            return item.setValue(o);
        }
        item = item.next();
        size++;
        return item.setValue(o);
    }

    /**
     *
     * @return возвращает значение элемента в конце очереди
     */
    public Value element() {
        return item.getValue();
    }

    public int size() {
        return size;
    }
}
