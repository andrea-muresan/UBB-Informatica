package domain;

import static domain.SortingStrategy.BUBBLESORT;
import static domain.SortingStrategy.QUICKSORT;

public class SortingTask extends Task {
    private static abstract class AbstractSorter {
        public abstract int[] sort(int[] arr);
    }

    private static class BubbleSort extends AbstractSorter {
        public int[] sort(int[] arr) {
            boolean ok;
            do {
                ok = true;
                for (int i = 0; i < arr.length - 1; ++i) {
                    if (arr[i] > arr[i + 1]) {
                        int aux = arr[i + 1];
                        arr[i + 1] = arr[i];
                        arr[i] = aux;
                        ok = false;
                    }
                }
            } while (!ok);
            return arr;
        }
    }

    private static class QuickSort extends AbstractSorter {
        public int[] sort(int[] arr) {
            quickSort(arr, 0, arr.length - 1);
            return arr;
        }

        public void quickSort(int[] arr, int begin, int end) {
            if (begin < end) {
                int partitionIndex = partition(arr, begin, end);

                quickSort(arr, begin, partitionIndex - 1);
                quickSort(arr, partitionIndex + 1, end);
            }
        }

        private int partition(int[] arr, int begin, int end) {
            int pivot = arr[end];
            int i = begin - 1;

            for (int j = begin; j < end; ++j) {
                if (arr[j] <= pivot) {
                    ++i;

                    int aux = arr[i];
                    arr[i] = arr[j];
                    arr[j] = aux;
                }
            }

            int aux = arr[i + 1];
            arr[i + 1] = arr[end];
            arr[end] = aux;

            return i + 1;
        }

    }

    public int[] vector;
    BubbleSort bubbleSort;
    QuickSort quickSort;

    public SortingTask(int[] vector, SortingStrategy strategy) {


        switch (strategy) {
            case BUBBLESORT:
                this.bubbleSort = new BubbleSort();
                this.vector = this.bubbleSort.sort(vector.clone());
                break;
            case QUICKSORT:
                this.quickSort = new QuickSort();
                this.vector = this.quickSort.sort(vector.clone());
                break;
            default:
                break;
        }
    }

    public void execute() {
        for (int i : this.vector) {
            System.out.print(i + " ");
        }
    }
}
