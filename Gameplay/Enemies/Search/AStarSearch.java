package Gameplay.Enemies.Search;
import java.util.*;
import Engine.Graphics.Tile.*;


public class AStarSearch{
    static Stack<Pair> Path = new Stack<>();
    static final int COL = 50;
    static final int ROW = 50;


    // A Utility Function to check whether given cell (Column, Row)
    // is a valid cell or not.
    public boolean isValid(int Column, int Row) {
        // Returns true if Column number and column number
        // is in range
        return (Column >= 0) && (Column < COL) && (Row >= 0) && (Row < ROW);
    }

    // A Utility Function to check whether the given cell is
    // blocked or not
    public boolean isUnBlocked(int Column, int Row) {
        // Returns true if the cell is not blocked else false
        if (isValid(Column, Row)){
            Block block = TileManager.sLevelObjects.GetBlockAt(Column, Row);
            //System.out.println(Column + " " + Row);
            if(block instanceof Normblock) return true;
            if(block instanceof HoleBlock || block instanceof ObjectBlock) return false;
            return true;
        }else{
            //System.out.println(Column + " " + Row);
            return false;
        }

    }

    // A Utility Function to check whether destination cell has
    // been reached or not
    public boolean isDestination(int Column, int Row, Pair dest) {
        return (Column == dest.first && Row == dest.second);
    }

    // A Utility Function to calculate the 'h' heuristics.
    public double calculateHValue(int Column, int Row, Pair dest) {
        return Math.sqrt((Column - dest.first) * (Column - dest.first) + (Row - dest.second) * (Row - dest.second));
    }


