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
function formatterOp(value,rowData) {
    var title=rowData.moduleName+"_二级菜单";
    var href='javascript:openSecondModule("'+title+'",'+rowData.id+')';
    return "<a href='"+href+"'>二级菜单</a>";
}
function openSecondModule(title,mid) {
    window.parent.openTab(title,ctx+"/module/index/1?mid="+mid);
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