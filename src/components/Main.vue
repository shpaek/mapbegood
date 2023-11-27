<template>
    <div class="main-container">
        <Menubar />
        <Section />
    </div>
</template>
<script>
import Menubar from './Menubar.vue';
import Section from './Section.vue';
export default {
    name: "Main",
    components: {Menubar, Section}
}
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