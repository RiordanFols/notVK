let profileApi = Vue.resource('/profile');

Vue.component('user-profile', {
    props: ['me', 'updateMethod'],
    template:
        '<div class="user-info">' +
            '<div class="user-info-left">' +
                '<img class="user-photo" src="/img/stock_avatar_m.png" alt=""/>' +
                '<div class="under-avatar-btn">Сменить фото</div>' +
                '<div class="under-avatar-btn">Удалить фото</div>' +
            '</div>' +
            '<div class="user-info-right">' +
                '<div class="user-info-right-header">' +

                    '<div class="profile-input-line">' +
                        '<div class="profile-label user-name-label">Имя: </div>' +
                        '<input class="user-name-input" required maxlength="30" type="text" v-model="me.name"/><br/>' +
                    '</div>' +

                    '<div class="profile-input-line">' +
                        '<div class="profile-label user-name-label">Фамилия: </div>' +
                        '<input class="user-name-input" required maxlength="30" type="text" v-model="me.surname"/><br/>' +
                    '</div>' +

                    '<div class="profile-input-line">' +
                        '<label class="profile-label">Юзернейм: </label>' +
                        '<input type="text" required maxlength="30" v-model="me.username"/>' +
                    '</div>' +

                '</div>' +
                '<div class="user-info-right-main">' +

                    '<div class="profile-input-line">' +
                        '<label class="profile-label">Почта: </label>' +
                        '<input type="email" required maxlength="30" v-model="me.email"/>' +
                    '</div>' +

                    '<div class="profile-input-line">' +
                        '<div class="profile-label" >Статус: </div>' +
                        '<input maxlength="50" type="text" v-model="me.status"/>' +
                    '</div>' +

                    '<div class="profile-input-line profile-birthday-input-line">' +
                        '<label class="profile-label">День рождения: </label>' +
                        '<input class="user-birthday-input" type="date" v-model="me.birthday"/>' +
                    '</div>' +

                    '<div class="profile-save-btn" @click="saveProfile">Сохранить</div>' +
                '</div>' +
            '</div>' +
        '</div>',
    methods: {
        saveProfile: function () {
            let body = {
                name: this.me.name,
                surname: this.me.surname,
                status: this.me.status,
                username: this.me.username,
                email: this.me.email,
                birthday: this.me.birthday
            };
            profileApi.save({}, body).then(result => {
                result.json().then(data => {
                    this.updateMethod(data);
                    alert("Изменения сохранены");
                });
            });
        }
    }
});

var app = new Vue({
    el: '#app',
    data: {
        me: frontendData.me,
    },
    template:
        '<div class="middle">' +
            '<user-profile :me="me" :updateMethod="updateUser"/>' +
        '</div>',
    methods: {
        updateUser: function (user) {
            this.me = user;
        }
    }
});