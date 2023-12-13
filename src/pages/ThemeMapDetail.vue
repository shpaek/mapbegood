<template>
  <div class="content">
    <h1>테마지도 상세 정보</h1>
    <div>
      <h3>태마리스트 이름: {{ mymapdetail.themeMapDto.name }}</h3>
      <p>태마리스트 내용:{{ mymapdetail.themeMapDto.memo }}</p>
      <p>태마리스트 아이디: {{ mymapdetail.themeMapDto.id }}</p>
      <!-- Add other details as needed -->
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      mymapdetail: {
        themeMapDto: {
          name: '',
          memo: '',
          id: '',
          // 필요에 따라 다른 속성 추가
        },
      },
    };
  },
  mounted() {
    // 라우터에서 테마맵 ID를 받아와서 상세 정보를 불러옵니다.
    const themeMapId = this.$route.params.id;
    this.loadThemeMapDetail(themeMapId);
  },
  methods: {
    async loadThemeMapDetail(themeMapId) {
      const url = `${this.backURL}/mymap/${themeMapId}`;

      try {
        const response = await axios.get(url, {
          withCredentials: true,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('mapbegoodToken')}`,
          },
        });

        // 받은 데이터를 처리하고 mymapdetail 객체를 채웁니다.
        this.mymapdetail = {
          themeMapDto: response.data,
        };
      } catch (error) {
        console.error(error);
        alert(error.response.data.message || '테마 맵 세부 정보를 불러오지 못했습니다.');
      }
    },
  },
};
</script>