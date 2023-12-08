import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store/store";

<<<<<<< HEAD
// Vuetify
import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";

const vuetify = createVuetify({
  components,
  directives,
});

=======
>>>>>>> a5efb55 (rollback)
const app = createApp(App);
app.use(router);
app.use(store);
app.use(vuetify);
app.config.globalProperties.backURL = "http://localhost:8080";
app.mount("#app");
