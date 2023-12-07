<template>
    <div>
      <input v-model="searchTerm" placeholder="검색어를 입력하세요" />
      <button @click="executeSearch">검색하기</button>
      <div v-if="themeMaps.length > 0">
        <ul>
          <li v-for="themeMap in themeMaps" :key="themeMap.id">
            {{ themeMap.name }}  
            <button @click="addToFavorites(themeMap.id)">추가하기</button>
          </li>
        </ul>
      </div>
      <div v-else>
        검색 결과가 없습니다.
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        searchTerm: "",
        themeMaps: [],
      };
    },
    methods: {
      async searchThemeMap() {
        try {
          const url = `${this.backURL}/maplist/search`;
          const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
          axios.defaults.headers.common["Authorization"] = accessToken;
  
          const response = await axios.get(url, {
            params: {
              name: this.searchTerm,
            },
          });
  
          this.themeMaps = response.data;
        } catch (error) {
          console.error("검색 중 오류 발생:", error);
          this.themeMaps = [];
        }
      },
      executeSearch() {
        // 검색 메서드 호출
        this.searchThemeMap();
      },
      async addToFavorites(thememapId) {
        try {
          const url = `${this.backURL}/favorite/create/${thememapId}`;
          const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
          axios.defaults.headers.common["Authorization"] = accessToken;
  
          console.log("검색어:", this.searchTerm); // 디버깅을 위한 로그
          const response = await axios.post(url);
          console.log(response.data); // 성공하면 콘솔에 출력
  
        } catch (error) {
          console.error("즐겨찾기 추가 중 오류 발생:", error);
        }
      },
    },
  };
  </script>