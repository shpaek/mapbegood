<template>
  <div>
    <div id="searchContainer" class="bg_white">
      <div class="search">
        <input
          type="text"
          v-model="keyword"
          placeholder="장소 검색"
          @keyup.enter="search"
        />
        <img
          src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png"
          class="icon"
          @click="search"
        />
      </div>

      <!-- 장소 목록 -->
      <ul id="placesList">
        <!-- @click="selectPlace(place, index)" -->
        <li v-for="(place, index) in places" :key="index" @click="centerMap(place)">
          <!-- <img class="markerbg" :src="getMarkerImageUrl(index)" /> -->
          <span class="indexNum">{{index+1}}</span>
          <div class="info">
            <h5>{{ place.place_name }}</h5>
            <span v-if="place.road_address_name">{{
              place.road_address_name
            }}</span>
            <span class="jibun gray" v-if="place.road_address_name">{{
              place.address_name
            }}</span>
            <span v-else>{{ place.address_name }}</span>
            <span class="tel" v-if="place.phone">{{ place.phone }}</span>
          </div>
          <img
            src="/public/images/bookmark.png"
            @click="openModal(place)"
            class="bookmark"
          />  
        </li>
      </ul>

      <!-- 페이지네이션 -->
      <div id="pagination"></div>
    </div>

    <!-- 모달 창 구조 -->
    <div class="modal-container">
      <!-- 모달창 -->
      <AddBookmark
        :isModalOpen="isModalOpen"
        @close-modal="closeModal"
        @add-myplace="addMyplace"
        :groupId="groupId"
        :place="selectedPlace"
      />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import AddBookmark from "../pages/AddBookmark.vue";
import Swal from "sweetalert2";
export default {
  name: "Search",

  components: {
    AddBookmark,
  },

  data() {
    return {
      ps: null, // Kakao Places Service
      keyword: "",
      places: [], // 검색된 장소 리스트
      clickedPlaceId: null,
      markers: [], // 지도에 표시되는 마커 리스트
      map: null, // 지도 객체를 저장하기 위한 변수
      pagination: null,
      isModalOpen: false,
      
    };
  },
  props: {
    getMarkerImageUrl: Function, // Receive the method as a prop
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

    getMarkerImageUrl(index) {
      // 마커 이미지 URL을 설정하는 부분을 수정
      const markerColors = [
        "blue",
        "red",
        "yellow",
        "green",
        "purple",
        "gray",
        "orange",
        "pink",
        "cyan",
        "brown",
        "navy",
        "olive",
        "lime",
        "teal",
        "indigo",
      ];
      const placeIndex = index % markerColors.length;
      return `https://example.com/markers/${markerColors[placeIndex]}.png`;
    },

    centerMap(place) {
      const placePosition = new window.kakao.maps.LatLng(place.y, place.x);
      this.$emit("center-map", placePosition);
      const level = 3; // 원하는 줌 레벨로 설정
      this.$emit("set-zoom-level", level);
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
        Swal.fire({ text: "검색어를 입력해주세요.", icon: "warning" });
        return;
      }

      // 스크립트 로드 후 초기화를 수행
      await this.loadAndInitializeMap();

      // Places 서비스가 정상적으로 초기화되었는지 확인
      if (this.ps) {
        this.ps.keywordSearch(keyword, this.placesSearchCB);
      } else {
        Swal.fire({ text: "Places 서비스가 초기화되지 않았습니다.", icon: "warning" });
      }
    },

    selectPlace(place, index) {
      console.log("선택한 장소:", place, "인덱스:", index);
      const placePosition = new window.kakao.maps.LatLng(place.y, place.x);

      // 클릭한 장소의 좌표를 기준으로 지도를 중심으로 이동합니다.
      this.$emit("center-map", placePosition);

      // Emit a custom event with the selected place, index, and marker image URL
      // const markerImageUrl = this.getMarkerImageUrl(index);
      this.$emit("place-selected", { place, index, markerImageUrl });
    },

    // getMarkerImageUrl(markerIndex) {
    //   const placeIndex = (markerIndex % 15) + 1;
    //   return `https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png#${placeIndex}`;
    // },

    placesSearchCB(data, status, pagination) {
  if (status === window.kakao.maps.services.Status.OK) {
    this.places = data.map((place, index) => {
      return { ...place, markerImage: index }; // Set markerImage with index
    });

        // 검색 결과를 Map.vue로 전송
        this.$emit("search-results", this.places);

        this.pagination = pagination; // pagination을 data에 저장

        this.displayPagination(pagination);
      } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
        Swal.fire({ text: "검색 결과가 존재하지 않습니다.", icon: "warning" });
      } else if (status === window.kakao.maps.services.Status.ERROR) {
        Swal.fire({ text: "검색 중 오류가 발생했습니다.", icon: "error" });
      }
    },

    displayPlacesOnMap(places) {
      const bounds = new window.kakao.maps.LatLngBounds();

      // 기존 마커 및 목록 제거
      this.removeMarkers();
      this.removeAllPlacesListItems();

      // 마커 생성 및 지도에 표시
      places.forEach((place, index) => {
        const placePosition = new window.kakao.maps.LatLng(place.y, place.x);
        const marker = this.addMarker(placePosition, place, index);
        marker.setMap(this.map);

        window.kakao.maps.event.addListener(marker, "click", () => {
          this.centerMap(placePosition);
        });

        bounds.extend(placePosition);

        // 목록에 추가
        this.displayListItem(place); // 주석처리 되어 있지 않음
      });

      // 지도의 중심 및 확대 레벨 설정
      this.setMapBounds(bounds);
    },

    displayListItem(place) {
      const listEl = document.getElementById("placesList");
      const itemEl = this.getListItem(place);

      listEl.appendChild(itemEl);
    },

    getListItem(place) {
      const el = document.createElement("li");
      const itemStr = `<span class="markerbg marker_1"></span>
    <div class="info">
      <h5>${place.place_name}</h5>
      <span>${place.road_address_name}</span>
      <span class="jibun gray">${place.address_name}</span>
      <span class="tel">${place.phone}</span>
    </div>`;

      el.innerHTML = itemStr;
      el.className = "item";

      // 이벤트 리스너 추가
      el.addEventListener("mouseover", () => {
        this.displayInfowindow(place);
      });

      el.addEventListener("mouseout", () => {
        this.infowindow.close();
      });

      return el;
    },

    removeAllPlacesListItems() {
      const listEl = document.getElementById("placesList");
      listEl.innerHTML = "";
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

    openModal(place) {
      // 모달 창 열기
      this.isModalOpen = true;
      // 선택된 장소(place) 정보를 이용하여 모달 창 내용 업데이트 가능
      this.selectedPlace = place;
    },
    closeModal() {
      // 모달 창 닫기
      this.isModalOpen = false;
    },
  },
};
</script>

