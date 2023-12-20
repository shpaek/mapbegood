<template>
  <div class="section-container">
    <div class="g-part">
      <div class="search-wrapper" style="max-width: 600px; margin: 0 auto">
        <div class="header-container">
          <a href="/thememap" style="color: #000; text-decoration: none">
            <h2 class="theme-list" style="margin-left: 10px">
              나의 테마지도
            </h2></a
          >

          <v-dialog v-model="themeMapAddDialog" persistent width="500">
            <template v-slot:activator="{ props }">
              <!-- "테마맵 추가" 버튼 -->
              <!-- @click="addNewThememap" -->

              <button
                class="btn btn-outline-secondary"
                v-bind="props"
                style="display: block; margin-left: 80%"
              >
                추가
              </button>
              <v-divider color="warningss"></v-divider>
            </template>

            <v-card>
              <v-card-title>
                <h1 class="text-primary mb-4">테마지도 생성</h1>
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

                  <!-- 색상 선택 드롭다운 -->
                  <div class="mb-3">
                    <label for="colorSelector" class="form-label text-black"
                      >테마 색상 선택</label
                    >
                    <select
                      v-model="selectedColor"
                      id="colorSelector"
                      name="colorSelector"
                      class="form-select"
                      ref="colorSelector"
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
              <v-card-actions>
                <v-spacer></v-spacer>
                <!-- 테마맵 생성 버튼 -->
                <button @click="createThemeMap" class="btn btn-dark">
                  생성
                </button>
                <button
                  data-v-fce5df64=""
                  class="btn btn-light"
                  @click="cancleThemeMapAdd"
                >
                  취소
                </button>
                <v-spacer></v-spacer>
              </v-card-actions>
              <br />
            </v-card>
          </v-dialog>
        </div>

        <!-- 각 테마맵에 대한 반복문 -->
        <div
          style="
            position: absolute;
            width: 390px;
            height: 85vh;
            overflow-y: auto;
            overflow-x: hidden;
          "
        >
          <div v-for="thememap in favoriteList" :key="thememap.themeMapDto.id">
            <ul
              class="elevated-list mb-4"
              style="margin-left: 10px; margin-right: 10px"
            >
              <li>
                <div style="display: inline-block; width: 240px">
                  <h5 @click="detailThememap(thememap.themeMapDto.id)">
                    <b>{{ thememap.themeMapDto.name }}</b>
                  </h5>
                  <p>{{ thememap.themeMapDto.memo }}</p>
                  <p v-show="thememap.themeMapDto.memo == null">&nbsp;</p>
                </div>
                <h5>
                  <!-- 공개 여부에 따라 다른 아이콘 표시 -->
                  <span v-if="thememap.themeMapDto.show">
                    <!-- ... -->
                  </span>
                  <span v-else>
                    <!-- ... -->
                  </span>
                </h5>

                <div class="text-right">
                  <!-- "상세보기" 버튼 -->
                  <!-- <button            
                  @click="detailThememap(thememap.themeMapDto.id)"
                  class="btn btn-outline-secondary"
                >
                  상세보기
                </button> -->

                  <!-- 리스트 수정 버튼 -->
                  <v-dialog
                    v-model="thememap.themeMapDto.themeMapEditDialog"
                    persistent
                    width="500"
                  >
                    <template v-slot:activator="{ props }">
                      <button
                        class="btn btn-outline-secondary"
                        v-bind="props"
                        @click="loadThemeMapDetails(thememap.themeMapDto.id)"
                      >
                        수정
                      </button>
                    </template>

                    <v-card>
                      <v-card-title>
                        <h1 class="text-primary mb-4">테마지도 수정</h1>
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
                              v-model="thememap.themeMapDto.show"
                              type="checkbox"
                              class="form-check-input"
                              id="themeMapDto.show"
                            />
                            <label
                              class="form-check-label text-black"
                              for="themeMapDto.show"
                              >테마 공개 여부</label
                            >
                          </div>

                          <!-- 메인맵 여부 체크박스 -->
                          <div class="mb-3 form-check">
                            <input
                              v-model="thememap.themeMapDto.mainmap"
                              type="checkbox"
                              class="form-check-input"
                              id="themeMapDto.mainmap"
                            />
                            <label
                              class="form-check-label text-black"
                              for="themeMapDto.mainmap"
                              >메인맵으로 설정</label
                            >
                          </div>
                        </v-container>
                      </v-card-text>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <!-- 테마맵 생성 버튼 -->
                        <button
                          @click="updateThemeMap(thememap.themeMapDto)"
                          class="btn btn-dark"
                        >
                          수정
                        </button>
                        <button
                          data-v-fce5df64=""
                          class="btn btn-light"
                          @click="cancleThemeMapEdit(thememap.themeMapDto)"
                        >
                          취소
                        </button>
                        <v-spacer></v-spacer>
                      </v-card-actions>
                      <br />
                    </v-card>
                  </v-dialog>

                  <!-- 리스트 삭제 버튼 -->
                  <button
                    @click="deleteThememap(thememap.themeMapDto.id)"
                    class="btn btn-outline-danger"
                  >
                    삭제
                  </button>

                  <!-- 리스트 복사 버튼 -->
                  <!-- <button
                    @click="copyThememap(thememap.themeMapDto.id)"
                    class="btn btn-outline-secondary"
                  >
                    복사
                  </button> -->
                </div>
              </li>
            </ul>
          </div>
        </div>
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
      const url = `${this.backURL}/mymap/list`;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
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
      axios
        .delete(url)
        .then((response) => {
          console.log(response.data);

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
                .delete(`${backURL}/myfeed/${this.myplaceId}`)
                .then((response) => {
                  this.loadMymapList();
                })
                .catch((error) => {
                  console.error("Error deleting feed:", error);
                });
              Swal.fire({
                text: "삭제되었습니다",
                icon: "success",
              });
            }
          });
        })
        .catch((error) => {
          console.error(error);
          Swal.fire({ text: "삭제하지 못했습니다", icon: "error" });
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
        })
        .catch((error) => {
          console.error(error);
          Swal.fire({ text: "테마맵 수정에 실패했습니다.", icon: "error" });
          this.cancleThemeMapEdit();
        });
    },

    cancleThemeMapEdit(themeMapDto) {
      themeMapDto.themeMapEditDialog = false;
    },
  },
};
</script>
<style scoped>
.search-wrapper {
  /* position: absolute; */
  /* left: 454px; 왼쪽 영역의 너비 만큼 이동 */
  /* right: 0; 오른쪽에 닿도록 */
  /* height: 100%; */

  position: absolute;
  width: 390px;
  height: 100vh;
  /* overflow-x: hidden;
  overflow-y: auto; */
}

