const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.env === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_7432.txt'),
  output: process.stdout
});

let ln = 0, N = 0, result = "";
const root = new Map();

rl.on("line", (line) => {
  if (N === 0) {
    N = Number(line);
  } else { 
    const splitStr = line.split("\\");
    let now = root;
    for (let i = 0, len = splitStr.length; i < len; i++) {
      const v = now.get(splitStr[i]);
      if (v !== undefined) {
        now = v;
      } else { 
        const vv = new Map();
        now.set(splitStr[i], vv);
        now = vv;
      }
    }
  }
  ln++;
});

rl.on("close", () => {
  traverse(root, 0);
  console.log(result);
});

function traverse(map, depth) { 
  for (const [k, v] of [...map.entries()].sort((a, b) => a[0] > b[0] ? 1 : -1)) { 
    result += `${" ".repeat(depth)}${k}\n`;
    traverse(v, depth + 1);
  }
}