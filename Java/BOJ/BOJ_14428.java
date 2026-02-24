package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// 14428. 수열과 쿼리 16
public class BOJ_14428 {
    public static void main(String[] args) throws IOException {
        int N, M;
        // 수열의 실제 값 A_i, A_{i+1}, ... 을 저장하는 배열 numb
        int[] numb, segmentTree;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        numb = new int[N + 1];
        String[] strArr = br.readLine().split(" ");
        // numb에는 index 1~N에 A_1, ... 을 저장
        // numb[0]에는 Integer.MAX_VALUE를 넣어두어, 이후 최소값 index 찾을 때 범위 벗어난 경우 index 0(MAX.VALUE) 반환하도록 함.
        numb[0] = Integer.MAX_VALUE;
        for(int i=0; i<N; i++) {
            numb[i+1] = Integer.parseInt(strArr[i]);
        }
        // 세그먼트 트리 초기화
        segmentTree = initSegmentTree(numb, N);
        M = Integer.parseInt(br.readLine());
        for(int i=0; i<M; i++) {
            strArr = br.readLine().split(" ");
            int command = Integer.parseInt(strArr[0]);
            int ii = Integer.parseInt(strArr[1]);
            int jj = Integer.parseInt(strArr[2]);
            switch (command) {
                case 1:
                    // 1 i v <- A_i를 v로 바꾼다.
                    int changeI = find(1, ii, 1, N);
                    numb[ii] = jj;
                    change(segmentTree, numb, changeI / 2);
                    break;
                case 2:
                    // 2 i j <- A_i, A_{i+1}, ..., A_j 에서 가장 작은 값의 인덱스를 출력한다.
                    int foundI = findMinimum(segmentTree, numb, ii, jj, 1, N, 1);
                    System.out.println(foundI);
                    break;
            }
        }
    }
    public static int[] initSegmentTree(int[] numb, int N) {
        // 세그먼트 트리의 높이 = log_2(N)
        int height = (int) Math.ceil(Math.log(N) / Math.log(2));
        int length = (int) Math.pow(2, height + 1);
        int[] result = new int[length + 1];
        initialize(numb, result, 1, N, 1);
        return result;
    }
    public static int initialize(int[] numb, int[] tree, int left, int right, int node_num) {
        if (left == right) {
            return tree[node_num] = left;
        }
        int mid = (left + right) / 2;
        int l = initialize(numb, tree, left, mid, node_num * 2);
        int r = initialize(numb, tree, mid + 1, right, node_num * 2 + 1);
        if (numb[l] <= numb[r])
            tree[node_num] = l;
        else
            tree[node_num] = r;
        return tree[node_num];
    }
    public static int findMinimum(int[] tree, int[] numb, int i, int j, int start, int end, int node_num) {
        // 현재 node_num 번 노드가 관장하는 노드의 범위 [start, end]가 [i, j] 범위 밖인 경우 index 0(MAX.VALUE) 반환
        if(start > j || end < i) return 0;
        // 현재 node_num 번 노드가 관장하는 노드의 범위 [start, end]가 [i, j] 범위 내에 포함된 경우 해당 노드에 기록된 index 바로 반환 
        if(start >= i && end <= j) return tree[node_num];
        int mid = (start + end) / 2;
        // 왼쪽 자식 노드의 index
        int left = findMinimum(tree, numb, i, j, start, mid, node_num * 2);
        // 오른쪽 자식 노드의 index
        int right = findMinimum(tree, numb, i, j, mid + 1, end, node_num * 2 + 1);
        // 두 index가 가리키는 수열의 값 비교해 더 낮은 값 갖는 index 반환
        if (numb[left] <= numb[right])
            return left;
        else
            return right;
    }
    public static int find(int node_num, int i, int start, int end) {
        // 수열의 i번째 요소(A_i)에 대응되는 세그먼트 트리 노드의 index를 찾는 함수
        if(start == end && i == start) return node_num;
        int mid = (start + end) / 2;
        if (i <= mid) {
            return find(node_num * 2, i, start, mid);
        } else {
            return find(node_num * 2 + 1, i, mid + 1, end);
        }
    }
    public static void change(int[] tree, int[] numb, int node_num) {
        // 노드의 루트까지 계속해서 현재 노드(node_num)의 값을 갱신하는 함수
        if(node_num < 1) return;
        // 왼쪽 자식의 수열 값
        int left = numb[tree[node_num * 2]];
        // 오른쪽 자식의 수열 값
        int right = numb[tree[node_num * 2 + 1]];
        // 둘 중 수열 값이 더 낮은 index를 현재 위치에 기록
        if(left <= right)
            tree[node_num] = tree[node_num * 2];
        else
            tree[node_num] = tree[node_num * 2 + 1];
        change(tree, numb, node_num / 2);
    }
}
