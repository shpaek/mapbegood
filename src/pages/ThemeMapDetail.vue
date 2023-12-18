<template>
  <!-- 전체화면에 맵 표시 -->
  <DetailMap ref="detailMap" :mymapdetail="mymapdetail" :places="mymapdetail.myplaces" />

  <div class="theme-map-details">
    <h1>{{ mymapdetail.themeMapDto.name }}</h1>
    <p>내용(없어도될듯): {{ mymapdetail.themeMapDto.memo }}</p>
    <p>아이디(가릴예정): {{ mymapdetail.themeMapDto.id }}</p>

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
import DetailMap from "./Detailmap.vue"; 

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
        this.mymapdetail = {
          themeMapDto: response.data,
        };
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
      const myPlacesUrl = `${this.backURL}/myplace/${themeMapId}`;
      try {
        const response = await axios.get(myPlacesUrl, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("mapbegoodToken")}`,
          },
        });
        this.mymapdetail.myplaces = response.data;
        // Call the method in the child component
        this.$refs.detailMap.displayPlacesOnMap(response.data);
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
