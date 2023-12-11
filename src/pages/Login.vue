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
          <v-dialog v-model="signupDialog" persistent width="500">
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
                  src="../../public/images/signup_logo.png"
                  alt="mapbegood"
                  style="width: 150px"
                />
              </v-card-title>
              <v-card-text style="padding-top: 0px; padding-bottom: 0px">
                <v-container>
                  <div class="signup-profile" style="padding-left: 160px">
                    <v-file-input
                      label="File input"
                      accept="image/*"
                      v-show="false"
                      ref="fileInput"
                      @change="uploadProfileImageHandler"
                    ></v-file-input>

                    <a href="javascript:void(0)">
                      <v-avatar
                        :image="displayImage"
                        size="100"
                        @click="clickProfileImageHandler"
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
                    :loading="showSignUpAuthEmailBtDisable"
                    ref="signupEmail"
                    @keyup="editEmailHandler"
                    style="text-align: right"
                  ></v-text-field>

                  <v-btn
                    v-show="showSignUpAuthEmailBt"
                    :disabled="
                      signupEmail.length < 1 || showSignUpAuthEmailBtDisable
                    "
                    size="small"
                    color="blue-darken-1"
                    variant="tonal"
                    @click="sendSignUpAuthEmailHandler"
                    style="left: 316px"
                  >
                    이메일 인증
                  </v-btn>

                  <v-text-field
                    v-show="showSignUpAuthCode"
                    v-model="signupAuthCode"
                    color="primary"
                    label="AuthCode"
                    variant="underlined"
                    ref="signupAuthCode"
                  ></v-text-field>

                  <div v-show="showSignUpAuthCodeBt" style="height: 31px">
                    <div style="display: inline-block; margin-left: 273px">
                      <span
                        v-show="timerActive"
                        style="color: #ff2323; font-size: 13px"
                        >{{ minutes }}:{{
                          seconds < 10 ? "0" + seconds : seconds
                        }}</span
                      >
                    </div>
                    <div
                      style="
                        display: inline-block;
                        float: right;
                        margin-right: 8px;
                      "
                    >
                      <v-btn
                        v-show="showSignUpAuthCodeBt"
                        :disabled="signupAuthCode.length < 10"
                        size="small"
                        color="blue-darken-1"
                        variant="tonal"
                        @click="sendSignUpAuthCodeHandle"
                      >
                        인증번호 확인
                      </v-btn>
                    </div>
                  </div>

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
                    label="Nickname"
                    variant="underlined"
                    ref="signupNickName"
                    @keyup="editNicknameHandler"
                  ></v-text-field>

                  <v-btn
                    v-show="!nickduplication"
                    :disabled="signupNickName.length < 1"
                    size="small"
                    color="blue-darken-1"
                    variant="tonal"
                    @click="nickNameDuplicationHandler"
                    style="left: 290px"
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
                <v-btn color="blue-darken-1" @click="initSignUpHandler">
                  취소
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
              <br />
            </v-card>
          </v-dialog>
          <span class="var"> |</span>
          <v-dialog v-model="findPwdDialog" persistent width="500">
            <template v-slot:activator="{ props }">
              <a
                class="text-caption text-decoration-none text-blue"
                v-bind="props"
                href="#"
              >
                비밀번호 찾기</a
              >
            </template>
            <v-card>
              <v-card-title>
                <img
                  src="../../public/images/findPwd_logo.png"
                  alt="mapbegood"
                  style="width: 200px"
                />
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-text-field
                    v-show="showAuthEmail"
                    v-model="authEmail"
                    color="primary"
                    label="Email"
                    variant="underlined"
                    :rules="[rules.email]"
                    :loading="showAuthEmailBtDisable"
                    ref="authEmail"
                    style="text-align: right"
                  ></v-text-field>

                  <v-btn
                    v-show="showAuthEmailBt"
                    size="small"
                    color="blue-darken-1"
                    variant="tonal"
                    :disabled="authEmail.length < 1 || showAuthEmailBtDisable"
                    style="left: 310px"
                    @click="sendAuthEmailHandler"
                  >
                    인증번호 전송
                  </v-btn>

                  <v-text-field
                    v-show="showAuthCode"
                    v-model="authCode"
                    color="primary"
                    label="AuthCode"
                    variant="underlined"
                    ref="authCode"
                  ></v-text-field>

                  <div v-show="showAuthCodeBt" style="height: 31px">
                    <div style="display: inline-block; margin-left: 273px">
                      <span
                        v-show="timerActive"
                        style="color: #ff2323; font-size: 13px"
                        >{{ minutes }}:{{
                          seconds < 10 ? "0" + seconds : seconds
                        }}</span
                      >
                    </div>
                    <div
                      style="
                        display: inline-block;
                        float: right;
                        margin-right: 2px;
                      "
                    >
                      <v-btn
                        v-show="showAuthCodeBt"
                        :disabled="authCode.length < 10"
                        size="small"
                        color="blue-darken-1"
                        variant="tonal"
                        @click="sendAuthCodeHandle"
                      >
                        인증번호 확인
                      </v-btn>
                    </div>
                  </div>

                  <v-text-field
                    v-show="showPassword"
                    v-model="changePassword"
                    :type="'password'"
                    color="primary"
                    label="Password"
                    placeholder="Enter your password"
                    variant="underlined"
                    :rules="[rules.password]"
                    ref="changePassword"
                    style="text-align: right"
                  ></v-text-field>

                  <v-text-field
                    v-show="showPassword1"
                    v-model="changePassword1"
                    :type="'password'"
                    color="primary"
                    label="Password"
                    placeholder="Enter your password"
                    variant="underlined"
                    :rules="[rules.passwordCheck]"
                    ref="changePassword1"
                    style="text-align: right"
                  ></v-text-field>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  v-show="showChangePwdBt"
                  color="blue-darken-1"
                  @click="sendChangePassword"
                  :disabled="
                    changePassword.length < 9 ||
                    changePassword1.length < 9 ||
                    changePassword != changePassword1
                  "
                >
                  변경하기
                </v-btn>
                <v-btn color="blue-darken-1" @click="initFindPwdHandler">
                  취소
                </v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
              <br />
            </v-card>
          </v-dialog>
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
          href="https://kauth.kakao.com/oauth/authorize?client_id=d1c3e8aae1df4eb30d44f96550d55977&redirect_uri=http://localhost:8080/login/oauth2/code/kakao&response_type=code"
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
          if (value == this.signupPassword || value == this.changePassword)
            return true;
          return "Incorrect Password";
        },
      },
      minutes: 5,
      seconds: 0,
      timerActive: false,
      timer: null,

      // signup data
      signupDialog: false,

      signupProfileImage: "",
      displayImage:
        "https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/profile-image/default-profile.jpg",

      signupEmail: "",
      signupAuthEmail: false,
      showSignUpAuthEmailBtDisable: false,
      showSignUpAuthEmailBt: true,

      signupAuthCode: "",
      showSignUpAuthCode: false,
      showSignUpAuthCodeBt: false,

      signupPassword: "",
      signupPassword1: "",
      signupNickName: "",
      nickduplication: false,

      // findPwd data
      findPwdDialog: false,

      authEmail: "",
      showAuthEmail: true,
      showAuthEmailBtDisable: false,
      showAuthEmailBt: true,

      authCode: "",
      showAuthCode: false,
      showAuthCodeBt: false,

      changePassword: "",
      showPassword: false,
      changePassword1: "",
      showPassword1: false,
      showChangePwdBt: false,

      tmpAccessToken: "",
    };
  },
  methods: {
    ...mapActions(["login"]),

    startTimer() {
      this.timerActive = true;
      this.timer = setInterval(() => {
        if (this.seconds === 0) {
          if (this.minutes === 0) {
            this.stopTimer();
            this.signupAuthCode = "";
            this.authCode = "";
            this.showSignUpAuthCode = false;
            this.showSignUpAuthCodeBt = false;
            this.showAuthCode = false;
            this.showAuthCodeBt = false;
            return;
          }
          this.minutes--;
          this.seconds = 59;
        } else {
          this.seconds--;
        }
      }, 1000);
    },

    stopTimer() {
      clearInterval(this.timer);
      this.timerActive = false;
      this.minutes = 5;
      this.seconds = 0;
    },

    loginFormSubmitHandler() {
      if (this.email == "") {
        alert("이메일을 입력하세요.");
        this.$refs.email.focus();
        return;
      } else if (this.rules.email(this.email) != true) {
        alert("올바른 이메일 형식이 아닙니다.");
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
      } else if (this.signupAuthEmail != true) {
        alert("이메일 인증이 필요합니다.");
        this.$refs.signupEmail.focus();
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
      } else if (this.signupNickName == "") {
        alert("닉네임을 입력하세요.");
        this.$refs.signupNickName.focus();
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
          this.initSignUpHandler();
          this.$refs.password.focus();
        })
        .catch((err) => {
          // console.log(err);
          alert(err.response.data.message);
          this.$refs.signupEmail.focus();
        });
    },

    editEmailHandler() {
      this.signupAuthEmail = false;
      this.showSignUpAuthEmailBt = true;
    },

    editNicknameHandler() {
      this.nickduplication = false;
    },

    nickNameDuplicationHandler() {
      if (this.signupNickName == "") {
        alert("닉네임을 입력하세요.");
        this.$refs.signupNickName.focus();
        return;
      }

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

    clickProfileImageHandler() {
      this.$refs.fileInput.click();
    },

    uploadProfileImageHandler(e) {
      if (e != null && e.target.files[0].type.indexOf("image") < 0) {
        alert("이미지 파일만 업로드 가능합니다.");
        return;
      }
      this.signupProfileImage = e.target.files[0];
      this.displayImage = window.URL.createObjectURL(this.signupProfileImage);
    },

    sendSignUpAuthEmailHandler() {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      if (this.signupEmail == "") {
        alert("이메일을 입력해주세요.");
        this.$refs.signupEmail.focus();
        return;
      } else if (!pattern.test(this.signupEmail)) {
        alert("이메일을 확인해 주세요.");
        this.$refs.signupEmail.focus();
        return;
      }

      const sendEmail = {
        email: this.signupEmail,
      };

      this.showSignUpAuthEmailBtDisable = true;
      axios
        .post(`${this.backURL}/auth-email`, sendEmail)
        .then((res) => {
          alert(res.data.message);
          this.showSignUpAuthCode = true;
          this.showSignUpAuthCodeBt = true;
          this.showSignUpAuthEmailBtDisable = false;
          this.stopTimer();
          this.startTimer();
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data.message);
        });
    },

    sendSignUpAuthCodeHandle() {
      if (this.signupAuthCode == "") {
        alert("인증번호를 입력해 주세요.");
        this.$refs.signupAuthCode.focus();
        return;
      }

      const sendCode = {
        email: this.signupEmail,
        code: this.signupAuthCode,
      };

      axios
        .post(`${this.backURL}/signup-auth-code`, sendCode)
        .then((res) => {
          alert(res.data.message);
          this.signupAuthEmail = true;
          this.showSignUpAuthEmailBt = false;
          this.showSignUpAuthCode = false;
          this.showSignUpAuthCodeBt = false;
          this.stopTimer();
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data.message);
        });
    },

    initSignUpHandler() {
      this.signupDialog = false;

      this.signupProfileImage = "";
      this.displayImage =
        "https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/profile-image/default-profile.jpg";

      this.signupEmail = "";
      this.signupAuthEmail = false;
      this.showSignUpAuthEmailBt = true;

      this.signupAuthCode = "";
      this.showSignUpAuthCode = false;
      this.showSignUpAuthCodeBt = false;

      this.signupPassword = "";
      this.signupPassword1 = "";

      this.signupNickName = "";
      this.nickduplication = false;

      this.email = "";
      this.password = "";
      this.stopTimer();
    },

    sendAuthEmailHandler() {
      const pattern =
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

      if (this.authEmail == "") {
        alert("가입한 이메일을 입력해주세요.");
        this.$refs.authEmail.focus();
        return;
      } else if (!pattern.test(this.authEmail)) {
        alert("이메일을 확인해 주세요.");
        this.$refs.authEmail.focus();
        return;
      }

      const sendEmail = {
        email: this.authEmail,
      };

      this.showAuthEmailBtDisable = true;
      axios
        .post(`${this.backURL}/auth-email`, sendEmail)
        .then((res) => {
          alert(res.data.message);
          this.showAuthCode = true;
          this.showAuthCodeBt = true;
          this.showAuthEmailBtDisable = false;
          this.stopTimer();
          this.startTimer();
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data.message);
        });
    },

    sendAuthCodeHandle() {
      if (this.authCode == "") {
        alert("인증번호를 입력해 주세요.");
        this.$refs.authCode.focus();
        return;
      }

      const sendCode = {
        email: this.authEmail,
        code: this.authCode,
      };

      axios
        .post(`${this.backURL}/auth-code`, sendCode)
        .then((res) => {
          alert(res.data.message);
          this.tmpAccessToken = res.headers.authorization;

          this.showAuthEmail = false;
          this.showAuthEmailBt = false;
          this.showAuthCode = false;
          this.showAuthCodeBt = false;
          this.showPassword = true;
          this.showPassword1 = true;
          this.showChangePwdBt = true;
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data.message);
        });
    },

    sendChangePassword() {
      if (this.changePassword == "") {
        alert("변경할 비밀번호를 입력하세요.");
        this.$refs.changePassword.focus();
        return;
      } else if (this.rules.password(this.changePassword) != true) {
        alert("비밀번호 규칙에 맞지 않습니다.");
        this.$refs.changePassword.select();
        return;
      } else if (this.changePassword != this.changePassword1) {
        alert("비밀번호가 다릅니다.");
        this.$refs.changePassword.select();
        return;
      }

      const data = {
        password: this.changePassword,
      };

      const config = {
        headers: {
          Authorization: "Bearer " + this.tmpAccessToken,
        },
      };

      axios
        .put(`${this.backURL}/pwd`, data, config)
        .then((res) => {
          console.log(res);
          alert(res.data.message);
          this.email = this.authEmail;
        })
        .catch((err) => {
          console.log(err);
          alert(err.response.data.message);
        });
      this.initFindPwdHandler();
    },

    initFindPwdHandler() {
      this.findPwdDialog = false;

      this.authEmail = "";
      this.showAuthEmail = true;
      this.showAuthEmailBt = true;

      this.authCode = "";
      this.showAuthCode = false;
      this.showAuthCodeBt = false;

      this.changePassword = "";
      this.showPassword = false;
      this.changePassword1 = "";
      this.showPassword1 = false;
      this.showChangePwdBt = false;

      this.tmpAccessToken = "";
      this.email = "";
      this.password = "";
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
