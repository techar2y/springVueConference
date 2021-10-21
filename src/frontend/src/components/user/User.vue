<template>
    <b-container>
        <div v-if="currentUser != null">
            <div v-if="!notFound">
                <div v-if="!deleted && !updated">
                    <b-form class="edit-form">
                        <b-card bg-variant="light">
                            <b-form-group
                                    label-cols-lg="3"
                                    label="Редактировать пользователя"
                                    label-size="lg"
                                    label-class="font-weight-bold pt-0"
                                    class="mb-0"
                            >
                                <b-form-group label="Полное имя" label-for="fullName">
                                    <b-input-group size="sm" class="mb-2">
                                        <b-input-group-prepend is-text>
                                            <b-icon icon="person-square"></b-icon>
                                        </b-input-group-prepend>
                                        <b-form-input
                                                type="text"
                                                class="form-control"
                                                required
                                                v-model="currentUser.fullName"
                                                placeholder="Peter Parker"
                                                name="fullName"
                                                id="fullName"
                                                :state="validationFullNameInfo.value" @input="validateFullName">
                                        </b-form-input>
                                    </b-input-group>
                                    <b-form-invalid-feedback :state="validationFullNameInfo.value"
                                                             id="fullNameInvalidFeedback">
                                        {{ validationFullNameInfo.invalid }}
                                    </b-form-invalid-feedback>
                                    <b-form-valid-feedback :state="validationFullNameInfo.value"
                                                           id="fullNameValidFeedback">
                                    </b-form-valid-feedback>
                                </b-form-group>

                                <b-form-group label="Логин" label-for="username">
                                    <b-input-group size="sm" class="mb-2">
                                        <b-input-group-prepend is-text>
                                            <b-icon icon="at"></b-icon>
                                        </b-input-group-prepend>
                                        <b-form-input
                                                type="text"
                                                class="form-control"
                                                id="username"
                                                required
                                                v-model="currentUser.username"
                                                name="username"
                                                placeholder="username"
                                                :state="validationUsernameInfo.value" @input="validateUsername">
                                        </b-form-input>
                                    </b-input-group>
                                    <b-form-invalid-feedback :state="validationUsernameInfo.value"
                                                             id="usernameInvalidFeedback">
                                        {{ validationUsernameInfo.invalid }}
                                    </b-form-invalid-feedback>
                                    <b-form-valid-feedback :state="validationUsernameInfo.value" id="usernameValidFeedback">
                                        {{ validationUsernameInfo.valid }}
                                    </b-form-valid-feedback>
                                </b-form-group>

                                <b-form-group label="Email" label-for="email">
                                    <b-input-group size="sm" class="mb-2">
                                        <b-input-group-prepend is-text>
                                            <b-icon icon="envelope"></b-icon>
                                        </b-input-group-prepend>
                                        <b-form-input
                                                type="email"
                                                class="form-control"
                                                id="email"
                                                required
                                                v-model="currentUser.email"
                                                name="email"
                                                placeholder="me@example.com"
                                                :state="validationEmailInfo.value" @input="validateEmail">
                                        </b-form-input>
                                    </b-input-group>
                                    <b-form-invalid-feedback :state="validationEmailInfo.value"
                                                             id="emailInvalidFeedback">
                                        {{ validationEmailInfo.invalid }}
                                    </b-form-invalid-feedback>
                                    <b-form-valid-feedback :state="validationEmailInfo.value" id="emailValidFeedback">
                                        {{ validationEmailInfo.valid }}
                                    </b-form-valid-feedback>
                                </b-form-group>

                                <b-form-group label="Статус" label-for="role">
                                    <b-form-select
                                            class="form-control"
                                            id="role"
                                            required
                                            v-model="selectedRoleId"
                                            :options="cmbFormRoles"
                                            :state="validationRoleInfo.value"
                                            name="role"
                                            placeholder="user's role"
                                            @input="validateRole"
                                            aria-placeholder="Значение">
                                    </b-form-select>
                                    <b-form-invalid-feedback :state="validationRoleInfo.value" id="roleInvalidFeedback">
                                        {{ validationRoleInfo.invalid }}
                                    </b-form-invalid-feedback>
                                    <b-form-valid-feedback :state="validationRoleInfo.value" id="roleValidFeedback">
                                        {{ validationRoleInfo.valid }}
                                    </b-form-valid-feedback>
                                </b-form-group>

                                <b-button @click="updateUser(currentUser.id, currentUser)"
                                          variant="outline-success" style="margin: 5px 5px 5px 0px">
                                    Обновить
                                </b-button>

                                <b-button @click="deleteUserById(currentUser.id)"
                                          variant="outline-danger" style="margin: 5px 5px 5px 0px">
                                    Удалить
                                </b-button>

                                <b-button to="/users" variant="outline-secondary" style="margin: 5px 5px 5px 0px">
                                    Отмена
                                </b-button>
                            </b-form-group>
                        </b-card>
                    </b-form>
                </div>

                <div v-else-if="deleted">
                    <p>Пользователь успешно удалён</p>
                    <div>
                        <b-button to="/users" variant="link">Вернуться к списку пользователей</b-button>
                    </div>
                </div>


                <div v-else-if="updated">
                    <div style="text-align: center">
                        <p>Пользователь успешно обновлён</p>
                        <b-button to="/users" variant="link">Вернуться к списку пользователей</b-button>
                    </div>
                </div>

            </div>
            <div v-else>
                <h4>Пользователь не найден</h4>
            </div>
        </div>
        <div v-else>
            <div class="d-flex justify-content-center mb-3">
                <b-spinner label="Loading..."></b-spinner>
            </div>
        </div>
    </b-container>

