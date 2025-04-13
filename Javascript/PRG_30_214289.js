// 3일 붙잡고있다가 도저히 모르겠어서 해설보고 푼 문제
// 해설: https://school.programmers.co.kr/questions/52432

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/PRG_30_214289.txt'),
  output: process.stdout,
});

let temperature = 0, t1 = 0, t2 = 0, a = 0, b = 0, onboard = [];
let ln = 0;

rl.on("line", (line) => {
  switch (ln) { 
    case 0:
      temperature = Number(line);
      break;
    case 1:
      t1 = Number(line);
      break;
    case 2:
      t2 = Number(line);
      break;
    case 3:
      a = Number(line);
      break;
    case 4:
      b = Number(line);
      break;
    case 5:
      onboard = line.split(" ").map(Number);
      break;
  }
  ln++;
});

rl.on("close", () => { 
  console.log(solution(temperature, t1, t2, a, b, onboard));
})
function solution(temperature, t1, t2, a, b, onboard) {
  let answer = 0;
  // 온도범위 상한선 41도(offset해서 51도)
  const t = 51;
  // dp[i][j] : i초에 실내온도 j를 달성하는데 드는 최소 소비전력
  const dp = new Array(onboard.length + 1).fill().map(_ => new Array(t+1).fill(2_000_000));
  // temperature, t1, t2 모두 최소 -10까지 가능하므로, 배열 index에 대응될 수 있게 10씩 올려줌
  temperature += 10;
  t1 += 10;
  t2 += 10;
  // 초기값 설정(0분에 실외온도에 도달하는 최소 소비전력은 0)
  dp[0][temperature] = 0;

  for (let i = 1, len = onboard.length; i < len; i++) { 
    const start_temp = onboard[i] === 1 ? t1 : 0;
    const end_temp = onboard[i] === 1 ? t2 : t;
    for (let j = start_temp; j <= end_temp; j++) { 
      // 1. 에어컨 꺼서 실외온도 쪽으로 다가가는 경우
      let turn_off_cost = 2_000_000;
      if (j - 1 >= 0 && j - 1 < temperature) { 
        // 1.1. i-1분에 j-1도에서 에어컨을 껐을때 j도가 되는 경우
        // 즉, j-1이 temperature보다 낮은 경우
        turn_off_cost = dp[i - 1][j - 1];
      }
      if (j === temperature) { 
        // 1.2. i-1분에 j도에서 에어컨을 껐을때 j도가 되는 경우
        turn_off_cost = Math.min(turn_off_cost, dp[i - 1][j]);
      }
      if (j + 1 <= t && j + 1 > temperature) { 
        // 1.3. i-1분에 j+1도에서 에어컨을 껐을때 j도가 되는 경우
        // 즉, j+1이 temperature보다 높은 경우
        turn_off_cost = Math.min(turn_off_cost, dp[i - 1][j + 1]);
      }
      // 2. 에어컨 온도 유지하는 경우
      const maintain_cost = dp[i - 1][j] + b;

      // 3. 에어컨 켜서 온도 조절한 경우
      // 3.1. j가 0초과 51 미만인 경우, [j-1도에서 올라오기, j+1도에서 내려오기] 중 더 낮은 비용
      // 3.2. j가 51인 경우, j-1도에서 올라오는 경우
      // 3.3. j가 0인 경우, j+1도에서 내려오는 경우
      const turn_on_cost = j > 0 && j < 51 ? Math.min(dp[i - 1][j - 1] + a, dp[i - 1][j + 1] + a) : (
        j === t ? dp[i - 1][j - 1] + a : dp[i - 1][j + 1] + a
      );

      // 위 세가지 경우 중 최소 값으로 dp[i][j] 기록
      dp[i][j] = Math.min(turn_off_cost, Math.min(maintain_cost, turn_on_cost));
    }
  }
  // dp[onboard.length-1] 에서 가장 작은 소비전력 값 뽑아 반환
  answer = dp[onboard.length-1].reduce(((pv, cv) => Math.min(pv, cv)));
  return answer;
}


