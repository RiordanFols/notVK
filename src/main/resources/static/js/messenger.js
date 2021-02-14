let messagesApi = Vue.resource('/message{/id}');

Vue.component('message-img', {
    props: ['imgFilename'],
    template:
        '<div>' +
            '<img class="message-img" v-bind:src="\'/uploads/img/message/\' + imgFilename" alt=""/>' +
        '</div>'
});

Vue.component ('message-imgs', {
    props: ['message'],
    template:
        '<div class="message-image-section">' +
            '<message-img v-for="filename in message.imgFilenames" :key="filename" :imgFilename="filename"/>' +
        '</div>'
});

Vue.component('message', {
    props: ['message', 'user', 'me'],
    template:
        '<div class="message">' +
            '<a v-bind:href="\'/user/\' + message.author.username">' +
                '<img class="message-author-img" v-bind:src="\'/uploads/img/avatar/\' + message.author.avatarFilename" alt=""/>' +
            '</a>' +
            '<div class="message-main">' +
                '<div class="message-info">' +
                    '<a v-bind:href="\'/user/\' + message.author.username">' +
                        '<div class="message-author">{{ message.author.name }}</div>' +
                    '</a>' +
                    '<div class="message-time">{{ message.creationDateTime }}</div>' +
                '</div>' +
                '<div class="message-text">{{ message.text }}</div>' +
                '<message-imgs :message="message"/>' +
            '</div>' +
        '</div>'
});

Vue.component('chat', {
    props: ['messages', 'me'],
    template:
        '<div class="chat">' +
            '<message v-for="message in messages" :key="message.id" :message="message" :me="me"/>' +
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
                '<img class="chat-header-img" v-bind:src="\'/uploads/img/avatar/\' + target.avatarFilename" alt=""/>' +
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
    props: ['messages', 'target', 'me'],
    template:
        '<div class="msg-block" >' +
            '<chat-header v-if="target != null" :target="target"/>' +
            '<chat v-if="target != null" :messages="messages" :me="me"/>' +
            '<message-form v-if="target != null" :target="target" :messages="messages"/>' +
        '</div>'
});

Vue.component('user-el', {
    props: ['user'],
    template:
        '<a v-bind:href="\'/messenger/\' + user.username">' +
            '<div class="contact">' +
                '<img class="contact-img" v-bind:src="\'/uploads/img/avatar/\' + user.avatarFilename" alt=""/>' +
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
        me: frontendData.me,
        target: frontendData.target,
    },
    template:
        '<div class="content">' +
            '<user-list :users="userList"/>' +
            '<msg-block :messages="messages" :target="target" :me="me"/>' +
        '</div>'
});