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
      <ul id="placesList">
        <li
          v-for="(place, index) in places"
          :key="index"
          @click="centerMap(place)"
        >
          <img class="markerbg" :src="place.markerImage" />
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
          <button @click="addBookmark(place)">북마크</button>
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
      ps: null,
      keyword: "",
      places: [],
      clickedPlaceId: null,
    };
  },

  methods: {
    async loadScript() {
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
        this.ps = new window.kakao.maps.services.Places();

        // 지도 생성 및 초기화
        this.map = new window.kakao.maps.Map(document.getElementById("map"), {
          // 지도 초기화 옵션 설정
          center: new window.kakao.maps.LatLng(37.566826, 126.9786567),
          level: 3,
        });
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
      await this.loadScript();

      // Places 서비스가 정상적으로 초기화되었는지 확인
      if (this.ps) {
        this.ps.keywordSearch(keyword, this.placesSearchCB);
      } else {
        alert("Places 서비스가 초기화되지 않았습니다.");
      }
    },

    placesSearchCB(data, status, pagination) {
      if (status === window.kakao.maps.services.Status.OK) {
        console.log("Received data:", data);

        this.places = data.map((place, index) => {
          const placeIndex = (index % 15) + 1;
          place.markerImage = `https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png#${placeIndex}`;
          return place;
        });

        console.log("Places for Map:", this.places);
        this.displayPlacesOnMap(this.places);

        this.displayPagination(pagination);
      } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 존재하지 않습니다.");
      } else if (status === window.kakao.maps.services.Status.ERROR) {
        alert("검색 결과 중 오류가 발생했습니다.");
      }
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
    centerMap(place) {
      const placePosition = new window.kakao.maps.LatLng(place.y, place.x);
      this.$emit("center-map", placePosition);
      const level = 3; // 원하는 줌 레벨로 설정
      this.$emit("set-zoom-level", level);
    },

    displayPlacesOnMap(places) {
      const bounds = new window.kakao.maps.LatLngBounds();

      // 마커를 제거하고 초기화
      this.removeMarkers();

      // 마커 생성 및 지도에 표시
      places.forEach((place, index) => {
        const placePosition = new window.kakao.maps.LatLng(place.y, place.x);

        // 마커 생성
        const marker = new window.kakao.maps.Marker({
          position: placePosition,
          // 기존 코드에서 사용한 markerImage 대신 여기에 직접 마커 이미지 URL을 사용하세요
          image: new window.kakao.maps.MarkerImage(
            place.markerImage,
            new window.kakao.maps.Size(36, 37),
            {
              spriteSize: new window.kakao.maps.Size(36, 691),
              spriteOrigin: new window.kakao.maps.Point(0, index * 46 + 10),
              offset: new window.kakao.maps.Point(13, 37),
            }
          ),
        });

        // 지도에 마커 추가
        marker.setMap(this.map);

        // 마커 클릭 시 이벤트 처리
        window.kakao.maps.event.addListener(marker, "click", () => {
          // 클릭한 마커에 대한 로직 추가 (예: 지도 확대 등)
          this.centerMap(place);
        });

        // 지도의 확대 영역 설정
        bounds.extend(placePosition);
      });

      // 지도의 중심과 확대 레벨 설정
      this.setMapBounds(bounds);
    },

    removeMarkers() {
      // this.markers가 정의되었고 배열인지 확인합니다.
      if (this.markers && Array.isArray(this.markers)) {
        this.markers.forEach((marker) => marker.setMap(null));
        this.markers = [];
      }
    },
    setMapBounds(bounds) {
      // Ensure that this.map is defined before calling setBounds
      if (this.map) {
        this.map.setBounds(bounds);
      }
    },

    // 이하 생략
  },
};
</script>
