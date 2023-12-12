<template>
    <div class="bg-gray-50">
      <div class="max-w-md mx-auto my-10 bg-white p-6 rounded-lg shadow">
        <h1 class="text-2xl font-semibold mb-4">테마지도 수정하기</h1>
  
        <!-- 테마 이름 입력 -->
        <label for="themeName" class="block text-sm font-medium text-gray-700">테마 이름</label>
        <input v-model="themeMapDto.name" id="themeName" name="themeName" type="text" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">
  
        <!-- 색상 선택 드롭다운 -->
        <label for="colorSelector" class="block text-sm font-medium text-gray-700 mt-4">테마 색상 선택</label>
        <select v-model="themeMapDto.color" id="colorSelector" name="colorSelector" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">
          <option v-for="color in colors" :key="color" :value="color">{{ color }}</option>
        </select>
  
        <!-- 테마 메모 입력 -->
        <label for="themeMemo" class="block text-sm font-medium text-gray-700 mt-4">테마 메모</label>
        <textarea v-model="themeMapDto.memo" id="themeMemo" name="themeMemo" rows="4" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300"></textarea>
  
                <!-- 테마 공개 여부 체크박스 -->
        <label class="flex items-center mt-4">
        <input v-model="themeMapDto.show" type="checkbox" class="form-checkbox text-blue-500">
        <span class="ml-2 text-sm">테마 공개 여부</span>
        </label>

        <!-- 메인맵 여부 체크박스 -->
        <label class="flex items-center mt-2">
        <input v-model="themeMapDto.mainmap" type="checkbox" class="form-checkbox text-blue-500">
        <span class="ml-2 text-sm">메인맵으로 설정</span>
        </label>
        <!-- 테마맵 수정 버튼 -->
        <button @click="updateThemeMap" class="mt-4 p-2 bg-blue-500 text-white rounded-md">테마맵 수정</button>
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
            id: null, // 새로 추가한 부분
            name: '',
            color: '',
            memo: '',
            show: false,
            mainmap: false,
          }, colors: ['red', 'yellow', 'green', 'blue', 'indigo', 'purple', 'pink', 'gray', 'black'],
        };
      },
      mounted() {
        this.loadThemeMapDetails();
      },
      methods: {
        
        loadThemeMapDetails() {
             // 기존 코드에서 $route.params.id가 정의되어 있는지 확인하는 부분 추가
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
              // 수정 후 필요한 동작 수행
            })
            .catch(error => {
              console.error(error);
              alert("테마맵 수정에 실패했습니다.");
            });
        },
      },
    };
  </script>
 