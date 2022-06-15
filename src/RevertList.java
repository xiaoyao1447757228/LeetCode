public class RevertList {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;
        ListNode tmp = node1;
        while (tmp != null) {
            System.out.println(tmp.val);
            tmp = tmp.next;
        }
        System.out.println("===============");
        tmp = reverseKGroup(node1, 2);
        while (tmp != null) {
            System.out.println(tmp.val);
            tmp = tmp.next;
        }

    }


    /**
     * k个一组反转链表
     * @param head
     * @param k
     * @return
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        //新头
        ListNode newHead = head;
        //尾部
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            if (tail == null) {
                return head;
            }
            tail = tail.next;
        }
        newHead = reverse(head, tail);
        head.next = reverseKGroup(tail, k);
        return newHead;
    }

    /**
     * 返回反转之后的新头
     * @param head
     * @param tail
     * @return
     */
    public static ListNode reverse(ListNode head, ListNode tail){
        ListNode cur = head;
        ListNode pre=null,temp;
        while(cur != tail){
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    public static ListNode reverseList(ListNode head) {
        ListNode pre, cur, temp;
        pre = null;
        cur = head;
        while (cur != null) {
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;

    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode node = new ListNode(-1), cur, temp;
        node.next = head;
        ListNode pre = node;
        for (int i =0 ;i < left - 1; i++) {
            pre = pre.next;
        }
        cur = pre.next;

        for (int i = 0;i < right - left;i++) {
            temp = cur.next;
            cur.next = temp.next;
            temp.next = pre.next;
            pre.next = temp;
        }
        return node.next;
    }

}
