package bean;

/**
 * Created by Administrator on 2017/1/4 0004.
 */
public class GetOpenProducitonOrderList {

    /**
     * DocDate : 2016.12.30
     * DocEntry : 42
     * DocNum : 42
     * ItemName : 40%仔猪前期浓缩料4011
     * PlannedQty : 7000
     * OpenQty : 7000
     */

    private String DocDate;
    private int DocEntry;
    private String DocNum;
    private String ItemName;
    private double PlannedQty;
    private double OpenQty;

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String DocDate) {
        this.DocDate = DocDate;
    }

    public int getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(int DocEntry) {
        this.DocEntry = DocEntry;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String DocNum) {
        this.DocNum = DocNum;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public double getPlannedQty() {
        return PlannedQty;
    }

    public void setPlannedQty(int PlannedQty) {
        this.PlannedQty = PlannedQty;
    }

    public double getOpenQty() {
        return OpenQty;
    }

    public void setOpenQty(int OpenQty) {
        this.OpenQty = OpenQty;
    }
}
