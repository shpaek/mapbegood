import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store/store";

// Vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

const vuetify = createVuetify({
  components,
  directives,
});

const app = createApp(App);
app.use(router);
app.use(store);
app.use(vuetify);
app.config.globalProperties.backURL = "http://localhost:8080";
// app.config.globalProperties.beforCreate = function () {
//   console.log("before");
//   this.$store.dispatch("getUserInfo");
// };
app.mount("#app");
