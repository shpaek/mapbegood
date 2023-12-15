<template>
  <div class="bg-dark">
    <div class="container my-5">
      <div class="card text-black bg-light p-4 rounded">
        <h1 class="text-primary mb-4">테마지도 수정하기</h1>

        <!-- 테마 이름 입력 -->
        <div class="mb-3">
          <label for="themeName" class="form-label text-black">테마 이름</label>
          <input v-model="themeMapDto.name" id="themeName" name="themeName" type="text" class="form-control">
        </div>

        <!-- 색상 선택 드롭다운 -->
        <div class="mb-3">
          <label for="colorSelector" class="form-label text-black">테마 색상 선택</label>
          <select v-model="themeMapDto.color" id="colorSelector" name="colorSelector" class="form-select">
            <option v-for="color in colors" :key="color" :value="color">{{ color }}</option>
          </select>
        </div>

        <!-- 테마 메모 입력 -->
        <div class="mb-3">
          <label for="themeMemo" class="form-label text-black">테마 메모</label>
          <textarea v-model="themeMapDto.memo" id="themeMemo" name="themeMemo" rows="4" class="form-control"></textarea>
        </div>

        <!-- 테마 공개 여부 체크박스 -->
        <div class="mb-3 form-check">
          <input v-model="themeMapDto.show" type="checkbox" class="form-check-input" id="showCheckbox">
          <label class="form-check-label text-black" for="showCheckbox">테마 공개 여부</label>
        </div>

        <!-- 메인맵 여부 체크박스 -->
        <div class="mb-3 form-check">
          <input v-model="themeMapDto.mainmap" type="checkbox" class="form-check-input" id="mainmapCheckbox">
          <label class="form-check-label text-black" for="mainmapCheckbox">메인맵으로 설정</label>
        </div>

        <!-- 테마맵 수정 버튼 -->
        <button @click="updateThemeMap" class="btn btn-dark">테마맵 수정</button>
   
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ThememapUpdate',
  data() {
    return {
      themeMapDto: {
        id: null,
        name: '',
        color: '',
        memo: '',
        show: false,
        mainmap: false,
      },
      colors: ['red', 'yellow', 'green', 'blue', 'indigo', 'purple', 'pink', 'gray', 'black'],
    };
  },
  mounted() {
    this.loadThemeMapDetails();
  },
  methods: {
    loadThemeMapDetails() {
      if (!this.$route.params.id) {
        console.error("ID가 정의되지 않았습니다.");
        return;
      }
      const url = `${this.backURL}/mymap/${this.$route.params.id}`;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios.get(url, { withCredentials: true })
        .then(response => {
          this.themeMapDto = response.data;
        })
        .catch(error => {
          console.error(error);
          alert(error.msg);
        });
    },
    updateThemeMap() {
      const url = `${this.backURL}/mymap/update/${this.themeMapDto.id}`;
      const updatedThemeMapDto = this.themeMapDto;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios.put(url, updatedThemeMapDto)
        .then(response => {
          console.log(response.data);
          alert("테마맵이 성공적으로 수정되었습니다.");
        })
        .catch(error => {
          console.error(error);
          alert("테마맵 수정에 실패했습니다.");
        });
    },
  },
};
</script>