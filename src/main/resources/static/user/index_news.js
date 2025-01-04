

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    loadNews(0); // 默认加载所有新闻 //ct

    // 使用事件委托，将点击事件绑定到父元素上
    document.getElementById('contentox').addEventListener('click', function(event) {
        // 检查点击的是否是分类按钮
        if (event.target.classList.contains('category-btn')) {
            event.preventDefault();
            event.stopPropagation();

            const button = event.target;
            const category = button.getAttribute('data-category');
            console.log('Category clicked:', category); // 调试日志

            // 检查是否所有按钮都被点击过
            const allButtons = document.querySelectorAll('.category-btn');
            const clickedButtons = document.querySelectorAll('.category-btn.clicked');

            // 如果当前按钮已经点击过且所有按钮都被点击过
            if (button.classList.contains('clicked') && clickedButtons.length === allButtons.length) {
                // 移除所有按钮的clicked类
                allButtons.forEach(btn => btn.classList.remove('clicked'));
                // 只给当前按钮添加clicked类
                button.classList.add('clicked');
            } else {
                // 正常添加clicked类
                button.classList.add('clicked');
            }

            // 处理分类逻辑
            if (button.classList.contains('active')) {
                // 如果按钮已激活，取消激活并显示所有新闻
                button.classList.remove('active');
                loadNews(0); //ct
            } else {
                // 移除其他按钮的active类
                allButtons.forEach(btn => btn.classList.remove('active'));
                // 激活当前按钮并加载分类新闻
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
            let pageInfo = document.getElementById('page-info')
            let pageInfoText = pageInfo.innerText
            // 使用正则表达式匹配页码
            const regex = /第 (\d+) 页 \/ 共 (\d+) 页/;
            const match = pageInfoText.match(regex);

            if (match) {
                // 如果匹配成功，提取当前页码和总页数
                let currentPage = parseInt(match[1], 10);
                const totalPages = parseInt(match[2], 10);

                if (currentPage>0) {
                    // currentPage -= 1
                    loadNews(currentPage-2)
                    pageInfo.innerText = `第 ${currentPage} 页 / 共 ${totalPages} 页`
                }else {
                    console.log("页码错误...")
                }
            } else {
                console.log("没有匹配到页码信息");
            }
        }
    });

    // 下一页
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'next-page') {
            let pageInfo = document.getElementById('page-info')
            let pageInfoText = pageInfo.innerText
            // 使用正则表达式匹配页码
            const regex = /第 (\d+) 页 \/ 共 (\d+) 页/;
            const match = pageInfoText.match(regex);

            if (match) {
                // 如果匹配成功，提取当前页码和总页数
                let currentPage = parseInt(match[1], 10);
                const totalPages = parseInt(match[2], 10);

                if (currentPage<Math.ceil(totalPages / 3)) {
                    loadNews(currentPage)
                    currentPage += 1
                    console.log(currentPage)
                    pageInfo.innerText = `第 ${currentPage} 页 / 共 ${totalPages} 页`
                }
            } else {
                console.log("没有匹配到页码信息");
            }
        }
    });

    // 跳转
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'go-to-page') {
            let pageInfo = document.getElementById('page-info')
            let pageInput = document.getElementById('page-input')
            let pageInfoText = pageInfo.innerText
            let pageInputText = pageInput.value
            // 使用正则表达式匹配页码
            const regex = /第 (\d+) 页 \/ 共 (\d+) 页/;
            const match = pageInfoText.match(regex);
            console.log('pageInputText--->',pageInputText)
            if (match) {
                // 如果匹配成功，提取当前页码和总页数
                let currentPage = parseInt(match[1], 10);
                const totalPages = parseInt(match[2], 10);

                if (pageInputText>0 && pageInputText<=Math.ceil(totalPages / 3)) {
                    loadNews(pageInputText-1)
                    pageInfo.innerText = `第 ${ pageInputText } 页 / 共 ${ totalPages } 页`
                }else {
                    console.log("页码错误...")
                }

            } else {
                console.log("没有匹配到页码信息");
            }
        }
    });
    //ct
});

// 加载新闻数据的函数
function loadNews(pageNum) {
    axios({
        url: 'http://localhost:8080/news/selectAllNews',
        method: 'get',
        //ct
        params:{
            pageNum: pageNum,
            pageSize: 3
        }
        //ct
    }).then(result => {
        if (result.data.code === 200) {
            if (result.data.data) {
                //ct
                if (pageNum === 0){
                    renderNewsTotal(result.data.total)
                }
                //ct
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

    // 只取前3条数据
    const limitedNewsList = newsList.slice(0, 3);

    limitedNewsList.forEach(news => {
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

