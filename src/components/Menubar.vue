<template>
  <div class="menubar">
    <ul>
      <!-- 로그인이 안된 경우 -->
      <!-- <template v-show="loginedId == ''"> -->
      <li>
        <router-link to="/signup" class="menubar">가입</router-link>
      </li>
      <li>
        <router-link to="/login" class="menubar">로그인</router-link>
      </li>
      <!-- </template> -->

      <!-- 로그인된 경우 -->
      <!-- <template v-show="loginedId != ''"> -->
      <li>
        <a href="#" @click="logoutClickHandler" class="menubar">로그아웃</a>
      </li>
      <!-- </template> -->

      <!-- 로그인 관계없이 보여줄 거 -->
      <img
        src="../../public/images/logo.jpg"
        alt="로고"
        @click="logoClickHandler"
      />
      <router-link to="/nickchange" class="menubar">{{ nickname }}</router-link>

      <!-- 로그인 관계없이 보여줄 거 -->
      <li>
        <router-link to="/" class="menubar">추천<br />테마지도</router-link>
      </li>

      <!-- 로그인된 경우 -->
      <!-- <template v-show="loginedId != ''"> -->
      <li>
        <router-link to="/thememaplist" class="menubar"
          >내 테마지도</router-link
        >
      </li>
      <li>
        <router-link to="/favoritelist" class="menubar">즐겨찾기</router-link>
      </li>
      <li><router-link to="/map" class="menubar">지도</router-link></li>
      <li><router-link to="/group" class="menubar">그룹</router-link></li>
      <!-- </template> -->
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Menubar",
  data() {
    return {
      loginedId: "",
      nickname: "닉네임",
    };
  },
  methods: {
    logoClickHandler() {
      //로고img객체 클릭이벤트
      location.href = "/";
    },
    logoutClickHandler() {
      //로그아웃 클릭이벤트
      const url = `${this.backURL}/refresh`;

      axios.defaults.headers.common["Authorization"] =
        localStorage.getItem("mapbegoodToken");

      axios.defaults.headers.common["Refresh"] =
        "Bearer " + localStorage.getItem("refresh");

      axios
        .delete(url, { withCredentials: true })
        .then((response) => {
          delete axios.defaults.headers.common["Authorization"];
          delete axios.defaults.headers.common["Refresh"];

          localStorage.removeItem("mapbegoodToken");
          localStorage.removeItem("refresh");

          console.log(response.data);
          alert("로그아웃 되었습니다.");
          location.href = "/";
        })
        .catch((error) => {
          console.log(error);
          alert(error.message);
        });
    },
  },
};
</script>
<style scoped>
div.menubar {
  width: 76px;
  height: 100%;
  font-size: 13px;
}
div.menubar > ul {
  padding: 0;
}
div.menubar > ul > li {
  list-style-type: none;
  list-style: none;
}
router-link {
  text-decoration: none;
}
a {
  text-decoration: none;
}
.menubar {
  text-align: center;
}
</style>
