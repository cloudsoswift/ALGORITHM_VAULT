// 처음 TypeError가 발생해, 질문 게시판에서 그 원인을 확인한 뒤 푼 문제
// 원인: 폴더 또는 파일 생성 구문(2번째 줄 ~ N + M + 1번째 줄)들이 계층 순서대로 주어지지 않을 수 있음
// 따라서, 별도로 폴더 생성 구문/파일 생성 구문들을 배열에 기록한 뒤, 각각에 대해 초기화 수행


class Directory { 
  // name: 폴더 이름
  // set: 폴더 내 파일 이름들(파일 이름 중복 X)
  // cnt: 폴더 내 파일 갯수(파일 이름 중복 O)
  // child: 하위 폴더들의 Directory 인스턴스들을 저장하는 <name, Directory> 형태의 맵
  // parent: 부모 폴더(Directory 인스턴스)에 대한 참조
  constructor(name) { 
    this.name = name;
    this.set = new Set();
    this.cnt = 0;
    this.child = new Map();
    this.parent = null;
  }
}

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_22860.txt'),
  output: process.stdout
});

let ln = 0;
let N = 0, M = 0, total_line = 0, query_count = 0, isInitializationDone = false;
// 가장 최상위 폴더인 "main"에 대한 Directory 인스턴스 생성
const root = new Directory("main");
// dictionary: <이름, Directory> 구조의 Map
const dictionary = new Map();
dictionary.set("main", root);
// 파일 생성 구문, 폴더 생성 구문들을 각각 저장하는 배열
const file_input = [];
const folder_input = [];

rl.on("line", (line) => {
  if (ln === 0) {
    [N, M] = line.split(" ").map(Number);
    total_line = N + M;
  } else if (ln <= total_line) {
    const [P, F, C] = line.split(" ");
    if (Number(C) === 0) {
      file_input.push([P, F]);
    } else { 
      folder_input.push([P, F]);
    }
  } else { 
    // Q개의 쿼리를 받는 단계에 진입했을 때, 먼저 초기화 수행
    if (!isInitializationDone) {
      initialization();
      isInitializationDone = true;
      
    } 
    if (query_count === 0) {
      query_count = Number(line);
    } else { 
      const query = line.split("/");
      
      let now_map = root;
      // root("main" 폴더)로부터 밑으로 차례차례 탐색하며 쿼리 대상 폴더 Directory 인스턴스 획득
      for (let i = 1; i < query.length; i++) { 
        now_map = now_map.child.get(query[i]);
      }
      // 해당 인스턴스의 set 크기(= 파일의 종류의 개수)와 cnt(= 파일의 총 개수) 출력
      console.log(now_map.set.size + " " + now_map.cnt);
    }
  }
  ln++;
});

rl.on("close", () => {

});

function initialization() {
  // 초기화 함수
  
  // queue: BFS 탐색에 사용할 Queue
  const queue = [];
  // temp_map: <부모 폴더 명, [자식 폴더 들]>로 이뤄진 Map
  const temp_map = new Map();
  for (let i = 0; i < folder_input.length; i++) { 
    // 각각의 폴더 생성 구문들을 토대로 부모 - 자식 관계를 Map에 기록
    const [P, F] = folder_input[i];
    if (temp_map.has(P)) {
      temp_map.get(P).push(F);
    } else { 
      temp_map.set(P, [F]);
    }
  }
  // 최상위 부모("main")부터 자식들을 temp_map에 기록된 정보를 기반으로 탐색 및 실제 폴더 구조 생성
  for (const item of temp_map.get("main")) { 
    createFolder("main", item);
    queue.push(item);
  }
  while (queue.length > 0) { 
    // Queue를 이용해 BFS 방식으로 하위 폴더들 탐색
    let queueSize = queue.length;
    while (queueSize-- > 0) { 
      const now = queue.pop();
      if (temp_map.has(now)) { 
        for (const item of temp_map.get(now)) { 
          queue.push(item);
          createFolder(now, item);
        }
      }
    }
  }
  // 폴더들을 모두 만든 뒤, 파일 생성 작업도 수행
  for (let i = 0; i < file_input.length; i++) { 
    const [P, F] = file_input[i];
    insertFile(P, F);
  }
}

function insertFile(dir, file_name) { 
  // 파일 생성 작업
  // 부모 폴더의 cnt, set에 파일을 생성했음을 반영한 뒤,
  // 조상 폴더까지 올라가며 각각의 폴더에 그 값들을 반영(누적합 처럼)
  const parent = dictionary.get(dir);
  parent.cnt = parent.cnt + 1;
  parent.set.add(file_name);
  let p = parent.parent;
  while (p !== null) { 
    p.set.add(file_name);
    p.cnt++;
    p = p.parent;
  }
}

function createFolder(dir, folder_name) { 
  // 폴더 생성 작업
  const new_folder = new Directory(folder_name);
  dictionary.set(folder_name, new_folder);
  const parent = dictionary.get(dir);
  new_folder.parent = parent;
  parent.child.set(folder_name, new_folder);
}