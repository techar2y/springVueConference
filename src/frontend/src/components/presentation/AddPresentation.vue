<template>
    <b-container>
        <b-form class="submit-form" v-if="!submitted">
            <b-card bg-variant="light">
                <b-form-group
                        label-cols-lg="3"
                        label="Новый доклад"
                        label-size="lg"
                        label-class="font-weight-bold pt-0"
                        class="mb-0"
                >

                    <b-form-group label="Тема доклада" label-for="title">
                        <b-form-input
                                type="text"
                                id="title"
                                name="title"
                                v-model="currentPresentation.title"
                                placeholder="Чем интересней заголовок тем больше студентов придёт на доклад"
                                :state="validationTitleInfo.value" @input="validateTitle">
                        </b-form-input>

                        <b-form-invalid-feedback :state="validationTitleInfo.value" id="titleInvalidFeedback">
                            {{ validationTitleInfo.invalid }}
                        </b-form-invalid-feedback>
                        <b-form-valid-feedback :state="validationTitleInfo.value" id="titleValidFeedback">
                            {{ validationTitleInfo.valid }}
                        </b-form-valid-feedback>
                    </b-form-group>

                    <b-form-group label="Предмет" label-for="subject">
                        <b-form-input
                                type="text"
                                id="subject"
                                name="subject"
                                v-model="currentPresentation.subject"
                                placeholder="Предмет доклада"
                                :state="validationSubjectInfo.value" @input="validateSubject">
                        </b-form-input>

                        <b-form-invalid-feedback :state="validationSubjectInfo.value" id="subjectInvalidFeedback">
                            {{ validationSubjectInfo.invalid }}
                        </b-form-invalid-feedback>
                        <b-form-valid-feedback :state="validationSubjectInfo.value" id="subjectValidFeedback">
                            {{ validationSubjectInfo.valid }}
                        </b-form-valid-feedback>
                    </b-form-group>

                    <b-form-group label="Описание" label-for="textarea">
                        <b-form-textarea
                                id="textarea"
                                name="textarea"
                                v-model="currentPresentation.description"
                                placeholder="Введите что нибудь..."
                                rows="3"
                                max-rows="6"
                        ></b-form-textarea>
                    </b-form-group>

                    <b-form-group label="Дата доклада" label-for="date">
                        <b-form-input
                                type="date"
                                id="date"
                                name="date"
                                v-model="currentPresentation.date"
                                :state="validationDateInfo.value"
                                @input="validateDate">
                        </b-form-input>
                        <b-form-invalid-feedback :state="validationDateInfo.value" id="dateInvalidFeedback">
                            {{ validationDateInfo.invalid }}
                        </b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group label="Время начало доклада" label-for="start_time">
                        <b-form-input
                                type="time"
                                id="start_time"
                                name="start_time"
                                v-model="currentPresentation.startTime"
                                :state="validationStartTimeInfo.value"
                                @input="validateStartTime">
                        </b-form-input>
                        <b-form-invalid-feedback :state="validationStartTimeInfo.value"
                                                 id="startTimeInvalidFeedback">
                            {{ validationStartTimeInfo.invalid }}
                        </b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group label="Продолжительность доклада" label-for="lasts">
                        <b-form-input
                                type="time"
                                id="lasts"
                                name="lasts"
                                v-model="currentPresentation.lasts"
                                :state="validationLastsInfo.value"
                                @input="validateLasts">
                        </b-form-input>

                        <b-form-invalid-feedback :state="validationLastsInfo.value"
                                                 id="lastsInvalidFeedback">
                            {{ validationLastsInfo.invalid }}
                        </b-form-invalid-feedback>
                    </b-form-group>

                    <b-form-group label="Аудитория" label-for="room">
                        <b-form-select
                                id="room"
                                name="room"
                                v-model="selectedRoomId"
                                :options="rooms"
                                :state="validationRoomInfo.value"
                                placeholder="Номер аудитории"
                                @input="validateRoom"
                                aria-placeholder="Значение">
                        </b-form-select>
                        <b-form-invalid-feedback :state="validationRoomInfo.value" id="roomInvalidFeedback">
                            {{ validationRoomInfo.invalid }}
                        </b-form-invalid-feedback>
                        <b-form-valid-feedback :state="validationRoomInfo.value" id="roomValidFeedback">
                            {{ validationRoomInfo.valid }}
                        </b-form-valid-feedback>
                    </b-form-group>

                    <b-form-group label="Преподаватель" label-for="presenter">
                        <b-form-select
                                id="presenter"
                                name="presenter"
                                v-model="selectedPresenterId"
                                :options="cmbFormPresenters"
                                placeholder="Ведущий"
                                :state="validationPresenterInfo.value">
                        </b-form-select>

                        <b-form-invalid-feedback :state="validationPresenterInfo.value" id="presenterInvalidFeedback">
                            {{ validationPresenterInfo.invalid }}
                        </b-form-invalid-feedback>
                        <b-form-valid-feedback :state="validationPresenterInfo.value" id="presenterValidFeedback">
                            {{ validationPresenterInfo.valid }}
                        </b-form-valid-feedback>
                    </b-form-group>


                    <b-button variant="outline-success" style="margin: 10px 5px 0px"
                              @click="createPresentation">Добавить
                    </b-button>
                    <b-button to="/presentations" variant="outline-secondary" style="margin: 10px 5px 0px">Отмена
                    </b-button>
                    <b-form-invalid-feedback :state="validationForm.value" id="formInvalidFeedback">
                        {{ validationForm.invalid }}
                    </b-form-invalid-feedback>
                </b-form-group>
            </b-card>
        </b-form>

        <div v-else>
            <h4>Доклад успешно добавлен!</h4>
            <b-button variant="success" size="sm" @click="newPresentation">Добавить ещё</b-button>

            <div>
                <b-button to="/presentations" variant="link">Вернуться к списку докладов</b-button>
            </div>
        </div>
    </b-container>
