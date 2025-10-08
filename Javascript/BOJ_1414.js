// 게시판의 여러 반례들을 통해 시행착오 끝에 푼 문제

// 크루스칼 알고리즘 구현을 위한 우선순위 큐
// 해당 큐는 각 간선 정보([비용, 출발지, 도착지])들로 이루어짐
class PriorityQueue {
  constructor() {
      this.heap = [];
  }

  // Helper Methods
  getLeftChildIndex(parentIndex) {
      return 2 * parentIndex + 1;
  }

  getRightChildIndex(parentIndex) {
      return 2 * parentIndex + 2;
  }

  getParentIndex(childIndex) {
      return Math.floor((childIndex - 1) / 2);
  }

  hasLeftChild(index) {
      return this.getLeftChildIndex(index)
          < this.heap.length;
  }

  hasRightChild(index) {
      return this.getRightChildIndex(index)
          < this.heap.length;
  }

  hasParent(index) {
      return this.getParentIndex(index) >= 0;
  }

  leftChild(index) {
      return this.heap[this.getLeftChildIndex(index)];
  }

  rightChild(index) {
      return this.heap[this.getRightChildIndex(index)];
  }

  parent(index) {
      return this.heap[this.getParentIndex(index)];
  }

  swap(indexOne, indexTwo) {
      const temp = this.heap[indexOne];
      this.heap[indexOne] = this.heap[indexTwo];
      this.heap[indexTwo] = temp;
  }

  peek() {
      if (this.heap.length === 0) {
          return null;
      }
      return this.heap[0];
  }
  size() { 
      return this.heap.length;
  }

  // remove 호출시 가장 높은 우선순위를 가지는 top 요소를 삭제하고
  // heapifyDown를 호출함
  remove() {
      if (this.heap.length === 0) {
          return null;
      }
      const item = this.heap[0];
      this.heap[0] = this.heap[this.heap.length - 1];
      this.heap.pop();
      this.heapifyDown();
      return item;
  }

  add(item) {
      this.heap.push(item);
      this.heapifyUp();
  }

  heapifyUp() {
      let index = this.heap.length - 1;
      while (this.hasParent(index) && this.parent(index)[0]
          > this.heap[index][0]) {
          this.swap(this.getParentIndex(index), index);
          index = this.getParentIndex(index);
      }
  }

  heapifyDown() {
      let index = 0;
      while (this.hasLeftChild(index)) {
          let smallerChildIndex = this.getLeftChildIndex(index);
          if (this.hasRightChild(index) && this.rightChild(index)[0]
              < this.leftChild(index)[0]) {
              smallerChildIndex = this.getRightChildIndex(index);
          }
          if (this.heap[index][0] < this.heap[smallerChildIndex][0]) {
              break;
          } else {
              this.swap(index, smallerChildIndex);
          }
          index = smallerChildIndex;
      }
  }
}

const fs = require('fs');
const readline = require('readline');
const rl = readline.createInterface({
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_1414.txt'),
  output: process.stdout,
});

const CODE_A = 'A'.charCodeAt(), CODE_Z = 'Z'.charCodeAt();
const CODE_a = 'a'.charCodeAt(), CODE_z = 'z'.charCodeAt(), CODE_0 = '0'.charCodeAt();

// matrix[i][j] -> i번 컴퓨터에서 j번 컴퓨터로 연결하기 위한 랜선 길이
// parent[i] -> union-find를 위한 부모 idx 배열
let ln = 0, N = 0, matrix = [[]], parent = [];
let whole_value = 0;
const queue = new PriorityQueue();
rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
    matrix = new Array(N).fill().map(_ => new Array(N).fill(0));
    parent = new Array(N).fill().map((_, i) => i);
  } else { 
    for (let i = 0, len = line.length; i < len; i++) { 
      const c = line.charCodeAt(i);
      if (c >= CODE_A && c <= CODE_Z) {
        matrix[ln - 1][i] = c - CODE_A + 27;
      } else if (c >= CODE_a && c <= CODE_z) {
        matrix[ln - 1][i] = c - CODE_a + 1;
      }
      whole_value += matrix[ln - 1][i];
      // 스스로와 연결된 랜선(ln-1 === i), 또는 연결되지 않은(랜선 길이가 0인) 경우는 queue에 삽입하지 않음
      if(ln - 1 !== i && c !== CODE_0) queue.add([matrix[ln - 1][i], ln - 1, i]);
    }
  }
  ln++;
});

rl.on("close", () => {
  if (N === 1) { 
    // 컴퓨터가 한대 있는 경우(= N === 1인 경우),
    // 그 한대의 랜선(matrix[0][0])을 기부
    console.log(matrix[0][0]);
    return;
  }
  // 선택된 간선 개수 count
  let count = 0;
  // 간선을 N-1개 선택하거나, queue에 있는 간선을 모두 탐색하기 전까지 계속 탐색
  while (queue.size() > 0 && count < N-1) { 
    const [cost, from, to] = queue.remove();
    // 간선의 각 끝 컴퓨터 번호의 부모를 불러옴
    const from_p = find(from), to_p = find(to);
    // 둘이 이미 같은 그룹(= 같은 부모를 가짐)인 경우 연결할 필요 없음
    if (from_p === to_p) continue;
    union(from_p, to_p);
    // 이 둘을 이은 랜선은 기부하지 못하므로, 기부할 랜선 길이(whole_value)에서 뺌
    whole_value -= cost;
    count++;
  }
  // N-1개의 간선을 선택한 경우(count === N-1), 기부할 랜선 길이를 출력하고
  // 선택하지 못한 경우 -1을 출력
  console.log(count === N-1 ? whole_value : -1);
});

function union(a, b) { 
  const parentA = find(a);
  const parentB = find(b);
  if (parentA < parentB) {
    parent[parentB] = parentA;
    find(b);
  } else { 
    parent[parentA] = parentB;
    find(a);
  }
}

function find(a) { 
  while (a !== parent[a]) { 
    parent[a] = parent[parent[a]];
    a = parent[a];
  }
  return a;
}