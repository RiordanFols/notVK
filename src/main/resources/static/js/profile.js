let profilePhotoApi = Vue.resource('/profile/photo');

Vue.component('user-profile', {
    props: ['me', 'updateAvatarMethod'],
    template:
        '<div class="user-info">' +
            '<div class="user-info-left">' +
                '<img class="user-photo" v-bind:src="\'/uploads/img/avatar/\' + me.avatarFilename" alt=""/>' +
                '<form action="/profile/update/avatar" method="post" enctype="multipart/form-data">' +
                    '<input class="profile-img-update" type="file" name="avatar"/>' +
                    '<input class="profile-img-submit" type="submit" value="Обновить фото"/>' +
                '</form>' +
                '<div class="profile-img-delete" @click="deletePhoto">Удалить фото</div>' +
            '</div>' +
            '<div class="user-info-right">' +
                '<form action="profile/update/profile" method="post">' +
                    '<div class="user-info-right-names">' +

                        '<div class="profile-input-line">' +
                            '<div class="profile-label user-name-label">Имя: </div>' +
                            '<input class="profile-input user-name-input" name="name" required maxlength="30" type="text" v-model="me.name"/><br/>' +
                        '</div>' +

                        '<div class="profile-input-line">' +
                            '<div class="profile-label user-name-label">Фамилия: </div>' +
                            '<input class="profile-input user-name-input" name="surname" required maxlength="30" type="text" v-model="me.surname"/><br/>' +
                        '</div>' +

                        '<div class="profile-input-line">' +
                            '<div class="profile-label">Юзернейм: </div>' +
                            '<input class="profile-input" name="username" type="text" required maxlength="30" v-model="me.username"/>' +
                        '</div>' +

                    '</div>' +
                    '<div class="user-info-right-other">' +
                        '<div class="profile-input-line">' +
                            '<div class="profile-label">Почта: </div>' +
                            '<input class="profile-input" name="email" type="email" required maxlength="30" v-model="me.email"/>' +
                        '</div>' +

                        '<div class="profile-input-line">' +
                            '<div class="profile-label" >Статус: </div>' +
                            '<input class="profile-input" name="status" maxlength="50" type="text" v-model="me.status"/>' +
                        '</div>' +

                        '<div class="profile-input-line profile-birthday-input-line">' +
                            '<div class="profile-label">День рождения: </div>' +
                            '<input class="profile-input user-birthday-input" name="birthday" type="date" v-model="me.birthday"/>' +
                        '</div>' +

                        '<input class="profile-submit" type="submit" value="Готово"/>' +
                    '</div>' +
                '</form>' +

                '<form action="profile/update/password" method="post">' +
                    '<div class="user-info-right-password">' +
                        '<div class="profile-input-line">' +
                            '<label class="profile-label-password">Старый пароль</label>' +
                            '<input class="profile-input-password" name="oldPassword" type="password">' +
                        '</div>' +
                        '<div class="profile-input-line">' +
                            '<label class="profile-label-password">Новый пароль</label>' +
                            '<input class="profile-input-password" name="newPassword" type="password">' +
                        '</div>' +
                        '<div class="profile-input-line">' +
                            '<label class="profile-label-password">Подтвердите новый пароль</label>' +
                            '<input class="profile-input-password" name="newPasswordConfirm" type="password">' +
                        '</div>' +

                        '<input class="profile-submit" type="submit" value="Готово"/>' +
                    '</div>' +
                '</form>' +
            '</div>' +
        '</div>',
    methods: {
        deletePhoto: function () {
            if (confirm("Вы уверены, что хотите удалить своё фото?")) {
                profilePhotoApi.remove().then(result => {
                    result.json().then(data => {
                        this.updateAvatarMethod(data);
                    });
                });
            }
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
            '<user-profile :me="me" :updateAvatarMethod="updateUser"/>' +
        '</div>',
    methods: {
        updateUser: function (user) {
            this.me = user;
        }
    }
});