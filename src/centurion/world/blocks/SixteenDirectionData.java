package centurion.world.blocks;

import arc.util.io.*;

/**
 * A pure data container for 16-direction values。
 * Store a rotation value from 0-15, with support for serialization and common queries。
 * Note: In the new architecture, the Building of SixteenDirectionBlock no longer uses this class，
 * Instead, the complete 16-direction values are passed directly through the config/configured mechanism (full pipeline)。
 * This is reserved for old code or special uses that need to store 16-way data independently。
 */
public class SixteenDirectionData {

    public static final int DIRECTIONS = 16;
    public static final float DEG_PER_DIR = 360f / DIRECTIONS;

    protected int rotation = 0;

    public SixteenDirectionData() {}

    public SixteenDirectionData(int rotation) {
        this.rotation = normalize(rotation);
    }

    public int get()             { return rotation; }
    public void set(int rot)     { this.rotation = normalize(rot); }
    public float deg()           { return rotation * DEG_PER_DIR; }
    public float rad()           { return (float) Math.toRadians(deg()); }
    public boolean isCardinal()  { return rotation % 4 == 0; }
    public int toCardinal()      { return isCardinal() ? rotation / 4 : -1; }
    public void fromCardinal(int c) { rotation = normalize(c * 4); }
    public void rotate(int steps)   { rotation = normalize(rotation + steps); }
    public void flip()              { rotate(DIRECTIONS / 2); }

    private int normalize(int r) {
        r %= DIRECTIONS;
        if (r < 0) r += DIRECTIONS;
        return r;
    }

    public void write(Writes w) { w.s(rotation); }
    public void read(Reads r)   { rotation = r.s(); }

    @Override
    public boolean equals(Object o) {
        return this == o || (o instanceof SixteenDirectionData d && rotation == d.rotation);
    }

    @Override
    public int hashCode() { return rotation; }

    @Override
    public String toString() {
        return "SixteenDirectionData{rot=" + rotation + ", deg=" + deg() + "}";
    }
}
