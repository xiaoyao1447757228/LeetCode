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
        FindKthToTail(node1, 1);
        tmp = reverseKGroup(node1, 2);
        while (tmp != null) {
            System.out.println(tmp.val);
            tmp = tmp.next;
        }

    }

    /**
     * 合并k个有序链表
     * 分治思想
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return merge(lists, 0, lists.length - 1);
    }

    public ListNode merge(ListNode[] lists, int left, int right) {
        //终止条件
        if (left == right) {
            return lists[left];
        }
        if (left > right) {
            return null;
        }
        int mid = left + (right - left) >> 1;
        ListNode L1 = merge(lists, left, mid);
        ListNode L2 = merge(lists, mid + 1, right);
        return mergeTwoList(L1, L2);
    }

    public ListNode mergeTwoList(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) {
            return list1 == null ? list2 : list1;
        }
        ListNode vHead = new ListNode(-1);
        ListNode tmpHead = vHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                tmpHead.next = list1;
                list1 = list1.next;
            } else {
                tmpHead.next = list2;
                list2 = list2.next;
            }
            tmpHead = tmpHead.next;
        }
        if (list1 != null) {
            tmpHead.next = list1;
        } else {
            tmpHead.next = list2;
        }
        return vHead.next;
    }


    /**
     * k个一组反转链表
     *
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
     *
     * @param head
     * @param tail
     * @return
     */
    public static ListNode reverse(ListNode head, ListNode tail) {
        ListNode cur = head;
        ListNode pre = null, temp;
        while (cur != tail) {
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

    /**
     * 返回倒数第k个节点
     *
     * @param pHead
     * @param k
     * @return
     */
    public static ListNode FindKthToTail(ListNode pHead, int k) {
        //快慢指针
        if (pHead == null || k < 0) {
            return null;
        }
        ListNode fast = pHead, slow = pHead;
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }


    /**
     * 删除链表的倒数第n个节点
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // write code here
        if (head == null || n < 0) {
            return null;
        }
        ListNode fast = head, slow = head;
        ListNode slowPre = null;
        for (int i = 0; i < n; i++) {
            if (fast == null) {
                return null;
            }
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slowPre = slow;
            slow = slow.next;
        }
        if (slowPre == null) {
            return slow.next;
        }
        slowPre.next = slow.next;
        return head;
    }

}
