public class OffByN implements CharacterComparator {

    private int n;

    public OffByN(int i) {
        n = i;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == n || x - y == -n;
    }

}
