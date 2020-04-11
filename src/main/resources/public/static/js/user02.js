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
    openDialog("dlg","添加用户")
}
function closeUserDialog() {
    closeDialog("dlg");
}
function saveOrUpdateUser() {
    saveOrUpdateRecode(ctx+"/user/save",ctx+"/user/update","dlg",searchUsers,clearFormData);
}

function openUserModifyDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待修改的数据!","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量修改!","warning");
        return;
    }

    rows[0].roleIds=rows[0].rids.split(",");
    $("#fm").form("load",rows[0]);

    openDialog("dlg","用户更新");
}
function deleteUser() {
    deleteRecode("dg",ctx+"/user/delete",searchUsers);
}