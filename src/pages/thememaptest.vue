<!--thememaptest.vue-->
<template>
    <div v-for="thememap in myThememapList" :key="thememap.themeMapDto.id">
 
        <h3>리스트이름:{{ thememap.themeMapDto.name }}</h3>
      <!-- 여기에 세부 정보를 표시하는 코드 추가 -->
    </div>
  </template>
  
  <script>
import axios from "axios"; //axios를 사용하기 위해서는 import를 해야한다.

export default {
  name: "myThememapList",
  data() {
    return {
      myThememapList: [], // 테마 맵 목록을 저장할 배열
      themeMapId: null,
    };
  },
  mounted() {
    this.loadMymapList();
  },
  //서버에서 테마 맵 목록 가져오는 코드 Start------
  methods: {
    loadMymapList() {
      const url = `${this.backURL}/mymap/list`;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
        .then((response) => {
          // 받은 데이터를 처리하고 myThememapList 배열을 채운다.
          const themeMapList = response.data;
          this.myThememapList = themeMapList.map((themeMap) => {
            return {
              themeMapDto: themeMap,
            };
          });
        })
        .catch((error) => {
          console.error(error);
          // alert(error.msg);
          alert("로그인이 필요한 서비스 입니다.");
          location.href = "/login";
        });
    }
  }
};
  </script>