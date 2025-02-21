package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 14889. 스타트와 링크
public class BOJ_14889 {
    static int N, arr[][], MIN_DIFFER;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        arr = new int[N][N];
        MIN_DIFFER = Integer.MAX_VALUE;
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        comb(0, 0, 0);
        System.out.println(MIN_DIFFER);
    }
    public static void comb(int start, int cnt, int flag) {
        if(cnt==N/2) {
            int startStat = 0, linkStat = 0;
            for(int i=0; i<N; i++) {
                if((flag&1<<i) != 0) {
                    // 선택된 인원들 스타트 팀으로
                    for(int j=i; j<N; j++) {
                        if((flag&1<<j) != 0) {
                            startStat += arr[i][j];
                            startStat += arr[j][i];
                        }
                    }
                } else {
                    // 선택되지 않은 인원들 링크 팀으로
                    for(int j=i; j<N; j++) {
                        if((flag&1<<j) == 0) {
                            linkStat += arr[i][j];
                            linkStat += arr[j][i];
                        }
                    }
                }
            }
            // 최소 차이값 갱신
            if(MIN_DIFFER > Math.abs(startStat - linkStat)) {
                MIN_DIFFER = Math.abs(startStat - linkStat);
            }
            return;
        }
        for(int i=start; i<N; i++) {
            comb(i+1, cnt+1, (flag|1<<i));
        }
    }
}
