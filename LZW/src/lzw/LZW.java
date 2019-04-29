/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Formatter;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;


/**
 *
 * @author Wahba
 */
public class LZW { 
   
      
   public void compress ( ) throws FileNotFoundException, IOException
           
   {
       int co=128;
       String first,next,tem;
       Vector <String> mystring = new Vector <> ();
       Vector <Integer> codes = new Vector <> ();
       
       //reading from the file
       BufferedReader b=null;
       b= new BufferedReader(new FileReader("compress.txt"));
       String text;
       text=b.readLine();
       
       //opening a new file to write to
       File f = new File("codes.txt");
       f.createNewFile();
       PrintWriter p= new PrintWriter(f);
       
       //Algorithm
       first="";
       int y=text.length()+1;
       
       for (int i=0; i<y; i++)
       {
           if(i==text.length())
           {
               if (first.length()==1)
               {
                   char c=first.charAt(0);
                   int a = (int) c;
                   p.println(a);
                   
                   break;
               }
               else
               {
                   int t= mystring.indexOf(first);
                   p.println(codes.get(t));
                  
                   break;
                   
               }
           }
           char temp=text.charAt(i);
           next= Character.toString(temp);
           tem=first.concat(next);
           if (mystring.contains(tem) || tem.length()==1)
           {
               first=tem;
           }
           else 
           {
               if (first.length()==1)
               {
                   char c=tem.charAt(0);
                   int a = (int) c;
                   p.println(a);
                   mystring.add(tem);
                   codes.add(co);
                   co++;
                   first=next;
                                      
               }
               else
               {
                   int t= mystring.indexOf(first);
                   p.println(codes.get(t));
                   mystring.add(tem);
                   codes.add(co);
                   co++;
                   first=next;
               }
               
           }
     }
       
       p.close();
   }
   
   
   public void decomression () throws FileNotFoundException, IOException
   {
       int co=128, f,n;
       char next;
       String first,temp;
       Vector <String> mystring = new Vector <> ();
       Vector <Integer> stream = new Vector <> ();
       
       //filling the dectionary with all possible values
       Vector <Integer> codes = new Vector <> ();
       for (int a=0; a<128; a++)
       {
           codes.add(a);
           String s= Character.toString((char) a);
           mystring.add(s);
       }
       
       //reading from a file to a vector
       BufferedReader b;
       b= new BufferedReader(new FileReader("codes.txt"));
       String t;
       while ((t=b.readLine())!=null)
       {
           
           int tt = Integer.parseInt(t);
           stream.addElement(tt);
       }
       b.close();
       //opening a new file to write to
       File ff;
       ff = new File("string.txt");
       ff.createNewFile();
       //Algorithm
       try (PrintWriter p = new PrintWriter(ff)) {
           //Algorithm
           n=stream.get(0);
           String tm= Character.toString((char) n);
           p.print(tm);
           

           for ( int i=1; i<stream.size(); i++)
           {
               f=n;
               n=stream.get(i);
               
               if (codes.contains(n))
               {
                   int aa= codes.indexOf(n);
                   String ss= mystring.get(aa);
                   p.print(ss);
                   int a= codes.indexOf(f);
                   first=mystring.get(a);
                   next=ss.charAt(0);
                   String tt= ""+ss;
                   temp=first.concat(tt);
                   mystring.add(temp);
                   System.out.println("----tempppppt:   "+temp);
                   codes.add(co);
                   co++;
                   
                   
               }
               else
               {
                   int a= codes.indexOf(f);
                   System.out.println("A:  "+a);
                   first=mystring.get(a);
                   System.out.println("First:   "+first);
                   next = first.charAt(0);
                   System.out.println("Next:   "+next);
                   String k= ""+next;
                   temp=first.concat(k);
                   System.out.println("Temp:   "+temp);
                   p.print(temp);
                   mystring.add(temp);
                   codes.add(co);
                   co++;
               }
           }
       }
       
       
       
       
   }
   
   
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        lzw1 ff=new lzw1();
        ff.setVisible(true); 
        
    }

    private void compress(Scanner In) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
