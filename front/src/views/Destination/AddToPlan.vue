<script setup>
import { ref, onMounted } from "vue";
import { useMarkerStore } from "@/stores/useMarkerStore";
import MaterialInput from "@/components/MaterialInput.vue";
import MaterialButton from "@/components/MaterialButton.vue";
import setMaterialInput from "@/assets/js/material-input";
import { useUserStore } from "@/stores/user";
import { storeToRefs } from "pinia";
import Swal from "sweetalert2";
import { httpStatusCode } from "@/util/http-status";

const store = useMarkerStore();
const userStore = useUserStore();
const { userInfo } = storeToRefs(userStore);
const user = userInfo.value.userIdx;

const form = ref({
    userIdx: user,
    placeName: store.placeName,
    address: store.address,
    phone: store.phone,
    category: store.category,
    date: "", // 날짜 입력을 위한 새 속성
    time: "", // 시간 입력을 위한 새 속성
});

const emit = defineEmits(["close"]);

function emitClose() {
    emit("close");
}

import { postPlan, getPlan } from "@/api/plan";

/**
 * 이건 plan list를 뽑아오는 코드이다.
 * 이를 위해서는 http status를 갖고 오는 import와 api/plan.js를 import를 필수적으로 해주어야 한다.
 */
const planList = ref(null);

async function moveToPlan() {
    await getPlan(
        user,
        (response) => {
            console.log(response);
            if (response.status === httpStatusCode.OK) {
                planList.value = response.data.planinfo;
            } else {
                console.log("계획이 없음!!!!");
            }
        },
        async (error) => {
            console.error("[토큰 만료되어 사용 불가능.] : ", error.response.status, error.response.statusText);
            isValidToken.value = false;

            await tokenRegenerate();
        }
    );
}

async function handleSubmit() {
    console.log(form.value);

    Swal.fire({
        icon: "success",
        title: "계획을 추가하셨습니다",
        text: "계획을 확인하시겠습니까?",
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#429f50",
        cancelButtonColor: "#d33",
    }).then((result) => {
        if (result.isConfirmed) {
            // 만약 모달창에서 confirm 버튼을 눌렀다면
            // ...실행
            moveToPlan();
        } else if (result.isDismissed) {
            // 만약 모달창에서 cancel 버튼을 눌렀다면
            // ...실행
        }
    });

    try {
        emitClose();
        await postPlan(
            form.value,
            (response) => {
                console.log("Plan added successfully:", response);
            },
            (error) => {
                console.error("Failed to add plan:", error);
            }
        );
    } catch (error) {
        console.error("Error in handleSubmit:", error);
    }
}

onMounted(() => {
    setMaterialInput();
});
</script>

<template>
    <section>
        <div class="container">
            <div class="row">
                <div class="card d-flex blur justify-content-center shadow-lg my-sm-0 my-sm-6 mt-8 mb-5">
                    <div class="card-header p-0 position-relative mt-n5 mx-4 z-index-2 bg-transparent">
                        <div class="bg-gradient-secondary shadow-secondary border-radius-lg p-3">
                            <h3 class="text-white text-success mb-0">계획에 추가하기</h3>
                        </div>
                    </div>
                    <div class="card-body">
                        <form id="contact-form" method="post" autocomplete="off" @submit.prevent="handleSubmit">
                            <div class="d-flex flex-wrap">
                                <MaterialInput class="input-group-static mb-4 flex-fill" type="text" label="여행지명"
                                    v-model="form.placeName" />
                                <MaterialInput class="input-group-static mb-4 flex-fill" type="text" label="주소"
                                    v-model="form.address" />
                                <MaterialInput class="input-group-static mb-4 flex-fill" type="text" label="전화번호"
                                    v-model="form.phone" />
                                <MaterialInput class="input-group-static mb-4 flex-fill" type="text" label="항목"
                                    v-model="form.category" />
                                <MaterialInput class="input-group-static mb-4 flex-fill" type="date" label="날짜"
                                    v-model="form.date" />
                                <MaterialInput class="input-group-static mb-4 flex-fill" type="time" label="시간"
                                    v-model="form.time" />
                            </div>
                            <div class="text-center mt-3">
                                <MaterialButton variant="gradient" color="secondary" class="mx-2"> 여행지를 계획에 추가하기 </MaterialButton>
                                <MaterialButton variant="gradient" color="secondary" class="mx-2" @click="emitClose">
                                    닫기 </MaterialButton>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<style scoped>
.container {
    max-width: 800px;
    /* 폼의 최대 너비를 800px로 조정 */
    margin: auto;
    /* 중앙 정렬 */
}

.input-group-static {
    font-size: 18px;
    /* 입력 필드의 글씨 크기를 늘림 */
}

.card-header h3 {
    font-size: 24px;
    /* 카드 헤더의 글씨 크기를 늘림 */
}

.flex-fill {
    flex: 1 1 0px;
    margin-right: 10px;
    /* 필드 사이의 간격 유지 */
}

.text-center {
    display: flex;
    justify-content: center;
    /* 버튼을 중앙에 배치 */
}

.mx-2 {
    margin-left: 10px;
    margin-right: 10px;
    /* 버튼 사이의 간격 */
}

.MaterialButton {
    font-size: 16px;
    /* 버튼의 글씨 크기를 늘림 */
}
</style>
