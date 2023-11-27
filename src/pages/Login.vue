<template>
  <div>Login입니다</div>
  <!-- <form @submit.prevent="loginFormSubmitHandler">
    <label for="i">아이디:</label
    ><input type="text" name="email" id="i" v-model="email" /><br />
    <label for="p">비밀번호:</label
    ><input type="password" name="pwd" id="p" v-model="pwd" /><br />
    <input type="checkbox" v-model="checked" />아이디저장
    <button>로그인</button>
  </form> -->

  <div class="loginContainer">
    <span class="logo_mapbegood">MapBeGood</span>

    <article id="mainContent" class="content-article">
      <div class="cont_login">
        <div class="login_kakaomail">
          <form @submit.prevent="loginFormSubmitHandler">
            <div class="item_form fst">
              <div class="box_tf">
                <label class="screen_out" for="loginId--1">계정정보 입력</label
                ><input
                  type="text"
                  id="loginId"
                  name="loginId"
                  placeholder=" 이메일 "
                  class="tf_g"
                  v-model="email"
                />
                <div class="util_tf"></div>
              </div>
            </div>
            <div class="item_form">
              <div class="box_tf">
                <label class="screen_out" for="password--2">비밀번호 입력</label
                ><input
                  type="password"
                  id="password--2"
                  name="password"
                  placeholder=" 비밀번호 "
                  autocomplete="off"
                  class="tf_g"
                  v-model="pwd"
                />
                <div class="util_tf"></div>
              </div>
            </div>
            <div class="set_login">
              <div class="item_choice">
                <input
                  type="checkbox"
                  id="saveSignedIn--4"
                  class="inp_choice"
                  name="saveSignedIn"
                  v-model="checked"
                /><label
                  class="lab_choice"
                  for="saveSignedIn--4"
                  id="label-saveSignedIn"
                  ><span class="ico_comm ico_check"></span>간편로그인 정보
                  저장</label
                >
                <span>
                  <a
                    href="/weblogin/find_password?lang=ko&amp;continue=%2Flogin%3Fcontinue%3Dhttps%253A%252F%252Fmap.kakao.com"
                    class="link_user"
                    >비밀번호 찾기
                  </a>
                </span>
              </div>
            </div>
            <div class="confirm_btn">
              <button type="submit" class="btn_g highlight submit">
                로그인
              </button>
              <span class="line_or"><span class="txt_or">또는</span></span>
              <button type="button" class="btn_g">
                카카오 계정으로 회원가입
              </button>
            </div>
          </form>
        </div>
      </div>
    </article>
  </div>
</template>

<script>
import axios from "axios";

let model = {
  email: "test@mail.com",
  pwd: "test",
  checked: true,
};

export default {
  name: "Login",
  data() {
    return model;
  },
  methods: {
    loginFormSubmitHandler() {
      console.log("loginFormSubmitHandler() 호출");
      if (this.checked) {
        localStorage.setItem("mapbegoodId", this.email);
      } else {
        localStorage.removeItem("mapbegoodId");
      }

      const url = `${this.backURL}/auth`;
      let data = {
        email: `${this.email}`,
        password: `${this.pwd}`,
      };

      axios
        .post(url, data, {
          withCredentials: true,
        })
        .then((response) => {
          console.log(response);
          console.log(response.headers);
          if (response.data.status == 0) {
            alert(response.data.msg);
          } else if (response.data.status == 1) {
            localStorage.setItem("mapbegoodId", this.email);
            location.href = "/";
          }

          // // API 요청하는 콜마다 헤더에 accessToken 담아 보내도록 설정
          // axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
        })
        .catch((error) => {
          console.log(error);
          alert(error.message);
        });
    },
    created() {
      const savedId = localStorage.getItem("mapbegoodId");

      if (savedId != null) {
        this.email = savedId;
      }
    },
  },
};
</script>

<style scoped>
.logo_mapbegood {
  display: block;
  width: 88px;
  height: 27px;
  margin: 0 auto;
  background: #fff url(/public/images/logo.png) no-repeat 0 0;
  background-size: 100px 80px;
  font-size: 19px;
  line-height: 27px;
  text-align: center;
}
.content-article {
  width: 580px;
  height: 100%;
  margin: 40px auto 42px;
  padding: 55px 69px 50px 69px;
  border: 1px solid rgba(0, 0, 0, 0.12);
  font-size: 12px;
  box-sizing: border-box;
}
.box_tf .tf_g {
  position: relative;
  z-index: 1;
  width: 100%;
  min-height: 45px;
  padding: 10px 0 8px;
  border: 0;
  font-size: 18px;
  line-height: 25px;
  color: #191919;
  background-color: transparent;
  letter-spacing: 0;
  box-sizing: border-box;
  outline: 0 none;
  caret-color: #191919;
  opacity: 1;
}
.box_tf {
  position: relative;
  border: solid #ccc;
  border-width: 0 0 2px;
  font-family: -apple-system, Roboto, NotoSans, AppleSDGothicNeo, Malgun Gothic,
    맑은 고딕, 돋움, dotum, sans-serif;
}
.set_login {
  margin-top: 25px;
}
.confirm_btn {
  padding-top: 40px;
  text-align: center;
}
.screen_out {
  /* position: absolute; */
  width: 0;
  height: 0;
  color: white;
}
.line_or {
  position: relative;
  display: block;
  width: 100%;
  padding: 15px 0;
  font-size: 12;
  line-height: 2;
}
dl,
li,
ol,
ul {
  list-style: none;
  text-decoration: none;
}
.link_user {
  text-align: right;
}
</style>
