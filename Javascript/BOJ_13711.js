// 답 보고 아이디어 도출해서 푼 문제
// ======== 원래 시도했던 아이디어 1 ============
// 1. 두 배열의 LIS를 구한다.
// 2. 두 배열의 LIS를 기준으로 LCS를 구한다.
// ------------ 위 아이디어의 문제점---------------
// 1. LIS를 만들 때, 다음으로 등장한 작은 수에 의해 큰 수가 덮혀버리는 경우,
// ex) 1 3 2 -> LIS = [1, 2] (3이 무시됨)
// 그 수를 필요로 하는 조합을 발견할 수 없음
// + 이 아이디어는 애초에 문제를 잘못 이해한 아이디어임
// 문제에서 요구하는건 LCS이므로, 꼭 그 부분수열이 오름차순으로 증가하는 형태여야 할 필요가 없음
// 또한, 그냥 크기 [N+1][N+1]인 배열을 만들어 LCS를 구하려 하는 경우, 메모리 초과가 발생
// ==============================================
// ========= 원래 시도했던 아이디어 2 ============
// 1. 두 배열에서 각 숫자가 등장하는 인덱스를 기록한다.
// 2. 1~N까지 순서대로 탐색하며, 인덱스 숫자가 증가하는 조합을 구한다.
// ex) 1 3 2 4 -> [0, 2, 1, 3] -> 0 1 3 or 0 2 3
// 3. 두 배열에서 발생하는 조합간을 비교해서 ...
// ---- 위 아이디어의 문제점 ----
// 발생할 수 있는 조합이 많은 경우, 이들을 모두 비교하게되면 시간 초과가 발생할 가능성이 높음
// ================ 답 아이디어 ====================
// 1. 주어진 배열 A, B 중 한 배열(여기서는 임의로 B 배열)에서 1 ~ N 숫자가 등장하는 인덱스를 기록한 배열을 구한다. (아래 코드의 B_idxes)
// 2. A 배열에서 1~N 사이의 수가 등장하는 순서대로, 해당 수가 B에서는 언제 등장하는지를 저장하는 배열 arr를 기록
// 즉, arr는 A 배열의 숫자들이 B 배열에서 언제 등장하는지를, A 배열에 나열된 순서대로 기록한 배열
// 3. arr를 기준으로 LIS(최장 증가 부분 수열)을 만듦
// 즉, A 배열에서 순서대로 나열된 수들이, B에서 등장하는 idx가 오름차순인 케이스를 구하는 것
// B의 경우 B에서 등장하는 idx가 오름차순인 걸로 나열한 LIS를 만들기 때문에, B에서 등장하는 순서대로임이 보장되고,
// A의 경우에도 애초에 A 배열에서 나열된 순서를 기준으로 arr을 만들었기 때문에, A에서 등장하는 순서대로임이 보장됨
// 즉, 둘에서 공통된 순서로 나타나는 부분 수열임

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_13711.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, A = [], B = [];

rl.on("line", (line) => {
  switch (ln) { 
    case 0:
      N = Number(line);
      break;
    case 1:
      A = line.split(" ").map(Number);
      break;
    case 2:
      B = line.split(" ").map(Number);
      break;
  }
  ln++;
});

rl.on("close", () => {
  const B_idxes = make_idx_array(B);
  const arr = new Array(N).fill(0);
  for (let i = 0; i < N; i++) { 
    arr[i] = B_idxes[A[i]];
  }
  const lis = [];
  for (let i = 0; i < N; i++) { 
    if (lis.length === 0 || lis.at(-1) < arr[i]) {
      lis.push(arr[i]);
    } else { 
      let left = 0, right = lis.length;
      // arr[i]보다 작은 원소중 가장 큰 수(lower_bound)를 찾기 위해 이분 탐색 수행
      while (left <= right) { 
        const mid = Math.floor((left + right) / 2);
        if (lis[mid] > arr[i]) {
          right = mid - 1;
        } else { 
          left = mid + 1;
        }
      }
      lis[left] = arr[i];
    }
  }
  console.log(lis.length);
});

function make_idx_array(array) { 
  const N = array.length;
  const d = new Array(N+1).fill(0);
  for (let i = 0; i < N; i++) {
    d[array[i]] = i;
  }
  return d;
}