package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1541. 잃어버린 괄호
public class BOJ_1541 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        String[] arr = str.split("-");
        int result = 0;
        boolean isFirst = true;
        for(String s : arr) {
            int num = 0;
            if(s.contains("+")) {
                for(String posNum : s.split("\\+")) {
                    num += Integer.parseInt(posNum);
                }
            } else {
                num = Integer.parseInt(s);
            }
            if(isFirst) {
                result = num;
                isFirst = false;
            } else {
                result -= num;
            }
        }
        System.out.println(result);
    }
}
