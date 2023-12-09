<!--ThemeMap.vue-->
<template>
    <div class="content">
      <h1>나의 테마지도리스트 입니다.</h1>
      <ul>
        <!-- favoriteList 내 각 항목에 대해 반복 -->
        <!-- v-for을 사용해서 반복문 구현 -->
        <!--in favaoriteList: 반복할 대상 데이터이며 , favoriteList 배열의 각 요소를 하나씩 순회함.-->
        <!--key는반복된 엘리먼트에 고유한 키를 제공하며, thememapDto.id를 사용-->
        <li v-for="thememap in favoriteList" :key="thememap.themeMapDto.id">
          <h3>{{ thememap.themeMapDto.name }}</h3>
          <p>{{ thememap.themeMapDto.memo }}</p>
          <p>{{ thememap.themeMapDto.id }}</p>
            
          <!-- "상세보기" 버튼 -->
          <button @click="detailThememap(thememap.themeMapDto.id)">상세보기</button>
          <!--리스트 수정 버튼-->
          <button @click="editThememap(thememap.themeMapDto.id)">수정</button>
          <!--리스트 삭제 버튼-->
          <button @click="deleteThememap(thememap.themeMapDto.id)">삭제</button>
        </li>
      </ul>
  
      <!-- "테마맵 추가" 버튼 -->
      <button @click="addNewThememap">리스트 생성</button>
    </div>
  </template>
  
  <script>
  import axios from 'axios'; //axios를 사용하기 위해서는 import를 해야한다.
  

  export default {
    name: 'FavoriteList',
    data() {
      return {
        favoriteList: [], // 테마 맵 목록을 저장할 배열
        themeMapId: null, 
    };
    },
    mounted() {
      this.loadMymapList();
    },
  //서버에서 테마 맵 목록 가져오는 코드 Start------
    methods: {
      loadMymapList() {
        const url = `${this.backURL}/mymap/list`;
  
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;
  
        axios.get(url, { withCredentials: true })
          .then(response => {
             // 받은 데이터를 처리하고 favoriteList 배열을 채운다.
            const themeMapList = response.data;
            this.favoriteList = themeMapList.map(themeMap => {
              return {
                themeMapDto: themeMap,
              };
            });
          })
          .catch(error => {
            console.error(error);
            alert(error.msg);
          });
      },
    //서버에서 테마 맵 목록 가져오는 코드 End------

      addNewThememap() {
        this.$router.push({ name: 'ThememapCreate' });
      },
  
      detailThememap(themeMapId) {
        this.$router.push({ name: 'thememapdetail', params: { id: themeMapId } });
      },
      //이건 페이지 이동하는 것이며 $router.push를 통해 지정한 .vue로 이동함. 또한 index.js에서 따로 수정해야함.
      editThememap(themeMapId) {
      this.themeMapId = themeMapId; // themeMapId를 설정
      this.$router.push({ name: 'thememapupdate', params: { id: themeMapId } });
      },
      //삭제 버튼을 눌렀을때 할 일 START----- 
      deleteThememap(themeMapId) {
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;
  
        const url = `${this.backURL}/mymap/delete/${themeMapId}`;
        axios.delete(url)
          .then(response => {
            console.log(response.data);
            alert("삭제 성공");
            this.loadMymapList(); // 함수명 수정
          })
          .catch(error => {
            console.error(error);
            alert("삭제 실패");
          });
      },
    },
    //삭제 버튼을 눌렀을때 할 일 END----- 
  };
  </script>
  
  <style scoped>
  /* 기존 스타일 */
  
  h2 {
    color: #600;
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }
  
  button {
    margin-right: 10px;
    margin-bottom: 0;
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
  
  .sidebar {
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
    margin-left: 200px;
  }
  </style>