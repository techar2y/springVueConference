import http from "../../http-common"
import authHeader from "./auth.service/auth-header";

const API_URL = "/rooms";

class RoomDataService {
    getAllRooms () {
        return http.get(`${API_URL}/getAllRooms`, { headers: authHeader() });
    }
}

export default new RoomDataService()