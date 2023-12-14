<template>
  <div class="bg-gray-50">
    <div class="max-w-md mx-auto my-10 bg-white p-6 rounded-lg shadow">
      <h1 class="text-2xl font-semibold mb-4">그룹테마지도 생성하기</h1>

      <!-- 그룹 테마 이름 입력 -->
      <label for="themeName" class="block text-sm font-medium text-gray-700">그룹테마 이름</label>
      <input v-model="groupmapname" id="themeName" name="themeName" type="text" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">

      <!-- 색상 선택 드롭다운 -->
      <label for="colorSelector" class="block text-sm font-medium text-gray-700 mt-4">그룹테마 색상 선택</label>
      <select v-model="selectedColor" id="colorSelector" name="colorSelector" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">
        <option v-for="color in colors" :key="color" :value="color">{{ color }}</option>
      </select>

      <!-- 테마 메모 입력 -->
      <label for="themeMemo" class="block text-sm font-medium text-gray-700 mt-4">그룹테마 메모</label>
      <textarea v-model="groupmapMemo" id="themeMemo" name="themeMemo" rows="4" class="mt-1 p-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300"></textarea>

      <!-- 테마맵 생성 버튼 -->
      <button @click="CreateGroupMap" class="btn btn-dark">테마맵 생성</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "addgroupmap",
  data() {
    return {
      colors: ['red', 'yellow', 'green', 'blue', 'indigo', 'purple', 'pink', 'gray', 'black'],
      selectedColor: '',
      groupmapMemo: '',
      groupmapname: ''
    };
  },
  methods: {
   async CreateGroupMap() {
      try {
          // 현재 라우트에서 추출한 groupId 사용
         const groupId = this.$route.params.groupId;
          // 사용자 입력을 이용해 groupthememapDto 생성
          const groupthemeMapDto = {
            name: this.groupmapname,
            color: this.selectedColor,
            memo: this.groupmapMemo
        };

       // 백엔드로 그룹 테마지도 생성 요청 보내기
       const response = await axios.post(`http://localhost:8080/ourmap/create/${groupId}`, groupthemeMapDto, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("mapbegoodToken")
          }
        });

        // 응답이 정상적으로 오면 성공 메시지 출력 또는 추가 작업 수행
        console.log("Group Theme Map created:", response.data);
        alert("그룹테마맵이 생성되었습니다.");
      } catch (error) {
        // 오류 처리
        console.error("Error creating Group Theme Map:", error);
      }
    }
  }
};
    

</script>