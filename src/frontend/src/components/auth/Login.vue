<template>
    <b-container class="card card-container">
        <img
                id="profile-img"
                src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                class="profile-img-card"
        />
        <b-form name="form" @submit.prevent="handleUsername">
            <b-form-group label="Имя пользователя" label-for="username">
                <b-form-input
                        v-model="user.username"
                        v-validate="'required'"
                        type="text"
                        class="form-control"
                        name="username"
                />
                <div
                        v-if="errors.has('username')"
                        class="alert alert-danger"
                        role="alert"
                >Username is required!</div>
            </b-form-group>

            <b-form-group label="Пароль" label-for="password">
                <b-form-input
                        v-model="user.password"
                        v-validate="'required'"
                        type="password"
                        class="form-control"
                        name="password"
                />
                <div
                        v-if="errors.has('password')"
                        class="alert alert-danger"
                        role="alert"
                >Password is required!</div>
            </b-form-group>

            <b-button block variant="primary" @click="handleUsername">Войти</b-button>
        </b-form>

        <b-alert show class="mt-3 mb-0">
            <p>Listener:
                <br>Username - listener,
                <br>Password - listener
            </p>
            <p>Presenter:
                <br>Username - presenter,
                <br>Password - presenter
            </p>
            <p>Administrator:
                <br>Username: admin,
                <br>Password: admin
            </p>
        </b-alert>
    </b-container>

</template>

<script>
    import User from '../../models/User';

    export default {
        name: 'login',
        data() {
            return {
                user: new User('', '', '', ''),
                loading: false,
                message: ''
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        created() {
            if (this.loggedIn) {
                this.$router.push('/profile');
            }
        },
        methods: {
            handleUsername() {
                this.loading = true;
                this.$validator.validateAll().then(isValid => {
                    if (!isValid) {
                        this.loading = false;
                        return;
                    }

                    if (this.user.username && this.user.password) {
                        this.$store.dispatch('auth/login', this.user).then(
                            () => {
                                this.$router.push('/profile');
                            },
                            error => {
                                this.loading = false;
                                this.message =
                                    (error.response && error.response.data) ||
                                    error.message ||
                                    error.toString();
                            }
                        );
                    }
                });
            }
        }
    };
</script>

<style scoped>
    label {
        display: block;
        margin-top: 10px;
    }

    .card-container.card {
        max-width: 350px !important;
        padding: 40px 40px;
    }

    .card {
        background-color: #f7f7f7;
        padding: 20px 25px 30px;
        margin: 0 auto 25px;
        margin-top: 50px;
        -moz-border-radius: 2px;
        -webkit-border-radius: 2px;
        border-radius: 2px;
        -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
    }

    .profile-img-card {
        width: 96px;
        height: 96px;
        margin: 0 auto 10px;
        display: block;
        -moz-border-radius: 50%;
        -webkit-border-radius: 50%;
        border-radius: 50%;
    }
</style>