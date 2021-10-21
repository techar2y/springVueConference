<template>
    <b-container class="bv-example-row">
        <b-row align-v="center" align-h="center">
            <b-col sm="4">
                <b-input-group prepend="Аудитория №" style="margin: 20px 0px 30px 0px; width: 200px;">
                    <b-form-select
                            class="form-control"
                            id="role"
                            required
                            v-model="selectedRoomId"
                            :options="cmbFormRooms"
                            name="role"
                            placeholder="Аудитория"
                            aria-placeholder="Значение"
                            @input="selectRoom">
                    </b-form-select>
                </b-input-group>
            </b-col>
            <b-col sm="4">
                <b-pagination
                        v-model="page"
                        :total-rows="pageCount"
                        :per-page="pageSize"
                        align="fill"
                        @change="handlePageChange"
                ></b-pagination>
            </b-col>
        </b-row>
        <b-row align-h="center">
            <b-col sm="10">
                <b-table hover
                         :items="items" :fields="flds" variant="dark"></b-table>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    import PresentationDataService from "../../services/PresentationDataService";
    import RoomDataService from "../../services/RoomDataService";

    export default {
        name: "schedules-list",
        data() {
            return {
                items: [],
                flds: [{key: "subject", label: "Предмет", sortable: true}, {key: "title", label: "Тема доклада"},
                    {key: "room.number", label: "Аудитория"}, {key: "date", label: "Дата", sortable: true},
                    {key: "startTime", label: "Время начала" }, {key: "lasts", label: "Длительность", sortable: true },
                    {key: "presenters[0].fullName", label: "Преподаватель" } ],
                rooms: [],
                cmbFormRooms: [],
                selectedRoomId: null,

                page: 1,
                pageCount: 0,
                pageSize: 10,
            };
        }, methods: {
            getPresentationsByRoom() {
                return new Promise((resolve, reject) => {
                    let params = {};

                    if (this.page) {
                        params["page"] = this.page - 1;
                    }

                    if (this.pageSize) {
                        params["pageSize"] = this.pageSize;
                    }
                    PresentationDataService.getPresentationsByRoom({id: this.selectedRoomId}, params)
                        .then(result => {

                            if (typeof result.data === 'undefined')
                                return;

                            this.pageCount = typeof result.data.totalItems !== 'undefined' ?
                                result.data.totalItems : 0;

                            resolve(result.data.presentations);
                        })
                        .catch(e => {
                            reject(e);
                        })
                })
            }, getRooms() {
                return new Promise((resolve, reject) => {
                    RoomDataService.getAllRooms()
                        .then(result => {
                            if (result.data.length === 0)
                                return;

                            this.selectedRoomId = 1;
                            this.rooms = result.data;
                            this.rooms.forEach(obj => {
                                this.cmbFormRooms.push({value: obj.id, text: obj.number});
                            });

                            resolve(this.rooms);
                        })
                        .catch(error => {
                            reject(error);
                        })
                })
            }, async selectRoom() {
                this.items = await this.getPresentationsByRoom();
            }, handlePageChange(value) {
                this.page = value;
                this.getPresentationsByRoom();
            }
        }, async created() {
            try {
                await this.getRooms();
                this.items = await this.getPresentationsByRoom();


            } catch (e) {
                console.log(e);
            }
        }
    }
</script>

<style scoped>
    .content {
        background-color: #78c9ff;
    }
</style>