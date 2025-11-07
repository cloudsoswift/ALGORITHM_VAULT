const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_21942.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, L = "", F = 0, penalty_time = 0;
// 각 회원 별로 누적된 지각 시간 기록한 배열
const penalties = new Map();
// <회원명, <부품명, 빌린 시간(Date)>> 형태의 맵
const account = new Map();


rl.on("line", (line) => {
  if (ln === 0) {
    [N, L, F] = line.split(" ");
    // 대여기간을 파싱하여, 분 단위로 저장
    const [day, time] = L.split("/");
    if (+day > 0) penalty_time += day * 24 * 60;
    const [hour, minute] = time.split(":").map(Number);
    penalty_time += hour * 60 + minute;
  } else { 
    const [date, time, P, M] = line.split(" ");
    // 부품을 빌린(또는 반납한) 시간
    const d = new Date(`${date} ${time}`);
    // 회원의 부품 장부
    let person = account.get(M);
    if (!person) {
      // 해당 회원의 부품 장부 없을 경우 만듦
      account.set(M, new Map());
      person = account.get(M);
    }
    if (person.has(P)) { 
      // 부품 장부에 해당 부품 기록 있을 경우, 즉 이번에 반납해야 하는 경우
      const before_date = person.get(P);
      // (현재 시간 - 빌리기 시작한 시간) 사이의 분을 구함
      const min_gap = Math.abs(d - before_date) / 60000;
      if (min_gap > penalty_time) { 
        // 대여한 시간이 "대여 기간(L)"보다 길면,
        // 그 차이만큼 지각 시간 누적
        penalties.set(M, (penalties.get(M) | 0) + (min_gap - penalty_time));
      }
    } else { 
      person.set(P, d);
    }
  }
  ln++;
});

rl.on("close", () => {
  if (penalties.size === 0) { 
    console.log(-1);
    return;
  }
  // 회원 닉네임 사전순으로 닉네임과 벌금 출력
  for (const k of [...penalties.keys()].sort()) { 
    console.log(`${k} ${penalties.get(k) * F}`);
  }
});