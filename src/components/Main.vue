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
    let path = location.pathname;
    this.menu = path != "/login" && path != "/signup";
  },
};
</script>

<style scoped>
.main-container {
  display: flex;
  height: 100vh; /* 부모 요소를 화면의 100% 높이로 설정 */
}

div.section {
  flex: 1; /* Section이 남은 공간을 모두 차지하도록 설정 */
}
</style>
