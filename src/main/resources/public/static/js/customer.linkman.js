/**
 * 联系人管理
 */

function openCustomerLinkman() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择客户记录！","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量查看!","warning");
        return;
    }
    var id=rows[0].id;
    $("#dg02").edatagrid({
        toolbar:"#toolbar",
        url:ctx+"/customer_linkman/list?cusId="+id,
        saveUrl:ctx+"/customer_linkman/save?cusId="+id,
        updateUrl:ctx+"/customer_linkman/update",
        destroyUrl:ctx+"/customer_linkman/delete"
    });
    openDialog("dlg02","联系人管理");
}
function saveCustomerLinkman() {
    $("#dg02").edatagrid("saveRow");
    $("#dg02").edatagrid("load");
}
function deleteCustomerLinkman() {
    $("#dg02").edatagrid("destroyRow");
    $.messager.alert("系统消息",data.msg,"success");
    $("#dg02").edatagrid("load");
}
function searchCustomerLinkman() {
    $("#dg02").datagrid("load",{
        linkName:$("#linkName").val(),
        zhiwei:$("#zhiwei").val(),
        phone:$("#phone02").val()
    })
}