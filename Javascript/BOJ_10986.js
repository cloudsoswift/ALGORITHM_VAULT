// 풀이 보고 푼 문제
// ===========================
// 각 인덱스까지의 누적합을 M으로 나눈 나머지 값을 저장한다.
// 이후, 해당 나머지 값이 같은 누적합 간에 앞의 누적을 뒤의 누적에서 빼게 되면
// 이는 나머지가 0인 부분 집합이 된다.
// ex. [1, 2, 3, 1, 2] 일때,
// 누적합 -> [1, 3, 6, 7, 9]
// M: 3일때 나머지 -> [1, 0, 0, 1, 0]
// 나머지가 똑같이 1인 0~0과 0~3 구간의 경우, 0~3 구간 누적합에서 0~1 구간 누적합을 빼면 7-1=6으로 나머지가 0인 부분집합(1~3)을 구할 수 있게 된다.
// 이를 활용해 나머지 값이 0~M-1이 나오는 경우들을 카운트 한 뒤,
// 이들 중 2개를 선택, 즉 N_C_2를 각각 계산한 뒤 이들을 합하면 그것이 
// 문제에서 요구하는 i, j쌍의 갯수가 된다.

let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_10986.txt"
).toString().trim().split('\n');
const [N, M] = input[0].split(" ").map(Number);
const arr = input[1].split(" ").map(Number);
// A_1~i까지의 구간합을 저장하는 배열. 실질적인 값 저장은 1번 인덱스부터 시작하며, 0번째 인덱스에는 0이 존재
const subsum = new Array(N + 1).fill(0);
// A_1~i까지의 구간합을 M으로 나눈 나머지를 저장하는 배열. subsum과 다르게 0번 인덱스부터 시작한다.
const modArr = new Array(N).fill(0);
// 0부터 M-1까지 각 나머지 값이 몇 번 나왔는지 카운트하는 배열.
const modCounts = new Array(M).fill(0);
subsum[1] = arr[0];
for (let i = 1; i <= N; i++) { 
  // 구간합을 계산하고
  subsum[i] = arr[i - 1] + subsum[i - 1];
  // 이를 M으로 나눈 나머지를 저장한 뒤
  modArr[i - 1] = subsum[i] % M;
  // 해당 나머지 값의 카운트를 증가시킨다.
  modCounts[modArr[i - 1]]++;
}
let modulableCount = modCounts[0];
for (let i = 0; i < M; i++) { 
  modulableCount += combination(modCounts[i]);
}
console.log(modulableCount);

function combination(v) { 
  return v < 2 ? 0 : v * (v - 1) / 2;
}