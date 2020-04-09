function searchSaleChance() {
    var customerName=$("#s_customerName").val();
    var createMan=$("#s_createMan").val();
    var state=$("#s_state").combobox("getValue");
    $("#dg").datagrid("load",{
        customerName:customerName,
        createMan:createMan,
        state:state
    })
}
function formatterState(value) {
    /**
     *  0-未分配
     *  1-已分配
     */
    if(value==0){
        return "未分配";
    }else if(value==1){
        return "已分配";
    }else{
        return "未知";
    }
}


function formatterDevResult(value) {
    /**
     * 0-未开发
     * 1-开发中
     * 2-开发成功
     * 3-开发失败
     */
    if(value==0){
        return "未开发";
    }else if(value==1){
        return "开发中";
    }else if(value==2){
        return "开发成功";
    }else if(value==3){
        return "开发失败";
    }else {
        return "未知"
    }

}
function openSaleChanceAddDialog() {
    $("#dlg").dialog("open").dialog("setTitle","添加数据");
}
function closeSaleChanceDialog() {
    $("#dlg").dialog("close");
}

function clearForm() {
    $("#customerName").val("");
    $("#chanceSource").val("");
    $("#linkMan").val("");
    $("#linkPhone").val("");
    $("#cgjl").val("");
    $("#overview").val("");
    $("#assignMan").combobox("setValue","");
    $("input[name='id']").val("");
}

function saveOrUpdateSaleChance() {
    //添加操作的后台访问路径
    var url=ctx+"/sale_chance/save";
    var id=$("input[name='id']").val();
    //更新操作的后台访问路径
    if(!isEmpty(id)){
        url=ctx+"/sale_chance/update";
    }
    $("#fm").form("submit",{
        url:url,
        OnSubmit:function () {
            return $("#fm").form("validate")
        },
        success:function (data) {
            var data = JSON.parse(data);
            if(data.code==200){
                //关闭对话框
                closeSaleChanceDialog();
                $.messager.confirm("系统消息",data.msg,"success")
                //刷新页面
                searchSaleChance();
                //清空表单
                clearForm();
            }else {
                $.messager.confirm("系统消息",data.msg,"error")
            }
        }
    })
}

function openSaleChanceModifyDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.confirm("系统消息","请选择待更新的记录！","warning")
        return;
    }
    if(rows.length>1){
        $.messager.confirm("系统消息","暂不支持多条记录更新！","warning")
        return;
    }
    $("#fm").form("load",rows[0]);
    $("#dlg").dialog("open").dialog("setTitle","更新数据");

}

function deleteSaleChance() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待删除的记录！","warning")
        return;
    }
    $.messager.confirm("系统消息","你确定要删除记录吗？",function (r) {
        if(r){
            var ids= "ids=";
            for(var i=0;i<rows.length;i++){
                if(i<rows.length-1){
                    ids=ids+rows[i].id+"&ids=";
                }else {
                    ids=ids+rows[i].id
                }
            }
            $.ajax({
                url:ctx+"/sale_chance/delete",
                type:"post",
                data:ids,
                dataType:"json",
                success:function (data) {
                    // var data=JSON.parse(data);
                    if(data.code==200){
                        //刷新表格
                        searchSaleChance();
                    }else {
                        $.messager.alert("系统消息",data.msg,"error")
                    }
                }
            })
        }
    })
}