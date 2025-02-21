let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2003.txt"
).toString().trim().split('\n');
const [N, M] = input[0].split(" ").map(Number);
const aArr = input[1].split(" ").map(Number);
// 부분수열의 시작 인덱스 left, 끝 인덱스 right
let left = 0, right = 0;
// 부분수열이 M과 일치한 횟수 count
let count = 0;
// left ~ right 까지 부분수열의 합 sum
let sum = aArr[left];
while (left < N && right < N) { 
  if (sum < M) {
    sum += aArr[++right];
  } else if (sum == M) {
    count++;
    sum += aArr[++right];
  } else { 
    sum -= aArr[left++];
  }
}
console.log(count);