
import java.util.Arrays;

public class MyArray<E> {
	E[] elements;
	int num = 0;

	@SuppressWarnings("unchecked")
	public MyArray() {
		elements = (E[]) new Object[10];
	}

	public void add(E e) {
		if (num == elements.length)
			elements = Arrays.copyOf(elements, num * 2);
		elements[num++] = e;
	}

	public E get(int i) {
		if (i > num)
			throw new IndexOutOfBoundsException();
		return elements[i];
	}

	public int length() {
		return num;
	}

	public int indexOf(E e) {
		if (e == null) {
			for (int i = 0; i < num; i++)
				if (elements[i] == null)
					return i;
		} else {
			for (int i = 0; i < num; i++)
				if (e.equals(elements[i]))
					return i;
		}
		return -1;
	}
}
