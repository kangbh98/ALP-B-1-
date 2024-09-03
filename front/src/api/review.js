import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function postReview(param, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.post(`/review/write`, param).then(success).catch(fail);
}

async function getReview(placeName, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    try {
        const response = await local.get(`/review/${placeName}`);
        return response;
    } catch (error) {
        console.error("Error fetching reviews:", error);
        throw error; // 에러를 던져서 외부에서 캐치할 수 있도록 함
    }
}

export { postReview, getReview };
