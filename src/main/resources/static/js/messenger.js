let messagesApi = Vue.resource('/messages{/id}');

Vue.component('message', {
    props: ['message', 'user'],
    template:
        '<div class="message">' +
            '<a v-bind:href="\'/user/\' + message.author.username">' +
                '<img class="message-author-img" src="/img/stock_avatar_m.png" alt=""/>' +
            '</a>' +
            '<div class="message-main">' +
                '<div class="message-info">' +
                    '<a v-bind:href="\'/user/\' + message.author.username">' +
                        '<div class="message-author">{{ message.author.name }}</div>' +
                    '</a>' +
                    '<div class="message-time">{{ message.creationDateTime }}</div>' +
                '</div>' +
                '<div class="message-text">{{ message.text }}</div>' +
            '</div>' +
        '</div>'
});

Vue.component('chat', {
    props: ['messages'],
    template:
        '<div class="chat">' +
            '<message v-for="message in messages" :key="message.id" :message="message"/>' +
        '</div>'
});

Vue.component('chat-header', {
    props: ['target'],
    template:
        '<div class="chat-header">' +
            '<a v-bind:href="\'/user/\' + target.username">' +
                '<div class="chat-header-name">{{ target.name }} {{ target.surname }}</div>' +
            '</a>' +
            '<div class="chat-header-last-online">*последний раз онлайн*</div>' +
            '<a v-bind:href="\'/user/\' + target.username">' +
                '<img class="chat-header-img" src="/img/stock_avatar_m.png" alt=""/>' +
            '</a>' +
        '</div>'
});

Vue.component('message-form', {
    props: ['target', 'messages'],
    data: function () {
        return {
            text: ''
        }
    },
    template:
        '<div class="message-form">' +
            '<input class="message-form-text" type="text" placeholder="Напишите сообщение" v-model="text"/>' +
            '<input class="message-form-btn" type="button" value="✔" @click="save"/>' +
        '</div>',
    methods: {
        save: function () {
            if (this.text !== '') {
                let body = {text: this.text};

                messagesApi.save({id: this.target.id}, body).then(result => {
                    result.json().then(data => {
                        this.messages.splice(0, 0, data);
                        this.text = '';
                    });
                });
            }
        }
    }
});

Vue.component('msg-block', {
    props: ['messages', 'target'],
    template:
        '<div class="msg-block" >' +
            '<chat-header v-if="target != null" :target="target"/>' +
            '<chat v-if="target != null" :messages="messages"/>' +
            '<message-form v-if="target != null" :target="target" :messages="messages"/>' +
        '</div>'
});

Vue.component('user-el', {
    props: ['user'],
    template:
        '<a v-bind:href="\'/messenger/\' + user.username">' +
            '<div class="contact">' +
                '<img class="contact-img" src="/img/stock_avatar_m.png" alt=""/>' +
                '<div class="contact-info">' +
                    '<div class="contact-name">{{ user.name }} {{ user.surname }}</div>' +
                    '<div class="contact-last-message">*Последнее сообщение*</div>' +
                '</div>' +
            '</div>' +
        '</a>',
});

Vue.component('user-list', {
    props: ['users'],
    template:
        '<div class="contact-list">' +
            '<user-el v-for="user in users" :key="user.id" :user="user"/>' +
        '</div>'
});

var app = new Vue({
    el: '#app',
    data: {
        userList: frontendData.userList,
        messages: frontendData.messages,
        target: frontendData.target,
    },
    template:
        '<div class="content">' +
            '<user-list :users="userList"/>' +
            '<msg-block :messages="messages" :target="target"/>' +
        '</div>'
});