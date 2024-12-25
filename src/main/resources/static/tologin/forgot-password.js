let countdown = 60;  // 定义倒计时变量

function sendCode() {
    const phone = document.getElementById('phone').value;
    if (!phone) {
        alert('请输入手机号');
        return;
    }

    // 禁用发送验证码按钮
    document.getElementById('sendCode').disabled = true;
    let timer = setInterval(function () {
        document.getElementById('sendCode').textContent = countdown + "秒后重发";
        countdown--;
        if (countdown <= 0) {
            clearInterval(timer);
            document.getElementById('sendCode').textContent = "发送验证码";
            document.getElementById('sendCode').disabled = false;
            countdown = 60;
        }
    }, 1000);

    // 这里可以调用后端API发送验证码
    console.log('发送验证码到手机号：', phone);
}

document.getElementById('forgotPasswordForm').onsubmit = function (e) {
    e.preventDefault();
    
    const phone = document.getElementById('phone').value;
    const phoneCode = document.getElementById('phoneCode').value;
    const newPassword = document.getElementById('newPassword').value;
    
    if (!phone || !phoneCode || !newPassword) {
        alert('请填写完整信息');
        return;
    }

    // 在这里可以添加验证验证码的逻辑，比如调用后端接口
    // 例如：validatePhoneCode(phone, phoneCode).then(response => {...})
    alert("密码重置成功！");
}
