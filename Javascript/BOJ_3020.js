let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_3020.txt"
).toString().trim().split('\n');
const [N, H] = input[0].split(" ").map(Number);
const seoksoon = new Array(Math.round(H+1)).fill(0);
const jongyuseok = new Array(Math.floor(H + 1)).fill(0);
// 각 석순, 종유석의 길이 별 갯수 카운트
for (let i = 0; i < N; i++) { 
  const obstacle = +input[i + 1];
  if (i % 2 === 0) {
    // 짝수번째 (0, 2, 4, ...) -> 석순(아래서 위로 솟은 장애물)
    seoksoon[obstacle]++;
  } else { 
    // 홀수번째 (1, 3, 5, ...) -> 종유석(위에서 아래로 솟은 장애물)
    jongyuseok[obstacle]++;
  }
}

// 높이 i(바닥/천장 기준)에서 부딪힐 수 있는 석순/종유석 갯수 몇개인지 누적합 통해 카운트
for (let i = H-1; i > 0; i--) { 
  seoksoon[i - 1] += seoksoon[i];
  jongyuseok[i - 1] += jongyuseok[i];
}

// 장애물 최솟값
let lowest_number = Number.MAX_SAFE_INTEGER;
// 그러한 구간의 수
let lowest_count = 0;

for (let i = 1; i <= H; i++) { 
  // 바닥으로부터 높이 i일때 부딪히는 석순과
  // 천장으로부터 높이 H - i + 1일때 부딪히는 종유석 갯수의 합
  // = i 구간에서 부딪히는 장애물의 갯수
  const sum = seoksoon[i] + jongyuseok[H - i+1];
  if (sum < lowest_number) {
    lowest_number = sum;
    lowest_count = 1;
  } else if (sum === lowest_number) { 
    lowest_count++;
  }
}
console.log(lowest_number + " " + lowest_count);
