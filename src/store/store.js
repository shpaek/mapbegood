import { createApp } from "vue";
import { createStore } from "vuex";
import axios from "axios";

export default createStore({
  state: {
    userInfo: {
      email: "email",
      nickName: "nickName",
      profileImage: "profileImage",
      accessToken: "accessToken",
      refreshToken: "refreshToken",
      isLogin: false,
    },
  },
  getters: {
    isLogin(state) {
      return state.user.isLogin;
    },
  },
  mutations: {
    // commit 으로 부를 수 있다.
    loginSuccess({ state }, payload) {
      // console.log(payload.email);
      // console.log(payload.nickName);
      // console.log(payload.profileImage);
      // console.log(payload.accessToken);
      // console.log(payload.refreshToken);

      state.userInfo.email = payload.email;
      state.userInfo.nickName = payload.nickName;
      state.userInfo.profileImage = payload.profileImage;
      state.userInfo.accessToken = payload.accessToken;
      state.userInfo.refreshToken = payload.refreshToken;
      state.userInfo.isLogin = true;
    },
    logOut(state) {
      state.userInfo.email = "";
      state.userInfo.nickName = "";
      state.userInfo.profileImage = "";
      state.userInfo.accessToken = "";
      state.userInfo.refreshToken = "";
      state.userInfo.isLogin = false;
    },
  },
  actions: {
    // dispatch 로 부를 수 있다.
    login({ dispatch }, loginObj) {
      axios
        .post(loginObj.backUrl + "/auth", loginObj.userInfo, {
          withCredentials: true,
        })
        .then((res) => {
          localStorage.setItem("mapbegoodToken", res.headers.authorization);
          localStorage.setItem("refresh", res.headers.refresh);

          // axios.defaults.headers.common["Authorization"] =
          //   "Bearer " + accessToken;
          // console.log(`${this.backURL}`);

          dispatch("getUserInfo");
          alert("로그인 성공");
        })
        .catch(() => {
          alert("이메일과 비밀번호를 확인해 주세요.");
        });
    },
    logOut({ commit }) {
      commit("logOut");
    },
    getUserInfo({ commit }) {
      let config = {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("mapbegoodToken"),
        },
      };

      axios
        .get("http://localhost:8080" + "/login-info", config, {
          withCredentials: true,
        })
        .then((response) => {
          console.log(response.data.email);
          console.log(response.data.nickName);
          console.log(response.data.profileImage);
          console.log("Bearer " + localStorage.getItem("mapbegoodToken"));
          console.log("Bearer " + localStorage.getItem("refresh"));

          let userInfo = {
            email: response.data.email,
            nickName: response.data.nickName,
            profileImage: response.data.profileImage,
            accessToken: "Bearer " + localStorage.getItem("mapbegoodToken"),
            refreshToken: "Bearer " + localStorage.getItem("refresh"),
          };

          // commit("loginSuccess", userInfo);
        })
        .catch(() => {
          alert("로그아웃 되었습니다.");
        });
    },
  },
});

const app = createApp({});

app.use(createStore);
