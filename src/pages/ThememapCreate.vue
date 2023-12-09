<!-- ThememapCreate.vue -->
<template>
    <div class="bg-gray-50">
      <div class="max-w-md mx-auto my-10 bg-white p-6 rounded-lg shadow">
        <h1 class="text-2xl font-semibold mb-4">테마지도 생성하기</h1>
  
        <!-- 테마 이름 입력 -->
        <label for="themeName" class="block text-sm font-medium text-gray-700">테마 이름</label>
        <input v-model="themeName" id="themeName" name="themeName" type="text" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">
  
        <!-- 색상 선택 드롭다운 -->
        <label for="colorSelector" class="block text-sm font-medium text-gray-700 mt-4">테마 색상 선택</label>
        <select v-model="selectedColor" id="colorSelector" name="colorSelector" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">
          <option v-for="color in colors" :key="color" :value="color">{{ color }}</option>
        </select>
  
        <!-- 테마 메모 입력 -->
        <label for="themeMemo" class="block text-sm font-medium text-gray-700 mt-4">테마 메모</label>
        <textarea v-model="themeMemo" id="themeMemo" name="themeMemo" rows="4" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300"></textarea>
  
        <!-- 테마 공개 여부 체크박스 -->
        <label class="flex items-center mt-4">
          <input v-model="isThemePublic" type="checkbox" class="form-checkbox text-blue-500">
          <span class="ml-2 text-sm">테마 공개 여부</span>
        </label>
  
        <!-- 메인맵 여부 체크박스 -->
        <label class="flex items-center mt-2">
          <input v-model="isMainMap" type="checkbox" class="form-checkbox text-blue-500">
          <span class="ml-2 text-sm">메인맵으로 설정</span>
        </label>
  
        <!-- 테마맵 생성 버튼 -->
        <button @click="createThemeMap" class="mt-4 p-2 bg-blue-500 text-white rounded-md">테마맵 생성</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios'
  export default {
    name: "ThememapCreate",
    data() {
      return {
        colors: ['red', 'yellow', 'green', 'blue', 'indigo', 'purple', 'pink', 'gray', 'black'],
        themeName: '',
        selectedColor: '',
        themeMemo: '',
        isThemePublic: false,
        isMainMap: false,
      };
    },
    methods: {
      createThemeMap() {
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

      axios.post(url, themeMapDto)
        .then(response => {
          console.log(response.data);
          // 성공적으로 생성되었을 때의 로직 추가
          alert("thememap이 성공적으로 생성되었습니다.");
         // Thememap.vue로 자동으로 이동
        this.$router.push({ name: 'FavoriteList' });
    })

        

        .catch(error => {
          console.error(error);
          alert("thememap 생성에 실패했습니다.다시 시도하세요.")
        });
    },
  },
};
  </script>
  
  <style scoped>
  /* Additional styles go here */
  font-family: 'Open+Sans', sans-serif;
</style>