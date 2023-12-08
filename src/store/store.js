import { createStore } from "vuex";
import axios from "axios";

export default createStore({
  state: {
    user: {
      loginEmail: "",
      nickName: "",
      accessToken: "",
      refreshToken: "",
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
    loginUser({ state, commit }, data) {
      axios
        .post(`${this.backURL}/auth`, data, {
          withCredentials: true,
        })
        .then((response) => {
          if (response.status == 200) {
            const accessToken = response.headers.authorization;
            const refreshToke = response.headers.refresh;

            axios.defaults.headers.common["Authorization"] = accessToken;
            localStorage.setItem("mapbegoodToken", accessToken);
            localStorage.setItem("refresh", refreshToke);

            alert("로그인 성공");
            location.href = "/";

            console.log(axios.defaults.headers.common);

            this.$store.dispacer;
          }
        })
        .catch(() => {
          alert("로그인 실패");
        });

      state.user.loginEmail = _user.loginEmail;
      state.user.nickName = _user.nickName;
      state.user.accessToken = _user.accessToken;
      state.user.refreshToken = _user.refreshToken;
      state.user.isLogin = true;
    },
    logOutUser(state) {
      state.user.loginEmail = "";
      state.user.nickName = "";
      state.user.accessToken = "";
      state.user.refreshToken = "";
      state.user.isLogin = false;
    },
  },
  actions: {
    // dispatch 로 부를 수 있다.
    loginUser: ({ commit }, _user) => {
      commit("setUser", _user);
    },
    logOutUser: ({ commit }) => {
      commit("logOutUser");
    },
  },
});
