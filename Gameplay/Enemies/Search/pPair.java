package Gameplay.Enemies.Search;

public class pPair implements Comparable<pPair>{
    double first;
    Pair second;

    public pPair(double first, Pair second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(pPair o) {
        if (this.first < o.first)
            return -1;
        if (this.first > o.first)
            return 1;
        return 0;
    }
    
}
