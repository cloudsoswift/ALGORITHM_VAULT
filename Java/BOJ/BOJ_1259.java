package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1259 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        loop:
        while(!(str=br.readLine()).equals("0")) {
            int i=0, size=str.length()-1;
            while(size-i>i) {
                if(str.charAt(i) != str.charAt(size-i)) {
                    System.out.println("no");
                    continue loop;
                }
                i++;
            }
            System.out.println("yes");
        }
    }
}
