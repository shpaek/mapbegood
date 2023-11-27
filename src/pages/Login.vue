<template>
  <div>Login입니다</div>
  <div class="login-container">
    <div class="login-from">
      <form @submit.prevent="loginFormSubmitHandler">
        <input type="text" v-model="email" /><br />
        <input type="password" v-model="password" /><br />
        <input type="checkbox" v-model="checked" />아이디저장
        <button>로그인</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

let model = {
  email: "",
  password: "",
  checked: "",
};

export default {
  name: "Login",
  data() {
    return model;
  },
  methods: {
    loginFormSubmitHandler() {
      if (this.checked) {
        localStorage.setItem("mapbegoodId", this.email);
      } else {
        localStorage.removeItem("mapbegoodId");
      }

      const url = `${this.backURL}/auth`;
      let data = {
        email: `${this.email}`,
        password: `${this.password}`,
      };

      axios
        .post(url, data, {
          withCredentials: true,
        })
        .then((response) => {
          if (response.status == 200) {
            console.log(response);

            alert("로그인 성공");
            // location.href = "/";
          }
          // // API 요청하는 콜마다 헤더에 accessToken 담아 보내도록 설정
          // axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
        })
        .catch((error) => {
          console.log(error);
          alert("로그인 실패");
        });
    },
  },
  created() {
    const savedId = localStorage.getItem("mapbegoodId");
    if (savedId != null) {
      this.email = savedId;
      this.checked = true;
    }
  },
};
</script>

<style scoped></style>
