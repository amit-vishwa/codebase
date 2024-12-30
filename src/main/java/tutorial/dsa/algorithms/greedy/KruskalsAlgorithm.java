package tutorial.dsa.algorithms.greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Refer: https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
 *
 * Kruskal's Algorithm:	Finds the minimum spanning tree of a weighted graph.
 *
 * A minimum spanning tree (MST) or minimum weight spanning tree for a weighted, connected, undirected graph is a spanning
 * tree with a weight less than or equal to the weight of every other spanning tree. To learn more about Minimum Spanning
 * Tree, refer to this article.
 *
 * Introduction to Kruskal’s Algorithm:
 * Here we will discuss Kruskal’s algorithm to find the MST of a given weighted graph.
 * In Kruskal’s algorithm, sort all edges of the given graph in increasing order. Then it keeps on adding new edges and
 * nodes in the MST if the newly added edge does not form a cycle. It picks the minimum weighted edge at first and the
 * maximum weighted edge at last. Thus we can say that it makes a locally optimal choice in each step in order to find the
 * optimal solution. Hence this is a Greedy Algorithm.
 *
 * How to find MST using Kruskal’s algorithm?
 * Below are the steps for finding MST using Kruskal’s algorithm:
 * Sort all the edges in non-decreasing order of their weight.
 * Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If the cycle is not formed,
 * include this edge. Else, discard it.
 * Repeat step#2 until there are (V-1) edges in the spanning tree.
 * Step 2 uses the Union-Find algorithm to detect cycles.
 * So we recommend reading the following post as a prerequisite.
 * Union-Find Algorithm | Set 1 (Detect Cycle in a Graph)
 * Union-Find Algorithm | Set 2 (Union By Rank and Path Compression)
 *
 * Kruskal’s algorithm to find the minimum cost spanning tree uses the greedy approach. The Greedy Choice is to pick
 * the smallest weight edge that does not cause a cycle in the MST constructed so far.
 *
 * Output
 * Following are the edges in the constructed MST
 * 2 -- 3 == 4
 * 0 -- 3 == 5
 * 0 -- 1 == 10
 * Minimum Cost Spanning Tree: 19
 *
 * Time Complexity: O(E * logE) or O(E * logV)
 * Sorting of edges takes O(E * logE) time.
 * After sorting, we iterate through all edges and apply the find-union algorithm. The find and union operations can take at most O(logV) time.
 * So overall complexity is O(E * logE + E * logV) time.
 * The value of E can be at most O(V2), so O(logV) and O(logE) are the same. Therefore, the overall time complexity is O(E * logE) or O(E*logV)
 *
 * Auxiliary Space: O(V + E), where V is the number of vertices and E is the number of edges in the graph.
 * */
public class KruskalsAlgorithm {

    // Starting point of program execution
    public static void main(String[] args)
    {
        int V = 4;
        List<Edge> graphEdges = new ArrayList<Edge>(
                List.of(new Edge(0, 1, 10), // included
                        new Edge(0, 2, 6),
                        new Edge(0, 3, 5), // included
                        new Edge(1, 3, 15),
                        new Edge(2, 3, 4))); // included

        // Sort the edges in non-decreasing order
        // (increasing with repetition allowed)
        graphEdges.sort(new Comparator<Edge>() {
            @Override public int compare(Edge o1, Edge o2)
            {
                return o1.weight - o2.weight;
            }
        });

        kruskals(V, graphEdges);
    }

    // Defines edge structure
    static class Edge {
        int src, dest, weight;

        public Edge(int src, int dest, int weight)
        {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    // Defines subset element structure
    static class Subset {
        int parent, rank;

        public Subset(int parent, int rank)
        {
            this.parent = parent;
            this.rank = rank;
        }
    }

    // Function to find the MST
    private static void kruskals(int V, List<Edge> edges)
    {
        int j = 0;
        int noOfEdges = 0;

        // Allocate memory for creating V subsets
        Subset subsets[] = new Subset[V];

        // Allocate memory for results
        Edge results[] = new Edge[V];

        // Create V subsets with single elements
        for (int i = 0; i < V; i++) {
            subsets[i] = new Subset(i, 0);
        }

        // Number of edges to be taken is equal to V-1
        while (noOfEdges < V - 1) {

            // Pick the smallest edge. And increment
            // the index for next iteration
            Edge nextEdge = edges.get(j);
            int x = findRoot(subsets, nextEdge.src);
            int y = findRoot(subsets, nextEdge.dest);

            // If including this edge doesn't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                results[noOfEdges] = nextEdge;
                union(subsets, x, y);
                noOfEdges++;
            }

            j++;
        }

        // Print the contents of result[] to display the
        // built MST
        System.out.println(
                "Following are the edges of the constructed MST:");
        int minCost = 0;
        for (int i = 0; i < noOfEdges; i++) {
            System.out.println(results[i].src + " -- "
                    + results[i].dest + " == "
                    + results[i].weight);
            minCost += results[i].weight;
        }
        System.out.println("Total cost of MST: " + minCost);
    }

    // Function to unite two disjoint sets
    private static void union(Subset[] subsets, int x,
                              int y)
    {
        int rootX = findRoot(subsets, x);
        int rootY = findRoot(subsets, y);

        if (subsets[rootY].rank < subsets[rootX].rank) {
            subsets[rootY].parent = rootX;
        }
        else if (subsets[rootX].rank
                < subsets[rootY].rank) {
            subsets[rootX].parent = rootY;
        }
        else {
            subsets[rootY].parent = rootX;
            subsets[rootX].rank++;
        }
    }

    // Function to find parent of a set
    private static int findRoot(Subset[] subsets, int i)
    {
        if (subsets[i].parent == i)
            return subsets[i].parent;

        subsets[i].parent
                = findRoot(subsets, subsets[i].parent);
        return subsets[i].parent;
    }
}
