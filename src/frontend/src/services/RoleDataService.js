import http from "../../http-common"
import authHeader from "./auth.service/auth-header";

const API_URL = "/roles";

class RoleDataService {
    getAllRoles () {
        return http.get(`${API_URL}/getAllRoles`, { headers: authHeader() } );
    }
}

export default new RoleDataService()