package Homework_2.Huffman;

import java.io.*;
import java.util.*;

/**
 * 可以为这个类添加额外的方法及数据成员.
 * ID就是指学号, 下面的作者一定要写上你的名字和学号
 * 作业中出现的示范数据abdc001需要改成学生的学号数据
 *
 * @author YOUR NAME and ID
 * @version THE DATE
 **/


public class TextZip {

    //ID, 该学号的值需要修改!
    private static final String ID = "201592150";
    private static Hashtable codes = new Hashtable();



/**
     * This method generates the huffman tree for the text: "abracadabra!"
     * 此方法生成文本的哈夫曼树。
     * @return the root of the huffman tree
     * 返回哈夫曼树的根节点*/

    public static TreeNode abracadbraTree() {
        TreeNode n0 = new TreeNode(new CharFreq('!', 1));
        TreeNode n1 = new TreeNode(new CharFreq('c', 1));
        TreeNode n2 = new TreeNode(new CharFreq('\u0000', 2), n0, n1);
        TreeNode n3 = new TreeNode(new CharFreq('r', 2));
        TreeNode n4 = new TreeNode(new CharFreq('\u0000', 4), n3, n2);
        TreeNode n5 = new TreeNode(new CharFreq('d', 1));
        TreeNode n6 = new TreeNode(new CharFreq('b', 2));
        TreeNode n7 = new TreeNode(new CharFreq('\u0000', 3), n5, n6);
        TreeNode n8 = new TreeNode(new CharFreq('\u0000', '7'), n7, n4);
        TreeNode n9 = new TreeNode(new CharFreq('a', 5));
        TreeNode n10 = new TreeNode(new CharFreq('\u0000', 12), n9, n8);
        return n10;
    }

/**
     * This method decompresses a huffman compressed text file.
     * The compressed file must be read one bit at a time using the supplied BitReader,
     * and then by traversing the supplied huffman tree,
     * each sequence of compressed bits should be converted to their corresponding characters.
     * The decompressed characters should be written to the FileWriter
     *
     * 此方法解压缩一个哈夫曼压缩文本文件。
     *使用提供的BitReader对压缩文件一次一个比特的读
     *然后通过遍历提供的huffman tree
     *每个压缩位序列都应该转换成相应的字符
     *解压缩的特性应该被写入FileWriter
     *
     * @param br the BitReader which reads one bit at a time from the compressed file
     *           huffman the huffman tree that was used for compression, and
     *           hence should be used for decompression
     *           fw      a FileWriter for storing the decompressed text file
     *
     *           BitReader从压缩文件中一次读一个比特
     *           FileWriter用于存储解压缩的文本文件输出*/


    public static void decompress(BitReader br, TreeNode huffman, FileWriter fw) throws Exception {

        // IMPLEMENT THIS METHOD
        while (br.hasNext()) {
            TreeNode t = huffman;
            do {
                boolean b = br.next();
                t = b ? t.getRight() : t.getLeft();
            } while (!t.isLeaf());

            fw.write(((CharFreq) t.getItem()).getChar());
        }

    }

/**
     * This method traverses the supplied huffman tree and prints out the
     * codes associated with each character
     *
     * 此方法遍历所提供的哈夫曼树并打印出与每个字符相关联的代码
     *
     * @param t the root of the huffman tree to be traversed 遍历
     *          code a String used to build the code for each character as
     *          the tree is traversed recursively
     *          写一个用于递归遍历树时为每个字符构建代码的字符串*/


    public static void traverse(TreeNode t, String code) {

        // IMPLEMENT THIS METHOD
        traverse(t, code, true);
    }

    private static void traverse(TreeNode t, String code, boolean verbose) {
        if (t.isLeaf()) {
            char c = ((CharFreq) t.getItem()).getChar();
            if (verbose) { System.out.println(c + " : " + code); }
            codes.put(new Character(c), code);
        }
        else
        {
            traverse(t.getLeft(),code+"0", verbose);
            traverse(t.getRight(),code+"1", verbose);
        }


    }
/**
     * This method removes the TreeNode, from an ArrayList of TreeNodes,  which
     * contains the smallest item.  The items stored in each TreeNode must
     * implement the Comparable interface.
     * The ArrayList must contain at least one element.
     *
     * 此方法从ArrayList删除包含的最小项的树节点
     *存储在每个树节点的项目必须实现Comparable接口
     *数组列表必须包含至少一个元素。
     *
     * @param a an ArrayList containing TreeNode objects
     * @return the TreeNode in the ArrayList which contains the smallest item.
     * This TreeNode is removed from the ArrayList.*/


