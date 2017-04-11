package constant;

/**
 * Created by Jinpan on 2016/12/12 0012.
 */
public class Constant {
    public static final String HOST_BASE = "http://58.209.114.157/B1WebS";
    //本地:http://10.227.128.112/AnyouWebservice
    //客户:http://10.0.10.101:8081
    //外网:http://58.209.114.157/B1WebS
    public static final long INTERVAL = 2000;    //两秒间隔内点击两次退出就能退出程序
    public static final String time = "2017/4/10";    //系统更新时间

    //采购入库
    public static final String HOST_SAPB1Login = HOST_BASE + "/B1Webservice.asmx/SAPB1Login";
    public static final String HOST_GetOpenPOList = HOST_BASE + "/B1Webservice.asmx/GetOpenPOList";
    public static final String HOST_GetOpenPODetail = HOST_BASE + "/B1Webservice.asmx/GetOpenPODetail";
    public static final String HOST_AddGRPOBasedonPO = HOST_BASE + "/B1Webservice.asmx/AddGRPOBasedonPO";
    public static final String HOST_GetInformedPOList = HOST_BASE + "/B1Webservice.asmx/GetInformedPOList";
    public static final String HOST_GetInformedPODetail = HOST_BASE + "/B1Webservice.asmx/GetInformedPODetail";
    public static final String HOST_SearchOpenPOList = HOST_BASE + "/B1Webservice.asmx/SearchOpenPOList";
    public static final String HOST_SearchInformedPOList = HOST_BASE + "/B1Webservice.asmx/SearchInformedPOList";
    public static final String HOST_AddGRPOBasedonInformedPO = HOST_BASE + "/B1Webservice.asmx/AddGRPOBasedonInformedPO";

    //销售出库
    public static final String HOST_GetOpenSOList = HOST_BASE + "/B1Webservice.asmx/GetOpenSOList";
    public static final String HOST_SearchOpenSOList = HOST_BASE + "/B1Webservice.asmx/SearchOpenSOList";
    public static final String HOST_GetOpenSODetail = HOST_BASE + "/B1Webservice.asmx/GetOpenSODetail";
    public static final String HOST_AddODLNBasedonSO = HOST_BASE + "/B1Webservice.asmx/AddODLNBasedonSO";

    //生产收货
    public static final String HOST_GetOpenProducitonOrderList = HOST_BASE + "/B1Webservice.asmx/GetOpenProducitonOrderList";
    public static final String HOST_SearchOpenProductionOrderList = HOST_BASE + "/B1Webservice.asmx/SearchOpenProductionOrderList";
    public static final String HOST_GetOpenProductionDetail = HOST_BASE + "/B1Webservice.asmx/GetOpenProductionDetail";
    public static final String HOST_AddProductionReceive = HOST_BASE + "/B1Webservice.asmx/AddProductionReceive";

    //库存转储
    public static final String HOST_GetOpenInvTransRequestList = HOST_BASE + "/B1Webservice.asmx/GetOpenInvTransRequestList";
    public static final String HOST_SearchOpenInvTransRequestList = HOST_BASE + "/B1Webservice.asmx/SearchOpenInvTransRequestList";
    public static final String HOST_GetInvTransDetail = HOST_BASE + "/B1Webservice.asmx/GetInvTransDetail";
    public static final String HOST_AddInventoryTransfer = HOST_BASE + "/B1Webservice.asmx/AddInventoryTransfer";
    public static final String HOST_GetInvInformation = HOST_BASE + "/B1Webservice.asmx/GetInvInformation";

    //采购退货
    public static final String HOST_GetPOReturnInfo = HOST_BASE + "/B1Webservice.asmx/GetPOReturnInfo";
    public static final String HOST_SearchPOReturnDetail = HOST_BASE + "/B1Webservice.asmx/SearchPOReturnDetail";
    public static final String HOST_AddPOReturn = HOST_BASE + "/B1Webservice.asmx/AddPOReturn";

    //销售退货
    public static final String HOST_GetSOReturnInfo = HOST_BASE + "/B1Webservice.asmx/GetSOReturnInfo";
    public static final String HOST_SearchSOReturnDetail = HOST_BASE + "/B1Webservice.asmx/SearchSOReturnDetail";
    public static final String HOST_AddSOReturn = HOST_BASE + "/B1Webservice.asmx/AddSOReturn";

    //历史单据查询
    public static final String HOST_getOpenDeliveryList = HOST_BASE + "/B1Webservice.asmx/getOpenDeliveryList";
    public static final String HOST_DownloadDeliveryPDF = HOST_BASE + "/B1Webservice.asmx/DownloadDeliveryPDF";
    public static final String HOST_searchOpenDeliveryList = HOST_BASE + "/B1Webservice.asmx/searchOpenDeliveryList";

    //库存收货
    public static final String HOST_GetInventoryReceiptInfo = HOST_BASE + "/B1Webservice.asmx/GetInventoryReceiptInfo";
    public static final String HOST_AddInventoryReceipt = HOST_BASE + "/B1Webservice.asmx/AddInventoryReceipt";

    //库存发货
    public static final String HOST_GetInventoryIssueInfo = HOST_BASE + "/B1Webservice.asmx/GetInventoryIssueInfo";
    public static final String HOST_AddInventoryIssue = HOST_BASE + "/B1Webservice.asmx/AddInventoryIssue";

    //到货通知单执行情况
    public static final String HOST_getInformedReport = HOST_BASE + "/B1Webservice.asmx/getInformedReport";
    //销售交退货明细
    public static final String HOST_getSalesReturnReport = HOST_BASE + "/B1Webservice.asmx/getSalesReturnReport";
    //采购收退货明细
    public static final String HOST_getPurchaseReturnReport = HOST_BASE + "/B1Webservice.asmx/getPurchaseReturnReport";
    //成品生产入库明细
    public static final String HOST_getProductionReceiptReport = HOST_BASE + "/B1Webservice.asmx/getProductionReceiptReport";
    //非生产性物料出入库明细
    public static final String HOST_getInventoryTransactionReport = HOST_BASE + "/B1Webservice.asmx/getInventoryTransactionReport";
    //每个仓库的物料进销存报表
    public static final String HOST_getWarehouseReport = HOST_BASE + "/B1Webservice.asmx/getWarehouseReport";
    //仓库列表
    public static final String HOST_GetWhsInfo = HOST_BASE + "/B1Webservice.asmx/GetWhsInfo";
    //物料转储明细
    public static final String HOST_getInventoryTransferReport = HOST_BASE + "/B1Webservice.asmx/getInventoryTransferReport";
}
