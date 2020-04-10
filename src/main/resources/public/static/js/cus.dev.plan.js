/*function formatterOp(value,rowData) {
    console.log(rowData)
    var devResult = rowData.devResult;
    var href1='javascript:openCusDevPlanTab("查看客户开发计划详情"'+","+rowData.id+",0"+')';
    var href2='javascript:openCusDevPlanTab("客户开发计划管理"'+","+rowData.id+",1"+')';
    if(devResult==2 || devResult==3){
        return "<a href='"+href1+"'>查看详情</a>"
    }else{
        return "<a href='"+href2+"'>开发</a>"
    }
}
function openCusDevPlanTab(title,saleChanceId,flag) {
    window.parent.openTab(title,"saleChance/list?saleChanceId="+saleChanceId+" &show="+flag+"");
}*/
function formatterOp(value,rowData) {
    var devResult = rowData.devResult;
    var href="javascript:openCusDevPlanDialog()";
    if(devResult == 2|| devResult==3){
        return "<a href='"+href+"' >查看详情</a>";
    }else{
        return "<a href='"+href+"'>开发</a>"
    }

}
function openCusDevPlanDialog() {
    var recode =$("#dg").datagrid("getSelections")[0];
    console.log(recode);
    $("#fm").form("load",recode);
    if(recode.devResult==2 || recode.devResult==3){
        $("#toolbar a").hide();
        $("#dg02").edatagrid({
            url:ctx+"/cus_dev_plan/list?sid="+recode.id,
            saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+recode.id,
            updateUrl:ctx+"/cus_dev_plan/update",
            destroyUrl:ctx+"/cus_dev_plan/delete"
        });
        $("#dg02").edatagrid("disableEditing");
    }else{
        $("#toolbar a").show();
        $("#dg02").edatagrid({
            toolbar:"#toolbar",
            url:ctx+"/cus_dev_plan/list?sid="+recode.id,
            saveUrl:ctx+"/cus_dev_plan/save?saleChanceId="+recode.id,
            updateUrl:ctx+"/cus_dev_plan/update",
            destroyUrl:ctx+"/cus_dev_plan/delete"
        });
    }
    $("#dlg").dialog("open").dialog("setTitle","开发计划项展示");
}
function saveCusDevPlan() {
    $("#dg02").edatagrid("saveRow");
    $("#dg02").edatagrid("load");
}

function delCusDevPlan() {
    $("#dg02").edatagrid("destroyRow");
    $.messager.alert("系统消息",data.msg,"success");
    $("#dg02").edatagrid("load");
}

function updateSaleChanceDevResult(status) {
    $.ajax({
        url:ctx+"/sale_chance/updateDevResult",
        type:"post",
        data:{
            devResult:status,
            id:$("#dg").datagrid("getSelections")[0].id
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                $("#dg").edatagrid("load");
                /**
                 * 1.更新营销机会表格数据
                 * 2.隐藏开发计划项数据 工具栏
                 * 3.计划项表格数据不可编辑
                 */
                $("#dg02").edatagrid("disableEditing");
                $("#toolbar a").hide();
            }else {
                $.messager.alert("系统消息",data.msg,"error");
            }
        }

    })
}