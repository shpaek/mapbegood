import { createApp } from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import { loadFonts } from "./plugins/webfontloader";
import router from "./router";

loadFonts();

const app = createApp(App);
app.use(vuetify);
app.use(router);
app.config.globalProperties.backURL = "http://localhost:8080/mapbegood";
app.mount("#app");
