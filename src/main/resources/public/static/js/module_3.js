function formatterGrade(value) {
    if (value==0){
        return "一级菜单";
    }else if (value==1){
        return "二级菜单";
    }else if (value==2){
        return "三级菜单";
    }else{
        return "未知";
    }
}
function searchModules() {
    $('#dg').datagrid("load",{
        moduleName:$("#s_moduleName").val(),
        code:$("#s_code").val()
    })
}

function clearData() {
    $("#moduleName").val("");
    $("#moduleStyle").val("");
    $("#orders").val("");
    $("#optValue").val("");
    $("#url").val("");
    $("#parentId").combobox("setValue","");
    $("input[name='id']").val("");
}

function openModuleAddDialog() {
    openDialog("dlg","添加菜单");
}
function closeModuleDialog() {
    closeDialog("dlg");
}
function saveOrUpdateModule() {
    saveOrUpdateRecode(ctx+"/module/save",ctx+"/module/update","dlg",searchModules,clearData)
}
function openModuleModifyDialog() {
    openModifyDialog("dg","fm","dlg","更新菜单");
}

function deleteModule() {
    deleteRecode("dg",ctx+"/module/delete",searchModules)
}