**BM16** **删除有序链表中重复的元素-II**

## 描述

给出一个升序排序的链表，删除链表中的所有重复出现的元素，只保留原链表中只出现一次的元素。

**引入空节点遍历遍历**
**核心思想：**
    引入一个新的空节点，作为链表的头结点。逐个遍历链表节点，由于需要删除所有的重复节点（包括重复节点中第一个出现的节点），因此这里还需要引入一个变量记录重复节点中第一个节点的值，并逐个与后续的节点值相比较，然后逐个删除。

![图片说明](https://uploadfiles.nowcoder.com/images/20210711/9970047_1625982819165/4D08CFDE78C10F0E1C9EA287864A5DE7)

```java
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
```

