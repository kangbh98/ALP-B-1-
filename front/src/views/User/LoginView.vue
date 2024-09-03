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

const moveFindPassword = () => {
    router.push({ name: "find-password" });
};

// example components
import DefaultNavbar from "@/examples/navbars/NavbarBeforeLogin.vue";
import Header from "@/examples/Header.vue";

//Vue Material Kit 2 components
import LoginInput from "@/components/LoginInput.vue";
import MaterialSwitch from "@/components/MaterialSwitch.vue";
import MaterialButton from "@/components/MaterialButton.vue";

import bgImage from "@/assets/img/bg9.jpg";

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
                                    <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">Login</h4>
                                </div>
                            </div>
                            <div class="card-body">
                                <form role="form" class="text-start" @submit.prevent="login">
                                    <LoginInput
                                        v-model="loginUser.email"
                                        id="email"
                                        class="input-group-outline my-3"
                                        :label="{
                                            text: 'Email',
                                            class: 'form-label',
                                        }"
                                        type="email"
                                    />
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

                                    <div class="text-center">
                                        <MaterialButton class="my-4 mb-2" variant="gradient" color="success" fullWidth>Login</MaterialButton>
                                    </div>
                                    <p class="mt-4 text-sm text-center">
                                        아이디가 없으신가요?
                                        <button class="text-success font-weight-bold" @click="moveSignUp" style="border: none; background-color: transparent;">회원가입</button>
                                    </p>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </Header>
</template>
<style scoped>
</style>

