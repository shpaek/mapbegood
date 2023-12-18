<template>
  <div class="container mt-5">
    <h5 class="display-4" style="font-size:2rem; font-weight:bold;">   
      <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" class="bi bi-caret-right-fill" viewBox="0 0 16 16">
        <path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"/>
      </svg>
      {{ thememap.name }}
    </h5>
    
    <div class="col-md-3">
    <div class="card">
      <div class="card-body">
        <h5 class="card-title" text-warning>
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-left-text" viewBox="0 0 16 16">
            <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
            <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6zm0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"/>
          </svg>
          내용:
        </h5>
        <p class="card-text">{{ thememap.memo }}</p>
      </div>
    </div>
  </div>
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