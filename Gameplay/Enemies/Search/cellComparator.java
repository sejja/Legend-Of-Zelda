package Gameplay.Enemies.Search;

import java.util.Comparator;

class cellComparator implements Comparator<pPair> {
    public int compare(pPair p1, pPair p2) {
        if (p1.first < p2.first)
            return -1;
        if (p1.first > p2.first)
            return 1;
        return 0;
    }
}