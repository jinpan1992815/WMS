package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/17 0017.
 */
public class SearchPOReturnDetail {
    /**
     * CardCode : S00001
     * CardName : 天津安佑饲料科技有限公司
     * DocDate : null
     * DocEntry : 223
     * DocNum : 223
     * Lines : [{"ItemCode":"AN00011","ItemName":"赖氨酸 L-Lys HCL 98.5 (LYS 77.5)","BagAmount":1,"Amount":20,"Factor1":1,"Factor2":1,"WhsCode":"105","PriceAfVat":2,"LineGross":40,"BaseEntry":223,"BaseLineNum":0,"Batches":[],"reWhs":[{"WhsCode":"102","WhsName":"原料筒仓2","chooseBatches":[{"ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","WhsCode":"102","Quantity":25,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016123001","SysNumber":"10","WhsCode":"102","Quantity":10,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","WhsCode":"102","Quantity":2,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","WhsCode":"102","Quantity":3,"Amount":20}]},{"WhsCode":"105","WhsName":"原料良品仓","chooseBatches":[{"ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","WhsCode":"105","Quantity":421.8999938964844,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122201","SysNumber":"3","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122204","SysNumber":"4","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122205","SysNumber":"5","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122302","SysNumber":"7","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122701","SysNumber":"8","WhsCode":"105","Quantity":500,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122702","SysNumber":"9","WhsCode":"105","Quantity":450,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010401","SysNumber":"11","WhsCode":"105","Quantity":2040,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","WhsCode":"105","Quantity":8,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"20170104002","SysNumber":"13","WhsCode":"105","Quantity":20,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010403","SysNumber":"14","WhsCode":"105","Quantity":200,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","WhsCode":"105","Quantity":17,"Amount":20}]}]}]
     */

    private String CardCode;
    private String CardName;
    private Object DocDate;
    private int DocEntry;
    private String DocNum;
    /**
     * ItemCode : AN00011
     * ItemName : 赖氨酸 L-Lys HCL 98.5 (LYS 77.5)
     * BagAmount : 1
     * Amount : 20
     * Factor1 : 1
     * Factor2 : 1
     * WhsCode : 105
     * PriceAfVat : 2
     * LineGross : 40
     * BaseEntry : 223
     * BaseLineNum : 0
     * Batches : []
     * reWhs : [{"WhsCode":"102","WhsName":"原料筒仓2","chooseBatches":[{"ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","WhsCode":"102","Quantity":25,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016123001","SysNumber":"10","WhsCode":"102","Quantity":10,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","WhsCode":"102","Quantity":2,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","WhsCode":"102","Quantity":3,"Amount":20}]},{"WhsCode":"105","WhsName":"原料良品仓","chooseBatches":[{"ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","WhsCode":"105","Quantity":421.8999938964844,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122201","SysNumber":"3","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122204","SysNumber":"4","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122205","SysNumber":"5","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122302","SysNumber":"7","WhsCode":"105","Quantity":1000,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122701","SysNumber":"8","WhsCode":"105","Quantity":500,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016122702","SysNumber":"9","WhsCode":"105","Quantity":450,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010401","SysNumber":"11","WhsCode":"105","Quantity":2040,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","WhsCode":"105","Quantity":8,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"20170104002","SysNumber":"13","WhsCode":"105","Quantity":20,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010403","SysNumber":"14","WhsCode":"105","Quantity":200,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","WhsCode":"105","Quantity":17,"Amount":20}]}]
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

    public Object getDocDate() {
        return DocDate;
    }

    public void setDocDate(Object DocDate) {
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

    public List<Linesbean> getLines() {
        return Lines;
    }

    public void setLines(List<Linesbean> Lines) {
        this.Lines = Lines;
    }

    public static class Linesbean {
        private String ItemCode;
        private String ItemName;
        private double BagAmount;
        private double Amount;
        private double Factor1;
        private double Factor2;
        private String WhsCode;
        private double PriceAfVat;
        private double LineGross;
        private int BaseEntry;
        private int BaseLineNum;
        private List<?> Batches;
        /**
         * WhsCode : 102
         * WhsName : 原料筒仓2
         * chooseBatches : [{"ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","WhsCode":"102","Quantity":25,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2016123001","SysNumber":"10","WhsCode":"102","Quantity":10,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","WhsCode":"102","Quantity":2,"Amount":0},{"ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","WhsCode":"102","Quantity":3,"Amount":20}]
         */

        private List<ReWhsbean> reWhs;

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

        public double getLineGross() {
            return LineGross;
        }

        public void setLineGross(double LineGross) {
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

        public List<?> getBatches() {
            return Batches;
        }

        public void setBatches(List<?> Batches) {
            this.Batches = Batches;
        }

        public List<ReWhsbean> getReWhs() {
            return reWhs;
        }

        public void setReWhs(List<ReWhsbean> reWhs) {
            this.reWhs = reWhs;
        }

        public static class ReWhsbean {
            private String WhsCode;
            private String WhsName;
            /**
             * ItemCode : AN00011
             * BatchNumber : 2016052001
             * SysNumber : 2
             * WhsCode : 102
             * Quantity : 25
             * Amount : 0
             */

            private List<ChooseBatchesbean> chooseBatches;

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

            public List<ChooseBatchesbean> getChooseBatches() {
                return chooseBatches;
            }

            public void setChooseBatches(List<ChooseBatchesbean> chooseBatches) {
                this.chooseBatches = chooseBatches;
            }

            public static class ChooseBatchesbean {
                private String ItemCode;
                private String BatchNumber;
                private String SysNumber;
                private String WhsCode;
                private double Quantity;
                private double Amount;

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

                public String getWhsCode() {
                    return WhsCode;
                }

                public void setWhsCode(String WhsCode) {
                    this.WhsCode = WhsCode;
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
            }
        }
    }
}
