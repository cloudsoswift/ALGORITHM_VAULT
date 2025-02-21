package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 1238. [S/W 문제해결 기본] 10일차 - Contact
public class SWEA_1238 {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("input_1238.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N, start;
        for(int test_case = 1; test_case <= 10; test_case++) {
            String str[] = br.readLine().split(" ");
            N = Integer.parseInt(str[0]);
            start = Integer.parseInt(str[1]);
            str = br.readLine().split(" ");
            int v[] = new int[101];
            List<Integer> list[] = new ArrayList[101];
            for(int i=0; i<N; i+=2) {
                int from = Integer.parseInt(str[i]);
                int to = Integer.parseInt(str[i+1]);
                if(list[from] == null) {
                    list[from] = new ArrayList<Integer>();
                }
                if(!list[from].contains(to)) {
                    list[from].add(to);
                }
            }
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            v[start] = 1;
            int lastCalled = -1;
            int before = 0;
            while(!q.isEmpty()) {
                int queueSize = q.size();
                boolean isLast = true;
                while(queueSize-->0) {
                    int now = q.poll();
                    if(list[now] != null) {
                        for(int i=0, size=list[now].size(); i<size; i++) {
                            int to = list[now].get(i);
                            if(v[to] == 1) continue;
                            q.offer(to);
                            v[to] = 1;
                            if(to > lastCalled) lastCalled = to;
                            isLast = false;
                        }
                    }
                }
                if(!isLast) {
                    before = lastCalled;
                    lastCalled = -1;
                }
            }
            System.out.printf("#%d %d\n", test_case, before);
        }
    }
}
