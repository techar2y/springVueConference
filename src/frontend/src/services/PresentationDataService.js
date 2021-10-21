import http from "../../http-common";
import authHeader from "./auth.service/auth-header";

const API_URL = "/presentations";

class PresentationDataService {

    getAllPresentations(params) {
        return http.get(`${API_URL}/getAllPresentations`, {params: params});
    }

    getPresentationById(id) {
        return http.get(`${API_URL}/getPresentationById/${id}`, { headers: authHeader() });
    }

    findByTitle(title) {
        return http.get(`${API_URL}/?title=${title}`, { headers: authHeader() });
    }

    deleteAllPresentations() {
        return http.delete(`${API_URL}/deleteAllPresentations`, { headers: authHeader() });
    }

    deletePresentationById(id) {
        return http.delete(`${API_URL}/deletePresentationById/${id}`, { headers: authHeader() });
    }

    updatePresentation(id, schedule) {
        return http.put(`${API_URL}/updatePresentation/${id}`, schedule, { headers: authHeader() });
    }

    createPresentation(data) {
        return http.post(`${API_URL}/createPresentation`, data, { headers: authHeader() });
    }

    getPresentationsByRoom(room, params) {
        return http.post(`${API_URL}/getPresentationsByRoom`, room, {params: params, headers: authHeader()} );
    }
}

export default new PresentationDataService()