import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

let router = new Router({
    mode: "history",
    routes: [
        {
            path: "/presentation",
            alias: "/presentations",
            name: "presentations-list",
            component: () => import("./components/presentation/PresentationsList")
        },

        {
            path: "/presentations/:id",
            name: "presentation-edit",
            component: () => import("./components/presentation/Presentation")
        },

        {
            path: "/addPresentation",
            name: "presentation-add",
            component: () => import("./components/presentation/AddPresentation")
        },

        {
            path: "/users",
            name: "users-list",
            component: () => import("./components/user/UsersList")
        },

        {
            path: "/users/:id",
            name: "user-edit",
            component: () => import("./components/user/User")
        },

        {
            path: "/addUser",
            name: "user-add",
            component: () => import("./components/user/AddUser")
        },


        {
            path: "/schedules",
            name: "schedules-list",
            component: () => import("./components/schedule/SchedulesList")
        },

        {
            path: "/login",
            name: "login",
            component: () => import("./components/auth/Login")
        },

        {
            path: "/register",
            name: "register",
            component: () => import("./components/auth/Register")
        },

        {
            path: "/notFound",
            name: "notFound",
            component: () => import("./components/NotFound")
        },

        {
            path: "/",
            name: "Home",
            component: () => import("./components/Home")
        }
    ]

});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});

export default router;