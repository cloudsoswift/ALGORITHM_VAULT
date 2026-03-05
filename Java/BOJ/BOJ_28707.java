package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// 28707. 배열 정렬
public class BOJ_28707 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        // 배열을 long으로 바꾼 비트마스크를 key로, 해당 상태에 도달하는데 드는 최소 비용을 value로 갖는 Map
        Map<Long, Long> dict = new HashMap<>();
        String[] strArr = br.readLine().split(" ");
        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        // long 타입의 비트마스크를 사용
        long bitmask = arrayToLong(arr, N);
        dict.put(bitmask, 0L);
        // 배열 원소들을 역내림차순으로 정렬
        // 이 상태가 문제에서 목표로 하는 '비내림차순' 정렬된 상태
        Arrays.sort(arr);
        long goal = arrayToLong(arr, N);
        int M = Integer.parseInt(br.readLine());
        // 배열의 각 원소에 대해, 가능한 조작 연산들을 포함하는,
        // 즉, edges[i] : i가 l_i 또는 r_i인 조작의 [변경될 위치, 비용] 들을 담는 List
        int[][] commands = new int[M][];
        for(int i=0; i<M; i++) {
            strArr = br.readLine().split(" ");
            int l = Integer.parseInt(strArr[0]);
            int r = Integer.parseInt(strArr[1]);
            int c = Integer.parseInt(strArr[2]);
            commands[i] = new int[] {l, r, c};
        }
        PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> {
            return o1[1] < o2[1] ? -1 : (
                    o1[1] == o2[1] ? 0 : 1
                    );
        });
        pq.offer(new long[] {bitmask, 0});
        while(!pq.isEmpty()) {
            long[] now = pq.poll();
            for(int i=0; i<M; i++) {
                int[] command = commands[i];
                long swapped = swapLong(now[0], command[0], command[1]);
                long newCost = now[1] + command[2];
                if(dict.getOrDefault(swapped, 100000000L) > newCost) {
                    dict.put(swapped, newCost);
                    pq.offer(new long[] {swapped, newCost});
                }
            }
        }
        if(dict.get(goal) == null) {
            System.out.println(-1);
        } else {
            System.out.println(dict.get(goal));
        }
    }
    public static long arrayToLong(int[] arr, int N) {
        // Java의 long은 8바이트이고, 문제에서 배열 A의 길이는 최대 8이므로
        // long의 각 byte를 배열의 각 요소들로 치환해 일종의 길이 8짜리 배열로 사용
        // bitmask에는 초기 배열 A의 각 원소들을 미리 기록
        long bitmask = 0;
        for(int i=0; i<N; i++) {
            // 32 / 8 = 4
            // 따라서 배열의 각 원소들을 i * 4 만큼 쉬프트 한 위치에 기록
            bitmask |= ((long) arr[i] << (i * 4));
        }
        return bitmask;
    }
    public static long swapLong(long bitmask, int i, int j) {
        // 참고) i와 j 모두 1-based 이므로 -1하여 사용
        // 인덱스 i, j에 있는 값을 swap하는 함수
        // 1. 먼저 i, j에 위치한 값을 추출함
        // 추출할 때, 먼저 4 * index 만큼 오른쪽으로 시프트한 뒤, 마스크를 씌움
        // 이때, 각 4비트를 각 배열 칸으로 쓰기로 했으므로, 1111, 즉 0xF를 씌움

        // 인덱스 i에 위치한 값 l
        int l = (int) ((bitmask >> ((i-1) * 4)) & 0xF);
        // 인덱스 j에 위치한 값 r
        int r = (int) ((bitmask >> ((j-1) * 4)) & 0xF);
        // 2. 이후, 각 위치의 값을 0으로 만듦
        bitmask &= ~(0xF << (i-1) * 4);
        bitmask &= ~(0xF << (j-1) * 4);

        // 3. 다시, 인덱스 i, j의 값들을 반대로 j, i 위치에 기록
        bitmask |= ((long) l << ((j-1) * 4));
        bitmask |= ((long) r << ((i-1) * 4));

        return bitmask;
    }
//    public static void longToString(long bitmask, int N) {
//        StringBuilder sb = new StringBuilder();
//        for(int i=0; i<N; i++) {
//            sb.append((int) ((bitmask >> (i * 4)) & 0xF));
//            if(i < N-1)
//                sb.append(" ");
//        }
//        System.out.println(sb.toString());
//    }
}
