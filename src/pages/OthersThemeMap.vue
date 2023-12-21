<template>
  <div class="search-wrapper" style="max-width: 600px; margin: auto">
    <h2 class="mt-3" style="margin-left: 10px; font-size: 24px; font-weight: bold;">추천 테마지도</h2>
    <div class="input-group mb-3">
      <input
        v-model="searchTerm"
        placeholder="테마지도 검색하기"
        class="form-control"
        @keyup.enter="executeSearch"
      />
      <div class="input-group-append">
        <!-- <button @click="executeSearch" class="btn btn-dark">검색하기</button> -->
        <button @click="executeSearch" class="btn btn-dark">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="30"
            height="30"
            fill="currentColor"
            class="bi bi-search-heart"
            viewBox="0 0 16 16"
          >
            <path
              d="M6.5 4.482c1.664-1.673 5.825 1.254 0 5.018-5.825-3.764-1.664-6.69 0-5.018Z"
            />
            <path
              d="M13 6.5a6.471 6.471 0 0 1-1.258 3.844c.04.03.078.062.115.098l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1.007 1.007 0 0 1-.1-.115h.002A6.5 6.5 0 1 1 13 6.5ZM6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11Z"
            />
          </svg>
        </button>
      </div>
    </div>

    <div v-if="themeMaps.length > 0">
      <!-- 검색 결과가 있는 경우 -->
      <ul
      class="elevated-list search-results-list mb-4"
      v-for="themeMap in themeMaps"
      :key="themeMap.id"
    >
        <!-- 각 테마맵에 대한 목록 -->
        <li class="list-group-item" @click="showDetails(themeMap.id)">
        <div>
          <h5 class="mb-1" style="font-weight: bold;">제목: {{ themeMap.name }}</h5>
          <p class="mb-1">내용: {{ themeMap.memo }}</p>
            <!-- <small>{{ themeMap.id }}</small> -->

            <!-- 추가하기 버튼 -->
            <button
              @click="addToFavorites(themeMap.id)"
              class="btn btn-dark"
              :disabled="isInFavorites(themeMap.id)"
            >
              추가하기
            </button>

            <!-- 중복 여부 확인 메시지 -->
            <span v-if="isInFavorites(themeMap.id)" class="text-danger ml-2">
              이미 추가된 list입니다.
            </span>
         
          </div>
        </li>
      </ul>
    </div>

    <div v-else class="alert alert-warning mt-3 text-center" role="alert">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="30"
        height="30"
        fill="currentColor"
        class="bi bi-exclamation-circle-fill text-warning mb-2"
        viewBox="0 0 16 16"
      >
        <path
          d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zM7 4a.5.5 0 0 1 1 0v4a.5.5 0 0 1-1 0V4zm.5 6a.5.5 0 0 1 0 1h-1a.5.5 0 0 1 0-1h1z"
        />
      </svg>
      <p class="mb-0">검색 결과가 없습니다.</p>
    </div>
  </div>
  <div class="m-part">
    <Detailmap :mymapdetail="themeMapDetail" />
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import Detailmap from "./Detailmap.vue";
import Swal from "sweetalert2";
export default {
  computed: {
    ...mapState(["userInfo"]),
  },
  components: {
    Detailmap,
  },
  created() {
    // this.$store.dispatch("getUserInfo").then() //=> {
    // 사용자 정보가 설정되면 이메일 값을 콘솔에 출력합니다.
    // console.log("이메일:", this.userInfo.email);
    // });
    this.$store.dispatch("getUserInfo").then(() => {
      //구독 메서드 호출
      this.checkNotifications();
    });

    this.recommend(this.recommendPageNum);
  },

  data() {
    return {
      // 검색어와 테마맵 목록을 담는 데이터
      searchTerm: "",
      themeMaps: [],
      recommendPageNum: 1,
    };
  },
  methods: {
    // 테마맵 검색 메서드
    async searchThemeMap() {

      
      try {
        const url = `${this.backURL}/maplist/search`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;

        const response = await axios.get(url, {
          params: {
            name: this.searchTerm,
          },
        });
        console.log(response.data);
        this.themeMaps = response.data.map((map) => ({
          ...map,
          isInFavorites: false,
        }));
      } catch (error) {
        console.error("검색 중 오류 발생:", error);
        this.themeMaps = [];
      }
    },
    executeSearch() {
      // 검색 메서드 호출
      this.searchThemeMap();
      //구독 메서드 호출
      this.checkNotifications();
    },

    async showDetails(themeMapId) {
      try {
        // 클릭한 테마맵ID와 연결된 내 장소 목록 가져오기
        const url = `${this.backURL}/myplace/${themeMapId}`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;

        const response = await axios.get(url);
        const myPlaces = response.data;

        // 여기에서 myPlaces 데이터를 활용하여 내 장소 목록을 표시하도록 구현할 수 있습니다.
        console.log('내 장소 목록:', myPlaces);

        this.themeMapDetail = {
          themeMap: response.data,
          myPlaces,
        };

        // 예시: 특정 동작 수행을 위해 가져온 내 장소 목록 활용
        // this.performSomeActionWithMyPlaces(myPlaces);
      } catch (error) {
        console.error("내 장소 목록을 가져오는 중 오류 발생:", error);
      }
    },

    
    // sub 호출 메서드
    async checkNotifications() {
      try {
        // 토큰 먼저 가져오는지 보기
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        if (!accessToken) {
          console.log("사용자가 로그인하지 않았습니다.");
          return;
        }

        // 로그인되면 구독 하기
        const url = `${this.backURL}/notifications/subs/${this.userInfo.email}`;
        axios.defaults.headers.common["Authorization"] = accessToken;

        // const response = await axios.get(url,{
        //   withCredentials:true,
        // });

        this.$sse
          .create({ url: url, withCredentials: true })
          .on("sse", (data) => {
            console.log("Message:", data);
            console.log("데이터 타입", data.type, data.type !== "Created");
            //로그인 상태에서 구독했을때에도 알림창이 띄어지는데, 급한대로 이름으로 무시하기, 만들때는 data.type 꼭 주자.
            // alert("알림:" + data);
            Swal.fire({ text: "알림:" + data, icon: "info" });
            //  // data.type이 정의되어 있을 때만 알림창 띄우기
            //  if (data.type !== undefined && data.type !== 'Created') {
            //     alert('알림:' + data);
            // }
          })
          .on("error", (err) =>
            console.error("Failed to parse or lost connection:", err)
          )
          .connect()
          .catch((err) =>
            console.error("Failed make initial connection:", err)
          );
      } catch (error) {
        console.log("알림 확인 중 오류 발생 :", error);
      }
    },

    async detailThememap(themeMapId) {
      try {
        // 클릭한 테마맵ID와 연결된 장소에 대한 자세한 정보를 가져오기
        const url = `${this.backURL}/maplist/details/${themeMapId}`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;

        const response = await axios.get(url);
        const detailedPlaces = response.data; // API가 자세한 장소 정보를 반환한다고 가정합니다

        // 자세한 장소 정보를 콘솔에 기록하기
        console.log('자세한 장소 정보:', detailedPlaces);

        // 여기에서 detailedPlaces 데이터를 활용하여 상세 정보를 표시하도록 구현할 수 있습니다.
        // 예를 들어, 모달 창이나 다른 컴포넌트를 사용하여 상세 정보를 표시할 수 있습니다.
      } catch (error) {
        console.error("자세한 정보를 가져오는 중 오류 발생:", error);
      }
    },
    // 즐겨찾기에 추가 메서드
    async addToFavorites(themeMapId) {
      try {
        const url = `${this.backURL}/favorite/create/${themeMapId}`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;

        console.log("검색어:", this.searchTerm); // 디버깅을 위한 로그
        const response = await axios.post(url);
        console.log(response.data); // 성공하면 콘솔에 출력
        // 추가 성공 메시지
        Swal.fire({ text: "즐겨찾기에 추가되었습니다", icon: "success" })

        // Update the isInFavorites property after successfully adding to favorites
        const updatedThemeMaps = this.themeMaps.map((map) => {
          if (map.id === themeMapId) {
            return { ...map, isInFavorites: true };
          }
          return map;
        });
        this.themeMaps = updatedThemeMaps;
      } catch (error) {
        console.error("즐겨찾기 추가 중 오류 발생:", error);
      }
    },

    // themeMapId가 현재 사용자의 즐겨찾기 목록에 있는지 확인
    isInFavorites(themeMapId) {
      return this.themeMaps.some(
        (map) => map.id === themeMapId && map.isInFavorites
      );
    },

    recommend(pageNum) {
      axios
        .get(`${this.backURL}/recommend-thememap/` + pageNum)
        .then((res) => {
          console.log(res.data);
          this.themeMaps = res.data.map((map) => ({
            ...map,
            isInFavorites: false,
          }));
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
<style>
.mb-1{

}
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

ul.elevated-list {
  list-style-type: none;
  padding: 0;
  border: 2px solid #4e4e52;
  border-radius: 10px;
  margin: 20px 0;
  background-color: #f8f9fa;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

ul.elevated-list li {
  border-bottom: 1px solid #4e4e52;
  padding: 10px;
}

ul.elevated-list li:hover {
  background-color: #e9ecef7a; 
  cursor: pointer; 
}

ul.search-results-list li:hover {
  background-color: #e9ecef; 
  cursor: pointer; 
} 

ul.elevated-list li:last-child {
  border-bottom: none;
}

/* 새로운 스타일 추가: 검색 결과 목록에 선을 추가 */
ul.search-results-list li {
  border-bottom: 1px solid #4e4e52;
  padding: 10px;
}

/* 마지막 검색 결과 목록 아이템에는 선을 표시하지 않음 */
ul.search-results-list li:last-child {
  border-bottom: none;
}
/* 새로운 스타일 추가: 검색 결과 목록의 각 리스트 아이템을 독립적으로 표시 */
ul.search-results-list li {
  padding: 10px;
  border-bottom: none;
}
</style>