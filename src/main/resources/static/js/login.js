
let app = new Vue({
    el: '#app',
    template:
        '<div class="middle">' +
            '<div class="middle-center">' +
                '<form method="post" action="/login">' +
                    '<h2 class="auth-header">Авторизация</h2>' +

                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Имя пользователя</div>' +
                        '<input class="auth-input" type="text" name="username" required autofocus>' +
                    '</div>' +

                    '<div class="auth-input-line">' +
                        '<div class="auth-label">Пароль</div>' +
                        '<input class="auth-input" type="password" name="password" required>' +
                    '</div>' +

                    '<div class="auth-input-line">' +
                        '<button class="auth-submit-btn" type="submit">Войти</button>' +
                    '</div>' +

                '</form>' +

                '<div class="auth-bottom-block">' +
                    '<p class="auth-bottom-caption">Нет аккаунта?' +
                        '<a class="auth-bottom-link" href="/registration">Регистрация</a>' +
                    '</p>' +
                '</div>' +
            '</div>' +
        '</div>'
});