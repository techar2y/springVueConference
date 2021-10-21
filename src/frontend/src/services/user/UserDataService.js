import http from "../../../http-common.js";
import authHeader from "../auth.service/auth-header";

const API_URL = "/users";

class UserDataService {

    getAllUsers(params) {
        return http.get(`${API_URL}/getAllUsers`, { params: params, headers: authHeader() });
    }

    getUserById(id) {
        return http.get(`${API_URL}/getUserById/${id}`, { headers: authHeader() });
    }

    createUser(data) {
        return http.post(`${API_URL}/createUser`, data, { headers: authHeader() });
    }

    deleteAllUsers() {
        return http.delete(`${API_URL}/deleteAllUsers`, { headers: authHeader() });
    }

    deleteUserById(id) {
        return http.delete(`${API_URL}/deleteUserById/${id}`, { headers: authHeader() });
    }

    updateUser(id, user) {
        return http.put(`${API_URL}/updateUser/${id}`, user, { headers: authHeader() });
    }

    isUsernameUnique(username, id) {
        return http.get(`${API_URL}/isUsernameUnique?username=${username}&id=${id}`, { headers: authHeader() });
    }

    isEmailUnique(email, id) {
        return http.get(`${API_URL}/isEmailUnique?email=${email}&id=${id}`, { headers: authHeader() });
    }

    getUsersByUserRole(role) {
        return http.post(`${API_URL}/getUsersByUserRole`, role, { headers: authHeader() });
    }
}

export default new UserDataService()