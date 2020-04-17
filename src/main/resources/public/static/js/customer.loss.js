$(function () {
    searchCustomerLoss();
})

function formatterState(value) {
    if(value==0){
        return "暂缓流失";
    }else if(value==1){
        return "确定流失";
    }else {
        return "状态未知";
    }
}
function searchCustomerLoss() {
    $("#dg").datagrid("load",{
        cusNo:$("#s_cusNo").val(),
        cusName:$("#s_cusName").val(),
        state:$("#s_state").combobox("getValue")
    })
}

function formatterOp(value,rowData) {
    var state=rowData.state;
    if(state==0){
        var title=rowData.cusName+"_暂缓设置";
        var href='javascript:openCustomerReprieveTab("'+title+'",'+rowData.id+')'
        return "<a href='"+href+"'>暂缓设置</a>";
    }
    if(state==1){
        var title=rowData.cusName+"_流失查看";
        var href='javascript:openCustomerReprieveTab("'+title+'",'+rowData.id+')'
        return "<a href='"+href+"'>流失查看</a>";
    }
}
function openCustomerReprieveTab(title,lossId) {

    window.parent.openTab(title,ctx+"/customer_reprieve/index?lossId="+lossId);
}
