
// 在文件开头添加样式
const buttonStyles = `
<style>
    .category-btn {
        padding: 8px 16px;
        margin: 5px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        background-color: #f0f0f0;
        transition: all 0.3s ease;
    }
    
    .category-btn:hover {
        background-color: #e0e0e0;
    }
    
    .category-btn.clicked,
    .category-btn.active {
        background-color: #007bff;
        color: white;
    }
    
    .category-btn.disabled {
        background-color: #cccccc;
        cursor: not-allowed;
    }

    .button {
        padding: 8px 16px;
        margin: 5px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        background-color: #f0f0f0;
        transition: all 0.3s ease;
    }
</style>
`;

// 在页面加载时添加样式
document.head.insertAdjacentHTML('beforeend', buttonStyles);

// 在文件开头添加一个变量来记住当前选中的分类
let currentCategory = null;

// 页面加载完成后初始化
document.addEventListener('DOMContentLoaded', function() {
    loadNews(1);

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
                loadNewsBySort(category, 1);
            }
        }
    });

    //ct
    // 搜索
    document.body.addEventListener('click', function(event) {
        if (event.target.id === 'search-btn') {
            selectNews(1);
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
        params: {
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
                updatePaginationButtons(pageNum, totalPages);
            } else {
                document.getElementById('news-list').innerHTML = '<div class="news-item">暂无新闻</div>';
                document.getElementById('page-info').innerText = '第 1 页 / 共 1 页';
            }
        }
    }).catch(error => {
        console.error('请求失败：', error);
        document.getElementById('news-list').innerHTML =
            '<div class="news-item">加载失败，请稍后重试</div>';
    });
}

// 添加更新分页按钮状态的函数
function updatePaginationButtons(currentPage, totalPages) {
    const prevButton = document.getElementById('prev-page');
    const nextButton = document.getElementById('next-page');

    // 更新上一页按钮状态
    if (currentPage <= 1) {
        prevButton.disabled = true;
        prevButton.classList.add('disabled');
    } else {
        prevButton.disabled = false;
        prevButton.classList.remove('disabled');
    }

    // 更新下一页按钮状态
    if (currentPage >= totalPages) {
        nextButton.disabled = true;
        nextButton.classList.add('disabled');
    } else {
        nextButton.disabled = false;
        nextButton.classList.remove('disabled');
    }

    // 更新跳转按钮事件
    const goToPageBtn = document.getElementById('go-to-page');
    const pageInput = document.getElementById('page-input');

    goToPageBtn.onclick = () => {
        const inputPage = parseInt(pageInput.value);
        if (inputPage && inputPage > 0 && inputPage <= totalPages) {
            // 检查是否在分类模式
            const activeButton = document.querySelector('.category-btn.clicked');
            if (activeButton) {
                loadNewsBySort(activeButton.getAttribute('data-category'), inputPage);
            } else {
                loadNews(inputPage);
            }
            pageInput.value = '';
        } else {
            alert(`请输入1到${totalPages}之间的页码！`);
            pageInput.value = '';
        }
    };
}

// 修改搜索功能的实现
function selectNews(pageNum) {
    let title = document.getElementById('news-search').value.trim();

    // 如果搜索框为空，从第一页开始显示所有新闻
    if (!title) {
        loadNews(1);
        return;
    }

    axios({
        url: 'http://localhost:8080/news/selectNews',
        method: 'get',
        params: {
            title: title,
            pageNum: pageNum,  // 使用传入的页码
            pageSize: 3
        }
    }).then(result => {
        if (result.data.code === 200) {
            if (result.data.data && result.data.data.length > 0) {
                renderNewsList(result.data.data);
                // 使用后端返回的总记录数计算总页数
                const totalPages = Math.ceil(result.data.data.length / 3);
                document.getElementById('page-info').innerText =
                    `第 ${pageNum} 页 / 共 ${totalPages} 页`;
                updatePaginationButtons(pageNum, totalPages);
            } else {
                document.getElementById('news-list').innerHTML =
                    '<div class="news-item">未找到相关新闻</div>';
                document.getElementById('page-info').innerText = '第 1 页 / 共 1 页';
                updatePaginationButtons(1, 1);
            }
        } else {
            console.error('搜索失败：', result.data.msg);
            document.getElementById('news-list').innerHTML =
                '<div class="news-item">搜索失败，请稍后重试</div>';
        }
    }).catch(error => {
        console.error('请求失败：', error);
        document.getElementById('news-list').innerHTML =
            '<div class="news-item">搜索失败，请稍后重试</div>';
    });
}

// 修改搜索按钮的事件绑定
document.body.addEventListener('click', function(event) {
    if (event.target.id === 'search-btn') {
        selectNews(1);
    }
});

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

// 渲染新闻总条数
function renderNewsTotal(total) {
    let pageInfo = document.getElementById('page-info')
    pageInfo.innerHTML = `第 1 页 / 共 ${total} 页`;
}

