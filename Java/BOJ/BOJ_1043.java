package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 1043. 거짓말

// union-find를 이용해 푼 문제
public class BOJ_1043 {
    static int u[], party[][];
    static boolean truth[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String strArr[] = br.readLine().split(" ");
        // 사람의 수 N, 파티의 수 M
        int N = Integer.parseInt(strArr[0]);
        int M = Integer.parseInt(strArr[1]);
        strArr = br.readLine().split(" ");
        // 진실을 아는 사람의 수 T
        int T = Integer.parseInt(strArr[0]);
        // T == 0 => 진실을 아는 사람이 없다. = 모든 파티에 거짓말을 해도 된다.
        if(T == 0) {
            System.out.println(M);
            return;
        } else {
            // 같은 파티였던 친구(진실을 아는 친구 우선해서) 기록하는 배열 u
            u = new int[N+1];
            // 진실을 아는지 여부 기록하는 배열 truth 초기화
            truth = new boolean[N+1];
            // 각 파티 별 참가 인원 기록하는 배열 party 초기화
            party = new int[M][];
            // 파티 탐색 전엔 아는 친구 없어 스스로를  친구로 초기화
            for(int i=1; i<=N; i++) {
                u[i] = i;
            }
            // 진실을 아는 사람 기록
            for(int i=1; i<=T; i++) {
                int num = Integer.parseInt(strArr[i]);
                truth[num] = true;
            }
            // 파티 별 참여한 사람들 기록
            for(int i=0; i<M; i++) {
                strArr = br.readLine().split(" ");
                // 주어진 파티 인원으로 배열 초기화
                party[i] = new int[Integer.parseInt(strArr[0])];
                for(int j=1; j<=party[i].length; j++) {
                    party[i][j-1] = Integer.parseInt(strArr[j]);
                    if(j>1) {
                        // 파티 인원이 2명 이상이면 서로 친구 관계 맺음
                        union(party[i][j-2], party[i][j-1]);
                    }
                }
            }
            // 거짓을 말할 수 있는 파티 수 저장하는 변수 result
            int result = 0;
            loop:
            for(int i=0; i<M; i++) {
                for(int j=0; j<party[i].length; j++) {
                    // 파티 인원 중 한명이 진실을 알거나 진실을 아는 사람과 파티였다면
                    // 해당 파티는 진실만 말할 수 있으므로 스킵
                    if(truth[find(party[i][j])]) continue loop;
                }
                // 진실을 아는 사람이 없는 파티면 result 증가
                result++;
            }
            System.out.println(result);
        }
    }
    public static int find(int num) {
        if(u[num] == num) return num;
        else return u[num] = find(u[num]);
    }
    public static void union(int left, int right) {
        int lParent = find(left);
        int rParent = find(right);
        if(truth[lParent]) {
            // 왼쪽 부모가 진실을 안다면 오른쪽 부모를 진실 아는쪽으로 연결
            u[rParent] = lParent;
        } else if(truth[rParent]) {
            // 오른쪽 부모가 진실 안다면 왼쪽 부모를 진실 아는쪽으로 연결
            u[lParent] = rParent;
        } else {
            // 둘 다 진실 모르는 경우, 번호 낮은 쪽으로 연결
            if(lParent < rParent) {
                u[rParent] = lParent;
            } else {
                u[lParent] = rParent;
            }
        }
    }
}
