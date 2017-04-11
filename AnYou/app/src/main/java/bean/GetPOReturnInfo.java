package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/16 0016.
 */
public class GetPOReturnInfo {
    /**
     * CardCode : S00001
     * CardName : 天津安佑饲料科技有限公司
     */

    private List<SupplierListbean> SupplierList;
    /**
     * ItemCode : AN00011
     * ItemName : 赖氨酸 L-Lys HCL 98.5 (LYS 77.5)
     * IfBatches : Y
     * WhsList : [{"WhsCode":"102","WhsName":"原料筒仓2","Batches":[{"WhsCode":"102","BatchNumber":"2016052001","SysNumber":"2","Quantity":25,"Amount":0},{"WhsCode":"102","BatchNumber":"2016123001","SysNumber":"10","Quantity":10,"Amount":0},{"WhsCode":"102","BatchNumber":"2017010402","SysNumber":"12","Quantity":2,"Amount":0},{"WhsCode":"102","BatchNumber":"2017010404","SysNumber":"15","Quantity":3,"Amount":0}]},{"WhsCode":"105","WhsName":"原料良品仓","Batches":[{"WhsCode":"105","BatchNumber":"2016052001","SysNumber":"2","Quantity":431.9,"Amount":0},{"WhsCode":"105","BatchNumber":"2016122201","SysNumber":"3","Quantity":1000,"Amount":0},{"WhsCode":"105","BatchNumber":"2016122204","SysNumber":"4","Quantity":1000,"Amount":0},{"WhsCode":"105","BatchNumber":"2016122205","SysNumber":"5","Quantity":1000,"Amount":0},{"WhsCode":"105","BatchNumber":"2016122302","SysNumber":"7","Quantity":1000,"Amount":0},{"WhsCode":"105","BatchNumber":"2016122701","SysNumber":"8","Quantity":500,"Amount":0},{"WhsCode":"105","BatchNumber":"2016122702","SysNumber":"9","Quantity":450,"Amount":0},{"WhsCode":"105","BatchNumber":"2017010401","SysNumber":"11","Quantity":2040,"Amount":0},{"WhsCode":"105","BatchNumber":"2017010402","SysNumber":"12","Quantity":8,"Amount":0},{"WhsCode":"105","BatchNumber":"20170104002","SysNumber":"13","Quantity":20,"Amount":0},{"WhsCode":"105","BatchNumber":"2017010403","SysNumber":"14","Quantity":200,"Amount":0},{"WhsCode":"105","BatchNumber":"2017010404","SysNumber":"15","Quantity":17,"Amount":0}]}]
     * SOReturnBatches : []
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
        private String IfBatches;
        /**
         * WhsCode : 102
         * WhsName : 原料筒仓2
         * Batches : [{"WhsCode":"102","BatchNumber":"2016052001","SysNumber":"2","Quantity":25,"Amount":0},{"WhsCode":"102","BatchNumber":"2016123001","SysNumber":"10","Quantity":10,"Amount":0},{"WhsCode":"102","BatchNumber":"2017010402","SysNumber":"12","Quantity":2,"Amount":0},{"WhsCode":"102","BatchNumber":"2017010404","SysNumber":"15","Quantity":3,"Amount":0}]
         */

        private List<WhsListbean> WhsList;
        private List<?> SOReturnBatches;

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

        public List<WhsListbean> getWhsList() {
            return WhsList;
        }

        public void setWhsList(List<WhsListbean> WhsList) {
            this.WhsList = WhsList;
        }

        public List<?> getSOReturnBatches() {
            return SOReturnBatches;
        }

        public void setSOReturnBatches(List<?> SOReturnBatches) {
            this.SOReturnBatches = SOReturnBatches;
        }

        public static class WhsListbean {
            private String WhsCode;
            private String WhsName;
            /**
             * WhsCode : 102
             * BatchNumber : 2016052001
             * SysNumber : 2
             * Quantity : 25
             * Amount : 0
             */

            private List<Batchesbean> Batches;

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

            public List<Batchesbean> getBatches() {
                return Batches;
            }

            public void setBatches(List<Batchesbean> Batches) {
                this.Batches = Batches;
            }

            public static class Batchesbean {
                private String WhsCode;
                private String BatchNumber;
                private String SysNumber;
                private double Quantity;
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
