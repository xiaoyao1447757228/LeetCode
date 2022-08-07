```javsj a
public ListNode removeNthFromEnd (ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
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
```

