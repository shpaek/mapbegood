<template>
  <div class="section-container">
    <div class="g-part">
      <div class="search-wrapper" style="max-width: 600px; margin: 0 auto">
        <div class="header-container" style="display: flex">
          <a
            href="/thememap"
            style="color: #000; text-decoration: none; margin-top: 25px"
          >
            <span
              class="theme-list"
              style="
                font-weight: bold;
                font-size: 25px;
                margin-left: 30px;
                padding-top: 15px;
              "
              >나의 테마지도</span
            >
          </a>
        </div>
        <v-dialog v-model="themeMapAddDialog" persistent width="500">
          <template v-slot:activator="{ props }">
            <div
              class="new-thememap cursor-pointer btn btn-outline-dark"
              v-bind="props"
            >
              <span class="new">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="19"
                  height="19"
                  fill="currentColor"
                  class="bi bi-plus-circle-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3z"
                  /></svg
                >&nbsp;&nbsp;
              </span>
              <span class="text">테마지도 추가</span>
            </div>
          </template>
          <v-card>
            <v-card-title style="display: flex">
              <span
                class="text-create-thememap"
                style="font-weight: bold; margin-left: auto; margin-right: auto"
                >테마지도 생성</span
              >
            </v-card-title>

            <v-card-text>
              <v-container>
                <!-- 테마 이름 입력 -->
                <div class="mb-3">
                  <label for="themeName" class="form-label text-black"
                    >테마 이름</label
                  >
                  <input
                    v-model="themeName"
                    id="themeName"
                    name="themeName"
                    type="text"
                    class="form-control"
                    ref="themeName"
                  />
                </div>

                <div class="mb-4">
                  <label class="form-label text-black">테마 색상</label>
                  <div class="flex space-x-2">
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'red',
                      }"
                      @click="selectColor('red')"
                      class="bg-red-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'yellow',
                      }"
                      @click="selectColor('yellow')"
                      class="bg-yellow-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'green',
                      }"
                      @click="selectColor('green')"
                      class="bg-green-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'blue',
                      }"
                      @click="selectColor('blue')"
                      class="bg-blue-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'indigo',
                      }"
                      @click="selectColor('indigo')"
                      class="bg-indigo-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'purple',
                      }"
                      @click="selectColor('purple')"
                      class="bg-purple-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'pink',
                      }"
                      @click="selectColor('pink')"
                      class="bg-pink-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'gray',
                      }"
                      @click="selectColor('gray')"
                      class="bg-gray-500"
                    ></button>
                    <button
                      :class="{
                        'color-button': true,
                        selected: selectedColor === 'black',
                      }"
                      @click="selectColor('black')"
                      class="bg-black"
                    ></button>
                  </div>
                </div>

                <!-- 테마 메모 입력 -->
                <div class="mb-3">
                  <label for="themeMemo" class="form-label text-black"
                    >테마 메모</label
                  >
                  <textarea
                    v-model="themeMemo"
                    id="themeMemo"
                    name="themeMemo"
                    rows="4"
                    class="form-control"
                  ></textarea>
                </div>

                <!-- 테마 공개 여부 체크박스 -->
                <div class="mb-3 form-check">
                  <input
                    v-model="isThemePublic"
                    type="checkbox"
                    class="form-check-input"
                    id="showCheckbox"
                  />
                  <label class="form-check-label text-black" for="showCheckbox"
                    >테마 공개 여부</label
                  >
                </div>

                <!-- 메인맵 여부 체크박스 -->
                <div class="mb-3 form-check">
                  <input
                    v-model="isMainMap"
                    type="checkbox"
                    class="form-check-input"
                    id="mainmapCheckbox"
                  />
                  <label
                    class="form-check-label text-black"
                    for="mainmapCheckbox"
                    >메인맵으로 설정</label
                  >
                </div>
              </v-container>
            </v-card-text>
            <div class="btn-create-thememap" style="display: flex">
              <!-- 테마맵 생성 버튼 -->
              <span @click="createThemeMap" class="create-thememap">생성</span>
              <span @click="cancleThemeMapAdd" class="cancel-thememap"
                >취소</span
              >
            </div>
            <br />
          </v-card>
        </v-dialog>

        <!-- 각 테마맵에 대한 반복문 -->
        <ul class="list-group">
          <li
            v-for="thememap in favoriteList"
            :key="thememap.themeMapDto.id"
            class="thememap"
          >
            <div class="info" @click="detailThememap(thememap.themeMapDto.id)">
              <div class="name">{{ thememap.themeMapDto.name }}</div>
              <div class="memo">{{ thememap.themeMapDto.memo }}</div>
              <!-- <div v-show="thememap.themeMapDto.memo == null"></div> -->
            </div>
            <!-- 공개 여부에 따라 다른 아이콘 표시 -->
            <h5>
              <span v-if="thememap.themeMapDto.show">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="20"
                  height="20"
                  fill="currentColor"
                  class="bi bi-unlock-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M11 1a2 2 0 0 0-2 2v4a2 2 0 0 1 2 2v5a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2h5V3a3 3 0 0 1 6 0v4a.5.5 0 0 1-1 0V3a2 2 0 0 0-2-2z"
                  />
                </svg>
              </span>
              <span v-else>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="20"
                  height="20"
                  fill="currentColor"
                  class="bi bi-lock-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M8 1a2 2 0 0 1 2 2v4H6V3a2 2 0 0 1 2-2zm3 6V3a3 3 0 0 0-6 0v4a2 2 0 0 0-2 2v5a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V9a2 2 0 0 0-2-2z"
                  />
                </svg>
              </span>
            </h5>
            <!-- 더보기 버튼 -->
            <div class="btn-group" role="group">
              <button
                type="button"
                class="more"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="15"
                  height="15"
                  fill="currentColor"
                  class="bi bi-three-dots"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3"
                  />
                </svg>
              </button>
              <ul class="dropdown-menu">
                <v-dialog
                  v-model="thememap.themeMapDto.themeMapEditDialog"
                  persistent
                  width="500"
                >
                  <template v-slot:activator="{ props }">
                    <li v-bind="props">
                      <!-- @click="editGroupmap(thememap.themeMapDto.id)" -->
                      수정
                    </li>
                  </template>

                  <v-card>
                    <v-card-title style="display: flex">
                      <span
                        class="text-create-thememap"
                        style="
                          font-weight: bold;
                          margin-left: auto;
                          margin-right: auto;
                        "
                        >테마지도 수정</span
                      >
                    </v-card-title>

                    <v-card-text>
                      <v-container>
                        <!-- 테마 이름 입력 -->
                        <div class="mb-3">
                          <label for="themeName" class="form-label text-black"
                            >테마 이름</label
                          >
                          <input
                            v-model="thememap.themeMapDto.name"
                            id="themeMapDto.name"
                            name="themeMapDto.name"
                            type="text"
                            class="form-control"
                            ref="themeMapDto.name"
                          />
                        </div>

                        <!-- 색상 선택 드롭다운 -->
                        <div class="mb-3">
                          <label
                            for="colorSelector"
                            class="form-label text-black"
                            >테마 색상 선택</label
                          >
                          <select
                            v-model="thememap.themeMapDto.color"
                            id="themeMapDto.color"
                            name="themeMapDto.color"
                            class="form-select"
                            ref="themeMapDto.color"
                          >
                            <option
                              v-for="color in colors"
                              :key="color"
                              :value="color"
                            >
                              {{ color }}
                            </option>
                          </select>
                        </div>

                        <!-- 테마 메모 입력 -->
                        <div class="mb-3">
                          <label for="themeMemo" class="form-label text-black"
                            >테마 메모</label
                          >
                          <textarea
                            v-model="thememap.themeMapDto.memo"
                            id="themeMapDto.memo"
                            name="themeMapDto.memo"
                            rows="4"
                            class="form-control"
                          ></textarea>
                        </div>

                        <!-- 테마 공개 여부 체크박스 -->
                        <div class="mb-3 form-check">
                          <input
                            v-model="isThemePublic"
                            type="checkbox"
                            class="form-check-input"
                            id="showCheckbox"
                          />
                          <label
                            class="form-check-label text-black"
                            for="showCheckbox"
                            >테마 공개 여부</label
                          >
                        </div>

                        <!-- 메인맵 여부 체크박스 -->
                        <div class="mb-3 form-check">
                          <input
                            v-model="isMainMap"
                            type="checkbox"
                            class="form-check-input"
                            id="mainmapCheckbox"
                          />
                          <label
                            class="form-check-label text-black"
                            for="mainmapCheckbox"
                            >메인맵으로 설정</label
                          >
                        </div>
                      </v-container>
                    </v-card-text>
                    <div class="btn-update-thememap">
                      <!-- 테마맵 생성 버튼 -->
                      <span
                        @click="updateThemeMap(thememap.themeMapDto)"
                        class="update-thememap"
                      >
                        수정</span
                      >&nbsp;&nbsp;
                      <span
                        class="cancel-thememap"
                        @click="cancleThemeMapEdit(thememap.themeMapDto)"
                      >
                        취소
                      </span>
                    </div>
                    <br />
                  </v-card>
                </v-dialog>

                <li @click="deleteThememap(thememap.themeMapDto.id)">삭제</li>
                <!-- 리스트 복사 버튼 -->
                <!-- <button @click="copyThememap(thememap.themeMapDto.id)" class="btn btn-outline-secondary">
                  복사
                </button> -->
              </ul>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="m-part">
      <Detailmap />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Detailmap from "./Detailmap.vue";
