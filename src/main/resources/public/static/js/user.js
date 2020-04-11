
function searchUsers() {
    var userName=$("#s_userName").val();
    var phone=$("#s_phone").val();
    var trueName=$("#s_trueName").val();
    $("#dg").datagrid("load",{
        userName:userName,
        phone:phone,
        trueName:trueName
    })
}

function  clearFormData(){
    $("#userName").val("");
    $("#email").val("");
    $("#trueName").val("");
    $("#phone").val("");
    $("input[name='id']").val("");
}

function openUserAddDialog() {
    $("#dlg").dialog("open").dialog("setTitle","添加用户");
}
function closeUserDialog() {
    $("#dlg").dialog("close");
}
function saveOrUpdateUser() {
    var url=ctx+"/user/save";
    var id = $("input[name='id']").val();
    if(!isEmpty(id)){
        var url=ctx+"/user/update";
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
                closeUserDialog();
                $.messager.alert("系统消息",data.msg,"success")
                //刷新页面
                searchUsers();
                //清空表单
                clearFormData();
            }else {
                $.messager.alert("系统消息",data.msg,"error")
            }
        }
    })
}

function openUserModifyDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待更新记录！","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量更新！","warning");
        return;
    }
    $("#fm").form("load",rows[0]);
    $("#dlg").dialog("open").dialog("setTitle","更新用户");
}
function deleteUser() {
    var rows=$("#dg").datagrid("getSelections");
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
                url:ctx+"/user/delete",
                data:{
                    userId:rows[0].id
                },
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        searchUsers();
                        $.messager.alert("系统消息",data.msg,"success");
                    }else{
                        $.messager.alert("系统消息",data.msg,"error");
                    }
                }
            })

        }
    })
}