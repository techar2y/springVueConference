import axios from "axios";

export default axios.create({
    baseURL: "https://springvueconference.herokuapp.com/api",
    headers: {
        "Content-type": "application/json"
    }
});
