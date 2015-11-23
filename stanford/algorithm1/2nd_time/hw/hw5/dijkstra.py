from sets import Set
from heapq import heappush, heappop
import math



class Dijkstra(object):
    
    def __init__(self, wg, source):
        self.test= 1
        self.g = wg
        self.s = source
        
        v = self.g.getV()
        
        self.score = [100000] * (v+1)
        
        self.score[source] = 0
        
        self.doneset = [source]
        
        print "number of vertex is " + str(self.g.getV())
        
        heap = IndexMinPQ(self.g.getV())
        
        
        
        for vertex, weight in self.g.neibors(self.s):
            print "adding vertex " + str(vertex) + "," + str(weight)
            heap.insert(vertex, weight)
        
        
        print "heap size is " + str(heap.N)
        
        while len(self.doneset) < self.g.getV():
            score = heap.minKey()
            vertex = heap.delMin()
            self.score[vertex] = score
            self.doneset.append(vertex)
            print "score is" + str(score) + "vertex is " + str(vertex)
            for v,weight in self.g.neibors(vertex):
                print "v is " + str(v)
                if heap.contains(v):
                    print "existing key  of " + str(v) + " is " + str(heap.keyOf(v))
                    newscore = min(heap.keyOf(v), weight + score )
                    print "update score " + str(v) + "," + str(newscore)
                    heap.changeKey(v, newscore)
                elif v not in self.doneset:
                    print "add score " + str(v) + "," + str(weight + score)
                    #print "add score " + str(weight + score)
                    heap.insert(v, weight + score )
        print self.score[7]
        print self.score[37]
        print self.score[59]
        print self.score[82]
        print self.score[99]
        print self.score[115]
        print self.score[133]
        print self.score[165]
        print self.score[188]
        print self.score[197]
                     
                 
class WeightedDigram(object):
    def __init__(self, v):
        try:
            int(v)
            self.V = v
            self.E = 0
            self.adjacent = [None] * (v+1)
        except ValueError:
            self.adjacent = []
            self.E = 0
            self.V = 0
            
            with open(v) as lines:
                 for line in lines:
                     words = line.strip().split()
                     fnode = int(words[0].strip())
                     self.V += 1
                     
                     
                     if len(self.adjacent) - 1 < fnode :
                         self.adjacent.extend([None] * (fnode + 1 - len(self.adjacent)))
                         for i in range(fnode, len(self.adjacent)):
                             self.adjacent[i] = []

                     for i in range(1, len(words)):
                        pieces = words[i].strip().split(",")
                        tonode = int(pieces[0])
                        weight = int(pieces[1])
                        self.addEdge(fnode, tonode, weight)
                        #print "adding (" + str(fnode) + "," + str(tonode) + ")"; 
                        
                    
    def addEdge(self, s, d, weight):
        if len(self.adjacent) - 1 < s :
            self.adjacent.extend([None] * (s + 1 - len(self.adjacent)))
            for i in range(s, len(self.adjacent)):
                self.adjacent[i] = []
        self.adjacent[s].append((d, weight))
        self.E = self.E + 1
    
    def getE(self):
        return self.E
    
    def getV(self):
        return self.V
    
    def neibors(self, v):
        return self.adjacent[v]

#IndexMinPQ ds from Princeton book P.333
class IndexMinPQ:
    
    def __init__(self, maxN):
        self.keys = [None] * (maxN + 1)
        self.pq = [-1] * (maxN + 1)
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
        print "heap adding index " + str(i) + ", value is " + str(key)
        self.N += 1
        self.qp[i] = self.N
        self.pq[self.N] = i
        self.keys[i] = key
        self.swim(self.N)
        
    def swim(self, k):
        while k > 1 and self.less (k/2, k) :
            self.exch(k/2, k)
            k = k / 2
    
    def sink(self, k):
        while (2 * k <= self.N):
             j = 2 * k
             if j < self.N and self.less(j, j+1):
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
        self.qp[rankj] = i
    
    def less(self, i, j):
        indexi = self.pq[i]
        indexj = self.pq[j]
        if self.keys[indexi] > self.keys[indexj]:
            return True
        else:
            return False
    
    def minKey(self):
        return self.keys[self.pq[1]]
        
    def delMin(self):
        minindex = self.pq[1]
        self.exch(1,self.N)
        self.N -= 1
        self.sink(1)
        self.qp[minindex] = -1
        self.keys[minindex] = None
        return minindex
    
    def minIndex(self):
        return self.pq[1]
    
    def changeKey(self, i, newkey):
        self.keys[i] = newkey
        print "i is " + str(i) + ", qp is " + str(self.qp[i])
        #self.swim(self.qp[i])
        self.swim(self.qp[i])
        self.sink(self.qp[i])
        #self.sink(self.qp[i])
    
    def delete(self, i):
        index = self.qp[i]
        self.exch(index, self.N)
        self.N -= 1
        self.swim(index)
        self.sink(index)
        self.keys[i] = None
        self.qp[i] = -1
    
    
    def printout(self):
        for i in range(1, self.N + 1):
            minval = self.minKey()
            minindex = self.delMin()
            print "index: " + str(minindex) + ", minval: " + str(minval)
    
    def keyOf(self, v):
        return self.keys[v]

'''
test = IndexMinPQ(10)
test.insert(1,3)
test.insert(2,5)
test.insert(3,6)
test.insert(4,9)
test.insert(5,1)
test.insert(6,2)
test.insert(7,8)
test.insert(8,4)
test.insert(9,7)
test.changeKey(9, -1)
test.delete(8)

test.changeKey(4, 3.5)

test.printout()'''

test = WeightedDigram("dijkstraData.txt")
#test = WeightedDigram("test.txt")
#print(test.neibors(1))
d = Dijkstra(test,1)

       