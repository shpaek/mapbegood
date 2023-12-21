<!--favoriteList.vue-->
<!-- favoriteList.vue -->
<template>
  <!-- <router-link to="/favoriteadd" class="addfavorite">즐찾목록 추가</router-link> -->
  <div class="search-wrapper" style="max-width: 600px; margin: 0 auto">
    <a href="/favoriteList" style="color: #000; text-decoration: none">
      <h2 class="theme-list" style="margin-left: 10px; font-size: 24px; font-weight: bold;">즐겨찾기 목록</h2></a
    >

    <v-divider color="warningss"></v-divider>

    <div
      style="
        position: absolute;
        width: 390px;
        height: 91vh;
        overflow-x: hidden;
        overflow-y: auto;
      "
    >
      <ul
        class="elevated-list"
        v-for="favorite in favoriteList"
        :key="favorite.themeMapDto.id"
        style="margin-left: 10px; margin-right: 10px"
      >
        <li>
          <div style="display: inline-block; width: 265px">
            <h5>
              <b>{{ favorite.themeMapDto.name }}</b>
            </h5>
            <p>{{ favorite.themeMapDto.memo }}</p>
            <p v-show="favorite.themeMapDto.memo == null">&nbsp;</p>
          </div>
          <button
            @click="deleteFavorite(favorite.themeMapDto.id)"
            class="btn btn-dark"
            style="position: absolute; margin-top: 8px"
          >
            삭제
            <!-- <svg
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
            </svg> -->
          </button>
        </li>
      </ul>
    </div>
 
     
    <ul class="list-group">
      <li v-for="favorite in favoriteList" :key="favorite.themeMapDto.id"
          class="thememap">
        <div class="info">  
          <div class="name">{{ favorite.themeMapDto.name }}</div>
          <div class="memo">{{ favorite.themeMapDto.memo }}</div>
          <!-- <div v-show="favorite.themeMapDto.memo == null"></div> -->
        </div>
          <span @click="deleteFavorite(favorite.themeMapDto.id)" class="star">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
              <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
            </svg>
          </span>
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
import Swal from "sweetalert2";

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
          // console.log(this.favoriteList);
        })
        .catch((error) => {
          // console.error(error);
          // alert(error.response.data.msg); // 수정된 부분
          Swal.fire({
            text: "로그인이 필요한 서비스 입니다.",
            icon: "warning",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              location.href = "/login";
            }
          });
        });
    },
    deleteFavorite(themeMapId) {
      console.log("즐찾취소")
      const url = `${this.backURL}/favorite/delete/${themeMapId}`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      Swal.fire({
        title: "삭제",
        text: "정말로 삭제하시겠습니까?",
        icon: "warning",
        showDenyButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "삭제",
        denyButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          axios
            .delete(url, { withCredentials: true })
            .then((response) => {
              // alert(response.data);
              // 삭제 후에 목록 다시 불러오기
              this.loadFavoriteList();
            })
            .catch((error) => {
              console.error(error);
              // alert(error.response.data.msg); // 수정된 부분
            });
          Swal.fire({
            title: "즐겨찾기 삭제",
            text: "삭제되었습니다",
            icon: "success",
          });
        }
      });
    },
  },
};
</script>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
div.g-part {
  position: absolute;
  width: 390px;
  height: 100vh;
  overflow-x: hidden;
  overflow-y: auto;
}
.m-part {
  position: absolute;
  left: 454px;
  right: 0;
  height: 100vh;
}

ul {
  list-style-type: none;
  padding: 0;
  margin-top:30px;
}
li.thememap {
  display:flex;
  margin-left:auto;
  margin-right:auto;
  border: 1px solid grey;
  border-radius: 5px;
  height: 60px;
  width: 330px;
  vertical-align: baseline;
}
li.thememap > div.info {
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 13px;
  width: 260px;
}
li.thememap > div.info > div.name {
  font-size: 18px;
  font-weight: bold;
}
li.thememap > div.info > div.memo {
  font-size: 13px;
  color: grey;
  overflow-x: hidden;
  white-space: nowrap;
  max-width: 230px;
}

ul.list-group > li {
  margin-top: 15px;
}
ul.list-group > li:hover {
  background-color: rgba(0,112,192,0.329); /* 좋을지도 로고 파란색, 투명도 적용 */
}
span.star{
  margin-left:20px;
  margin-top: 13px;
}
span.star>svg{
  fill:rgba(250, 206, 60, 0.767);
}
span.star>svg :hover{
  fill:white;
}
/* 
.search-wrapper {
  position: absolute;
  width: 390px;
  height: 100vh;
}
h2 {
  margin: 12px 0px 8px;
}
li {
  border: 1px solid #ccc;
  padding: 1rem;
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
ul.elevated-list {
  list-style-type: none;
  padding: 0;
  border: 2px solid #4e4e52;
  border-radius: 10px;
  margin: 20px 0;
  background-color: #f8f9fa; 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
  max-width: 400px;
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
  margin-left: 20px;
} */
</style>
