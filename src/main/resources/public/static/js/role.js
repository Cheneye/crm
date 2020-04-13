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
var zTreeObj;
var roleId;
function openAddModuleDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("系统消息","请选择待授权记录！","warning");
        return;
    }
    if(rows.length>1){
        $.messager.alert("系统消息","暂不支持批量授权！","warning");
        return;
    }
    roleId=rows[0].id;
    $.ajax({
        url:ctx+"/module/queryAllModules",
        type:"post",
        data:{
            roleId:roleId
        },
        dataType:"json",
        success:function (data) {
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                check: {
                    enable: true
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            var zNodes =data;
            zTreeObj=$.fn.zTree.init($("#treeDemo"), setting, zNodes);
            openDialog("module","角色授权");
        }
    })
}
function zTreeOnCheck(event, treeId, treeNode) {
    /**
     * 获取ztree中所有选中的节点
     */
    var nodes=zTreeObj.getCheckedNodes(true);
    // console.log(nodes);
    var mids="mids=";
    for(var i=0;i<nodes.length;i++){
        if(i<nodes.length-1){
            mids=mids+nodes[i].id+"&mids=";
        }else{
            mids=mids+nodes[i].id;
        }
    }
    // console.log(mids);
    $.ajax({
        type:"post",
        url:ctx+"/role/addGrant",
        data:mids+"&roleId="+roleId,
        dataType:"json",
        success:function (data) {
            // console.log(data);
        }
    })
};