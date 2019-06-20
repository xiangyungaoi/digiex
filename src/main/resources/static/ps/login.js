$(function(){

    loginInit();
});

/**
 * 登录
 */
function loginInit(){
    $('.js-logBtn').on("click",function(){
        var userName = $("input[name='userName']").val(),
            passWord = $("input[name='passWord']").val();
        if (helper.isEmpty(userName)) {
            layer.alert('登录名不能为空！',{icon: 5,area:'500px'});
            return;
        }else if(helper.isEmpty(passWord)){
            layer.alert('密码不能为空！',{icon: 5,area:'500px'});
            return;
        }
        login(userName,passWord);
    });
    $(document).keydown(function(e){
        if(e.keyCode == 13){
            var userName = $("input[name='userName']").val(),
                passWord = $("input[name='passWord']").val();
            if (helper.isEmpty(userName)) {
                layer.alert('登录名不能为空！',{icon: 5,area:'500px'});
                return;
            }else if(helper.isEmpty(passWord)){
                layer.alert('密码不能为空！',{icon: 5,area:'500px'});
                return;
            }
            login(userName,passWord);
        }
    });
}

/**
 * 用户登录
 * @param userName
 * @param passWord
 */
function login(userName,passWord){
    var req = {"username":userName,"password":passWord};
    doRequestwithnoheader(req,"post",urlData.login,function(res){
        window.location.href = basePath + "/index.html";
        helper.setCookie("TOKEN",res.data.username);
    },function(res){
    });
}


