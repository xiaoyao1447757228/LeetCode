import java.util.HashMap;
import java.util.HashSet;

public class HashPart {

    /**
     * BM50 两数之和
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum (int[] numbers, int target) {
        // write code here
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i =0; i<numbers.length;i++){
            if(map.containsKey(target-numbers[i])){
                return new int[]{map.get(target-numbers[i])+1, i+1};
            } else {
                map.put(numbers[i],i);
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * BM51 数组中出现次数超过一半的数字
     * @param array
     * @return
     */
    public int MoreThanHalfNum_Solution(int [] array) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for(int num : array){
            if(!hashMap.containsKey(num)){
                hashMap.put(num, 1);
            } else {
                hashMap.put(num, hashMap.get(num)+1);
                hashMap.put(num, hashMap.get(num)+1);
            }
            if(hashMap.get(num)>array.length/2){
                return num;
            }
        }
        return 0;
    }

    /**
     * BM52 数组中只出现一次的两个数字
     * @param array
     * @return
     */
    public int[] FindNumsAppearOnce (int[] array) {
        // write code here
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for(int num:array){
            if(!hashMap.containsKey(num)){
                hashMap.put(num, 1);
            } else {
                hashMap.put(num, hashMap.get(num)+1);
            }
        }
        int[] result = new int[2];
        int i = 0;
        for(HashMap.Entry<Integer, Integer> entry:hashMap.entrySet()){
            if(entry.getValue() == 1){
                result[i] = entry.getKey();
                i++;
            }
        }
        if(result[0]>result[1]){
            int tmp = result[0];
            result[0] = result[1];
            result[1] = tmp;
        }
        return result;
    }

    /**
     * BM53 缺失的第一个正整数
     * @param nums
     * @return
     */
    public int minNumberDisappeared (int[] nums) {
        // write code here
        HashSet<Integer> set = new HashSet<>();
        for(int num:nums){
            set.add(num);
        }
        for(int i= 0;i<nums.length;i++){
            if(!set.contains(i+1)){
                return i+1;
            }
        }
        return nums.length + 1;
    }









}
