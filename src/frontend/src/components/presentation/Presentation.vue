<template>
    <b-container>
        <div v-if="currentPresentation != null">
            <div v-if="!notFound">
                <div v-if="!deleted && !updated">
                    <b-form class="edit-form">
                        <b-card bg-variant="light">
                            <b-form-group
                                    label-cols-lg="3"
                                    label="Редактировать доклад"
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

                                    <b-form-invalid-feedback :state="validationTitleInfo.value"
                                                             id="titleInvalidFeedback">
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

                                    <b-form-invalid-feedback :state="validationSubjectInfo.value"
                                                             id="subjectInvalidFeedback">
                                        {{ validationSubjectInfo.invalid }}
                                    </b-form-invalid-feedback>
                                    <b-form-valid-feedback :state="validationSubjectInfo.value"
                                                           id="subjectValidFeedback">
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
                                            @input="validateRoom">
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
                                            :state="validationPresenterInfo.value">
                                    </b-form-select>

                                    <b-form-invalid-feedback :state="validationPresenterInfo.value"
                                                             id="presenterInvalidFeedback">
                                        {{ validationPresenterInfo.invalid }}
                                    </b-form-invalid-feedback>
                                    <b-form-valid-feedback :state="validationPresenterInfo.value"
                                                           id="presenterValidFeedback">
                                        {{ validationPresenterInfo.valid }}
                                    </b-form-valid-feedback>
                                </b-form-group>

                                <b-button @click="updatePresentation(currentPresentation.id)"
                                          variant="outline-success" style="margin: 5px 5px 5px 0px">
                                    Обновить
                                </b-button>

                                <b-button @click="deletePresentationById(currentPresentation.id)"
                                          variant="outline-danger" style="margin: 5px 5px 5px 0px">
                                    Удалить
                                </b-button>

                                <b-button to="/presentations" variant="outline-secondary"
                                          style="margin: 5px 5px 5px 0px">
                                    Отмена
                                </b-button>

                            </b-form-group>
                        </b-card>
                    </b-form>
                </div>

                <div v-else-if="deleted">
                    <p>Пользователь успешно удалён</p>
                    <div>
                        <b-button to="/presentations" variant="link">Вернуться к списку пользователей</b-button>
                    </div>
                </div>


                <div v-else-if="updated">
                    <div style="text-align: center">
                        <p>Пользователь успешно обновлён</p>
                        <b-button to="/presentations" variant="link">Вернуться к списку пользователей</b-button>
                    </div>
                </div>
            </div>
        </div>

    </b-container>
</template>

<script>
    import PresentationDataService from "../../services/PresentationDataService";
    import RoomDataService from "../../services/RoomDataService";
    import ValidatePresentationFormUtil from "../../utils/ValidatePresentationFormUtil";
    import UserDataService from "../../services/user/UserDataService";

    const ROLE_PRESENTER = { name: "PRESENTER" };

    export default {
        name: "presentation-edit",
        data() {
            return {
                notFound: false,
                currentPresentation: null,
                deleted: false,
                updated: false,
                selectedRoomId: null,
                rooms: [],
                presenters: [],
                cmbFormPresenters: [],
                selectedPresenterId: null,
                validationTitleInfo: {valid: "", invalid: "", value: null},
                validationDescriptionInfo: {valid: "", invalid: "", value: null},
                validationLastsInfo: {valid: "", invalid: "", value: null},
                validationSubjectInfo: {valid: "", invalid: "", value: null},
                validationRoomInfo: {valid: "", invalid: "", value: null},
                validationPresenterInfo: {valid: "", invalid: "", value: null},
                validationDateInfo: {valid: "", invalid: "", value: null},
                validationStartTimeInfo: {valid: "", invalid: "", value: null},
                validationForm: {invalid: "", value: null}
            };
        },
        methods: {
            getPresentationById(id) {
                return new Promise((resolve, reject) => {
                    PresentationDataService.getPresentationById(id)
                        .then(result => {
                            this.currentPresentation = result.data;
                            this.selectedRoomId = this.currentPresentation.room.id;
                            let presenter = this.currentPresentation.presenters[0];
                            if (typeof presenter !== 'undefined')
                                this.selectedPresenterId = presenter.id;
                            resolve(this.currentPresentation);
                        })
                        .catch(error => {
                            this.notFound = true;
                            reject(error);
                        })
                })
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
            }, deletePresentationById(id) {
                PresentationDataService.deletePresentationById(id)
                    .then(() => {
                        this.currentPresentation = null;
                        this.deleted = true;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }, async updatePresentation(id) {
                let valid = await this.validateAll();
                if (!valid) {
                    this.validationForm.invalid = "Проверьте правильность заполнения полей формы";
                    this.validationForm.value = false;
                    return;
                }

                console.log(this.currentPresentation);
                PresentationDataService.updatePresentation(id, this.currentPresentation)
                    .then(() => {
                        this.updated = true;
                    })
                    .catch(error => {
                        console.log(error);
                    });
            }, getPresenters() {
                return new Promise((resolve, reject) => {
                    UserDataService.getUsersByUserRole(ROLE_PRESENTER)
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
            }/*, IsDateTimeUnique() {
                return new Promise((resolve, reject) => {
                    PresentationDataService.isDateTimeUnique(date)
                        .then(result => {

                            resolve(date);
                        })
                        .catch(error => {
                            reject(error);
                        })
                })
            }*/, validateAll() {
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
        },
        created: async function () {
            try {
                await this.getRooms();
                await this.getPresenters();
                await this.getPresentationById(this.$route.params.id);
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