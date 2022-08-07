**第一个指针先移动k步，然后第二个指针再从头开始，这个时候这两个指针同时移动，当第一个指针到链表的末尾的时候，返回第二个指针即可**

```java
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

```

