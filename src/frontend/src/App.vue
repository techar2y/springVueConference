<template>
    <div id="app">
        <div>
            <b-navbar variant="dark" type="dark">
                <b-navbar-nav>
                    <b-navbar-brand to="/">
                        <font-awesome-icon icon="home" /> Home
                    </b-navbar-brand>
                    <b-nav-item v-if="currentUser != null" to="/schedules">Расписание</b-nav-item>
                    <b-nav-item v-if="hasRoleAdmin || hasRolePresenter" to="/presentations">Доклады</b-nav-item>
                    <b-nav-item v-if="hasRoleAdmin" to="/users">Пользователи</b-nav-item>
                </b-navbar-nav>
                <b-navbar-nav v-if="!currentUser" class="ml-auto">
                    <b-nav-item to="/register">
                        <font-awesome-icon icon="user-plus"/>Регистрация
                    </b-nav-item>
                    <b-nav-item to="/login">
                        <font-awesome-icon icon="sign-in-alt" />Войти
                    </b-nav-item>
                </b-navbar-nav>
                <b-navbar-nav v-if="currentUser" class="ml-auto">
                    <b-nav-item>
                        <font-awesome-icon icon="user" /> {{ currentUser.username }}
                    </b-nav-item>
                    <b-nav-item @click.prevent="logOut">
                        <font-awesome-icon icon="sign-out-alt" />Выйти
                    </b-nav-item>
                </b-navbar-nav>
            </b-navbar>
        </div>

        <div class="container mt-3">
            <router-view/>
        </div>
    </div>
</template>

<script>
    import EventService from './services/event-service'

    const ROLE_ADMIN = 'ADMIN';
    const ROLE_PRESENTER = 'PRESENTER';

    export default {
        name: "app",
        computed: {
            currentUser() {
                return this.$store.state.auth.user;
            },

            hasRoleAdmin() {
                if(this.$store.state.auth.user == null)
                    return false;

                return this.$store.state.auth.user.roles.find(x => x === ROLE_ADMIN);
            },

            hasRolePresenter() {
                if(this.$store.state.auth.user == null)
                    return false;

                return this.$store.state.auth.user.roles.find(x => x === ROLE_PRESENTER)
            }
        },
        methods: {
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            },
            notFound() {
                this.$router.push('/notFound');
            }
        },
        mounted() {
            EventService.on("logout", () => {
                this.logOut();
            });

            EventService.on("notFound", () => {
                this.notFound();
            });
        },
        beforeUnmount() {
            EventService.remove("logout");
        }
    };
</script>

<style>

</style>
