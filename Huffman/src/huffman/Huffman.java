/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Wahba
 */
public class Huffman {

    static Map<Character, String> Dectionary= new HashMap<>();
    public void compress() throws FileNotFoundException, IOException {

        //reading from the file
        BufferedReader b = new BufferedReader(new FileReader("compress.txt"));
        String text;
        text = b.readLine();

        int[] FrequencyTable = ft(text);
        Node root = tree(FrequencyTable);
        Dectionary = table(root);
        System.out.println();
        PrintToFile(text, Dectionary);

        
    }

    public void PrintToFile(String x, Map<Character, String> t) throws IOException {
        File f = new File("table.txt");
        f.createNewFile();
        PrintWriter p = new PrintWriter(f);
        for (char c : x.toCharArray()) {
            p.println(t.get(c));
            System.out.print(t.get(c)+ " ");
            System.out.println(c);

        }

        p.close();
    }

    public class Node implements Comparable<Node> {

        public char character;
        public int frequency;
        public Node left;
        public Node right;

        Node(char c, int f, Node l, Node r) {
            this.character = c;
            this.frequency = f;
            this.left = l;
            this.right = r;
        }

        @Override
        public int compareTo(Node o) {
            int f = Integer.compare(this.frequency, o.frequency);
            if (f != 0) {
                return f;
            }
            return Integer.compare(this.character, o.character);
        }
    }

    public int[] ft(String x) {
        int[] freq = new int[127];
        for (char c : x.toCharArray()) {
            freq[c]++;
        }
        return freq;
    }

    public Node tree(int[] f) {
        PriorityQueue<Node> q = new PriorityQueue();
        for (int i = 0; i < f.length; i++) {
            if (f[i] > 0) {
                q.add(new Node((char) i, f[i], null, null));

            }
        }
        if (q.size() == 1) {
            q.add(new Node('\0', 1, null, null));
        }

        while (q.size() > 1) {
            Node nl = q.poll();
            Node nr = q.poll();
            Node parent = new Node('\0', nl.frequency + nr.frequency, nl, nr);
            q.add(parent);
        }
        return q.poll();
    }

    public void table0(Node n, String s, Map<Character, String> m) {
        if (n == null) {
            return;
        }
        m.put(n.character, s);
        table0(n.left, s + "1", m);
        table0(n.right, s + "0", m);
    }

    public Map<Character, String> table(Node root) {
        Map<Character, String> t = new HashMap<>();
        table0(root, "", t);

        return t;
    }

    public void decompress(Map<Character, String> tt) throws FileNotFoundException, IOException {
        Vector<String> buf = new Vector<String>();
        Set<Character> pre = tt.keySet();
        Character[] arr = pre.toArray(new Character[pre.size()]);
        

        BufferedReader b;
        b = new BufferedReader(new FileReader("table.txt"));
        String t;
        while ((t = b.readLine()) != null) {
            buf.addElement(t);
        }
        b.close();
        //opening a new file to write to
        File ff;
        ff = new File("string.txt");
        ff.createNewFile();
        PrintWriter p= new PrintWriter(ff);
        for (int i = 0; i < buf.size() ; i++) {
            for(int j=0; j<arr.length; j++)
            {
                if(buf.get(i).equals(tt.get(arr[j])))
                {
                    p.print(arr[j]);
                    break;
                }
            }

        }
        p.close();
    }

    public static void main(String[] args) throws IOException {

        GUI test=new GUI();
        test.setVisible(true);
    }

}
