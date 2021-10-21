<template>
    <b-container class="card card-container">
        <img
                id="profile-img"
                src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                class="profile-img-card"
        />

        <b-form name="form" @submit.prevent="handleRegister">
            <div v-if="!successful">
                <b-form-group label="Полное имя" label-for="fullname">
                    <b-form-input
                            v-model="user.fullname"
                            v-validate="'required'"
                            type="text"
                            class="form-control"
                            name="fullname"
                    />
                    <div
                            v-if="errors.has('fullname')"
                            class="alert alert-danger"
                            role="alert"
                    >Нужно заполнить ФИО!
                    </div>
                </b-form-group>

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
                    >Задайте имя пользователя!
                    </div>
                </b-form-group>

                <b-form-group label="Email" label-for="email">
                    <b-form-input
                            v-model="user.email"
                            v-validate="'required'"
                            type="text"
                            class="form-control"
                            name="email"
                    />
                    <div
                            v-if="errors.has('email')"
                            class="alert alert-danger"
                            role="alert"
                    >Введите почту!
                    </div>
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
                    >Password is required!
                    </div>
                </b-form-group>

                <b-button block variant="primary">Зарегистрироваться</b-button>
            </div>
        </b-form>
    </b-container>
</template>

<script>
    import User from '../../models/User';

    export default {
        name: 'register',
        data() {
            return {
                user: new User('', '', '', ''),
                submitted: false,
                successful: false,
                message: ''
            };
        },
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }
        },
        mounted() {
            if (this.loggedIn) {
                this.$router.push('/profile');
            }
        },
        methods: {
            handleRegister() {
                this.message = '';
                this.submitted = true;
                this.$validator.validate().then(isValid => {
                    if (isValid) {
                        this.$store.dispatch('auth/register', this.user).then(
                            data => {
                                this.message = data.message;
                                this.successful = true;
                            },
                            error => {
                                this.message =
                                    (error.response && error.response.data) ||
                                    error.message ||
                                    error.toString();
                                this.successful = false;
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