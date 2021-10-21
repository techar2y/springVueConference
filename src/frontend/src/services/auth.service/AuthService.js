//import axios from 'axios';
import http from '../../../http-common';

const API_URL = '/auth';

class AuthService {
    login(user) {
        return http.post(`${API_URL}/signin`, {
            username: user.username,
            password: user.password
        }).then(response => {
            if (response.data.accessToken) {
                localStorage.setItem('user', JSON.stringify(response.data));
            }

            return response.data;
        });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return http.post(`${API_URL}/signup`, {
            fullName: user.fullName,
            username: user.username,
            email: user.email,
            password: user.password
        });
    }

    /*login(user) {
        return axios
            .post(API_URL + '/signin', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + '/signup', {
            fullName: user.fullName,
            username: user.username,
            email: user.email,
            password: user.password
        });
    }*/
}

export default new AuthService();