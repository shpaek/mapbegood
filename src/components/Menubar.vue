<template>
  <div class="menubar">
    <!-- SVG 심볼 -->
    <svg xmlns="http://www.w3.org/2000/svg" class="d-none">
      <symbol id="logo" viewBox="0 0 47 47" width="100%" height="100%">
        <image href="/images/logo.png" width="47" height="47" />
      </symbol>

      <symbol id="Map" viewBox="0 0 16 16">
        <path
          fill-rule="evenodd"
          d="M15.817.113A.5.5 0 0 1 16 .5v14a.5.5 0 0 1-.402.49l-5 1a.502.502 0 0 1-.196 0L5.5 15.01l-4.902.98A.5.5 0 0 1 0 15.5v-14a.5.5 0 0 1 .402-.49l5-1a.5.5 0 0 1 .196 0L10.5.99l4.902-.98a.5.5 0 0 1 .415.103zM10 1.91l-4-.8v12.98l4 .8V1.91zm1 12.98 4-.8V1.11l-4 .8v12.98zm-6-.8V1.11l-4 .8v12.98l4-.8z"
        />
      </symbol>

      <symbol id="othersthememap" viewBox="0 0 16 16">
        <path
          d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z"
        />
      </symbol>

      <symbol id="thememap" viewBox="0 0 16 16">
        <path
          d="M8 16.016a7.5 7.5 0 0 0 1.962-14.74A1 1 0 0 0 9 0H7a1 1 0 0 0-.962 1.276A7.5 7.5 0 0 0 8 16.016zm6.5-7.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"
        />
        <path d="m6.94 7.44 4.95-2.83-2.83 4.95-4.949 2.83 2.828-4.95z" />
      </symbol>

      <symbol id="FavoriteList" viewBox="0 0 16 16">
        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
        <path
          d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"
        />
      </symbol>

      <symbol id="group" viewBox="0 0 16 16">
        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
        <path
          fill-rule="evenodd"
          d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"
        />
      </symbol>
    </svg>

    <!-- 메뉴바 -->
    <main class="d-flex flex-nowrap">
      <h1 class="visually-hidden">메뉴바</h1>
      <div class="b-example-divider b-example-vr"></div>
      <div
        class="d-flex flex-column flex-shrink-0 bg-body-tertiary"
        style="width: 63px; height: 100%"
      >
        <!-- 로고 -->
        <a
          href="/"
          class="d-nav-link active py-3 border-bottom rounded-0 p-3 link-body-emphasis text-decoration-none"
          title="logo"
          data-bs-toggle="tooltip"
          data-bs-placement="right"
        >
          <svg class="bi pe-none" width="63" height="55">
            <use xlink:href="#logo" />
          </svg>
          <span class="visually-hidden">로고</span>
        </a>

        <router-link
          to="/map"
          class="menu-item"
          title="Map"
          data-bs-toggle="tooltip"
          data-bs-placement="right"
        >
          <svg
            class="bi pe-none"
            width="24"
            height="24"
            style="margin: 5"
            role="img"
            aria-label="Map"
          >
            <use xlink:href="#Map" />
          </svg>
          <span class="menu-text">지도</span>
        </router-link>

        <router-link
          to="/othersthememap"
          class="menu-item"
          title="othersthememap"
          data-bs-toggle="tooltip"
          data-bs-placement="right"
        >
          <svg
            class="bi pe-none"
            width="24"
            height="24"
            style="margin: 5"
            role="img"
            aria-label="othersthememap"
          >
            <use xlink:href="#othersthememap" />
          </svg>
          <span class="menu-text">추천</span>
        </router-link>

        <router-link
          to="/thememap"
          class="menu-item"
          title="thememap"
          data-bs-toggle="tooltip"
          data-bs-placement="right"
        >
          <svg
            class="bi pe-none"
            width="24"
            height="24"
            style="margin: 5"
            role="img"
            aria-label="thememap"
          >
            <use xlink:href="#thememap" />
          </svg>
          <span class="menu-text">테마지도</span>
        </router-link>

        <router-link
          to="/FavoriteList"
          class="menu-item"
          title="FavoriteList"
          data-bs-toggle="tooltip"
          data-bs-placement="right"
        >
          <svg
            class="bi pe-none"
            width="24"
            height="24"
            style="margin: 5"
            role="img"
            aria-label="FavoriteList"
          >
            <use xlink:href="#FavoriteList" />
          </svg>
          <span class="menu-text">즐겨찾기</span>
        </router-link>

        <router-link
          to="/groups"
          class="menu-item"
          title="group"
          data-bs-toggle="tooltip"
          data-bs-placement="right"
        >
          <svg
            class="bi pe-none"
            width="24"
            height="24"
            style="margin: 5"
            role="img"
            aria-label="group"
          >
            <use xlink:href="#group" />
          </svg>
          <span class="menu-text">그룹</span>
        </router-link>

        <!-- 드롭다운 메뉴 -->
        <div class="mt-auto">
          <div class="dropdown border-end" ref="profileDropdown">
            <a
              href="/"
              @click.prevent="toggleDropdown"
              class="d-flex align-items-center justify-content-center p-3 link-body-emphasis text-decoration-none dropdown-toggle"
            >
              <!-- :src="profileImage ? profileImage : '/images/avatar.png'" -->
              <img
                :src="
                  profileImage
                    ? profileImage
                    : 'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/profile-image/default-profile.jpg'
                "
                alt="icon"
                width="30"
                height="30"
                class="rounded-circle"
              />
            </a>
            <ul class="dropdown-menu text-small shadow dropdown-menu-end">
              <li>
                <v-dialog v-model="modifyMyInfo" persistent width="500">
                  <template v-slot:activator="{ props }">
                    <!-- @click="profileClickHandler" -->
                    <a class="dropdown-item" v-bind="props" href="#"
                      >내 정보 수정</a
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
                        <div class="signup-profile" style="padding-left: 160px">
                          <!-- <v-file-input
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
                          </a> -->
                        </div>

                        <!-- <v-text-field
                          v-show="showAuthEmail"
                          v-model="authEmail"
                          color="primary"
                          label="Email"
                          variant="underlined"
                          :rules="[rules.email]"
                          :loading="showAuthEmailBtDisable"
                          ref="authEmail"
                          style="text-align: right"
                        ></v-text-field> -->

                        <!-- <v-text-field
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
                        ></v-text-field> -->

                        <!-- <v-text-field
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
                        ></v-text-field> -->

                        <!-- <v-text-field
                          v-model="signupNickName"
                          color="primary"
                          label="Nickname"
                          variant="underlined"
                          ref="signupNickName"
                          @keyup="editNicknameHandler"
                        ></v-text-field> -->

                        <!-- <v-btn
                          v-show="!nickduplication"
                          :disabled="signupNickName.length < 1"
                          size="small"
                          color="blue-darken-1"
                          variant="tonal"
                          @click="nickNameDuplicationHandler"
                          style="left: 290px"
                        >
                          닉네임 중복확인
                        </v-btn> -->
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <!-- <v-btn
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
                      </v-btn> -->
                      <!-- <v-btn color="blue-darken-1" @click="initFindPwdHandler">
                        취소
                      </v-btn> -->
                      <v-spacer></v-spacer>
                    </v-card-actions>
                    <br />
                  </v-card>
                </v-dialog>
                <!-- =========================================================== -->
              </li>
              <!-- <li><hr class="dropdown-divider" /></li> -->
              <li>
                <a @click="logoutClickHandler" class="dropdown-item" href="#"
                  >로그아웃</a
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </main>
  </div>
  {{ userInfo }}
