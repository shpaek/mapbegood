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
    >
      <v-text-field
        v-model="email"
        label="Email"
        color="primary"
        variant="underlined"
        :rules="[rules.email]"
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
        clearable
        ref="password"
        v-on:keyup.enter="loginFormSubmitHandler"
      ></v-text-field>

      <div class="sub-func-container">
        <div class="saveid">
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

        <div class="signup">
          <v-dialog v-model="dialog" persistent width="500">
            <template v-slot:activator="{ props }">
              <a
                class="text-caption text-decoration-none text-blue"
                v-bind="props"
                href="#"
              >
                회원가입</a
              >
            </template>
            <v-card>
              <v-card-title>
                <img
                  src="../../public/images/login_logo.png"
                  alt="mapbegood"
                  style="width: 150px"
                />
              </v-card-title>
              <v-card-text style="padding-top: 0px; padding-bottom: 0px">
                <v-container>
                  <div class="signup-profile" style="padding-left: 150px">
                    <v-file-input
                      label="File input"
                      accept="image/*"
                      v-show="false"
                      ref="fileInput"
                      @change="profileImageUploadHandler"
                    ></v-file-input>

                    <a href="javascript:void(0)">
                      <v-avatar
                        :image="displayImage"
                        size="100"
                        @click="profileImageClickHandler"
                      ></v-avatar>
                    </a>
                  </div>
                  <br />

                  <v-text-field
                    v-model="signupEmail"
                    color="primary"
                    label="Email"
                    variant="underlined"
                    :rules="[rules.email]"
                    ref="signupEmail"
                    style="text-align: right"
                  ></v-text-field>

                  <v-text-field
                    v-model="signupPassword"
                    :type="'password'"
                    color="primary"
                    label="Password"
                    placeholder="Enter your password"
                    variant="underlined"
                    :rules="[rules.password]"
                    ref="signupPassword"
                    style="text-align: right"
                  ></v-text-field>

                  <v-text-field
                    v-model="signupPassword1"
                    :type="'password'"
                    color="primary"
                    label="Password"
                    placeholder="Enter your password"
                    variant="underlined"
                    :rules="[rules.passwordCheck]"
                    ref="signupPassword1"
                    style="text-align: right"
                  ></v-text-field>

                  <v-text-field
                    v-model="signupNickName"
                    color="primary"
                    label="Nick-Name"
                    variant="underlined"
                    ref="signupNickName"
                  ></v-text-field>
                  <v-btn
                    size="small"
                    color="blue-darken-1"
                    variant="tonal"
                    style="left: 290px"
                    v-model="nickduplication"
                    @click="nickNameDuplicationHandler"
                    v-show="!nickduplication"
                  >
                    닉네임 중복확인
                  </v-btn>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue-darken-1" @click="signupFormSubmitHandler">
                  가입하기
                </v-btn>
                <v-btn color="blue-darken-1" @click="signUpInitHandler">
                  취소
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
              <br />
            </v-card>
          </v-dialog>
          <span class="var"> |</span>
          <a
            class="text-caption text-decoration-none text-blue"
            rel="noopener noreferrer"
            href="#"
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
            alt="kakao_signup_img1"
          />
        </a>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";

