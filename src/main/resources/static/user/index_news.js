// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    loadNews(1); // 修改为从第1页开始加载，而不是0

    // 使用事件委托，将点击事件绑定到父元素上
    document.getElementById('contentox').addEventListener('click', function(event) {
        // 检查点击的是否是分类按钮
        if (event.target.classList.contains('category-btn')) {
            event.preventDefault();
            event.stopPropagation();

            const button = event.target;
            const category = button.getAttribute('data-category');
            console.log('Category clicked:', category);

            // 如果当前按钮已经有样式，则取消样式并加载所有新闻
            if (button.classList.contains('clicked')) {
                button.classList.remove('clicked');
                button.classList.remove('active');
                loadNews(1);
            } else {
                // 先移除所有按钮的样式
                document.querySelectorAll('.category-btn').forEach(btn => {
                    btn.classList.remove('clicked');
                    btn.classList.remove('active');
                });
                // 给当前按钮添加样式并加载分类新闻
                button.classList.add('clicked');
                button.classList.add('active');
                loadNewsBySort(category);
            }
        }
    });
    //ct
    // 搜索
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'search-btn') {
            selectNews(0)
        }
    });

    // 上一页
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'prev-page') {
            const pageInfo = document.getElementById('page-info').innerText;
            const match = pageInfo.match(/第 (\d+) 页/);
            if (match) {
                const currentPage = parseInt(match[1]);
                if (currentPage > 1) {
                    loadNews(currentPage - 1);
                }
            }
        }
    });

    // 下一页
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'next-page') {
            const pageInfo = document.getElementById('page-info').innerText;
            const match = pageInfo.match(/第 (\d+) 页 \/ 共 (\d+) 页/);
            if (match) {
                const currentPage = parseInt(match[1]);
                const totalPages = parseInt(match[2]);
                if (currentPage < totalPages) {
                    loadNews(currentPage + 1);
                }
            }
        }
    });

    // 跳转
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'go-to-page') {
            let pageInfo = document.getElementById('page-info');
            let pageInput = document.getElementById('page-input');
            let pageInfoText = pageInfo.innerText;
            let pageInputText = parseInt(pageInput.value);
            // 使用正则表达式匹配页码
            const regex = /第 (\d+) 页 \/ 共 (\d+) 页/;
            const match = pageInfoText.match(regex);

            if (match) {
                // 如果匹配成功，提取总页数
                const totalPages = parseInt(match[2], 10);

                // 验证输入的页码并直接跳转
                if (pageInputText > 0 && pageInputText <= totalPages) {
                    loadNews(pageInputText);
                }
                // 清空输入框
                pageInput.value = '';
            }
        }
    });
    //ct
});

