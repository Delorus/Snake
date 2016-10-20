package ru.sherb.Snake.util;

// TODO �������� ����� �������:
// ������� ������� ���������, ���������� �� ������������� ������ .next();
// �������� ��������
public class RelateList<Value> /*extends List<Value>*/ {
    private List<Value> start;
    private List<Value> item;
    private int size = 0;

    public RelateList() {
        item = new List<Value>();
        start = item;
    }
    //TODO ������������ ��������� Iterable
    public void setValue(Value value) {
        item.setValue(value);
    }

    public Value getValue() {
        return item.getValue();
    }

    public void next() {
        if (item.next == null) {
            grow();
        }
        item = item.next;
    }

    public boolean isNext() {
        return item.isNext();
    }

    /**
     * ������������� ��������� �� ������ ������� ��� ������� �� ������,
     * ����� ������ ������ ���������� ���������� ������ ����������.
     * <br>����������, ���� �� ���������� ��������� Iterable</br>
     */
    @Deprecated
    public void startCount() {
        item = start;
    }

    public int size() {
        return size;
    }

    protected void grow() {
        item.next = new List<Value>();
        size++;
    }
}

class List<E> {
    private E value;
    protected List<E> next;

    //TODO �������� �����

    public void setValue(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public boolean isNext() {
        return (next != null && value != null);
    }

}
