function searchCustomersByParams() {
    $("#dg").datagrid("load",{
        cusName:$("#name").val(),
        cusNo:$("#khno").val(),
        level:$("#level").combobox("getValue"),
        myd:$("#myd").combobox("getValue")
    })
}
function clearFormData() {

    $("#name").val("");
    $("#area").val("");
    $("#cusManager").val("");
    $("#level").val("");
    $("#xyd").val("");
    $("#address").val("");
    $("#postCode").val("");
    $("#phone").val("");
    $("#fax").val("");
    $("#webSite").val("");
    $("#fr").val("");
    $("#zczj").val("");
    $("#nyye").val("");
    $("#khyh").val("");
    $("#khzh").val("");
    $("#gsdjh").val("");
    $("#dsdjh").val("");
    $("input[name='id']").val("");

}

function openCustomerAddDialog() {
    openDialog("dlg","添加客户信息");
}

function openCustomerModifyDialog() {
    openModifyDialog("dg","fm","dlg","更新客户信息");
}

function closeCustomerDialog() {
    closeDialog("dlg");
}

function saveOrUpdateCustomer() {
    saveOrUpdateRecode(ctx+"/customer/save",ctx+"/customer/update","dlg",searchCustomersByParams,clearFormData)
}

function deleteCustomer() {
    deleteRecode("dg",ctx+"/customer/delete",searchCustomersByParams)
}

function openCustomerOrderTab() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待查看的数据!","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量查看!","warning");
        return;
    }
    window.parent.openTab(rows[0].name+"_订单展示",ctx+"/customer/queryCustomerInfo?cusId="+rows[0].id);
}