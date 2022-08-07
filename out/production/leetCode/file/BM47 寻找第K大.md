## 描述

有一个整数数组，请你根据快速排序的思路，找出数组中第 k 大的数。 

给定一个整数数组 a ,同时给定它的大小n和要找的 k ，请返回第 k 大的数(包括重复的元素，不用去重)，保证答案存在。 

要求：时间复杂度 O(nlogn)*O*(*n**l**o**g**n*)，空间复杂度 O(1)*O*(1)

数据范围：0≤n≤1050≤*n*≤105， 1≤K≤n1≤*K*≤*n*，数组中每个元素满足 0≤val≤1090≤*v**a**l*≤109

[1,3,5,2,2],5,3

2

```java
import java.util.*;
public class Solution {
    //常规的快排划分，但这次是大数在左
    public int partion(int[] a, int low, int high){ 
        int temp = a[low];
        while(low < high){ 
            //小于标杆的在右
            while(low < high && a[high] <= temp)
                high--;
            if(low == high)
                break;
            else
                a[low] = a[high];
            //大于标杆的在左
            while(low < high && a[low] >= temp)
                low++;
            if(low == high)
                break;
            else
                a[high] = a[low];
        }
        a[low] = temp;
        return low;
    }
    public int quickSort(int[] a, int low, int high, int K){
        //先进行一轮划分，p下标左边的都比它大，下标右边都比它小
        int p = partion(a, low, high); 
        //若p刚好是第K个点，则找到
        if(K == p - low + 1)  
            return a[p];
        //从头到p超过k个数组，则目标在左边
        else if(p - low + 1 > K)  
            //递归左边
            return quickSort(a, low, p - 1, K);  
        else  
            //否则，在右边,递归右边,但是需要减去左边更大的数字的数量
            return quickSort(a, p + 1, high, K - (p - low + 1));  
    }
    public int findKth(int[] a, int n, int K) {
        return quickSort(a, 0, n - 1, K);
    }
}

```



 