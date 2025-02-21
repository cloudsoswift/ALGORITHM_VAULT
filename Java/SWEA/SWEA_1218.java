package SWEA;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

// 1218. [S/W 문제해결 기본] 4일차 - 괄호 짝짓기
public class SWEA_1218 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        System.setIn(new FileInputStream("input_1218.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Stack<Character> stack = new Stack<>();
        int T = 10, N;
        String str;
        char tmp = ' ';
        boolean isValid;
        for(int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine());
            str = br.readLine();
            isValid = true;
            for(int i=0; i<N; i++) {
                tmp = str.charAt(i);
                switch(tmp) {
                    case '(':
                    case '[':
                    case '{':
                    case '<':
                        stack.push(tmp);
                        break;
                    case ')':
                        if(!stack.isEmpty() && stack.peek() == '(') {
                            stack.pop();
                        } else {
                            isValid = false;
                        }
                        break;
                    case ']':
                        if(!stack.isEmpty() && stack.peek() == '[') {
                            stack.pop();
                        } else {
                            isValid = false;
                        }
                        break;
                    case '}':
                        if(!stack.isEmpty() && stack.peek() == '{') {
                            stack.pop();
                        } else {
                            isValid = false;
                        }
                        break;
                    case '>':
                        if(!stack.isEmpty() && stack.peek() == '<') {
                            stack.pop();
                        } else {
                            isValid = false;
                        }
                        break;

                }
                if(!isValid) {
                    System.out.printf("#%d %d\n", tc, 0);
                    break;
                }
            }
            if(isValid) {
                System.out.printf("#%d %d\n", tc, 1);
            }
        }
    }
}
