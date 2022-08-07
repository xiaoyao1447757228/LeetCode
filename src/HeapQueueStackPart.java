import java.util.Random;
import java.util.Stack;

public class HeapQueueStackPart {


    /**
     * BM47 寻找第K大
     *
     * @param a
     * @param n
     * @param K
     * @return
     */
    public int findKth(int[] a, int n, int K) {
        return quickSort(a, 0, n - 1, K-1);
    }


    public int partion(int[] a, int low, int high){
        Random r = new Random();
        int index = Math.abs(r.nextInt())%(high-low+1)+low;
        int temp = a[index];
        a[index] = a[low];
        a[low] = temp;
        temp = a[low];
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
        if(K == p)
            return a[p];
            //从头到p超过k个数组，则目标在左边
        else if(p  > K)
            //递归左边
            return quickSort(a, low, p - 1, K);
        else
            //否则，在右边,递归右边,但是需要减去左边更大的数字的数量
            return quickSort(a, p + 1, high, K);
    }


    //BM43 包含min函数的栈
    //用于栈的push 与 pop
    Stack<Integer> s1 = new Stack<Integer>();
    //用于存储最小min
    Stack<Integer> s2 = new Stack<Integer>();
    public void push(int node) {
        s1.push(node);
        //空或者新元素较小，则入栈
        if(s2.isEmpty() || s2.peek() > node)
            s2.push(node);
        else
            //重复加入栈顶
            s2.push(s2.peek());
    }

    public void pop() {
        s1.pop();
        s2.pop();
    }

    public int top() {
        return s1.peek();
    }

    public int min() {
        return s2.peek();
    }



}
