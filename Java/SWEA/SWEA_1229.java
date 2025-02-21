package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

// 1229. [S/W 문제해결 기본] 8일차 - 암호문2
public class SWEA_1229 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        LinkedList<Integer> list = new LinkedList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, M, x, y;
        StringTokenizer st;
        for(int test_case = 1; test_case <= 10; test_case++) {
            N = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<N; i++) {
                list.add(Integer.parseInt(st.nextToken()));
            }
            M = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            for(int i=0; i<M; i++) {
                String exec = st.nextToken();
                if(exec.equals("I")) {
                    x = Integer.parseInt(st.nextToken());
                    y = Integer.parseInt(st.nextToken());
                    for(int j=0; j<y; j++) {
                        list.add(x+j, Integer.parseInt(st.nextToken()));
                    }
                } else if(exec.equals("D")) {
                    x = Integer.parseInt(st.nextToken());
                    y = Integer.parseInt(st.nextToken());
                    for(int j=0; j<y; j++) {
                        list.remove(x);
                    }
                }
            }
            System.out.printf("#%d ", test_case);
            for(int i=0; i<10; i++) {
                System.out.printf("%d ", list.get(i));
            }
            System.out.println();
            list.clear();
        }
    }
}
