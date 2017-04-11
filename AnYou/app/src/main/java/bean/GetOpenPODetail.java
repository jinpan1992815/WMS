package bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/14 0014.
 */
public class GetOpenPODetail {//GetInformedPODetail 和 GetOpenPODetail类型一样,共用


    private String CardCode;
    private String CardName;
    private String DocDate;
    private int DocEntry;
    /**
     * ItemCode : D0911TAY4001
     * Dscription : 911T(40KG)包装袋
     * BagAmount : 0
     * Amount : 5000
     * WhsCode : 501
     * BatchNum : 2016122001
     * PriceAfVat : 2.5
     * LineGross : 12500
     * DocEntry : 62
     * LineNum : 5
     * WhsItems : [{"WhsCode":"201","WhsName":"主车间仓"},{"WhsCode":"501","WhsName":"包材良品仓"},{"WhsCode":"502","WhsName":"包材待检仓"},{"WhsCode":"503","WhsName":"包材待退货仓"},{"WhsCode":"901","WhsName":"封存仓"}]
     */

    private List<Linesbean> Lines;

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String CardCode) {
        this.CardCode = CardCode;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String CardName) {
        this.CardName = CardName;
    }

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

    public List<Linesbean> getLines() {
        return Lines;
    }

    public void setLines(List<Linesbean> Lines) {
        this.Lines = Lines;
    }

    public static class Linesbean {
        private String ItemCode;
        private String Dscription;
        private double BagAmount;
        private double Amount;
        private String WhsCode;
        private String BatchNum;
        private double PriceAfVat;
        private double LineGross;
        private int DocEntry;
        private int LineNum;
        /**
         * WhsCode : 201
         * WhsName : 主车间仓
         */

        private List<WhsItemsbean> WhsItems;

        public String getItemCode() {
            return ItemCode;
        }

        public void setItemCode(String ItemCode) {
            this.ItemCode = ItemCode;
        }

        public String getDscription() {
            return Dscription;
        }

        public void setDscription(String Dscription) {
            this.Dscription = Dscription;
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

        public String getBatchNum() {
            return BatchNum;
        }

        public void setBatchNum(String BatchNum) {
            this.BatchNum = BatchNum;
        }

        public double getPriceAfVat() {
            return PriceAfVat;
        }

        public void setPriceAfVat(double PriceAfVat) {
            this.PriceAfVat = PriceAfVat;
        }

        public double getLineGross() {
            return LineGross;
        }

        public void setLineGross(double LineGross) {
            this.LineGross = LineGross;
        }

        public int getDocEntry() {
            return DocEntry;
        }

        public void setDocEntry(int DocEntry) {
            this.DocEntry = DocEntry;
        }

        public double getLineNum() {
            return LineNum;
        }

        public void setLineNum(int LineNum) {
            this.LineNum = LineNum;
        }

        public List<WhsItemsbean> getWhsItems() {
            return WhsItems;
        }

        public void setWhsItems(List<WhsItemsbean> WhsItems) {
            this.WhsItems = WhsItems;
        }

        public static class WhsItemsbean {
            private String WhsCode;
            private String WhsName;

            public String getWhsCode() {
                return WhsCode;
            }

            public void setWhsCode(String WhsCode) {
                this.WhsCode = WhsCode;
            }

            public String getWhsName() {
                return WhsName;
            }

            public void setWhsName(String WhsName) {
                this.WhsName = WhsName;
            }
        }
    }
}
