/** 
 *  Always link the root of smaller tree to larger tree.
 *  When we try to find the root of a specific node, tie all nodes seen to the root.
 */
public class WeightedQuickUnionDSWithPathCompression implements DisjointSets {
	private int[] parent;
	private int[] size;

	/** 
	 *  Initialize the index array.
	 *  The parent of each node is itself, and the size is 1.
	 */
	public WeightedQuickUnionDSWithPathCompression(int N) {
		parent = new int[N];
		size = new int[N];
		for (int i = 0; i < N; i++) {
			parent[i] = i;
			size[i] = 1;
		}
	}

	/** 
	 *  Find the parent of p, 
	 *  and change the parent of every node seen to the root.
	 */
	public int find(int p) {
		if (p == parent[p]) {
			return p;
		} else {
			parent[p] = find(parent[p]);
			return parent[p];
		}
	}

	@Override
	public void connect(int p, int q) {
		int i = find(p);
		int j = find(q);
		if (i == j) return;
		if (size[i] < size[j]) {
			parent[i] = parent[j];
			size[j] += size[i];
		} else {
			parent[j] = parent[i];
			size[i] += size[j];
		}
	}

	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}
}