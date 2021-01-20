
Vue.component('post-el', {
    props: ['post'],
    template:
        '<div class="post-el">' +
            '<div class="post-header">' +
                '<img class="post-author-img" alt=""/>' +
                '<div class="post-info">' +
                    '<div class="post-author">{{ post.author.name }} {{ post.author.surname }}</div>' +
                    '<div class="post-datetime">{{ post.creationDateTime }}</div>' +
                '</div>' +
            '</div>' +

            '<div class="post-main">' +
                '<div class="post-text">{{ post.text }}</div>' +
            '</div>' +

            '<div class="post-footer">' +
                '<img class="footer-btn" src="/img/unliked.png" alt=""/>' +
                '<div class="footer-number">5</div>' +

                '<img class="footer-btn" src="/img/comment_btn.png" alt="">' +
                '<div class="footer-number">2</div>' +
            '</div>' +
    '</div>',
});

Vue.component('post-list', {
    props: ['posts'],
    template:
        '<div class="post-list">' +
            '<post-el v-for="post in posts" :key="post.id" :post="post"/>' +
        '</div>>',
});

Vue.component('user-info', {
    props: ['user'],
    template:
        '<div class="user-info">' +
            '<div class="user-info-left">' +
                '<img class="user-photo" alt=""/>' +
                '<a href="/messages" style="text-decoration: none">' +
                    '<div class="user-message-btn">Написать сообщение</div>' +
                '</a>' +
                '<div class="user-subscribe-btn">Подписаться</div>' +
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
});

var app = new Vue({
    el: '#app',
    data: {
        user: frontendData.user,
        posts: frontendData.userPosts,
    },
    // methods: {
    //
    // },
    template:
        '<div class="content">' +
            '<user-info :user="user"/>' +
            '<post-list :posts="posts"/>' +
        '</div>',
    // created: function () {
    //
    // }
});