package pixel;
/*
 * Created by ivanovcinnikov on 04.01.17.
 */

class GameOver {
    private static boolean over;

    boolean getGameOver(){
        return over;
    }

    void setGameOver(boolean b){
        over = b;
    }

    GameOver() { }
}
