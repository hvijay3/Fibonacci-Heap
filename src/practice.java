/**
 * Created by Harshit Vijayvargia on 11/5/2016.
 */

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Harshit Vijayvargia on 11/5/2016.
 */

public class practice {


    public static void main(String[] args) throws Exception {

        String  thisLine = "sampleInput_Million.txt";
        HashMap<String,Fnode> table = new HashMap();
        FibonacciHeap obj = new FibonacciHeap();
        String output1 = "output_file.txt";
        File file = new File("Output.txt");
        BufferedWriter output=null;
        try {
            // open input stream test.txt for reading purpose.
            FileReader sourcefile = new FileReader(thisLine);
            BufferedReader br = new BufferedReader(sourcefile);
            String s = br.readLine();

            Pattern p = Pattern.compile("([#?])([a-z_]+)(\\s)(\\d+)");
            Pattern p1 = Pattern.compile("(\\d+)");
            Pattern p2 = Pattern.compile("STOP");
            output = new BufferedWriter( new FileWriter(file));

            while (s != "STOP") {

                Matcher m = p.matcher(s);
                Matcher m1 = p1.matcher(s);
                Matcher m2 = p2.matcher(s);

                if (m.find()) {
                    String data = m.group(2);
                    int key = Integer.parseInt(m.group(4));
                    //System.out.println(data + " " + key);
                    if ( table.containsKey(m.group(2)))
                    {

                        key = table.get(m.group(2)).key + key;
                        obj.increaseKey(table.get(m.group(2)),key);
                        // modify fibonacci heap structure
                        // Increase key operation , increase

                    }
                   else
                    {
                        Fnode node = new Fnode(m.group(2),key);
                        obj.insert(node);
                        table.put(m.group(2),node);

                        // Insert into fibonacci heap insert( table.get(m.group(2)),key)
                    }





                } else if (!m.find() && m1.find()) {
                    int keystop = Integer.parseInt(m1.group(1));
                    ArrayList<Fnode> removed_nodes = new ArrayList<Fnode>(keystop);
                   // System.out.println(removed_nodes.size());
                    for ( int i=0;i<keystop;i++)
                    {

                        Fnode node = obj.removeMax();
                        // remove from hashtable also
                         table.remove(node.hash);
                        Fnode node1 = new Fnode(node.hash,node.key);
                        //node.degree = 0 ;
                        //node.mark = false;
                        removed_nodes.add(node1);
                        if ( i <keystop-1) {
                            output.write(node.hash + ", ");

                        }
else {
                            output.write(node.hash);


                        }
                        //System.out.println(node);
                        // getting null in consolidate

                       //System.out.println(node.hash + " " + node.key );
                    }
                    for ( Fnode iterate : removed_nodes)
                    {
                        //iterate.degree = 0;
                                               //added
                            obj.insert(iterate);
                        table.put(iterate.hash,iterate);


                    }
                    //System.out.print( " after Insertion " +obj.size());
                   // System.out.println(" ");

                    // extract maxkey keystop times , write them to a file
                    // again insert the extracted ones.
                    //  insert again println in outputfile

output.newLine();
                }
                else if (m2.find()) {
                    break;

                }



                s = br.readLine();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if ( output != null ) {
                try {
                    output.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        }
        System.out.println(" ");

        /*for ( Map.Entry<String, Fnode> iterate : table.entrySet() )
        {
            System.out.println( iterate.getKey() + " " + iterate.getValue().key);
        }*/
    }
}

