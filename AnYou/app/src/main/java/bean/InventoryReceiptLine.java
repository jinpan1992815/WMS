package bean;

/**
 * Created by Administrator on 2017/1/19 0019.
 */
public class InventoryReceiptLine {
    /**
     * ItemCode : ZQK400911KAY03
     * ItemName : 赖氨酸Lysine SO4-70 (LYS 55.3)
     * Quantity : 20
     * BagAmount : 2
     * Price : 2
     * IfBatches : Y
     * BatchNumber : A00A17011601
     */

    private String ItemCode;
    private String ItemName;
    private double Quantity;
    private double Price;
    private String IfBatches;
    private String BatchNumber;

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

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getIfBatches() {
        return IfBatches;
    }

    public void setIfBatches(String IfBatches) {
        this.IfBatches = IfBatches;
    }

    public String getBatchNumber() {
        return BatchNumber;
    }

    public void setBatchNumber(String BatchNumber) {
        this.BatchNumber = BatchNumber;
    }
}
