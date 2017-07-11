package Homework_2.Huffman;

// This class represents an object which stores a single character and an integer frequency
//该类表示存储单个字符和整数频率的对象。

public class CharFreq implements Comparable {

    private char c;
    private int freq;

    public CharFreq(char c, int freq) {
        this.c = c;
        this.freq = freq;
    }

    public char getChar() {
        return c;
    }

    public int getFreq() {
        return freq;
    }

    public int compareTo(Object o) {
        return freq - ((CharFreq)o).freq;
    }

    public String toString() {
        return "(" + c + ":" + freq + ")";
    }
}