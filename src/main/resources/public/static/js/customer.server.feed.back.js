function searchCustomerServeByParams() {
    $("#dg").datagrid("load",{
        customer:$("#s_customer").val(),
        type:$("#s_serveType").combobox("getValue")
    })
}
function formatterServeType(value) {
    if(value==6){
        return "咨询";
    }else if(value==7){
        return "建议";
    }else if(value==8){
        return "投资";
    }else if(value==9){
        return "维修";
    }else {
        return "其他";
    }
}
function openFeedBackDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待反馈的数据!","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量反馈!","warning");
        return;
    }
    $("#fm").form("load",rows[0]);
    $("#serviceProce").html(rows[0].serviceProce);
    openDialog("dlg","服务反馈")
}
function doFeedBack() {
    $("#fm").form("submit",{
        url:ctx+"/customer_serve/saveOrUpdateCustomerServe",
        onSubmit:function (param) {
            param.state="fw_004";
            return  $("#fm").form("validate");
        },
        success:function(data){
            data =JSON.parse(data);
            if(data.code==200){
                $.messager.alert("系统消息","服务反馈成功","success");
                closeRoleDialog();
                clearFormData();
                searchCustomerServeByParams()
            }else{
                $.messager.alert("系统消息",data.msg,"error");
            }
        }
    })
}
function closeRoleDialog() {
    closeDialog("dlg")
}
function clearFormData() {
    $("input").val("");
    $("#serveType").combobox("setValue","");
    $("#s_assigner").combobox("setValue","");
    $("#serviceProce").val("");

}