    public static TreeNode removeMin(ArrayList a) {
        int minIndex = 0;
        for (int i = 0; i < a.size(); i++) {
            TreeNode ti = (TreeNode) a.get(i);
            TreeNode tmin = (TreeNode) a.get(minIndex);
            if (((Comparable) (ti.getItem())).compareTo(tmin.getItem()) < 0)
                minIndex = i;
        }
        TreeNode n = (TreeNode) a.remove(minIndex);
        return n;
    }

/**
     * This method counts the frequencies of each character in the supplied
     * FileReader, and produces an output text file which lists (on each line)
     * each character followed by the frequency count of that character.  This
     * method also returns an ArrayList which contains TreeNodes.  The item stored
     * in each TreeNode in the returned ArrayList is a CharFreq object, which
     * stores a character and its corresponding frequency
     *
     * 这种方法在提供有每个字符的频率计数，并产生一个输出文本文件列表（每行）每个字符后跟该字符的频率计数。
     *该方法返回一个数组包含treenodes。存储在每个树节点在返回的数组列表的项目是一个charfreq对象，它存储字符和对应的频率
     *
     * @param fr the FileReader for which the character frequencies are being
     *           counted
     *           pw the PrintWriter which is used to produce the output text file
     *           listing the character frequencies
     * @return the ArrayList containing TreeNodes.  The item stored in each
     * TreeNode is a CharFreq object.*/



    public static ArrayList countFrequencies(FileReader fr, PrintWriter pw) throws Exception {

        // IMPLEMENT THIS METHOD

        ArrayList al = new ArrayList();
        int[] a = new int[128];
        //128
        int c;
        while ((c=fr.read()) != -1){
            a[c]++;
        }

        for (int i=0;i<a.length;i++) {
            if (a[i]!=0){
                CharFreq cf = new CharFreq((char)i,a[i]);
                al.add(new TreeNode(cf));
                pw.println((char)i + " " + a[i]);
            }
        }
        return al;
    }

/**
     * This method builds a huffman tree from the supplied ArrayList of TreeNodes.
     * Initially, the items in each TreeNode in the ArrayList store a CharFreq object.
     * As the tree is built, the smallest two items in the ArrayList are removed,
     * merged to form a tree with a CharFreq object storing the sum of the frequencies
     * as the root, and the two original CharFreq objects as the children.  The right
     * child must be the second of the two elements removed from the ArrayList (where
     * the ArrayList is scanned from left to right when the minimum element is found).
     * When the ArrayList contains just one element, this will be the root of the
     * completed huffman tree.
     *
     *该方法从 ArrayList提供的树节点建立了的哈夫曼树。
     *最初，在ArrayList的每个树节点存储charfreq的对象。
     *在建立树时，最小的两个项目在ArrayList中删除并合并，形成一个存储的频率总和的charfreq对象为根的树，原来的两个charfreq对象为孩子节点。
     *右边的孩子必须是两元素中第二个从列表移除的（ArrayList从左到右进行扫描，当最小单元时发现被发现时）
     *当数组只包含一个元素，这将是完成哈夫曼树的根。
     *
     * @param trees the ArrayList containing the TreeNodes used in the algorithm
     *              for generating the huffman tree
     * @return the TreeNode referring to the root of the completed huffman tree*/


    public static TreeNode buildTree(ArrayList trees) throws IOException {

        // IMPLEMENT THIS METHOD
        while (trees.size()>=2) {
            TreeNode t1 = removeMin(trees);
            TreeNode t2 = removeMin(trees);
            int f1 = ((CharFreq)t1.getItem()).getFreq();
            int f2 = ((CharFreq)t2.getItem()).getFreq();

            CharFreq cf = new CharFreq('\u0000', f1 + f2);
            TreeNode a = new TreeNode(cf,t1, t2);
            trees.add(a);
        }
        return (TreeNode) trees.get(0);
    }

/**
     * This method compresses a text file using huffman encoding.  Initially, the
     * supplied huffman tree is traversed to generate a lookup table of codes for
     * each character.  The text file is then read one character at a time, and
     * each character is encoded by using the lookup table.  The encoded bits for
     * each character are written one at a time to the specified BitWriter.
     *
     * @param fr the FileReader which contains the text file to be encoded
     *           huffman the huffman tree that was used for compression, and
     *           hence should be used for decompression
     *           bw      the BitWriter used to write the compressed bits to file*/


    public static void compress(FileReader fr, TreeNode huffman, BitWriter bw) throws Exception {

        // IMPLEMENT THIS METHOD
        int c;
        while ((c=fr.read()) != -1) {
            String code = (String) codes.get(new Character((char)c));
            for(int i=0; i<code.length();i++) {
                bw.writeBit((code.charAt(i)=='0')?0:1);
            }

        }

    }

/**
     * This method reads a frequency file (such as those generated by the
     * countFrequencies() method) and initialises an ArrayList of TreeNodes
     * where the item of each TreeNode is a CharFreq object storing a character
     * from the frequency file and its corresponding frequency.  This method provides
     * the same functionality as the countFrequencies() method, but takes in a
     * frequency file as parameter rather than a text file.
     *
     * @param inputFreqFile the frequency file which stores characters and their
     *                      frequency (one character per line)
     * @return the ArrayList containing TreeNodes.  The item stored in each
     * TreeNode is a CharFreq object.*/


