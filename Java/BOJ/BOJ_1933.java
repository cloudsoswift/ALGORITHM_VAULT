package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// 1933. 스카이라인
public class BOJ_1933 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        // event[i]: 빌딩 하나의 시작(L) 또는 끝(R)의 등장 기록을 담는 배열
        // event[i][0]: i번째 빌딩의 시작 또는 끝 위치(X)
        // event[i][1]: i번째 빌딩의 높이
        // event[i][2]: 현재 기록이 시작인지(>0), 끝인지(0) 나타내는 값
        int[][] events = new int[N * 2][3];
        List<int[]> list = new ArrayList<>();
        String[] strArr;
        for(int i=0; i<N; i++) {
            strArr = br.readLine().split(" ");
            int L = Integer.parseInt(strArr[0]);
            int H = Integer.parseInt(strArr[1]);
            int R = Integer.parseInt(strArr[2]);
            events[i * 2] = new int []{L, H, R};
            events[i * 2 + 1] = new int[]{R, H, 0};
        }
        // L 순으로 정렬
        // 원래는 만약 둘의 L이 같다면 퇴장 이벤트(R == 0)이 먼저 오도록 정렬 했으나,
        // 뒤의 event 처리 로직에서 while문을 돌며 L이 같은 이벤트 중 등장 이벤트만 처리하기 때문에,
        // 퇴장, 등장 이벤트가 섞여있어도 처리 결과에 영향을 주지 않아 굳이 R 관련 비교는 하지 않음.
        Arrays.sort(events, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] < o2[0] ? -1 :
                        (o1[0] == o2[0] ? 0: 1);
            }
        });
        // [H, R]로 이뤄진 PQ로, H가 높은것이 먼저 오도록 정렬
        // 원래는 만약 H가 같은 경우, R이 먼저인 순으로 정렬했으나, 어차피 추후 PQ에서 R <= X인 것들을 거를때
        // '지난 것은 비워지고, 지나지 않은 것은 PQ에서 살아 남는다'는 사실이 그 처리 순서에 따라 달라지지 않기 때문에
        // 굳이 R 관련 비교는 하지 않음.
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] > o2[0] ? -1 :
                        (o1[0] == o2[0] ? 0 : 1);
            }
        });
        // 직전의 높이를 기록하는 변수
        int lastH = 0;
        for(int i=0,len=events.length; i<len;) {
            int X = events[i][0];
            while(i < len && events[i][0] == X) {
                // 현재 이벤트의 X와 같은 X의 모든 이벤트를 탐색
                if(events[i][2] > 0) {
                    // 시작 이벤트인 경우, PQ에 넣음
                    // 퇴장 이벤트는 별도로 기록할 필요 없음.
                    pq.offer(new int[] {events[i][1], events[i][2]});
                }
                i++;
            }

            while(!pq.isEmpty() && pq.peek()[1] <= X) {
                // PQ 중, 현재 X 이하인 R을 갖는, 즉 이미 지난 건물들 비움
                pq.poll();
            }
            // 위 과정을 거치고 나면, PQ에는 (비어있지 않는 한) 현재 X에서 가장 높은 건물의 기록이 들어있음
            int highestH = pq.isEmpty() ? 0 : pq.peek()[0];
            if(highestH != lastH) {
                // 현재 위치의 가장 높은 H가 직전에 기록한 H와 다르다면,
                // 높이 변화가 발생했음을 의미.
                // 따라서 list에 기록한 뒤, 직전 기록 H를 갱신해줌.
                list.add(new int[] {X, highestH});
                lastH = highestH;
            }
//            아래 코드는 기존에 작성했던 로직으로,
//            동일한 X 좌표에 대해 빌딩의 등장/퇴장 이벤트가 엄청나게 많을 경우를 고려한
//            list 관리 처리가 복잡해 Gemini의 도움을 받아 위와 같이 재설계 함
//            int X = events[i][0];
//            int H = events[i][1];
//            int R = events[i][2];
//            if(R > 0) {
//                // 빌딩의 등장
//                if(pq.isEmpty()) {
//                    list.add(new int[]{X, H});
//                    pq.offer(new int[]{H, R});
//                } else {
//                    while(!pq.isEmpty() && X >= pq.peek()[1])
//                        pq.poll();
//                    if(pq.isEmpty()) {
//                        // 현재 위치에 건물 없는 경우
//                        if(!list.isEmpty() && list.get(list.size() - 1)[0] == X) {
//                            list.get(list.size() - 1)[1] = Math.max(list.get(list.size() - 1)[1], H);
//                        } else {
//                            list.add(new int[]{X, H});
//                        }
//                    } else if(H > pq.peek()[0]) {
//                        if(!list.isEmpty() && list.get(list.size() - 1)[0] == X) {
//                            list.get(list.size() - 1)[1] = Math.max(list.get(list.size() - 1)[1], H);
//                        } else {
//                            list.add(new int[]{X, H});
//                        }
//                    }
//                    pq.offer(new int[]{H, R});
//                }
//            } else {
//                // 빌딩의 퇴장
//                int beforePeek = pq.peek()[0];
//                while(!pq.isEmpty() && X >= pq.peek()[1])
//                    pq.poll();
//                if(pq.isEmpty()) {
//                    // 현재
//                    list.add(new int[]{X, 0});
//                } else if(pq.peek()[0] != beforePeek) {
//                    list.add(new int[]{X, pq.peek()[0]});
//                }
//            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0, len=list.size(); i<len; i++) {
            sb.append(list.get(i)[0]);
            sb.append(" ");
            sb.append(list.get(i)[1]);
            if(i < len - 1)
                sb.append(" ");
        }
        System.out.println(sb.toString());
    }
}
