function saveCustomerServe() {
    $("#fm").form("submit",{
        url:ctx+"/customer_serve/saveOrUpdateCustomerServe",
        onSubmit:function (param) {
            param.createName=$.cookie("trueName");
            return  $("#fm").form("validate");
        },
        success:function(data){
            data =JSON.parse(data);
            if(data.code==200){
                $.messager.alert("系统消息","服务创建成功","success");
                clearFormData();
            }else{
                $.messager.alert("系统消息",data.msg,"error");
            }
        }
    })
}
function clearFormData() {
    $("input").val("");
    $("#serveType").combobox("setValue","");
}