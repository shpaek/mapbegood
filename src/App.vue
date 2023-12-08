<template>
  <Main />
</template>

<script>
import Main from "./components/Main.vue";
import Map from "./pages/Map.vue";
// import Search from "./pages/Search.vue";

export default {
  name: "App",

  components: {
    Main,
    Map,
    // Search,
  },
  methods: {
    centerMap(position) {
      // Do whatever you want with the centered map position
      console.log("Centering map to:", position);
    },
  },

  data: () => ({
    //
  }),
};
</script>

<style scoped>
</style>