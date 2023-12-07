<template>
  <div class="login-container">
    <div class="login-logo">
      <img src="../../public/images/login_logo.png" alt="mapbegood" />
    </div>
    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="8"
      max-width="448"
      rounded="lg"
      style="text-align: right"
    >
      <v-text-field
        v-model="email"
        label="Email"
        color="primary"
        variant="underlined"
        :rules="[rules.required, rules.email]"
        clearable
        ref="email"
      ></v-text-field>

      <v-text-field
        v-model="password"
        label="Password"
        color="primary"
        variant="underlined"
        :type="'password'"
        density="compact"
        :rules="[rules.required]"
        clearable
        ref="password"
        v-on:keyup.enter="loginFormSubmitHandler"
      ></v-text-field>

      <div class="sub-func-container">
        <div class="saveid" style="">
          <!-- <v-checkbox
                label="info"
                color="info"
                value="info"
                hide-details
              ></v-checkbox> -->
          <label
            for="saveemail"
            class="text-caption text-decoration-none text-blue"
            rel="noopener noreferrer"
            target="_blank"
          >
            <input type="checkbox" v-model="checked" id="saveemail" />
            Email 저장</label
          >
        </div>
        <div class="findpwd">
          <a
            class="text-caption text-decoration-none text-blue"
            href="#"
            rel="noopener noreferrer"
            target="_blank"
          >
            비밀번호 찾기</a
          >
        </div>
      </div>
      <v-btn
        block
        class="mb-8"
        color="blue"
        size="large"
        variant="tonal"
        @click="loginFormSubmitHandler"
      >
        로그인
      </v-btn>

      <v-divider color="warningss"></v-divider>

      <v-card-text class="text-center">
        <a
          href="https://kauth.kakao.com/oauth/authorize?client_id=d1c3e8aae1df4eb30d44f96550d55977&redirect_uri=http://localhost:5173/signup&response_type=code"
        >
          <img
            src="../../public/images/kakao_login_medium_narrow.png"
            alt="kakao_signup"
          />
        </a>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      rules: {
        required: (value) => !!value || "Required.",
        email: (value) => {
          const pattern =
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
          return pattern.test(value) || "Invalid Email.";
        },
      },
    };
  },
  methods: {
    loginFormSubmitHandler() {
      if (this.checked) {
        localStorage.setItem("mapbegoodId", this.email);
      } else {
        localStorage.removeItem("mapbegoodId");
      }

      if (this.email == "") {
        alert("이메일을 입력하세요.");
        this.$refs.email.focus();
        return;
      } else if (this.password == "") {
        alert("비밀번호를 입력하세요.");
        this.$refs.password.focus();
        return;
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
            const accessToken = response.headers.authorization;
            const refreshToke = response.headers.refresh;

            axios.defaults.headers.common["Authorization"] = accessToken;
            localStorage.setItem("mapbegoodToken", accessToken);
            localStorage.setItem("refresh", refreshToke);

            alert("로그인 성공");
            location.href = "/";

            console.log(axios.defaults.headers.common);
          }
        })
        .catch((error) => {
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

<style scoped>
.login-container {
  margin-top: 150px;
}

.login-logo {
  text-align: center;
  margin-bottom: 30px;
}

.login-logo > img {
  width: 220px;
}

.mx-auto {
  text-align: right;
}

.sub-func-container {
  margin-bottom: 20px;
}

#saveemail {
  vertical-align: sub;
}

.saveid,
.findpwd {
  display: inline-block;
}

.saveid {
  float: left;
}
</style>
