package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class BuyReturnLine {
    /**
     * ItemCode : AN00011
     * ItemName : 赖氨酸 L-Lys HCL 98.5 (LYS 77.5)
     * BagAmount : 10
     * Amount : 950
     * Factor1 : 0
     * Factor2 : 1
     * WhsCode : 105
     * PriceAfVat : 2.5
     * LineGross : 2500
     * BaseEntry : 206
     * BaseLineNum : 0
     * Batches : [{"BatchNumber":"2016121502","Quantity":950}]
     */

    private String ItemCode;
    private String ItemName;
    private double BagAmount;
    private double Amount;
    private double Factor1;
    private double Factor2;
    private String WhsCode;
    private double PriceAfVat;
    private int LineGross;
    private int BaseEntry;
    private int BaseLineNum;
    /**
     * BatchNumber : 2016121502
     * Quantity : 950
     */

    private List<GetPOReturnInfo.ItemListbean.WhsListbean.Batchesbean> Batches;

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

    public double getFactor1() {
        return Factor1;
    }

    public void setFactor1(double Factor1) {
        this.Factor1 = Factor1;
    }

    public double getFactor2() {
        return Factor2;
    }

    public void setFactor2(double Factor2) {
        this.Factor2 = Factor2;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String WhsCode) {
        this.WhsCode = WhsCode;
    }

    public double getPriceAfVat() {
        return PriceAfVat;
    }

    public void setPriceAfVat(double PriceAfVat) {
        this.PriceAfVat = PriceAfVat;
    }

    public int getLineGross() {
        return LineGross;
    }

    public void setLineGross(int LineGross) {
        this.LineGross = LineGross;
    }

    public int getBaseEntry() {
        return BaseEntry;
    }

    public void setBaseEntry(int BaseEntry) {
        this.BaseEntry = BaseEntry;
    }

    public int getBaseLineNum() {
        return BaseLineNum;
    }

    public void setBaseLineNum(int BaseLineNum) {
        this.BaseLineNum = BaseLineNum;
    }

    public List<GetPOReturnInfo.ItemListbean.WhsListbean.Batchesbean> getBatches() {
        return Batches;
    }

    public void setBatches(List<GetPOReturnInfo.ItemListbean.WhsListbean.Batchesbean> Batches) {
        this.Batches = Batches;
    }

    public static class Batchesbean {
        private String BatchNumber;
        private double Quantity;

        public String getBatchNumber() {
            return BatchNumber;
        }

        public void setBatchNumber(String BatchNumber) {
            this.BatchNumber = BatchNumber;
        }

        public double getQuantity() {
            return Quantity;
        }

        public void setQuantity(double Quantity) {
            this.Quantity = Quantity;
        }
    }
}
