package pixel;
/*
 * Created by ivanovcinnikov on 04.01.17.
 */

class Field {
    private byte[][] f;
    private int h;
    private int w;

    byte[][] getField() { return this.f; }
    int getHeight() { return this.h; }
    int getWidth() { return this.w; }
    void setCell(int i, int j, byte col) { this.f[i][j] = col; }
    byte getCellColor(int i, int j){ return this.f[i][j]; }

    void init(int level){
        for (int i = 0; i < f.length; i++) {
            for (int j = 0; j < f[i].length; j++) {
                f[i][j] = (byte)(Math.round(Math.random() * level));
            }
        }
    }

    Field(int h, int w){
        this.h = h;
        this.w = w;
        this.f = new byte[h][w];
    }
}
