package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class GetInvInformation {
    /**
     * TransCode : 101
     * TransName : 生产领用调拨
     */

    private List<TransCodeListbean> TransCodeList;
    /**
     * WhsCode : 101
     * WhsName : 原料筒仓1
     */

    private List<WhsListbean> WhsList;
    /**
     * ItemCode : AN00011
     * ItemName : 赖氨酸 L-Lys HCL 98.5 (LYS 77.5)
     * IfBatches : Y
     * QtyInWhs : [{"WhsCode":"102","ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","Quanity":22,"Amount":0},{"WhsCode":"102","ItemCode":"AN00011","BatchNumber":"2016123001","SysNumber":"10","Quanity":10,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016052001","SysNumber":"2","Quanity":434.9,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122201","SysNumber":"3","Quanity":1000,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122204","SysNumber":"4","Quanity":1000,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122205","SysNumber":"5","Quanity":1000,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122302","SysNumber":"7","Quanity":1000,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122701","SysNumber":"8","Quanity":500,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2016122702","SysNumber":"9","Quanity":450,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010401","SysNumber":"11","Quanity":2040,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010402","SysNumber":"12","Quanity":10,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"20170104002","SysNumber":"13","Quanity":20,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010403","SysNumber":"14","Quanity":200,"Amount":0},{"WhsCode":"105","ItemCode":"AN00011","BatchNumber":"2017010404","SysNumber":"15","Quanity":20,"Amount":0}]
     */

    private List<ItemListbean> ItemList;

    public List<TransCodeListbean> getTransCodeList() {
        return TransCodeList;
    }

    public void setTransCodeList(List<TransCodeListbean> TransCodeList) {
        this.TransCodeList = TransCodeList;
    }

    public List<WhsListbean> getWhsList() {
        return WhsList;
    }

    public void setWhsList(List<WhsListbean> WhsList) {
        this.WhsList = WhsList;
    }

    public List<ItemListbean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<ItemListbean> ItemList) {
        this.ItemList = ItemList;
    }

    public static class TransCodeListbean {
        private String TransCode;
        private String TransName;

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
    }

    public static class WhsListbean {
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

    public static class ItemListbean {
        private String ItemCode;
        private String ItemName;
        private String IfBatches;
        /**
         * WhsCode : 102
         * ItemCode : AN00011
         * BatchNumber : 2016052001
         * SysNumber : 2
         * Quanity : 22
         * Amount : 0
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

            public double getQuanity() {
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