import Swal from "sweetalert2";

export default {
  name: "FavoriteList",
  components: {
    Detailmap,
  },
  data() {
    return {
      favoriteList: [],
      themeMapId: null, // 초기값 추가

      themeMapAddDialog: false,

      colors: [
        "red",
        "yellow",
        "green",
        "blue",
        "indigo",
        "purple",
        "pink",
        "gray",
        "black",
      ],
      themeName: "",
      selectedColor: "",
      themeMemo: "",
      isThemePublic: false,
      isMainMap: false,

      // themeMapEditDialog: false,

      themeMapDto: {
        themeMapEditDialog: false,
        id: null,
        name: "",
        color: "",
        memo: "",
        show: false,
        mainmap: false,
      },
    };
  },

  created() {
    this.loadMymapList();
  },

  methods: {
    loadMymapList() {
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(
          "http://ec2-15-164-213-115.ap-northeast-2.compute.amazonaws.com:8080/mymap/list",
          { withCredentials: true }
        )
        .then((response) => {
          console.log(response.data);
          const themeMapList = response.data;
          this.favoriteList = themeMapList.map((themeMap) => {
            return {
              themeMapDto: themeMap,
            };
          });
        })
        .catch((error) => {
          console.error(error);
          Swal.fire({
            text: "로그인이 필요한 서비스 입니다.",
            icon: "warning",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              location.href = "/login";
            }
          });
        });
    },

    detailThememap(themeMapId) {
      this.$router.push({
        name: "thememapdetail",
        params: { id: themeMapId },
      });
    },

    editThememap(themeMapId) {
      this.themeMapId = themeMapId;
      this.$router.push({
        name: "thememapupdate",
        params: { id: themeMapId },
      });
    },

    copyThememap(themeMapId) {
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      const url = `${this.backURL}/mymap/copy/${themeMapId}`;
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .post(url)
        .then((response) => {
          console.log(response.data);
          alert("테마맵이 성공적으로 복사되었습니다.");
          this.loadMymapList();
        })
        .catch((error) => {
          console.error(error);
          alert("테마맵 복사에 실패했습니다. 다시 시도하세요.");
        });
    },

    deleteThememap(themeMapId) {
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      const url = `${this.backURL}/mymap/delete/${themeMapId}`;

      Swal.fire({
        title: "삭제",
        text: "정말로 삭제하시겠습니까?",
        icon: "warning",
        showDenyButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "삭제",
        denyButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          const backURL = this.$root.backURL;
          axios
            .delete(url)
            .then((response) => {
              console.log(response.data);
              this.loadMymapList();
            })
            .catch((error) => {
              console.error(error);
              Swal.fire({ text: "삭제하지 못했습니다", icon: "error" });
            });
          Swal.fire({
            text: "삭제되었습니다",
            icon: "success",
          });
        }
      });
    },

    createThemeMap() {
      if (this.themeName == "") {
        Swal.fire({ text: "테마지도 이름을 입력해주세요.", icon: "warning" });
        this.$refs.themeName.focus();
        return;
      }
      if (this.selectedColor == "") {
        Swal.fire({ text: "테마지도 색상을 선택해 주세요.", icon: "warning" });
        this.$refs.colorSelector.focus();
        return;
      }

      // 사용자 입력을 이용해 themeMapDto 객체 생성
      const themeMapDto = {
        name: this.themeName,
        color: this.selectedColor,
        memo: this.themeMemo,
        show: this.isThemePublic,
        mainmap: this.isMainMap,
      };

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      const url = `${this.backURL}/mymap/create`;

      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .post(url, themeMapDto)
        .then((response) => {
          // console.log(response.data);
          // 성공적으로 생성되었을 때의 로직 추가
          Swal.fire({
            text: `${this.themeName}이 성공적으로 생성되었습니다.`,
            icon: "success",
          });
          // Thememap.vue로 자동으로 이동
          this.themeMapAddDialog = false;
          this.cancleThemeMapAdd();
          this.loadMymapList();
        })
        .catch((error) => {
          console.error(error);
        });
    },

    cancleThemeMapAdd() {
      this.themeMapAddDialog = false;
      this.themeName = "";
      this.selectedColor = "";
      this.themeMemo = "";
      this.isThemePublic = false;
      this.isMainMap = false;
    },

    loadThemeMapDetails(themeMapId) {
      console.log(themeMapId);
      if (!this.themeMapDto) {
        console.error("ID가 정의되지 않았습니다.");
        return;
      }
      const url = `${this.backURL}/mymap/` + themeMapId;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
        .then((response) => {
          this.themeMapDto = response.data;
          console.log(this.themeMapDto);
        })
        .catch((error) => {
          console.error(error);
          Swal.fire({ text: error.msg, icon: "error" });
        });
    },

    updateThemeMap(themeMapDto) {
      const url = `${this.backURL}/mymap/update/` + themeMapDto.id;
      // const updatedThemeMapDto = themeMapDto;

      const updatedThemeMapDto = {
        id: themeMapDto.id,
        name: themeMapDto.name,
        color: themeMapDto.color,
        memo: themeMapDto.memo,
        show: themeMapDto.show,
        mainmap: themeMapDto.mainmap,
      };

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .put(url, updatedThemeMapDto)
        .then((response) => {
          console.log(response.data);
          Swal.fire({
            text: "테마맵이 성공적으로 수정되었습니다.",
            icon: "success",
          });
          this.cancleThemeMapEdit(themeMapDto);
          this.loadMymapList();
        })
        .catch((error) => {
          console.error(error);
          Swal.fire({ text: "테마맵 수정에 실패했습니다.", icon: "error" });
          this.cancleThemeMapEdit(themeMapDto);
        });
    },

    cancleThemeMapEdit(themeMapDto) {
      themeMapDto.themeMapEditDialog = false;
    },
  },
};
</script>
<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
.m-part {
  position: absolute;
  left: 454px;
  right: 0;
  height: 100%;
}