    public static ArrayList readFrequencies(String inputFreqFile) throws Exception {

        // IMPLEMENT THIS METHOD
        ArrayList al = new ArrayList();
        BufferedReader br = new BufferedReader(new FileReader(inputFreqFile));
        String s = null;
        char c2;
        while ((c2=(char)br.read()) != -1) {
            s = br.readLine();
            if (s==null) { break; }
            int i = Integer.parseInt(s.substring(1));
            al.add(new TreeNode(new CharFreq(c2,i)));
        }
        br.close();
        br = null;
        return al;
    }

/*
 This TextZip application should support the following command line flags:

	QUESTION 2 PART 1
	=================
		 -a : this uses a default prefix code tree and its compressed
		      file, "a.txz", and decompresses the file, storing the output
		      in the text file, "a.txt".  It should also print out the size
		      of the compressed file (in bytes), the size of the decompressed
		      file (in bytes) and the compression ratio

	QUESTION 2 PART 2
	=================
		 -f : given a text file (args[1]) and the name of an output frequency file
		      (args[2]) this should count the character frequencies in the text file
		      and store these in the frequency file (with one character and its
		      frequency per line).  It should then build the huffman tree based on
		      the character frequencies, and then print out the prefix code for each
		      character

	QUESTION 2 PART 3
	=================
		 -c : given a text file (args[1]) and the name of an output frequency file
		      (args[2]) and the name of the output compressed file (args[3]), this
		      should compress file

	QUESTION 2 PART 4
	=================
		 -d : given a compressed file (args[1]) and its corresponding frequency file
		      (args[2]) and the name of the output decompressed text file (args[3]),
		      this should decompress the file
*/




    public static void main(String[] args) throws Exception {

        if (args[0].equals("-a")) {
            BitReader br = new BitReader("C:\\Users\\Dave\\Downloads\\第二次作业\\huffman\\a.txz");
            FileWriter fw = new FileWriter("C:\\Users\\Dave\\Downloads\\第二次作业\\huffman\\a.txt");

            // Get the default prefix code tree
            TreeNode tn = abracadbraTree();

            // Decompress the default file "a.txz"
            decompress(br, tn, fw);

            // Close the ouput file
            fw.close();
            // Output the compression ratio
            // Write your own implementation here.
            File f1 = new File("C:\\Users\\Dave\\Downloads\\第二次作业\\huffman\\a.txz");
            File f2 = new File("C:\\Users\\Dave\\Downloads\\第二次作业\\huffman\\a.txt");

            System.out.println("b.txt decompressed by " + ID);
            System.out.println("Size of the compressed file: " + f1.length()
                    + " bytes");
            System.out.println("Size of the original file: " + f2.length()
                    + " bytes");
            System.out.println("Compressed ratio: " + (double) f1.length()
                    / f2.length() * 100 + "%");

        } else if (args[0].equals("-f")) {
            FileReader fr = new FileReader(args[1]);
            PrintWriter pw = new PrintWriter(new FileWriter(args[2]));

            // Calculate the frequencies
            ArrayList trees = countFrequencies(fr, pw);

            // Close the files
            fr.close();
            pw.close();

            // Build the huffman tree
            TreeNode n = buildTree(trees);

            // Display the codes
            traverse(n, "");
        } else if (args[0].equals("-c")) {

            FileReader fr = new FileReader(args[1]);
            PrintWriter pw = new PrintWriter(new FileWriter(args[2]));
            ArrayList trees = countFrequencies(fr, pw);

            fr.close();
            pw.close();


            TreeNode n = buildTree(trees);

            // IMPLEMENT NEXT
            // Finish the compress function here


            // then output the compression ratio
            // Write your own implementation here.
            traverse(n, "", false);

            fr = new FileReader(args[1]);
            BitWriter bw = new BitWriter(args[3]);
            compress(fr, n, bw);
            bw.close();
            fr.close();

            // Output the compression ratio
            // Write your own implementation here.
            long l1 = new File(args[3]).length();
            long l2 = new File(args[1]).length();
            System.out.println(args[1] + " compressed by " + ID);
            System.out.println("Size of the compressed file: " + l1 + " bytes" );
            System.out.println("Size of the original file: " + l2 + " bytes" );
            System.out.println("Compressed ratio: " + (double) l1 / l2 * 100 + "%");

        } else if (args[0].equals("-d")) {
            ArrayList a = readFrequencies(args[2]);
            TreeNode tn = buildTree(a);
            BitReader br = new BitReader(args[1]);
            FileWriter fw = new FileWriter(args[3]);
            decompress(br, tn, fw);
            fw.close();

            // Output the compression ratio
            // Write your own implementation here.
            long l1 = new File(args[1]).length();
            long l2 = new File(args[3]).length();
            System.out.println(args[1] + " decompressed by " + ID);
            System.out.println("Size of the compressed file: " + l1 + " bytes" );
            System.out.println("Size of the original file: " + l2 + " bytes" );
            System.out.println("Compressed ratio: " + (double) l1 / l2 * 100 + "%");


        }
    }
}
