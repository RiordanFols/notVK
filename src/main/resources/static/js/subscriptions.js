let subscriptionsApi = Vue.resource('/subscription{/id}');

Vue.component('sub-info', {
    props: ['sub', 'subs'],
    template:
        '<div class="sub-el">' +
            '<a v-bind:href="\'/user/\' + sub.username">' +
                '<img src="/img/stock_avatar_m.png" class="sub-el-img" alt=""/>' +
            '</a>' +
            '<div class="sub-info">' +
                '<a v-bind:href="\'/user/\' + sub.username" style="text-decoration: none">' +
                    '<div class="sub-user-name">{{ sub.name }} {{ sub.surname }}</div>' +
                '</a>' +
                '<div class="unsubscribe-btn" @click="unsubscribe">Отписаться</div>' +
            '</div>' +
        '</div>',
    methods: {
        unsubscribe: function () {
            if (confirm("Вы уверены, что хотите отписаться?")) {
                subscriptionsApi.remove({id: this.sub.id}).then(result => {
                    if (result.ok)
                        this.subs.splice(this.subs.indexOf(this.sub), 1);
                });
            }
        }
    }
});

Vue.component('sub-list', {
    props: ['subs'],
    template:
        '<div class="sub-list">' +
            '<sub-info v-for="sub in subs" :key="sub.id" :subs="subs" :sub="sub"></sub-info>' +
        '</div>'
});

var app = new Vue({
    el: '#app',
    data: {
        subscriptions: frontendData.subscriptions,
    },
    template:
        '<div class="middle">' +
            '<sub-list :subs="subscriptions"/>' +
        '</div>',
});