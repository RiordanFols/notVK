var postLikeApi = Vue.resource('/post-like{/id}');

Vue.component('post-el', {
    props: ['post', 'posts'],
    data: function () {
        return {
            likeN: 0,
            isLiked: false,
        }
    },
    template:
        '<div class="post-el">' +
            '<div class="post-header">' +
                '<img class="post-author-img" alt=""/>' +
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
            '<post-el v-for="post in posts" :key="post.id" :post="post" :posts="posts"/>' +
        '</div>',
});

var app = new Vue({
    el: '#app',
    data: {
        feed: frontendData.feed,
    },
    template:
        '<div class="content">' +
            '<post-list :posts="feed"/>' +
        '</div>',
});