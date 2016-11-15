
 import java.io.*;
 import java.util.*;
 import java.text.*;
 import java.math.*;
 import java.util.regex.*;

 public class Solution {

 public static void main(String[] args) {
 Scanner in = new Scanner(System.in);
 int n = in.nextInt();
     StringBuilder res =new StringBuilder("");
 for(int a0 = 0; a0 < n; a0++){
 String op = in.next();
 String contact = in.next();

 if ( op.equals("add"))
 {
     res.append(" "+contact);

 }
 if (op.equals("find"))
 {

     Pattern pat = Pattern.compile("\\s"+contact);
     Matcher m = pat.matcher(res);
     int count = 0;
while(m.find())
{
   count++;

 }
 System.out.println(count);
 }
 }
 }
 }