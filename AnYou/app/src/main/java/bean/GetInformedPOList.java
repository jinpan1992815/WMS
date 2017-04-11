package bean;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
public class GetInformedPOList {
    /**
     * DocDate : 2016.12.20
     * DocEntry : 2
     * DocNum : 2
     * CardName : 山西省祁县盐业公司
     * CarNumber : 鲁A-656AB
     * OrderAmount : 5500
     * PlanQuantity : 1050
     * status : 0
     */

    private String DocDate;
    private int DocEntry;
    private String DocNum;
    private String CardName;
    private String CarNumber;
    private String OrderAmount;
    private String PlanQuantity;
    private String status;

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

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

    public String getCarNumber() {
        return CarNumber;
    }

    public void setCarNumber(String CarNumber) {
        this.CarNumber = CarNumber;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String OrderAmount) {
        this.OrderAmount = OrderAmount;
    }

    public String getPlanQuantity() {
        return PlanQuantity;
    }

    public void setPlanQuantity(String PlanQuantity) {
        this.PlanQuantity = PlanQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
