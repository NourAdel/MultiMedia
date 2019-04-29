/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arithmetic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Wahba
 */
public class Arithmetic {

        int t;
        double value;
        int StringL=0;
        int j=0;
        int[] allchars = new int[127];
        char [] chars;
        double [] probs ;
        double [] high ;
        double [] low ;
        
    public int compress() throws FileNotFoundException, IOException
    {

        
        //reading from a file
        BufferedReader b = new BufferedReader(new FileReader("compress.txt"));
        String text;
        text = b.readLine();
        
       //openenig a file to write to
       File f = new File("Answer.txt");
       f.createNewFile();
       PrintWriter p= new PrintWriter(f);
       
       for (int i=0; i<allchars.length; i++)
       {
           allchars[i]=0;
       }
       
        for (int i=0; i< text.length(); i++) {
            allchars[(int) text.charAt(i)]++;            
        }
        for (int i=0; i< allchars.length; i++)
        {
            if (allchars[i]>0)
                StringL++;
            
        }
                
                


        chars= new char[StringL];
         probs = new double[StringL];
         high = new double[StringL];
        low = new double[StringL];
        
        for (int i=0; i<allchars.length;i++)
        {
            if(allchars[i]>0)
            {
                chars[j]= (char)i;
                probs[j]= (double) allchars[i]/text.length();
               
                j++;
            }
        }
        
        low[0]=0;
        high[0]=probs[0];
        
     

        
        for (int i=1; i<probs.length;i++)
        {
            low[i]=high[i-1];
            high[i]=high[i-1]+probs[i];
             
        }
                    

        double lower=0;
        double upper=1;
        double range= upper-lower;
        Boolean a=true;
        for(int i=0; i<text.length();i++)
        {
            a=true;
            for (int k=0; k<chars.length;k++)
            {
                if(chars[k]==text.charAt(i))
                {
                    a=false;
                    upper=lower+(range*high[k]);
                    lower=lower+(range*low[k]);
                    range= upper-lower;
                    
                }
                if (a==false)
                    break;
            }
        }
         value=(lower+upper)/2;
         p.print(value);
         p.close();    
        
        return text.length();
       
      
       
    }
    public void decompress(int textL) throws FileNotFoundException, IOException
    {
        BufferedReader b = new BufferedReader(new FileReader("Answer.txt"));
        String t;
        t= b.readLine();
        double Dvalue= Double.parseDouble(t);
        //opening a new file to write to
        File ff;
        ff = new File("Decompression.txt");
        ff.createNewFile();
        PrintWriter p= new PrintWriter(ff);
        
        double lower=0;
        double upper=1;
        double range= upper-lower;
        Boolean a=true;
        
        for (int m=0; m<textL ; m++)
        {
            a=true;
            for (int i=0; i<chars.length;i++)
            {
                if(Dvalue> low[i]&& Dvalue<= high[i])
            {
                a=false;
                p.print(chars[i]);
                
                upper=high[i];
                lower=low[i];
                
                range= upper-lower;
                
                Dvalue=(Dvalue-lower)/range;
            }
                if(a==false)
                    break;
        }
    }
    
    p.close();
    }
    
    public static void main(String[] args) throws IOException {
       
       // int t;
      //Arithmetic a = new Arithmetic();
    //t=a.compress();
    //a.decompress(t);
    GUI t=new GUI();
    t.setVisible(true);
       }
}