// 그리디 방식으로 풀려고 헛걸음한 코드... 최대 정확성 48.0 / 100.0
// ----------------------------------------------------------
// function solution(temperature, t1, t2, a, b, onboard) {
//   let answer = 0, least_one = -1;
//   const next_time = new Array(onboard.length).fill(-1);
//   // t1, t2 중 실외온도(temperature)와 더 가까운 온도
//   const nearTemperature = Math.abs(temperature - t1) < Math.abs(temperature - t2) ? t1 : t2;

//   // onboard[i]에 다음 탑승객 탑승 시간까지 얼마나 남았는지 기록
//   for (let i = onboard.length - 1; i >= 0; i--) { 
//     if (onboard[i] === 1) { 
//       least_one = i;
//     }
//     if (least_one >= 0) {
//       next_time[i] = least_one - i;
//     }
//   }
//   let temp = temperature;
//   // 실외온도(temperature)는 무조건 t1 ~ t2 범위 밖의 값
//   // 따라서, t1과 t2 중 실외온도와 더 가까운 값으로 다가가는게 효율적
//   for (let i = 0, len = onboard.length; i < len; i++) { 
//     // i 초 이상의 시간에는 승객이 탑승하지 않으므로 에어컨 틀지 말지 여부를 따질 이유가 없음
//     if (next_time[i] === -1) break;
//     if (next_time[i] > 0) {
//       // next_time[i] 가 1 이상인 경우, 즉 곧 승객이 탑승할 예정인 경우
//       let j = next_time[i];
//       // i ~ j 까지의 시간 차이 time_diff와 temp ~ nearTemperature 사이의 온도 차이 temp_diff
//       const temp_diff = Math.abs(temp - nearTemperature);
//       if (temp_diff === 0) {
//         // 만약 목표 온도와 현재 온도 차가 0인 경우
//         // 즉, [승객 탑승 -> 미탑승 -> 승객 탑승] 중 "미탑승"에 해당하는 시간대인 경우
//         const gap_to_outside = Math.abs(temperature - temp);
//         let gap_to_outside_cost = Number.MAX_SAFE_INTEGER;
//         if ((gap_to_outside * 2) <= j) { 
//           // 에어컨 꺼서 실외온도 맞춘 뒤, 에어컨 켜서 다시 목표 온도(nearTemperature) 맞추는게 가능한 경우
//           gap_to_outside_cost = a * gap_to_outside;
//         }
//         // 그 시간동안 온도를 유지할지, 올렸다 놔뒀다 올렸다 할지 경우 비교
//         const maintain_cost = b * j, upper_cost = (a * (j === 1 ? 1 : Math.floor(j / 2)));
//         answer += Math.min(maintain_cost, Math.min(upper_cost, gap_to_outside_cost));
//       } else { 
//         if (temp_diff === j) {
//           // 시간 갭 === 온도차 인경우, i ~ j 기간동안 계속 에어컨 희망온도를 실내온도와 다르게 설정
//           answer += (a * j);
//         } else { 
//           // 시간 갭 > 온도차 인경우, 시간 갭 - 온도차 만큼은 에어컨 안틂
//           answer += (a * temp_diff);
//         }
//       }
//       temp = nearTemperature;
//       i = i + j - 1;
//     } else { 
//       // next_time[i] 가 0인 경우, 즉 현재 승객이 탑승중인 경우
//       let j = i;
//       while (j < len && next_time[j] === 0) { 
//         // next_time의 범위 내에서, next_time[j]가 0이 아닐때 까지 j 증가
//         j++;
//       }
//       j--;
//       const dist = j - i + 1;
//       const maintain_cost = b * dist, upper_cost = (a * Math.floor(dist / 2));
//       if (maintain_cost > upper_cost) {
//         answer += upper_cost;
//       } else { 
//         answer += maintain_cost;
//         if (j === len - 1) answer -= b;
//       }
//       i = j;
//     }
//   }
//   return answer;
// }