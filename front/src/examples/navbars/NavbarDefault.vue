<script setup>
import { RouterLink, useRouter } from "vue-router";
import { ref, watch } from "vue";
import { useWindowsWidth } from "../../assets/js/useWindowsWidth";
import { storeToRefs } from "pinia";
import { useUserStore } from "@/stores/user";

const userStore = useUserStore();
const router = useRouter();

// images
import ArrDark from "@/assets/img/down-arrow-dark.svg";
import downArrow from "@/assets/img/down-arrow.svg";
import DownArrWhite from "@/assets/img/down-arrow-white.svg";
import bootstrapMin from "bootstrap/dist/js/bootstrap.min";

const { userLogout } = userStore;

const logout = () => {
    userLogout();
    router.replace({ name: "presentation" });
};

const props = defineProps({
    action: {
        type: Object,
        route: Boolean,
        color: String,
        label: String,
        default: () => ({
            // route: isAuthenticated.value ? logout : moveLogin,
            // label: "LOGIN",
            color: "btn-white",
        }),
    },
    transparent: {
        type: Boolean,
        default: false,
    },
    light: {
        type: Boolean,
        default: false,
    },
    dark: {
        type: Boolean,
        default: false,
    },
    sticky: {
        type: Boolean,
        default: false,
    },
    darkText: {
        type: Boolean,
        default: false,
    },
});

// set arrow  color
function getArrowColor() {
    if (props.transparent && textDark.value) {
        return ArrDark;
    } else if (props.transparent) {
        return DownArrWhite;
    } else {
        return ArrDark;
    }
}

// set text color
const getTextColor = () => {
    let color;
    if (props.transparent && textDark.value) {
        color = "text-dark";
    } else if (props.transparent) {
        color = "text-white";
    } else {
        color = "text-dark";
    }

    return color;
};

// set nav color on mobile && desktop

let textDark = ref(props.darkText);
const { type } = useWindowsWidth();

if (type.value === "mobile") {
    textDark.value = true;
} else if (type.value === "desktop" && textDark.value == false) {
    textDark.value = false;
}

watch(
    () => type.value,
    (newValue) => {
        if (newValue === "mobile") {
            textDark.value = true;
        } else {
            textDark.value = false;
        }
    }
);
</script>

