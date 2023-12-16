<template>
  <div class="main-container">
    <Menubar v-show="menu" />
    <Section />
  </div>
</template>

<script>
import Menubar from "./Menubar.vue";
import Section from "./Section.vue";
import Search from "./Search.vue";

let menu = true;

export default {
  name: "Main",
  components: {
    Menubar,
    Section,
    Search,
  },
  created() {
    let path = location.pathname.substring(1).toLowerCase();
    this.menu = path != "login" && path != "signup";
  },
};
</script>

<style scoped>
.main-container {
  height: 100vh;
}
div.section {
  margin-left: 64px;
  overflow-y: auto; /* 추가: Section이 내용이 넘칠 때 스크롤 표시 */
}
</style>
