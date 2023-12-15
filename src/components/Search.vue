<template>
  <div>
    <div id="searchContainer" class="bg_white">
      <div class="search">
        <input type="text" v-model="keyword" placeholder="장소 검색" @keyup.enter="search"/>
        <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" 
          class="icon" @click="search"/>
      </div>
      <ul id="placesList">
        <li v-for="(place, index) in places" :key="index" @click="selectPlace(place)">
          <img class="markerbg" :src="place.markerImage" />
          <div class="info">
            <h5>{{ place.place_name }}</h5>
            <span v-if="place.road_address_name">{{place.road_address_name}}</span>
            <span class="jibun gray" v-if="place.road_address_name">{{place.address_name}}</span>
            <span v-else>{{ place.address_name }}</span>
            <span class="tel" v-if="place.phone">{{ place.phone }}</span>
          </div>
          <!-- <button class="bookmark-btn" @click.stop="addBookmark(place)">북마크</button> -->
          <button @click.stop="customButtonClick">버튼</button>
        
          <img src="/public/images/bookmark.png"  @click.stop="addBookmark(place)"
          class = "bookmark" />
        </li>
      </ul>
      <div id="pagination"></div>
    </div>
  </div>
</template>

<script>
 import axios from "axios";

export default {
  name: "Search",
  data() {
    return {
      ps: null, // Kakao Places Service
      keyword: "",
      places: [], // 검색된 장소 리스트
      clickedPlaceId: null,
      markers: [], // 지도에 표시되는 마커 리스트
      map: null, // 지도 객체를 저장하기 위한 변수
      pagination: null,

    };
  },

  methods: {
    async loadAndInitializeMap() {
      // Kakao 지도 스크립트 로드
      return new Promise((resolve, reject) => {
        const script = document.createElement("script");
        script.src =
          "https://dapi.kakao.com/v2/maps/sdk.js?appkey=872b5a083c1af3f5ac36a2d8e87b0790&libraries=services&autoload=false";
        script.onload = () => {
          window.kakao.maps.load(() => {
            this.initialize();
            resolve();
          });
        };
        script.onerror = (error) => {
          console.error("카카오 지도 SDK 로딩 오류:", error);
          reject(error);
        };
        document.head.appendChild(script);
      });
    },

    async initialize() {
      try {
        if (!window.kakao || !window.kakao.maps) {
          throw new Error("Kakao Maps API가 로드되지 않았습니다.");
        }

        // Places 서비스 초기화
        this.ps = new window.kakao.maps.services.Places();
      } catch (error) {
        console.error("지도 초기화 오류:", error);
      }
    },

    async search() {
      const keyword = this.keyword.trim();
      if (!keyword) {
        alert("검색어를 입력해주세요.");
        return;
      }

      // 스크립트 로드 후 초기화를 수행
      await this.loadAndInitializeMap();

      // Places 서비스가 정상적으로 초기화되었는지 확인
      if (this.ps) {
        this.ps.keywordSearch(keyword, this.placesSearchCB);
      } else {
        alert("Places 서비스가 초기화되지 않았습니다.");
      }
    },
    
    selectPlace(place) {
  // 클릭한 장소의 좌표를 가져옵니다.
  const placePosition = new window.kakao.maps.LatLng(place.y, place.x);

  // 클릭한 장소의 좌표를 기준으로 지도를 중심으로 이동합니다.
  this.$emit("center-map", placePosition);
},
placesSearchCB(data, status, pagination) {
    if (status === window.kakao.maps.services.Status.OK) {
      console.log("Received data:", data);

      this.places = data.map((place, index) => {
        const placeIndex = (index % 15) + 1;
        place.markerImage = `https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png#${placeIndex}`;
        console.log(place.markerImage);
        return place;
      });

      console.log("Places for Map:", this.places);

      // 검색 결과를 Map.vue로 전송
      this.$emit("search-results", this.places);

      this.displayPlacesOnMap(this.places);

      this.pagination = pagination; // pagination을 data에 저장

      this.displayPagination(pagination);
    } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
      alert("검색 결과가 존재하지 않습니다.");
    } else if (status === window.kakao.maps.services.Status.ERROR) {
      alert("검색 결과 중 오류가 발생했습니다.");
    }
  },
  displayPlacesOnMap(places) {
      // 이벤트를 통해 전달받은 장소 데이터를 이용하여 지도에 마커를 표시
      this.$refs.map.displayPlacesOnMap(places);
    },
    displayPagination(pagination) {
      const paginationEl = document.getElementById("pagination");
      this.removeAllChildNodes(paginationEl);

      const fragment = document.createDocumentFragment();

      for (let i = 1; i <= pagination.last; i++) {
        const el = document.createElement("a");
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
          el.className = "on";
        } else {
          el.addEventListener("click", () => {
            pagination.gotoPage(i);
          });
        }

        fragment.appendChild(el);
      }

      paginationEl.appendChild(fragment);
    },

    removeAllChildNodes(el) {
      while (el.firstChild) {
        el.removeChild(el.firstChild);
      }
    },

    addBookmark(place) {
      const url = `${this.backURL}/place/${place.id}`;
      axios
        .post(url, {
          placeId: place.id,
          name: place.place_name,
        })
        .then((response) => {
          console.log("Bookmark added successfully:", response.data);
        })
        .catch((error) => {
          console.error("Error adding bookmark:", error);
        });
    },
  },
};
</script>






