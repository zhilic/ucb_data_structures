public interface DisjointSets {
	/** Connects two items p and q. */
	void connect(int p, int q);

	/** Checks to see if two items are connected. */
	boolean isConnected(int p, int q);
}