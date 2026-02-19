package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 17825. 주사위 윷놀이
public class BOJ_17825 {
    static int[] points;
    static int maxScore;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] str = br.readLine().split(" ");
        int[] dies = Arrays.stream(str).mapToInt(Integer::parseInt).toArray();
        // 윷놀이 판에 해당하는 배열
        points = new int[] {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30,
            32, 34, 36, 38, 40, 13, 16, 19, 25, 30, 35, 22, 24, 28, 27, 26};
        // 윷놀이 판의 각 지점에 대해 해당 위치에 말이 있는지 기록하는 배열
        // 4개의 말들은 각각의 위치 값을 따로 관리할 필요가 없는,
        // 즉, "1번말이 2번 칸 + 2번말이 1번칸" 인 경우와 "2번말이 2번 칸 + 1번말이 1번칸" 인 경우가 구분되지 않으므로
        // boolean 1차원 배열을 사용해 윷놀이 판의 각 위치에 말이 있는지 없는지만 관리
        boolean[] visited = new boolean[31];
        // 모든 말이 시작 점에 있을 때, 임의의 하나의 말을 이동시킴
        visited[dies[0] - 1] = true;
        comb(1, dies, points[dies[0] - 1], visited, 1);
        System.out.println(maxScore);
    }
    public static void comb(int step, int[] dies, int scoreSum, boolean[] visited, int count)
    {
        if(step == 10)
        {
            maxScore = Math.max(maxScore, scoreSum);
            return;
        }

        int nP = 0;
        for(int i=0; i<31; i++)
        {
            // 0 ~ 30번 위치 까지 각 위치에 말이 있는 경우 해당 말을 진행 시키는 것 시도
            // 해당 위치에 말이 있는 경우
            if(visited[i]) {
                // 해당 말을 이동시켰을 때 다음 위치 nP
                nP = nextPos(i, i + dies[step]);
                if(nP == -1) {
                    // nP가 -1, 즉 도착 지점에 다다른는 경우
                    // 현재 말을 지도에서 지우고 다음 step으로 진행
                    visited[i] = false;
                    comb(step + 1, dies, scoreSum, visited, count);
                    visited[i] = true;
                    continue;
                }
                // 다음 위치에 이미 말이 있는 경우 스킵
                if(visited[nP]) continue;
                if (maxScore < (9 - step) * 40 + scoreSum + points[nP]) {
                    // 남은 횟수만큼 40점 칸을 밟았을때 이미 기록된 maxScore보다 큰 경우만 시도 (미미한 가지치기)
                    visited[nP] = true;
                    visited[i] = false;
                    comb(step + 1, dies, scoreSum + points[nP], visited, count);
                    visited[i] = true;
                    visited[nP] = false;
                }
            }
        }
        // 이외에, 나와있는 말이 아직 4개가 아니라면, 시작점에서 새로운 말 하나 출발시킴
        if(count < 4)
        {
           nP = nextPos(0, dies[step]) - 1;
           if (!visited[nP])
           {
               if (maxScore < (9 - step) * 40 + scoreSum + points[nP]) {
                   visited[nP] = true;
                   comb(step + 1, dies, scoreSum + points[nP], visited, count + 1);
                   visited[nP] = false;
               }
           }
        }
    }
    // 0 ~ 19 / 20 ~ 22(13 ~ 19) / 23 ~ 25 (25 ~ 35)
    // 26 ~ 27 (22 ~ 24) / 28 ~ 30 (28 ~ 26)
    // 10 <- [4] / 20 <- [9] / 30 <- [14] / 40 <- [20]

    // 10 -> 13 / 20 -> 22 / 30 -> 28
    // [4] -> [20] / [9] -> [26] /  [14] -> [28]
    public static int nextPos(int before, int after)
    {
        // 말의 다음 위치 after를 실제 게임판에 맞게 절삭 및 이동처리 해주는 함수
        int gap = 0;
        // 10점 / 20점 / 30점 말에 도착해있는 상태로 이동하는 경우
        // 파란색 화살표를 타야하므로 그에 맞게 before, after 전처리
        if (before == 4) {
            gap = after - before - 1;
            before = 20;
            after = before + gap;
        }
        else if(before == 9) {
            gap = after - before - 1;
            before = 26;
            after = before + gap;
        }
        else if(before == 14) {
            gap = after - before - 1;
            before = 28;
            after = before + gap;
        }

        if(before <= 19) {
            // 40점 칸을 넘어가는, 즉 도착 칸에 도착하는 경우 -1 반환
            if(after > 19)
                return -1;
        } else if(before <= 22) {
            // 10점에서 파란색 화살표를 탄 상태로
            // 다음 위치에 25점 칸을 지나가는 형태인 경우
            // 즉, 19점 칸 다음을 가야하는 경우 25점 칸으로 워프해 남은만큼 이동
            if(after > 22)
                return nextPos(23, after);
        } else if(before <= 25) {
            // 25점 칸에서부터 이동하는 케이스
            if(after >= 26)
            {
                if(after == 26)
                    return 19;
                else
                    return -1;
            }
        } else if(before <= 27) {
            // 20점에서 파란색 화살표를 탄 상태로
            // 다음 위치에 25점 칸을 지나가는 형태인 경우
            // 즉, 24점 칸 다음을 가야하는 경우 25점 칸으로 워프해 남은만큼 이동
            if(after > 27)
                return nextPos(23, 23 + after - 28 );
        } else {
            // 30점에서 파란색 화살표를 탄 상태로
            // 다음 위치에 25점 칸을 지나가는 형태인 경우
            // 즉, 26점 칸 다믕을 가야하는 경우 25점 칸으로 워프해 남은만큼 이동
            if(after > 30)
                return nextPos(23, 23 + after - 31);
        }
        return after;
    }
}
