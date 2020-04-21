/*======== // Nathan Choi // =========|
|  Last updated: 4.20.20              |
|                                     |
|  About this file:                   |
|    * APCS 3rd Period                |
|    * PercentCell.java               |
|    * Stores real percent value      |
|------------------------------------*/
package textExcel;

public class PercentCell extends RealCell {
    public PercentCell(double input) {
        // Convert fraction to decimal
        super(input / 100);
    }
    @Override
    public String abbreviatedCellText() {
        // Convert decimal to percent
        String stringValue = getDouble() * 100 + "";
        String output =  stringValue.substring(0, stringValue.indexOf('.')) + "%";

        return (output + "          ").substring(0, 10);
    }
}
