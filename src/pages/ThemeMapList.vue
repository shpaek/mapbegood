<template>
    <div>
      <!-- 생성 -->
      <button @click="createThemeMap">테마맵 생성</button>
  
      <!-- 삭제 -->
      <button @click="deleteThemeMap">테마맵 삭제</button>
  
      <!-- 수정 -->
      <button @click="updateThemeMap">테마맵 수정</button>
  
      <!-- 조회 -->
      <button @click="getAllThemeMaps">모든 테마맵 조회</button>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    methods: {
        name:"thememaplist",
      async createThemeMap() {
        try {
          const response = await axios.post('/mymap/create', { /* 테마맵 데이터 */ });
          console.log(response.data); // 생성된 테마맵 데이터
        } catch (error) {
          console.error('테마맵 생성 오류:', error);
        }
      },
  
      async deleteThemeMap() {
        try {
          const themeMapId = 1; // 삭제할 테마맵의 ID
          const response = await axios.delete(`/mymap/delete/${themeMapId}`);
          console.log(response.data); // 삭제 결과 메시지
        } catch (error) {
          console.error('테마맵 삭제 오류:', error);
        }
      },
  
      async updateThemeMap() {
        try {
          const themeMapId = 1; // 수정할 테마맵의 ID
          const response = await axios.put(`/mymap/update/${themeMapId}`, { /* 수정할 테마맵 데이터 */ });
          console.log(response.data); // 수정된 테마맵 데이터
        } catch (error) {
          console.error('테마맵 수정 오류:', error);
        }
      },
  
      async getAllThemeMaps() {
        try {
          const response = await axios.get('/mymap/list');
          console.log(response.data); // 모든 테마맵 목록
        } catch (error) {
          console.error('테마맵 조회 오류:', error);
        }
      },
    },
  };
  </script>