</template>

<script>
    import UserDataService from "../../services/user/UserDataService";
    import RoleDataService from "../../services/RoleDataService";

    export default {
        name: "user-edit",
        data() {
            return {
                notFound: false,
                currentUser: null,
                deleted: false,
                updated: false,
                selectedRoleId: null,
                roles: [],
                cmbFormRoles: [],
                validationFullNameInfo: {valid: "", invalid: "", value: null},
                validationUsernameInfo: {valid: "", invalid: "", value: null},
                validationEmailInfo: {valid: "", invalid: "", value: null},
                validationRoleInfo: {valid: "", invalid: "", value: null},
                validationForm: {invalid: "", value: null}
            };
        },
        methods: {
            getUserById(id) {
                return new Promise((resolve, reject) => {
                    UserDataService.getUserById(id)
                        .then(result => {
                            this.currentUser = result.data;
                            this.selectedRoleId = this.currentUser.roles[0].id;
                            resolve(this.currentUser);
                        })
                        .catch(error => {
                            this.notFound = true;
                            reject(error);
                        });
                })
            },
            getRoles() {
                return new Promise((resolve, reject) => {
                    RoleDataService.getAllRoles()
                        .then(result => {
                            this.roles = result.data;
                            this.cmbFormRoles.push({value: null, text: "Выберете статус пользователя", disabled: true});

                            this.roles.forEach(obj => {
                                this.cmbFormRoles.push({value: obj.id, text: obj.name});
                            });
                            resolve(this.roles);
                        })
                        .catch(error => {
                            reject(error);
                        })
                })
            },
            deleteUserById(id) {
                UserDataService.deleteUserById(id)
                    .then(() => {
                        this.currentUser = null;
                        this.deleted = true;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },
            updateUser(id, user) {
                UserDataService.updateUser(id, user)
                    .then(() => {
                        this.updated = true;
                        //setTimeout(() => this.updated = false, 3000);
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },
            validateFullName() {

                if (this.currentUser.fullName.length < 1
                    || this.currentUser.fullName.length > 128) {
                    this.validationFullNameInfo.invalid = "Поле обязательное к заполнению";
                    this.validationFullNameInfo.value = false;
                    return this.validationFullNameInfo.value;
                } else if (!/^[a-zA-Z\s]+$/.test(this.currentUser.fullName)) {
                    this.validationFullNameInfo.invalid = "Имя не должно содержать числа и специальные символы";
                    this.validationFullNameInfo.value = false;
                    return this.validationFullNameInfo.value;
                } else
                    this.validationFullNameInfo.value = true;
            },
            async validateUsername() {
                if (this.currentUser.username.length < 2) {
                    this.validationUsernameInfo.invalid = "Логин не может состоять меньше чем из двух символов";
                    this.validationUsernameInfo.value = false;
                    return this.validationUsernameInfo.value;
                }

                if (this.currentUser.username.length > 64) {
                    this.validationUsernameInfo.invalid = "Логин не может превышать 64 символа";
                    this.validationUsernameInfo.value = false;
                    return this.validationUsernameInfo.value;
                }

                UserDataService.isUsernameUnique(this.currentUser.username, this.currentUser.id)
                    .then(response => {
                        if (response.data > 0) {
                            this.validationUsernameInfo.invalid = "Такой логин уже занят";
                            this.validationUsernameInfo.value = false;
                            return this.validationFullNameInfo.value;
                        }

                        this.validationUsernameInfo.value = true;
                        return this.validationFullNameInfo.value;
                    })
                    .catch(error => {
                        console.log(error);
                        return false;
                    })
            },
            async validateEmail() {
                UserDataService.isEmailUnique(this.currentUser.email, this.currentUser.id)
                    .then(response => {
                        //console.log(response.data);

                        if (response.data > 0) {
                            this.validationEmailInfo.invalid = "Такой почтовый адрес уже занят";
                            this.validationEmailInfo.value = false;
                            return this.validationFullNameInfo.value;
                        }

                        this.validationEmailInfo.value = true;
                        return this.validationFullNameInfo.value;
                    })
                    .catch(error => {
                        console.log(error);
                        return false;
                    })
            },
            validateRole() {
                if (this.selectedRoleId == null) {
                    this.validationRoleInfo.value = false;
                    return this.validationFullNameInfo.value;
                }

                console.log(this.roles);
                console.log(this.selectedRoleId);
                let role = this.roles.find(x => x.id === this.selectedRoleId);
                this.currentUser.roles = [];
                this.currentUser.roles.push(role);
                console.log(this.currentUser.roles);
                this.validationRoleInfo.value = true;
                return this.validationFullNameInfo.value;
            },
            validateAll() {
                this.validateFullName();
                this.validateUsername();
                this.validateEmail();
                this.validateRole();
            }
        }, created: async function () {
            try {
                await this.getUserById(this.$route.params.id);
                await this.getRoles();
                this.validateAll();
            } catch (e) {
                console.log(e);
            }
        }
    }
</script>

<style scoped>
    .edit-form {
        max-width: 850px;
        margin: auto;
    }
</style>