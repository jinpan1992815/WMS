package bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/25 0025.
 */
public class GetOpenSODetail {

    /**
     * CardCode : C000046
     * CardName : 闫云维
     * DocDate : 2016.12.28
     * DocEntry : 233
     * Lines : [{"ItemCode":"ZNF204011KAY02","Dscription":"40%乳猪浓缩料4010K","BagAmount":32,"Factor1":20,"Factor2":50,"Amount":640,"WhsCode":"401","PriceAfVat":8.25,"LineGross":8250,"DocEntry":233,"LineNum":0,"WhsItems":[{"WhsCode":"401","WhsName":"产成品良品仓","QtyInWhs":[{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016051601","SysNumber":"12","Quanity":100,"Amount":100},{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016052701","SysNumber":"13","Quanity":2020,"Amount":540},{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016051701","SysNumber":"14","Quanity":400,"Amount":0}]}],"Batches":[]}]
     */

    private String CardCode;
    private String CardName;
    private String DocDate;
    private int DocEntry;
    /**
     * ItemCode : ZNF204011KAY02
     * Dscription : 40%乳猪浓缩料4010K
     * BagAmount : 32
     * Factor1 : 20
     * Factor2 : 50
     * Amount : 640
     * WhsCode : 401
     * PriceAfVat : 8.25
     * LineGross : 8250
     * DocEntry : 233
     * LineNum : 0
     * WhsItems : [{"WhsCode":"401","WhsName":"产成品良品仓","QtyInWhs":[{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016051601","SysNumber":"12","Quanity":100,"Amount":100},{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016052701","SysNumber":"13","Quanity":2020,"Amount":540},{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016051701","SysNumber":"14","Quanity":400,"Amount":0}]}]
     * Batches : []
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
        private double Factor1;
        private double Factor2;
        private double Amount;
        private String WhsCode;
        private double PriceAfVat;
        private double LineGross;
        private int DocEntry;
        private int LineNum;
        /**
         * WhsCode : 401
         * WhsName : 产成品良品仓
         * QtyInWhs : [{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016051601","SysNumber":"12","Quanity":100,"Amount":100},{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016052701","SysNumber":"13","Quanity":2020,"Amount":540},{"WhsCode":"401","ItemCode":"ZNF204011KAY02","BatchNumber":"2016051701","SysNumber":"14","Quanity":400,"Amount":0}]
         */

        private List<WhsItemsbean> WhsItems;
        private List<?> Batches;

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

        public double getFactor1() {
            return Factor1;
        }

        public void setFactor1(int Factor1) {
            this.Factor1 = Factor1;
        }

        public double getFactor2() {
            return Factor2;
        }

        public void setFactor2(int Factor2) {
            this.Factor2 = Factor2;
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

        public int getLineNum() {
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

        public List<?> getBatches() {
            return Batches;
        }

        public void setBatches(List<?> Batches) {
            this.Batches = Batches;
        }

        public static class WhsItemsbean {
            private String WhsCode;
            private String WhsName;
            /**
             * WhsCode : 401
             * ItemCode : ZNF204011KAY02
             * BatchNumber : 2016051601
             * SysNumber : 12
             * Quanity : 100
             * Amount : 100
             */

            private List<QtyInWhsbean> QtyInWhs;

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

            public List<QtyInWhsbean> getQtyInWhs() {
                return QtyInWhs;
            }

            public void setQtyInWhs(List<QtyInWhsbean> QtyInWhs) {
                this.QtyInWhs = QtyInWhs;
            }

            public static class QtyInWhsbean {
                private String WhsCode;
                private String ItemCode;
                private String BatchNumber;
                private String SysNumber;
                private int Quanity;
                private double Amount;

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

                public String getBatchNumber() {
                    return BatchNumber;
                }

                public void setBatchNumber(String BatchNumber) {
                    this.BatchNumber = BatchNumber;
                }

                public String getSysNumber() {
                    return SysNumber;
                }

                public void setSysNumber(String SysNumber) {
                    this.SysNumber = SysNumber;
                }

                public int getQuanity() {
                    return Quanity;
                }

                public void setQuanity(int Quanity) {
                    this.Quanity = Quanity;
                }

                public double getAmount() {
                    return Amount;
                }

                public void setAmount(double Amount) {
                    this.Amount = Amount;
                }
            }
        }
    }
}