export default {
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      checked: "",
      rules: {
        email: (value) => {
          if (value.length >= 1) {
            const pattern =
              /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return pattern.test(value) || "Invalid Email.";
          }
        },
        password: (value) => {
          if (value.length >= 1) {
            const pattern =
              /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$/;
            return (
              pattern.test(value) || "영문, 숫자, 특수기호 조합 8자리 이상."
            );
          }
        },
        passwordCheck: (value) => {
          if (value == this.signupPassword) return true;
          return "Incorrect Password";
        },
      },
      dialog: false,
      signupProfileImage: "",
      signupEmail: "",
      signupPassword: "",
      signupPassword1: "",
      signupNickName: "",
      nickduplication: false,
      displayImage: "",
    };
  },
  methods: {
    ...mapActions(["login"]),

    loginFormSubmitHandler() {
      if (this.email == "") {
        alert("이메일을 입력하세요.");
        this.$refs.email.focus();
        return;
      } else if (this.password == "") {
        alert("비밀번호를 입력하세요.");
        this.$refs.password.focus();
        return;
      }

      this.login({
        backUrl: `${this.backURL}`,
        userInfo: {
          email: this.email,
          password: this.password,
        },
      });

      if (this.checked) {
        localStorage.setItem("mapbegoodId", this.email);
      } else {
        localStorage.removeItem("mapbegoodId");
      }
    },

    signupFormSubmitHandler() {
      if (this.signupEmail == "") {
        alert("이메일을 입력하세요.");
        this.$refs.signupEmail.focus();
        return;
      } else if (this.rules.email(this.signupEmail) != true) {
        alert("올바른 이메일 형식이 아닙니다.");
        this.$refs.signupEmail.focus();
        return;
      } else if (this.signupNickName == "") {
        alert("닉네임을 입력하세요.");
        this.$refs.signupNickName.focus();
        return;
      } else if (this.signupPassword == "") {
        alert("비밀번호를 입력하세요.");
        this.$refs.signupPassword.focus();
        return;
      } else if (this.rules.password(this.signupPassword) != true) {
        alert("비밀번호 규칙에 맞지 않습니다.");
        this.$refs.signupPassword.select();
        return;
      } else if (this.signupPassword != this.signupPassword1) {
        alert("비밀번호가 다릅니다.");
        this.$refs.signupPassword.select();
        return;
      } else if (this.nickduplication != true) {
        alert("닉네임 중복확인이 필요합니다.");
        this.$refs.signupNickName.focus();
        return;
      } else if (this.signupProfileImage != "") {
        if (this.signupProfileImage.type.indexOf("image") < 0) {
          alert("이미지 파일만 업로드 가능합니다.");
          return;
        }
      }

      const signupUser = {
        email: this.signupEmail,
        password: this.signupPassword,
        nickname: this.signupNickName,
      };

      const blobSignupUser = new Blob([JSON.stringify(signupUser)], {
        type: "application/json",
      });

      let formData = new FormData();
      formData.append("signUpDto", blobSignupUser);
      formData.append("profileImage", this.signupProfileImage);

      const config = {
        Headers: {
          "Content-Type": "multipart/form-data",
        },
      };

      axios
        .post(`${this.backURL}/signup`, formData, config)
        .then((res) => {
          // console.log(res);
          alert(res.data.message);
          this.email = this.signupEmail;
          this.signUpInitHandler();
        })
        .catch((err) => {
          // console.log(err);
          if (err.response.data.status == 400) {
            alert(err.response.data.message);
          } else {
            alert("회원가입에 실패했습니다.");
          }
          this.$refs.signupEmail.focus();
        });
    },

    nickNameDuplicationHandler() {
      const params = {
        nickName: this.signupNickName,
      };

      axios
        .get(`${this.backURL}/name`, { params })
        .then((res) => {
          alert(res.data.message);
          this.nickduplication = true;
        })
        .catch((err) => {
          alert(err.response.data.message);
          this.$refs.signupNickName.focus();
        });
    },

    profileImageClickHandler() {
      this.$refs.fileInput.click();
    },

    profileImageUploadHandler(e) {
      if (e != null && e.target.files[0].type.indexOf("image") < 0) {
        alert("이미지 파일만 업로드 가능합니다.");
        return;
      }
      this.signupProfileImage = e.target.files[0];
      this.displayImage = window.URL.createObjectURL(this.signupProfileImage);
    },

    signUpInitHandler() {
      this.signupProfileImage = "";
      this.signupEmail = "";
      this.signupPassword = "";
      this.signupPassword1 = "";
      this.signupNickName = "";
      this.nickduplication = false;
      this.displayImage = "";
      this.dialog = false;
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

.var {
  color: skyblue;
}
</style>
