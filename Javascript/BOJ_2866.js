// 시간 초과때문에 문제 유형보고 푼 문제
// 원 아이디어:
// 1. 0부터 R-1까지 행을 줄여나가며 탐색한다.(idx i) (문제에서 나오는 "가장 위의 행을 지우는 행동"과 유사)
// 2. 열마다 i ~ R-1 까지 같은 열에 존재하는 각 행 문자들을 배열에 저장한 뒤, join하여 문자열로 합친다.
// 3. 이후 합친 문자열이 map에 존재하지 않는다면 기록하고, 존재한다면 탈출한다.
// (해당 위치(i)에서 가장 위의 행(i-1)을 지워도 문자열이 중복되지 않아야 하는데, 이 조건을 만족하지 못하는 것이기 때문)
// 4. 조건을 만족할 때 까지, i를 하나씩 증가시키며 탐색한 뒤 i-1을 출력한다.
// (해당 위치(i)에서 조건이 만족되지 못했기 때문에, i-1까지가 조건이 만족된 것이기 때문)
// -------------------------
// 위 아이디어는 최악의 경우 N^3(1000 * 1000 * 1000) (0~R-1까지 i 줄여나가기 * 0 ~ C 탐색하기 * i ~ R-1까지 각 문자 탐색)
// 이 소요되기 때문에, 이를 줄이기 위해 "0부터 R-1까지 행을 줄이는 과정"을 이분 탐색으로 대체
// 이는 문제가 갖고있는 특성에 따라 가능한 것
// "가장 위의 행을 지웠을때 문자열이 중복"되는 경우, 즉 i ~ R-1까지 각 열의 문자들을 이었을 때 중복이 발생할 경우,
// i, i+1, ... , R-1까지 모두 중복이 발생하는 것이기 때문에 i 이하로 더 탐색하지 않아도 됨
// 따라서 중복 없다 -> 더 아래(R-1쪽으로)로 탐색 / 중복 있다 -> 더 위로(0쪽으로) 탐색
// 하는 형태의 이분 탐색이 가능

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2866.txt'),
  output: process.stdout,
});

let R = 0, C = 0;
let arr = [];
let ln = 0;

rl.on("line", (line) => {
  if (ln === 0) {
    [R, C] = line.split(" ").map(Number);
    arr = new Array(R).fill().map(_ => new Array(C).fill(""));
  } else { 
    const strArr = line.split("");
    for (let i = 0, len = strArr.length; i < len; i++) { 
      arr[ln - 1][i] = strArr[i];
    }
  }
  ln++;
});

rl.on("close", () => {
  // 문자열이 중복되는지 확인하기 위해 Map 사용
  const map = new Map();
  // 가능한 최대 count를 나타내는 변수
  let count = 0;
  // 이분 탐색에 사용할 변수 left, right
  let left = 0, right = R;
  loop:
  while (left < right) { 
    const mid = Math.floor((left + right) / 2);
    for (let i = 0; i < C; i++) {
      // 각 열을 순회하며 mid ~ R-1 번째 문자까지 문자들을 합칠 예정
      // 이때, 각 문자들을 tmpArr이라는 배열에 저장
      const tmpArr = new Array(R - mid).fill("");
      for (let j = mid; j < R; j++) {
        tmpArr[j - mid] = arr[j][i];
      }
      // 배열에 저장된 각 문자들을 join을 통해 합침
      const str = tmpArr.join("");
      // 만약 합친 문자열이 이미 map에 존재하는 경우, 즉 중복되는 문자열이 발견되는 경우
      if (map.has(str)) {
        // 반복을 생략한 뒤, 0쪽으로 더 탐색하기 위해 왼쪽으로 범위를 더 좁힘
        right = mid;
        continue loop;
      }
      // map에 존재하지 않는 경우, 해당 문자열이 등장했음을 map에 기록
      map.set(str, true);
    }
    map.clear();
    // 중복이 발생하지 않은 경우, R-1 쪽으로 더 탐색하기 위해 오른쪽으로 범위를 더 좁힘
    left = mid + 1;
    // 또한, mid - 1 위로까지는 행을 지워도 중복되지 않음을 기록
    count = Math.max(count, mid);
  }
  // count 개수 출력
  console.log(count);
});