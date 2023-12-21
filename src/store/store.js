import { createStore } from "vuex";
import axios from "axios";
import Swal from "sweetalert2";

export default createStore({
  state: {
    userInfo: "",
    isLogin: false,
    loading: false,
    chats: {},
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
      // console.log("#getLoginInfo Success");
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
          Swal.fire({
            text: "로그인 성공",
            icon: "success",
          });
          location.href = "/";
        })
        .catch(() => {
          this.state.loading = false;
          Swal.fire({
            text: "이메일과 비밀번호를 확인해 주세요.",
            icon: "error",
          });
        });
    },
    logOut({ commit }) {
      this.commit("logOut");
    },
    async getUserInfo({ commit, dispatch }) {
      let isToken = localStorage.getItem("mapbegoodToken");
      // console.log("getUserInfo 호출");
      if (isToken != null) {
        let config = {
          headers: {
            Authorization: "Bearer " + isToken,
          },
        };

        // try {
        //   const res = await axios.get(
        //     "http://localhost:8080/login-info",
        //     config,
        //     {
        //       withCredentials: true,
        //     }
        //   );

        //   if (res.data.message == "The token has expired.") {
        //     this.dispatch("getTokenRefresh");
        //   }

        //   let userInfo = {
        //     email: res.data.email,
        //     nickName: res.data.nickName,
        //     profileImage: res.data.profileImage,
        //     status: res.data.status,
        //   };

        //   this.commit("loginSuccess", userInfo);

        try {
          await axios
            // .get("http://localhost:8080/login-info", config, {
            .get(
              "http://ec2-15-164-213-115.ap-northeast-2.compute.amazonaws.com:8080/login-info",
              config,
              {
                withCredentials: true,
              }
            )
            .then((res) => {
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
              console.log("getUserInfo: 성공");
              console.log(this.state.userInfo);
            });
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
        // .post("http://localhost:8080/refresh", {
        .post(
          "http://ec2-15-164-213-115.ap-northeast-2.compute.amazonaws.com:8080/refresh",
          {
            withCredentials: true,
          }
        )
        .then((res) => {
          localStorage.setItem("mapbegoodToken", res.headers.authorization);
          location.reload();
        })
        .catch(() => {
          this.commit("logOut");
          // location.href = "/ ";
        });
    },
  },
});
