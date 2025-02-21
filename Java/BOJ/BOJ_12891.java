package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 12891. DNA 비밀번호

// 조합, 순열 문제 아니었음.
// 애초에 다시 생각해보니 문자열 잘라서 그 안의 갯수만 minimum 넘으면 되는거였음.
// 슬라이딩 윈도우, 투 포인터 개념
public class BOJ_12891 {
    static int S, P, count[], minimum[], totalCount; // count : [ 'A의 갯수', 'C의 갯수', 'G의 갯수', 'T의 갯수 ]
    static String input;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] in = br.readLine().split(" ");
        S = Integer.parseInt(in[0]);
        P = Integer.parseInt(in[1]);
        count = new int[4];
        minimum = new int[4];
        totalCount = 0;
        input = br.readLine();
        StringTokenizer st = new StringTokenizer(br.readLine()); // A, C, G, T의 최소 갯수
        for(int i=0; i<4; i++) {
            minimum[i] = Integer.parseInt(st.nextToken());
        }
        boolean impossible = false;
        for(int i=0; i<P; i++) {
            switch(input.charAt(i)) {
                case 'A':
                    count[0]++;
                    break;
                case 'C':
                    count[1]++;
                    break;
                case 'G':
                    count[2]++;
                    break;
                case 'T':
                    count[3]++;
                    break;
            }
        }
        for(int i=0; i<4; i++) {
            if(count[i] < minimum[i]) {
                impossible = true;
                break;
            }
        }
        if(!impossible) {
            totalCount++;
        }
        for(int i=0; i<S-P; i++) {
            impossible = false;
            switch(input.charAt(i)) {
                case 'A':
                    count[0]--;
                    break;
                case 'C':
                    count[1]--;
                    break;
                case 'G':
                    count[2]--;
                    break;
                case 'T':
                    count[3]--;
                    break;
            }
            switch(input.charAt(i+P)) {
                case 'A':
                    count[0]++;
                    break;
                case 'C':
                    count[1]++;
                    break;
                case 'G':
                    count[2]++;
                    break;
                case 'T':
                    count[3]++;
                    break;
            }
            for(int j=0; j<4; j++) {
                if(count[j] < minimum[j]) {
                    impossible = true;
                    break;
                }
            }
            if(!impossible) {
                totalCount++;
            }
        }

        System.out.println(totalCount);
    }
}
