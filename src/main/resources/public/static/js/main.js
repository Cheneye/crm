function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}


function logout() {
    $.messager.confirm("系统消息","你确定要退出吗？",function (r) {
        if(r){
            $.removeCookie("userIdStr");
            $.removeCookie("userName");
            $.removeCookie("trueName");
            $.messager.alert("系统消息","系统将在三秒后自动退出……","info")
            window.setTimeout(function () {
                window.location.href=ctx+"/index";
            },3000)
        }
    })
}


function openPasswordModifyDialog() {
    $("#dlg").dialog("open").dialog("setTitle","密码修改");
}
function closePasswordModifyDialog() {
    $("#dlg").dialog("close");
}

function modifyPassword() {
    $("#fm").form("submit",{
        url:ctx+"/user/updatePassword",
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            var data = JSON.parse(data);
            if(data.code==200){
                $.removeCookie("userIdStr");
                $.removeCookie("userName");
                $.removeCookie("trueName");
                $.messager.alert("系统消息","密码修改成功，系统将在三秒后退出，请重新登录……","info")
                window.setTimeout(function () {
                    window.location.href=ctx+"/index";
                },3000)
            }else{
                $.messager.alert("系统消息",data.msg,"error");
            }
        }
    })
}