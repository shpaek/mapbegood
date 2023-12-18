<!--favoriteList.vue-->
<!-- favoriteList.vue -->
<template>
  <!-- <router-link to="/favoriteadd" class="addfavorite">즐찾목록 추가</router-link> -->
  <div class="search-wrapper" style="max-width: 600px; margin: 0 auto">
    <h1>favoriteList 목록</h1>
    <ul class="elevated-list">
      <li v-for="favorite in favoriteList" :key="favorite.themeMapDto.id">
        <h2>{{ favorite.themeMapDto.name }}</h2>
        <!-- <p>{{ favorite.themeMapDto.memo }}</p> -->
        <!-- <p>ID: {{ favorite.themeMapDto.id }}</p> -->
        <button
          @click="deleteFavorite(favorite.themeMapDto.id)"
          class="btn btn-dark"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="20"
            height="20"
            fill="currentColor"
            class="bi bi-x-square"
            viewBox="0 0 16 16"
          >
            <path
              d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"
            />
            <path
              d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"
            />
          </svg>
        </button>
      </li>
    </ul>
  </div>
  <div class="m-part">
    <Detailmap />
  </div>
</template>

<script>
import axios from "axios";
import Detailmap from "./Detailmap.vue";

export default {
  name: "FavoriteList",
  components: {
    Detailmap,
  },

  data() {
    return {
      favoriteList: [],
    };
  },

  mounted() {
    this.loadFavoriteList();
  },

  methods: {
    loadFavoriteList() {
      const url = `${this.backURL}/favorite/list`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
        .then((response) => {
          this.favoriteList = response.data;
          console.log(this.favoriteList);
        })
        .catch((error) => {
          // console.error(error);
          // alert(error.response.data.msg); // 수정된 부분
          alert("로그인이 필요한 서비스 입니다.");
          location.href = "/login";
        });
    },

    deleteFavorite(themeMapId) {
      const url = `${this.backURL}/favorite/delete/${themeMapId}`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .delete(url, { withCredentials: true })
        .then((response) => {
          alert(response.data);
          // 삭제 후에 목록 다시 불러오기
          this.loadFavoriteList();
        })
        .catch((error) => {
          console.error(error);
          alert(error.response.data.msg); // 수정된 부분
        });
    },
  },
};
</script>

<style scoped>
/* 기존 스타일 */

h2 {
  color: #600;
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  border: 1px solid #ccc;
  padding: 1rem;
  margin-bottom: 0.5rem;
}

h3 {
  color: #007bff;
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

p {
  color: #666;
  font-size: 1rem;
  margin-bottom: 0;
}

.search-wrapper {
  /* position: absolute; */
  /* left: 454px; 왼쪽 영역의 너비 만큼 이동 */
  /* right: 0; 오른쪽에 닿도록 */
  /* height: 100%; */

  position: absolute;
  width: 390px;
  height: 100vh;
  overflow-x: hidden;
  overflow-y: auto;
}

.m-part {
  position: absolute;
  left: 454px; /* 왼쪽 영역의 너비 만큼 이동 */
  right: 0; /* 오른쪽에 닿도록 */
  height: 100%;
}

ul.elevated-list {
  list-style-type: none;
  padding: 0;
  border: 2px solid #4e4e52;
  border-radius: 10px;
  margin: 20px 0;
  background-color: #f8f9fa; /* Background color for the ul element */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Box shadow for elevation */
}
.sidebar {
  /*사이드바에서 사용하는 것이고 위치 설정해두기*/
  position: fixed;
  top: 50%;
  left: 10px;
  transform: translateY(-50%);
  background-color: #f8f9fa;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.content {
  margin-left: 20px; /* 사이드바 너비에 따라 조정 */
}
</style>
