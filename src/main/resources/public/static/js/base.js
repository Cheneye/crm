
function openDialog(dialogId,title) {
    $("#"+dialogId).dialog("open").dialog("setTitle",title);
}
function closeDialog(dialogId) {
    $("#"+dialogId).dialog("close");
}

/**
 * 添加与更新记录
 * @param saveUrl    添加记录后端url 地址
 * @param updateUrl   更新记录后端url 地址
 * @param dialogId     对话框id
 * @param search    多条件搜索方法名
 * @param clearData   清除表单方法名
 */
function saveOrUpdateRecode(saveUrl,updateUrl,dialogId,search,clearData) {
    var url=saveUrl;
    var id = $("input[name='id']").val();
    if(!isEmpty(id)){
        var url=updateUrl;
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
                closeDialog(dialogId);
                $.messager.alert("系统消息",data.msg,"success")
                //刷新页面
                search();
                //清空表单
                clearData();
            }else {
                $.messager.alert("系统消息",data.msg,"error")
            }
        }
    })
}

/**
 *
 * @param dataGridId  表格id
 * @param formId      表单id
 * @param dialogId      对话框id
 * @param title       对话框标题
 */
function openModifyDialog(dataGridId,formId,dialogId,title) {
    var rows=$("#"+dataGridId).datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待修改的数据!","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量修改!","warning");
        return;
    }

    $("#"+formId).form("load",rows[0]);
    openDialog(dialogId,title);
}


/**
 *
 * @param dataGridId  表格id
 * @param deleteUrl   后端删除url 地址
 * @param search     搜索方法
 */
function deleteRecode(dataGridId,deleteUrl,search) {
    var rows=$("#"+dataGridId).datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待删除记录！","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量删除！","warning");
        return;
    }
    $.messager.confirm("系统消息","确定删除选中的记录?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:deleteUrl,
                data:{
                    id:rows[0].id
                },
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        search();
                        $.messager.alert("系统消息",data.msg,"success");
                    }else{
                        $.messager.alert("系统消息",data.msg,"error");
                    }
                }
            })

        }
    })
}

