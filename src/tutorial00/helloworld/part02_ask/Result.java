/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial00.helloworld.part02_ask;

/**
 *
 * @author Christian Poliwoda <christian.poliwoda@gcsc.uni-frankfurt.de>
 */
class Result {

    private int x;
    private String s;

    Result(int x, String s) {
        this.x = x;
        this.s = s;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the s
     */
    public String getS() {
        return s;
    }

    @Override
    public String toString() {
        return "x = " + getX() + " , s = " + getS();
    }
}
