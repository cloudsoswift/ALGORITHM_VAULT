// https://staticvoidlife.tistory.com/143
// 위 글을 보고 풀이 방법을 알아낸 문제
let fs = require('fs');
let input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2138.txt"
).toString().trim().split('\n');
let result = 200_001;
const N = +input[0];
// 0번 스위치를 누르지 않았을 때의 결과 값 배열
const now_off = input[1].trim().split("").map(Number);
// 0번 스위치를 눌렀을 때의 결과 값 배열
const now_on = [...now_off];
for (let i = 0; i <= 1; i++) { 
  now_on[i] = now_on[i] === 1 ? 0 : 1;
}
const goal = input[2].trim().split("").map(Number);
count(now_off, goal, false);
count(now_on, goal, true);
console.log(result === 200_001 ? -1 : result);

// now : 전구 현재 상태 배열 / goal : 목표 전구 상태 배열 / isTriggered : 0번 스위치를 누르고 시작하는건지 나타내는 boolean 변수
function count(now, goal, isTriggered) { 
  // 스위치 누른 횟수 카운트 하는 변수
  let ans = isTriggered ? 1 : 0;
  for (let i = 1; i < N; i++) { 
    if (now[i - 1] != goal[i - 1]) { 
      // 현재 i-1번 전구와 목표의 i-1번 전구 값이 다른 경우
      // i번 스위치를 켜서 i-1번 전구의 상태 변경해 같도록 만듦.
      for (let j = i - 1; j <= i + 1; j++) { 
        if (j < 0 || j >= N) continue;
        now[j] = now[j] === 1 ? 0 : 1;
      }
      // 스위치 카운트
      ans++;
    }
  }
  for (let i = 0; i < N; i++) { 
    if (now[i] != goal[i]) return;
  }
  result = Math.min(ans, result);
}