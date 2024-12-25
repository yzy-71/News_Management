// common.js
document.addEventListener("DOMContentLoaded", function () {
    // 绑定 #nav-bar 中的按钮点击事件
    const navBarModuleNames = document.querySelectorAll('#nav-bar .module-name');
    navBarModuleNames.forEach(module => {
        let isLoading = false; // 添加状态标志
        module.addEventListener('click', function () {
            if (isLoading) return; // 如果正在加载，直接返回
            isLoading = true; // 设置为正在加载状态
            loadModule(module.dataset.module, 'contentox');
            setTimeout(() => isLoading = false, 1000); // 1秒后重置状态
        });
    });

    // 绑定 #workstation-page 中的按钮点击事件
    const workstationPageModuleNames = document.querySelectorAll('#workstation-page .module-name');
    workstationPageModuleNames.forEach(module => {
        let isLoading = false; // 添加状态标志
        module.addEventListener('click', function () {
            if (isLoading) return; // 如果正在加载，直接返回
            isLoading = true; // 设置为正在加载状态
            loadModule(module.dataset.module, 'content');
            setTimeout(() => isLoading = false, 1000); // 1秒后重置状态
        });
    });


    // 默认加载模块
    const defaultNavBarModule = document.querySelector('#nav-bar .module-name.active');
    if (defaultNavBarModule) {
        loadModule(defaultNavBarModule.dataset.module, 'contentox');
    }

    const defaultWorkstationModule = document.querySelector('#workstation-page .module-name.active');
    if (defaultWorkstationModule) {
        loadModule(defaultWorkstationModule.dataset.module, 'content');
    }

    // 新增：绑定新闻表格中的编辑按钮点击事件
    const editButtons = document.querySelectorAll('#news-table .edit-btn');
    editButtons.forEach(button => {
        button.addEventListener('click', function () {
            const moduleName = button.dataset.module; // 获取模块名
            console.log(moduleName);
            loadModule(moduleName, 'box'); // 在 box 中加载 edit.html
        });
    });

    // 新增：绑定审核箱中的编辑按钮点击事件
    const reviewEditButtons = document.querySelectorAll('#review-box .edit-btn');
    reviewEditButtons.forEach(button => {
        button.addEventListener('click', function () {
            const moduleName = button.dataset.module; // 获取模块名
            console.log(moduleName);
            loadModule(moduleName, 'contentox'); // 在 contentox 中加载对应模块
        });
    });

    // 新增：绑定取消按钮点击事件


    // 新增：绑定模块内的点击事件
    bindModuleClickEvents();
});

function loadModule(moduleName, contentId) {
    const contentDiv = document.getElementById(contentId); // 根据传入的 contentId 获取内容区域
    if (!contentDiv) {
        console.error(`内容区域未找到: ${contentId}`); // 添加错误处理
        return; // 如果内容区域未找到，提前返回
    }
    console.log(`加载模块: ${moduleName} 到区域: ${contentId}`);

    // 新增：更新导航栏模块颜色
    const navBarModuleNames = document.querySelectorAll('#nav-bar .module-name');
    navBarModuleNames.forEach(module => {
        module.classList.remove('active'); // 移除所有模块的激活状态
    });
    const activeModule = document.querySelector(`#nav-bar .module-name[data-module="${moduleName}"]`);
    if (activeModule) {
        activeModule.classList.add('active'); // 添加激活状态
    }

    fetch(`${moduleName}.html`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`网络响应错误: ${response.status}`);
            }
            return response.text();
        })
        .then(data => {
            contentDiv.innerHTML = data; // 加载模内容到目标区域

            // 更新导航栏样式
            const allModuleNames = document.querySelectorAll(`#${contentId} .module-name`);
            allModuleNames.forEach(module => module.classList.remove('active'));

            const activeModule = document.getElementById(`${contentId}-module-${moduleName}`);
            if (activeModule) {
                activeModule.classList.add('active');
            }

            bindModuleClickEvents(); // 绑定模块内的点击事件

            // 绑定新加载模块中的编辑按钮点击事件
            bindEditButtonClickEvents(); // 新增绑定编辑按钮事件
        })
        .catch(error => console.error('加载模块失败:', error));
}

// 新增：绑定模块内的点击事件
function bindModuleClickEvents() {
    const moduleNames = document.querySelectorAll('#workstation-page .module-name');
    moduleNames.forEach(module => {
        // 移除之前的事件监听器，以防止重复绑定
        module.removeEventListener('click', handleClick);
        module.addEventListener('click', handleClick);
    });
}

// 处理点击事件的函数
function handleClick(event) {
    let isLoading = false; // 添加状态标志
    if (isLoading) return; // 如果正在加载，直接返回
    isLoading = true; // 设置为正在加载状态
    loadModule(event.currentTarget.dataset.module, 'content');
    setTimeout(() => isLoading = false, 1000); // 1秒后重置状态
}

// 新增：绑定新加载模块中的编辑按钮点击事件
function bindEditButtonClickEvents() {
    const editButtons = document.querySelectorAll('#news-table .edit-btn, #review-box .edit-btn');
    editButtons.forEach(button => {
        button.addEventListener('click', function () {
            const moduleName = button.dataset.module; // 获取模块名
            console.log(moduleName);
            loadModule(moduleName, 'box'); // 在 box 中加载对应模块
        });
    });

    // 新增：绑定新加载模块中的取消按钮点击事件
    const cancelButtons = document.querySelectorAll('#box .cancel');
    cancelButtons.forEach(button => {
        button.addEventListener('click', function () {
            loadModule('empty-module', 'box'); // 在 box 中加载空模块
        });
    });
    const submitted = document.querySelectorAll('#box .save-draft');
    submitted.forEach(button => {
        button.addEventListener('click', function () {
            loadModule('empty-module', 'box'); // 在 box 中加载空模块
        });
    });
    const cancelButtonsall = document.querySelectorAll('#content .cancel');
    cancelButtonsall.forEach(button => {
        button.addEventListener('click', function () {
            loadModule('empty-module', 'content'); // 在 box 中加载空模块
        });
    });

    const submittedall = document.querySelectorAll('#content .save-draft');
    submittedall.forEach(button => {
        button.addEventListener('click', function () {
            loadModule('empty-module', 'content');
        });
    });


    // 

    const submitteone = document.querySelectorAll('#content .submit');
    submitteone.forEach(button => {
        button.addEventListener('click', function () {
            loadModule('empty-module', 'content');
        });
    });



    // 测试请求(可删)
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
    } else {
        alert('验证码发送失败，请稍后重试');
    }
    }).catch((error) => {
        alert('请求失败：' + error);
    });
    });
}