<style scoped>
* {
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
}


#searchContainer {
  position: fixed;
  top: 0px;
  left: 902px;
  z-index: 2;
  background: rgba(255, 255, 255, 0.8);
  padding: 10px;
  border-radius: 20px;
  max-width: 330px;
  width: 100%;
  margin: 20px;
  box-sizing: border-box;
  overflow-y: auto;  
}
 
</style>

<style scoped>
* {
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
}
 

 
#placesList li .info .gray {
  color: #8a8a8a;
}
 

#placesList li .markerbg,
#placesList li h5,
#placesList li .info,
#placesList li .info .gray,
#placesList li .info .jibun,
#placesList li .info .tel {
  display: block;
}

#placesList {
  list-style: none;
  padding: 0;
  cursor: pointer;
  margin-top: 10px;
  overflow-y: auto;
  max-height: 660px;
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
  font-weight: bold;
}

#placesList li .info .gray {
  color: #8a8a8a;
}

#placesList li .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png)
    no-repeat;
  opacity: 0.8;
}

#placesList li .info .tel {
  color: #009900;
}

#searchContainer {
  position: fixed;
  top: 0px;
  left: 90px;
  z-index: 2;
  max-width: 330px;
  width: 100%;
  box-sizing: border-box;
  overflow-y: auto;
  transition: height 0.5s ease; /* Add transition for smooth animation */
  /* max-height: 660px; */
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

.bookmark {
  width: 30px;
}
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

#placesList li .indexNum {
  display: inline-block;
  width: 25px;
  height: 25px;
  background-color: #3498db; 
  color: #fff;
  text-align: center;
  line-height: 25px;
  border-radius: 50%;
  margin-right: 10px;
  font-size: 14px;
}

#placesList li .indexNum {
  font-weight: bold;
}
</style>