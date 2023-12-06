<template>
  <div> <!--사이드바 사용하는 코드-->
    <div class="sidebar">
      <button>gd</button> <!--버튼 만들기-->
      <button>Sidebar</button>
      
    </div>
    
    <div class="content"> 
      <!-- 기존 컨텐츠 -->
      <h2>favoriteList 목록</h2>
      <ul>
        <li v-for="favorite in favoriteList" :key="favorite.themeMapDto.id">
          <h3>{{ favorite.themeMapDto.name }}</h3>
          <p>{{ favorite.themeMapDto.memo }}</p>
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
    async loadFavoriteList() {
      try {
        const response = await axios.get('/favorite/list');
        this.favoriteList = response.data;
      } catch (error) {
        console.error('즐겨찾기 목록을 불러오는 중 오류가 발생했습니다:', error);
      }
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

/* 추가된 스타일 - 사이드바 스타일 및 위치 조정 */
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