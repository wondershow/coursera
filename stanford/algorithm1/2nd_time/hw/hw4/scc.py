from __builtin__ import True
import bisect
import Queue
import sys
import resource
import threading

class Digraph(object):
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
                    vertices = line.strip().split(" ")
                    vfrom = int(vertices[0])
                    vto = int(vertices[1])
                    if len(self.adj) - 1 < vfrom:
                        self.adj.extend([None] * (int(vfrom) + 1 - len(self.adj)))
                    self.addEdge(int(vfrom), int(vto))
            
            self.V = len(self.adj)
        
 
    def getV(self):
        return self.V - 1
    
    def getE(self):
        return self.E
    
    def addEdge(self, vfrom, vto):
        if self.adj[vfrom] is None:
            self.adj[vfrom] = set()
        self.adj[vfrom].add(vto)
        self.E = self.E + 1
    
    def adj(self, v):
        return adj[v]
    
    def reverse(self):
        r =  Digraph(self.V)
        for v in range(1, self.V):
            if self.adj[v] is None:
                continue
            for w in self.adj[v]:
                r.addEdge(w,v)
        return r





class Kosajua(object):
    
    def __init__(self, graph):
        self.g = graph
        self.reverseG = graph.reverse()
        self.marked = [False] * (self.g.getV() + 1)
        self.reversePost = []
        self.getRPostOrder()
        
       
        
        
        self.componetsizes = []
        self.size = 0
        self.marked = [False] * (self.g.getV() + 1)
        self.computeSCC()
        
        self.componetsizes.sort();
        print(self.componetsizes[-5:])
        
   
    def getRPostOrder(self):
        for v in range (1, g.getV()+1):
            #print "v is " + str(v)
            self.layer = 0;
            if self.marked[v] is False:
                self.dfs(v)
        tmporder = [];
       
    def dfs(self, v):
        self.marked[v] = True
        self.layer = self.layer + 1
        print "self.layer = " + str(self.layer) 
        #print "visiting " + str(v)
        if self.reverseG.adj[v] is not None:
            for w in self.reverseG.adj[v]:
                if self.marked[w] is False:
                    self.dfs(w)
        self.reversePost.append(v)
    
    def computeSCC(self):
        while len(self.reversePost) > 0 :
            v = self.reversePost.pop()
            #print "pop out" + str(v)
            self.size = 0
            if self.marked[v] is False:
                self.dfs2nd(v)
                self.componetsizes.add(self.size)
    
    def dfs2nd(self,v):
        self.marked[v] = True
        self.layer = self.layer + 1
        #print "self.layer = " + str(self.layer) 
        if self.g.adj[v] is not None:
            for w in self.g.adj[v]:
                if self.marked[w] is False:
                    self.dfs2nd(w)
        self.size = self.size + 1





sys.setrecursionlimit(70000)
g = Digraph("SCC.txt")

print "number is " + str(g.getV())
print "number is " + str(g.getE())
t = Kosajua(g)