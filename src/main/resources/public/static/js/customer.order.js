function searchOrders() {
    $("#dg").datagrid("load",{
        orderNo:$("#s_orderNo").val(),
        state:$("#s_state").combobox("getValue")
    })
}
function formatterState(value) {
    if(value==1){
        return "已支付";
    }else if(value==0){
        return "未支付";
    }else{
        return "未知";
    }
}
function formatterOp() {
    var href='javascript:openOrderDetailDialog()';
    return "<a href='"+href+"'>详情查看</a>";
}

function openOrderDetailDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待修改的数据!","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量修改!","warning");
        return;
    }
    $("#fm").form("load",ctx+"/customer_order/queryOrderInfo?orderId="+rows[0].id);
    $("#dg02").datagrid("load",{
        orderId:rows[0].id
    });
    openDialog("dlg","订单详情")
}
function searchOrderDetails() {
    $("#dg02").datagrid("load",{
        orderId:$("#dg").datagrid("getSelections")[0].id,
        goodsName:$("#s_goodsName").val(),
        type:$("#s_price").combobox("getValue")
    });
}