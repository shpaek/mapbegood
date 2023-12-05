<template lang="">
  <div>Signup입니다</div>
  <div class="signup-container">
    <div class="signup-from">
      <form @submit.prevent="signupFormSubmitHandler">
        이메일 <input type="text" v-model="email" /><br />
        닉네임 <input type="text" v-model="nickname" />
        <input type="button" value="중복확인" />
        <br />
        패스워드 <input type="password" v-model="password" /><br />
        패스워드 <input type="password" v-model="repassword" /><br />
        <button>회원가입</button> <br />
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

let model = {
  email: "",
  password: "",
  repassword: "",
  nickname: "",
};

export default {
  name: "Signup",
  data() {
    return model;
  },
  methods: {
    signupFormSubmitHandler() {
      const url = `${this.backURL}/signup`;
      let data = {
        email: `${this.email}`,
        nickname: `${this.nickname}`,
        password: `${this.password}`,
      };

      axios
        .post(url, data)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          alert(error.response.data.message);
        });
    },
  },
};
</script>

<style scoped></style>
