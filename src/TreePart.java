import apple.laf.JRSUIUtils;
import util.TreeNode;

import java.util.*;

public class TreePart {


    /**
     * BM23 二叉树的前序遍历
     *
     * @param root
     * @return
     */
    public int[] preorderTraversal(TreeNode root) {
        // write code here
        ArrayList<TreeNode> arrayList = new ArrayList<>();
        preorder(arrayList, root);
        int[] arr = new int[arrayList.size()];
        int i = 0;
        for (TreeNode node : arrayList) {
            arr[i++] = node.val;
        }
        return arr;
    }

    public void preorder(List<TreeNode> list, TreeNode root) {
        if (root == null) {
            return;
        }
        list.add(root);
        preorder(list, root.left);
        preorder(list, root.right);
    }


    /**
     * BM24
     *
     * @param root
     * @return
     */
    public int[] inorderTraversal(TreeNode root) {
        // write code here
        ArrayList<TreeNode> arrayList = new ArrayList<>();
        inorder(arrayList, root);
        int[] arr = new int[arrayList.size()];
        int i = 0;
        for (TreeNode node : arrayList) {
            arr[i++] = node.val;
        }
        return arr;
    }


    public void inorder(List<TreeNode> list, TreeNode root) {
        if (root == null)
            return;
        inorder(list, root.left);
        list.add(root);
        inorder(list, root.right);
    }

    /**
     * BM25 二叉树的后序遍历
     *
     * @param root
     * @return
     */
    public int[] postorderTraversal(TreeNode root) {
        // write code here
        ArrayList<TreeNode> arrayList = new ArrayList<>();
        postorder(arrayList, root);
        int[] arr = new int[arrayList.size()];
        int i = 0;
        for (TreeNode node : arrayList) {
            arr[i++] = node.val;
        }
        return arr;
    }


    public void postorder(List<TreeNode> list, TreeNode root) {
        if (root == null) {
            return;
        }
        preorder(list, root.left);
        preorder(list, root.right);
        list.add(root);
    }

    /**
     * BM26 求二叉树的层序遍历
     *
     * @param root
     * @return
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
        if (root == null) {
            return arrayLists;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (size > 0) {
                size--;
                TreeNode node = queue.poll();
                arrayList.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            arrayLists.add(arrayList);
        }

        return arrayLists;
    }

    /**
     * BM27 按之字形顺序打印二叉树
     * 给定一个二叉树，返回该二叉树的之字形层序遍历，（第一层从左向右，下一层从右向左，一直这样交替）
     *
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (pRoot == null) {
            return result;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(pRoot);
        int flag = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            ArrayList<Integer> arrayList = new ArrayList<>();
            while (size-- > 0) {
                int i = flag % 2;
                TreeNode tmp;
                if (i == 0) {
                    tmp = deque.removeFirst();
                    if (tmp.left != null) {
                        deque.addLast(tmp.left);
                    }
                    if (tmp.right != null) {
                        deque.addLast(tmp.right);
                    }
                } else {
                    tmp = deque.removeLast();
                    if (tmp.right != null) {
                        deque.addFirst(tmp.right);
                    }
                    if (tmp.left != null) {
                        deque.addFirst(tmp.left);
                    }

                }
                arrayList.add(tmp.val);
                //反方向添加
            }
            flag++;
            result.add(arrayList);
        }
        return result;
    }

    /**
     * BM28 二叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        // write code here
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int length = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            length++;
        }

        return length;
    }

//    /**
//     * BM28 二叉树的最大深度2
//     * @param root
//     * @return
//     */
//    public int maxDepth2 (util.TreeNode root) {
//
//    }

