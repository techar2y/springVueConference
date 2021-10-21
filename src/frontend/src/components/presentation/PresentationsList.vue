<template>
    <b-container class="bv-example-row">
        <b-row align-v="center" align-h="center" class="mb-3">
            <b-col sm="10">
                <b-input-group class="mb-3" style="width: 700px">
                    <b-input-group-prepend is-text>
                        <b-icon icon="search"></b-icon>
                    </b-input-group-prepend>
                    <b-form-input id="input-1" v-model="searchStr" placeholder="Тема доклада">
                    </b-form-input>
                    <b-button variant="outline-info" @click="getAllPresentations">Искать
                        <b-icon icon="search"></b-icon>
                    </b-button>

                </b-input-group>
            </b-col>
        </b-row>

        <b-row align-v="top" align-h="center" class="mb-3">
            <b-col sm="5">
                Докладов на странице
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
                        @change="handlePageChange($event)"
                ></b-pagination>
            </b-col>
        </b-row>

        <b-row align-v="top" align-h="center">
            <b-col sm="5">
                <h4>Список докладов</h4>
                <div v-if="!pending">
                    <ul v-if="Object.keys(presentations).length !== 0" class="list-group">
                        <li class="list-group-item"
                            :class="{ active: index == currentIndex }"
                            v-for="(presentation, index) in presentations"
                            :key="index"
                            @click="setActivePresentation(presentation, index)"
                        >
                            <div style="display:inline" v-if="Object.keys(presentation.title).length !== 0">
                                {{ presentation.title }}
                            </div>
                            <div v-else style="display:inline"> Отсутствует заголовок</div>
                        </li>
                    </ul>
                    <p v-else>Доклады отсутствуют</p>
                </div>
                <div v-else>
                    <div class="m-5" label="Busy">
                        <b-spinner label="Loading..."></b-spinner>
                    </div>
                </div>

                <b-button to="/addPresentation" variant="success" size="sm" style="margin: 10px 5px 0px">
                    Добавить доклад
                </b-button>

                <b-button variant="danger" size="sm" style="margin: 10px 5px 0px"
                          @click="deleteAllPresentations"
                          v-if="Object.keys(presentations).length !== 0">
                    Очистить весь список
                </b-button>
            </b-col>

            <b-col sm="5">
                <div v-if="currentPresentation !== null">
                    <h4>Доклад</h4>
                    <div>
                        <label><strong>ID:</strong></label> {{ currentPresentation.id }}
                    </div>
                    <div>
                        <label><strong>Заголовок</strong></label> {{ currentPresentation.title }}
                    </div>
                    <div>
                        <label><strong>Предмет:</strong></label> {{ currentPresentation.subject }}
                    </div>
                    <div v-if="currentPresentation.description.length > 0">
                        <label><strong>Описание:</strong></label> {{ currentPresentation.description }}
                    </div>
                    <div>
                        <label><strong>Дата:</strong></label> {{ currentPresentation.date }}
                    </div>
                    <div>
                        <label><strong>Начало в:</strong></label> {{ currentPresentation.startTime }}
                    </div>
                    <div>
                        <label><strong>Длительность:</strong></label> {{ currentPresentation.lasts }}
                    </div>
                    <div>
                        <label><strong>Аудитория:</strong></label> {{ currentPresentation.room.number }}
                    </div>
                    <div v-if="currentPresenter != null">
                        <label><strong>Преподаватель:</strong></label> {{ currentPresenter.fullName }}
                    </div>

                    <b-button v-bind:to="`/presentations/` + currentPresentation.id" variant="warning" size="sm"
                              style="margin: 10px 0px">
                        Редактировать
                    </b-button>
                </div>
                <div v-else>
                    <br/>
                    <p v-if="Object.keys(presentations).length !== 0">Пожалуйста выберите доклад</p>
                </div>
            </b-col>
        </b-row>

    </b-container>
</template>

<script>
    import PresentationDataService from "../../services/PresentationDataService";
    import EventService from "../../services/event-service";

    export default {
        name: "presentations-list",
        data() {
            return {
                presentations: [],
                currentPresentation: null,
                currentPresenter: null,
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
            getAllPresentations() {
                let params = this.getRequestParams(this.searchStr, this.page, this.pageSize);
                this.pending = true;
                PresentationDataService.getAllPresentations(params)
                    .then(result => {
                        if (typeof result.data === 'undefined')
                            return;

                        this.presentations = typeof result.data.presentations !== 'undefined' ? result.data.presentations : {};
                        this.pageCount = typeof result.data.totalItems != 'undefined' ? result.data.totalItems : 0;
                        this.pending = false;
                    }, error => {
                        this.content =
                            (error.response && error.response.data && error.response.data.message) ||
                            error.message ||
                            error.toString();

                        if (error.response && error.response.status === 401) {
                            EventService.dispatch("logout");
                        }
                    })
                    .catch(e => {
                        this.pending = false;
                        console.log(e);
                    })
            },
            setActivePresentation(presentation, index) {
                this.currentPresentation = presentation;

                this.currentPresenter = this.currentPresentation.presenters.length > 0 ? this.currentPresentation.presenters[0] : "Не найден";
                this.currentIndex = presentation ? index : -1;
            },
            deleteAllPresentations() {
                PresentationDataService.deleteAllPresentations()
                    .then(() => {
                        this.presentations = [];
                        this.currentPresentation = null;
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
                this.getAllPresentations();
            },

            handlePageSizeChange(event) {
                this.pageSize = event.target.value;
                this.page = 1;
                this.getAllPresentations();
            }
        },
        mounted() {
            this.getAllPresentations();
        }
    }
</script>

<style scoped>

</style>