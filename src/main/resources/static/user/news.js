// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    initializeCategoryButtons();
    loadNews(); // 默认加载所有新闻
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

// 初始化分类按钮
function initializeCategoryButtons() {
    document.querySelectorAll('.category-btn').forEach(button => {
        button.addEventListener('click', function() {
            const category = this.getAttribute('data-category');

            // 移除所有按钮的active类
            document.querySelectorAll('.category-btn').forEach(btn => {
                btn.classList.remove('active');
            });
            // 添加active类到当前点击的按钮
            this.classList.add('active');

            // 根据分类加载新闻
            loadNewsBySort(category);
        });
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

    axios({
        url: 'http://localhost:8080/news/selectNewsBySort',
        method: 'get',
        params: {
            sort: sortType
        }
    }).then(result => {
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
        }
    }).catch(error => {
        console.error('请求失败：', error);
    });
}