    /**
     * BM29 二叉树中和为某一值的路径(一)
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        // write code here
        if (root == null) {
            return false;
        }
        return dfs(root, sum);

    }

    public boolean dfs(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        sum -= root.val;
        if (root.left == null && root.right == null && sum == 0) {
            return true;
        }
        return dfs(root.left, sum) || dfs(root.right, sum);
    }


    /**
     * BM30 二叉搜索树与双向链表
     *
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }
        ArrayList<TreeNode> arrayList = new ArrayList<>();
        inorder(arrayList, pRootOfTree);
        for (int i = 0; i < arrayList.size() - 1; i++) {
            arrayList.get(i).right = arrayList.get(i + 1);
            arrayList.get(i + 1).left = arrayList.get(i);
        }
        return arrayList.get(0);
    }


    public void inorder(ArrayList<TreeNode> arrayList, TreeNode root) {
        if (root == null) {
            return;
        }
        inorder(arrayList, root.left);
        arrayList.add(root);
        inorder(arrayList, root.right);
    }

    /**
     * BM31 对称的二叉树
     *
     * @param pRoot
     * @return
     */
    boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) {
            return true;
        }

        Deque<TreeNode> deque1 = new LinkedList<>();
        deque1.add(pRoot.left);
        Deque<TreeNode> deque2 = new LinkedList<>();
        deque2.add(pRoot.right);
        while (!deque1.isEmpty() && !deque2.isEmpty()) {
            TreeNode left = deque1.poll();
            TreeNode right = deque2.poll();
            if (left == null && right == null) {
                continue;
            }
            if (left == null || right == null || left.val != right.val) {
                return false;
            }
            deque1.add(left.left);
            deque1.add(left.right);

            deque2.add(right.right);
            deque2.add(right.left);

        }
        return true;

    }

    /**
     * BM32 合并二叉树
     * 已知两颗二叉树，将它们合并成一颗二叉树。
     * 合并规则是：都存在的结点，就将结点值加起来，否则空的位置就由另一个树的结点来代替
     *
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        // write code here
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        TreeNode node = new TreeNode(t1.val + t2.val);
        node.left = mergeTrees(t1, t2);
        node.right = mergeTrees(t1, t2);
        return node;
    }


    /**
     * BM33 二叉树的镜像
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     *
     * @param pRoot
     * @return
     */
    public TreeNode Mirror(TreeNode pRoot) {
        // write code here
        if (pRoot == null) {
            return null;
        }
        TreeNode node = new TreeNode(pRoot.val);
        node.left = Mirror(node.right);
        node.right = Mirror(node.left);
        return node;
    }


    /**
     * BM34 判断是不是二叉搜索树
     * 给定一个二叉树根节点，请你判断这棵树是不是二叉搜索树。
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        // write code here
        ArrayList<Integer> list = new ArrayList<>();
        inOrder(root, list);
        for (int i = 0; i < list.size() - 2; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;

    }

    public void inOrder(TreeNode root, ArrayList<Integer> arrayList) {
        if (root == null) {
            return;
        }
        inOrder(root.left, arrayList);
        arrayList.add(root.val);
        inOrder(root.right, arrayList);

    }

    /**
     * BM35 判断是不是完全二叉树
     * 给定一个二叉树，确定他是否是一个完全二叉树。
     *
     * @param root
     * @return
     */
    public boolean isCompleteTree(TreeNode root) {
        // write code here
        if (root == null) {
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean notComplete = false;
        while (!queue.isEmpty()) {
            TreeNode tmp = queue.poll();
            if (tmp == null) {
                notComplete = true;
                continue;
            }
            if (notComplete) {
                return false;
            }
            queue.offer(tmp.left);
            queue.offer(tmp.right);
        }
        return true;
    }

    /**
     * BM36判断是不是平衡二叉树
     * 左右高度差不超过1
     *
     * @param root
     * @return
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        deep(root);
        return isBalanced;
    }

    boolean isBalanced = true;

    public int deep(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = deep(root.left);
        if (l == -1) {
            return -1;
        }
        int r = deep(root.right);
        if (r == -1) {
            return -1;
        }
        if (Math.abs(l - r) > 1) {
            isBalanced = false;
            return -1;
        }
        return Math.max(l, r) + 1;
    }

    /**
     * BM37 二叉搜索树的最近公共祖先
     *
     * @param root
     * @return
     */
    public int lowestCommonAncestor(TreeNode root, int p, int q) {
        // write code here
        ArrayList<Integer> list1 = getPath(root, p);
        ArrayList<Integer> list2 = getPath(root, q);
        int res = 0;
        for (int i = 0; i < list1.size() && i < list2.size(); i++) {
            int x = list1.get(i);
            int y = list2.get(i);
            if (x == y) {
                //记录每一个相同祖先节点
                res = list1.get(i);
            } else {
                break;
            }
        }
        return res;
    }

    public ArrayList<Integer> getPath(TreeNode root, int target) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        TreeNode temp = root;
        while (temp.val != target) {
            arrayList.add(temp.val);
            //小于目标值，往右走
            if (temp.val < target) {
                temp = temp.right;
            } else {
                temp = temp.left;
            }

        }
        arrayList.add(temp.val);
        return arrayList;
    }

}
