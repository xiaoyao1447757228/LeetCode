public class BinarySortAndSearch {

    public static void main(String[] args) {
        int arr[] = {1, 2, 1, 3, 5, 6, 4};
        findPeakElement(arr);

        String s1="1.1";
        String s2="1.01";
        compare(s1, s2);

    }

    public int search(int[] nums, int target) {
        // write code here
        if (nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (right + left) >> 1;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * BM18 二维数组中的查找
     * 在一个二维数组array中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     *
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int[][] array) {
        if (array.length == 0) {
            return false;
        }
        int hangLen = array.length;

        if (array[0].length == 0) {
            return false;
        }

        int lieLen = array[0].length;
        for (int hang = hangLen - 1, lie = 0; hang >= 0 && lie < lieLen; ) {
            if (array[hang][lie] > target) {
                hang--;
            } else if (array[hang][lie] < target) {
                lie++;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * BM19 寻找峰值
     */
    public static int findPeakElement(int[] nums) {
        // write code here
        if (nums.length == 0) {
            return -1;
        }
        int mid = 0, left = 0, right = nums.length - 1;
        while (left != right) {
            mid = left + ((right - left) >> 1);
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return mid;
    }


    /**
     * BM22 比较版本号
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compare(String version1, String version2) {
        int n1 = version1.length();
        int n2 = version2.length();
        int i = 0, j = 0;
        //直到某个字符串结束
        while (i < n1 || j < n2) {
            long num1 = 0;
            //从下一个点前截取数字
            while (i < n1 && version1.charAt(i) != '.') {
                num1 = num1 * 10 + (version1.charAt(i) - '0');
                i++;
            }
            //跳过点
            i++;
            long num2 = 0;
            //从下一个点前截取数字
            while (j < n2 && version2.charAt(j) != '.') {
                num2 = num2 * 10 + (version2.charAt(j) - '0');
                j++;
            }
            //跳过点
            j++;
            //比较数字大小
            if (num1 > num2)
                return 1;
            if (num1 < num2)
                return -1;
        }
        //版本号相同
        return 0;
    }


    /**
     * BM21 旋转数组的最小数字
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        // 特殊情况判断
        if (array.length== 0) {
            return 0;
        }
        // 左右指针i j
        int i = 0, j = array.length - 1;
        // 循环
        while (i < j) {
            // 找到数组的中点 m
            int m = (i + j) / 2;
            // m在左排序数组中，旋转点在 [m+1, j] 中
            if (array[m] > array[j]) i = m + 1;
                // m 在右排序数组中，旋转点在 [i, m]中
            else if (array[m] < array[j]) j = m;
                // 缩小范围继续判断
            else j--;
        }
        // 返回旋转点
        return array[i];
    }



}
