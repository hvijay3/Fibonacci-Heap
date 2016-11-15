import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harshit Vijayvargia on 11/12/2016.
 */
class FibonacciHeap
{
  // private static final double oneOverLogPhi =
       //     1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);
    private Fnode maxNode;
    private int nNodes;

    public FibonacciHeap()
    {
    }

    public boolean isEmpty()

    {
        return maxNode == null;
    }

    public void clear()
    {
        maxNode = null;
        nNodes = 0;
    }



    public void insert(Fnode node)
    {


        // concatenate node into min list
        if (maxNode != null) {
            node.left = maxNode;
            node.right = maxNode.right;
            maxNode.right = node;
            if ( node.right!=null) {                                // I added optimizing
                node.right.left = node;
            }
            if ( node.right==null)
            {
                node.right = maxNode;
                maxNode.left = node;
            }
            if (node.key > maxNode.key) {
                maxNode = node;
            }
        } else {
            maxNode = node;
            node.left = node;     // added
            node.right = node;   // added
        }

        nNodes++;
    }


    public void increaseKey(Fnode x, int k)
    {
        if (k < x.key) {
        }

        x.key = k;

        Fnode y = x.parent;

        if ((y != null) && (x.key > y.key)) {
            cut(x, y);
            cascadingCut(y);
        }

        if (x.key > maxNode.key) {
            maxNode = x;
        }
    }
    public Fnode max()
    {
        return maxNode;
    }

    public Fnode removeMax()
    {
        Fnode z = maxNode;

        if (z != null) {
            int numKids = z.degree;
            Fnode x = z.child;
            Fnode tempRight;
//System.out.print("ane se phle bacche" + numKids + (z.right == z));
            // for each child of z do...
            while (numKids > 0) {
                tempRight = x.right;

                // remove x from child list
                x.left.right = x.right;
                x.right.left = x.left;

                // add x to root list of heap
                x.left = maxNode;
                x.right = maxNode.right;
                maxNode.right = x;
                x.right.left = x;

                // set parent[x] to null
                x.parent = null;
                x = tempRight;

                numKids--;
               //System.out.println("ane se phle bacche" + numKids + (z.right == z));
            }


            // remove z from root list of heap
            z.left.right = z.right;
            z.right.left = z.left;

            if (z == z.right) {
                maxNode = null;
               // System.out.print("yha pr aya");
                //consolidate();
            } else {
               maxNode = z.right;

               // System.out.print("*"+maxNode);
                consolidate();
                //System.out.print("*"+maxNode);
            }

            // decrement size of heap
            nNodes--;
            return z;
        }

        return null;
    }

    public int size()
    {
        return nNodes;
    }





    /**
     * Performs a cascading cut operation. This cuts y from its parent and then
     * does the same for its parent, and so on up the tree.
     *
     * <p>Running time: O(log n); O(1) excluding the recursion</p>
     *
     * @param y node to perform cascading cut on
     */
    protected void cascadingCut(Fnode y)
    {
        Fnode z = y.parent;

        // if there's a parent...
        if (z != null) {
            // if y is unmarked, set it marked
            if (!y.mark) {
                y.mark = true;
            } else {
                // it's marked, cut it from parent
                cut(y, z);

                // cut its parent as well
                cascadingCut(z);
            }
        }
    }

    protected void cut(Fnode x, Fnode y)
    {
        // remove x from childlist of y and decrement degree[y]
        x.left.right = x.right;
        x.right.left = x.left;
        y.degree--;

        // reset y.child if necessary
        if (y.child == x) {
            y.child = x.right;
        }

        if (y.degree == 0) {
            y.child = null;
        }

        // add x to root list of heap
        x.left = maxNode;
        x.right = maxNode.right;
        maxNode.right = x;
        x.right.left = x;

        // set parent[x] to nil
        x.parent = null;

        // set mark[x] to false
        x.mark = false;
    }

    protected void consolidate()
    {
       // int arraySize =
                //((int) Math.floor(Math.log(nNodes) * oneOverLogPhi)) + 1;
        int arraySize =45;


        List<Fnode> array =
                new ArrayList<Fnode>(arraySize);

        // Initialize degree array
        for (int i = 0; i < arraySize; i++) {
            array.add(null);
        }
                                          // till here


        // Find the number of root nodes.
        int numRoots = 0;
        Fnode x = maxNode;
       // System.out.print(" max node in consolidate   " +x.hash);   // testing

        if (x != null) {
            numRoots++;
            x = x.right;                       // mistake

            while (x != maxNode) {
                numRoots++;
                x = x.right;
            }
        }
       // System.out.print("maxnode after while  "+ x.hash);       // testing
        //System.out.print(numRoots);

        // For each node in root list do...
        while (numRoots > 0) {
            // Access this node's degree..
            int d = x.degree;
            Fnode next = x.right;

            // ..and see if there's another of the same degree.
            for (;;) {
                Fnode y = array.get(d);
                if (y == null) {
                    // Nope.
                    break;
                }

                // There is, make one of the nodes a child of the other.
                // Do this based on the key value.
                if (x.key < y.key) {
                    Fnode temp = y;
                    y = x;
                    x = temp;
                }

                // FibonacciHeapNode<T> y disappears from root list.
                link(y, x);

                // We've handled this degree, go to next one.
                array.set(d, null);
                d++;
            }

            // Save this node for later when we might encounter another
            // of the same degree.
           // System.out.print(d + " " + x.hash);
            array.set(d, x);

            // Move forward through list.
            x = next;
            numRoots--;
        }


        // Set min to null (effectively losing the root list) and
        // reconstruct the root list from the array entries in array[].
        maxNode = null;                                                   //mistake

        for (int i = 0; i < arraySize; i++) {
            Fnode y = array.get(i);
            if (y == null) {
                continue;
            }


            // We've got a live one, add it to root list.
            if (maxNode != null) {

                                          //mistake
                // First remove node from root list.
                y.left.right = y.right;
                y.right.left = y.left;

                // Now add to root list, again.
                y.left = maxNode;
                y.right = maxNode.right;
                maxNode.right = y;
                y.right.left = y;

                // Check if this is a new min.
                if (y.key > maxNode.key) {
                    maxNode = y;
                }
            } else {
                maxNode = y;
            }
        }
    }

    // consolidate
    /**
     * Make node y a child of node x.
     *
     * <p>Running time: O(1) actual</p>
     *
     * @param y node to become child
     * @param x node to become parent
     */
    protected void link(Fnode y, Fnode x)
    {
        // remove y from root list of heap
        y.left.right = y.right;
        y.right.left = y.left;

        // make y a child of x
        y.parent = x;

        if (x.child == null) {
            x.child = y;
            y.right = y;
            y.left = y;
        } else {
            y.left = x.child;
            y.right = x.child.right;
            x.child.right = y;
            y.right.left = y;
        }

        // increase degree[x]
        x.degree++;

        // set mark[y] false
        y.mark = false;
    }

    // link
}


