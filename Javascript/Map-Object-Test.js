// 경로로 주어질 문자열 최대 길이
let MAX_LEN = 15;
// 명령어 저장할 배열과, 존재하는 경로들을 저장하는 배열 
const COMMANDS = []; 
const EXIST_DIRS = [];

// length개의 랜덤한 문자열이 .으로 이어진 형태의 문자열을 반환하는 함수
function generateRandomWord(length) {
  let result = "";
  for (let i = 0; i < length; i++) { 
    let str = undefined;
    while (str == undefined) { 
      str = (Math.random() + 1).toString(36).slice(2);
    }
    result += str;
    if (i == length - 1) break;
    result += ".";
  }
  return result;
}
// 생성 명령 500,000개 생성
for (let i = 0; i < 500_000; i++) { 
  const comm = [1, ""];
  const isPossible = Math.round(Math.random());
  // 랜덤한 숫자를 36진법으로 나타낸 뒤 소수점 자리만 뗀 것을 문자열로 사용
  const randomWord = generateRandomWord(1);
  if (isPossible === 0) {
    // 경로가 유효하지 않은 명령
    if (Math.random() > 0.8) {
      // 경로가 유효하다가, 마지막만 유효하지 않은 경우
      const idx = Math.floor(Math.random() * EXIST_DIRS.length);
      comm[1] = EXIST_DIRS[idx] + "." + generateRandomWord(2);
    } else {
      // 경로가 통째로 유효하지 않은 경우
      comm[1] = generateRandomWord(Math.ceil(Math.random() * MAX_LEN) + 1);
     }
  } else { 
    // 경로가 유효한 명령
    if (EXIST_DIRS.length > 0) {
      const idx = Math.floor(Math.random() * EXIST_DIRS.length);
      comm[1] = EXIST_DIRS[idx] + "." + randomWord;
      EXIST_DIRS.push(comm[1]);
    } else { 
      comm[1] = randomWord;
      EXIST_DIRS.push(randomWord);
    }
  }
  COMMANDS.push(comm);
}
// 삭제 명령 500,000개 생성
for (let i = 0; i < 100_000; i++) { 
  const comm = [2, ""];
  const isPossible = Math.round(Math.random());
  if (isPossible === 0 || EXIST_DIRS.length == 0) {
    // 경로가 유효하지 않은 명령
    if (Math.random() > 0.5) {
      // 경로가 유효하다가, 마지막만 유효하지 않은 경우
      const idx = Math.floor(Math.random() * EXIST_DIRS.length);
      comm[1] = EXIST_DIRS[idx] + "." + generateRandomWord(1);
    } else {
      // 경로가 통째로 유효하지 않은 경우
      comm[1] = generateRandomWord(Math.ceil(Math.random() * MAX_LEN) + 1);
     }
  } else { 
    // 경로가 유효한 명령
    const idx = Math.floor(Math.random() * EXIST_DIRS.length);
    comm[1] = EXIST_DIRS[idx];
    EXIST_DIRS.splice(idx, 1);
  }
  COMMANDS.push(comm);
}

// 삽입 삭제 작업을 수행할 Root 객체
const root = {};

// Object를 통해 삽입/삭제 작업을 수행하는 함수들
function insertionObject(comm) { 
  const splited = comm.split(".");
  let temp = root;
  for (let i = 0; i < splited.length - 1; i++) { 
    if (Object.hasOwn(temp, splited[i])) {
      temp = temp[splited[i]];
    } else { 
      return;
    }
  }
  temp[splited.at(-1)] = {};
}
function deletionObject(comm) { 
  const splited = comm.split(".");
  let temp = root;
  for (let i = 0; i < splited.length - 1; i++) { 
    if (Object.hasOwn(temp, splited[i])) {
      temp = temp[splited[i]];
    } else { 
      return;
    }
  }
  if (Object.hasOwn(temp, splited.at(-1))) { 
    delete temp[splited.at(-1)];
  }
}

// 삽입 삭제 작업을 수행할 Root 객체
const rootMap = new Map();

// Map을 통해 삽입/삭제 작업을 수행하는 함수들
function insertionMap(comm) { 
  const splited = comm.split(".");
  let temp = rootMap;
  for (let i = 0; i < splited.length - 1; i++) { 
    if (temp.has(splited[i])) {
      temp = temp.get(splited[i]);
    } else { 
      return;
    }
  }
  temp.set(splited.at(-1), new Map());
}
function deletionMap(comm) { 
  const splited = comm.split(".");
  let temp = rootMap;
  for (let i = 0; i < splited.length - 1; i++) { 
    if (temp.has(splited[i])) {
      temp = temp.get(splited[i]);
    } else { 
      return;
    }
  }
  if (temp.has(splited.at(-1))) { 
    temp.delete(splited.at(-1));
  }
}
// gc();
// Object 작업 수행 및 시간 계산 후 출력
// let startTime = performance.now();
// let startMemory = process.memoryUsage().heapTotal / 1024 / 1024;
// for (let i = 0; i < COMMANDS.length; i++) { 
//   const comm = COMMANDS[i];
//   if (comm[0] == 1) {
//     insertionObject(comm[1]);
//   } else { 
//     // console.log(comm[1]);
//     deletionObject(comm[1]);
//   }
// }
// let endTime = performance.now();
// let endMemory = process.memoryUsage().heapTotal / 1024 / 1024;
// console.log("Object Excecution Time : " + (endTime - startTime));
// console.log("Object Memory usage : " + (endMemory - startMemory));
gc();
// Map 작업 수행 및 시간 계산 후 출력
let startTime = performance.now();
let startMemory = process.memoryUsage().heapTotal / 1024 / 1024;
for (let i = 0; i < COMMANDS.length; i++) { 
  const comm = COMMANDS[i];
  if (comm[0] == 1) {
    insertionMap(comm[1]);
  } else { 
    deletionMap(comm[1]);
  }
}
let endTime = performance.now();
let endMemory = process.memoryUsage().heapTotal / 1024 / 1024;
console.log("Map Excecution Time : " + (endTime - startTime));
console.log("Map Memory usage : " + (endMemory - startMemory));
gc();