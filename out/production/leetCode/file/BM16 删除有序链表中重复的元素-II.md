**BM16** **删除有序链表中重复的元素-II**

**引入空节点遍历遍历**
**核心思想：**
    引入一个新的空节点，作为链表的头结点。逐个遍历链表节点，由于需要删除所有的重复节点（包括重复节点中第一个出现的节点），因此这里还需要引入一个变量记录重复节点中第一个节点的值，并逐个与后续的节点值相比较，然后逐个删除。

![图片说明](https://uploadfiles.nowcoder.com/images/20210711/9970047_1625982819165/4D08CFDE78C10F0E1C9EA287864A5DE7)