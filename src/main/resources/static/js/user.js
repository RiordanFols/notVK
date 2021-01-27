let postLikeApi = Vue.resource('/post-like{/id}');
let subscriptionApi = Vue.resource('/subscription{/id}');
let commentApi = Vue.resource('/comment{/id}');
let commentLikeApi = Vue.resource('/comment-like{/id}');

Vue.component('comment', {
    props: ['comment'],
    data: function() {
        return {
            isLiked: false,
            likeN: 0
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
            '</div>' +

            '<div class="comment-main">' +
                '<div class="comment-text">{{ comment.text }}</div>' +
                '<div class="comment-number">{{ likeN }}</div>' +
                '<img class="comment-btn" v-if="isLiked" @click="unlike" src="/img/liked.png" alt=""/>' +
                '<img class="comment-btn" v-else="isLiked" @click="like" src="/img/unliked.png" alt=""/>' +
            '</div>' +

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
    },
    created: function () {
        commentLikeApi.get({id: this.comment.id}).then(result => {
            result.json().then(data => {
                this.likeN = data.likeN;
                this.isLiked = data.isLiked;
            });
        });
    }
});

Vue.component('comment-section', {
    props: ['comments'],
    template:
        '<div class="comment-section">' +
            '<comment v-for="comment in comments" :key="comment.id" :comment="comment"/>' +
        '</div>'
});

Vue.component('post-el', {
    props: ['post'],
    data: function () {
        return {
            likeN: 0,
            isLiked: false,
            comments: [],
            commentsVisible: true,
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

            '<comment-section v-if="commentsVisible" :comments="comments"/>' +
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
            });
        });
    }
});

Vue.component('post-list', {
    props: ['posts'],
    template:
        '<div class="post-list">' +
            '<post-el v-for="post in posts" :key="post.id" :post="post"/>' +
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
        user: frontendData.user,
        posts: frontendData.userPosts,
    },
    template:
        '<div class="middle">' +
            '<user-info :user="user"/>' +
            '<post-list :posts="posts"/>' +
        '</div>',
});