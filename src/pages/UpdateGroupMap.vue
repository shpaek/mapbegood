<template>
  <div class="bg-gray-50">
    <div class="container my-10">
      <div class="card bg-white p-6 rounded-lg shadow">
        <h1 class="text-2xl font-semibold mb-4">그룹테마지도 수정하기</h1>

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

        <!-- 테마맵 수정 버튼 -->
        <button @click="updateGroupMap" class="btn btn-dark">그룹테마맵 수정</button>
      </div>
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
      groupmapname: '',
      groupId: null,
      groupThememapId: null,
    };
  },
  mounted() {
    this.loadGroupThememapDetails();
  },
  methods: {
    loadGroupThememapDetails() {
      // Set groupId and groupThememapId based on the route params or any other logic
      this.groupId = this.$route.params.groupId;
      this.groupThememapId = this.$route.params.groupThememapId;

      const url = `http://localhost:8080/ourmap/get/${this.groupThememapId}`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios.get(url)
        .then(response => {
          this.groupmapname = response.data.name;
          this.selectedColor = response.data.color;
          this.groupmapMemo = response.data.memo;
        })
        .catch(error => {
          console.error(error);
          alert(error.msg);
        });
    },
    updateGroupMap() {
      const url = `http://localhost:8080/ourmap/update/${this.groupId}/${this.groupThememapId}`;
      const updatedGroupThememap = {
        name: this.groupmapname,
        color: this.selectedColor,
        memo: this.groupmapMemo
      };

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios.put(url, updatedGroupThememap)
        .then(response => {
          console.log(response.data);
          Swal.fire({ text: "그룹테마맵이 성공적으로 수정되었습니다.", icon: "success" });
        })
        .catch(error => {
          console.error(error);
          Swal.fire({ text: "그룹테마맵 수정에 실패했습니다.", icon: "error" });
        });
    },
  },
};
</script>
<style>

</style>