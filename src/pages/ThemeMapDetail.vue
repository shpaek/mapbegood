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
    <h2 class="display-4" style="font-size: 2rem; fonr-weight: bold">
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
        <h5 class="card-title" text-warning>
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
        <li
          v-for="myplace in mymapdetail.myplaces"
          :key="myplace.id"
          class="myplace-item"
        >
          <div class="myplace-info">
            <h5>{{ myplace.placeId.placeName }}</h5>
            <!-- 방문일자가 null이 아닌 경우에만 표시 -->
            <p v-if="myplace.visitedAt">방문 일자: {{ myplace.visitedAt }}</p>
            <p>주소: {{ myplace.placeId.address }}</p>
                    
            <!-- 추가적인 Myplace 정보 표시 -->
          </div>
          <button class="add-bookmark-btn" @click="cancelBookmark(myplace.id)">
            <img src="/public/images/bookmark.png" class="bookmark-icon" />          
          </button>
          <router-link
            v-if="myplace.feed"
            :to="{
              name: 'myfeed',
              params: {
                myplaceId: myplace.id,
                id: themeMapId,
              },
              query: {
                placeName: myplace.placeId.placeName,
                address: myplace.placeId.address,
                visitedAt: myplace.visitedAt
              },
            }"
          >
            <button class = "feed=btn">
              <img src ="/public/images/feed.png" class = "feed-icon"/>

            </button>
          </router-link>
          <router-link
            v-else
            :to="{
              name: 'myfeedcreate',
              params: {
                myplaceId: myplace.id,
                id: themeMapId,
              },
              query: {
                placeName: myplace.placeId.placeName,
                address: myplace.placeId.address,
                visitedAt: myplace.visitedAt
              },
            }"
          >
          <button>피드생성</button>
        </router-link>

        <span>방문일자</span>
        <input type="date" @input="updateVisitedAt(myplace.id)" v-model="selectedVisitedDate">
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
      selectedVisitedDate: null, // 이 부분을 추가
      themeMapId:null,
    };
  },
  mounted() {
    // 라우터에서 테마맵 ID를 받아와서 상세 정보를 불러옵니다.
    const themeMapId = this.$route.params.id;
    this.loadThemeMapDetail(themeMapId);
  },
  methods: {
    async loadThemeMapDetail(themeMapId) {
      this.themeMapId=themeMapId;
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

   
    // 방문일자를 선택한 경우, selectedVisitedDate에 값이 들어감
    // 이 값을 서버로 전송하거나 다른 처리를 수행할 수 있음
    updateVisitedAt(myplaceId) {
      this.myplaceId=myplaceId;
  if (  myplaceId && this.selectedVisitedDate) {
    // 서버로 전송하기 전에 형식을 변경
    // const formattedDate = new Date(this.selectedVisitedDate)
    // console.log("Updating visited date:", this.selectedVisitedDate);
    this.addVisitedAt(myplaceId,  this.selectedVisitedDate); // 수정된 부분
  }
},

async addVisitedAt(myplaceId, visitedAt) {
  const url = `${this.backURL}/myplace/${myplaceId}`;
  try {
    await axios.put(
      url,
      {
        // thememapId: this.themeMapId , // 숫자를 문자열로 변환
        // placeId: myplaceId,
        id :  myplaceId,
        visitedAt: visitedAt, // 수정된 부분
      },
      {
        withCredentials: true,
        headers: {
          Authorization: `Bearer ${localStorage.getItem("mapbegoodToken")}`,
          'Content-Type': 'application/json',
        },
      }
    );
    // 성공적으로 업데이트한 후에 Myplaces를 다시 불러올 수 있도록 처리
    this.findAllMyPlace(this.mymapdetail.themeMapDto.id);
  } catch (error) {
    console.log(error.response.data);
    alert(error.response.data.message || "생성일자 추가하지 못했습니다.");
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
  top: 00px;
  left: 80px;
  z-index: 2;
  background: rgba(255, 255, 255, 0.8);
  padding: 10px;
  border-radius: 20px;
  max-width: 540px;
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
