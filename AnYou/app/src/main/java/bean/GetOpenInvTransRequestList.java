package bean;

/**
 * Created by Administrator on 2017/1/4 0004.
 */
public class GetOpenInvTransRequestList {

    /**
     * DocDate : 2016.12.30
     * DocEntry : 2
     * DocNum : 2
     * Filler : 原料良品仓
     * Quantity : 40
     */

    private String DocDate;
    private int DocEntry;
    private String DocNum;
    private String Filler;
    private double Quantity;

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

    public String getFiller() {
        return Filler;
    }

    public void setFiller(String Filler) {
        this.Filler = Filler;
    }

    public double getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
}
