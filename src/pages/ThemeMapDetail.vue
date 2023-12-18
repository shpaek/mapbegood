<template>
  <!-- 전체화면에 맵 표시 -->
  <DetailMap :mymapdetail="mymapdetail" />

  <div class="theme-map-details">
    <h2 class="display-4"   style="font-size:2rem; fonr-weight:bold;">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-right-fill" viewBox="0 0 16 16">
        <path d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"/>
      </svg>
      {{ mymapdetail.themeMapDto.name }}
    </h2>
    
    <div class="card">
      <div class="card-body">
        <h5 class="card-title" text-warning>
          <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" class="bi bi-chat-left-text" viewBox="0 0 16 16">
            <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
            <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6zm0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"/>
          </svg>
          내용:
        </h5>
        <p class="card-text">{{ mymapdetail.themeMapDto.memo }}</p>
      </div>
    </div>
  <!--<p>아이디(가릴예정): {{ mymapdetail.themeMapDto.id }}</p>-->

    <div class="myplace-list-container">
      <ul class="myplace-list">
        <li
          v-for="myplace in mymapdetail.myplaces"
          :key="myplace.id"
          class="myplace-item"
        >
          <div class="myplace-info">
            <h5>{{ myplace.placeId.placeName }}</h5>
            <p>방문 일자: {{ myplace.visitedAt }}</p>
            <p>주소: {{ myplace.placeId.address }}</p>
            <!-- 추가적인 Myplace 정보 표시 -->
          </div>
          <button
            class="add-bookmark-btn"
            @click.stop="addBookmark(myplace.placeId)"
          >
            <img src="/public/images/bookmark.png" class="bookmark-icon" />
            북마크
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import DetailMap from "./Detailmap.vue"; // Update the path accordingly

export default {
  components: {
    DetailMap,
  },
  data() {
    return {
      mymapdetail: {
        themeMapDto: {
          name: "",
          memo: "",
          id: "",
        },
        myplaces: [],
      },
    };
  },
  mounted() {
    // 라우터에서 테마맵 ID를 받아와서 상세 정보를 불러옵니다.
    const themeMapId = this.$route.params.id;
    this.loadThemeMapDetail(themeMapId);
  },
  methods: {
    async loadThemeMapDetail(themeMapId) {
      const url = `${this.backURL}/mymap/${themeMapId}`;

      try {
        const response = await axios.get(url, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("mapbegoodToken")}`,
          },
        });

        // 받은 데이터를 처리하고 mymapdetail 객체를 채웁니다.
        this.mymapdetail = {
          themeMapDto: response.data,
        };

        // 추가: Myplaces 불러오기
        this.findAllMyPlace(themeMapId);
      } catch (error) {
        console.error(error);
        alert(
          error.response.data.message ||
            "테마 맵 세부 정보를 불러오지 못했습니다."
        );
      }
    },

    async findAllMyPlace(themeMapId) {
      // Myplaces 불러오기
      const myPlacesUrl = `${this.backURL}/myplace/${themeMapId}`;

      try {
        const response = await axios.get(myPlacesUrl, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("mapbegoodToken")}`,
          },
        });

        // 받은 Myplaces 데이터를 mymapdetail에 추가
        this.mymapdetail.myplaces = response.data;
      } catch (error) {
        console.error(error);
        alert(error.response.data.message || "Myplaces를 불러오지 못했습니다.");
      }
    },
  },
};
</script>

<style scoped>
.full-screen {
  display: flex;
  height: 100vh;
  overflow: hidden; /* 스크롤 없애기 */
}

.content {
  flex-grow: 1;
  padding: 20px;
  position: relative;
}

.theme-map-details {
  position: fixed;
  top: 0px;
  left: 90px;
  z-index: 2;
  background: rgba(255, 255, 255, 0.8);
  padding: 10px;
  border-radius: 20px;
  max-width: 330px;
  width: 100%;
  margin: 20px;
  box-sizing: border-box;
  overflow-y: auto; /* 추가된 부분 */
}

.myplace-list {
  list-style: none;
  padding: 0;
  position: relative;
  z-index: 1;
}

.myplace-item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
  display: flex;
  align-items: center;
  padding: 8px;
}

.myplace-info {
  flex-grow: 1;
}

.myplace-list-container {
  overflow: auto;
  max-height: 650px; /* 스크롤이 나타날 최대 높이 */
}

.add-bookmark-btn {
  background-color: #4caf50;
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
}

.add-bookmark-btn:hover {
  background-color: #45a049;
}

.bookmark-icon {
  margin-right: 5px;
  width: 30px;
}
</style>
