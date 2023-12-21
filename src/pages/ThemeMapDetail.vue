<template>
  <!-- 전체화면에 맵 표시 -->
  <DetailMap
    ref="detailMap"
    :mymapdetail="mymapdetail"
    :places="mymapdetail.myplaces"
    :color="mymapdetail.themeMapDto.color"
  />
  <!-- <Map /> -->
  <div class="theme-map-details">
    <h2 class="display-4" style="font-size: 2rem; font-weight: bold">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="16"
        height="16"
        fill="currentColor"
        class="bi bi-caret-right-fill"
        viewBox="0 0 16 16"
      >
        <path
          d="m12.14 8.753-5.482 4.796c-.646.566-1.658.106-1.658-.753V3.204a1 1 0 0 1 1.659-.753l5.48 4.796a1 1 0 0 1 0 1.506z"
        />
      </svg>
      {{ mymapdetail.themeMapDto.name }}
    </h2>

    <div class="card">
      <div class="card-body">
        <h5 class="card-title text-warning">
          <svg
          
            xmlns="http://www.w3.org/2000/svg"
            width="14"
            height="14"
            fill="currentColor"
            class="bi bi-chat-left-text"
            viewBox="0 0 16 16"
          >
            <path
              d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"
            />
            <path
              d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6zm0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"
            />
          </svg>
          내용:
        </h5>
        <p class="card-text">{{ mymapdetail.themeMapDto.memo }}</p>
      </div>
    </div>
    <!--<p>아이디(가릴예정): {{ mymapdetail.themeMapDto.id }}</p>-->

    <div class="myplace-list-container">
      <ul class="myplace-list">
        <li v-for="myplace in mymapdetail.myplaces" :key="myplace.id">
          <div class="myplace-item">
            <div class="myplace-info">
              <h5>{{ myplace.placeId.placeName }}</h5>
              <p>방문 일자: {{ myplace.visitedAt }}</p>
              <p>주소: {{ myplace.placeId.address }}</p>
              <!-- 추가적인 Myplace 정보 표시 -->
            </div>
            <button
              class="add-bookmark-btn"
              @click="cancelBookmark(myplace.id)"
            >
              <img src="/public/images/bookmark.png" class="bookmark-icon" />
            </button>

            <button
              type="button"
              class="more"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="15"
                height="15"
                fill="currentColor"
                class="bi bi-three-dots"
                viewBox="0 0 16 16"
              >
                <path
                  d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3"
                />
              </svg>
            </button>

            <ul class="dropdown-menu">
              <li>
                <router-link
                  :to="{
                    name: 'myfeed',
                    params: {
                      myplaceId: myplace.id,
                      id: mymapdetail.themeMapDto.id,
                    },
                    query: {
                      placeName: myplace.placeId.placeName,
                      address: myplace.placeId.address,
                      visitedAt: myplace.visitedAt,
                    },
                  }"
                >
                  <button
                    class="feed-button"
                    @click="checkAndDisplayFeed(myplace.id)"
                  >
                    피드 보기
                  </button>
                </router-link>
              </li>
              <li>
                <router-link
                  :to="{
                    name: 'myfeedcreate',
                    params: {
                      myplaceId: myplace.id,
                      id: mymapdetail.themeMapDto.id,
                    },
                    query: {
                      placeName: myplace.placeId.placeName,
                      address: myplace.placeId.address,
                      visitedAt: myplace.visitedAt,
                    },
                  }"
                >
                  <button>피드 생성</button>
                </router-link>
              </li>
            </ul>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import DetailMap from "./Detailmap.vue";
