import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listArticle(param, success, fail) {
    local.get(`/board`, { params: param }).then(success).catch(fail);
}

function detailArticle(boardIdx, success, fail) {
    local.get(`/board/${boardIdx}`).then(success).catch(fail);
}

function registArticle(board, success, fail) {
    console.log("boardjs article", board);
    local.post(`/board`, JSON.stringify(board)).then(success).catch(fail);
}

function getModifyArticle(boardIdx, success, fail) {
    local.get(`/board/modify/${boardIdx}`).then(success).catch(fail);
}

function modifyArticle(board, success, fail) {
    local.patch(`/board/modify/${boardIdx}`, JSON.stringify(board)).then(success).catch(fail);
}

function deleteArticle(boardIdx, success, fail) {
    local.delete(`/board/${boardIdx}`).then(success).catch(fail);
}

export { listArticle, detailArticle, registArticle, getModifyArticle, modifyArticle, deleteArticle };
