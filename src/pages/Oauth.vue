<template></template>

<script>
export default {
  name: "Oauth",
  beforeCreate() {
    const urlParams = new URLSearchParams(window.location.search);
    localStorage.setItem("mapbegoodToken", urlParams.get("access_token"));
    localStorage.setItem("refresh", urlParams.get("refresh_token"));

    location.href = "/";
  },
};
</script>

<style></style>
