<!-- ThememapCreate.vue -->
<template>
  <div v-show="isCreateOpen" class="modal" @click="backClickHandler">
    <v-sheet :elevation="18" :height="500" :width="1000" rounded @click.stop>
      <!-- 여기에 기존코드 넣기 -->
      <div class="bg-dark">
        <div class="container my-5">
          <div class="card text-black bg-light p-4 rounded">
            <h1 class="text-primary mb-4">테마지도 생성하기</h1>

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
                id="colorSelector"location.href='/groups'";
                name="colorSelector"
                class="form-select"
                ref="colorSelector"
              >
                <option v-for="color in colors" :key="color" :value="color">
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
              <label class="form-check-label text-black" for="mainmapCheckbox"
                >메인맵으로 설정</label
              >
            </div>

            <!-- 테마맵 생성 버튼 -->
            <button @click="createThemeMap" class="btn btn-dark">
              테마맵 생성
            </button>
          </div>
        </div>
      </div>
    </v-sheet>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  name: "ThememapCreate",
  props: {
    isCreateOpen: Boolean,
  },
  data() {
    return {
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
    };
  },
  methods: {
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
          console.log(response.data);
          // 성공적으로 생성되었을 때의 로직 추가
          Swal.fire({
            text: `${this.themeName}이 성공적으로 생성되었습니다.`,
            icon: "success",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              // Thememap.vue로 자동으로 이동
              // this.$router.push({ name: "thememap" });
              this.$emit("close-create");
            }
          });
        })
        .catch((error) => {
          // console.error(error);
          // alert(error);
          Swal.fire({
            text: "테마지도 생성에 실패했습니다",
            icon: "error",
            showDenyButton: true,
            denyButtonText: "확인",
          })
        });
    },
    backClickHandler() {
      this.$emit("close-create");
    },
  },
};
</script>

<style scoped>
/* Additional styles go here */
/* font-family : 'Open+Sans', sans-serif; */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}
</style>
