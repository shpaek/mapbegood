<template>
  <div>
    <div id="searchContainer" class="bg_white">
      <div class="search">
        <input type="text" v-model="keyword" placeholder="장소 검색" @keyup.enter="search" />
        <img src="https://s3.ap-northeast-2.amazonaws.com/cdn.wecode.co.kr/icon/search.png" class="icon" @click="search" />     
      </div>
      <ul id="placesList">
        <li v-for="(place, index) in places" :key="index" @click="centerMap(place)">
          <span class="markerbg" :class="'marker_' + (index + 1)"></span>
          <div class="info">
            <h5>{{ place.place_name }}</h5>
            <span v-if="place.road_address_name">{{ place.road_address_name }}</span>
            <span class="jibun gray" v-if="place.road_address_name">{{ place.address_name }}</span>
            <span v-else>{{ place.address_name }}</span>
            <span class="tel" v-if="place.phone">{{ place.phone }}</span>
          </div>
        </li>
        <div id="pagination"></div>
      </ul>
    </div>
  </div>
</template>



  <script>
  export default {
    name: "Search",
    data() {
      return {
        ps: null,
        keyword: "",
        places: [],
      };
    },
    methods: {
      async loadScript() {
        return new Promise((resolve, reject) => {
          const script = document.createElement('script');
          script.src =
            'https://dapi.kakao.com/v2/maps/sdk.js?appkey=872b5a083c1af3f5ac36a2d8e87b0790&libraries=services&autoload=false';
          script.onload = () => {
            window.kakao.maps.load(() => {
              this.initialize();
              resolve();
            });
          };
          script.onerror = (error) => {
            console.error('카카오 지도 SDK 로딩 오류:', error);
            reject(error);
          };
          document.head.appendChild(script);
        });
      },
      async initialize() {
        try {
          if (!window.kakao || !window.kakao.maps) {
            throw new Error('Kakao Maps API가 로드되지 않았습니다.');
          }

          // 이미 services.Places가 초기화되어 있으므로 바로 사용 가능
          this.ps = new window.kakao.maps.services.Places();
          // 나머지 코드 계속...
        } catch (error) {
          console.error('지도 초기화 오류:', error);
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
          this.places = data;
          this.displayPagination(pagination);
        } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
          alert("검색 결과가 존재하지 않습니다.");
        } else if (status === window.kakao.maps.services.Status.ERROR) {
          alert("검색 결과 중 오류가 발생했습니다.");
        }
      },
      centerMap(place) {
        const placePosition = new window.kakao.maps.LatLng(place.y, place.x);
        this.$emit("center-map", placePosition);
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
    },
  };
  </script>

  <style scoped>  
  * {
    font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
}
  .markerbg {
    display: inline-block;
    width: 20px;
    height: 20px;
    background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;
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
    max-height: 600px;
}

#placesList li {
    position: relative;
    border-bottom: 1px solid #888;
    overflow: hidden;
    cursor: pointer;
    min-height: 65px;
    display: flex;
    align-items: center;
    padding: 10px;
}

#placesList li .markerbg {
    width: 20px;
    height: 20px;
    background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;
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
    background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;
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

  #placesList .item .markerbg {
      float: left;
      position: absolute;
      width: 36px;
      height: 37px;
      margin: 10px 0 0 10px;
      background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;
  }

  #placesList .item .marker_1 {
      background-position: 0 -10px;
  }
  #placesList .item .marker_2 {
      background-position: 0 -56px;
  }
  #placesList .item .marker_3 {
      background-position: 0 -102px;
  }
  #placesList .item .marker_4 {
      background-position: 0 -148px;
  }
  #placesList .item .marker_5 {
      background-position: 0 -194px;
  }
  #placesList .item .marker_6 {
      background-position: 0 -240px;
  }
  #placesList .item .marker_7 {
      background-position: 0 -286px;
  }
  #placesList .item .marker_8 {
      background-position: 0 -332px;
  }
  #placesList .item .marker_9 {
      background-position: 0 -378px;
  }
  #placesList .item .marker_10 {
      background-position: 0 -423px;
  }
  #placesList .item .marker_11 {
      background-position: 0 -470px;}

  #placesList .item .marker_12 {
      background-position: 0 -516px;
  }
  #placesList .item .marker_13 {
      background-position: 0 -562px;
  }
  #placesList .item .marker_14 {
      background-position: 0 -608px;
  }
  #placesList .item .marker_15 {
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
  left: 160px;
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

  .marker-1 { background-position: 0 -10px; }
  .marker-2 { background-position: 0 -60px; }
  .marker-3 { background-position: 0 -110px; }
  /* Add more marker styles as needed */
  </style>

  
<style >
/* 전역 스타일 - 컴포넌트 스코프 외부에서도 적용 */
#pagination {
  margin: 20px 0;
  text-align: center;
}

#pagination a {
  display: inline-block;
  margin: 0 10px;
  text-decoration: none;
  padding: 5px 10px;
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