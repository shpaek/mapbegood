<template>
  <div class="search-wrapper" style="max-width: 600px; margin: 0 auto">
    <div style="display:flex;">
      <a href="/othersthememap" style="color:#000;text-decoration:none;margin-top:25px;margin-bottom:30px;">
        <span class="theme-list" style="font-weight:bold;font-size:25px;margin-left:30px;padding-top:15px;">추천 테마지도</span>
      </a>
    </div>
    <div>
      <div class="input-group mb-3" style="width:370px;padding-left:20px;">
        <input v-model="searchTerm" placeholder="테마지도 검색하기" class="form-control"
          @keyup.enter="executeSearch" style="margin-left: 5px"/>
        <div class="input-group-append" style="display: flex;">
          <!-- <button @click="executeSearch" class="btn btn-dark">검색하기</button> -->
          <span @click="executeSearch" class="btn btn-dark" style="margin-right: 5px;padding:2px 7px 0 7px;">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
              <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
            </svg>
          </span>
        </div>
      </div>
    </div>

    <div v-if="themeMaps.length > 0" style=" position: absolute; width: 390px; height: 85vh; overflow-y: auto; overflow-x: hidden;">
      <!-- 검색 결과가 있는 경우 -->
      <ul class="list-group">
        <!-- 각 테마맵에 대한 목록 -->
        <li v-for="themeMap in themeMaps" :key="themeMap.id" @click="showDetails(themeMap.id)"
            class="thememap">
          <div class="info">
            <div class="name">{{ themeMap.name }}</div>
            <div class="memo">{{ themeMap.memo }}</div>
            <p v-show="themeMap.memo == null" class="mb-1">&nbsp;</p>
            <!-- <small>{{ themeMap.id }}</small> -->
          </div>
          <!-- 추가하기 버튼 -->
          <span @click="addToFavorites(themeMap.id)" class="star" v-show="!isInFavorites(themeMap.id)">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16"
                  style="cursor: pointer;">
              <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
            </svg>
            <!-- <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16"
            style="cursor: pointer;">
              <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
            </svg> -->
          </span>
        </li>
      </ul>
    </div>

    <div v-else class="alert alert-warning mt-3 text-center" role="alert"
      style="width: 370px; margin: auto">
      <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor"
        class="bi bi-exclamation-circle-fill text-warning mb-2" viewBox="0 0 16 16">
        <path d="M8 0a8 8 0 1 0 0 16A8 8 0 0 0 8 0zM7 4a.5.5 0 0 1 1 0v4a.5.5 0 0 1-1 0V4zm.5 6a.5.5 0 0 1 0 1h-1a.5.5 0 0 1 0-1h1z"/>
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
  state: {
  themeMapDetail: null,
},
mutations: {
  setThemeMapDetail(state, payload) {
    state.themeMapDetail = payload;
  },
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
    const url = `${this.backURL}/myplace/${themeMapId}`;
    const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
    axios.defaults.headers.common["Authorization"] = accessToken;

    const response = await axios.get(url);
    const myPlaces = response.data;

    // 스토어에 themeMapDetail 데이터 설정
    this.$store.commit("setThemeMapDetail", {
      themeMap: response.data,
      myPlaces,
    });

    // themeMapId를 매개변수로하여 상세 보기로 리디렉션
    this.$router.push({
      name: "otherthememapdetail",
      params: { id: themeMapId },
    });

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
        console.log("자세한 장소 정보:", detailedPlaces);

        // 여기에서 detailedPlaces 데이터를 활용하여 상세 정보를 표시하도록 구현할 수 있습니다.
        // 예를 들어, 모달 창이나 다른 컴포넌트를 사용하여 상세 정보를 표시할 수 있습니다.
      } catch (error) {
        console.error("자세한 정보를 가져오는 중 오류 발생:", error);
      }
    },
    // 즐겨찾기에 추가 메서드
    async addToFavorites(themeMapId) {
      if (this.isInFavorites(themeMapId)) {
        alert("이미 추가된 테마지도 입니다.");
        return;
      }
      try {
        const url = `${this.backURL}/favorite/create/${themeMapId}`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;

        // console.log("검색어:", this.searchTerm); // 디버깅을 위한 로그
        const response = await axios.post(url);
        // console.log(response.data); // 성공하면 콘솔에 출력
        // 추가 성공 메시지
        Swal.fire({ text: "즐겨찾기에 추가되었습니다", icon: "success" });

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
          // console.log(res.data);
          this.themeMaps = res.data.map((map) => ({
            ...map,
            isInFavorites: false,
          }));
        })
        .catch((err) => {
          console.log(err);
        });
    },
    reCommendDeatail() {
      console.log("테마지도 상세 페이지");
    },
  },
};
</script>
<style>

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
  margin-top: 15px;
}
span.star>svg:hover{
  fill:rgba(250, 206, 60, 0.767);
}
.m-part {
  position: absolute;
  left: 454px; 
  right: 0; 
  height: 100%;
}

.search-wrapper {
  position: absolute;
  width: 390px;
  height: 100vh;
}
/* 

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

ul.search-results-list li {
  border-bottom: 1px solid #4e4e52;
  padding: 10px;
}

ul.search-results-list li:last-child {
  border-bottom: none;
}
ul.search-results-list li {
  padding: 10px;
  border-bottom: none;
}
*/
</style>