/** Set the value at position p the same as p's parent node. */
public class QuickUnionDS implements DisjointSets {
	private int[] parent;

	/** Initialize the index array. The parent of each node is itself. */
	public QuickUnionDS(int N) {
		parent = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
		}
	}

	/** Find the parent of p. */
	public int find(int p) {
		while (p != parent[p]) {
			p = parent[p];
		}
		return p;
	}

	@Override
	public void connect(int p, int q) {
		int i = find(p);
		int j = find(q);
		parent[i] = j;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}
}