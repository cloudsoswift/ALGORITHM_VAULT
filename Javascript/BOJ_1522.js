// 답 보고 푼 문제
// a가 연속되게 만듦 = 길이가 문자열 내 a 갯수만큼인 슬라이딩 윈도우를 만든 뒤
// 해당 범위 내 b의 갯수가 최솟값이 되는 경우를 찾으면 된다.
const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1522.txt"
).toString().trim().split('\n')[0].split("");
let whole_a_count = 0;
let b_count = 0;
let MINIMUM = 0;
for (let i = 0; i < input.length; i++) { 
  if (input[i] === "a") whole_a_count++;
}
for (let i = 0; i < whole_a_count; i++) { 
  if (input[i] === "b") b_count++;
}

MINIMUM = b_count;
let front = 0, rear = whole_a_count-1;
for (let i = 0; i < input.length; i++) { 
  if (input[front] === "b") b_count--;
  front++;
  rear = (rear + 1) % input.length;
  if (input[rear] === "b") b_count++;
  if (b_count < MINIMUM) MINIMUM = b_count;
}
console.log(MINIMUM);