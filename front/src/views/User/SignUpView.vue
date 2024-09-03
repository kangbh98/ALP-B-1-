<script setup>
import { RouterLink } from "vue-router";
import { onMounted, ref, watch, computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

// example components
import DefaultNavbar from "@/examples/navbars/NavbarBeforeLogin.vue";
import Header from "@/examples/Header.vue";

//Vue Material Kit 2 components
import MaterialInput from "@/components/LoginInput.vue";
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

const movePresentation = () => {
    router.push({ name: "presentation" });
    console.log("Success Move Login");
};
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
                                    <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">KT Trip</h4>
                                </div>
                            </div>
                            <div class="card-body">
                                <form role="form" class="text-start">
                                    <MaterialInput
                                        id="email"
                                        class="input-group-outline my-3"
                                        :label="{
                                            text: 'Email',
                                            class: 'form-label',
                                        }"
                                        type="email"
                                    />
                                    <MaterialInput
                                        id="password"
                                        class="input-group-outline mb-3"
                                        :label="{
                                            text: 'Password',
                                            class: 'form-label',
                                        }"
                                        type="password"
                                    />
                                    <MaterialInput
                                        id="nickname"
                                        class="input-group-outline mb-3"
                                        :label="{
                                            text: '이름',
                                            class: 'form-label',
                                        }"
                                        type="text"
                                        pattern="^[가-힣a-zA-Z0-9]+$"
                                    />
                                    <div class="text-center">
                                        <MaterialButton class="my-4 mb-2" variant="gradient" color="success" fullWidth @click="movePresentation">회원 가입</MaterialButton>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </Header>
</template>

<style scoped></style>
