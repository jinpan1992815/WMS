package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18 0018.
 */
public class GetSOReturnInfo {

    /**
     * CardCode : C000002
     * CardName : 彭淑联
     */

    private List<SupplierListbean> SupplierList;
    /**
     * ItemCode : ZNF202525NAY01
     * ItemName : 25%母猪用浓缩料2525
     * Factor1 : 20
     * IfBatches : Y
     * WhsList : [{"WhsCode":"401","WhsName":"产成品良品仓","Batches":[]},{"WhsCode":"402","WhsName":"产成品待检仓","Batches":[]},{"WhsCode":"403","WhsName":"产成品回机仓","Batches":[]},{"WhsCode":"404","WhsName":"产成品散装仓","Batches":[]},{"WhsCode":"901","WhsName":"封存仓","Batches":[]}]
     * SOReturnBatches : [{"WhsCode":"401","BatchNumber":"2016041801","SysNumber":"6","Quantity":0,"Amount":0},{"WhsCode":"401","BatchNumber":"2016033001","SysNumber":"5","Quantity":0,"Amount":0},{"WhsCode":"401","BatchNumber":"2016031901","SysNumber":"4","Quantity":0,"Amount":0},{"WhsCode":"401","BatchNumber":"2016031701","SysNumber":"3","Quantity":0,"Amount":0},{"WhsCode":"401","BatchNumber":"2016012201","SysNumber":"2","Quantity":0,"Amount":0}]
     */

    private List<ItemListbean> ItemList;

    public List<SupplierListbean> getSupplierList() {
        return SupplierList;
    }

    public void setSupplierList(List<SupplierListbean> SupplierList) {
        this.SupplierList = SupplierList;
    }

    public List<ItemListbean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<ItemListbean> ItemList) {
        this.ItemList = ItemList;
    }

    public static class SupplierListbean {
        private String CardCode;
        private String CardName;

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
    }

    public static class ItemListbean {
        private String ItemCode;
        private String ItemName;
        private double Factor1;
        private String IfBatches;
        /**
         * WhsCode : 401
         * WhsName : 产成品良品仓
         * Batches : []
         */

        private List<WhsListbean> WhsList;
        /**
         * WhsCode : 401
         * BatchNumber : 2016041801
         * SysNumber : 6
         * Quantity : 0
         * Amount : 0
         */

        private List<SOReturnBatchesbean> SOReturnBatches;

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

        public double getFactor1() {
            return Factor1;
        }

        public void setFactor1(double Factor1) {
            this.Factor1 = Factor1;
        }

        public String getIfBatches() {
            return IfBatches;
        }

        public void setIfBatches(String IfBatches) {
            this.IfBatches = IfBatches;
        }

        public List<WhsListbean> getWhsList() {
            return WhsList;
        }

        public void setWhsList(List<WhsListbean> WhsList) {
            this.WhsList = WhsList;
        }

        public List<SOReturnBatchesbean> getSOReturnBatches() {
            return SOReturnBatches;
        }

        public void setSOReturnBatches(List<SOReturnBatchesbean> SOReturnBatches) {
            this.SOReturnBatches = SOReturnBatches;
        }

        public static class WhsListbean {
            private String WhsCode;
            private String WhsName;
            private List<?> Batches;

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

            public List<?> getBatches() {
                return Batches;
            }

            public void setBatches(List<?> Batches) {
                this.Batches = Batches;
            }
        }

        public static class SOReturnBatchesbean {
            private String WhsCode;
            private String BatchNumber;
            private String SysNumber;
            private int Quantity;
            private double Amount;

            public String getWhsCode() {
                return WhsCode;
            }

            public void setWhsCode(String WhsCode) {
                this.WhsCode = WhsCode;
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

            public double getQuantity() {
                return Quantity;
            }

            public void setQuantity(int Quantity) {
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
