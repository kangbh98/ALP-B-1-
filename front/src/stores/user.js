import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";
import Swal from "sweetalert2";

import { userConfirm, findById, tokenRegeneration, logout } from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useUserStore = defineStore("userStore", () => {
    const router = useRouter();

    // 테스트를 위해 임시로 true를 발행
    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userInfo = ref(null);
    const isValidToken = ref(false);

    const userLogin = async (loginUser) => {
        await userConfirm(
            loginUser,
            (response) => {
                if (response.status === httpStatusCode.CREATE) {
                    console.log("로그인 성공!!!!");
                    let { data } = response;
                    let accessToken = data["access-token"];
                    let refreshToken = data["refresh-token"];
                    isLogin.value = true;
                    isLoginError.value = false;
                    isValidToken.value = true;
                    sessionStorage.setItem("accessToken", accessToken);
                    sessionStorage.setItem("refreshToken", refreshToken);
                }
            },
            (error) => {
                Swal.fire({
                    icon: "error",
                    title: "로그인 실패.",
                    text: "잘못된 아이디 혹은 비밀번호입니다.",
                });
                console.log("로그인 실패!!!!");
                isLogin.value = false;
                isLoginError.value = true;
                isValidToken.value = false;
                console.error(error);
            }
        );
    };

    const getUserInfo = async (token) => {
        let decodeToken = jwtDecode(token);
        console.log(decodeToken);
        await findById(
            decodeToken.userId,
            (response) => {
                if (response.status === httpStatusCode.OK) {
                    userInfo.value = response.data.userInfo;
                    sessionStorage.setItem("userIdx", userInfo.value.userIdx);
                    Swal.fire({
                        icon: "success",
                        title: `${userInfo.value.nickName}님 환영합니다.`,
                        text: `KT Trip과 함께 즐거운 여행을 그려보세요.`,
                    });
                } else {
                    console.log("유저 정보 없음!!!!");
                }
            },
            async (error) => {
                console.error("g[토큰 만료되어 사용 불가능.] : ", error.response.status, error.response.statusText);
                isValidToken.value = false;

                await tokenRegenerate();
            }
        );
    };

    const tokenRegenerate = async () => {
        await tokenRegeneration(
            JSON.stringify(userInfo.value),
            (response) => {
                if (response.status === httpStatusCode.CREATE) {
                    let accessToken = response.data["access-token"];
                    sessionStorage.setItem("accessToken", accessToken);
                    isValidToken.value = true;
                }
            },
            async (error) => {
                // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
                if (error.response.status === httpStatusCode.UNAUTHORIZED) {
                    // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
                    await logout(
                        userInfo.value.userid,
                        (response) => {
                            if (response.status === httpStatusCode.OK) {
                                console.log("리프레시 토큰 제거 성공");
                            } else {
                                console.log("리프레시 토큰 제거 실패");
                            }
                            alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                            isLogin.value = false;
                            userInfo.value = null;
                            isValidToken.value = false;
                            router.push({ name: "user-login" });
                        },
                        (error) => {
                            console.error(error);
                            isLogin.value = false;
                            userInfo.value = null;
                        }
                    );
                }
            }
        );
    };

    const userLogout = async () => {
        Swal.fire({
            icon: "success",
            title: `${userInfo.value.nickName}님 안녕히 가십시요.`,
            text: `KT Trip과 함께 즐거운 여행 되셨기를 바랍니다.`,
        });
        console.log("로그아웃 아이디 : " + userInfo.value.userIdx);
        await logout(
            userInfo.value.userIdx,
            (response) => {
                if (response.status === httpStatusCode.OK) {
                    isLogin.value = false;
                    userInfo.value = null;
                    isValidToken.value = false;

                    sessionStorage.removeItem("accessToken");
                    sessionStorage.removeItem("refreshToken");
                } else {
                    console.error("유저 정보 없음!!!!");
                }
            },
            (error) => {
                console.log(error);
            }
        );
    };

    return {
        isLogin,
        isLoginError,
        userInfo,
        isValidToken,
        userLogin,
        getUserInfo,
        tokenRegenerate,
        userLogout,
    };
});
