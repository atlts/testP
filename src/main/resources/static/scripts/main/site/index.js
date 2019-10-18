(function (window, undefined) {
    var Action = Base.getClass('main.util.Action');
    var Business = Base.getClass('main.util.Business');

    Base.ready({
        initialize: fInitialize,
        // 事件代理
        events: {
            'click #load-more-question': fMore
        }
    });

    function fInitialize() {
        var that = this;
        Business.followQuestion({
            // listEl: $('.js-user-list')
        });
    }


    function fMore(e) {
        let btn = e.target;
        let offset = btn.dataset.offset;
        Action['fGetQuestions']({
            offset: offset,
            limit: 10,
            call: function (oResult) {
                // 获取问题信息 增加到页面
                console.log(oResult)
                let list = oResult.data; // list 为返回数据
                // 通过前端按钮的 dataset.offset 记录当前共查询了多少数据 下次接着现在的条数查询
                btn.dataset.offset = list.length + offset;
                // 不足10条数据证明没有更多数据了 按钮不再显示
                // TODO 应该后端返回是否还有数据 而不是通过数据条数来判断
                if (list.length < 10) {
                    btn.style.display = 'none';
                }
                function createDocumentFragment(template) {
                    return document.createRange().createContextualFragment(template);
                }
                // 将查询到内容加到页面上
                let html = list.map(vo => {
                    let user = vo.objs.user || {};
                    return `<div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
                            <meta itemprop="ZReactor" data-id="389034" data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
                            <div class="feed-item-inner">
                                <div class="avatar">
                                    <a title="${user.name}" data-tip="p$t$amuro1230" class="zm-item-link-avatar" target="_blank" href="https://nowcoder.com/people/amuro1230">
                                        <img src="${user.headUrl}" class="zm-item-img-avatar"></a>
                                </div>
                                <div class="feed-main">
                                    <div class="feed-content" data-za-module="AnswerItem">
                                        <meta itemprop="answer-id" content="389034">
                                        <meta itemprop="answer-url-token" content="13174385">
                                        <h2 class="feed-title">
                                            <a class="question_link" target="_blank" href="/question/${vo.objs.question.id}">${vo.objs.question.title}</a></h2>
                                        <div class="feed-question-detail-item">
                                            <div class="question-description-plain zm-editable-content"></div>
                                        </div>
                                        <div class="expandable entry-body">
                                            <div class="zm-item-vote">
                                                <a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;" data-bind-votecount="">${vo.objs.followCount}</a></div>
                                            <div class="zm-item-answer-author-info">
                                                <a class="author-link" data-tip="p$b$amuro1230" target="_blank" href="/user/${user.id}">${user.name}</a>
                                                ，$date.format('yyyy-MM-dd HH:mm:ss', ${vo.objs.question.createdDate})</div>
                                            <div class="zm-item-vote-info" data-votecount="4168" data-za-module="VoteInfo">
                                                <span class="voters text">
                                                    <a href="#" class="more text">
                                                        <span class="js-voteCount">4168</span>&nbsp;人赞同</a></span>
                                            </div>
                                            <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="123114" data-action="/answer/content" data-author-name="李淼" data-entry-url="/question/19857995/answer/13174385">
                                                <div class="zh-summary summary clearfix">${vo.objs.question.content}</div>
                                            </div>
                                        </div>
                                        <div class="feed-meta">
                                            <div class="zm-item-meta answer-actions clearfix js-contentActions">
                                                <div class="zm-meta-panel">
                                                    <a data-follow="q:link" class="follow-link zg-follow meta-item" href="javascript:;" id="sfb-123114">
                                                        <i class="z-icon-follow"></i>关注问题</a>
                                                    <a href="#" name="addcomment" class="meta-item toggle-comment js-toggleCommentBox">
                                                        <i class="z-icon-comment"></i>${vo.objs.question.commentCount} 条评论</a>


                                                    <button class="meta-item item-collapse js-collapse">
                                                        <i class="z-icon-fold"></i>收起</button>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`
                }).join('');
                const container = document.getElementById('js-home-feed-list');
                container.appendChild(createDocumentFragment(html));
            },
            error: function (oResult) {
                if (oResult.code === 999) {
                    alert('请登录后再操作');
                    window.location.href = '/reglogin?next=' + window.decodeURIComponent(window.location.href);
                } else {
                    alert('出现错误，请重试');
                }
            }
        });
    }

})(window);