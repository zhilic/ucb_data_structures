public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        deque = wordToDeque(word);
        return isPalindromeDeque(deque);
    }

    /* new method that overloads isPalindrome */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = new LinkedListDeque<>();
        deque = wordToDeque(word);
        return isPalindromeDeque(deque, cc);
    }

    /* helper method for isPalindrome */
    private boolean isPalindromeDeque(Deque deque) {
        if (deque.size() == 0 || deque.size() == 1) {
            return true;
        }
        return deque.removeFirst() == deque.removeLast() && isPalindromeDeque(deque);
    }

    private boolean isPalindromeDeque(Deque deque, CharacterComparator cc) {
        if (deque.size() == 0 || deque.size() == 1) {
            return true;
        }
        return cc.equalChars((char) deque.removeFirst(), (char) deque.removeLast())
                && isPalindromeDeque(deque, cc);
    }
}
