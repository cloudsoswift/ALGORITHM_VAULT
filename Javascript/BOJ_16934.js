const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_16934.txt'),
  output: process.stdout,
});

// Trie를 구성하는 노드 클래스
class Node {
  constructor() { 
    this.child = new Map();
    this.count = 0;
  }
}

let ln = 0, N = 0;
const trie = new Node(), answer = [];

rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
  } else { 
    const [shortestPrefix, count] = insertNickname(line);
    if (shortestPrefix === line) {
      // 가능한 별칭이 없는 경우,
      // x(count) > 1인 경우 닉네임 뒤에 x를 붙임
      answer.push(shortestPrefix + (count > 1 ? count : ""));
    } else { 
      // 가능한 별칭이 있는 경우,
      // 해당 별칭을 기록
      answer.push(shortestPrefix);
    }
  }
  ln++;
});

rl.on("close", () => {
  console.log(answer.join("\n"));
});

// trie에 입력받은 닉네임을 삽입하고, 
// [가장 짧은 접두사, 같은 닉네임으로 가입한 사람 수]를 반환하는 함수
function insertNickname(nickname) { 
  let now = trie, isShortestPrefixFixed = false;
  const shortestPrefix = [];
  // 닉네임의 각 문자를 순회
  for (const word of nickname.split("")) {
    if (now.child.has(word)) {
      now = now.child.get(word);
    } else { 
      now.child.set(word, new Node());
      now = now.child.get(word);
    }
    if (!isShortestPrefixFixed) { 
      // 아직 가장 짧은 접두사가 기록되지 않은 경우
      // 현재 문자도 가장 짧은 접두사에 포함
      shortestPrefix.push(word);
      if (now.child.size === 0 && now.count === 0) { 
        // 만약 현재 문자까지 자른 접두사가 겹치는 닉네임이 없고, (now.child.size === 0)
        // 접두사가 이전까지의 다른 유저의 닉네임과 일치하지 않는 경우 (now.count === 0)
        // 현재 문자까지 자른 접두사가 가장 짧은 접두사임
        isShortestPrefixFixed = true;
      }
    }
  }
  // 현재 닉네임 가입자 수를 증가시킴
  now.count++;

  return [shortestPrefix.join(""), now.count];
}