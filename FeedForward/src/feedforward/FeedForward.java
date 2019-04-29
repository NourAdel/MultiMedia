/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package feedforward;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Wahba
 */
public class FeedForward {

    
    public static class node
          
    {
        int data;
        int diff;
        int quantization;
        int Dequantization;
        int decode;
        int error;
        
        public node(int v)
        {
            data=v;
        }
    }
    public static class table
    {
        int code;
        int lowR;
        int highR;
        int Qi;
        
    }
    
    public static ArrayList<node> nodes=new ArrayList<>();
    public static ArrayList<table> tableLevels=new ArrayList<>();
    public static int numberofbits;
    public static int width;
    public static int height;
    
    public void readd() throws IOException
    {
         File f = null;
        f = new File("C:\\Users\\Wahba\\Desktop\\nn.png");
        BufferedImage im = null;
       
            //im = new BufferedImage(width , height , BufferedImage.TYPE_INT_ARGB);
        im = ImageIO.read(f);
         width = im.getWidth();
         height = im.getHeight();
        int arr[][] = new int[height][width];
        for(int i = 0 ; i < height; i++)
            for(int j = 0 ; j < width; j++){
                int p = im.getRGB(j, i);
                int r = (p>>16)&0xff;
                arr[i][j] = r;
           }
        
        for (int i=0 ; i<height; i++)
        {
            for (int j=0; j<width; j++)
            {
                nodes.add(new node(arr [i][j]));
            }
        }
        
    }
   
    public void calDiff ()
    {
       
        for (int i=1; i<nodes.size(); i++)
        {
            nodes.get(i).diff=nodes.get(i).data-nodes.get(i-1).data;
        }
    }
    
    public void buildTable()
    {
        int levels=(int) Math.pow(numberofbits, 2.0);
        int max=1;
        int min=1;
        int range;
        
        
        
        for (int i=1; i<nodes.size(); i++ )
        {
            if (nodes.get(i).diff<nodes.get(min).diff)
                min=i;
            if (nodes.get(i).diff>nodes.get(min).diff)
                max=i;
        }
        double tt = ((double)nodes.get(max).diff - (double)nodes.get(min).diff)/(double)levels;
        System.out.println(tt);
        range =(int)Math.ceil(tt);
        int ic=0;
        int ilr=nodes.get(min).diff;
        for (int i=0; i<levels; i++)
        {
            table t=new table();
            t.code=ic;
            t.lowR=ilr;
            t.highR=ilr+range;
            double x= ((double)t.highR+(double)t.lowR)/2;
            t.Qi=(int)Math.ceil(x);
            ilr=t.highR+1;
            ic++;
            tableLevels.add(t);
            
        }
        System.out.println("range: "+ range);
        System.out.println("min: "+ nodes.get(min).diff);
        System.out.println("max: "+ nodes.get(max).diff);
        
        for (int i=0; i<tableLevels.size(); i++)
        {
           System.out.println(tableLevels.get(i).code +"  "+tableLevels.get(i).lowR+" -> "+tableLevels.get(i).highR+"  "+tableLevels.get(i).Qi);

        }
    }
    


    public void Quantization()
    {
        for (int i=1; i<nodes.size(); i++)
        {
            
            for(int j=0; j<tableLevels.size(); j++)
            {
                
                System.out.println("k");
                if(nodes.get(i).diff>= tableLevels.get(j).lowR &&nodes.get(i).diff<= tableLevels.get(j).highR)
                {
                    System.out.println("code: "+ tableLevels.get(j).code);
                    nodes.get(i).quantization=tableLevels.get(j).code;
                    
                }
            }
        }
    }
    
    public void compress() throws IOException
    {
        readd();
        calDiff();
        buildTable();
        Quantization();
        
        File f=new File("compressed.txt");
        f.createNewFile();
        PrintWriter p=new PrintWriter(f);
        
        p.println("Data   "+"Diffrence   "+"Quantization");
        
        for(int i=0; i<nodes.size(); i++)
        {
            p.println(nodes.get(i).data+"   "+nodes.get(i).diff+"   "+nodes.get(i).quantization);
        }
        
        p.close();
        
    }
    
    public void Dequantization()
    {
        for (int i=1; i<nodes.size(); i++)
        {
            for (int j=0; j<tableLevels.size(); j++)
                if (nodes.get(i).diff>= tableLevels.get(j).lowR &&nodes.get(i).diff<= tableLevels.get(j).highR)
                {
                    nodes.get(i).Dequantization=tableLevels.get(j).Qi;
                    break;
                }
        }
        
    }
    
    public void Decode()
    {
        nodes.get(0).decode=nodes.get(0).data;
        
        for (int i=1; i<nodes.size(); i++)
        {
            nodes.get(i).decode=nodes.get(i-1).decode+nodes.get(i).Dequantization;
                    
        }
    }
     public void error()
     {
         for (int i=1; i<nodes.size(); i++)
         {
             nodes.get(i).error=nodes.get(i).decode-nodes.get(i).data;
         }
     }
     
     public void Decompress() throws IOException
     {
         Dequantization();
         Decode();
         error();
         writeI();
         
        File f=new File("Decompressed.txt");
        f.createNewFile();
        PrintWriter p=new PrintWriter(f);
        
        p.println("Dequantization   "+"decode   "+"error");
        
        for(int i=0; i<nodes.size(); i++)
        {
            p.println(nodes.get(i).Dequantization+"   "+nodes.get(i).decode+"   "+nodes.get(i).error);
        }
        
        p.close();
         
     }
    public void writeI() throws IOException
    {
        int arr [][]= new int [height][width];
        int c=0;
        for (int i=0; i<height; i++)
        {
            for (int j=0; j<width; j++)
            {
                arr[i][j]=nodes.get(c++).decode;
                        
            }
        }
        
         File f = new File("C:\\Users\\Wahba\\Desktop\\nn.png");;
        BufferedImage im = null;
       im = ImageIO.read(f);
            //im = new BufferedImage(width , height , BufferedImage.TYPE_INT_ARGB);
        
            for(int i = 0 ; i < height; i++)
            for(int j = 0 ; j < width; j++){
                
                arr[i][j] = (arr[i][j]<<24) | (arr[i][j]<<16) | (arr[i][j]<<8) | arr[i][j];
                im.setRGB(j, i, arr[i][j]);
           }
            f = new File("C:\\Users\\Wahba\\Desktop\\nll.png");
        
            ImageIO.write(im, "png", f);
            
    }
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        Gui g=new Gui();
        g.setVisible(true);
       /* Scanner in= new Scanner(System.in);
        int num=in.nextInt();
        for (int i=0; i<num; i++)
        {
            int n=in.nextInt();
            d.nodes.add(new node(n));
            
        }
        int l=in.nextInt();
        d.numberofbits=l;
        
        d.compress();
        d.Decompress();
        
        for (int i=0; i<d.nodes.size(); i++)
        {
            System.out.println(d.nodes.get(i).data +"  "+d.nodes.get(i).diff+"  "+ d.nodes.get(i).quantization+"  "+d.nodes.get(i).Dequantization+ "   "+ d.nodes.get(i).decode+"   "+ d.nodes.get(i).error );
        }
        
        System.out.println("----------------------------------");
        
        for (int i=0; i<d.tableLevels.size(); i++)
        {
           System.out.println(d.tableLevels.get(i).code +"  "+d.tableLevels.get(i).lowR+" -> "+ d.tableLevels.get(i).highR+"  "+d.tableLevels.get(i).Qi);

        }*/
    }
    
}
