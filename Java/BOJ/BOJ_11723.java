package BOJ;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

// 11723. 집합
public class BOJ_11723 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());
        StringBuilder st = new StringBuilder();
        int num = 0;
        for(int tc = 1; tc <= T; tc++) {
            String str[] = br.readLine().split(" ");
            switch(str[0]) {
                case "add":
                    num = num | 1 << Integer.parseInt(str[1]);
                    break;
                case "remove":
                    num = num & ~(1 << Integer.parseInt(str[1]));
                    break;
                case "check":
                    int out = (num & 1 << Integer.parseInt(str[1])) != 0 ? 1 : 0;
                    st.append(out);
                    st.append("\n");
                    break;
                case "toggle":
                    num = num ^ 1 << Integer.parseInt(str[1]);
                    break;
                case "all":
                    for(int i=1; i<=20; i++) {
                        num = num | 1 << i;
                    }
                    break;
                case "empty":
                    num = 0;
                    break;
            }
        }
        bw.append(st.toString());
        bw.flush();
        bw.close();
    }
}
