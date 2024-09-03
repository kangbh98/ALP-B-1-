<template>
    <div class="container position-sticky z-index-sticky top-0">
        <div class="row">
            <div class="col-12">
                <DefaultNavbar class="background" style="height: 70px; background-color: white; border-radius: 10px" />
            </div>
        </div>
    </div>
    <div class="page-header z-index-0">
        <KakaoMap :search-keyword="combinedKeyword" />
    </div>
    <div id="searchBox" class="card custom-search-box">
        <div class="p-2">
            <div class="d-flex align-items-center">
                <i class="fa fa-search"></i>
                <span class="ps-2" style="font-size: 1rem">주소 검색 및 키워드 검색</span>
            </div>
            <div class="p-3 pb-2 d-flex justify-content-evenly">
                <div class="col-lg-4 col-md-6 col-sm-6">
                    <fieldset>
                        <select v-model="selectSido" @change="onSidoChange" class="form-control form-select">
                            <option value="">시도 선택</option>
                            <option v-for="sido in sidoList" :key="sido.sidoCode" :value="sido.sidoCode">{{ sido.sidoName }}</option>
                        </select>
                    </fieldset>
                </div>
                <div class="col-lg-4 col-md-6 col-sm-6">
                    <fieldset>
                        <select v-model="selectGungu" @change="onGunguChange" class="form-control form-select">
                            <option value="">군구 선택</option>
                            <option v-for="gungu in gunguList" :key="gungu.gunguCode" :value="gungu.gunguCode">{{ gungu.gunguName }}</option>
                        </select>
                    </fieldset>
                </div>
                <div class="col-lg-4 col-md-6 col-sm-6">
                    <fieldset>
                        <select v-model="selectDong" class="form-control form-select">
                            <option value="">동 선택</option>
                            <option v-for="dong in dongList" :key="dong.dongCode" :value="dong.dongCode">{{ dong.dongName }}</option>
                        </select>
                    </fieldset>
                </div>
            </div>
            <MaterialInput v-model="customKeyword" class="input-group-dynamic mb-4" icon="search" type="text" placeholder="Search" />
            <div class="col-md-12">
                <MaterialButton @click="onSearchClick" type="submit" variant="gradient" color="secondary" fullWidth> 검색</MaterialButton>
            </div>
        </div>
    </div>

    <AddToPlan v-if="modalOpen1" @close="closeModal1" />
    <AddReview v-if="modalOpen2" @close="closeModal2" />
    <div id="showList" class="card p-0">
        <!-- 관광지 정보 요약 -->
        <div class="bg-white mb-2">
            <div class="p-3 border-bottom d-flex justify-content-between align-items-center">
                <h4 class="m-0">{{ markerStore.placeName }}</h4>
            </div>
            <!-- contents -->
            <div class="px-3">
                <div class="d-flex py-2">
                    <div class="text-secondary w-25">전화번호</div>
                    <div>{{ markerStore.phone }}</div>
                </div>
                <div class="d-flex py-2">
                    <div class="text-secondary w-25">카테고리</div>
                    <div>{{ markerStore.category }}</div>
                </div>
                <div class="d-flex py-2">
                    <div class="text-secondary w-25">주소</div>
                    <div>{{ markerStore.address }}</div>
                </div>
            </div>
            <div class="d-flex py-2 justify-content-center">
                <MaterialButton @click="openModal1" type="submit" variant="gradient" color="secondary"> 내 계획에 추가 </MaterialButton>
            </div>
        </div>
        <div class="py-1 bg-secondary"></div>
        <!-- 거주민 리뷰 -->
        <div class="bg-white mb-2">
            <div class="d-flex justify-content-between align-items-center">
                <h5 class="p-3 m-0">관광객 리뷰</h5>
                <i class="bi bi-plus-circle px-3 cursor-pointer" @click="openModal2">+</i>
            </div>
            <div class="border-top border-bottom d-flex align-items-center p-2">
                <div class="text-secondary ps-2 pe-3">
                    <img class="avatar rounded-circle" width="25px" src="https://th.bing.com/th/id/OIP.3X4AupAJ7GEjKFlYN-4KcwAAAA?w=400&h=400&rs=1&pid=ImgDetMain" />
                </div>
            </div>
            <div class="px-3">
                <div class="border-bottom d-flex py-2 text-danger">
                    <div class="w-25">추천점수</div>
                    <div></div>
                </div>
                <div class="border-bottom d-flex py-2">
                    <div class="text-secondary w-25">교통요건</div>
                    <div></div>
                </div>
                <div class="border-bottom d-flex py-2">
                    <div class="text-secondary w-25">관광환경</div>
                    <div></div>
                </div>
                <div class="border-bottom d-flex py-2">
                    <div class="text-secondary w-25">음식환경</div>
                    <div></div>
                </div>
                <div class="pt-2 text-secondary">종합의견</div>
                <div class="py-2">
                    <h6>Great place to trip I really Love this place</h6>
                </div>
            </div>
        </div>
        <div class="py-1 bg-secondary"></div>
        <!-- 매물정보 -->
    </div>
