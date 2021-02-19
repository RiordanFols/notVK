let sessionApi = Vue.resource('/session');

export function updateLastOnline() {
    sessionApi.update();
    setInterval(function () {
        sessionApi.update();
    }, 10000);
}