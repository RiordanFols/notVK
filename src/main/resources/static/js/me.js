var postApi = Vue.resource('/post{/id}');


Vue.component('post-form', {
    props: ['posts'],
    data: function () {
        return {
            text: '',
            id: '',
        }
    },
    template:
        '<div class="post-form">' +
            '<img class="post-form-img" alt=""/>' +
            '<input class="post-form-text" type="text" placeholder="Что у вас нового?" v-model="text"/>' +
            '<input class="post-form-btn" type="button" value="✔" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            if (this.text !== '') {
                let body = {text: this.text}

                postApi.save({}, body).then(result => {
                    result.json().then(data => {
                        this.posts.splice(0, 0, data);
                        this.text = '';
                    });
                });
            }
        }
    }
});

Vue.component('post-el', {
    props: ['post', 'posts'],
    template:
        '<div class="post-el">' +
            '<div class="post-header">' +
                '<img class="post-author-img" alt=""/>' +

                '<div class="post-info">' +
                    '<div class="post-author">{{ post.author.name }} {{ post.author.surname }}</div>' +
                    '<div class="post-datetime">{{ post.creationDateTime }}</div>' +
                '</div>' +

                '<div class="post-action">' +
                    '<img class="post-del-btn" src="/img/del_btn.png" @click="del" alt=""/>' +
                    '<img class="post-edit-btn" src="/img/edit_btn.png" alt=""/>' +
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
    methods: {
        del: function () {
            let confirmation = confirm("Вы уверены, что хотите удалить пост?");
            if (confirmation) {
                postApi.remove({id: this.post.id}).then(result => {
                    if (result.ok)
                        this.posts.splice(this.posts.indexOf(this.post), 1);
                });
            }
        }
    }
});

Vue.component('post-list', {
    props: ['posts'],
    template:
        '<div class="post-list">' +
            '<post-el v-for="post in posts" :key="post.id" :post="post" :posts="posts"/>' +
        '</div>>',
});

Vue.component('user-info', {
    props: ['user'],
    template:
        '<div class="user-info">' +
            '<div class="user-info-left">' +
                '<img class="user-photo" alt=""/>' +
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
            '<post-form :posts="posts"/>' +
            '<post-list :posts="posts"/>' +
        '</div>',
    // created: function () {
    //
    // }
});