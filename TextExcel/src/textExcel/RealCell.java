/*======== // Nathan Choi // =========|
|  Last updated: 4.20.20              |
|                                     |
|  About this file:                   |
|    * APCS 3rd Period                |
|    * RealCell.java                  |
|    * Numerical Cell [42, %, ()]     |
|-------------------------------------*/
package textExcel;

public class RealCell implements Cell {
    private String value;

    // Takes doubles or integers
    public RealCell(int input) {
        value = input + "";
    }
    public RealCell(double input) {
        value = input + "";
    }

    @Override
    public String abbreviatedCellText() {
        return (getDouble() + "          ").substring(0, 10);
    }
    @Override
    public String fullCellText() {
        return value;
    }
    public double getDouble() {
        return Double.parseDouble(value);
    }
}
