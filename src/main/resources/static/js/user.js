let postLikeApi = Vue.resource('/post-like{/id}');
let subscriptionApi = Vue.resource('/subscription{/id}');
let commentApi = Vue.resource('/comment{/id}');
let commentLikeApi = Vue.resource('/comment-like{/id}');
let replyApi = Vue.resource('/reply{/id}');
let replyLikeApi = Vue.resource('/reply-like{/id}');

Vue.component('comment-form', {
    props: ['comments', 'post'],
    data: function (){
        return {
            text: ''
        };
    },
    template:
        '<div class="comment-form">' +
            '<img class="comment-form-img" src="/img/stock_avatar_m.png" alt=""/>' +
            '<input class="comment-form-text" type="text" placeholder="Напишите комментарий" v-model="text"/>' +
            '<input class="comment-form-btn" type="button" value="✔" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            let body = {text: this.text};
            commentApi.save({id: this.post.id}, body).then(result => {
                result.json().then(data => {
                    this.comments.push(data);
                    this.text = '';
                });
            });
        }
    }
});


Vue.component('reply-el', {
    props: ['reply', 'deleteReply', 'me'],
    data: function() {
        return {
            likeN: 0,
            isLiked: false
        };
    },
    template:
        '<div class="reply-el">' +
            '<div class="reply-header">' +
                '<a v-bind:href="\'/user/\' + reply.author.username">' +
                    '<img class="reply-author-img" src="/img/stock_avatar_m.png" alt=""/>' +
                '</a>' +
                '<div class="reply-info">' +
                    '<a v-bind:href="\'/user/\' + reply.author.username">' +
                        '<div class="reply-author">{{ reply.author.name }} {{ reply.author.surname }}</div>' +
                    '</a>' +
                    '<div class="reply-datetime">{{ reply.creationDateTime }}</div>' +
                '</div>' +

                '<div v-if="me.id === reply.author.id" class="reply-action">' +
                    '<img class="reply-del-btn" src="/img/del_btn.png" @click="del" alt=""/>' +
                '</div>' +
            '</div>' +

            '<div class="reply-main">' +
                '<div class="reply-text">{{ reply.text }}</div>' +
                '<div class="reply-number">{{ likeN }}</div>' +
                '<img class="reply-btn" v-if="isLiked" @click="unlike" src="/img/liked.png" alt=""/>' +
                '<img class="reply-btn" v-else="isLiked" @click="like" src="/img/unliked.png" alt=""/>' +
            '</div>' +
        '</div>',
    methods: {
        like: function () {
            replyLikeApi.save({id: this.reply.id}, {}).then(result => {
                if (result.ok) {
                    this.isLiked = true;
                    this.likeN++;
                }
            });
        },
        unlike: function () {
            replyLikeApi.remove({id: this.reply.id}).then(result => {
                if (result.ok) {
                    this.isLiked = false;
                    this.likeN--;
                }
            });
        },
        del: function () {
            this.deleteReply(this.reply);
        }
    },
    created: function () {
        replyLikeApi.get({id: this.reply.id}).then(result => {
            result.json().then(data => {
                this.likeN = data.likeN;
                this.isLiked = data.isLiked;
            });
        });
    }
});

Vue.component('reply-section', {
    props: ['replies', 'me'],
    template:
        '<div class="reply-section">' +
            '<reply-el v-for="reply in replies" :reply="reply" :key="reply.id" ' +
                    ':deleteReply="deleteReply" :me="me"/>' +
        '</div>',
    methods: {
        deleteReply: function (reply) {
            replyApi.remove({id: reply.id}).then(result => {
                if (result.ok) {
                    this.replies.splice(this.replies.indexOf(reply), 1);
                }
            });
        }
    }
});

