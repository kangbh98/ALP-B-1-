<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";

const router = useRouter();

const userStore = useUserStore();

const { isLogin, isLoginError } = storeToRefs(userStore);
const { userLogin, getUserInfo } = userStore;

const loginUser = ref({
    email: "",
    password: "",
});

const login = async () => {
    await userLogin(loginUser.value);
    let token = sessionStorage.getItem("accessToken");
    console.log(token);
    console.log("isLogin: " + isLogin.value);
    if (isLogin.value) {
        console.log("성공!!");
        getUserInfo(token);
        router.replace({ name: "presentation" });
    }
};

const moveSignUp = () => {
    router.push({ name: "signup" });
    console.log("Success Move sign-up");
};

const moveUserInfo = () => {
    router.push({ name: "user-infoo" });
};

const moveFindPassword = () => {
    router.push({ name: "find-password" });
};

// example components
import DefaultNavbar from "@/examples/navbars/NavbarDefault.vue";
import Header from "@/examples/Header.vue";

//Vue Material Kit 2 components
import LoginInput from "@/components/LoginInput.vue";
import MaterialInput from "@/components/MaterialInput.vue";
import MaterialSwitch from "@/components/MaterialSwitch.vue";
import MaterialButton from "@/components/MaterialButton.vue";
import MaterialAvatar from "@/components/MaterialAvatar.vue";

import bgImage from "@/assets/img/bgg6.jpg";
import proImg from "@/assets/img/favicon.png";

const backgroundStyle = {
    backgroundImage: `url(${bgImage})`,
};

// material-input
import setMaterialInput from "@/assets/js/material-input";

onMounted(() => {
    setMaterialInput();
});
</script>

<template>
    <DefaultNavbar transparent />
    <Header>
        <div class="page-header align-items-start min-vh-100" :style="backgroundStyle" loading="lazy">
            <span class="mask bg-gradient-dark opacity-6"></span>
            <div class="container my-auto">
                <div class="row">
                    <div class="col-lg-4 col-md-8 col-12 mx-auto">
                        <div class="card z-index-0 fadeIn3 fadeInBottom">
                            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                <div class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1">
                                    <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">Change User Info</h4>
                                </div>
                            </div>
                            <div class="d-flex justify-content-center align-items-center mt-3">
                                <MaterialAvatar :image="proImg" alt="Image placeholder" size="xxl" />
                            </div>
                            <div class="card-body">
                                <form role="form" class="text-start" @submit.prevent="moveUserInfo">
                                    <MaterialInput class="mb-2 mt-3" type="text" placeholder="kodd1102@naver.com" isDisabled />
                                    <LoginInput
                                        v-model="loginUser.password"
                                        id="password"
                                        class="input-group-outline mb-3"
                                        :label="{
                                            text: 'Password',
                                            class: 'form-label',
                                        }"
                                        type="password"
                                    />
                                    <!-- <MaterialSwitch class="d-flex align-items-center mb-3" id="rememberMe" labelClass="mb-0 ms-3" checked>ID 정보 저장</MaterialSwitch> -->
                                    <LoginInput
                                        id="nickname"
                                        class="input-group-outline mb-3"
                                        :label="{
                                            text: '데옹',
                                            class: 'form-label',
                                        }"
                                        type="text"
                                    />
                                    <div class="text-center">
                                        <MaterialButton class="my-4 mb-2" variant="gradient" color="success" fullWidth>Change</MaterialButton>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <footer class="footer position-absolute bottom-2 py-2 w-100">
                <div class="container">
                    <div class="row align-items-center justify-content-lg-between">
                        <div class="col-12 col-md-6 my-auto">
                            <div class="copyright text-center text-sm text-white text-lg-start">
                                Welcome!! We are
                                <a href="https://github.com/NinjasTrip" class="font-weight-bold text-white" target="_blank">
                                    <i class="fa fa-heart" aria-hidden="true"></i>
                                    [ Team. Semicolon Ninjas ]</a
                                >
                                of the 11th SSAFY.
                            </div>
                        </div>
                        <div class="col-12 col-md-6">
                            <ul class="nav nav-footer justify-content-center justify-content-lg-end">
                                <li class="nav-item">
                                    <a href="https://www.creative-tim.com/presentation" class="nav-link text-white" target="_blank">About Us</a>
                                </li>
                                <li class="nav-item">
                                    <a href="https://www.instagram.com/bb_hhnn" class="nav-link text-white" target="_blank">ByungHeon Kang</a>
                                </li>
                                <li class="nav-item">
                                    <a href="https://github.com/seongddiyong" class="nav-link pe-0 text-white" target="_blank">SeongHoon Park</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    </Header>
</template>
