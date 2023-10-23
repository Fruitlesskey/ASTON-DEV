package hw1_CustomArrayList;

import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> {
    public static final int FIRST_CAPACITY = 10;

    private Object[] items;
    private int size;

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }

        items = new Object[initialCapacity];
    }

    public CustomArrayList() {
        items = new Object[FIRST_CAPACITY];
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (size == items.length) {
            size++;
        }
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = element;
        size++;
    }

    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return false;
        }

        if (size + numNew > items.length) {
            expandCapacity();
        }

        System.arraycopy(a, 0, items, size, numNew);
        size += numNew;
        return true;

    }

    public void expandCapacity() {
        int expand = (items.length) * 3 / 2;
        Object[] expandArray = new Object[expand];
        System.arraycopy(items, 0, expandArray, 0, size);
        items = expandArray;

    }

    public void clear() {
        for (int i = 0; i < size; i++)
            items[i] = null;
    }

    public E get(int index) {
        if (index < 0 || index > items.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return (E) items[index];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (size == items.length) {
            size--;
        }
        System.arraycopy(items, index + 1, items, index, size - index - 1);
        //todo Устанавливаем последний элемент в массиве (который теперь является дублированным) в null,
        //todo чтобы избежать утечки памяти
        items[size - 1] = null;
        size--;
    }

    public boolean remove(Object o) {
        if (o == null) {
            return false;
        }
        for (int i = 0; i < items.length; i++) {
            if (o.equals(items[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public void sort(Comparator<? super E> c) {
        // Вызываем вспомогательный метод для сортировки элементов
        quickSort(0, size - 1, c);
    }

    private void quickSort(int low, int high, Comparator<? super E> c) {
        if (low < high) {
            // Разбиваем массив и получаем индекс опорного элемента
            int pivotIndex = partition(low, high, c);

            // Рекурсивно сортируем две половины массива
            quickSort(low, pivotIndex - 1, c);
            quickSort(pivotIndex + 1, high, c);
        }
    }

    private int partition(int low, int high, Comparator<? super E> c) {
        // Выбираем опорный элемент
        E pivot = (E) items[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Если текущий элемент меньше или равен опорному, меняем их местами
            if (c.compare((E) items[j], pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }

        // Помещаем опорный элемент на правильное место
        swap(i + 1, high);

        return i + 1;
    }

    private void swap(int i, int j) {
        // Меняем местами элементы в массиве
        Object temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }


}
