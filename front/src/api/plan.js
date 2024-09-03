import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function postPlan(param, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.post(`/plan/create`, param).then(success).catch(fail);
}

async function getPlan(userIdx, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`/plan/${userIdx}`).then(success).catch(fail);
}

export { postPlan, getPlan };
