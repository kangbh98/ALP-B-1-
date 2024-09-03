import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import { useKakao } from "vue3-kakao-maps/@utils";
import vue3StarRatings from "vue3-star-ratings";
// Nucleo Icons
import "./assets/css/nucleo-icons.css";
import "./assets/css/nucleo-svg.css";

import materialKit from "./material-kit";

// import axios from "axios";
// app.config.globalProperties.axios = axios;

useKakao("3b2905e963dece1f2a34a9b0c887904b", ["clusterer", "services", "drawing"]);
const app = createApp(App);
app.component("vue3-star-ratings", vue3StarRatings);

app.use(createPinia());
app.use(router);
app.use(materialKit);
app.mount("#app");