    // A Function to find the shortest path between
    // a given source cell to a destination cell according
    // to A* Search Algorithm
    public Stack<Pair> aStarSearch( Pair src, Pair dest) {
        if (!isValid(src.first, src.second)) {
            //System.out.println("Source is invalid");
            return Path;
        }

        if (!isValid(dest.first, dest.second)) {
            //System.out.println("Destination is invalid");
            return Path;
        }

        if (!isUnBlocked( src.first, src.second) || !isUnBlocked( dest.first, dest.second)) {
            //System.out.println("Source or the destination is blocked");
            return Path;
        }

        if (isDestination(src.first, src.second, dest)) {
            //System.out.println("We are already at the destination");
            return Path;
        }

        boolean[][] closedList = new boolean[COL][ROW];
        Gameplay.Enemies.Search.cell[][] cellDetails = new Gameplay.Enemies.Search.cell[COL][ROW];

        int i, j;
        for (i = 0; i < COL; i++) {
            for (j = 0; j < ROW; j++) {
                cellDetails[i][j] = new cell();
                cellDetails[i][j].f = Double.MAX_VALUE;
                cellDetails[i][j].g = Double.MAX_VALUE;
                cellDetails[i][j].h = Double.MAX_VALUE;
                cellDetails[i][j].parent_i = -1;
                cellDetails[i][j].parent_j = -1;
            }
        }

        i = src.first;
        j = src.second;
        cellDetails[i][j].f = 0.0;
        cellDetails[i][j].g = 0.0;
        cellDetails[i][j].h = 0.0;
        cellDetails[i][j].parent_i = i;
        cellDetails[i][j].parent_j = j;

        TreeSet<pPair> openList = new TreeSet<>((o1, o2) -> {
            if (o1.first == o2.first) {
                Pair p1 = o1.second;
                Pair p2 = o2.second;
                return Integer.compare(p1.first * ROW + p1.second, p2.first * ROW + p2.second);
            }
            return Double.compare(o1.first, o2.first);
        });

        openList.add(new pPair(0.0, new Pair(i, j)));

        boolean foundDest = false;

        while (!openList.isEmpty()) {
            pPair p = openList.first();
            openList.remove(p);

            i = p.second.first;
            j = p.second.second;
            closedList[i][j] = true;

            double gNew, hNew, fNew;

            // 1st Successor (North)
            if (isValid(i - 1, j)) {
                if (isDestination(i - 1, j, dest)) {
                    cellDetails[i - 1][j].parent_i = i;
                    cellDetails[i - 1][j].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i - 1][j] && isUnBlocked( i - 1, j)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i - 1, j, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i - 1][j].f == Double.MAX_VALUE || cellDetails[i - 1][j].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i - 1, j)));
                        cellDetails[i - 1][j].f = fNew;
                        cellDetails[i - 1][j].g = gNew;
                        cellDetails[i - 1][j].h = hNew;
                        cellDetails[i - 1][j].parent_i = i;
                        cellDetails[i - 1][j].parent_j = j;
                    }
                }
            }

            // 2nd Successor (South)
            if (isValid(i + 1, j)) {
                if (isDestination(i + 1, j, dest)) {
                    cellDetails[i + 1][j].parent_i = i;
                    cellDetails[i + 1][j].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i + 1][j] && isUnBlocked( i + 1, j)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i + 1, j, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i + 1][j].f == Double.MAX_VALUE || cellDetails[i + 1][j].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i + 1, j)));
                        cellDetails[i + 1][j].f = fNew;
                        cellDetails[i + 1][j].g = gNew;
                        cellDetails[i + 1][j].h = hNew;
                        cellDetails[i + 1][j].parent_i = i;
                        cellDetails[i + 1][j].parent_j = j;
                    }
                }
            }

            // 3rd Successor (East)
            if (isValid(i, j + 1)) {
                if (isDestination(i, j + 1, dest)) {
                    cellDetails[i][j + 1].parent_i = i;
                    cellDetails[i][j + 1].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i][j + 1] && isUnBlocked( i, j + 1)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i, j + 1, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i][j + 1].f == Double.MAX_VALUE || cellDetails[i][j + 1].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i, j + 1)));
                        cellDetails[i][j + 1].f = fNew;
                        cellDetails[i][j + 1].g = gNew;
                        cellDetails[i][j + 1].h = hNew;
                        cellDetails[i][j + 1].parent_i = i;
                        cellDetails[i][j + 1].parent_j = j;
                    }
                }
            }

            // 4th Successor (West)
            if (isValid(i, j - 1)) {
                if (isDestination(i, j - 1, dest)) {
                    cellDetails[i][j - 1].parent_i = i;
                    cellDetails[i][j - 1].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i][j - 1] && isUnBlocked( i, j - 1)) {
                    gNew = cellDetails[i][j].g + 1.0;
                    hNew = calculateHValue(i, j - 1, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i][j - 1].f == Double.MAX_VALUE || cellDetails[i][j - 1].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i, j - 1)));
                        cellDetails[i][j - 1].f = fNew;
                        cellDetails[i][j - 1].g = gNew;
                        cellDetails[i][j - 1].h = hNew;
                        cellDetails[i][j - 1].parent_i = i;
                        cellDetails[i][j - 1].parent_j = j;
                    }
                }
            }

            // 5th Successor (North-East)
            if (isValid(i - 1, j + 1) && (isUnBlocked( i - 1, j) && isUnBlocked( i, j + 1))) {
                if (isDestination(i - 1, j + 1, dest)) {
                    cellDetails[i - 1][j + 1].parent_i = i;
                    cellDetails[i - 1][j + 1].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i - 1][j + 1] && isUnBlocked( i - 1, j + 1)) {
                    gNew = cellDetails[i][j].g + 1.414;
                    hNew = calculateHValue(i - 1, j + 1, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i - 1][j + 1].f == Double.MAX_VALUE || cellDetails[i - 1][j + 1].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i - 1, j + 1)));
                        cellDetails[i - 1][j + 1].f = fNew;
                        cellDetails[i - 1][j + 1].g = gNew;
                        cellDetails[i - 1][j + 1].h = hNew;
                        cellDetails[i - 1][j + 1].parent_i = i;
                        cellDetails[i - 1][j + 1].parent_j = j;
                    }
                }
            }

            // 6th Successor (North-West)
            if (isValid(i - 1, j - 1) && (isUnBlocked( i - 1, j) && isUnBlocked( i, j - 1))) {
                if (isDestination(i - 1, j - 1, dest)) {
                    cellDetails[i - 1][j - 1].parent_i = i;
                    cellDetails[i - 1][j - 1].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i - 1][j - 1] && isUnBlocked( i - 1, j - 1)) {
                    gNew = cellDetails[i][j].g + 1.414;
                    hNew = calculateHValue(i - 1, j - 1, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i - 1][j - 1].f == Double.MAX_VALUE || cellDetails[i - 1][j - 1].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i - 1, j - 1)));
                        cellDetails[i - 1][j - 1].f = fNew;
                        cellDetails[i - 1][j - 1].g = gNew;
                        cellDetails[i - 1][j - 1].h = hNew;
                        cellDetails[i - 1][j - 1].parent_i = i;
                        cellDetails[i - 1][j - 1].parent_j = j;
                    }
                }
            }

            // 7th Successor (South-East)
            if (isValid(i + 1, j + 1) && (isUnBlocked( i + 1, j) && isUnBlocked( i, j + 1))) {
                if (isDestination(i + 1, j + 1, dest)) {
                    cellDetails[i + 1][j + 1].parent_i = i;
                    cellDetails[i + 1][j + 1].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i + 1][j + 1] && isUnBlocked( i + 1, j + 1)) {
                    gNew = cellDetails[i][j].g + 1.414;
                    hNew = calculateHValue(i + 1, j + 1, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i + 1][j + 1].f == Double.MAX_VALUE || cellDetails[i + 1][j + 1].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i + 1, j + 1)));
                        cellDetails[i + 1][j + 1].f = fNew;
                        cellDetails[i + 1][j + 1].g = gNew;
                        cellDetails[i + 1][j + 1].h = hNew;
                        cellDetails[i + 1][j + 1].parent_i = i;
                        cellDetails[i + 1][j + 1].parent_j = j;
                    }
                }
            }

            // 8th Successor (South-West)
            if (isValid(i + 1, j - 1) && (isUnBlocked( i + 1, j) && isUnBlocked( i, j - 1))) {
                if (isDestination(i + 1, j - 1, dest)) {
                    cellDetails[i + 1][j - 1].parent_i = i;
                    cellDetails[i + 1][j - 1].parent_j = j;
                    //System.out.println("The destination cell is found");
                    tracePath(cellDetails, dest);
                    foundDest = true;
                    return Path;
                } else if (!closedList[i + 1][j - 1] && isUnBlocked( i + 1, j - 1)) {
                    gNew = cellDetails[i][j].g + 1.414;
                    hNew = calculateHValue(i + 1, j - 1, dest);
                    fNew = gNew + hNew;
                    if (cellDetails[i + 1][j - 1].f == Double.MAX_VALUE || cellDetails[i + 1][j - 1].f > fNew) {
                        openList.add(new pPair(fNew, new Pair(i + 1, j - 1)));
                        cellDetails[i + 1][j - 1].f = fNew;
                        cellDetails[i + 1][j - 1].g = gNew;
                        cellDetails[i + 1][j - 1].h = hNew;
                        cellDetails[i + 1][j - 1].parent_i = i;
                        cellDetails[i + 1][j - 1].parent_j = j;
                    }
                }
            }
        }

        // When no destination cell is found and the open list is empty, then no path exists.
        if (!foundDest){
            //System.out.println("Failed to find the destination cell");
        }
        return Path;
    }

    // A utility function to trace the path from the source to the destination.
    static void tracePath(cell cellDetails[][], Pair dest) {
        int Column = dest.first;
        int Row = dest.second;

        Path = new Stack<Pair>();

        while (!(cellDetails[Column][Row].parent_i == Column && cellDetails[Column][Row].parent_j == Row)) {
            Path.push(new Pair(Column, Row));
            int temp_row = cellDetails[Column][Row].parent_i;
            int temp_col = cellDetails[Column][Row].parent_j;
            Column = temp_row;
            Row = temp_col;
        }

        /*while (!Path.isEmpty()) {
            Pair p = Path.pop();
            System.out.print("-> (" + p.first + ", " + p.second + ") ");
        }*/
        return;
    }
}



