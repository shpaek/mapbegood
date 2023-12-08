<template lang="">
  <div>추천리스트입니다</div>
  <div>{{ userInfo }}</div>
</template>
<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "OthersThemeMap",
  computed: {
    ...mapState(["userInfo"]),
  },
  beforeCreate() {
    this.$store.dispatch("getUserInfo");
  },
};
</script>
<style scoped></style>
