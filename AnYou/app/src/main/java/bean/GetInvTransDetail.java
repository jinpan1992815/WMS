package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/5 0005.
 */
public class GetInvTransDetail {

    /**
     * TransCode : 101
     * TransName : 生产领用调拨
     * Filler : 105
     * ToWhs : 102
     * DocDate : 2017.01.10
     * DocEntry : 1
     * DocNum : 1
     * InvLines : [{"ItemCode":"AN00011","Dscription":"赖氨酸 L-Lys HCL 98.5 (LYS 77.5)","BagAmount":0,"Amount":5,"DocEntry":1,"LineNum":0,"Batches":[{"BatchNumber":"2016052001","Quantity":5},{"BatchNumber":"2016122201","Quantity":0},{"BatchNumber":"2016122204","Quantity":0},{"BatchNumber":"2016122205","Quantity":0},{"BatchNumber":"2016122302","Quantity":0},{"BatchNumber":"2016122701","Quantity":0},{"BatchNumber":"2016122702","Quantity":0},{"BatchNumber":"2017010401","Quantity":0},{"BatchNumber":"2017010402","Quantity":0},{"BatchNumber":"20170104002","Quantity":0},{"BatchNumber":"2017010403","Quantity":0},{"BatchNumber":"2017010404","Quantity":0}]}]
     * TransCodeList : []
     * WhsList : []
     * ItemList : [{"ItemCode":"AN00011","ItemName":"赖氨酸 L-Lys HCL 98.5 (LYS 77.5)","IfBatches":"Y","QtyInWhs":[{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","Quanity":456.9},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122201","SysNumber":"3","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122204","SysNumber":"4","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122205","SysNumber":"5","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122302","SysNumber":"7","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122701","SysNumber":"8","Quanity":500},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122702","SysNumber":"9","Quanity":450},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010401","SysNumber":"11","Quanity":2040},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","Quanity":10},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"20170104002","SysNumber":"13","Quanity":20},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010403","SysNumber":"14","Quanity":200},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","Quanity":20}]}]
     */

    private String TransCode;
    private String TransName;
    private String Filler;
    private String ToWhs;
    private String DocDate;
    private int DocEntry;
    private String DocNum;
    /**
     * ItemCode : AN00011
     * Dscription : 赖氨酸 L-Lys HCL 98.5 (LYS 77.5)
     * BagAmount : 0
     * Amount : 5
     * DocEntry : 1
     * LineNum : 0
     * Batches : [{"BatchNumber":"2016052001","Quantity":5},{"BatchNumber":"2016122201","Quantity":0},{"BatchNumber":"2016122204","Quantity":0},{"BatchNumber":"2016122205","Quantity":0},{"BatchNumber":"2016122302","Quantity":0},{"BatchNumber":"2016122701","Quantity":0},{"BatchNumber":"2016122702","Quantity":0},{"BatchNumber":"2017010401","Quantity":0},{"BatchNumber":"2017010402","Quantity":0},{"BatchNumber":"20170104002","Quantity":0},{"BatchNumber":"2017010403","Quantity":0},{"BatchNumber":"2017010404","Quantity":0}]
     */

    private List<InvLinesbean> InvLines;
    private List<?> TransCodeList;
    private List<?> WhsList;
    /**
     * ItemCode : AN00011
     * ItemName : 赖氨酸 L-Lys HCL 98.5 (LYS 77.5)
     * IfBatches : Y
     * QtyInWhs : [{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","Quanity":456.9},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122201","SysNumber":"3","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122204","SysNumber":"4","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122205","SysNumber":"5","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122302","SysNumber":"7","Quanity":1000},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122701","SysNumber":"8","Quanity":500},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122702","SysNumber":"9","Quanity":450},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010401","SysNumber":"11","Quanity":2040},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","Quanity":10},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"20170104002","SysNumber":"13","Quanity":20},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010403","SysNumber":"14","Quanity":200},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","Quanity":20}]
     */

    private List<ItemListbean> ItemList;

    public String getTransCode() {
        return TransCode;
    }

    public void setTransCode(String TransCode) {
        this.TransCode = TransCode;
    }

    public String getTransName() {
        return TransName;
    }

    public void setTransName(String TransName) {
        this.TransName = TransName;
    }

    public String getFiller() {
        return Filler;
    }

    public void setFiller(String Filler) {
        this.Filler = Filler;
    }

    public String getToWhs() {
        return ToWhs;
    }

    public void setToWhs(String ToWhs) {
        this.ToWhs = ToWhs;
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

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String DocNum) {
        this.DocNum = DocNum;
    }

    public List<InvLinesbean> getInvLines() {
        return InvLines;
    }

    public void setInvLines(List<InvLinesbean> InvLines) {
        this.InvLines = InvLines;
    }

    public List<?> getTransCodeList() {
        return TransCodeList;
    }

    public void setTransCodeList(List<?> TransCodeList) {
        this.TransCodeList = TransCodeList;
    }

    public List<?> getWhsList() {
        return WhsList;
    }

    public void setWhsList(List<?> WhsList) {
        this.WhsList = WhsList;
    }

    public List<ItemListbean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<ItemListbean> ItemList) {
        this.ItemList = ItemList;
    }

    public static class InvLinesbean {
        private String ItemCode;
        private String Dscription;
        private double BagAmount;
        private double Amount;
        private int DocEntry;
        private int LineNum;
        /**
         * BatchNumber : 2016052001
         * Quantity : 5
         */

        private List<Batchesbean> Batches;

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

    public static class ItemListbean {
        private String ItemCode;
        private String ItemName;
        private String IfBatches;
        /**
         * WhsCode : 105
         * ItemCode : AN00011
         * BatchNumber : 2016052001
         * SysNumber : 2
         * Quanity : 456.9
         */

        private List<QtyInWhsbean> QtyInWhs;

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

        public String getIfBatches() {
            return IfBatches;
        }

        public void setIfBatches(String IfBatches) {
            this.IfBatches = IfBatches;
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
            private double Quanity;

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

            public double getQuanity() {
                return Quanity;
            }

            public void setQuanity(double Quanity) {
                this.Quanity = Quanity;
            }
        }
    }
}
