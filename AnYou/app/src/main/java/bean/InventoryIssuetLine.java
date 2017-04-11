package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/20 0020.
 */
public class InventoryIssuetLine {
    /**
     * ItemCode : ZQK400911KAY03
     * ItemName : 赖氨酸Lysine SO4-70 (LYS 55.3)
     * Quantity : 20
     * BagAmount : 2
     * Price : 2
     * Batches : [{"BatchNumber":"A00A17011601","Quantity":20}]
     */

    private String ItemCode;
    private String ItemName;
    private double Quantity;
    private double BagAmount;
    private double Price;
    /**
     * BatchNumber : A00A17011601
     * Quantity : 20
     */

    private List<Batchesbean> Batches;

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

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(double Quantity) {
        this.Quantity = Quantity;
    }

    public double getBagAmount() {
        return BagAmount;
    }

    public void setBagAmount(double BagAmount) {
        this.BagAmount = BagAmount;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public List<Batchesbean> getBatches() {
        return Batches;
    }

    public void setBatches(List<Batchesbean> Batches) {
        this.Batches = Batches;
    }

    public class Batchesbean {
        private String BatchNumber;
        private double Quantity;
        private double Amount;
        private String WhsCode;
        private String ItemCode;

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

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }
    }
}
