<template>
    <div>Login입니다</div>
    <div class="login-layout">
      <div class="login-container">
        <form class="login-form" @submit.prevent="loginFormSubmitHandler">
          <div id="logo">
            <h2>MapBeGood</h2>
          </div>
          <div class="mb-3">
            <!-- <label for="exampleFormControlInput1" class="form-label"
              >Email address</label
            > -->
            <input
              type="email"
              class="form-control"
              id="exampleFormControlInput1"
              placeholder=" 이메일"
            />
          </div>
          <!-- <div id="login-id">
            <input type="text" v-model="email" placeholder=" 이메일" />
          </div> -->
          <div class="mb-3" id="login-pwd">
            <!-- <label for="exampleFormControlInput1" class="form-label"
              >Email address</label
            > -->
            <input
              type="email"
              class="form-control"
              id="exampleFormControlInput1"
              placeholder=" 비밀번호"
            />
          </div>
          <!-- <div id="login-pwd">
            <input type="password" v-model="password" placeholder=" 비밀번호" />
          </div> -->
          <div id="login-id-save">
            <span id="id-save">
              <input type="checkbox" v-model="checked" /> ID저장
            </span>
            <span id="pwd-find">
              <a href="#" id="pwd-find-txt">비밀번호 찾기</a>
            </span>
          </div>
          <div id="login-bt">
            <button>로그인</button>
          </div>
          <div id="login-line">line</div>
          <div id="signup-bt">
            <a href="#">
              <img
                src="../../public/images/kakao_login_medium_narrow.png"
                alt="kakao-login"
              /> </a
            ><br />
          </div>
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
            alert(error.response.data.message);
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
    border: 1px solid black;
  
    width: 500px;
    height: 500px;
    margin: auto;
    margin-top: 150px;
  }
  
  .login-form {
    border: 1px solid black;
  
    margin: auto;
    margin-top: 50px;
  }
  
  #logo {
    border: 1px solid black;
  
    text-align: center;
  }
  
  .mb-3,
  #login-id,
  #login-pwd,
  #login-id-save,
  #login-bt,
  #login-line,
  #signup-bt {
    border: 1px solid black;
  
    padding-top: 30px;
    justify-content: center;
    display: flex;
  }
  
  #login-pwd {
    padding-top: px;
    text-decoration: none;
  }
  
  #login-id-save {
    padding-top: 10px;
  }
  
  #id-save {
    padding-right: 280px;
  }
  
  #login-bt {
    padding-top: 50px;
  }
  
  #login-line {
    padding-top: 20px;
  }
  
  #signup-bt {
    padding-top: 20px;
  }

  #pwd-find-txt {
    text-decoration: none;
    color: #000000;
  }
  
  </style>
  