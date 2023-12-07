<!--favoriteList.vue-->
<template>
  <div>  
    
    <div class="addfavorite">
      <!--router-link를 사용해서addfavorite으로 가게 하는 -->
      <router-link to="/favoriteadd" class="addfavorite">즐찾목록 추가</router-link>
    </div>
    <div class="content"> 
      <h2>favoriteList 목록</h2>
      <ul>
        <li v-for="favorite in favoriteList" :key="favorite.themeMapDto.id">
          <h3>{{ favorite.themeMapDto.name }}</h3>
          <p>{{ favorite.themeMapDto.memo }}</p>
          <p>ID: {{favorite.themeMapDto.id }}</p>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'FavoriteList',
  
  //data: Vue 컴포넌트의 데이터 속성을 정의
  //favoriteList는 화면에 표시되고 저장될 즐겨찾기 목록을 담는 배열임
  data() {
    return {
      favoriteList: [],//favoritelist이 보여지고 담을 곳
    };
  },
  //mounted: Vue 컴포넌트 라이프사이클 훅 중 하나인 mounted에서는 컴포넌트가 화면에 
  //나타난 직후에 실행될 코드를 작성, 여기서는 loadFavoriteList 메소드를 호출
  mounted() {//화면에 나타난 직후 실행되는 함수
    //loadFavoriteList 메소드 호출
    this.loadFavoriteList();
     // 추가 코드 시작
     console.log(this.favoriteList);
    if (this.favoriteList.length > 0) {
      console.log(this.favoriteList[0].themeMapDto);
      console.dir(this.favoriteList[0].themeMapDto);
    }
  },
//methods: Vue 컴포넌트의 메소드를 정의 
//여기서 loadFavoriteList 메소드는 Back으로부터 즐겨찾기 목록을 가져오는 역할. 
//Axios를 사용하여 Backend API로 GET 요청을 보내고,성공 또는 실패 시에 적절한 처리
//가져온 데이터는 favoriteList에 저장됩니다.
  methods: {
    loadFavoriteList() {
      //back으로 부터 즐겨찾기 목록을 가져오는 메소드 
      const url = `${this.backURL}/favorite/list`
      //로그인한 사용자의 access token을 헤더에 추가함
        const accessToken = "Bearer "+localStorage.getItem("mapbegoodToken")
        axios.defaults.headers.common["Authorization"] = accessToken;

        //back으로 get을 보내 즐겨찾기 목록을 가져옴.
        axios.get(url,{ withCredentials: true })
            .then(response => {
                //사용자의 그룹 목록 받기
                const list = response.data
                this.favoriteList = list
                console.log(response.data)
                console.log(error)
                alert(error.msg)
            })
    }
    },
  }
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