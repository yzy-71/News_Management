function sendCode() {
    const phone = document.getElementById('phone').value;
    if (!phone) {
        alert('请输入手机号');
        return;
    }

    document.getElementById('sendCode').disabled = true;
    let countdown = 60;
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

function sendEmailCode() {
    const email = document.getElementById('email').value;
    if (!email) {
        alert('请输入邮箱');
        return;
    }

    document.getElementById('sendEmailCode').disabled = true;
    let countdown = 60;
    let timer = setInterval(function () {
        document.getElementById('sendEmailCode').textContent = countdown + "秒后重发";
        countdown--;
        if (countdown <= 0) {
            clearInterval(timer);
            document.getElementById('sendEmailCode').textContent = "发送验证码";
            document.getElementById('sendEmailCode').disabled = false;
            countdown = 60;
        }
    }, 1000);

    // 这里可以调用后端API发送邮箱验证码
    console.log('发送验证码到邮箱：', email);
}

function previewImage(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const avatarPreview = document.getElementById('avatarPreview');
            avatarPreview.src = e.target.result;
            avatarPreview.style.display = 'block';
        }
        reader.readAsDataURL(file);
    }
}

document.getElementById('registerPhoneForm').onsubmit = function (e) {
    e.preventDefault();
    // 手机号注册逻辑
    const account = document.getElementById('account').value;
    const username = document.getElementById('username').value;
    const phone = document.getElementById('phone').value;
    const phoneCode = document.getElementById('phoneCode').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const avatar = document.getElementById('avatar').files[0];
    const role = document.getElementById('role').value;

    if (!account || !username || !phone || !phoneCode || !password || !confirmPassword || !avatar) {
        alert('请填写完整信息');
        return;
    }

    if (password !== confirmPassword) {
        alert('密码和确认密码不一致');
        return;
    }

    // 这里可以添加手机号注册的逻辑，比如调用后端接口
    alert("手机号注册成功！");
}

document.getElementById('registerEmailForm').onsubmit = function (e) {
    e.preventDefault();
    // 邮箱注册逻辑
    const account = document.getElementById('account').value;
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const emailCode = document.getElementById('emailCode').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const avatar = document.getElementById('avatar').files[0];
    const role = document.getElementById('role').value;

    if (!account || !username || !email || !emailCode || !password || !confirmPassword || !avatar) {
        alert('请填写完整信息');
        return;
    }

    if (password !== confirmPassword) {
        alert('密码和确认密码不一致');
        return;
    }

    // 这里可以添加邮箱注册的逻辑，比如调用后端接口
    alert("邮箱注册成功！");
}
