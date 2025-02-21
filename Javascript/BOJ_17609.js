const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: fs.createReadStream('./input/BOJ_17609.txt'),
  // input: process.stdin,
  output: process.stdout,
})

let ln = 0, result = "", N = 0;

rl.on('line', (line) => {
  if (ln === 0) {
    N = Number(line);
  } else { 
    let left = 0, right = line.length - 1;
    let count = 0;
    while (left < right) { 
      if (line.at(left) === line.at(right)) {
        left++;
        right--;
      } else { 
        let leftfail = false, rightfail = false;
        // 왼쪽, 오른쪽 문자 하나를 제거하는게 가능한 지 여부 저장하는 변수
        const leftPossible = line.at(left + 1) === line.at(right);
        const rightPosible = line.at(left) === line.at(right - 1);
        // 왼쪽/오른쪽 문자 제거한 경우 이후 쭉 회문 되는지 확인하는데 사용되는 변수
        let tmpleft = left, tmpright = right;
        if (leftPossible) {
          // 왼쪽 문자를 제거하면 동일해지는 경우, 해당 케이스로 쭉 확인
          tmpleft = left + 2, tmpright = right - 1;
          while (tmpleft < tmpright) { 
            if (line.at(tmpleft) === line.at(tmpright)) {
              tmpleft++;
              tmpright--;
            } else {
              // 왼쪽 문자 제거해도 회문 안되는 경우
              leftfail = true;
              break;
            }
          }
          if (!leftfail) { 
            // 왼쪽 문자 제거시 회문 되는 경우
            count++;
            break;
          }
        }
        if (rightPosible) {
          // 오른쪽 문자를 제거하면 동일해지는 경우, 해당 케이스로 쭉 확인
          tmpleft = left+1, tmpright = right - 2;
          while (tmpleft < tmpright) { 
            if (line.at(tmpleft) === line.at(tmpright)) {
              tmpleft++;
              tmpright--;
            } else { 
              // 오른쪽 문자 제거해도 회문 안되는 경우
              rightfail = true;
              break;
            }
          }
          if (!rightfail) { 
            // 오른쪽 문자 제거시 회문 되는 경우
            count++;
            break;
          }
        }
        
        if ((!rightPosible || rightfail) === (!leftPossible || leftfail)) {
          // (애초에 오른쪽 문자를 제거해도 문자가 같지 않음 || 오른쪽 문자를 제거한 걸 시도했는데 회문 아님) 이
          // (애초에 왼쪽 문자를 제거해도 문자가 같지 않음 || 왼쪽 문자를 제거한 걸 시도했는데 회문 아님) 과 같으면
          // 오른쪽 문자, 왼쪽 문자 둘 중 어느걸 제거하든 회문이 될 수 없는 경우임
          count = 2; 
        }
        break;
      }
    }
    if (count === 0) {
      result += "0\n";
    } else if (count === 1) {
      result += "1\n";
    } else { 
      result += "2\n";
    }
  } 
  ln++;
});

rl.on('close', () => {
  console.log(result);
});