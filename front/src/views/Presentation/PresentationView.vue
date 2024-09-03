<script setup>
import { onMounted, onUnmounted } from "vue";
import { useRouter } from 'vue-router';
import { storeToRefs } from "pinia";

//example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import BeforeLoginNav from "@/examples/navbars/NavbarBeforeLogin.vue";
import DefaultFooter from "@/examples/footers/FooterDefault.vue";
//image
import bg0 from "@/assets/img/bg9.jpg";
//sections
import Information from "../components/AboutInformation.vue";
//dep
import Typed from "typed.js";
import { useUserStore } from "@/stores/user";
import Swal from "sweetalert2";

const userStore = useUserStore();
const { isLogin } = storeToRefs(userStore);
const router = useRouter();
const body = document.getElementsByTagName("body")[0];
//hooks
onMounted(() => {
    body.classList.add("about-us");
    body.classList.add("bg-gray-200");

    if (document.getElementById("typed")) {
        // eslint-disable-next-line no-unused-vars
        var typed = new Typed("#typed", {
            stringsElement: "#typed-strings",
            typeSpeed: 90,
            backSpeed: 90,
            backDelay: 200,
            startDelay: 500,
            loop: true,
        });
    }
});

onUnmounted(() => {
    body.classList.remove("about-us");
    body.classList.remove("bg-gray-200");
});

function makeTrip() {

    if (isLogin.value) {
        router.push('/pages/landing-pages/contact-us');
    } else {
        Swal.fire({
            icon: 'info',
            title: '로그인 후 이용 가능합니다.',
            text: '로그인 하시겠습니까?',
            showCancelButton: true,
            confirmButtonText: '예',
            cancelButtonText: '아니오',
            confirmButtonColor: '#429f50',
            cancelButtonColor: '#d33',

        }).then(result => {
            if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
                router.push('/user/login');
            } else if (result.isDismissed) { // 만약 모달창에서 cancel 버튼을 눌렀다면
                // ...실행
            }
        });
    }
}

</script>

<template>
    <template v-if="isLogin">
        <DefaultNavbar transparent />
    </template>
    <template v-else>
        <BeforeLoginNav transparent />
    </template>
    <header class="bg-gradient-dark">
        <div class="page-header min-vh-75" :style="{ backgroundImage: `url(${bg1})` }">
            <span class="mask bg-gradient-dark opacity-6"></span>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-8 text-center mx-auto my-auto">
                        <h1 class="text-white">KT Trip 으로 여행을 <span class="text-white" id="typed"></span></h1>
                        <div id="typed-strings">
                            <h1>알차게</h1>
                            <h1>재밌게</h1>
                            <h1>행복하게</h1>
                        </div>
                            <p class="lead mb-4 text-white opacity-8" style="margin-top: 60px;">
                                KT 관광통합데이터를 기반으로 여행을 계획하세요! <br> 생성한 여행 계획과 여행 후기를 기반으로, AI가 나만의 여행기를 만화로 그려줘요.
                            </p>

                        <a class="btn bg-white text-dark" @click="makeTrip">여행 계획하기 </a>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <div class="card card-body shadow-xl mx-3 mx-md-4 mt-n6">
        <Information />
    </div>
    <DefaultFooter />
</template>
