<!--UpdateGroupMap.vue-->
<template>
    <div class="bg-gray-50">
      <div class="max-w-md mx-auto my-10 bg-white p-6 rounded-lg shadow">
        <h1 class="text-2xl font-semibold mb-4">그룹테마지도 수정하기</h1>
  
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
  
        <!-- 테마맵 수정 버튼 -->
        <button @click="updateGroupMap" class="btn btn-primary">테마맵 수정</button>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: "updategroupmap",
    data() {
      return {
        colors: ['red', 'yellow', 'green', 'blue', 'indigo', 'purple', 'pink', 'gray', 'black'],
        selectedColor: '',
        groupmapMemo: '',
        groupmapname: ''
      };
    },
    methods: {
        updateGroupMap() {
  // groupId와 groupThememapId는 실제로 사용하는 상황에 맞게 적절한 값으로 설정해야 합니다.
  const groupId = this.$route.params.groupId;
  const groupThememapId = this.$route.params.groupThememapId;
  
  // 로컬 스토리지에서 토큰을 가져옴
  const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
  
  // Authorization 헤더에 토큰 추가
  axios.defaults.headers.common["Authorization"] = accessToken;

  // 수정할 그룹 테마맵 데이터
  const updatedGroupThememap = {
    name: this.groupmapname,
    color: this.selectedColor,
    memo: this.groupmapMemo
  };

  // API 요청을 통해 그룹 테마맵 수정
  axios.put(`http://localhost:8080/ourmap/update/${groupId}/${groupThememapId}`, updatedGroupThememap)
    .then(response => {
      // 성공적으로 수정되었을 때의 로직
      console.log('그룹 테마맵이 성공적으로 수정되었습니다.', response.data);
      alert("그룹 테마맵이 성공적으로 수정되었습니다.");
      // 수정 후 필요한 동작 수행
    })
    .catch(error => {
      // 오류 발생 시의 로직
      console.error('그룹 테마맵 수정 중 오류 발생:', error);
      alert("그룹 테마맵 수정에 실패했습니다.");
    });
}
}
  }
  </script>
  
  <style>
  /* 스타일링이 필요한 경우 추가 */
  </style>