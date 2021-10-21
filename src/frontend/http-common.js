import axios from "axios";

export default axios.create({
    baseURL: "https://springVueConference.herokuapp.com/api",
    headers: {
        "Content-type": "application/json"
    }
});
