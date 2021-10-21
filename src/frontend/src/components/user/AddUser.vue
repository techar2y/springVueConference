<template>
    <b-container>
        <b-form class="submit-form" v-if="!submitted">
            <b-card bg-variant="light">
                <b-form-group
                        label-cols-lg="3"
                        label="Новый пользователь"
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
                        <b-form-invalid-feedback :state="validationFullNameInfo.value" id="fullNameInvalidFeedback">
                            {{ validationFullNameInfo.invalid }}
                        </b-form-invalid-feedback>
                        <b-form-valid-feedback :state="validationFullNameInfo.value" id="fullNameValidFeedback">
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
                        <b-form-invalid-feedback :state="validationUsernameInfo.value" id="usernameInvalidFeedback">
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
                        <b-form-invalid-feedback :state="validationEmailInfo.value" id="emailInvalidFeedback">
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

                    <b-button variant="outline-success" style="margin: 10px 5px 0px"
                              @click="createUser">Добавить
                    </b-button>
                    <b-button to="/users" variant="outline-secondary" style="margin: 10px 5px 0px">Отмена
                    </b-button>
                    <b-form-invalid-feedback :state="validationForm.value" id="formInvalidFeedback">
                        {{ validationForm.invalid }}
                    </b-form-invalid-feedback>

                </b-form-group>
            </b-card>
        </b-form>

        <div v-else>
            <h4>Пользователь успешно добавлен!</h4>
            <b-button variant="success" @click="newUser">Добавить ещё</b-button>

            <b-button to="/users" variant="link">Вернуться к списку пользователей</b-button>
        </div>
    </b-container>
</template>

<script>
    import UserDataService from "../../services/user/UserDataService";
    import RoleDataService from "../../services/RoleDataService";

    export default {
        name: "user-add",
        data() {
            return {
                selectedRoleId: null,
                currentUser: {
                    id: -1,
                    fullName: "",
                    username: "",
                    email: "",
                    role: {}
                },
                roles: [],
                cmbFormRoles: [],
                submitted: false,
                validationFullNameInfo: {valid: "", invalid: "", value: null},
                validationUsernameInfo: {valid: "", invalid: "", value: null},
                validationEmailInfo: {valid: "", invalid: "", value: null},
                validationRoleInfo: {valid: "", invalid: "", value: null},
                validationForm: {invalid: "", value: null}
            }
        },
        methods: {
            async createUser() {
                let valid = await this.validateAll();
                if (!valid) {
                    this.validationForm.invalid = "Проверьте правильность заполнения полей формы";
                    this.validationForm.value = false;
                    return;
                }

                UserDataService.createUser(this.currentUser)
                    .then(result => {
                        this.currentUser.id = result.data;
                        this.submitted = true;
                    })
                    .catch(e => {
                        console.log(e);
                    })
            },
            newUser() {
                this.getRoles();
                this.submitted = false;
                this.selectedRoleId = null;
                this.currentUser = {id: -1, fullName: "", username: "", email: "", role: {}};
                this.submitted = false;
                this.validationFullNameInfo = {valid: "", invalid: "", value: null};
                this.validationUsernameInfo = {valid: "", invalid: "", value: null};
                this.validationEmailInfo = {valid: "", invalid: "", value: null};
                this.validationRoleInfo = {valid: "", invalid: "", value: null};
                this.validationForm = {invalid: "", value: null};
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
                    this.validationUsernameInfo.invalid = "Логин не может стостоять меньше чем из двух символов";
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
                            return this.validationUsernameInfo.value;
                        }

                        this.validationUsernameInfo.value = true;
                        return this.validationUsernameInfo.value;
                    })
                    .catch(error => {
                        console.log(error);
                        return false;
                    })
            },
            async validateEmail() {
                if (this.currentUser.email.length < 2) {
                    this.validationEmailInfo.invalid = "Email не может стостоять меньше чем из двух символов";
                    this.validationEmailInfo.value = false;
                    return this.validationEmailInfo.value;
                }

                const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                if (!re.test(String(this.currentUser.email).toLowerCase())) {
                    this.validationEmailInfo.invalid = "Такая почта не подойдет";
                    this.validationEmailInfo.value = false;
                    return this.validationEmailInfo.value;
                }


                UserDataService.isEmailUnique(this.currentUser.email, this.currentUser.id)
                    .then(response => {
                        if (response.data > 0) {
                            this.validationEmailInfo.invalid = "Такой почтовый адрес уже занят";
                            this.validationEmailInfo.value = false;
                            return this.validationEmailInfo.value;
                        }

                        this.validationEmailInfo.value = true;
                        return this.validationEmailInfo.value;
                    })
                    .catch(error => {
                        console.log(error);
                        return false;
                    })
            },
            validateRole() {
                if (this.selectedRoleId == null) {
                    this.validationRoleInfo.value = false;
                    return this.validationRoleInfo.value;
                }

                let role = this.roles.find(x => x.value === this.selectedRoleId);
                this.currentUser.role.id = role.value;
                this.currentUser.role.status = role.text;
                this.currentUser.role.name = role.name;
                this.validationRoleInfo.value = true;
                return this.validationRoleInfo.value;
            },
            validateAll() {
                return new Promise((resolve) => {
                    let valid = this.validateFullName();
                    this.validateUsername()
                        .then(resolve => {
                            valid = resolve || valid;
                        })
                        .catch(reject => console.log(reject));
                    this.validateEmail()
                        .then(resolve => {
                            valid = resolve || valid
                        })
                        .catch(reject => console.log(reject));
                    valid = this.validateRole() || valid;
                    resolve(valid);
                })
            }
        },
        created: async function () {
            try {
                await this.getRoles();
            } catch (e) {
                console.log(e);
            }
        }
    }
</script>

<style scoped>
    .submit-form {
        max-width: 850px;
        margin: auto;
    }
</style>