let postLikeApi = Vue.resource('/post-like{/id}');
let commentApi = Vue.resource('/comment{/id}');
let commentLikeApi = Vue.resource('/comment-like{/id}');

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
    props: ['comments', 'post'],
    template:
        '<div class="comment-section">' +
            '<comment-form :comments="comments" :post="post"/>' +
            '<comment v-for="comment in comments" :key="comment.id" :comment="comment"/>' +
        '</div>'
});

Vue.component('post-el', {
    props: ['post', 'posts'],
    data: function () {
        return {
            likeN: 0,
            isLiked: false,
            comments: [],
            commentsVisible: false,
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
                '<img class="post-footer-btn" v-if="isLiked" @click="unlike" src="/img/liked.png" alt=""/>' +
                '<img class="post-footer-btn" v-else="isLiked" @click="like" src="/img/unliked.png" alt=""/>' +
                '<div class="post-footer-number">{{ this.likeN }}</div>' +

                '<img class="post-footer-btn" src="/img/comment_btn.png" alt="" @click="switchComments">' +
                '<div class="post-footer-number">{{ comments.length }}</div>' +
            '</div>' +

            '<comment-section v-if="commentsVisible" :post="post" :comments="comments"/>' +
        '</div>',
    methods: {
        del: function () {
            if (confirm("Вы уверены, что хотите удалить пост?")) {
                postApi.remove({id: this.post.id}).then(result => {
                    if (result.ok)
                        this.posts.splice(this.posts.indexOf(this.post), 1);
                });
            }
        },
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
    props: ['posts'],
    template:
        '<div class="post-list">' +
            '<post-el v-for="post in posts" :key="post.id" :post="post" :posts="posts"/>' +
        '</div>',
});

var app = new Vue({
    el: '#app',
    data: {
        feed: frontendData.feed,
    },
    template:
        '<div class="middle">' +
            '<post-list :posts="feed"/>' +
        '</div>',
});