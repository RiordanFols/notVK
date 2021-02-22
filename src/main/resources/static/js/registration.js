
let app = new Vue({
    el: '#app',
    data: {
        genders: frontendData.genders,
    },
    template:
        '<div class="middle">' +
            '<div class="middle-center">' +
                '<form method="post" action="/registration">' +
                    '<h2 class="auth-header">Регистрация</h2>' +

                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Имя пользователя</div>' +
                        '<input class="auth-input" type="text" name="username" required autofocus>' +
                    '</div>' +

                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Выберите ваш пол</div>' +
                        '<select class="profile-input profile-gender-input" size="1" id="gender" name="gender" required>' +
                            '<option class="" v-for="gender in genders" v-bind:value="gender.name">{{ gender.description }}</option>' +
                        '</select>' +
                    '</div>' +

                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Почта</div>' +
                        '<input class="auth-input" type="email" name="email" required>' +
                    '</div>' +

                    '<br/>' +
                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Имя</div>' +
                        '<input class="auth-input" type="text" name="name" required>' +
                    '</div>' +

                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Фамилия</div>' +
                        '<input class="auth-input" type="text" name="surname">' +
                    '</div>' +

                    '<br/>' +
                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Пароль</div>' +
                        '<input class="auth-input" type="password" name="password" required>' +
                    '</div>' +

                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Введите пароль еще раз</div>' +
                        '<input class="auth-input" type="password" name="passwordConfirm" required>' +
                    '</div>' +

                    '<div class="auth-input-line">' +
                        '<button class="auth-submit-btn" type="submit">Готово</button>' +
                    '</div>' +

                '</form>' +

                '<div class="auth-bottom-block">' +
                    '<p class="auth-bottom-caption">Есть аккаунт?' +
                        '<a class="auth-bottom-link" href="/login">Авторизация</a>' +
                    '</p>' +
                '</div>' +
            '</div>' +
        '</div>'
});