const fs = require('fs');
const input = fs.readFileSync(process.platform === "linux" ? "/dev/stdin" : "./input/BOJ_2179.txt"
).toString().trim().split('\n').map(str=>str.trim());
const N = +input[0];
let longest_length = 0;
let S = 1;
let T = 2;
const map = new Map();
for (let i = 0; i < N; i++) { 
  const str = input[i + 1];
  let tmp = "";
  for (let j = 0; j < str.length; j++) { 
    tmp += str.at(j);
    if (map.get(tmp)) {
      const queue = map.get(tmp);
      queue.push(i + 1);
      if (j + 1 > longest_length) {
        for (element of queue) {
          console.log(input[i + 1] + ", " + input[element]);
          if (input[i + 1] === input[element]) continue;
          S = element;
          T = i + 1;
          longest_length = j + 1;
          break;
        }
      } else if (j + 1 === longest_length) { 
        // 이전의 최장 접두사 길이와 동일하면서, S가 더 앞에있는 문자열인 경우엔 갱신
        for (element of queue) {
          if (input[i + 1] === input[element]) continue;
          if (element >= S) break;
          S = element;
          T = i + 1;
          break;
        }
      }
    } else { 
      map.set(tmp, [i+1]);
    }
  }
}
if (longest_length > 0) {
  console.log(input[S]);
  console.log(input[T]);
} else { 
  while (T < N-1 && input[S] === input[T]) { 
    T++;
  }
  console.log(input[S]);
  console.log(input[T]);
}