</template>

<script setup>
import { ref, computed, watch } from "vue";
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import KakaoMap from "@/views/Destination/KakaoMap.vue";
import MaterialInput from "@/components/MaterialInput.vue";
import MaterialButton from "@/components/MaterialButton.vue";
import AddToPlan from "@/views/Destination/AddToPlan.vue";
import AddReview from "@/views/Destination/AddReview.vue";
import { httpStatusCode } from "@/util/http-status";
import { useMarkerStore } from "@/stores/useMarkerStore";
import { storeToRefs } from "pinia";

const modalOpen1 = ref(false);
const modalOpen2 = ref(false);
const searchKeyword = ref("");
const customKeyword = ref("");
const selectSido = ref("");
const selectGungu = ref("");
const selectDong = ref("");
const sidoList = ref([
    { sidoCode: "01", sidoName: "서울" },
    { sidoCode: "02", sidoName: "부산" },
    { sidoCode: "03", sidoName: "대구" },
]);
const gunguList = ref([]);
const dongList = ref([]);
const markerStore = useMarkerStore();

const openModal1 = () => {
    modalOpen1.value = true;
};
const closeModal1 = () => {
    modalOpen1.value = false;
};
const openModal2 = () => {
    modalOpen2.value = true;
};
const closeModal2 = () => {
    modalOpen2.value = false;
};
const combinedKeyword = computed(() => {
    let keyword = "";
    if (selectSido.value) keyword += sidoList.value.find((sido) => sido.sidoCode === selectSido.value)?.sidoName || "";
    if (selectGungu.value) keyword += " " + gunguList.value.find((gungu) => gungu.gunguCode === selectGungu.value)?.gunguName || "";
    if (selectDong.value) keyword += " " + dongList.value.find((dong) => dong.dongCode === selectDong.value)?.dongName || "";
    if (customKeyword.value) keyword += " " + customKeyword.value;
    return keyword.trim();
});

const onSearchClick = () => {
    searchKeyword.value = combinedKeyword.value;
    console.log("Search Clicked - Search Keyword:", searchKeyword.value);
};

/**
 * Get Reviews
 *
 */
import { useUserStore } from "@/stores/user";
import { getReview } from "@/api/review";

const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);

// const userIdx = userInfo.value.userIdx;

const review = ref({
    reviewIdx: "",
    writerIdx: "",
    place: "",
    comment: "",
    total: 0,
    traffic: 0,
    travel: 0,
    food: 0,
    createdAt: "",
    nickname: "",
    profileImg: "",
});

async function getReviewList(placeName) {
    try {
        const response = await getReview(placeName);
        console.log("테스트 중 " + response);
        console.log("테스트 중 " + response.data);
        console.log("테스트 중 " + response.data.value);
        console.log("테스트 중 " + JSON.stringify(response.data.reviewList, null, 2));
        if (response.status === httpStatusCode.OK) {
            review.value = response.data.reviewList;
        } else {
            console.log("계획이 없음!!!!");
        }
    } catch (error) {
        console.error("리뷰 데이터를 가져오는 중 오류 발생:", error);
        if (error.response && error.response.status === httpStatusCode.UNAUTHORIZED) {
            isValidToken.value = false;
            await tokenRegenerate();
        }
    }
}

// markerStore.placeName이 변경될 때마다 getReviewList 실행
watch(
    () => markerStore.placeName,
    async (newPlaceName) => {
        if (newPlaceName) {
            await getReviewList(newPlaceName);
        } else {
            console.warn("markerStore.placeName이 비어 있습니다.");
        }
    }
);

/**
 * end of get Review
 */

