package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// 15686. 치킨 배달
public class BOJ_15686 {
    static class Pair{
        int x;
        int y;
        Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    static List<Pair> homes, chickens;
    static int N, M, arr[][], chickensLength, homesLength, MIN_CHICKEN_DISTANCE;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str[] = br.readLine().split(" ");
        N = Integer.parseInt(str[0]);
        M = Integer.parseInt(str[1]);
        MIN_CHICKEN_DISTANCE = Integer.MAX_VALUE;
        homes = new ArrayList<>();
        chickens = new ArrayList<>();
        arr = new int[N][N];
        for(int i=0; i<N; i++) {
            str = br.readLine().split(" ");
            for(int j=0; j<N; j++) {
                if(str[j].equals("1")) {
                    // 집인 경우
                    homes.add(new Pair(j, i));
                } else if(str[j].equals("2")) {
                    // 치킨집인 경우
                    chickens.add(new Pair(j, i));
                }
            }
        }
        homesLength = homes.size();
        chickensLength = chickens.size();
        comb(0, 0, 0);
        System.out.println(MIN_CHICKEN_DISTANCE);
    }
    public static void comb(int start, int cnt, int flag) {
        if(cnt==M) {
            // 각 집 별로 M개의 치킨집 중 가장 짧은 치킨거리 계산
            int minDistance[] = new int[homesLength];
            for(int i=0; i<chickensLength; i++) {
                if((flag & 1<<i)!=0) {
                    for(int j=0; j<homesLength; j++) {
                        int distance = Math.abs(chickens.get(i).x - homes.get(j).x) +
                                Math.abs(chickens.get(i).y - homes.get(j).y);
                        if(minDistance[j] == 0) {
                            minDistance[j] = distance;
                        } else if(minDistance[j] > distance) {
                            minDistance[j] = distance;
                        }
                    }
                }
            }
            int sum = 0;
            for(int i=0; i<homesLength; i++) {
                sum += minDistance[i];
                if(sum > MIN_CHICKEN_DISTANCE) return;
            }
            MIN_CHICKEN_DISTANCE = sum;
            return;
        }
        for(int i=start; i<chickensLength; i++) {
            comb(i+1, cnt+1, (flag|1<<i));
        }
    }
}
