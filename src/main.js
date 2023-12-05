import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";

const app = createApp(App);
app.use(router);
app.config.globalProperties.backURL = "http://localhost:8080/mapbegood";
app.mount("#app");
