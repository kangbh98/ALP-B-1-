<template>
    <div class="container position-sticky z-index-sticky top-0">
        <div class="row">
            <div class="col-12">
                <DefaultNavbar :sticky="true" :action="{
                    route: 'https://www.creative-tim.com/product/vue-material-kit-pro',
                    color: 'bg-gradient-success',
                    label: 'Buy Now',
                }" />
            </div>
        </div>
    </div>
    <section>
        <div class="page-header min-vh-100" :style="backgroundStyle" loading="lazy">
            <div class="row">
                <div
                    class="col-4 offset-1 d-lg-flex d-none h-100 my-auto position-absolute top-3 start-0 text-center justify-content-center flex-column">
                    <div class="rounded-calendar">
                        <FullCalendar :key="listCalendarKey" :options="listOptions" />

                        <div v-if="diaryData" class="diary-entry">
                            <h4 class="text-dark mt-5">여행 만화</h4>
                            <img :src="diaryData.imageUrl" alt="Diary Image" class="diary-image" />
                            <h4 class="text-dark mt-5">여행 일기</h4>
                            <div class="comment-box">
                                <p class="diary-comment">{{ diaryData.comment }}</p>
                            </div>
                        </div>

                        <MaterialButton variant="gradient" color="secondary" class="mx-3 mt-5" @click="makeImage">
                            AI로 나만의 여행기 만화로 생성하기
                        </MaterialButton>
                    </div>
                </div>
                <div
                    class="col-6 offset-6 d-lg-flex d-none h-100 my-auto position-absolute top-3 start-0 text-center justify-content-center flex-column">
                    <div
                        style="  border-radius: 15px; padding: 10px;background-color: #ffffff; box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);">
                        <FullCalendar :options="calendarOptions" />
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>



<script>
import { ref, watch, onMounted } from 'vue';
import axios from 'axios';
import FullCalendar from '@fullcalendar/vue3';
import dayGridPlugin from '@fullcalendar/daygrid';
import listPlugin from '@fullcalendar/list';
import interactionPlugin from '@fullcalendar/interaction';
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";
import bgImage from "@/assets/img/bg9.jpg";
import MaterialButton from "@/components/MaterialButton.vue";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";
import Swal from "sweetalert2";

