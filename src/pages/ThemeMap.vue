<template>
  <div class="content">
    <h1 class="theme-list">나의 테마지도리스트</h1>

    <!-- 각 테마맵에 대한 반복문 -->
    <div v-for="thememap in favoriteList" :key="thememap.themeMapDto.id">
      <ul class="elevated-list mb-4">
        <li>
          <h3>리스트이름:{{ thememap.themeMapDto.name }}</h3>
          <h5>
            <!-- 공개 여부에 따라 다른 아이콘 표시 -->
            <span v-if="thememap.themeMapDto.show">
              <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-unlock-fill" viewBox="0 0 16 16">
                <path d="M11 1a2 2 0 0 0-2 2v4a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2h5V3a3 3 0 0 1 6 0v4a.5.5 0 0 1-1 0V3a2 2 0 0 0-2-2z"/>
              </svg>
            </span>
            <span v-else>
              <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-lock-fill" viewBox="0 0 16 16">
                <path d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"/>
              </svg>
            </span>
          </h5>
          <div class="text-right">
            <!-- "상세보기" 버튼 -->
            <button @click="detailThememap(thememap.themeMapDto.id)" class="btn btn-outline-secondary">상세보기</button>
            <!-- 리스트 수정 버튼 -->
            <button @click="editThememap(thememap.themeMapDto.id)" class="btn btn-outline-secondary">수정</button>
            <!-- 리스트 삭제 버튼 -->
            <button @click="deleteThememap(thememap.themeMapDto.id)" class="btn btn-outline-danger">삭제</button>
            <!-- 리스트 복사 버튼 -->
            <button @click="copyThememap(thememap.themeMapDto.id)" class="btn btn-outline-secondary">복사</button>
          </div>
        </li>
      </ul>
    </div>
      <!-- "테마맵 추가" 버튼 -->
      <!-- <button @click="addNewThememap" class="sticker-btn btn btn-outline-secondary">리스트 생성</button> -->
      <button @click="addNewThememap" class="sticker-btn btn btn-outline-secondary">
  <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" fill="currentColor" class="bi bi-file-earmark-plus-fill" viewBox="0 0 16 16">
    <path d="M9.293 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V4.707A1 1 0 0 0 13.707 4L10 .293A1 1 0 0 0 9.293 0zM9.5 3.5v-2l3 3h-2a1 1 0 0 1-1-1zM8.5 7v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 1 0z"/>
  </svg>
</button>

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
      //복사 버튼을 눌렀을때 할일 START----
      copyThememap(themeMapId) {
      // 선택한 테마맵을 복사하기 위한 서버 요청
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      const url = `${this.backURL}/mymap/copy/${themeMapId}`;

      axios.defaults.headers.common["Authorization"] = accessToken;

      axios.post(url)
        .then(response => {
          console.log(response.data);
          // 성공적으로 복사되었을 때의 로직 추가
          alert("테마맵이 성공적으로 복사되었습니다.");
        })
        .catch(error => {
          console.error(error);
          alert("테마맵 복사에 실패했습니다. 다시 시도하세요.");
        });
    },

      //복사 버튼을 눌렀을때 할일 END---

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

  h1.list-heading {
    color: #004080;
    font-size: 2.5rem;
    font-weight: bold;
    margin-bottom: 1.5rem;
  }
  ul.theme-list {
  list-style-type: none;
  padding: 0;
  border: 2px solid #4e4e52; /* Border color for the ul element */
  border-radius: 10px; /* Border radius for rounded corners */
  margin: 20px 0; /* Adjust margin as needed */
}
  .sticker-btn {
    background-color: #4e4e52; /* Sticker background color */
    border: 1px solid #000000; /* Border for the sticker */
    padding: 0.75rem 1.5rem; /* Adjust padding for the sticker */
    font-size: 1rem;
    border-radius: 8px; /* Border radius for rounded corners */
    cursor: pointer;
    transition: background-color 0.3s ease; /* Add a smooth transition effect */
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
  .sticker-btn:hover {
    background-color: #e2e6e8; /* Change background color on hover */
  }

  button {
    margin-right: 10px;
    margin-bottom: 0;
  }

  ul {
    list-style-type: none;
    padding: 0;
    max-width: 600px;
    margin: 0;
  }

  li {
    border: 1px solid #ccc;
    padding: 1rem;
    margin-bottom: 0.5rem;
  }

  h3 {
    color: #030d18;
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
  