<template>
  <div class="signup-container">
    <div class="signup-layout">
      <div class="signup-title">
        <img src="../../public/images/login_logo.png" alt="mapbegood" />
      </div>
      <div class="signup-form">
        <v-card class="mx-auto" max-width="344" title="User Registration">
          <v-container>
            <div class="signup-profile">
              <v-file-input
                label="File input"
                variant="solo-inverted"
              ></v-file-input>

              <v-avatar
                image="../../public/images/xfda.jpg"
                size="100"
              ></v-avatar>
            </div>
            <br />

            <v-text-field
              v-model="email"
              color="primary"
              label="Email"
              variant="underlined"
              readonly
            ></v-text-field>

            <v-text-field
              v-model="first"
              color="primary"
              label="Nick-Name"
              variant="underlined"
            ></v-text-field>
            <v-btn variant="text" style="left: 200px"> 중복확인 </v-btn>

            <v-text-field
              v-model="password"
              :type="'password'"
              color="primary"
              label="Password"
              placeholder="Enter your password"
              variant="underlined"
            ></v-text-field>

            <v-text-field
              v-model="repassword"
              :type="'password'"
              color="primary"
              label="Password"
              placeholder="Enter your password"
              variant="underlined"
            ></v-text-field>

            <v-checkbox
              v-model="terms"
              color="secondary"
              label="I agree to site terms and conditions"
            ></v-checkbox>
          </v-container>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="success">
              Complete Registration

              <v-icon icon="mdi-chevron-right" end></v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </div>
  </div>
</template>

<!-- <template lang="">
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
</template> -->

<script>
import axios from "axios";

let model = {
  email: "dozy@mail.com",
  password: "",
  repassword: "",
  nickname: "",

  first: null,
  last: null,
  terms: false,
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

<style scoped>
.signup-container {
  border: 1px solid rgb(248, 6, 6);

  margin-top: 50px;
}

.signup-layout {
  border: 1px solid rgb(5, 49, 248);
  margin: 50px;
}

.signup-title {
  border: 1px solid rgb(10, 245, 49);
  margin: 50px;
}

.signup-title > img {
  width: 220px;
}

.signup-form {
  border: 1px solid rgb(212, 248, 10);
  margin: 50px;
}

.signup-profile {
  border: 1px solid rgb(5, 49, 248);
}
</style>
