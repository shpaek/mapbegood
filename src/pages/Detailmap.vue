<template>
  <div>
    <div id="map">
      <!-- <div class="chat-container"> -->
      <div :class="chatOnOff ? chatStyle2 : hidden">
        <chat />
      </div>

      <a href="#" style="text-decoration: none;">
        <!-- <img src="../../public/images/chat1.png" alt="chat" v-if="$route.name == '/group'" @click="switchChat"
          style="border-radius: 20px 20px 20px 20px" /> -->
        <span v-if="$route.name=='/group'" @click="switchChat" class="chat">
          <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" class="bi bi-chat-dots-fill" viewBox="0 0 16 16">
            <path d="M16 8c0 3.866-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.584.296-1.925.864-4.181 1.234-.2.032-.352-.176-.273-.362.354-.836.674-1.95.77-2.966C.744 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7M5 8a1 1 0 1 0-2 0 1 1 0 0 0 2 0m4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0m3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2" />
          </svg>
        </span>
      </a>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import chat from "../components/Chat.vue";

export default {
  name: "DetailMap",
  props: {
    mymapdetail: {
      type: Object,
      required: true,
    },
    color: {
      type: String,
      required: true,
    },
  },
  components: {
    chat,
  },
  data() {
    return {
      map: null,
      markers: [],

      chatOnOff: false,
      hidden: "chat-container1",
      chatStyle2: "chat-container2",
    };
  },
  mounted() {
    this.loadScript();
  },
  watch: {
    color(newColor) {
      this.updateColor(newColor);
    },
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
      // console.log("myplaces:", this.mymapdetail.myplaces);
  // console.log("myplaces type:", typeof this.mymapdetail.myplaces);
  // Check if myplaces is an array and not empty
  if (
    Array.isArray(this.mymapdetail.myplaces) &&
    this.mymapdetail.myplaces.length > 0
  ) {
    // Display markers on the map
    const markerPromises = this.mymapdetail.myplaces.map((place) => {
      return new Promise(async (resolve) => {
        if (place.placeId && place.placeId.x && place.placeId.y) {
          const placePosition = new window.kakao.maps.LatLng(
            place.placeId.y,
            place.placeId.x
          );
          const markerColor = await this.getMarkerColor(place.category);
          const imageSrc = `/images/location-${markerColor}.svg`;

          // Make sure the image source is correctly constructed
          console.log("Image Source:", imageSrc);

          const imageSize = new kakao.maps.Size(34, 39);
          const imageOption = { offset: new kakao.maps.Point(27, 69) };
          const markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imageOption
          );

              // Creating the marker
              const marker = new kakao.maps.Marker({
                position: placePosition,
                image: markerImage,
              });

              marker.setMap(this.map);

              window.kakao.maps.event.addListener(marker, "click", () => {
                this.centerMap(placePosition);
              });

              bounds.extend(placePosition);
              this.markers.push(marker);
              resolve();
            }
          });
        });

        // Wait for all markers to be created before setting the map bounds
        Promise.all(markerPromises).then(() => {
          // Set the map bounds even if there are no markers
          this.map.setBounds(bounds);
        });
      } else {
        console.warn("No places to display on the map.");
      }
    },
    centerMap(placePosition) {
  console.log("Centering map at:", placePosition);

  // You can adjust the zoom level based on your requirement
  const zoomLevel = 7;

  this.map.setLevel(zoomLevel, { anchor: placePosition });
  this.map.setCenter(placePosition);
},

    removeMarkers() {
      if (this.markers && this.markers.length > 0) {
        this.markers.forEach((marker) => {
          marker.setMap(null);
        });
        this.markers = [];
      }
    },

    getMarkerColor(category) {
      return this.color || "blue";
    },
    updateColor(newColor) {
      // Update markers with the new color
      this.displayPlacesOnMap();
    },
    switchChat() {
      this.chatOnOff = !this.chatOnOff;
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

.chat-container1 {
  display: none;
}

.chat-container2 {
  position: fixed;
  z-index: 2;
  /* margin-top: 15vh; */
  /* margin-left: 115vh; */
  /* margin-left: 111vh; */
  margin-top: 360px;
  margin-left: 1066px;
}

img {
  width: 50px;
  height: 50px;
  position: fixed;
  z-index: 20;
  /* margin-top: 88vh;
  margin-left: 140vh; */
  margin-top: 870px;
  margin-left: 1360px;
}
span.chat {
  width: 50px;
  height: 50px;
  position: fixed;
  z-index: 20;
  margin-top: 870px;
  margin-left: 1360px;
  fill:rgb(169, 178, 255);
}
</style>
