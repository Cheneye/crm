function searchRoles() {
    $("#dg").datagrid("load",{
        roleName:$("#s_roleName").val()
    })
}

function clearData() {
    $("#roleName").val("");
    $("#roleRemark").val("");
    $("input[name='id']").val("");
}

function openRoleAddDialog() {
    openDialog("dlg","添加角色");
}
function closeRoleDialog() {
    closeDialog("dlg")
}

function openRoleModifyDialog() {
    openModifyDialog("dg","fm","dlg","更新角色")
}

function deleteRole() {
    deleteRecode("dg",ctx+"/role/delete",searchRoles)
}

function saveOrUpdateRole() {
    saveOrUpdateRecode(ctx+"/role/save",ctx+"/role/update","dlg",searchRoles,clearData)
}