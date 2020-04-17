$(function () {
    $("#dg").edatagrid({
        url:ctx+"/customer_reprieve/list?lossId="+$("#lossId").val(),
        saveUrl:ctx+"/customer_reprieve/save?lossId="+$("#lossId").val(),
        updateUrl:ctx+"/customer_reprieve/update",
        destroyUrl:ctx+"/customer_reprieve/delete"
    })
    if($("#state").val()==0){
        $("#toolbar a").show();
    }else if($("#state").val()==1){
        $("#dg").edatagrid("disableEditing");
        $("#toolbar a").hide();
    }
})
function saveCustomerRep() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}
function delCustomerRep() {

    $("#dg").edatagrid("destroyRow");
    $("#dg").edatagrid("load");
}

function confirmLoss() {
    $.messager.prompt('提示信息', '请输入流失原因：', function(r){
        if (r){
            console.log($("#lossId").val());
            $.ajax({
                url:ctx+"/customer_loss/confirmLoss",
                data:{
                    lossReason:r,
                    lossId:$("#lossId").val()
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(data.code==200){

                        $("#dg").edatagrid("load");

                    }
                }
            })
        }
    });

}