<style scoped>
* {
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
}
.markerbg {
  display: inline-block;
  width: 20px;
  height: 20px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
    no-repeat;
  background-size: 36px 320px;
  text-indent: -9999px;
  margin-right: 5px;
}

#placesList {
  list-style: none;
  padding: 0;
  cursor: pointer;
  margin-top: 10px;
  overflow-y: auto;
  max-height: 560px;
}

#placesList li {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
  display: flex;
  align-items: center;
  padding: 7px;
}

#placesList li .markerbg {
  width: 20px;
  height: 20px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
    no-repeat;
  background-size: 27px 320px;
  text-indent: -9999px;
  margin-right: 5px;
}

#placesList li .info {
  flex-grow: 1;
}

#placesList li h5,
#placesList li .info {
  font-size: 12px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  margin: 1;
}

#placesList li .info h5 {
  font-weight: bold; /* h5 요소에 굵은 텍스트를 적용합니다. */
}

#placesList li .info .gray {
  color: #8a8a8a;
}

#placesList li .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
    no-repeat;
  opacity: 0.7; /* 흐릿한 효과를 위해 투명도를 조절합니다. */
}
#placesList li .info .tel {
  color: #009900;
}

#placesList li .markerbg,
#placesList li h5,
#placesList li .info,
#placesList li .info .gray,
#placesList li .info .jibun,
#placesList li .info .tel {
  display: block;
}
.markerbg {
  display: inline-block;
  width: 20px;
  height: 20px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png)
    no-repeat;
  background-size: 36px 320px;
  text-indent: -9999px;
  margin-right: 5px;
}

#placesList li .marker_1 {
  background-position: 0 -10px;
}
#placesList li .marker_2 {
  background-position: 0 -56px;
}
#placesList li .marker_3 {
  background-position: 0 -102px;
}
#placesList li .marker_4 {
  background-position: 0 -148px;
}
#placesList li .marker_5 {
  background-position: 0 -194px;
}
#placesList li .marker_6 {
  background-position: 0 -240px;
}
#placesList li .marker_7 {
  background-position: 0 -286px;
}
#placesList li .marker_8 {
  background-position: 0 -332px;
}
#placesList li .marker_9 {
  background-position: 0 -378px;
}
#placesList li .marker_10 {
  background-position: 0 -423px;
}
#placesList li .marker_11 {
  background-position: 0 -470px;
}
#placesList li .marker_12 {
  background-position: 0 -516px;
}
#placesList li .marker_13 {
  background-position: 0 -562px;
}
#placesList li .marker_14 {
  background-position: 0 -608px;
}
#placesList li .marker_15 {
  background-position: 0 -654px;
}

#myLocation {
  position: absolute;
  bottom: 20px;
  right: 20px;
  z-index: 2;
}
#myLocation img {
  width: 25px; /* 원하는 너비로 조절 */
  height: 25px; /* 원하는 높이로 조절 */
}

#searchContainer {
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
  user-drag: none; /* 드래그 비활성화 */
  -webkit-user-drag: none; /* 웹킷 브라우저 지원 */
  position: absolute;
  width: 17px;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  cursor: pointer;
  z-index: 3;
  pointer-events: none; /* 컨테이너에 대한 이벤트 비활성화 */
}

.search img.icon {
  pointer-events: auto; /* 아이콘에 대한 이벤트 활성화 */
}

/* 검색 이미지를 눌렀을 때 검색 기능 추가 */
.search img:hover {
  background-color: #f2f2f2; /* 마우스 오버 효과 */
}

/* 검색창에 포커스가 갔을 때 테두리 스타일 변경 */
.search input:focus {
  outline: none;
  border-color: #555;
}

.bookmark {
  width: 30px;
  
}

/* .marker-1 {
  background-position: 0 -10px;
}
.marker-2 {
  background-position: 0 -60px;
}
.marker-3 {
  background-position: 0 -110px;
} */
/* Add more marker styles as needed */
</style>

<style>
#pagination {
  margin: 3px 0;
  text-align: center;
}

#pagination a {
  display: inline-block;
  margin: 0 7px;
  text-decoration: none;
  padding: 2px 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
  color: #333;
}

#pagination a.on {
  font-weight: bold;
  cursor: default;
  color: #777;
  background-color: #eee;
  border: 1px solid #ddd;
}

#pagination a:hover {
  background-color: #f2f2f2;
}
</style>
