package bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/19 0019.
 */
public class GetInventoryReceiptInfo {

    private String RecommendBatches;
    /**
     * TrsCode : 101
     * TrsName : 生产领用调拨
     */

    private List<TransInfobean> transInfo;
    /**
     * WhsCode : 101
     * WhsName : 原料筒仓1
     */

    private List<WhsInfobean> whsInfo;
    /**
     * UseDepartCode : 3001005
     * UseDepartName : 生产部
     */

    private List<UseDepartbean> useDepart;
    /**
     * InDepartCode : 0001
     * InDepartName : TEST
     */

    private List<InDepartbean> inDepart;
    /**
     * TrsName : 机物料/燃料领用出库
     * TrsCode : 201
     * CostCenterCode : 1001001
     */

    private List<CostCenterbean> costCenter;
    /**
     * TypeCode : -
     * TypeName : -
     */

    private List<BaseDocTypebean> baseDocType;
    /**
     * ItemCode : AN00014
     * ItemName : 赖氨酸Lysine SO4-70 (LYS 55.3)
     * UnitPrice : 0
     * LineGross : 0
     * TranCode : 301
     * TransName : 工单材料调整
     * IfBatches : Y
     * RecommendBatch : null
     * WhsCode : 101
     */

    private List<Itembean> item;
    private List<?> batches;

    public String getRecommendBatches() {
        return RecommendBatches;
    }

    public void setRecommendBatches(String RecommendBatches) {
        this.RecommendBatches = RecommendBatches;
    }

    public List<TransInfobean> getTransInfo() {
        return transInfo;
    }

    public void setTransInfo(List<TransInfobean> transInfo) {
        this.transInfo = transInfo;
    }

    public List<WhsInfobean> getWhsInfo() {
        return whsInfo;
    }

    public void setWhsInfo(List<WhsInfobean> whsInfo) {
        this.whsInfo = whsInfo;
    }

    public List<UseDepartbean> getUseDepart() {
        return useDepart;
    }

    public void setUseDepart(List<UseDepartbean> useDepart) {
        this.useDepart = useDepart;
    }

    public List<InDepartbean> getInDepart() {
        return inDepart;
    }

    public void setInDepart(List<InDepartbean> inDepart) {
        this.inDepart = inDepart;
    }

    public List<CostCenterbean> getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(List<CostCenterbean> costCenter) {
        this.costCenter = costCenter;
    }

    public List<BaseDocTypebean> getBaseDocType() {
        return baseDocType;
    }

    public void setBaseDocType(List<BaseDocTypebean> baseDocType) {
        this.baseDocType = baseDocType;
    }

    public List<Itembean> getItem() {
        return item;
    }

    public void setItem(List<Itembean> item) {
        this.item = item;
    }

    public List<?> getBatches() {
        return batches;
    }

    public void setBatches(List<?> batches) {
        this.batches = batches;
    }

    public static class TransInfobean {
        private String TrsCode;
        private String TrsName;

        public String getTrsCode() {
            return TrsCode;
        }

        public void setTrsCode(String TrsCode) {
            this.TrsCode = TrsCode;
        }

        public String getTrsName() {
            return TrsName;
        }

        public void setTrsName(String TrsName) {
            this.TrsName = TrsName;
        }
    }

    public static class WhsInfobean {
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

    public static class UseDepartbean {
        private String UseDepartCode;
        private String UseDepartName;

        public String getUseDepartCode() {
            return UseDepartCode;
        }

        public void setUseDepartCode(String UseDepartCode) {
            this.UseDepartCode = UseDepartCode;
        }

        public String getUseDepartName() {
            return UseDepartName;
        }

        public void setUseDepartName(String UseDepartName) {
            this.UseDepartName = UseDepartName;
        }
    }

    public static class InDepartbean {
        private String InDepartCode;
        private String InDepartName;

        public String getInDepartCode() {
            return InDepartCode;
        }

        public void setInDepartCode(String InDepartCode) {
            this.InDepartCode = InDepartCode;
        }

        public String getInDepartName() {
            return InDepartName;
        }

        public void setInDepartName(String InDepartName) {
            this.InDepartName = InDepartName;
        }
    }

    public static class CostCenterbean {
        private String TrsName;
        private String TrsCode;
        private String CostCenterCode;

        public String getTrsName() {
            return TrsName;
        }

        public void setTrsName(String TrsName) {
            this.TrsName = TrsName;
        }

        public String getTrsCode() {
            return TrsCode;
        }

        public void setTrsCode(String TrsCode) {
            this.TrsCode = TrsCode;
        }

        public String getCostCenterCode() {
            return CostCenterCode;
        }

        public void setCostCenterCode(String CostCenterCode) {
            this.CostCenterCode = CostCenterCode;
        }
    }

    public static class BaseDocTypebean {
        private String TypeCode;
        private String TypeName;

        public String getTypeCode() {
            return TypeCode;
        }

        public void setTypeCode(String TypeCode) {
            this.TypeCode = TypeCode;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String TypeName) {
            this.TypeName = TypeName;
        }
    }

    public static class Itembean {
        private String ItemCode;
        private String ItemName;
        private double UnitPrice;
        private double LineGross;
        private String TranCode;
        private String TransName;
        private String IfBatches;
        private Object RecommendBatch;
        private String WhsCode;

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

        public double getUnitPrice() {
            return UnitPrice;
        }

        public void setUnitPrice(double UnitPrice) {
            this.UnitPrice = UnitPrice;
        }

        public double getLineGross() {
            return LineGross;
        }

        public void setLineGross(double LineGross) {
            this.LineGross = LineGross;
        }

        public String getTranCode() {
            return TranCode;
        }

        public void setTranCode(String TranCode) {
            this.TranCode = TranCode;
        }

        public String getTransName() {
            return TransName;
        }

        public void setTransName(String TransName) {
            this.TransName = TransName;
        }

        public String getIfBatches() {
            return IfBatches;
        }

        public void setIfBatches(String IfBatches) {
            this.IfBatches = IfBatches;
        }

        public Object getRecommendBatch() {
            return RecommendBatch;
        }

        public void setRecommendBatch(Object RecommendBatch) {
            this.RecommendBatch = RecommendBatch;
        }

        public String getWhsCode() {
            return WhsCode;
        }

        public void setWhsCode(String WhsCode) {
            this.WhsCode = WhsCode;
        }
    }
}
