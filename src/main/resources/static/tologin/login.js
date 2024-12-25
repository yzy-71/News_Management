// 模拟发送验证码的倒计时
let countdown = 60;

function sendCode() {
    const phone = document.getElementById('phone').value;
    if (!phone) {
        alert('请输入手机号');
        return;
    }

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
}

document.getElementById('loginForm').onsubmit = function (e) {
    e.preventDefault();
    // 登录逻辑，这里可以根据账号密码或验证码来判断
    alert("登录成功！");
}
