Trie
====

A trie implementation in Java.

About
-----

A **trie** is an ordered tree data structure that is used to
store an associative array where the keys are usually strings. Unlike
a binary search tree, no node in the tree stores the key associated
with that node; instead, its position in the tree shows what key
it is associated with. All the descendants of a node have a common
prefix of the string associated with that node, and the root is
associated with the empty string. Values are normally not associated
with every node, only with leaves and some inner nodes that correspond
to keys of interest.

Usage
-----

You can use this library to represent dictionaries, e.g. **morse code**:

    TrieMap<String> morse = new TrieMap<String>();
    morse.put("/", " ");
    morse.put(".-", "A");
    morse.put("-...", "B");
    ...
    
    String message = ".... . .-.. .-.. --- / .-- --- .-. .-.. -..";
    for (String letter : message.trim().split("\\s+"))
        System.out.print(morse.get(letter));
    System.out.println();