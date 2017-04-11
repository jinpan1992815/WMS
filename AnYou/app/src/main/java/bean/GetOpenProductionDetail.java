package bean;

/**
 * Created by Administrator on 2017/1/4 0004.
 */
public class GetOpenProductionDetail {

    /**
     * DocDate : 2017.01.04
     * DocEntry : 41
     * DocNum : 41
     * ItemCode : ZNF404011NAY05
     * ItemName : 40%仔猪前期浓缩料4011
     * BagAmount : 5000
     * Amount : 5000
     * WhsCode : 401
     * BatchNumber : 2017010401
     * Factor2 : 1
     */

    private String DocDate;
    private int DocEntry;
    private String DocNum;
    private String ItemCode;
    private String ItemName;
    private double BagAmount;
    private double Amount;
    private String WhsCode;
    private String BatchNumber;
    private double Factor1;

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

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String ItemName) {
        this.ItemName = ItemName;
    }

    public double getBagAmount() {
        return BagAmount;
    }

    public void setBagAmount(double BagAmount) {
        this.BagAmount = BagAmount;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String WhsCode) {
        this.WhsCode = WhsCode;
    }

    public String getBatchNumber() {
        return BatchNumber;
    }

    public void setBatchNumber(String BatchNumber) {
        this.BatchNumber = BatchNumber;
    }

    public double getFactor1() {
        return Factor1;
    }

    public void setFactor1(double Factor1) {
        this.Factor1 = Factor1;
    }
}
