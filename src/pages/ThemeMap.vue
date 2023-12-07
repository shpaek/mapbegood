<!--favoriteAdd.vue-->

<template>
    <div class="content">
      <h1>나의 테마지도리스트 입니다.</h1>
      <ul>
        <li v-for="thememap in favoriteList" :key="thememap.themeMapDto.id">
          <h3>{{ thememap.themeMapDto.name }}</h3>
          <p>{{ thememap.themeMapDto.memo }}</p>
          <!-- <p> {{favorite.themeMapDto.id }}</p> -->

           <!-- "상세보기" 버튼 -->
          <button @click="viewThememapDetail(thememap.themeMapDto.id)">상세보기</button>
        </li>
      </ul>
  
      <!-- "테마맵 추가" 버튼 -->
      <button @click="addNewThememap">리스트 생성</button>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: 'FavoriteList',
    data() {
      return {
        favoriteList: [], // favoritelist이 보여지고 담을 곳
      };
    },
    mounted() {
      this.loadFavoriteList();
    },
  
    methods: {
      loadFavoriteList() {
        const url = `${this.backURL}/mymap/list`;
  
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;
  
        axios.get(url, { withCredentials: true })
          .then(response => {
            // 사용자의 테마맵 리스트를 받아옴
            const themeMapList = response.data;
  
            // 즐겨찾기 테마맵 정보와 함께 favoriteList에 추가
            this.favoriteList = themeMapList.map(themeMap => {
              return {
                themeMapDto: themeMap, // 테마맵 정보
              };
            });
          })
          .catch(error => {
            console.error(error);
            alert(error.msg);
          });
      },
      addNewThememap() {
      // "리스트 생성" 버튼 클릭 시 ThememapCreate 페이지로 이동
      this.$router.push({ name: 'thememap-create' });
    },
      viewThememapDetail(themeMapId) {
        // 상세보기 버튼 클릭 시 ThememapDetail 페이지로 이동
        this.$router.push({ name: 'thememap-detail', params: { id: themeMapId } });
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