// 加载分类新闻
function loadNewsBySort(sort, pageNum = 1) {
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

    // 计算偏移量
    const offset = (pageNum - 1) * 3;  // 每页3条

    axios({
        url: 'http://localhost:8080/news/selectNewsBySort',
        method: 'get',
        params: {
            sort: sortType,
            offset: offset,      // 添加偏移量参数
            pageSize: 3         // 添加每页大小参数
        }
    }).then(result => {
        if (result.data.code === 200) {
            if (result.data.data && result.data.data.length > 0) {
                renderNewsList(result.data.data);
                document.getElementById('page-info').innerText =
                    `第 ${pageNum} 页 / 共 ${result.data.total} 页`;
                updatePaginationButtons(pageNum, result.data.total);
            } else {
                document.getElementById('news-list').innerHTML =
                    `<div class="news-item" style="text-align: center;">
                        <h3>${sortType}分类暂无新闻</h3>
                    </div>`;
                document.getElementById('page-info').innerText = '第 1 页 / 共 1 页';
            }
        }
    }).catch(error => {
        console.error('请求失败：', error);
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
            <div id="news-list"></div>
            <div id="pagination">
                <button id="prev-page" class="page-btn button">上一页</button>
                <span id="page-info"></span>
                <input type="number" id="page-input" class="page-input" min="1" placeholder="跳转到页">
                <button id="go-to-page" class="page-btn button">跳转</button>
                <button id="next-page" class="page-btn button">下一页</button>
            </div>
        </div>
    `;

    // 重新绑定所有事件
    if (currentCategory) {
        const button = document.querySelector(`[data-category="${currentCategory}"]`);
        if (button) {
            button.classList.add('clicked', 'active');
            loadNewsBySort(currentCategory, 1);
        } else {
            loadNews(1);
        }
    } else {
        loadNews(1);
    }

    // 重新绑定搜索按钮事件
    document.getElementById('search-btn').addEventListener('click', function() {
        selectNews(1);
    });

    // 修改分页按钮的事件处理
    document.getElementById('prev-page').addEventListener('click', function() {
        const pageInfo = document.getElementById('page-info').innerText;
        const match = pageInfo.match(/第 (\d+) 页/);
        if (match) {
            const currentPage = parseInt(match[1]);
            if (currentPage > 1) {
                const searchInput = document.getElementById('news-search');
                if (searchInput.value.trim()) {
                    selectNews(currentPage - 1);
                } else {
                    loadNews(currentPage - 1);
                }
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
                const searchInput = document.getElementById('news-search');
                if (searchInput.value.trim()) {
                    selectNews(currentPage + 1);
                } else {
                    loadNews(currentPage + 1);
                }
            }
        }
    });
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

    // 重置当前分类状态
    currentCategory = null;

    const contentox = document.getElementById('contentox');
    contentox.innerHTML = `
        <div id="workstation-page" class="module-page">
            <h2 style="text-align: center; color: #333;">工作站</h2>
            <!-- 工作站的其他内容 -->
        </div>
    `;
});

// 在搜索函数中修改
function searchNews() {
    // 重置当前页码为1
    currentPage = 1;

    var searchContent = $("#searchInput").val().trim();

    // 发送请求到后端
    $.ajax({
        url: "/news/searchNews",
        type: "GET",
        data: {
            "searchContent": searchContent,
            "currentPage": currentPage,
            "pageSize": pageSize
        },
        success: function(response) {
            if (response.code == 200) {
                // 更新新闻列表
                updateNewsList(response.data.records);
                // 更新分页信息
                updatePagination(response.data.total, response.data.current);
            } else {
                alert("搜索失败：" + response.msg);
            }
        },
        error: function() {
            alert("搜索请求失败，请稍后重试");
        }
    });
}


// 修改分页函数
function goToPage(page) {
    currentPage = page;

    // 获取当前搜索框的值
    var searchContent = $("#searchInput").val().trim();

    // 如果搜索框有值，使用搜索接口
    if (searchContent) {
        $.ajax({
            url: "/news/searchNews",
            type: "GET",
            data: {
                "searchContent": searchContent,
                "currentPage": currentPage,
                "pageSize": pageSize
            },
            success: function(response) {
                if (response.code == 200) {
                    updateNewsList(response.data.records);
                    updatePagination(response.data.total, response.data.current);
                }
            }
        });
    } else {
        // 如果搜索框为空，使用普通列表接口
        $.ajax({
            url: "/news/list",
            type: "GET",
            data: {
                "currentPage": currentPage,
                "pageSize": pageSize
            },
            success: function(response) {
                if (response.code == 200) {
                    updateNewsList(response.data.records);
                    updatePagination(response.data.total, response.data.current);
                }
            }
        });
    }
}