Vue.component('comment', {
    props: ['comment', 'me', 'deleteComment'],
    data: function() {
        return {
            isLiked: false,
            likeN: 0,
            replies: [],
        };
    },
    template:
        '<div class="comment-el">' +
            '<div class="comment-header">' +
                '<a v-bind:href="\'/user/\' + comment.author.username">' +
                    '<img class="comment-author-img" src="/img/stock_avatar_m.png" alt=""/>' +
                '</a>' +
                '<div class="comment-info">' +
                    '<a v-bind:href="\'/user/\' + comment.author.username">' +
                        '<div class="comment-author">{{ comment.author.name }} {{ comment.author.surname }}</div>' +
                    '</a>' +
                    '<div class="comment-datetime">{{ comment.creationDateTime }}</div>' +
                '</div>' +

                '<div v-if="me.id === comment.author.id" class="comment-action">' +
                    '<img class="comment-del-btn" src="/img/del_btn.png" @click="del" alt=""/>' +
                '</div>' +
            '</div>' +

            '<div class="comment-main">' +
                '<div class="comment-text">{{ comment.text }}</div>' +
                '<div class="comment-like-section">' +
                    '<img class="comment-btn" v-if="isLiked" @click="unlike" src="/img/liked.png" alt=""/>' +
                    '<img class="comment-btn" v-else="isLiked" @click="like" src="/img/unliked.png" alt=""/>' +
                    '<div class="comment-number">{{ likeN }}</div>' +
                    '<img class="comment-btn" src="/img/reply_btn.png" alt="" />' +
                '</div>' +
            '</div>' +

            // '<reply-section :replies="replies" :me="me"/>' +
        '</div>',
    methods: {
        like: function () {
            commentLikeApi.save({id: this.comment.id}, {}).then(result => {
                if (result.ok) {
                    this.isLiked = true;
                    this.likeN++;
                }
            });
        },
        unlike: function () {
            commentLikeApi.remove({id: this.comment.id}).then(result => {
                if (result.ok) {
                    this.isLiked = false;
                    this.likeN--;
                }
            });
        },
        del: function () {
            if (confirm("Вы уверены, что хотите удалить комментарий?"))
                this.deleteComment(this.comment);
        }
    },
    created: function () {
        commentLikeApi.get({id: this.comment.id}).then(result => {
            result.json().then(data => {
                this.likeN = data.likeN;
                this.isLiked = data.isLiked;
            });
        });

        replyApi.get({id: this.comment.id}).then(result => {
            result.json().then(data => {
                this.replies = data;
            });
        });
    }
});

Vue.component('comment-section', {
    props: ['comments', 'post', 'me'],
    template:
        '<div class="comment-section">' +
            '<comment-form :comments="comments" :post="post"/>' +
            '<comment v-for="comment in comments" :key="comment.id" ' +
                    ':comment="comment" :me="me" :deleteComment="deleteComment"/>' +
        '</div>',
    methods: {
        deleteComment: function (comment) {
            commentApi.remove({id: comment.id}).then(result => {
                if (result.ok) {
                    this.comments.splice(this.comments.indexOf(comment), 1);
                }
            });
        }
    }
});

