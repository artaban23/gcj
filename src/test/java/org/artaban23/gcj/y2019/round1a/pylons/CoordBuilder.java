package org.artaban23.gcj.y2019.round1a.pylons;

import org.artaban23.gcj.y2019.round1a.pylons.Solution;

class CoordBuilder {

    private int rowCount;
    private int colCount;

    CoordBuilder(int rowCount, int colCount) {
        this.rowCount = rowCount;
        this.colCount = colCount;
    }

    Solution.Coord build(int r, int c) {
        return new Solution.Coord(rowCount, colCount, r, c);
    }

    Solution.Cache buildCache() {
        return new Solution.Cache(rowCount, colCount);
    }
}
