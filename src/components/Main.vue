<template>
  <div class="main-container">
    <!-- <Menubar v-show="this.menu" /> -->
    <Menubar v-if="$route.name !== 'login'" />
    <Section />
  </div>
</template>

<script>
import Menubar from "./Menubar.vue";
import Section from "./Section.vue";
import Search from "./Search.vue";

export default {
  name: "Main",
  components: {
    Menubar,
    Section,
    Search,
  },
};
</script>

<style scoped>
.main-container {
  display: flex;
  height: 100vh;
}
div.section {
  margin-left: 64px;
  overflow-y: auto; /* 추가: Section이 내용이 넘칠 때 스크롤 표시 */
}
</style>
