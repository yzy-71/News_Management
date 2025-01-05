
document.addEventListener('DOMContentLoaded', function() {
    loadNews(); // 默认加载所有新闻

    // 使用事件委托，将点击事件绑定到父元素上
    document.getElementById('news-page').addEventListener('click', function(event) {
        // 检查点击的是否是分类按钮
        if (event.target.classList.contains('category-btn')) {
            event.preventDefault();
            event.stopPropagation();

            const button = event.target;
            const category = button.getAttribute('data-category');
            console.log('Category clicked:', category);

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
                button.classList.remove('active');
                loadNews();
            } else {
                allButtons.forEach(btn => btn.classList.remove('active'));
                button.classList.add('active');
                loadNewsBySort(category);
            }
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

    console.log('Loading news for category:', sortType);

    axios({
        url: 'http://localhost:8080/news/selectNewsBySort',
        method: 'get',
        params: {
            sort: sortType
        }
    }).then(result => {
        console.log('API response:', result);
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