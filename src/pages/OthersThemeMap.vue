<template>
  <div>
    <p class="mt-3">테마맵 이름을 검색하세요</p>
    <div class="input-group mb-3">
      <input v-model="searchTerm" placeholder="검색어를 입력하세요" class="form-control" />
      <div class="input-group-append">
        <button @click="executeSearch" class="btn btn-primary">검색하기</button>
      </div>
    </div>

    <div v-if="themeMaps.length > 0">
      <!-- 검색 결과가 있는 경우 -->
      <ul class="list-group">
        <!-- 각 테마맵에 대한 목록 -->
        <li v-for="themeMap in themeMaps" :key="themeMap.id" class="list-group-item">
          <div>
            <h5 class="mb-1">{{ themeMap.name }}</h5>
            <p class="mb-1">{{ themeMap.memo }}</p>
            <small>{{ themeMap.id }}</small>

            <!-- 추가하기 버튼 -->
            <button @click="addToFavorites(themeMap.id)" class="btn btn-success">추가하기</button>

            <!-- 중복 여부 확인 메시지 -->
            <span v-if="isInFavorites(themeMap.id)" class="text-danger ml-2">
              이미 추가된 list입니다.
            </span>
          </div>
        </li>
      </ul>
    </div>

    <!-- 중복 여부 확인 메시지 -->
    <div v-else class="alert alert-warning mt-3" role="alert">
      검색 결과가 없습니다.
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
// 검색어와 테마맵 목록을 담는 데이터
      searchTerm: "",
      themeMaps: [],
    };
  },
  methods: {
// 테마맵 검색 메서드
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

        this.themeMaps = response.data.map(map => ({
          ...map,
          isInFavorites: false,
        }));
      } catch (error) {
        console.error("검색 중 오류 발생:", error);
        this.themeMaps = [];
      }
    },
    executeSearch() {
// 검색 메서드 호출
      this.searchThemeMap();
    },
// 즐겨찾기에 추가 메서드
    async addToFavorites(themeMapId) {
      try {
        const url = `${this.backURL}/favorite/create/${themeMapId}`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;

console.log("검색어:", this.searchTerm); // 디버깅을 위한 로그
        const response = await axios.post(url);
        console.log(response.data); // 성공하면 콘솔에 출력
        // 추가 성공 메시지
        alert("추가되었습니다");

// Update the isInFavorites property after successfully adding to favorites
        const updatedThemeMaps = this.themeMaps.map(map => {
          if (map.id === themeMapId) {
            return { ...map, isInFavorites: true };
          }
          return map;
        });
        this.themeMaps = updatedThemeMaps;
      } catch (error) {
        console.error("즐겨찾기 추가 중 오류 발생:", error);
      }
    },
// themeMapId가 현재 사용자의 즐겨찾기 목록에 있는지 확인
    isInFavorites(themeMapId) {
      return this.themeMaps.some(map => map.id === themeMapId && map.isInFavorites);
    },
  },
};
</script>