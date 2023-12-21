<template>
  <!-- 전체화면에 맵 표시 -->
  <DetailMap
ref="detailMap"
:mymapdetail="ourmapdetail"
:places="ourmapdetail.myplaces"
:color="ourmapdetail.themeMapDto.color"
@updateColor="updateColor"
/>
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
      {{ ourmapdetail.themeMapDto.name }}
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
        <p class="card-text">{{ ourmapdetail.themeMapDto.memo }}</p>
      </div>
    </div>
      <div class="myplace-list-container">
        <ul class="myplace-list">
          <li
            v-for="ourplace in ourmapdetail.myplaces"
            :key="ourplace.id"
            class="myplace-item"
            @click="centerMap(place)"
          >
            <div class="myplace-info">
              <h5>{{ ourplace.placeId.placeName }}</h5>
              <p>방문 일자: {{ ourplace.visitedAt }}</p>
              <p>주소: {{ ourplace.placeId.address }}</p>
              <!-- 추가적인 Myplace 정보 표시 -->
            </div>
            <button
              class="add-bookmark-btn"
              @click="cancelBookmark(ourplace)"
            >
              <img
                src="/public/images/bookmark.png"
                class="bookmark-icon"
              />
            </button>
            <router-link
              :to="{
                name: 'ourfeed',
                params: {
                  groupId: groupId,
                  groupThememapId: ourplace.groupThememapId,
                  ourplaceId: ourplace.id,
                },
                query: {
                  placeName: ourplace.placeId.placeName,
                  address: ourplace.placeId.address,
                },
              }"
            >
              <button class = "feed-make">
                <img src ="/public/images/feedmake.png" class = "feed-make">
                </button>
            </router-link>
            <router-link
              :to="{
                name: 'ourfeedcreate',
                params: {
                  groupId: groupId,
                  groupThememapId: ourplace.groupThememapId, // Make sure you have the correct property
                  ourplaceId: ourplace.id,
                },
              }"
            >
            <button class = "feed-btn">
          <img src ="/public/images/feed.png" class = "feed-icon"/>
        </button>
            </router-link>
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
  name: "DetailGroupMap",
  data() {
    return {
      ourmapdetail: {
        themeMapDto: {
          name: "",
          memo: "",
          id: "",
          color: "",
        },
        myplaces: [],
      },
      groupThememapId: null,
    };
  },
  mounted() {
    const groupId = this.$route.params.groupId;
    const groupThememapId = this.$route.params.groupThememapId;
    this.loadThemeMapDetail(groupThememapId);
  },
  methods: {
    async loadThemeMapDetail(groupThememapId) {
      this.groupThememapId = groupThememapId;
      const url = `${this.backURL}/ourmap/get/${groupThememapId}`;
      try {
        const response = await axios.get(url);
        this.ourmapdetail.themeMapDto = response.data;
        console.log("ThemeMapDto:", this.ourmapdetail.themeMapDto);

        this.findAllOurPlace(groupThememapId);
      } catch (error) {
        console.error(error);
        alert(
          error.response.data.message ||
            "테마 맵 세부 정보를 불러오지 못했습니다."
        );
      }
    },
    async findAllOurPlace(groupThememapId) {
      axios
        .get(`http://localhost:8080/ourplace/${groupThememapId}`)
        .then((response) => {
          // API로부터 받은 데이터를 컴포넌트의 데이터에 저장
          this.ourmapdetail.myplaces = response.data;
          // this.firstPlace = response.data[0];
          console.log(response.data);
          this.$refs.detailMap.displayPlacesOnMap(response.data);
        })
        .catch((error) => {
          console.error("Error fetching theme map data:", error);
        });
    },
    async cancelBookmark(ourplace) {
      const ourplaceId = ourplace.id;
      const url = `${this.backURL}/ourplace/${ourplaceId}`;
      try {
        await axios.delete(url, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${localStorage.getItem("mapbegoodToken")}`,
          },
        });
        // After successful deletion, you might want to refresh the data
        await this.findAllOurPlace(this.groupThememapId);
      } catch (error) {
        console.error(error);
        alert(error.response.data.message || "북마크를 취소하지 못했습니다.");
      }
    },
    updateColor(newColor) {
      // This method will be called when the color changes in the child component
      this.firstPlace.thememapId.color = newColor;
    },

    centerMap(place) {
      const placePosition = new window.kakao.maps.LatLng(place.y, place.x);
      this.$emit("center-map", placePosition);
      const level = 3; // 원하는 줌 레벨로 설정
      this.$emit("set-zoom-level", level);
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
top: 100px;
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


.add-bookmark-btn:hover {
background-color: #45a049;
}

.bookmark-icon,
.feed-make,
.feed-icon {
width: 20px; /* Adjust the width as needed */
height: 20px; /* Adjust the height as needed */
/* Add any other styling properties you want */
}

.feed-make,
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