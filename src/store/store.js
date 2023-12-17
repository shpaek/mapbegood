import { createStore } from "vuex";
import axios from "axios";

export default createStore({
  state: {
    userInfo: "",
    isLogin: false,
    loading: false,
  },
  getters: {
    isLogin({ state }) {
      return this.state.userInfo.isLogin;
    },
  },
  mutations: {
    // commit 으로 부를 수 있다.
    loginSuccess({ state }, payload) {
      this.state.userInfo = payload;
      this.state.isLogin = true;
      console.log("#getLoginInfo Success");
    },
    logOut({ state }) {
      this.state.userInfo = "";
      this.state.isLogin = false;
      localStorage.removeItem("mapbegoodToken");
      localStorage.removeItem("refresh");
    },
  },
  actions: {
    // dispatch 로 부를 수 있다.
    login({ dispatch }, loginObj) {
      this.state.loading = true;
      axios
        .post(loginObj.backUrl + "/auth", loginObj.userInfo, {
          withCredentials: true,
        })
        .then((res) => {
          localStorage.setItem("mapbegoodToken", res.headers.authorization);
          localStorage.setItem("refresh", res.headers.refresh);

          this.dispatch("getUserInfo");
          this.state.loading = false;
          alert("로그인 성공");
          location.href = "/";
          
        })
        .catch(() => {
          this.state.loading = false;
          alert("이메일과 비밀번호를 확인해 주세요.");
        });
    },
    logOut({ commit }) {
      this.commit("logOut");
      alert("로그아웃 되었습니다.");
    },
    async getUserInfo({ commit, dispatch }) {
      let isToken = localStorage.getItem("mapbegoodToken");

      if (isToken != null) {
        let config = {
          headers: {
            Authorization: "Bearer " + isToken,
          },
        };

        try {
          const res = await axios.get(
            "http://localhost:8080/login-info",
            config,
            {
              withCredentials: true,
            }
          );

          if (res.data.message == "The token has expired.") {
            this.dispatch("getTokenRefresh");
          }

          let userInfo = {
            email: res.data.email,
            nickName: res.data.nickName,
            profileImage: res.data.profileImage,
            status: res.data.status,
          };

          this.commit("loginSuccess", userInfo);
        } catch (error) {
          console.log(error);
        }
      } else {
        this.commit("logOut");
      }
    },
    getTokenRefresh() {
      axios.defaults.headers.common["Refresh"] =
        "Bearer " + localStorage.getItem("refresh");

      axios
        .post("http://localhost:8080/refresh", {
          withCredentials: true,
        })
        .then((res) => {
          localStorage.setItem("mapbegoodToken", res.headers.authorization);
          location.reload();
        })
        .catch(() => {
          this.commit("logOut");
          location.href = "/ ";
        });
    },
  },
});
