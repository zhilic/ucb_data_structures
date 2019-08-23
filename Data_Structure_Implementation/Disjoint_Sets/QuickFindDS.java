/** 
 *  For nodes in the same set,
 *  set the values at their positions the same as well.
 */
public class QuickFindDS implements DisjointSets {
	private int[] id;

	/** Initialize the index array */
	public QuickFindDS(int N) {
		id = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
		}
	}

	@Override
	public void connect(int p, int q) {
		int pid = id[p];
		int qid = id[q];
		for (int i = 0; i < id.length; i++) {
			/** Change all nodes whose values are pid to qid. */
			if (id[i] == pid) {
				id[i] = qid;
			}
		}
	}

	@Override
	public boolean isConnected(int p, int q) {
		return id[p] == id[q];
	}
}