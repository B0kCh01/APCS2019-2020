/*======== // Nathan Choi // =========|
|  Last updated: 4.20.20              |
|                                     |
|  About this file:                   |
|    * APCS 3rd Period                |
|    * EmptyCell.java                 |
|    * Acts as an empty Cell          |
|-------------------------------------*/
package textExcel;

public class EmptyCell implements Cell{
    @Override
    public String abbreviatedCellText() { return "          "; }
    @Override
    public String fullCellText() { return ""; }
}
