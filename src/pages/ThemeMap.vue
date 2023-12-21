<template>
  <div class="section-container">
    <div class="g-part">
      <div class="search-wrapper" style="max-width: 600px; margin: 0 auto">
        <div class="header-container" style="display:flex;">
          <a href="/thememap" 
            style="color:#000;text-decoration:none;margin-top:25px;">
            <span class="theme-list" style="font-weight:bold;font-size:25px;margin-left:30px;padding-top:15px;">나의 테마지도</span>
          </a>
        </div>
        <v-dialog v-model="themeMapAddDialog" persistent width="500">
          <template v-slot:activator="{ props }">
            <button class="btn btn-outline-dark" v-bind="props"
              style="display:inline-block;" @click="openCreateThemeMapDialog">
              추가
            </button>
          </template>
          <v-card>
            <v-card-title style="display:flex;">
              <span class="text-create-thememap" style="font-weight:bold;margin-left:auto;margin-right:auto;">테마지도 생성</span>
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
            <div class="btn-create-thememap" style="display:flex;">
              <!-- 테마맵 생성 버튼 -->
              <span @click="createThemeMap" class="create-thememap">생성</span>
              <span class="cancel-thememap" @click="cancelThemeMapAdd">취소</span>
            </div>
            <br />
          </v-card>
        </v-dialog>

        <!-- 각 테마맵에 대한 반복문 -->
        <div style=" position: absolute; width: 390px; height: 85vh; overflow-y: auto; overflow-x: hidden;">
          <div v-for="thememap in favoriteList" :key="thememap.themeMapDto.id">
            <ul class="elevated-list mb-4" style="margin-left: 10px; margin-right: 10px">
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
                      <v-card-title style="display:flex;">
                        <span class="text-create-thememap" style="font-weight:bold;margin-left:auto;margin-right:auto;">테마지도 수정</span>
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

                          <div class="mb-4">
                            <label class="form-label text-black"
                              >테마 색상</label
                            >
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
                      <div class="btn-update-thememap">
                        <!-- 테마맵 수정 버튼 -->
                        <span @click="updateThemeMap(thememap.themeMapDto)" class="update-thememap">
                          수정
                        </span>
                        <span class="cancel-thememap" @click="cancelThemeMapEdit(thememap.themeMapDto)">
                          취소
                        </span>
                      </div>
                      <br/>
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
                  <!-- <button @click="copyThememap(thememap.themeMapDto.id)" class="btn btn-outline-secondary">
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
      themeMapId: null,
      themeMapAddDialog: true,
      themeName: "",
      // selectedColor: "",
      themeMemo: "",
      isThemePublic: false,
      selectedColor: "",
      isMainMap: false,
      markerImage: "",
      themeMapAddDialog: false,
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
        this.$nextTick(() => {
      this.$refs.themeName.focus();
    });
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
          // this.cancelThemeMapAdd();
          this.loadMymapList();
        })
        .catch((error) => {
          console.error(error);
        });
    },

    cancelThemeMapAdd() {
      this.themeMapAddDialog = false;
      this.cancelCreateThemeMap();
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
        color: this.selectedColor,
        memo: themeMapDto.memo,
        show: themeMapDto.show,
        mainmap: themeMapDto.mainmap,
      };

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;
      console.log(updatedThemeMapDto);
      axios
        .put(url, updatedThemeMapDto)
        .then((response) => {
          console.log(response.data);
          Swal.fire({
            text: "테마맵이 성공적으로 수정되었습니다.",
            icon: "success",
          });
          this.cancelThemeMapEdit(themeMapDto);
          this.loadMymapList();
        })
        .catch((error) => {
          console.error(error);
          Swal.fire({ text: "테마맵 수정에 실패했습니다.", icon: "error" });
          this.cancelThemeMapEdit(themeMapDto);
        });
    },

    cancelThemeMapEdit(themeMapDto) {
      themeMapDto.themeMapEditDialog = false;
    },

    openCreateThemeMapDialog() {
      this.themeMapAddDialog = true;
    },

    selectColor(color) {
      this.selectedColor = color;
      switch (color) {
        case "red":
          // this.markerImage = "/public/images/";
          this.themeMapDto.color = "red";
          break;
        case "yellow":
          this.themeMapDto.color = "yellow";
          break;
        case "green":
          this.themeMapDto.color = "green";
          break;
        case "blue":
          this.themeMapDto.color = "blue";
          break;
        case "indigo":
          this.themeMapDto.color = "indigo";
          break;
        case "purple":
          this.themeMapDto.color = "purple";
          break;
        case "pink":
          this.themeMapDto.color = "pink";
          break;
        case "gray":
          this.themeMapDto.color = "gray";
          break;
        case "black":
          this.themeMapDto.color = "black";
          break;
        default:
          this.themeMapDto.color = "default"; // 기본값 설정
      }
    },

    cancelCreateThemeMap() {
      this.themeMapAddDialog = false;
  this.themeName = "";
  this.selectedColor = "";
  this.themeMemo = "";
  this.isThemePublic = false;
  this.isMainMap = false;
    },

    // cancelThemeMapEdit() {
    //   // 취소 버튼 클릭 시 다이얼로그를 닫습니다.
    //   this.themeMapEditDialog = false;
    // },
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
  opacity: 1; /* 선택된 경우에만 표시되도록 설정 */
}
div.btn-create-thememap>span.create-thememap{
  margin-left:200px;
}
div.btn-create-thememap>span.cancel-thememap{
  margin-left:50px;
}
div.btn-create-thememap>span:hover{
  font-weight: bold;
}
div.btn-update-thememap>span.update-thememap{
  margin-left:200px;
}
div.btn-update-thememap>span.cancel-thememap{
  margin-left:50px;
}
div.btn-update-thememap>span:hover{
  font-weight: bold;
}
</style>