</template>

<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";

export default {
  name: "Menubar",
  data() {
    return {
      profileImage: "",
      modifyMyInfo: false,
    };
  },
  computed: {
    ...mapActions(["logOut"]),
    ...mapState(["userInfo"]),
  },
  async beforeCreate() {
    await this.$store.dispatch("getUserInfo");
    this.profileImage = this.userInfo.profileImage;
  },
  methods: {
    logoutClickHandler() {
      this.logOut;
      location.href = "/";
    },
    profileClickHandler() {
      alert("프로필 수정 모달창");
      // this.$router.push("/nickchange");
    },
    toggleDropdown() {
      const dropdown = this.$refs.profileDropdown;

      if (dropdown) {
        if (!localStorage.getItem("mapbegoodToken")) {
          // If the user is not logged in, redirect to the Login.vue component
          this.$router.push("/login");
        } else {
          // If the user is logged in, toggle the dropdown
          const instance = new bootstrap.Dropdown(dropdown);
          instance.toggle();
        }
      }
    },
  },
};
</script>
<script setup>
import * as bootstrap from "bootstrap";
// 툴팁 초기화 스크립트 코드를 여기에 둡니다.
var tooltipTriggerList = [].slice.call(
  document.querySelectorAll('[data-bs-toggle="tooltip"]')
);
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl);
});
</script>

<style scoped>
main {
  position: fixed;
  top: 0;
  left: 0;
  z-index: 1000;
  background-color: #f2f2f2;
  /* width: 63px; */
  height: 100vh;
  overflow: visible;
  border-right: 1px solid #ccc; /* 테두리를 통해 다른 컴포넌트와 구분선 생성 */
  height: -webkit-fill-available;
  max-height: 100vh;
  /* overflow-x: hidden;
  overflow-y: hidden; */
}

.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0.5rem;
  color: #333;
  text-decoration: none;
}

.menu-text {
  font-size: 10px; /* You can adjust the font size as needed */
}

.menu-item svg {
  margin-bottom: 1.8rem;
}

.d-nav-link {
  padding: 22px 0 !important; /* Set padding to 0 */
}

.dropdown-menu {
  min-width: 50px !important; /* Adjust the width as needed */
}

.dropdown-item {
  font-size: 11px; /* You can adjust the font size as needed */
}

.nav-link {
  color: #333;
}

body,
html {
  min-height: 100vh;
  min-height: -webkit-fill-available;
}
</style>