export default {
    components: {
        FullCalendar,
        DefaultNavbar,
        DefaultFooter,
        MaterialButton
    },
    setup() {
        const userStore = useUserStore();
        const { userInfo } = storeToRefs(userStore);

        const backgroundStyle = ref({
            backgroundImage: `url(${bgImage})`,
        });
        const comment = ref("");
        const events = ref([]);
        const listCalendarKey = ref(0); // 캘린더 리렌더링을 위한 키
        const selectedDate = ref(null); // 클릭된 날짜를 저장할 ref
        const diaryData = ref(null); // 다이어리 데이터를 저장할 ref
        const listCalendarHeight = ref(800); // 리스트 캘린더의 초기 높이

        const listOptions = ref({
            plugins: [listPlugin],
            initialView: 'listDay',
            events: events.value,
            initialDate: null
        });

        const calendarOptions = ref({
            plugins: [dayGridPlugin, interactionPlugin, listPlugin],
            initialView: 'dayGridMonth',
            weekends: true,
            events: events.value,
            dateClick: handleDateClick
        });

        function handleDateClick(arg) {
            selectedDate.value = arg.dateStr;
            // listOptions의 initialDate를 클릭된 날짜로 변경하고, key를 갱신하여 강제로 리렌더링
            listOptions.value = { ...listOptions.value, initialDate: arg.dateStr };
            listCalendarKey.value += 1; // key 변경을 통해 강제로 리렌더링

            // 새로운 데이터를 가져오는 함수 호출
            fetchDiaryData(arg.dateStr);
        }

        async function fetchEvents() {
            try {
                const response = await axios.get(`http://localhost:8080/plan/${userInfo.value.userIdx}`);
                const plans = response.data.planinfo;
                events.value = plans.map(plan => ({
                    title: `${plan.placeName} (${plan.category})`,
                    start: plan.date && plan.time ? `${plan.date}T${plan.time}` : plan.date,
                    allDay: !plan.time
                }));
                calendarOptions.value = { ...calendarOptions.value, events: events.value };
                listOptions.value = { ...listOptions.value, events: events.value };

            } catch (error) {
                console.error('Error fetching events:', error);
            }
        }

        async function fetchDiaryData(date) {
            try {
                console.log('Fetching diary data for date:', date); // 확인용 로그
                console.log('User ID:', userInfo.value.userIdx); // 확인용 로그
                const response = await axios.get(`http://localhost:8080/plan/get/diary`, {
                    params: {
                        userIdx: userInfo.value.userIdx,
                        date: date
                    }
                });
                diaryData.value = response.data;
            } catch (error) {
                console.error('Error fetching diary data:', error);
                diaryData.value = null;
            }
        }

        function makeImage() {
            Swal.fire({
                icon: "info",
                title: "이미지 생성",
                text: "DALL-E를 활용해 다이어리를 생성 하시겠습니까?",
                showCancelButton: true,
                confirmButtonText: "예",
                cancelButtonText: "아니오",
                confirmButtonColor: "#429f50",
                cancelButtonColor: "#d33",
            }).then((result) => {
                if (result.isConfirmed) {
                    // 만약 모달창에서 confirm 버튼을 눌렀다면
                    // ...실행
                    handleCommentInput()
                } else if (result.isDismissed) {
                    // 만약 모달창에서 cancel 버튼을 눌렀다면
                    // ...실행
                }
            });
        }
        async function handleCommentInput() {
            const { value: inputComment } = await Swal.fire({
                title: '여행기록 입력하세요',
                input: 'textarea',
                inputLabel: '당신의 기록',
                inputPlaceholder: '여기에 기록 입력하세요...',
                showCancelButton: true,
                confirmButtonText: '제출',
                cancelButtonText: '취소',
                confirmButtonColor: '#429f50',
                cancelButtonColor: '#d33',
                inputValidator: (value) => {
                    if (!value) {
                        return '내용을 입력해야 합니다!';
                    }
                }
            });

            if (inputComment) {
                comment.value = inputComment;
                console.log("입력된 코멘트:", comment.value);
                // 여기서 추가적인 처리 로직을 작성할 수 있습니다.
                await postComment(userInfo.value.userIdx, selectedDate.value, comment.value);
            }
        }
        async function postComment(userIdx, date, comment) {
            Swal.fire({
                title: '로딩 중...',
                text: `${userInfo.value.nickName}님의 다이어리를 만드는 중이에요.`,
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });
            try {
                const response = await axios.post('http://localhost:8080/plan/create/diary', null, {
                    params: {
                        userIdx: userIdx,
                        date: date,
                        comment: comment
                    }
                });
                Swal.close(); // 로딩 스피너를 닫습니다.
                if (response.status === 200) {
                    Swal.fire({
                        icon: 'success',
                        title: '다이어리 제작 완료',
                        text: `KT Trip이 ${userInfo.value.nickName}님의 다이어리를 완성했습니다.`,
                    });
                } else {
                    throw new Error('코멘트 제출 실패');
                }
            } catch (error) {
                console.error('코멘트 제출 중 오류 발생:', error);
                Swal.fire({
                    icon: 'error',
                    title: '오류 발생',
                    text: '코멘트 제출 중 오류가 발생했습니다. 다시 시도해 주세요.',
                });
            }
        }

        onMounted(fetchEvents);

        return {
            makeImage,
            backgroundStyle,
            calendarOptions,
            listOptions,
            listCalendarKey,
            selectedDate,
            listCalendarHeight,
            diaryData
        };
    }
};
</script>


<style scoped>
.rounded-calendar {
    border-radius: 15px;
    padding: 40px;
    background-color: #ffffff;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    max-height: 1000px;
    /* 최대 높이 설정 */
    overflow-y: auto;
    /* 세로 스크롤바 추가 */
}

.diary-entry {
    display: flex;
    flex-direction: column;
    align-items: start;
    /* Aligns content to the left */
    width: 100%;
}

.comment-box {
    background-color: #f8f9fa;
    /* Light grey background */
    padding: 10px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    width: 100%;
    /* Ensures the box takes full width of its container */
    border: 1px solid #dee2e6;
    /* Light grey border for subtle definition */
    margin-top: 10px;
}

.diary-image {
    width: 100%;
    height: auto;
    object-fit: contain;
    border: 5px solid #6c757d;
    filter: grayscale(30%);
    border-radius: 15px;
    margin-bottom: 10px;
    /* Adds space between the image and the comment box */
}

.diary-comment {
    font-size: 1.2em;
    color: #495057;
    /* Dark grey color for text for better readability */
}
</style>