<template>
    <nav
        class="navbar navbar-expand-lg top-0"
        :class="{
            'z-index-3 w-100 shadow-none navbar-transparent position-absolute my-3': props.transparent,
            'my-3 blur border-radius-lg z-index-3 py-2 shadow py-2 start-0 end-0 mx-4 position-absolute mt-4': props.sticky,
            'navbar-light bg-white py-3': props.light,
            ' navbar-dark bg-gradient-dark z-index-3 py-3': props.dark,
        }"
    >
        <div :class="props.transparent || props.light || props.dark ? 'container' : 'container-fluid px-0'">
            <RouterLink
                class="navbar-brand d-none d-md-block"
                :class="[(props.transparent && textDark.value) || !props.transparent ? 'text-dark font-weight-bolder ms-sm-3' : 'text-white font-weight-bolder ms-sm-3']"
                :to="{ name: 'presentation' }"
                rel="tooltip"
                title="Designed and Coded by Creative Tim"
                data-placement="bottom"
            >
                KT Trip
            </RouterLink>
            <RouterLink
                class="navbar-brand d-block d-md-none"
                :class="props.transparent || props.dark ? 'text-white' : 'font-weight-bolder ms-sm-3'"
                to="/"
                rel="tooltip"
                title="Designed and Coded by Creative Tim"
                data-placement="bottom"
            >
                KT Trip
            </RouterLink>
            <button class="btn btn-sm" :class="props.action.color + ' mb-0 ms-auto d-lg-none d-block'" @click="logout">LOGOUT</button>
            <button
                class="navbar-toggler shadow-none ms-2"
                type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navigation"
                aria-controls="navigation"
                aria-expanded="false"
                aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon mt-2">
                    <span class="navbar-toggler-bar bar1"></span>
                    <span class="navbar-toggler-bar bar2"></span>
                    <span class="navbar-toggler-bar bar3"></span>
                </span>
            </button>
            <div class="collapse navbar-collapse w-100 pt-3 pb-2 py-lg-0" id="navigation">
                <ul class="navbar-nav navbar-nav-hover ms-auto">
                    <li class="nav-item dropdown dropdown-hover mx-2">
                        <a role="button" class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()" id="dropdownMenuPages" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="material-icons opacity-6 me-2 text-md" :class="getTextColor()">dashboard</i>
                            여행지 검색
                            <img :src="getArrowColor()" alt="down-arrow" class="arrow ms-2 d-lg-block d-none" />
                            <img :src="getArrowColor()" alt="down-arrow" class="arrow ms-1 d-lg-none d-block ms-auto" />
                        </a>
                        <div
                            class="dropdown-menu dropdown-menu-animation ms-n3 dropdown-md p-3 border-radius-xl mt-0 mt-lg-3"
                            aria-labelledby="dropdownMenuPages"
                            style="overflow-x: hidden; overflow-y: hidden"
                        >
                            <div class="row d-none d-lg-block">
                                <div class="col-12 px-4 py-2">
                                    <div class="row">
                                        <div class="position-relative">
                                            <div class="dropdown-header text-dark font-weight-bolder d-flex align-items-center px-1">여행지</div>
                                            <RouterLink :to="{ name: 'contactus' }" class="dropdown-item border-radius-md">
                                                <span>검색 및 추가</span>
                                            </RouterLink>
                                        </div>
                                    </div>
                                </div>
                            </div>

    
                        </div>
                    </li>
                    <li class="nav-item dropdown dropdown-hover mx-2">
                        <a role="button" class="nav-link ps-2 d-flex cursor-pointer align-items-center" :class="getTextColor()" id="dropdownMenuBlocks" data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="material-icons opacity-6 me-2 text-md" :class="getTextColor()">view_day</i>
                            여행기 보기
                            <img :src="getArrowColor()" alt="down-arrow" class="arrow ms-2 d-lg-block d-none" />
                            <img :src="getArrowColor()" alt="down-arrow" class="arrow ms-1 d-lg-none d-block ms-auto" />
                        </a>
                        <div
                            class="dropdown-menu dropdown-menu-end dropdown-menu-animation dropdown-md dropdown-md-responsive p-3 border-radius-lg mt-0 mt-lg-3"
                            aria-labelledby="dropdownMenuBlocks"
                            style="overflow-x: hidden; overflow-y: hidden"
                        >
                            <div class="d-none d-lg-block">
                                <ul class="list-group">
                                    <li class="nav-item dropdown dropdown-hover dropdown-subitem list-group-item border-0 p-0">
                                        <a class="dropdown-item py-2 ps-3 border-radius-md" href="javascript:;">
                                            <div class="d-flex">
                                                <RouterLink :to="{ name: 'plan' }" class="w-100 d-flex align-items-center justify-content-between">
                                                    <div>
                                                        <h6 class="dropdown-header text-dark font-weight-bolder d-flex justify-content-cente align-items-center p-0">계획 및 여행기</h6>
                                                        <span class="text-sm">계획 및 여행기 조회</span>
                                                    </div>
                                                    <img :src="downArrow" alt="down-arrow" class="arrow" />
                                                </RouterLink>
                                            </div>
                                        </a>
                                        <div class="dropdown-menu mt-0 py-3 px-2 mt-3">
                                            <RouterLink class="dropdown-item ps-3 border-radius-md mb-1" :to="{ name: 'presentation' }"> Page Headers </RouterLink>
                                            <RouterLink class="dropdown-item ps-3 border-radius-md mb-1" :to="{ name: 'presentation' }"> Features </RouterLink>
                                        </div>
                                    </li>
                                    <li class="nav-item dropdown dropdown-hover dropdown-subitem list-group-item border-0 p-0">
                                        <a class="dropdown-item py-2 ps-3 border-radius-md" href="javascript:;">
                                            <div class="d-flex">
                                                <div class="w-100 d-flex align-items-center justify-content-between">
                                                    <div>
                                                        <h6 class="dropdown-header text-dark font-weight-bolder d-flex justify-content-cente align-items-center p-0">AI 만화</h6>
                                                        <span class="text-sm">여행기를 AI 만화로 생성 </span>
                                                    </div>
                                                    <img :src="downArrow" alt="down-arrow" class="arrow" />
                                                </div>
                                            </div>
                                        </a>
                                        <div class="dropdown-menu mt-0 py-3 px-2 mt-3">
                                            <RouterLink class="dropdown-item ps-3 border-radius-md mb-1" :to="{ name: 'presentation' }"> Alerts </RouterLink>
                                            <RouterLink class="dropdown-item ps-3 border-radius-md mb-1" :to="{ name: 'presentation' }"> Modals </RouterLink>
                                            <RouterLink class="dropdown-item ps-3 border-radius-md mb-1" :to="{ name: 'presentation' }"> Tooltips & Popovers </RouterLink>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li class="nav-item dropdown dropdown-hover mx-2">
                        <div
                            class="dropdown-menu dropdown-menu-end dropdown-menu-animation dropdown-md mt-0 mt-lg-3 p-3 border-radius-lg"
                            aria-labelledby="dropdownMenuDocs"
                            style="overflow-x: hidden"
                        >
                        </div>
                    </li>
                </ul>
                <ul class="navbar-nav d-lg-block d-none">
                    <li class="nav-item">
                        <a class="btn btn-sm mb-0" :class="action.color" @click="logout">LOGOUT</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<style scoped></style>