// 加载新闻数据的函数
function loadNews(pageNum) {
    // 确保页码不小于1
    pageNum = Math.max(1, pageNum);

    axios({
        url: 'http://localhost:8080/news/selectAllNewsList',
        method: 'get',
        params:{
            pageNum: pageNum,
            pageSize: 3
        }
    }).then(result => {
        if (result.data.code === 200) {
            if (result.data.data) {
                renderNewsList(result.data.data);
                // 更新分页信息
                const totalPages = result.data.total;
                document.getElementById('page-info').innerText =
                    `第 ${pageNum} 页 / 共 ${totalPages} 页`;

                // 更新按钮状态
                const prevButton = document.getElementById('prev-page');
                const nextButton = document.getElementById('next-page');

                // 禁用或启用上一页按钮
                if (pageNum <= 1) {
                    prevButton.disabled = true;
                    prevButton.classList.add('disabled');
                } else {
                    prevButton.disabled = false;
                    prevButton.classList.remove('disabled');
                }

                // 禁用或启用下一页按钮
                if (pageNum >= totalPages) {
                    nextButton.disabled = true;
                    nextButton.classList.add('disabled');
                } else {
                    nextButton.disabled = false;
                    nextButton.classList.remove('disabled');
                }
            } else {
                document.getElementById('news-list').innerHTML = '<div class="news-item">暂无新闻</div>';
            }
        }
    }).catch(error => {
        console.error('请求失败：', error);
    });
}
//ct
function selectNews(pageNum) {
    let title = document.getElementById('news-search').value
    // console.log('title-->',title)
    axios({
        url: 'http://localhost:8080/news/selectNews',
        method: 'get',
        params: {
            title: title,
            pageNum: pageNum,
            pageSize: 3
        }
    }).then(result => {
        if (result.data.code === 200) {
            if (result.data.data) {
                renderNewsList(result.data.data);
            } else {
                document.getElementById('news-list').innerHTML = '<div class="news-item">暂无新闻</div>';
            }
        } else {
            console.error('加载新闻失败：', result.data.msg);
        }
    }).catch(error => {
        console.error('请求失败：', error);
    });
}
//ct
// 渲染新闻列表
function renderNewsList(newsList) {
    const newsListElement = document.getElementById('news-list');
    newsListElement.innerHTML = '';

    // 移除这个限制，因为后端已经做了分页
    // const limitedNewsList = newsList.slice(0, 3);

    newsList.forEach(news => {
        const newsItem = document.createElement('div');
        newsItem.className = 'news-item';
        newsItem.innerHTML = `
            <h3>${news.title}</h3>
            <h4>作者：${news.username}</h4>
            <h4>分类：${news.sort}</h4>
            <h6>发布时间：${news.date}</h6>
            <p>${news.content}</p>
        `;
        newsListElement.appendChild(newsItem);
    });
}
//ct
// 渲染新闻总条数
function renderNewsTotal(total) {
    let pageInfo = document.getElementById('page-info')
    pageInfo.innerHTML = `第 1 页 / 共 ${total} 页`;
}
//ct
// 加载分类新闻
function loadNewsBySort(sort) {
    let sortType;
    switch(sort) {
        case 'technology':
            sortType = '科技';
            break;
        case 'culture':
            sortType = '文化';
            break;
        case 'sports':
            sortType = '体育';
            break;
        default:
            sortType = sort;
    }

    console.log('Loading news for category:', sortType); // 调试日志

    axios({
        url: 'http://localhost:8080/news/selectNewsBySort',
        method: 'get',
        params: {
            sort: sortType
        }
    }).then(result => {
        console.log('API response:', result); // 调试日志
        if (result.data.code === 200) {
            if (result.data.data && result.data.data.length > 0) {
                renderNewsList(result.data.data);
            } else {
                document.getElementById('news-list').innerHTML =
                    `<div class="news-item" style="text-align: center;">
                        <h3>${sortType}暂无新闻</h3>
                    </div>`;
            }
        } else {
            console.error('加载新闻失败：', result.data.msg);
            document.getElementById('news-list').innerHTML =
                `<div class="news-item" style="text-align: center;">
                    <h3>加载失败：${result.data.msg}</h3>
                </div>`;
        }
    }).catch(error => {
        console.error('请求失败：', error);
        document.getElementById('news-list').innerHTML =
            `<div class="news-item" style="text-align: center;">
                <h3>请求失败，请稍后重试</h3>
            </div>`;
    });
}

