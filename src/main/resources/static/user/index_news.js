
// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    loadNews(); // 默认加载所有新闻

    // 使用事件委托，将点击事件绑定到父元素上
    document.getElementById('contentox').addEventListener('click', function(event) {
        // 检查点击的是否是分类按钮
        if (event.target.classList.contains('category-btn')) {
            event.preventDefault();
            event.stopPropagation();

            const category = event.target.getAttribute('data-category');
            console.log('Category clicked:', category); // 调试日志

            // 移除所有按钮的active类
            document.querySelectorAll('.category-btn').forEach(btn => {
                btn.classList.remove('active');
            });
            // 添加active类到当前点击的按钮
            event.target.classList.add('active');

            // 加载分类新闻
            loadNewsBySort(category);
        }
    });
});

// 加载新闻数据的函数
function loadNews() {
    axios({
        url: 'http://localhost:8080/news/selectAllNews',
        method: 'get'
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

// 渲染新闻列表
function renderNewsList(newsList) {
    const newsListElement = document.getElementById('news-list');
    newsListElement.innerHTML = '';
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
                        <h3>${sortType}分类暂无新闻</h3>
                    </div>`;
            }
        } else {
            console.error('加载新闻失败：', result.data.msg);
        }
    }).catch(error => {
        console.error('请求失败：', error);
    });
}

// 模块切换事件
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
                <span id="page-info">第 1 页 / 共 10 页</span>
                <input type="number" id="page-input" class="page-input" min="1" placeholder="跳转到页">
                <button id="go-to-page" class="page-btn button">跳转</button>
                <button id="next-page" class="page-btn button">下一页</button>
            </div>
        </div>
    `;

    // 加载新闻
    loadNews();
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

