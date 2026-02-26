package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

// 1620. 나는야 포켓몬 마스터 이다솜
public class BOJ_1620 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strArr = br.readLine().split(" ");
        String s;
        Map<String, Integer> dict = new TreeMap<>();
        int N = Integer.parseInt(strArr[0]);
        int M = Integer.parseInt(strArr[1]);
        String[] arr = new String[N + 1];
        for(int i=1; i<=N; i++) {
            s = br.readLine();
            dict.put(s, i);
            arr[i] = s;
        }
        for(int i=0; i<M; i++) {
            s = br.readLine();
            if(isNumeric(s)) {
                int pos = Integer.parseInt(s);
                System.out.println(arr[pos]);
            } else {
                System.out.println(dict.get(s));
            }
        }
    }
    public static boolean isNumeric(String s) {
        if(s == null) return false;
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
