package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;

// 11286. 절댓값 힙
public class BOJ_11286 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(Math.abs(o1) > Math.abs(o2)) {
                    return 1;
                } else if(Math.abs(o1) == Math.abs(o2)) {
                    if(o1 > o2) return 1;
                    else if(o1 < o2) return -1;
                    else return 0;
                }
                return -1;
            }
        });
        for(int i=0; i<N; i++) {
            int in = Integer.parseInt(br.readLine());
            if(in!=0) {
                queue.add(in);
            } else {
                if(!queue.isEmpty())
                    System.out.println(queue.poll());
                else
                    System.out.println(0);
            }
        }
    }
}