// import Map from "./Map.vue";
export default {
  components: {
    DetailMap,
    // Map
  },
  data() {
    return {
      mymapdetail: {
        themeMapDto: {
          name: "",
          memo: "",
          id: "",
          color: "",
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

        this.$refs.detailMap.displayPlacesOnMap(response.data);
      } catch (error) {
        console.error(error);
        alert(error.response.data.message || "Myplaces를 불러오지 못했습니다.");
      }
    },

    async cancelBookmark(myplaceId) {
      const url = `${this.backURL}/myplace/${myplaceId}`;
      try {
        await axios.delete(url, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("mapbegoodToken")}`,
          },
        });
        // After successful deletion, you might want to refresh the data
        this.findAllMyPlace(this.mymapdetail.themeMapDto.id);
      } catch (error) {
        console.error(error);
        alert(error.response.data.message || "북마크를 취소하지 못했습니다.");
      }
    },
  },
  async checkAndDisplayFeed(myplaceId) {
    const url = `${this.backURL}/myfeed/${myplaceId}`;
    try {
      const response = await axios.get(url, {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${localStorage.getItem("mapbegoodToken")}`,
        },
      });
      const feedExists = response.data && response.data.length > 0;

      if (feedExists) {
        // 피드가 있는 경우 피드 보기 페이지로 이동
        this.$router.push({
          name: "myfeed",
          params: {
            myplaceId: myplaceId,
            id: this.mymapdetail.themeMapDto.id,
          },
          query: {
            placeName: this.myplace.placeId.placeName,
            address: this.myplace.placeId.address,
            visitedAt: this.myplace.visitedAt,
          },
        });
      } else {
        // 피드가 없는 경우 알림 표시
        alert("저장된 피드가 없습니다!");
      }
    } catch (error) {
      console.error(error);
      alert("피드를 확인하지 못했습니다.");
    }
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
  top: 00px;
  left: 80px;
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
  padding: 10px;
}

.myplace-info {
  flex-grow: 1;
}

.myplace-list-container {
  overflow: auto;
  max-height: 650px; /* 스크롤이 나타날 최대 높이 */
}

.add-bookmark-btn {
  color: #fff;
  border: none;
  padding: 8px 16px;
  border-radius: 10px;
  cursor: pointer;
}

/* .add-bookmark-btn:hover {
  background-color: #0077ff;
} */
.bookmark-icon,
.feed-icon {
  width: 20px; /* Adjust the width as needed */
  height: 20px; /* Adjust the height as needed */
  /* Add any other styling properties you want */
}

.add-bookmark-btn,
.feed-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px; /* Adjust the margin as needed */
}

.search {
  position: relative;
  width: calc(100% - 40px);
  margin: 0 auto 10px;
}
.dropdown-menu {
  position: absolute;
  top: 120%; /* Adjust as needed */
  right: 0;
  z-index: 1000;
  display: none;
  float: left;
  min-width: 5rem;
  padding: .5rem 0;
  margin: .125rem 0 0;
  font-size: 1rem; /* Corrected from "rem" to "1rem" */
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff; /* Background color of the dropdown */
  border: 2px solid rgba(0, 0, 0, 0.125);
  border-radius: 1.25rem;
  box-shadow: 0 .5rem 1rem rgba(0, 0, 0, 0.175); /* Box shadow for the dropdown */
  transform-origin: top right; /* Adjust as needed */
}

.dropdown-menu.show {
  display: block;
}

.dropdown-menu button {
  width: 100%;
  text-align: left;
  padding: 0.25rem 1.5rem;
  clear: both;
  font-weight: 400;
  color: #212529;
  text-decoration: none;
  white-space: nowrap;
  background-color: transparent;
  border: 0;
  cursor: pointer;
}
.dropdown-menu button:hover,
.dropdown-menu button:focus {
  background-color: #f8f9fa; /* Hover color */
}

.search input {
  width: 100%;
  border: 2px solid #bbb;
  border-radius: 20px;
  padding: 10px;
  font-size: 16px;
  box-sizing: border-box;
  z-index: 2;
}

.search img {
  user-drag: none;
  -webkit-user-drag: none;
  position: absolute;
  width: 17px;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  cursor: pointer;
  z-index: 3;
  pointer-events: none;
}

.search img.icon {
  pointer-events: auto;
}

.search img:hover {
  background-color: #f2f2f2;
}

.search input:focus {
  outline: none;
  border-color: #555;
}
</style>
