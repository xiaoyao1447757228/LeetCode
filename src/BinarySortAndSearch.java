public class BinarySortAndSearch {

    public static void main(String[] args) {
        int arr[] = {1,2,1,3,5,6,4};
        findPeakElement(arr);
    }

    public int search (int[] nums, int target) {
        // write code here
        if(nums.length ==0){
            return -1;
        }

        int left = 0;
        int right = nums.length-1;
        while(left <= right){
            int mid = (right+left)>>1;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[mid]< target){
                left = mid+1;
            } else {
                right = mid-1;
            }
        }
        return -1;
    }

    /**
     * BM18 二维数组中的查找
     * 在一个二维数组array中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int [][] array) {
        if (array.length == 0) {
            return false;
        }
        int hangLen = array.length;

        if (array[0].length == 0) {
            return false;
        }

        int lieLen = array[0].length;
        for (int hang = hangLen - 1, lie = 0; hang >= 0 && lie < lieLen; ) {
            if(array[hang][lie] > target){
                hang --;
            } else if(array[hang][lie]<target){
                lie++;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     *     BM19 寻找峰值
     */
    public static int findPeakElement (int[] nums) {
        // write code here
        if(nums.length == 0){
            return -1;
        }
        int mid =0, left = 0, right = nums.length-1;
        while(left!=right){
            mid = left + ((right-left)>>1);
            if(nums[mid]<nums[mid+1]){
                left = mid+1;
            } else {
               right = mid;
            }
        }
        return mid;
    }


    /**
     * BM22 比较版本号
     * @param version1
     * @param version2
     * @return
     */
    public int compare (String version1, String version2) {
        // write code here
    }

}
