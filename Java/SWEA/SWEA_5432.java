package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

// 5432. 쇠막대기 자르기
public class SWEA_5432 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Character> stack = new Stack<>();
        int T = Integer.parseInt(br.readLine());
        for(int test_case = 1 ; test_case <= T; test_case++) {
            char[] chars = br.readLine().toCharArray();
            int length = chars.length;
            int cnt = 0;
            for(int i=0; i<length; i++) {
                if(chars[i] == '(') {
                    stack.push(chars[i]);
                } else if (chars[i] == ')') {
                    if(!stack.isEmpty() && stack.peek() == '(') {
                        stack.pop();
                        if(chars[i-1] == '(')
                            cnt += stack.size();
                        else
                            cnt += 1;
                    }
                }
            }
            System.out.printf("#%d %d\n", test_case, cnt);
        }
    }
}
