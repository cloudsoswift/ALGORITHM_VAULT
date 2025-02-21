let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2531.txt"
).toString().trim().split('\n');
const [N, d, k, c] = input[0].split(" ").map(Number);
const sushies = new Array(N).fill(0);
const isIncluded = new Array(d + 1).fill(0);
let count = 0;
for (let i = 0; i < N; i++) { 
  sushies[i] = +input[i + 1];
  if (i < k) {
    if (isIncluded[sushies[i]] === 0) {
      count++;
    }
    isIncluded[sushies[i]]++;
  }
}
if (isIncluded[c] === 0) count++;
isIncluded[c]++;
let MAX_COUNT = count;
let front = 0, rear = k;
while (front < N) { 
  if (--isIncluded[sushies[front]] === 0) count--;
  front++;
  if (isIncluded[sushies[rear]] === 0) count++;
  isIncluded[sushies[rear]]++;
  rear = (rear + 1) % N;
  if (MAX_COUNT < count) MAX_COUNT = count;
}
console.log(MAX_COUNT);