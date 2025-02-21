package BOJ;

import java.util.Scanner;

// 17478. 재귀함수가 뭔가요?
public class BOJ_17478 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        System.out.println("어느 한 컴퓨터공학과 학생이 유명한 교수님을 찾아가 물었다.");
        JH(N, N);
    }
    public static void JH(int max, int cnt) {
        printbar(max, cnt);
        System.out.println("\"재귀함수가 뭔가요?\"");
        if(cnt==0) {
            printbar(max, cnt);
            System.out.printf("\"재귀함수는 자기 자신을 호출하는 함수라네\"\n");
            printbar(max, cnt);
            System.out.printf("라고 답변하였지.\n");
            return;
        }
        printbar(max, cnt);
        System.out.printf("\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.\n");
        printbar(max, cnt);
        System.out.printf("마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.\n");
        printbar(max, cnt);
        System.out.printf("그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"\n");
        JH(max, cnt-1);
        printbar(max, cnt);
        System.out.println("라고 답변하였지.");
    }
    public static void printbar(int max, int cnt) {
        for(int i=cnt; i<max; i++) {
            System.out.printf("____");
        }
    }
}
