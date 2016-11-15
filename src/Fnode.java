/**
 * Created by Harshit Vijayvargia on 11/7/2016.
 */
/*public class fibo
{
    node maxnode;*/
    class Fnode
    {
        int degree = 0;       // Number of children
        boolean mark = false; // Whether this node is marked

        public Fnode left;
        public Fnode right;
        public Fnode child;



        public Fnode parent;
        public String hash;
        public int key;
        public Fnode(String hash,int key)
        {
           this.left = null;
            this.right = null;
            this.parent = null;
            //this.key =0;
            this.degree = 0;             // I added
            this.hash = hash;
            this.key = key;

        }

    }



