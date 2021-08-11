import copy
import PriorityQueues


class Graph:

    class Vertex:
        __slots__ = '_element'

        def __init__(self, x):
            self._element = x

        def element(self):
            return self._element

        def __hash__(self):
            return hash(id(self))

    class Edge:
        __slots__ = '_origin', '_destination', '_element'

        def __init__(self, u, v, x):
            self._origin = u
            self._destination = v
            self._element = x

        def endpoints(self):
            return (self._origin, self._destination)

        def opposite(self, v):
            return self._destination if v is self._origin else self._origin

        def element(self):
            return self._element

        def __hash__(self):
            return hash((self._origin, self._destination))

    def __init__(self, directed=False):
        self._outgoing = {}
        self._incoming = {} if directed else self._outgoing

    def is_directed(self):
        return self._incoming is not self._outgoing

    def vertex_count(self):
        return len(self._outgoing)

    def vertices(self):
        return self._outgoing.keys()

    def edge_count(self):
        total = sum(len(self._outgoing[v]) for v in self._outgoing)
        return total if self.is_directed() else total // 2

    def edges(self):
        result = set()
        for secondary_map in self._outgoing.values():
            result.update(secondary_map.values())
        return result

    def get_edge(self, u, v):
        return self._outgoing[u].get(v)  # return none if v not adjacent

    def degree(self, v, outgoing=True):
        adj = self._outgoing if outgoing else self._incoming
        return len(adj[v])

    def incident_edges(self, v, outgoing=True):
        adj = self._outgoing if outgoing else self._incoming
        for edges in adj[v].values():
            yield edges

    def insert_vertex(self, x=None):
        v = self.Vertex(x)
        self._outgoing[v] = {}
        if self.is_directed():
            self._incoming[v] = {}
        return v

    def insert_edge(self, u, v, x=None):
        e = self.Edge(u, v, x)
        self._outgoing[u][v] = e
        self._incoming[v][u] = e

    def DFS(self, g, u, discovered={u: None}):
        for e in g.incident_edges(u):
            v = e.opposite(u)
            if v not in discovered:
                discovered[e] = v
                self.DFS(g, v, discovered)

    def constrcut_path(self, u, v, discovered):
        path = []
        if v in discovered:
            path.append(v)
            walk = v
            while walk is not u:
                e = discovered[walk]
                parent = e.opposite(walk)
                path.append(parent)
                walk = parent
            path.reverse()
        return path

    def BFS(self, g, s, discovered):
        level = [s]
        while len(level) > 0:
            next_level = []
            for u in level:
                for e in g.incident_edges(u):
                    v = e.opposite(u)
                    if v not in discovered:
                        discovered[v] = e
                        next_level.append(v)
            level = next_level

    # Flodyd_Warshall 算法
    def flodyd_warshall(self, g):
        closures = copy.deepcopy(g)
        verts = list(closures.vertices())
        n = len(verts)
        for k in range(n):
            for i in range(n):
                if i != k and closures.get_edge(
                        verts[i], verts[k]) is not None:
                    for j in range(n):
                        if i != j != k and closures.get_edge(
                                verts[k], verts[j]) is not None:
                            if closures.get_edge(verts[i], verts[j]) is None:
                                closures.insert_edge(verts[i], verts[j])
        return closures

    # DAG 有向非循环图，拓扑排序
    def topologicial_sort(self, g):
        topo = []
        ready = []
        incount = {}
        for u in g.vertices():
            incount[u] = g.degree(u, False)
            if incount[u] == 0:
                ready.append(u)
            while len(ready) > 0:
                u = ready.pop()
                topo.append(u)
                for e in g.incident_edges(u):
                    v = e.opposite(u)
                    incount[v] -= 1
                    if incount[v] == 0:
                        ready.append(v)
        return

    # Dijkstra算法单源计算最短距离实现
    def shortest_path_learning(self, g, src):
        d = {}
        cloud = {}
        pq = PriorityQueues.AdaptablePriorityQueue()
        pqlocator = {}

        for v in g.vertices():
            if v is src:
                d[v] = 0
            else:
                d[v] = float('inf')
            pqlocator[v] = pq.add(d[v], v)

        while not pq.is_empty():
            key, u = pq.remove_min()
            cloud[u] = key
            del pqlocator[u]
            for e in g.incident_edges(u):
                v = e.opposite(u)
                if v not in cloud:
                    wgt = e.element()
                    if d[u] + wgt < d[v]:
                        d[v] = d[u] + wgt
                        pq.update(pqlocator[v], d[v], v)
