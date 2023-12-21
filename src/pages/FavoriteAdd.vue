<!--favoriteAdd.vue-->

<template>
  <div class="content">
    <h1>나의 thememaplist 입니다.</h1>
    <h2>즐겨찾기 할 thememap을 선택하세요</h2>
    <ul>
      <li v-for="thememap in favoriteList" :key="thememap.themeMapDto.id">
        <h3>{{ thememap.themeMapDto.name }}</h3>
        <p>{{ thememap.themeMapDto.memo }}</p>
        <!-- "즐겨찾기 추가" 버튼 추가 -->
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  name: "FavoriteList",
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
      const url = "api.mapbegood.site/mymap/list";

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
        .then((response) => {
          // 사용자의 테마맵 리스트를 받아옴
          const themeMapList = response.data;

          // 즐겨찾기 테마맵 정보와 함께 favoriteList에 추가
          this.favoriteList = themeMapList.map((themeMap) => {
            return {
              themeMapDto: themeMap, // 테마맵 정보
            };
          });
          console.log(this.favoriteList);
        })
        .catch((error) => {
          console.error(error);
          alert(error.msg);
        });
      // },
      //     addFavorite(themeMapId) {
      //       console.log("아이디 가져와주세요:", themeMapId);

      //       // "즐겨찾기 추가" 로직 추가
      //       const url = `${this.backURL}/favorite/create/${themeMapId}`;

      //       const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      //       axios.defaults.headers.common["Authorization"] = accessToken;

      //       axios.post(url, { themeMapId: themeMapId }, { withCredentials: true })
      //         .then(response => {
      //           // 성공적으로 즐겨찾기를 추가한 경우에 수행할 작업
      //           console.log(response.data);
      //         })
      //         .catch(error => {
      //           // 즐겨찾기 추가 중에 오류가 발생한 경우에 수행할 작업
      //           console.error(error);
      //           alert(error.msg);
      //         });
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
  margin-left: 200px; /* 사이드바 너비에 따라 조정 */
}
</style>
