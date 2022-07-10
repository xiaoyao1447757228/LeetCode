**BM2** **链表内指定区间反转**

![image.png](https://pic.leetcode-cn.com/1615105232-cvTINs-image.png)

在需要反转的区间里，每遍历到一个节点，让这个新节点来到反转部分的起始位置。下面的图展示了整个流程。

<img src="https://pic.leetcode-cn.com/1615105242-ZHlvOn-image.png" alt="image.png" style="zoom:50%;" />

下面我们具体解释如何实现。使用三个指针变量 pre、curr、next 来记录反转的过程中需要的变量，它们的意义如下：

curr：指向待反转区域的第一个节点 left；
next：永远指向 curr 的下一个节点，循环过程中，curr 变化以后 next 会变化；
pre：永远指向待反转区域的第一个节点 left 的前一个节点，在循环过程中不变。
第 1 步，我们使用 ①、②、③ 标注「穿针引线」的步骤。

<img src="https://pic.leetcode-cn.com/1615105296-bmiPxl-image.png" alt="image.png" style="zoom:50%;" />

操作步骤：

先将 curr 的下一个节点记录为 next；
执行操作 ①：把 curr 的下一个节点指向 next 的下一个节点；
执行操作 ②：把 next 的下一个节点指向 pre 的下一个节点；
执行操作 ③：把 pre 的下一个节点指向 next。
第 1 步完成以后「拉直」的效果如下：

<img src="https://pic.leetcode-cn.com/1615105340-UBnTBZ-image.png" alt="image.png" style="zoom:50%;" />

第 2 步，同理。同样需要注意 **「穿针引线」操作的先后顺序**。

<img src="https://pic.leetcode-cn.com/1615105364-aDIFqy-image.png" alt="image.png" style="zoom:50%;" />

第 3 步，同理。

<img src="https://pic.leetcode-cn.com/1615105376-jIyGwv-image.png" alt="image.png" style="zoom:50%;" />

第 3 步完成以后「拉直」的效果如下：

<img src="https://pic.leetcode-cn.com/1615105395-EJQnMe-image.png" alt="image.png" style="zoom:50%;" />

```java
public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode node = new ListNode(-1), cur, temp;
        node.next = head;
        ListNode pre = node;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.next;
        }
        cur = pre.next;

        for (int i = 0; i < right - left; i++) {
            temp = cur.next;
            cur.next = temp.next;
            temp.next = pre.next;
            pre.next = temp;
        }
        return node.next;
}
```

