# exchange two elements in an array
cmpcount = 0

def exch(p, i, j):
    tmp = p[i]
    p[i] = p[j]
    p[j] = tmp

def less(a, b):
    global cmpcount
    cmpcount += 1
    return a<b

