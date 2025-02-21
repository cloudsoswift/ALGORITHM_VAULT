let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_1744.txt"
).toString().trim().split('\n');
const N = +input[0];
const arr = Array(N);
// 수열 값 입력받기
for (let i = 1; i <= N; i++) { 
  arr[i - 1] = +input[i];
}
// 배열 내림차 순으로 정렬
arr.sort((a, b) => a < b ? 1 : (a == b ? 0 : -1));
// 수열의 최대 합 저장하는 변수
let sum = 0;
// 수열의 이전 요소 값 저장하는 변수
let b = -10000;
// 수열 순회에 사용될 (+ 수열 요소의 값이 0 이상인 구간 기록하는데 사용될) 변수
let i = 0;
// i가 수열 크기보다 작으면서, 수열 요소 값이 0 이상인 동안 탐색
for (; i < N && arr[i] > 0; i++) { 
  // 기록 된 이전 값(arr[i-1]) 없으면 기록
  if (b == -10000) b = arr[i];
  else { 
    // 이전 값 기록된 것 있는 경우
    // 해당 값과 현재 요소를 묶은게 따로 더하는 것 보다 크거나 같은경우
    if (b * arr[i] >= b + arr[i]) {
      // 묶음
      sum += b * arr[i];
      // 이전 값 변수 초기화
      b = -10000;
    } else { 
      // 이전 요소를 현재 요소와 묶지않고 그냥 총합에 더 함.
      sum += b;
      // 이전 값 갱신
      b = arr[i];
    }
  }
}
if (b != -10000) { 
  // 남아있는 이전값이 있는 경우
  // 총합에 더함
  sum += b;
  b = -10000;
}
// 음수들을 역순(낮은 수 -> 높은 수)으로 탐색
// i는 수열에서 최초로 0 이하의 값이 나오는 위치
for (let j = N - 1; j >= i; j--) { 
  if (b == -10000) b = arr[j];
  else { 
    if (b * arr[j] >= b + arr[j]) {
      // 이전 값(b = arr[j+1])과 현재 값{arr[j])을 묶는게 더 나은 경우
      sum += b * arr[j];
      b = -10000;
    } else { 
      // 안 묶는게 더 나은 경우
      sum += b;
      b = arr[j];
    }
  }
}
if (b != -10000) sum += b;
console.log(sum);