.m-part {
  position: absolute;
  left: 454px; /* 왼쪽 영역의 너비 만큼 이동 */
  right: 0; /* 오른쪽에 닿도록 */
  height: 100%;
}

.list-button-container {
  display: flex;
  justify-content: space-between; /* Adjust as needed */
  align-items: center; /* Adjust as needed */
}

/* 기존 스타일 */
div.g-part > div.empty-msg {
  padding: 70px;
}

h2 {
  margin: 12px 0px 8px;
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
  border: 2px solid #4e4e52; /* Border color for the ul element */
  border-radius: 10px; /* Border radius for rounded corners */
  margin: 20px 0; /* Adjust margin as needed */
}
.sticker-btn {
  background-color: #4e4e52; /* Sticker background color */
  border: 1px solid #000000; /* Border for the sticker */
  padding: 0.75rem 1.5rem; /* Adjust padding for the sticker */
  font-size: 1rem;
  border-radius: 8px; /* Border radius for rounded corners */
  cursor: pointer;
  transition: background-color 0.3s ease; /* Add a smooth transition effect */
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000; /* Set a higher z-index to ensure it's above other elements */
  position: relative;
  top: 0; /* Adjust as needed */
  right: 0; /* Adjust as needed */
}

.sticker-btn:hover {
  background-color: #e2e6e8; /* Change background color on hover */
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
  background-color: #f8f9fa; /* Background color for the ul element */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Box shadow for elevation */
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
</style>