.color-button {
  display: inline-flex;
  width: 32px;
  height: 32px;
  border-radius: 9999px;
  margin-right: 0.1rem;
  cursor: pointer;
  font-weight: bold;
  position: relative;
}

.color-button:focus {
  outline: 2px solid transparent;
  outline-offset: 2px;
}

.color-button::after {
  content: "✓";
  color: white;
  font-weight: bold;
  width: 32px;
  height: 32px;
  border-radius: 9999px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.color-button.selected::after {
  opacity: 1;
}
div.btn-create-thememap > span.create-thememap {
  margin-left: 200px;
}
div.btn-create-thememap > span.cancel-thememap {
  margin-left: 50px;
}
div.btn-create-thememap > span:hover {
  font-weight: bold;
}
div.btn-update-thememap > span.update-thememap {
  margin-left: 200px;
}
div.btn-update-thememap > span.cancel-thememap {
  margin-left: 50px;
}
div.btn-update-thememap > span:hover {
  font-weight: bold;
}

div.g-part > div.empty-msg {
  padding: 70px;
}
div.new-thememap {
  margin-top: 32px;
  margin-bottom: 5px;
  margin-left: auto;
  margin-right: auto;
  width: 330px;
  height: 50px;
  border: 1px solid grey;
  border-radius: 5px;
  display: flex;
}
div.new-thememap > span {
  line-height: 33px;
}

ul {
  list-style-type: none;
  padding-left: 0px;
}
li.thememap {
  display: flex;
  margin-left: auto;
  margin-right: auto;
  border: 1px solid grey;
  border-radius: 5px;
  height: 60px;
  width: 330px;
  vertical-align: baseline;
}
li.thememap > div.info {
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 13px;
  width: 260px;
}
li.thememap > div.info > div.name {
  font-size: 18px;
  font-weight: bold;
}
li.thememap > div.info > div.memo {
  font-size: 13px;
  color: grey;
  overflow-x: hidden;
  white-space: nowrap;
  max-width: 230px;
}
li.thememap > h5 {
  margin-top: auto;
  margin-bottom: auto;
  margin-right: 5px;
}
.dropdown-menu {
  min-width: 45px;
  min-height: 50px;
}

.dropdown-menu li {
  font-size: 12px;
  text-align: center;
}

.dropdown-menu li:hover {
  background-color: rgb(211, 211, 211);
  /* color:white; */
  cursor: pointer;
}
ul.list-group > li {
  margin-top: 15px;
}
ul.list-group > li:hover {
  background-color: rgba(
    0,
    112,
    192,
    0.329
  ); /* 좋을지도 로고 파란색, 투명도 적용 */
}

/*
.search-wrapper {
  position: absolute;
  width: 390px;
  height: 100vh;
}
.list-button-container {
  display: flex;
  justify-content: space-between; 
  align-items: center; 
}
h1.list-heading {
  color: #004080;
  font-size: 2.5rem;
  font-weight: bold;
  margin-bottom: 1.5rem;
}
ul.theme-list {
  list-style-type: none;
  padding: 0;
  border: 2px solid #4e4e52; 
  border-radius: 10px; 
  margin: 20px 0;
}
.sticker-btn {
  background-color: #4e4e52; 
  border: 1px solid #000000; 
  padding: 0.75rem 1.5rem; 
  font-size: 1rem;
  border-radius: 8px; 
  cursor: pointer;
  transition: background-color 0.3s ease; 
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000; 
  position: relative;
  top: 0; 
  right: 0; 
}
.sticker-btn:hover {
  background-color: #e2e6e8;
}

button {
  margin-right: 10px;
  margin-bottom: 0;
}
ul.elevated-list {
  list-style-type: none;
  padding: 0;
  border: 2px solid #4e4e52;
  border-radius: 10px;
  margin: 5px 0;
  background-color: #f8f9fa; 
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 400px;
}

ul {
  list-style-type: none;
  padding: 0;
  max-width: 600px;
  margin: 0;
}

li {
  border: 1px solid #ccc;
  padding: 1rem;
  margin-bottom: 0.5rem;
}

h3 {
  color: #030d18;
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}
p {
  color: #666;
  font-size: 1rem;
  margin-bottom: 0;
}
 .sidebar {
  position: fixed;
  top: 50%;
  left: 10px;
  transform: translateY(-50%);
  background-color: #f8f9fa;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
} 
.content {
  margin-left: 100px;
}
*/
</style>