</template>

<script>
    import PresentationDataService from "../../services/PresentationDataService";
    import RoomDataService from "../../services/RoomDataService";
    import ValidatePresentationFormUtil from "../../utils/ValidatePresentationFormUtil";
    import UserDataService from "../../services/user/UserDataService";

    export default {
        name: "presentation-add",
        data() {
            return {
                currentPresentation: {
                    id: -1,
                    lasts: "",
                    subject: "",
                    description: "",
                    room: {},
                    title: "",
                    date: "",
                    presenters: [],
                    cmbFormPresenters: []
                },
                selectedRoomId: null,
                rooms: [],
                presenters: [],
                cmbFormPresenters: [],
                selectedPresenterId: null,
                submitted: false,
                validationTitleInfo: {valid: "", invalid: "", value: null},
                validationDescriptionInfo: {valid: "", invalid: "", value: null},
                validationLastsInfo: {valid: "", invalid: "", value: null},
                validationSubjectInfo: {valid: "", invalid: "", value: null},
                validationRoomInfo: {valid: "", invalid: "", value: null},
                validationPresenterInfo: {valid: "", invalid: "", value: null},
                validationDateInfo: {valid: "", invalid: "", value: null},
                validationStartTimeInfo: {valid: "", invalid: "", value: null},
                validationForm: {invalid: "", value: null}
            }
        }, methods: {
            async createPresentation() {
                let valid = await this.validateAll();
                if (!valid) {
                    this.validationForm.invalid = "Проверьте правильность заполнения полей формы";
                    this.validationForm.value = false;
                    return;
                }

                PresentationDataService.createPresentation(this.currentPresentation)
                    .then(result => {
                        this.currentPresentation.id = result.data;
                        this.submitted = true;
                    })
                    .catch(e => {
                        console.log(e);
                    })
            }, newPresentation() {
                this.submitted = false;
                this.selectedRoomId = null;
                this.selectedPresenterId = null;
                this.currentPresentation = {
                    id: -1,
                    date: "",
                    login: "",
                    subject: "",
                    description: "",
                    title: "",
                    room: {},
                    presenters: []
                };
                this.room = this.getRooms();
                this.submitted = false;
                this.validationTitleInfo = {valid: "", invalid: "", value: null};
                this.validationRoomInfo = {valid: "", invalid: "", value: null};
                this.validationPresenterInfo = {valid: "", invalid: "", value: null};
                this.validationDescriptionInfo = {valid: "", invalid: "", value: null};
                this.validationDateInfo = {valid: "", invalid: "", value: null};
                this.validationSubjectInfo = {valid: "", invalid: "", value: null};
                this.validationStartTimeInfo = {valid: "", invalid: "", value: null};
                this.validationForm = {invalid: "", value: null};
            }, getRooms() {
                return new Promise((resolve, reject) => {
                    RoomDataService.getAllRooms()
                        .then(result => {
                            if (result.data.length === 0)
                                return;

                            this.rooms = result.data;
                            this.rooms.forEach(obj => {
                                obj.value = obj.id;
                                delete obj.id;
                                obj.text = "Аудитория " + obj.number;
                                delete obj.number;
                            });
                            this.rooms.splice(0, 0, {
                                value: null,
                                text: "Выберете аудиторию для доклада",
                                disabled: true
                            });
                            resolve(this.rooms);
                        })
                        .catch(error => {
                            reject(error);
                        })
                })
            }, getPresenters() {
                return new Promise((resolve, reject) => {
                    let role = { name: "ROLE_PRESENTER" };
                    UserDataService.getUsersByUserRole(role)
                        .then(result => {
                            if (result.data.length === 0)
                                return;

                            this.presenters = result.data;
                            this.cmbFormPresenters.push({
                                value: null,
                                text: "Выберете преподавателя для доклада",
                                disabled: true
                            });
                            this.presenters.forEach(obj => {
                                this.cmbFormPresenters.push({value: obj.id, text: obj.fullName});
                            });
                            resolve(this.presenters);
                        })
                        .catch(error => {
                            reject(error);
                        })

                })
            }, validateAll() {
                return new Promise((resolve) => {
                    let valid = this.validateTitle();
                    valid = this.validateSubject() && valid;
                    valid = this.validateLasts() && valid;
                    valid = this.validateDate() && valid;
                    valid = this.validateRoom() && valid;
                    valid = this.validatePresenter() && valid;
                    valid = this.validateStartTime() && valid;
                    resolve(valid);
                })
            }, validateTitle() {
                return ValidatePresentationFormUtil.validateTitle(this);
            }, validateLasts() {
                return ValidatePresentationFormUtil.validateLasts(this);
            }, validateSubject() {
                return ValidatePresentationFormUtil.validateSubject(this);
            }, validateRoom() {
                return ValidatePresentationFormUtil.validateRoom(this);
            }, validatePresenter() {
                return ValidatePresentationFormUtil.validatePresenter(this);
            }, validateDate() {
                return ValidatePresentationFormUtil.validateDate(this);
            }, validateStartTime() {
                return ValidatePresentationFormUtil.validateStartTime(this);
            }

        }, created: async function () {
            try {
                await this.getRooms();
                await this.getPresenters();
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