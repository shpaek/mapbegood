<template>
  <div>
    <div id="map"></div>
    <Search @search-results="displayPlacesOnMap" @center-map="centerMap" />
  </div>
</template>

<script>
import Search from "@/components/Search.vue";

export default {
  name: "Map",
  data() {
    return {
      mapContainer: null,
      markers: [],
      map: null,
      ps: null,
      infowindow: null,
      keywordInput: null,
      placesList: null,
      pagination: null,
    };
  },
  components: {
    Search,
  },
  mounted() {
    this.mapContainer = document.getElementById("map");
    this.loadScript();
  },

  methods: {
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "https://dapi.kakao.com/v2/maps/sdk.js?appkey=872b5a083c1af3f5ac36a2d8e87b0790&libraries=services&autoload=false";
      script.onload = () => {
        window.kakao.maps.load(() => this.initialize());
      };
      script.onerror = (error) => {
        console.error("카카오 지도 SDK 로딩 오류:", error);
      };
      document.head.appendChild(script);
    },

    async initialize() {
      try {
        if (!window.kakao || !window.kakao.maps) {
          throw new Error("Kakao Maps API가 로드되지 않았습니다.");
        }

        // 이미 services.Places가 초기화되어 있으므로 바로 사용 가능
        this.ps = window.kakao.maps.services.Places;
        this.infowindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });

        const userLocation = await this.getCurrentLocation();
        this.setInitialMap(userLocation.latitude, userLocation.longitude);
        this.getMyLocation();
      } catch (error) {
        console.error("지도 초기화 오류:", error);
      }
    },

    getCurrentLocation() {
      return new Promise((resolve, reject) => {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(
            (position) => {
              const lat = position.coords.latitude;
              const lng = position.coords.longitude;
              resolve({ latitude: lat, longitude: lng });
            },
            (error) => {
              console.error("현재 위치 가져오기 오류:", error);
              reject(error);
            }
          );
        } else {
          reject("이 브라우저는 위치 정보를 지원하지 않습니다.");
        }
      });
    },

    setInitialMap(latitude, longitude) {
      if (!window.kakao.maps.services || !window.kakao.maps.services.Places) {
        window.kakao.maps.load(() => {
          // Initialize Places service
          window.kakao.maps.services.Places = new window.kakao.maps.services.Places();
          this.ps = window.kakao.maps.services.Places;
          this.infowindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
        });
      } else {
        // Places service is already defined
        this.ps = new window.kakao.maps.services.Places();
        this.infowindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });
      }

      const mapOption = {
        center: new window.kakao.maps.LatLng(latitude, longitude),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(this.mapContainer, mapOption);

      // Add mapTypeControl and zoomControl
      const mapTypeControl = new window.kakao.maps.MapTypeControl();
      const zoomControl = new window.kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
    },

    getMyLocation() {
      this.getCurrentLocation()
        .then((position) => {
          const myLocation = new window.kakao.maps.LatLng(
            position.latitude,
            position.longitude
          );
          this.map.setCenter(myLocation);
        })
        .catch((error) => {
          console.error("Error getting my location:", error);
          alert("현재 위치를 가져올 수 없습니다.");
        });
    },

    centerMap(placePosition) {
      console.log("Centering map at:", placePosition);
      this.map.setCenter(placePosition);
    },

    setZoomLevel(level) {
      if (this.map) {
        this.map.setLevel(level);
      }
    },
    // ... existing code ...

    removeMarkers() {
    if (this.markers && this.markers.length > 0) {
      for (let i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    }
  },
  setMapBounds(bounds) {
    // Map 객체의 중심과 확대 레벨을 설정합니다.
    this.map.setBounds(bounds);
  },

  displayPlacesOnMap(places) {
    const bounds = new window.kakao.maps.LatLngBounds();

    // 기존 마커 제거
    this.removeMarkers();

    // 마커 생성 및 지도에 표시
    places.forEach((place, index) => {
      const placePosition = new window.kakao.maps.LatLng(place.y, place.x);
      const marker = this.addMarker(placePosition, place, index);
      marker.setMap(this.map);

      window.kakao.maps.event.addListener(marker, "click", () => {
        this.centerMap(placePosition);
      });

      bounds.extend(placePosition);
    });

    // 지도의 중심 및 확대 레벨 설정
    this.setMapBounds(bounds);
  },
    

addMarker(position, place, markerIndex) {
  const imageSize = new window.kakao.maps.Size(36, 37);

  if (!this.markers || !Array.isArray(this.markers)) {
    this.markers = [];
  }

  if (place) {
    const placeIndex = (markerIndex % 15) + 1;
    const markerImage = new window.kakao.maps.MarkerImage(
      `https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png#${placeIndex}`,
      imageSize,
      {
        spriteSize: new window.kakao.maps.Size(36, 691),
        spriteOrigin: new window.kakao.maps.Point(0, placeIndex * 46 + 10),
        offset: new window.kakao.maps.Point(13, 37),
      }
    );

    const marker = new window.kakao.maps.Marker({
      position: position,
      image: markerImage,
    });

    this.markers.push(marker);

    return marker;
  }
},

    addBookmark(place) {
      const url = `${this.backURL}/place/${place.id}`;
      axios
        .post(url, {
          placeId: place.id,
          name: place.place_name /* 기타 정보 */,
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
#map {
  width: 100%;
  height: 100vh;
  position: relative;
  overflow: hidden;
  z-index: 1;
}

.my-location-btn {
  position: absolute;
  bottom: 510px;
  right: 0px;
  z-index: 2; /* 맵 보다 위에 나타나도록 설정 */
  cursor: pointer; /* 마우스 커서를 포인터로 변경 */
  border: none;
  background: none;
}
.my-location-btn img {
  user-drag: none; /* 드래그 비활성화 */
  -webkit-user-drag: none; /* 웹킷 브라우저 지원 */                  
  width: 30px; /* 원하는 너비로 조절하세요 */
  height: 30px; /* 원하는 높이로 조절하세요 */
}

</style>
