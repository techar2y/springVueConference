<template>
    <b-container class="bv-example-row">
        <b-row align-v="center" align-h="center" class="mb-3">
            <b-col sm="10">
                <b-input-group class="mb-3" style="width: 700px">
                    <b-input-group-prepend is-text>
                        <b-icon icon="search"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input id="input_search" type="search" v-model="searchStr"
                                  placeholder="Поиск пользователя по логину">
                    </b-form-input>
                    <b-button variant="outline-info" @click="getAllUsers">Искать
                        <b-icon icon="search"></b-icon>
                    </b-button>
                </b-input-group>
            </b-col>
        </b-row>

        <b-row align-v="top" align-h="center" class="mb-3">
            <b-col sm="5">
                Пользователей на странице
                <select v-model="pageSize" @change="handlePageSizeChange($event)">
                    <option v-for="size in pageSizes" :key="size" :value="size">
                        {{ size }}
                    </option>
                </select>
            </b-col>

            <b-col sm="5">
                <b-pagination
                        v-model="page"
                        :total-rows="pageCount"
                        :per-page="pageSize"
                        pills
                        @change="handlePageChange"
                ></b-pagination>
            </b-col>
        </b-row>

        <b-row align-v="top" align-h="center">
            <b-col sm="5">
                <h4>Список пользователей</h4>
                <div v-if="!pending">
                    <ul class="list-group" v-if="Object.keys(users).length !== 0">
                        <li class="list-group-item"
                            :class="{ active: index == currentIndex }"
                            v-for="(user, index) in users"
                            :key="index"
                            @click="setActiveUser(user, index)"
                        >
                            <div style="display:inline" v-if="Object.keys(user.fullName).length !== 0"> {{ user.fullName
                                }}
                            </div>
                            <div style="display:inline" v-else> Отсутствует полное имя</div>
                        </li>
                    </ul>
                    <p v-else>Пользователи отсутствуют</p>
                </div>
                <div v-else>
                    <div class="m-5" label="Busy">
                        <b-spinner label="Loading..."></b-spinner>
                    </div>
                </div>


                <b-button to="/addUser" variant="success" size="sm" style="margin: 10px 5px 0px">
                    Добавить пользователя
                </b-button>

                <b-button variant="danger" size="sm" style="margin: 10px 5px 0px"
                          @click="deleteAllUsers"
                          v-if="Object.keys(users).length !== 0">
                    Очистить весь список
                </b-button>
            </b-col>

            <b-col sm="5">
                <div v-if="currentUser">
                    <h4>Пользователь</h4>
                    <div>
                        <label><strong>ID:</strong></label> {{ currentUser.id }}
                    </div>
                    <div>
                        <label><strong>Полное имя:</strong></label> {{ currentUser.fullName }}
                    </div>
                    <div>
                        <label><strong>Email:</strong></label> {{ currentUser.email }}
                    </div>
                    <div>
                        <label><strong>Логин:</strong></label> {{ currentUser.username }}
                    </div>
                    <div v-for="role in currentUser.roles" :key="role.id">
                        <label><strong>Статус:</strong></label> {{ role.name }}
                    </div>

                    <b-button v-bind:to="`/users/` + currentUser.id" class="btn btn-sm btn-warning"
                              style="margin: 10px 0px">
                        Редактировать
                    </b-button>
                </div>
                <div v-else>
                    <br/>
                    <p v-if="Object.keys(users).length !== 0">Пожалуйста выберите пользователя</p>
                </div>
            </b-col>

        </b-row>

    </b-container>
</template>

<script>
    import UserDataService from "../../services/user/UserDataService";
    //import EventService from "../../services/event-service";

    export default {
        name: "users-list",
        data() {
            return {
                users: [],
                currentUser: null,
                currentIndex: -1,
                searchStr: "",
                pending: false,

                page: 1,
                pageCount: 0,
                pageSize: 3,

                pageSizes: [3, 6, 9]
            };
        },
        methods: {
            getAllUsers() {
                let params = this.getRequestParams(this.searchStr, this.page, this.pageSize);
                this.pending = true;
                UserDataService.getAllUsers(params)
                    .then(result => {
                        if (typeof result.data === 'undefined')
                            return;
                        this.users = typeof result.data.users !== 'undefined' ? result.data.users : {};
                        this.pageCount = typeof result.data.totalItems !== 'undefined' ?
                            result.data.totalItems : 0;

                        this.pending = false;
                    }/*, error => {
                        this.content =
                            (error.response && error.response.data && error.response.data.message) ||
                            error.message ||
                            error.toString();

                        if (error.response && error.response.status === 401) {
                            EventService.dispatch("logout");
                        }
                    }*/)
                    .catch(e => {
                        this.pending = false;
                        console.log(e);
                    });
            },
            setActiveUser(user, index) {
                this.currentUser = user;
                this.currentIndex = user ? index : -1;
            },
            deleteAllUsers() {
                UserDataService.deleteAllUsers()
                    .then(() => {
                        this.users = {};
                        this.currentUser = null;
                        this.currentIndex = -1;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            },
            getRequestParams(searchStr, page, pageSize) {
                let params = {};

                if (searchStr) {
                    params["searchStr"] = searchStr;
                }

                if (page) {
                    params["page"] = page - 1;
                }

                if (pageSize) {
                    params["pageSize"] = pageSize;
                }

                return params;
            },
            handlePageChange(value) {
                this.page = value;
                this.getAllUsers();
            },

            handlePageSizeChange(event) {
                this.pageSize = event.target.value;
                this.page = 1;
                this.getAllUsers();
            }
        },
        mounted() {
            this.getAllUsers();
        }
    }
</script>

<style scoped>

</style>