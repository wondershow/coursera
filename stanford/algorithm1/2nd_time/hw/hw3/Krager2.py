from matplotlib.cbook import Null
import random
import math

class Edge(object):
    def __init__(self, fromnode, tonode):
        #from node number always smaller than to node
        #self.edgeId = id
        self.orifrom = fromnode
        self.orito = tonode
        self.assignArc(fromnode, tonode)
    
    def selLoop(self):
        if self.vfrom == self.vto:
            return true
        else:
            return fasle
    
    def nodes(self):
        return (self.vfrom, self.vto)
    
    def reassign(node1, node2):
        assignArc(node1, node2)
    
    '''assign the from & to node of this edge
    always keep the smaller node id at from side'''
    def assignArc(self, node1, node2):
            self.vfrom = min(node1, node2)
            self.vto = max(node1, node2)
    
    def toString(self):
        res = "This edge is from vnode " + str(self.vfrom) + " to vnode " + str(self.vto)
        res += ", its oriedge is from vnode " + str(self.orifrom) + " to node " + str(self.orito) + "\n"
        return res
    
    
    def __hash__(self):
        return hash(str(self.vfrom) + "," +  str(self.vto))
    
    def __eq__(self, other):
        return (self.vfrom, self.vto) == (other.vfrom, other.vto)
    
    def __cmp__(self):
        return object.__cmp__(self)


class Graph(object):
    def __init__(self):
        self.edgecount = 0
        self.edges = list()
        self.N = 0
        self.edgeIdSeq = 0
        self.curEdgeSet = set()
        
    def addEdge(self, fromNumber, toNumber):
        
        e = Edge(fromNumber, toNumber)
        #print "addEdge fromNumber = " + str(fromNumber) + ",toNumber = " + str(toNumber)
        if e not in self.curEdgeSet:
            self.curEdgeSet.add(e)
            self.edges.append(e)
        
    def initGraph(self, fname):
        with open(fname) as f:
            content = f.readlines()
        self.N = len(content)
        for line in content:
            nodenums = line.split()
            fromnum = int(nodenums[0])
            #print "from node is" + str(fromnum)
            for i in range(1, len(nodenums)):
                self.addEdge(fromnum, int(nodenums[i]))
        #print "Edge number is" + str(len(self.edges))

    def getEdgeCnt(self):
        return len(self.edges)

    def getN(self):
        return self.N
    
    def contract(self):
        for i in range(0, self.N - 2):
            #print "len is " + str(len(self.edges))
            numToContr = random.randint(0, len(self.edges) - 1)
            edgeToRmv = self.edges[numToContr]
            #print "edgeToRmv is " + str(numToContr)
            (node1, node2) = edgeToRmv.nodes();
            #print "node 1 is " + str(node1) + ", node2 is " + str(node2)
            fromnode = min(node1, node2)
            tonode = max(node1, node2)
            newnode = tonode;
            newEdges = []
            #print "fromnode = " + str(fromnode) + ", tonode = " + str(tonode)
            #print "node1 = " + str(node1) + ", node2 = " + str(node2)
            
            #print "contracting edge " + str(fromnode) + "," + str(tonode)
            for edge in self.edges:
                #print "adding"
                if (edge.vfrom == fromnode and edge.vto == tonode):
                    #print "selfloop, removing" 
                    #print edge.toString()
                    continue 
                if edge.vfrom == fromnode or edge.vfrom == tonode: #merge
                    #print "merging " 
                    #print edge.toString()
                    edge.assignArc(edge.vto, newnode)
                elif edge.vto == fromnode or edge.vto == tonode:
                    #print "merging" 
                    #print edge.toString()
                    edge.assignArc(edge.vfrom, newnode)
                #print  "appending"
                newEdges.append(edge)    
            self.edges = newEdges
            
    def krager(self):
        self.contract()
        return len(self.edges)  
    






N = 1000
mincount = 10000
for i in range (1, 1000):
    g = Graph();
    g.initGraph("input.txt")
    test = g.krager()
    #print "test = " + str(test)
    if mincount > test:
        mincount = test
print mincount
    
