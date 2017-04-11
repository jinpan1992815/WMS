package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public class SaveLine {
    /**
     * ItemCode : AN00021
     * Amount : 3.0
     * BagAmount : 1.0
     * Factor2 : 1.0
     * DocEntry : 2
     * LineNum : 0
     * Batches : [{"BatchNumber":"2016051301","Quantity":3}]
     */

    private String ItemCode;
    private double Amount;
    private double BagAmount;
    private double Factor2;
    private int DocEntry;
    private int LineNum;
    /**
     * BatchNumber : 2016051301
     * Quantity : 3.0
     */

    private List<Batchesbean> Batches;

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public double getBagAmount() {
        return BagAmount;
    }

    public void setBagAmount(double BagAmount) {
        this.BagAmount = BagAmount;
    }

    public double getFactor2() {
        return Factor2;
    }

    public void setFactor2(double Factor2) {
        this.Factor2 = Factor2;
    }

    public int getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(int DocEntry) {
        this.DocEntry = DocEntry;
    }

    public int getLineNum() {
        return LineNum;
    }

    public void setLineNum(int LineNum) {
        this.LineNum = LineNum;
    }

    public List<Batchesbean> getBatches() {
        return Batches;
    }

    public void setBatches(List<Batchesbean> Batches) {
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
