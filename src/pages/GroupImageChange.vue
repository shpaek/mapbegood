<template lang="">
  <div v-show="isImageChangeOpen" class="modal" @click="backClickHandler">
    <v-sheet :elevation="18" :height="450" :width="400" rounded @click.stop>
      <form class="imagechange" @submit.prevent="imagechangeFormSubmitHandler">
        <div class="space"><span>그룹이미지 변경</span></div>
        <div class="fill">
          <img class="img" :src="image" alt="변경할이미지" />
          <input type="file" name="image" id="i"
            required @change="imageChangeHandler" />
          <div class="errorMsg" v-show="fileErrorMsg"><span>{{ fileErrorMsg }}</span></div>
        </div>
        <div class="button-container">
          <button type="button" id="b1" @click="b1ClickHandler">돌아가기</button>
          <button type="submit" id="b2">변경하기</button>
        </div>
      </form>
    </v-sheet>
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "GroupImageChange",
  props:{
    isImageChangeOpen: Boolean,
  },
  data() {
    return {
      groupId: 0,
      groupName: "",
      leaderNickname: "",
      image: "../../../public/images/defaultGroupProfile.jpg",
      groupImage: "",
      fileErrorMsg: '',
    };
  },
  created() {
    // $route.params를 통해 전달된 파라미터 확인
    const groupId = this.$route.params.groupId;
    const groupName = this.$route.params.groupName;
    const leaderNickname = this.$route.params.leaderNickname;
    this.groupId = groupId;
    this.groupName = groupName;
    this.leaderNickname = leaderNickname;

  },
  methods: {
    b1ClickHandler() {
      this.$emit('close-Image');
    },
    imageChangeHandler(e) {
      const url = URL.createObjectURL(e.target.files[0]); //<input type="file">선택된 파일자원
      this.image = url; //$('form.signup img.profile').attr('src', url)

      const fileInput = e.target;
      const file = fileInput.files[0];

      // 이미지 파일만 허용
      const allowedTypes = ["image/jpeg", "image/jpg", "image/png"];
      if (!allowedTypes.includes(file.type)) {
        this.fileErrorMsg = "이미지 파일만 허용됩니다.";
        fileInput.value = ""; // 파일 선택 초기화
        return;
      }

      // 파일 크기 제한 (2MB)
      const maxSize = 5 * 1024 * 1024; // 5MB
      if (file.size > maxSize) {
        this.fileErrorMsg = "파일 크기는 5MB를 초과할 수 없습니다.";
        fileInput.value = ""; // 파일 선택 초기화
        return;
      }

      // 유효성 검사를 통과한 경우
      this.fileErrorMsg = null;
    },
    imagechangeFormSubmitHandler(e) {
      //그룹 생성 버튼 클릭 시
      //axios로 백 url요청
      const url = `${this.backURL}/group/${this.groupId}/group-image`; //`${this.backURL}/group`

      const fd = new FormData(e.target);

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .put(url, fd, {
          contentType: false,
          processData: false,
          withCredentials: true,
        })
        .then((response) => {
          location.reload();
          this.$emit('close-Image');
        })
        .catch((error) => {
          console.log(error);
          alert("그룹 이미지를 변경하지 못했습니다");
        });
    },
  },
};
</script>
<style scoped>
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
div.space{
    display: flex;
}
div.errorMsg{
    display: flex;
}
div.errorMsg>span{
    margin-left: auto;
    margin-right: auto;
    margin-top:18px;
    color:red;
}
div.space>span{
    margin-left: auto;
    margin-right: auto;
    margin-top: 15px;
    margin-bottom:40px;
    font-size: 18px;
    font-weight: bold;
}
div.fill{
  height: 260px;
}
div.fill>img.img {
    display:flex;
    margin-left: auto;
    margin-right:auto;
    max-width: 200px;
    max-height: 200px;
    min-height: 200px;
    min-width: 200px;
    margin-bottom: 10px;
}
div.fill>input{
    margin-left: 25px;
}
div.button-container{
    display: flex;
    margin-top:50px;
}
div.button-container>button{
    margin-left: auto;
    margin-right:auto;
}
div.button-container>button:hover{
    font-weight: bold;
}
</style>