Vue.component('post-el', {
    props: ['post', 'me'],
    data: function () {
        return {
            likeN: 0,
            isLiked: false,
            comments: [],
            commentsVisible: false,
        };
    },
    template:
        '<div class="post-el">' +
            '<div class="post-header">' +
                '<a v-bind:href="\'/user/\' + post.author.username">' +
                    '<img class="post-author-img" src="/img/stock_avatar_m.png" alt=""/>' +
                '</a>' +
                '<div class="post-info">' +
                    '<a v-bind:href="\'/user/\' + post.author.username" style="text-decoration: none">' +
                        '<div class="post-author">{{ post.author.name }} {{ post.author.surname }}</div>' +
                    '</a>' +
                    '<div class="post-datetime">{{ post.creationDateTime }}</div>' +
                '</div>' +
            '</div>' +

            '<div class="post-main">' +
                '<div class="post-text">{{ post.text }}</div>' +
            '</div>' +

            '<div class="post-footer">' +
                '<img class="post-footer-btn" v-if="isLiked" @click="unlike" src="/img/liked.png" alt=""/>' +
                '<img class="post-footer-btn" v-else="isLiked" @click="like" src="/img/unliked.png" alt=""/>' +
                '<div class="post-footer-number">{{ likeN }}</div>' +

                '<img class="post-footer-btn" src="/img/comment_btn.png" alt="" @click="switchComments">' +
                '<div v-if="comments.length !== 0" class="post-footer-number">{{ comments.length }}</div>' +
            '</div>' +

            '<comment-section v-if="commentsVisible" :post="post" :comments="comments" :me="me"/>' +
        '</div>',
    methods: {
        like: function () {
            postLikeApi.save({id: this.post.id}, {}).then(result => {
                if (result.ok) {
                    this.isLiked = true;
                    this.likeN++;
                }
            });
        },
        unlike: function () {
            postLikeApi.remove({id: this.post.id}).then(result => {
                if (result.ok) {
                    this.isLiked = false;
                    this.likeN--;
                }
            });
        },
        switchComments: function () {
            this.commentsVisible = !this.commentsVisible;
        }
    },
    created: function () {
        postLikeApi.get({id: this.post.id}).then(result => {
            result.json().then(data => {
                this.likeN = data.likeN;
                this.isLiked = data.isLiked;
            });
        });

        commentApi.get({id: this.post.id}).then(result => {
            result.json().then(data => {
                this.comments = data;
                this.commentsVisible = this.comments.length !== 0;
            });
        });
    }
});

Vue.component('post-list', {
    props: ['posts', 'me'],
    template:
        '<div class="post-list">' +
            '<post-el v-for="post in posts" :key="post.id" :post="post" :me="me"/>' +
        '</div>',
});

Vue.component('user-info', {
    props: ['user'],
    data: function () {
        return {
            subscribersN: 0,
            isSubscribed: false,
        }
    },
    template:
        '<div class="user-info">' +
            '<div class="user-info-left">' +
                '<img class="user-photo" src="/img/stock_avatar_m.png" alt=""/>' +
                '<a v-bind:href="\'/messenger/\' + user.username" style="text-decoration: none">' +
                    '<div class="user-message-btn">Написать сообщение</div>' +
                '</a>' +
                '<div class="user-subscribe-btn" v-if="!isSubscribed" @click="subscribe">Подписаться</div>' +
                '<div class="user-subscribe-btn" v-else @click="unsubscribe">Отписаться</div>' +
            '</div>' +
            '<div class="user-info-right">' +
                '<div class="user-info-right-header">{{ user.name }} {{ user.surname }}</div>' +
                '<div class="user-info-right-main">' +
                    // день рождения, статус и т.д.
                    '<div></div>' +
                    '<div></div>' +
                    '<div></div>' +
                    '<div></div>' +
                    '<div></div>' +
                '</div>' +
                '<div class="user-info-right-footer">' +
                    '' +
                // кол-во подписчиков и другие показатели
                '</div>' +
            '</div>' +
        '</div>',
    methods: {
        subscribe: function () {
            subscriptionApi.save({id: this.user.id}, {}).then(result => {
                if (result.ok) {
                    this.isSubscribed = true;
                    this.subscribersN++;
                }
            });
        },
        unsubscribe: function () {
            if (confirm("Вы уверены, что хотите отписаться?")) {
                subscriptionApi.remove({id: this.user.id}).then(result => {
                    if (result.ok) {
                        this.isSubscribed = false;
                        this.subscribersN--;
                    }
                });
            }
        },
    },
    created: function () {
        subscriptionApi.get({id: this.user.id}).then(result => {
            result.json().then(data => {
                this.isSubscribed = data.isSubscribed;
                this.subscribersN = data.subscribersN;
            });
        });
    }
});

var app = new Vue({
    el: '#app',
    data: {
        me: frontendData.me,
        user: frontendData.user,
        posts: frontendData.userPosts,
    },
    template:
        '<div class="middle">' +
            '<user-info :user="user"/>' +
            '<post-list :posts="posts" :me="me"/>' +
        '</div>',
});