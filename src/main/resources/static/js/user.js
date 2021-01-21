let postLikeApi = Vue.resource('/post-like{/id}');
let subscriptionsApi = Vue.resource('/subscriptions{/id}');

Vue.component('post-el', {
    props: ['post'],
    data: function () {
        return {
            likeN: 0,
            isLiked: false
        }
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
                '<img class="footer-btn" v-if="isLiked" @click="unlike" src="/img/liked.png" alt=""/>' +
                '<img class="footer-btn" v-else="isLiked" @click="like" src="/img/unliked.png" alt=""/>' +
                '<div class="footer-number">{{ this.likeN }}</div>' +

                '<img class="footer-btn" src="/img/comment_btn.png" alt="">' +
                '<div class="footer-number">2</div>' +
            '</div>' +
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
    },
    created: function () {
        postLikeApi.get({id: this.post.id}).then(result => {
            result.json().then(data => {
                this.likeN = data.likeN;
                this.isLiked = data.isLiked;
            })
        })
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
                '<a href="/messages" style="text-decoration: none">' +
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
            subscriptionsApi.save({id: this.user.id}, {}).then(result => {
                if (result.ok) {
                    this.isSubscribed = true;
                    this.subscribersN++;
                }
            });
        },
        unsubscribe: function () {
            if (confirm("Вы уверены, что хотите отписаться?")) {
                subscriptionsApi.remove({id: this.user.id}).then(result => {
                    if (result.ok) {
                        this.isSubscribed = false;
                        this.subscribersN--;
                    }
                });
            }
        },
    },
    created: function () {
        subscriptionsApi.get({id: this.user.id}).then(result => {
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
        '<div class="content">' +
            '<user-info :user="user"/>' +
            '<post-list :posts="posts"/>' +
        '</div>',
});