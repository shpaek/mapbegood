<!--favoriteList.vue-->
<!-- favoriteList.vue -->
<template>
  <div>
    <div class="addfavorite">
      <router-link to="/favoriteadd" class="addfavorite">즐찾목록 추가</router-link>
    </div>
    <div class="content">
      <h2>favoriteList 목록</h2>
      <ul>
        <li v-for="favorite in favoriteList" :key="favorite.themeMapDto.id">
          <h3>{{ favorite.themeMapDto.name }}</h3>
          <p>{{ favorite.themeMapDto.memo }}</p>
          <p>ID: {{ favorite.themeMapDto.id }}</p>
          <button @click="deleteFavorite(favorite.themeMapDto.id)">삭제</button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'FavoriteList',

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

      axios.get(url, { withCredentials: true })
        .then(response => {
          const list = response.data;
          this.favoriteList = list;
        })
        .catch(error => {
          console.error(error);
          alert(error.response.data.msg); // 수정된 부분
        });
    },

    deleteFavorite(themeMapId) {
      const url = `${this.backURL}/favorite/delete/${themeMapId}`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios.delete(url, { withCredentials: true })
        .then(response => {
          alert(response.data);
          // 삭제 후에 목록 다시 불러오기
          this.loadFavoriteList();
        })
        .catch(error => {
          console.error(error);
          alert(error.response.data.msg); // 수정된 부분
        });
    }
  }
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
  
  .sidebar { /*사이드바에서 사용하는 것이고 위치 설정해두기*/  
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
    margin-left: 200px; /* 사이드바 너비에 따라 조정 */
  }
  </style>