// 修改模块切换事件
document.getElementById('module-news').addEventListener('click', function() {
    document.querySelectorAll('.module-name').forEach(m => {
        m.classList.remove('active');
    });
    this.classList.add('active');

    // 加载新闻页面内容
    const contentox = document.getElementById('contentox');
    contentox.innerHTML = `
        <div id="news-page" class="module-page">
            <h2 style="text-align: center; color: #333;">最新新闻</h2>
            <div id="sidebar">
                <ul>
                    <li><button class="category-btn button" data-category="technology">科技</button></li>
                    <li><button class="category-btn button" data-category="culture">文化</button></li>
                    <li><button class="category-btn button" data-category="sports">体育</button></li>
                </ul>
            </div>
            <div class="search-bar">
                <input type="text" id="news-search" placeholder="搜索新闻标题或作者..." aria-label="搜索新闻">
                <button id="search-btn" class="button">搜索</button>
            </div>
            <div id="news-list">
                <!-- 新闻列表将通过Ajax动态加载 -->
            </div>
            <div id="pagination">
                <button id="prev-page" class="page-btn button">上一页</button>
                <span id="page-info"></span>
                <input type="number" id="page-input" class="page-input" min="1" placeholder="跳转到页">
                <button id="go-to-page" class="page-btn button">跳转</button>
                <button id="next-page" class="page-btn button">下一页</button>
            </div>
        </div>
    `;

    // 重新绑定事件监听器
    document.getElementById('contentox').addEventListener('click', function(event) {
        // 分类按钮点击事件
        if (event.target.classList.contains('category-btn')) {
            event.preventDefault();
            event.stopPropagation();

            const button = event.target;
            const category = button.getAttribute('data-category');

            if (button.classList.contains('clicked')) {
                button.classList.remove('clicked');
                button.classList.remove('active');
                loadNews(1);  // 修改为从第1页开始
            } else {
                document.querySelectorAll('.category-btn').forEach(btn => {
                    btn.classList.remove('clicked');
                    btn.classList.remove('active');
                });
                button.classList.add('clicked');
                button.classList.add('active');
                loadNewsBySort(category);
            }
        }
    });

    // 重新绑定搜索按钮事件
    document.getElementById('search-btn').addEventListener('click', function() {
        selectNews(0);
    });

    // 重新绑定分页按钮事件
    document.getElementById('prev-page').addEventListener('click', function() {
        const pageInfo = document.getElementById('page-info').innerText;
        const match = pageInfo.match(/第 (\d+) 页/);
        if (match) {
            const currentPage = parseInt(match[1]);
            if (currentPage > 1) {
                loadNews(currentPage - 1);
            }
        }
    });

    document.getElementById('next-page').addEventListener('click', function() {
        const pageInfo = document.getElementById('page-info').innerText;
        const match = pageInfo.match(/第 (\d+) 页 \/ 共 (\d+) 页/);
        if (match) {
            const currentPage = parseInt(match[1]);
            const totalPages = parseInt(match[2]);
            if (currentPage < totalPages) {
                loadNews(currentPage + 1);
            }
        }
    });

    document.getElementById('go-to-page').addEventListener('click', function() {
        const pageInput = document.getElementById('page-input');
        const pageInfo = document.getElementById('page-info');
        const inputValue = parseInt(pageInput.value);
        const match = pageInfo.innerText.match(/第 \d+ 页 \/ 共 (\d+) 页/);

        if (match) {
            const totalPages = parseInt(match[1]);

            // 验证输入的页码
            if (inputValue > 0 && inputValue <= totalPages) {
                loadNews(inputValue);
                pageInput.value = ''; // 清空输入框
            } else {
                alert(`请输入1到${totalPages}之间的页码！`);
                pageInput.value = ''; // 清空输入框
            }
        }
    });

    // 加载第一页新闻
    loadNews(1);
});


// 在搜索时保存搜索内容
document.body.addEventListener('click', function(event) {
    if (event.target.id === 'search-btn') {
        const searchQuery = document.getElementById('news-search').value;
        sessionStorage.setItem('searchQuery', searchQuery);
    }
});

// 工作站模块切换
document.getElementById('module-workstation').addEventListener('click', function() {
    document.querySelectorAll('.module-name').forEach(m => {
        m.classList.remove('active');
    });
    this.classList.add('active');

    const contentox = document.getElementById('contentox');
    contentox.innerHTML = `
        <div id="workstation-page" class="module-page">
            <h2 style="text-align: center; color: #333;">工作站</h2>
            <!-- 工作站的其他内容 -->
        </div>
    `;
});





