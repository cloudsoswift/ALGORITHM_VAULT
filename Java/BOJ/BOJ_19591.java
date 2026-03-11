package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

// 19591. 독특한 계산기
public class BOJ_19591 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long answer = 0L;
        ArrayDeque<Character> operators = new ArrayDeque<>();
        ArrayDeque<Long> operands = new ArrayDeque<>();
        StringTokenizer st = new StringTokenizer(br.readLine(), "+-*/", true);
        long sign = 1L;
        while(st.hasMoreTokens()) {
            String s = st.nextToken();
            switch(s) {
                case "-":
                    if(operands.size() == 0) {
                        sign = -1L;
                        continue;
                    }
                    operators.offer(s.charAt(0));
                    break;
                case "+":
                case "*":
                case "/":
                    operators.offer(s.charAt(0));
                    break;
                default:
                    long operand = Long.parseLong(s);
                    if(sign < 0) {
                        operand *= sign;
                        sign = 1L;
                    }
                    operands.offer(operand);
                    break;
            }
        }
        while(!operators.isEmpty()) {
            System.out.println(operands.toString());
            System.out.println(operators.toString());
            if(operators.size() == 1) {
                char op = operators.poll();
                long value = calcOperation(op, operands.poll(), operands.poll());
                operands.offer(value);
            } else {
                char left = operators.peekFirst();
                char right = operators.peekLast();
                int priority = calcPriority(left, right);
                if(priority == 1) {
                    operators.pollFirst();
                    long value = calcOperation(left, operands.pollFirst(), operands.pollFirst());
                    operands.offerFirst(value);
                } else if(priority == -1) {
                    operators.pollLast();
                    long rv = operands.pollLast(), lv = operands.pollLast();
                    long value = calcOperation(right, lv, rv);
                    operands.offerLast(value);
                } else {
                    long leftOperand = operands.pollFirst();
                    long rightOperand = operands.pollLast();
                    long leftValue = calcOperation(left, leftOperand, operands.peekFirst());
                    long rightValue = calcOperation(right, operands.peekLast(), rightOperand);
                    if(leftValue >= rightValue) {
                        operators.pollFirst();
                        operands.pollFirst();
                        operands.offerFirst(leftValue);
                        operands.offerLast(rightOperand);
                    } else {
                        operators.pollLast();
                        operands.pollLast();
                        operands.offerLast(rightValue);
                        operands.offerFirst(leftOperand);
                    }
                }
            }
        }
        System.out.println(operands.poll());
    }
    public static int calcPriority(char left, char right) {
        // 앞 연산자 left와 뒷 연산자 right의 연산자 우선순위를 반환하는 함수
        // 앞 연산자가 더 우선이라면 1을, 뒷 연산자가 더 우선이라면 -1을, 동일하다면 0을 반환
        if(left == '*' || left == '/') {
            if(right == '*' || right == '/') return 0;
            return 1;
        } else {
            // left가 + 또는 -인 경우
            if(right == '+' || right == '-') return 0;
            return -1;
        }
    }
    public static long calcOperation(char operation, long operand1, long operand2) {
        switch(operation) {
            case '-':
                return operand1 - operand2;
            case '+':
                return operand1 + operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return (long) Math.floor(operand1 / operand2);
        }
        return 0;
    }
}
