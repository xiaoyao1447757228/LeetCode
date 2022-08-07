import java.util.Random;

public class Sortpart {

    public static void main(String[] args) {
//        int[] arr = new int[]{1, 2};
//        quickSort(arr);
//        for (int i : arr) {
//            System.out.println(i);
//        }

    }

    /**
     * 冒泡排序
     *
     * @param arr
     * @return
     */
    public static int[] Bubbling(int[] arr) {
        if (arr.length < 2) {
            return arr;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        return arr;
    }


    public void quickSort(int arr[]) {
        if (arr == null || arr.length == 1) {
            return;
        }
        sort(arr, 0, arr.length - 1);
    }

    public void sort(int arr[], int low, int high) {
        if (low > high) {
            return;
        }
        int stand = arr[low];
        int l = low;
        int h = high;
        while (l < h) {
            while (l < h && arr[h] >= stand) {
                h--;
            }
            if (l < h) {
                arr[l] = arr[h];
            }

            while (l < h && arr[l] <= stand) {
                l++;
            }
            if (l < h) {
                arr[h] = arr[l];
            }
        }
        arr[l] = stand;
        sort(arr, low, l - 1);
        sort(arr, l + 1, high);
    }
}
