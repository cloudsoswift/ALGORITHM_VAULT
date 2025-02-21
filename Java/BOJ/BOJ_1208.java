package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

// 1208. 부분수열의 합 2

// TODO: TreeMap 써서 그런지 거의 2배 느려서 개선 필요

public class BOJ_1208 {
    static int arr[], N, S;
    static boolean v[];
    static Map<Integer, Long> mapA, mapB;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        N = Integer.parseInt(strArr[0]);
        S = Integer.parseInt(strArr[1]);
        // 주어지는 정수들 저장할 배열 arr과 각 정수를 부분수열에 포함했는지 여부 체크할 배열 v
        arr = new int[N];
        v = new boolean[N];
        // 0 ~ N/2-1 까지 조합해 나온 경우의 수 저장할 mapA
        // N/2 ~ N-1 까지 조합해 나온 경우의 수 저장할 mapB
        mapA = new TreeMap<Integer, Long>();
        mapB = new TreeMap<Integer, Long>();
        // 경우의 수 합 저장할 cnt
        // input이
        // 40 0
        // 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
        // 와 같이 극단적일 경우 경우의 수 곱이 int 범위 벗어나므로 long으로 선언
        long cnt = 0;
        strArr = br.readLine().split(" ");
        for(int i=0; i<N; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        search(0, 0, N/2, mapA);
        search(0, N/2, N, mapB);
        Set<Integer> aKeys = mapA.keySet();
        // (S - 0 ~ N/2-1 범위 내 원소 조합해 나오는 부분순열의 값 중 하나) 만족하는 값이
        // (N/2 ~ N-1 범위 내 원소 조합해 나온 부분순열) 안에 있다면 두 경우의 경우의 수 곱을 cnt에 누계
        for(Integer i : aKeys) {
            Long b = mapB.get(S-i);
            if(b == null) continue;
            cnt += mapA.get(i) * b;
        }
        // 만약 S가 0일 경우, 아무것도 선택 안한(= 크기가 양수가 아닌)
        // 부분수열도 답이 될 수 있으므로 해당 경우의 수 제외
        if(S == 0) cnt--;
        System.out.println(cnt);
    }
    public static void search(int sum, int idx, int maxRange, Map<Integer, Long> map) {
        if(idx == maxRange) {
            Long s = map.get(sum);
            if(s == null) {
                map.put(sum, 1L);
            } else {
                map.put(sum, s+1);
            }
            return;
        }
        v[idx] = true;
        search(sum + arr[idx], idx+1, maxRange, map);
        v[idx] = false;
        search(sum, idx+1, maxRange, map);

    }
}
