// 질문 게시판에서 답 아이디어 보고 푼 문제
// 참고 글: https://www.acmicpc.net/board/view/145011

// 풀이 아이디어)
// 1. 1차원에서의 MST는 각 점들을 좌표순으로 나열한 뒤, 이들을 일렬로 이은 것임
// 2. 행성들의 각 축 별 좌표([x1, x2, x3, ...], [y1, y2, y3, ...], [z1, z2, z3, ...])들을 각각 분리해서 행성의 index와 함께 저장한 뒤
// 이들을 정렬하면 각 축별로 인접한 행성들의 간선 정보를 얻을 수 있음
// 3. 이렇게 축별로 인접한 행성간의 간성을 우선순위 큐에 넣고 크루스칼 알고리즘을 수행한다.

// 해당 아이디어를 보기 전에는 2중 포문(N^2)으로 각 행성들의 x, y, z 차이 중 가장 적은 값을 뽑아 간선으로 만든 뒤 pq에 넣어서 하려고 했는데
// 이렇게 하면 간선이 N^2개 만들어지는 문제가 발생(그래서 간선 만드는 단계에서 union-find를 사용해 거를려고 했으나 실패)

// 크루스칼을 위한 PQ
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
  input: process.platform === "linux" ? process.stdin : fs.createReadStream('./input/BOJ_2887.txt'),
  output: process.stdout,
});

let ln = 0, N = 0, MINIMUM_COST = 0, parent = [], size = [], count = 0;
// third_p => [0]: x축 좌표, [1]: y축 좌표, [2]: z축 좌표
let third_p = [[]];

rl.on("line", (line) => {
  if (ln === 0) {
    N = Number(line);
    parent = new Array(N).fill().map((_, i) => i);
    third_p = new Array(3).fill().map(_ => new Array(N).fill([]));
    size = new Array(N).fill(1);
  } else { 
    const planet = line.split(" ").map(Number);
    third_p[0][ln - 1] = [ln - 1, planet[0]];
    third_p[1][ln - 1] = [ln - 1, planet[1]];
    third_p[2][ln - 1] = [ln - 1, planet[2]];
  }
  ln++;
});

rl.on("close", () => {
  third_p.forEach((e) => e.sort((a, b) => a[1] > b[1] ? -1 : (a[1] === b[1] ? 0 : 1)));
  const pq = making_edges();
  making_MST(pq);
  console.log(MINIMUM_COST);
});

function making_edges() { 
  const pq = new PriorityQueue();
  for (let i = 0; i < N - 1; i++) {
    pq.add([Math.abs(third_p[0][i][1] - third_p[0][i + 1][1]), third_p[0][i][0], third_p[0][i + 1][0]]);
    pq.add([Math.abs(third_p[1][i][1] - third_p[1][i + 1][1]), third_p[1][i][0], third_p[1][i + 1][0]]);
    pq.add([Math.abs(third_p[2][i][1] - third_p[2][i + 1][1]), third_p[2][i][0], third_p[2][i + 1][0]]);
  }
  return pq;
}

function making_MST(pq) { 
  while (count < N-1 && pq.size() > 0) { 
    const [c, a, b] = pq.remove();
    if (find(a) !== find(b)) { 
      MINIMUM_COST += c;
      union(a, b);
      count++;
    }
  }
}

function union(a, b) { 
  const parentA = find(a);
  const parentB = find(b);
  if (size[parentA] < size[parentB]) {
    parent[parentB] = parent[parentA];
    size[parentB] += size[parentA];
  } else { 
    parent[parentA] = parent[parentB];
    size[parentA] += size[parentB];
  }
}
function find(a) { 
  if (parent[a] === a) return a;
  else { 
    while (a !== parent[a]) { 
      parent[a] = parent[parent[a]];
      a = parent[a];
    }
  }
  return a;
}