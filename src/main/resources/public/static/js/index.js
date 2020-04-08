function login() {
   var userName= $("input[name='userName']").val();
   var userPwd = $("input[name='userPwd']").val();
   if(isEmpty(userName)){
       alert("请输入用户名!")
       return ;
   }
    if(isEmpty(userPwd)){
        alert("请输入密码!")
        return ;
    }
    $.ajax({
        type:"post",
        url:ctx+"/user/login",
        dataType:"json",
        data:{
            userName:userName,
            userPwd:userPwd
        },
        success: function (data) {
            if(data.code==200){
                var result=data.result;
                $.cookie("userIdStr",result.userIdStr);
                $.cookie("userName",result.userName);
                $.cookie("trueName",result.trueName);
                window.location.href=ctx+"/main";
            }else{
                alert(data.msg)
            }
        }
    })
}