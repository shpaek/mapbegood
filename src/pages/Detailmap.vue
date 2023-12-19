<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "DetailMap",
  props: {
    mymapdetail: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      map: null,
      markers: [],
    };
  },
  mounted() {
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

        const userLocation = await this.getCurrentLocation();
        this.setInitialMap(userLocation.latitude, userLocation.longitude);

        // Check if mymapdetail is defined before calling displayPlacesOnMap
        if (this.mymapdetail) {
          // Display markers on the map
          this.displayPlacesOnMap();
        }
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
      const mapOption = {
        center: new window.kakao.maps.LatLng(latitude, longitude),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(
        document.getElementById("map"),
        mapOption
      );

      // Add mapTypeControl and zoomControl
      const mapTypeControl = new window.kakao.maps.MapTypeControl();
      const zoomControl = new window.kakao.maps.ZoomControl();
      this.map.addControl(zoomControl, window.kakao.maps.ControlPosition.RIGHT);
    },

    

    // getMyLocation() {
    //   this.getCurrentLocation()
    //     .then((position) => {
    //       const myLocation = new window.kakao.maps.LatLng(
    //         position.latitude,
    //         position.longitude
    //       );
    //       this.map.setCenter(myLocation);
    //     })
    //     .catch((error) => {
    //       console.error("Error getting my location:", error);
    //       alert("현재 위치를 가져올 수 없습니다.");
    //     });
    // },

    displayPlacesOnMap() {
      const bounds = new window.kakao.maps.LatLngBounds();

      // Remove existing markers
      this.removeMarkers();

      // Check if myplaces is an array and not empty
      if (
        Array.isArray(this.mymapdetail.myplaces) &&
        this.mymapdetail.myplaces.length > 0
      ) {
        // Display markers on the map
        this.mymapdetail.myplaces.forEach((place) => {
          if (place.placeId && place.placeId.x && place.placeId.y) {
            const placePosition = new window.kakao.maps.LatLng(
              place.placeId.y,
              place.placeId.x
            );

            const marker = new window.kakao.maps.Marker({
              position: placePosition,
            });

            marker.setMap(this.map);

            window.kakao.maps.event.addListener(marker, "click", () => {
              this.centerMap(placePosition);
            });

            bounds.extend(placePosition);
            this.markers.push(marker);
          }
        });
      } else {
        console.warn("No places to display on the map.");
      }

      // Set the map bounds even if there are no markers
      this.map.setBounds(bounds);
    },

    removeMarkers() {
      if (this.markers && this.markers.length > 0) {
        this.markers.forEach((marker) => {
          marker.setMap(null);
        });
        this.markers = [];
      }
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
</style>
