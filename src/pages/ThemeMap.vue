<template>
  <div class="section-container">
    <div class="g-part">
      <div class="search-wrapper" style="max-width: 600px; margin: 0 auto">

      <div class="header-container">
      <h1 class="theme-list">나의 테마지도리스트</h1>
           <!-- "테마맵 추가" 버튼 -->
           <button
          @click="openCreate"
          class="sticker-btn btn btn-outline-secondary"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="23"
            height="23"
            fill="currentColor"
            class="bi bi-file-earmark-plus-fill"
            viewBox="0 0 16 16"
          >
        </svg>
        </button>
      </div>

      <!-- 각 테마맵에 대한 반복문 -->
      <div v-for="thememap in favoriteList" :key="thememap.themeMapDto.id">
        <ul class="elevated-list mb-4">
          <li>
            <h3 @click="detailThememap(thememap.themeMapDto.id)">
              리스트이름:{{ thememap.themeMapDto.name }}
            </h3>
            <h5>
              <!-- 공개 여부에 따라 다른 아이콘 표시 -->
              <span v-if="thememap.themeMapDto.show">
                <!-- ... -->
              </span>
              <span v-else>
                <!-- ... -->
              </span>
            </h5>
            <div class="text-right">
              <!-- "상세보기" 버튼 -->
              <button
                @click="detailThememap(thememap.themeMapDto.id)"
                class="btn btn-outline-secondary"
              >
                상세보기
              </button>
              <!-- 리스트 수정 버튼 -->
              <button
                @click="editThememap(thememap.themeMapDto.id)"
                class="btn btn-outline-secondary"
              >
                수정
              </button>
              <!-- 리스트 삭제 버튼 -->
              <button
                @click="deleteThememap(thememap.themeMapDto.id)"
                class="btn btn-outline-danger"
              >
                삭제
              </button>
              <!-- 리스트 복사 버튼 -->
              <button
                @click="copyThememap(thememap.themeMapDto.id)"
                class="btn btn-outline-secondary"
              >
                복사
              </button>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
    <div class="m-part">
    <Detailmap />
  </div>
</div>
<div class="modal-container">
  <ThememapCreate
      :isCreateOpen="isCreateOpen"
      @close-create="closeCreate"
      />
      <!-- :groupId="groupMapId" -->
 </div>
   
   
 
</template>

<script>
import axios from "axios";
import Detailmap from "./Detailmap.vue";
import ThememapCreate from "./ThememapCreate.vue";

export default {
  name: "FavoriteList",
  components: {
    Detailmap,
    ThememapCreate
},
  data() {
    return {
      favoriteList: [],
      themeMapId: null, // 초기값 추가
      isCreateOpen: false
    };
  },

  mounted() {
    this.loadMymapList();
  },

  methods: {
    loadMymapList() {
      const url = `${this.backURL}/mymap/list`;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
        .then((response) => {
          const themeMapList = response.data;
          this.favoriteList = themeMapList.map((themeMap) => {
            return {
              themeMapDto: themeMap,
            };
          });
        })
        .catch((error) => {
          console.error(error);
          alert("로그인이 필요한 서비스입니다.");
          location.href = "/login";
        });
    },

    addNewThememap() {
      this.$router.push({ name: "ThememapCreate" });
    },

    detailThememap(themeMapId) {
      this.$router.push({
        name: "thememapdetail",
        params: { id: themeMapId },
      });
    },

    editThememap(themeMapId) {
      this.themeMapId = themeMapId;
      this.$router.push({
        name: "thememapupdate",
        params: { id: themeMapId },
      });
    },

    copyThememap(themeMapId) {
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      const url = `${this.backURL}/mymap/copy/${themeMapId}`;

      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .post(url)
        .then((response) => {
          console.log(response.data);
          alert("테마맵이 성공적으로 복사되었습니다.");
        })
        .catch((error) => {
          console.error(error);
          alert("테마맵 복사에 실패했습니다. 다시 시도하세요.");
        });
    },

    deleteThememap(themeMapId) {
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      const url = `${this.backURL}/mymap/delete/${themeMapId}`;
      axios
        .delete(url)
        .then((response) => {
          console.log(response.data);
          alert("삭제 성공");
          this.loadMymapList();
        })
        .catch((error) => {
          console.error(error);
          alert("삭제 실패");
        });
    },  addNewThememap() {
      this.showThememapCreateModal = true;
      // Set the groupMapId if needed
      // this.groupMapId = ...;
    },

    // Add this method to close the ThememapCreate modal
    closeThememapCreateModal() {
      this.showThememapCreateModal = false;
      // Reset the groupMapId if needed
      // this.groupMapId = null;
    },
    openCreate(){
      this.isCreateOpen = true;
      console.log(this.isCreateOpen)
    },
    closeCreate(){
      this.isCreateOpen = false;
      console.log(this.isCreateOpen)
    }
  },
};
  
  
 
</script>
<style scoped>

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

.list-button-container {
  display: flex;
  justify-content: space-between; /* Adjust as needed */
  align-items: center; /* Adjust as needed */
}

/* 기존 스타일 */
div.g-part > div.empty-msg {
  padding: 70px;
}

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
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000; /* Set a higher z-index to ensure it's above other elements */
  position: relative;
  top: 0; /* Adjust as needed */
  right: 0; /* Adjust as needed */
}

.sticker-btn:hover {
  background-color: #e2e6e8; /* Change background color on hover */
}

button {
  margin-right: 10px;
  margin-bottom: 0;
}

ul.elevated-list {
  list-style-type: none;
  padding: 0;
  border: 2px solid #4e4e52;
  border-radius: 10px;
  margin: 5px 0;
  background-color: #f8f9fa; /* Background color for the ul element */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Box shadow for elevation */
  max-width: 400px;
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
  margin-left: 100px;
}
</style>
