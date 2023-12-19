<!--AddGroupmap.vue-->
<template>
  <div class="bg-gray-50">
    <div class="container my-10">
      <div class="card bg-white p-6 rounded-lg shadow">
        <h1 class="text-2xl font-semibold mb-4">그룹테마지도 생성하기</h1>

        <!-- 그룹 테마 이름 입력 -->
        <div class="mb-3">
          <label for="themeName" class="form-label text-sm font-medium text-gray-700">그룹테마 이름</label>
          <input v-model="groupmapname" id="themeName" name="themeName" type="text" class="form-control">
        </div>

        <!-- 색상 선택 드롭다운 -->
        <div class="mb-3">
          <label for="colorSelector" class="form-label text-sm font-medium text-gray-700">그룹테마 색상 선택</label>
          <select v-model="selectedColor" id="colorSelector" name="colorSelector" class="form-select">
            <option v-for="color in colors" :key="color" :value="color">{{ color }}</option>
          </select>
        </div>

        <!-- 테마 메모 입력 -->
        <div class="mb-3">
          <label for="themeMemo" class="form-label text-sm font-medium text-gray-700">그룹테마 메모</label>
          <textarea v-model="groupmapMemo" id="themeMemo" name="themeMemo" rows="4" class="form-control"></textarea>
        </div>

        <!-- 테마맵 생성 버튼 -->
        <button @click="CreateGroupMap" class="btn btn-dark">테마맵 생성</button>
        <button @click="cancelCreateGroupMap" class="btn btn-light">취소</button>

      </div>
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
        
        //자동으로 GroupDetail.vue로 이동
        this.$router.push({name:"group"});

      } catch (error) {
        // 오류 처리
        console.error("생성실패:", error);
      }
    },
    cancelCreateGroupMap() {
       
      this.$router.go(-1); // Go back one step in the history stack
    }
  }
};
  

    

</script>
<style scoped>
  /* ... existing styles ... */
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
  .modal-container {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 9999; /* Adjust the z-index value as needed */
  }

  /* ... existing styles ... */
</style>