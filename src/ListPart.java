

import util.ListNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ListPart {

    public static void main(String[] args) {
//        util.ListNode node1 = new util.ListNode(1);
//        util.ListNode node2 = new util.ListNode(2);
//        util.ListNode node3 = new util.ListNode(3);
//        util.ListNode node4 = new util.ListNode(4);
//        util.ListNode node5 = new util.ListNode(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        node5.next = null;
//        util.ListNode tmp = node1;
//        while (tmp != null) {
//            System.out.println(tmp.val);
//            tmp = tmp.next;
//        }
//        System.out.println("===============");
//        FindKthToTail(node1, 1);
//        tmp = reverseKGroup(node1, 2);
//        while (tmp != null) {
//            System.out.println(tmp.val);
//            tmp = tmp.next;
//        }
        //两个链表生成相加链表
//        util.ListNode node1 = new util.ListNode(1);
//        util.ListNode node2 = new util.ListNode(2);
//        util.ListNode node3 = new util.ListNode(3);
//        util.ListNode node4 = new util.ListNode(4);
//        util.ListNode node5 = new util.ListNode(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = null;
//        node4.next = node5;
//        node5.next = null;
//        addInList(node1, node4);

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(1);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = null;
        isPail(node1);
    }


    /**
     * BM2 链表内指定区间反转
     * 将一个节点数为 size 链表 m 位置到 n 位置之间的区间反转
     * @param head
     * @return
     */
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
     * BM14 链表的奇偶重排
     * 给定一个单链表，请设定一个函数，将链表的奇数位节点和偶数位节点分别放在一起，重排后输出。注意是节点的编号而非节点的数值。
     *
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        // write code here
        if (head == null || head.next == null) {
            return head;
        }
        //基数位
        ListNode odd = head;
        //偶数位
        ListNode even = head.next;

        ListNode evenHead = even;


        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenHead;

        return head;
    }

    /**
     * 是否是回文结构
     *
     * @param head
     * @return
     */
    public static boolean isPail(ListNode head) {
        // write code here
        ArrayList<Integer> array = new ArrayList<>();
        while (head != null) {
            array.add(head.val);
            head = head.next;
        }
        if (array.size() == 0) {
            return false;
        }
        for (int i = 0; i < array.size() / 2; i++) {
            if (!array.get(i).equals(array.get(array.size() - 1 - i))) {
                return false;
            }
        }
        return true;
    }

    public ListNode sortInList(ListNode head) {
        // write code here
        ArrayList<Integer> arrayList = new ArrayList<>();
        ListNode p = head;
        while (p != null) {
            arrayList.add(p.val);
            p = p.next;
        }
        Collections.sort(arrayList);
        ListNode node = head;
        for (Integer integer : arrayList) {
            node.val = integer;
            node = node.next;
        }
        return head;
    }


    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null) {
            return null;
        }
        HashSet<ListNode> set = new HashSet<>();
        while (pHead1 != null) {
            set.add(pHead1);
            pHead1 = pHead1.next;
        }
        while (pHead2 != null) {
            if (set.contains(pHead2)) {
                return pHead2;
            }
            pHead2 = pHead2.next;
        }
        return null;
    }

    /**
     * 两个链表生成相加链表
     *
     * @param head1
     * @param head2
     * @return
     */
    public static ListNode addInList(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        // write code here
        head1 = revertList(head1);
        head2 = revertList(head2);
        ListNode newHead = new ListNode(-1);
        ListNode head = newHead;
        //用来标记上一次的进位
        int num = 0;
        while (head1 != null || head2 != null) {
            int val = num;
            if (head1 != null) {
                val += head1.val;
                head1 = head1.next;
            }
            if (head2 != null) {
                val += head2.val;
                head2 = head2.next;
            }
            num = val / 10;
            head.next = new ListNode(val % 10);
            head = head.next;
        }
        return revertList(newHead.next);
    }

    static ListNode revertList(ListNode head) {
        ListNode pre = null, cur = head, temp;
        while (cur != null) {
            temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
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
     *
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


    /**
     * BM16 删除有序链表中重复的元素-II
     * 给出一个升序排序的链表，删除链表中的所有重复出现的元素，只保留原链表中只出现一次的元素。
     * @param head
     * @return
     */
    public ListNode deleteDuplicates (ListNode head) {
        if(head == null){
            return head;
        }
        ListNode vHead = new ListNode(-1);
        vHead.next = head;
        ListNode cur = vHead;

        while(cur.next!=null&& cur.next.next!=null){
            //相邻的节点相等
            if(cur.next.val == cur.next.next.val){
                int flag = cur.next.val;
                while(cur.next !=null&& cur.next.val == flag){
                    //逐个移动相同的节点
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }
        return vHead.next;
    }
}
