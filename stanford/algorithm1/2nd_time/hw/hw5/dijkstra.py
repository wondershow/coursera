from sets import Set
from heapq import heappush, heappop

class Dijkstra(object):
    
    def __init__(self, wg, source):
        self.test= 1
        self.g = wg
        self.s = source
        
        v = self.g.getV()
        
        self.d = [-1]*(v+1)
        self.d[source] = 0
        
        self.doneset = Set()
        self.undoneset = Set()
        
        for i in range(1, v+1):
            self.undoneset.add(v)
        self.heap = []
        heappush(self.heap, vertexDistance(source, 0))
       
    def dijk(self):
        while len(self.doneset) <= self.g.getV():
             vd = heappop(self.heap)
             toadd = vd.getV()
             self.d[toadd] = vd.getDistance()
             self.doneset.add(toadd)
             neiborset = self.g.adj(v)
             
             for (v, weight) in neiborset:
                 if vertexDistance(v, weight) in self.heap:
                     
                 
class WeightedDigram(object):
    def __init__(self, v):
        try:
            int(v)
            self.V = v
            self.E = 0
            self.adj = [None] * (v+1)
        except ValueError:
            self.adj = []
            self.E = 0
            self.V = 0
            with open(v) as lines:
                 for line in lines:
                     words = line.strip().split()
                     fromnode = int(words[0])
                    for i in range(1, len(words)):
                        pieces = words[i].split()
                        tonode = int(pieces[0])
                        weight = int(pieces[1])
                        self.add(fromnode, tonode, weight)
                    
    def addEdge(self, s, d, weight):
        if len(self.adj) - 1 < s :
            self.adj.extend([] * (s + 1 - len(self.adj)))
            for i in range(s+1, len(self.adj)):
                self.adj[i] =[]
        self.adj[i].add((d, weight))
        self.E = self.E + 1
    
    def getE(self):
        return self.E
    
    def getV(self):
        return len(self.adj) - 1
    
    def adj(self, v):
        return self.adj[v]
    
    
class vertexDistance(object):
    def __init__(self, v, score):
        self.vertex = v
        self.distance = score
    
    def __cmp__(self, other):
        return cmp(self.distance, other.distance)
    
    def toString(self):
        return "vertex is " + str(self.vertex) + ", distance is " + str(self.distance)

    def getV(self):
        return self.vertex
    
    def getDistance(self):
        return self.distance
    
    def __eq__(self, other):
        return self.vertex == other.vertex

class IndexMinPQ
    
    def __init__(self, maxN):
        self.keys = [] * (maxN + 1)
        self.pq = [] * (maxN + 1)
        self.qp = [-1] * (maxN + 1)
        self.N = 0

    def isEmpty(self):
        if N == 0 :
            return True
        else:
            return False
    
    # if this pq contains an item associated wit integer i
    def contains (self, i):
        if self.qp[i] == -1:
            return False
        else:
            return True
    
    def insert(self, i, key):
        self.N += 1
        self.qp[i] = N
        self.pq[N] = i 
        self.keys[i] = key
        self.swim(N)
        
    def swim(self, k):
        while k > 1 && self.less (k/2, k) :
            self.exch(k/2, k)
            k = k / 2
    
    def sink(self, k):
        while (2 * k < self.N):
             j = 2 * k
             if j < self.N && self.less(j, j+1):
                 j += 1
             if self.less(j, k):
                 break
             self.exch(k, j)
             k = j
    
    def exch(self, i, j):
        ranki = self.pq[i]
        rankj = self.pq[j]
        self.pq[j] = ranki
        self.pq[i] = rankj
        self.qp[ranki] = j
        self.qp[ranj] = i
    
    def less(self, i, j):
        indexi = self.pq[i]
        indexj = self.pq[j]
        if self.keys[i] > self.keys[j]:
            return True
        else:
            return False
    
    def Key minKey(self):
        return Keys[pq[1]]
        
    def delMin(self):
        res = pq[1]
        self.exch(1,self.N)
        self.sink(1)
        self.N -= 1
        qp[res] = -1
        keys[res] = null
        return res
    
    def minIndex(self):
        return self.pq[1]
    
    def changeKey(self, i, newkey):
        self.keys[i] = newkey
        self.swim(qp[i])
        self.sink(qp[i])
    
    def delete(self, i):
        index = self.qp[i]
        exch(index, self.N)
        self.N -= 1
        self.swim(index)
        self.sink(index)
        self.keys[i] = null
        self.qp[i] = -1
    
    
    def printout(self):
        for i in range(1, self.N + 1):
            minval = self.minKey()
            minindex = self.delMin()
            print "index: " + str(minindex) + ", minval: " + str(minval)
    

test = IndexMinPQ(10)
test.insert(1, 9)

       