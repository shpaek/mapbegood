<template>
  <div>
    <div id="map"></div>
    <Search @center-map="centerMap" />
  </div>
</template>

<script>
import Search from '@/components/Search.vue';

export default {
  name: 'Map',
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
    this.mapContainer = document.getElementById('map');
    this.loadScript();
  },
  methods: {
    loadScript() {
      const script = document.createElement('script');
      script.src =
        'https://dapi.kakao.com/v2/maps/sdk.js?appkey=872b5a083c1af3f5ac36a2d8e87b0790&libraries=services&autoload=false';
      script.onload = () => {
        window.kakao.maps.load(() => this.initialize());
      };
      script.onerror = (error) => {
        console.error('카카오 지도 SDK 로딩 오류:', error);
      };
      document.head.appendChild(script);
    },
    async initialize() {
  try {
    if (!window.kakao || !window.kakao.maps) {
      throw new Error('Kakao Maps API가 로드되지 않았습니다.');
    }

    // 이미 services.Places가 초기화되어 있으므로 바로 사용 가능
    this.ps = window.kakao.maps.services.Places;
    this.infowindow = new window.kakao.maps.InfoWindow({ zIndex: 1 });

    const userLocation = await this.getCurrentLocation();
    this.setInitialMap(userLocation.latitude, userLocation.longitude);
    this.getMyLocation();
  } catch (error) {
    console.error('지도 초기화 오류:', error);
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
              console.error('현재 위치 가져오기 오류:', error);
              reject(error);
            }
          );
        } else {
          reject('이 브라우저는 위치 정보를 지원하지 않습니다.');
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
          console.error('Error getting my location:', error);
          alert('현재 위치를 가져올 수 없습니다.');
        });
    },
    centerMap(placePosition) {
      console.log('Centering map at:', placePosition);
      this.map.setCenter(placePosition);
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
