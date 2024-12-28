<!-- 添加发送验证码的逻辑 -->
document.getElementById('sendCode').addEventListener('click', function () {
    const phone = document.getElementById('phone').value;
    const button = this;

    // 验证手机号格式
    if (!/^1[3-9]\d{9}$/.test(phone)) {
        alert('请输入正确的手机号码');
        return;
    }

    // 发送验证码
    axios({
        url: "http://localhost:8080/tAdmin/sendCode",
        method: "post",
        data: {
            phone: phone
        }
    }).then((result) => {
        if (result.data.code == 200) {
            // 发送成功后禁用按钮并开始倒计时
            let countdown = 60;
            button.disabled = true;

            const timer = setInterval(() => {
                button.textContent = `${countdown}秒后重新发送`;
                countdown--;

                if (countdown < 0) {
                    clearInterval(timer);
                    button.disabled = false;
                    button.textContent = '发送验证码';
                }
            }, 1000);
            alert('验证码已发送，请注意查收');
        }else if(result.data.code == 500) {
            alert('手机号不存在');
        } else {
            alert('验证码发送失败，请稍后重试');
        }
    }).catch((error) => {
        alert('请求失败：' + error);
    });
});
// ------------------------------------------------------------------------------------
let btnCode = document.querySelector(".btn-code");
btnCode.onclick = function () {
    //获取verifyCode和phone的值发送给服务器(后端)
    let verifyCode=document.querySelector("#phoneCode").value;
    let phone=document.querySelector("#phone").value;
    axios({
        url: "http://localhost:8080/tAdmin/sendCodeLogin",
        method: "post",
        data: {
            verifyCode: verifyCode,
            phone: phone
        }
    }).then((result) => {
        if (result.data.code == 200) {
            //跳转首页
            location.href = "../admin/admin.html"
        }else if(result.data.code == 201){
            location.href = "../user/index.html"
        }else if (result.data.code==505){
            alert("验证码错误，请重新输入");
        }
    })
}