// 시도 선택에 따라 군구 데이터 업데이트
const onSidoChange = () => {
    selectGungu.value = "";
    selectDong.value = "";
    dongList.value = [];
    console.log("Sido Changed:", selectSido.value);

    if (selectSido.value === "01") {
        gunguList.value = [
            { gunguCode: "0101", gunguName: "강남구" },
            { gunguCode: "0102", gunguName: "강동구" },
            { gunguCode: "0103", gunguName: "강북구" },
            { gunguCode: "0104", gunguName: "강서구" },
            { gunguCode: "0105", gunguName: "관악구" },
            { gunguCode: "0106", gunguName: "광진구" },
            { gunguCode: "0107", gunguName: "구로구" },
            { gunguCode: "0108", gunguName: "금천구" },
            { gunguCode: "0109", gunguName: "노원구" },
            { gunguCode: "0110", gunguName: "도봉구" },
            { gunguCode: "0111", gunguName: "동대문구" },
            { gunguCode: "0112", gunguName: "동작구" },
            { gunguCode: "0113", gunguName: "마포구" },
            { gunguCode: "0114", gunguName: "서대문구" },
            { gunguCode: "0115", gunguName: "서초구" },
            { gunguCode: "0116", gunguName: "성동구" },
            { gunguCode: "0117", gunguName: "성북구" },
            { gunguCode: "0118", gunguName: "송파구" },
            { gunguCode: "0119", gunguName: "양천구" },
            { gunguCode: "0120", gunguName: "영등포구" },
            { gunguCode: "0121", gunguName: "용산구" },
            { gunguCode: "0122", gunguName: "은평구" },
            { gunguCode: "0123", gunguName: "종로구" },
            { gunguCode: "0124", gunguName: "중구" },
            { gunguCode: "0125", gunguName: "중랑구" },
        ];
    } else if (selectSido.value === "02") {
        gunguList.value = [
            { gunguCode: "0201", gunguName: "해운대구" },
            { gunguCode: "0202", gunguName: "부산진구" },
            { gunguCode: "0203", gunguName: "동래구" },
        ];
    } else if (selectSido.value === "03") {
        gunguList.value = [
            { gunguCode: "0301", gunguName: "수성구" },
            { gunguCode: "0302", gunguName: "달서구" },
            { gunguCode: "0303", gunguName: "중구" },
        ];
    }
};

// 군구 선택에 따라 동 데이터 업데이트
const onGunguChange = () => {
    if (selectGungu.value === "0101") {
        dongList.value = [
            { dongCode: "010101", dongName: "신사동" },
            { dongCode: "010102", dongName: "압구정동" },
            { dongCode: "010102", dongName: "청담동" },
            { dongCode: "010102", dongName: "논현동" },
            { dongCode: "010102", dongName: "삼성동" },
            { dongCode: "010102", dongName: "역삼동" },
            { dongCode: "010102", dongName: "대치동" },
            { dongCode: "010102", dongName: "도곡동" },
            { dongCode: "010102", dongName: "개포동" },
            { dongCode: "010102", dongName: "일원동" },
            { dongCode: "010102", dongName: "수서동" },
            { dongCode: "010102", dongName: "세곡동" },
        ];
    } else if (selectGungu.value === "0201") {
        dongList.value = [
            { dongCode: "020101", dongName: "중동" },
            { dongCode: "020102", dongName: "우동" },
        ];
    } else if (selectGungu.value === "0301") {
        dongList.value = [
            { dongCode: "030101", dongName: "범어동" },
            { dongCode: "030102", dongName: "수성동" },
        ];
    }
    console.log("Gungu Changed:", selectGungu.value);
};
</script>

<style scoped>
.custom-search-box {
    position: absolute;
    top: 80px;
    left: 15px;
    width: 400px;
    height: 250px;
    background-color: rgba(255, 255, 255);
    padding: 20px;
    box-shadow: 0 12px 16px rgba(0, 0, 0, 0.1);
}

#showList {
    position: absolute;
    top: 80px;
    bottom: 20px;
    right: 15px;
    width: 400px;
    padding: 10px;
    overflow-y: auto;
    box-shadow: 0 16px 20px rgba(0, 0, 0, 0.1);
}

.bi-plus-circle {
    font-size: 1.5rem;
}

.bi-plus-circle:hover {
    color: dodgerblue;
}
</style>
