/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lbg;
import java.lang.Math;
import java.util.Vector;

/**
 *
 * @author Wahba
 */
public class lbg {
    class LBG
    {
    public int value;
    public int less;
    public int greater;
    public Vector <Integer> LD=new Vector<>();
    public Vector <Integer> RD=new Vector<>();
    }
    
    class range
    {
        int low,high;
    }
    
    double numberofbits;
    int n= (int) java.lang.Math.pow(numberofbits, 2);
    Vector<Integer> values=new Vector<>();
    Vector<LBG> avrages=new Vector<>(); 
    Vector <range> ranges=new Vector<>();
    Vector <Integer> mids=new Vector<>();
     
    
    
    

    public int Avrage (Vector<Integer> a)
    {
        
        
        float av=0;
        for (int i=0; i<a.size();i++)
        {
            av+=a.get(i);
        }
        
        av/=a.size();
        int x=(int) Math.round(av);
        return x;
    }
    
    public void split (Vector<Integer> a )
    {
        if (n==0)
            return;
        LBG x= new LBG();
        x.value=Avrage(a);
        x.less=x.value-1;
        x.greater=x.value+1;
        for (int i=0; i<values.size(); i++)
        {
            if (values.get(i)<=x.value)
                x.LD.add(values.get(i));
            else
                x.RD.add(values.get(i));
        }
        if (n==1)
        {
            avrages.add(x);
        }
        n--;
        split(x.LD);
        split(x.RD);
        
        
        
    }
    
    public void setTable()
    {
        for (int i=0; i<avrages.size()-1;i++)
        {
            int x= avrages.get(i).value+avrages.get(i+1).value/2;
            mids.add(x);
        }
        for (int i=0; i<mids.size()-1; i++)
        {
            range x=new range();
            if (i==0)
            {
                x.low=0;
                x.high=mids.get(0);
                ranges.add(x);
            }
            else
            {
                x.low=mids.get(i-1);
                x.high=mids.get(i);
                ranges.add(x);
                        
            }
        }
    
    

   
    
}
    public void compress()
    {
        
    }
     public static void main(String[] args) {
        // TODO code application logic here
    }
}