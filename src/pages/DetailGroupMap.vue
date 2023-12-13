<!--DetailGroupMap.vue-->
<template>
  <div class="container mt-5">
    <h1 class="display-4">테마리스트 이름: {{ thememap.name }}</h1>
    <p class="lead">테마리스트 내용: {{ thememap.memo }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'DetailGroupMap',
  data() {
    return {
      thememap: {}, // 테마지도 정보 저장할 객체
    };
  },
  created() {
    const groupThememapId = this.$route.params.groupThememapId;

    axios.get(`http://localhost:8080/ourmap/get/${groupThememapId}`)
      .then(response => {
        // API로부터 받은 데이터를 컴포넌트의 데이터에 저장
        this.thememap = response.data;
      })
      .catch(error => {
        console.error('Error fetching theme map data:', error);
      });
  },
};
</script>

<style scoped>
/* 추가적인 스타일링을 할 수 있습니다. */
</style>