package cmsc256;

public class BinarySearchTree <E extends Comparable<? super E>>{
    private int size = 0;
    private BinaryNode root = null;

    //inner class of BinarySearchTree that represents a node in the tree
    class BinaryNode {
        protected E value;
        protected BinaryNode right;
        protected BinaryNode left;

        //constructor that will store the node's data
        public BinaryNode(E valueIn) {
            value = valueIn;
        }
    }
        private boolean addToParent (BinaryNode parentNode, BinaryNode addNode){
            //compare the parent node value with the child node value
            int compare = parentNode.value.compareTo(addNode.value);
            //determine if the node is added
            boolean wasAdded = false;

            //determine if parent is greater than child value
            if(compare > 0){
                //if parent as no left node (left = null), add new node as left
                if(parentNode.left == null){
                    parentNode.left = addNode;
                    wasAdded = true;
                }
                //otherwise, add to parentNode's left (recursive)
                else{
                    wasAdded = addToParent(parentNode.left, addNode);
                }
            }
            else if(compare < 0){
                //if parent has no right node (right == null), add new node as right
                if(parentNode.right == null){
                    parentNode.right = addNode;
                    wasAdded = true;
                }
                //otherwise, add to parentNode's right (recursive)
                else {
                    wasAdded = addToParent(parentNode.right, addNode);
                }
            }
            return wasAdded;
        }

        //method to add a value to the tree
        public boolean add(E inValue){
            BinaryNode node = new BinaryNode(inValue);
            boolean wasAdded = true;

            //if the root is empty (root == null), set root to new node
            if(root == null){
                root = node;
            }
            //otherwise, add the value to the tree using the root as the parent
            else{
                wasAdded = addToParent(root, node);
            }

            //if element successfully added to tree, size++
            if(wasAdded){
                size++;
            }
            return wasAdded;
        }

        //method to remove a value from the tree and return true if that value is found
        public boolean remove(E removeValue){
            //if tree is empty (root == null), return false
            if(root == null){
                return false;
            }

            if(root.value.compareTo(removeValue) == 0){
                //if left is null, then make right the root
                if(root.left == null){
                    root = root.right;
                }
                //if right is null, then make left the root
                else if(root.right == null){
                    root = root.left;
                }
                //if neither is null, set left as root and add right to left using addToParent method
                else{
                    BinaryNode formerRight = root.right;
                    root = root.left;
                    addToParent(root, formerRight);
                }
                size--;
                return true;
            }
            return removeSubNode(root, removeValue);
        }

        /* method that will remove a node that was not determined to be the root,
         * or work its way down the branches of the tree to remove the appropriate mode
         */
        private boolean removeSubNode(BinaryNode parent, E removeValue){
            //compare parent node to the value being removed
            int compareParent = parent.value.compareTo(removeValue);

            //determine if the value being removed is on the right or left of the parent
            BinaryNode subTree = null;
            if(compareParent > 0){
                subTree = parent.left;
            }
            else{
                subTree = parent.right;
            }

            //if branch is null (subTree == null), then value does not exist on tree
            if(subTree == null){
                return false;
            }

            //if the value is equal to the value currently on the branch, then the node can be deleted
            if(subTree.value.compareTo(removeValue) == 0){
                BinaryNode replacement;
                if(subTree.left == null){
                    replacement = subTree.right;
                }
                else if(subTree.right == null){
                    replacement = subTree.left;
                }
                else{
                    BinaryNode formerRight = subTree.right;
                    replacement = subTree.left;
                    addToParent(replacement, formerRight);
                }

                if(compareParent > 0){
                    parent.left = replacement;
                }
                else{
                    parent.right = replacement;
                }

                size--;
                return true;
            }
            return removeSubNode(subTree, removeValue);
        }

        //method to return size of tree
        public int size(){
            return size;
        }

        //method to return the root
        public BinaryNode getRoot(){
            return root;
        }

        //method to make the tree empty
        public void clear(){
            root = null;
            size = 0;
        }
    }

