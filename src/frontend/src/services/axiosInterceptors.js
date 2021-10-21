import http from '../../http-common'
import TokenService from "./token.service/TokenService";
import EventService from "../services/event-service";

const setup = (store) => {
    http.interceptors.request.use(
        (config) => {
            const token = TokenService.getLocalAccessToken();
            if (token) {
                config.headers["Authorization"] = 'Bearer ' + token;
            }
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    http.interceptors.response.use(
        (res) => {
            return res;
        },
        async (error) => {
            const originalConfig = error.config;

            if (originalConfig.url !== "/auth/signin" && error.response) {
                // Access Token was expired
                if (error.response.status === 401 && !originalConfig._retry) {
                    originalConfig._retry = true;


                    try {
                        const rs = await http.post("/auth/refreshtoken", {
                            refreshToken: TokenService.getLocalRefreshToken(),
                        });

                        const { accessToken } = rs.data;

                        store.dispatch('auth/refreshToken', accessToken);
                        TokenService.updateLocalAccessToken(accessToken);

                        return http(originalConfig);
                    } catch (_error) {
                        return Promise.reject(_error);
                    }
                } else if(error.response.status === 511) {
                    EventService.dispatch("logout");
                } else if(error.response.status === 403) {
                    EventService.dispatch("notFound");
                }
            }

            return Promise.reject(error);
        }
    );
};

export default setup;