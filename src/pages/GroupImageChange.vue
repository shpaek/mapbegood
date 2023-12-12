<template lang="">
  <form class="imagechange" @submit.prevent="imagechangeFormSubmitHandler">
      <h2>그룹이미지 변경</h2>
      <div class="fill">
          <div class="before">
              <ul class="before">
                  <li>
                      <label>기존 이미지:</label>&nbsp;
                  </li>
                  <li>
                      <!-- <img :src="'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/'+this.groupId+'_groupImage.jpg'" alt="기존이미지"> -->
                      <img :src="'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/' + this.groupId + '_groupImage.jpg?' + new Date().getTime()" alt="기존이미지">
                  </li>
              </ul>
          </div>
          <div class="svg">
              <!-- 화살표 -->
              <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                  <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z"/>
              </svg>
          </div>
          <div class="after">
              <ul class="after">
                  <li class="for-gap">
                      <label for="i">변경할 이미지:</label>&nbsp;
                  </li>
                  <li>
                      <img class="img" :src="image" alt="변경할이미지">
                  </li>
                  <li>
                      <input type="file" name="image" id="i" required
                      @change="imageChangeHandler"/>
                      <!-- <v-file-input label="변경할 이미지:"></v-file-input> -->
                  </li>
                  <li>
                      <div v-if="fileErrorMsg">{{ fileErrorMsg }}</div>
                  </li>
              </ul>
          </div>
      </div>
      <br>
      <div class="button-container">
          <button type="button" id="b1" @click="b1ClickHandler">돌아가기</button>&nbsp;&nbsp;&nbsp;
          <button type="submit" id="b2">변경하기</button>
      </div>
  </form>
</template>
<script>
import axios from 'axios';
export default {
  name:"GroupImageChange",
  data(){
      return{
          groupId: 0,
          groupName: '',
          leaderNickname: '',
          image: '../../../public/images/defaultGroupProfile.jpg',
          groupImage: '',
      }
  },
  created() {
      // $route.params를 통해 전달된 파라미터 확인
      const groupId = this.$route.params.groupId;
      const groupName = this.$route.params.groupName;
      const leaderNickname = this.$route.params.leaderNickname;
      this.groupId = groupId
      this.groupName = groupName
      this.leaderNickname = leaderNickname

      // 확인한 값들을 사용하거나 로그에 출력
      // console.log(groupId, groupName, leaderNickname);
  },
  methods: {
      b1ClickHandler() { //변경 취소 버튼 클릭 시
          this.$router.go(-1); // 뒤로가기
      },
      imageChangeHandler(e) {
          const url = URL.createObjectURL(e.target.files[0]) //<input type="file">선택된 파일자원
          this.image = url //$('form.signup img.profile').attr('src', url)

          const fileInput = e.target;
          const file = fileInput.files[0];

          // 이미지 파일만 허용
          const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png'];
          if (!allowedTypes.includes(file.type)) {
              this.fileErrorMsg = '이미지 파일만 허용됩니다.';
              fileInput.value = ''; // 파일 선택 초기화
              return;
          }

          // 파일 크기 제한 (2MB)
          const maxSize = 2 * 1024 * 1024; // 2MB
          if (file.size > maxSize) {
              this.fileError = '파일 크기는 2MB를 초과할 수 없습니다.';
              fileInput.value = ''; // 파일 선택 초기화
              return;
          }

          // 유효성 검사를 통과한 경우
          this.fileErrorMsg = null;
      },
      imagechangeFormSubmitHandler(e) {//그룹 생성 버튼 클릭 시
          //axios로 백 url요청
          const url = `${this.backURL}/group/${this.groupId}/group-image` //`${this.backURL}/group`

          const fd = new FormData(e.target)

          const accessToken = "Bearer "+localStorage.getItem("mapbegoodToken")
          axios.defaults.headers.common["Authorization"] = accessToken;

          axios.put(url, fd, { contentType: false, processData: false, withCredentials: true })
              .then(response => {
                  alert("그룹 이미지가 변경되었습니다")
                  
                  this.$router.push({
                      name: '/group', // 라우터에서 정의한 이름
                      params: { //params로 설정하여 아래의 데이터 전부 전달가능
                          groupId: this.groupId,
                          groupName: this.name,
                          leaderNickname: this.leaderNickname
                      }
                  });
              })
              .catch(error => {
                  alert("그룹 이미지를 변경하지 못했습니다")
              })
      },
  }
}
</script>
<style scoped>
div.fill {
  display: flex;
  align-items: center;
  /* 세로 방향 가운데 정렬 추가 */
  margin-top: 100px;
  border: 1px solid lightgray;
  width: 730px;
  height: 350px;
  margin-left: auto;
  margin-right: auto;
}

div.button-container {
  display: flex;
  justify-content: center;
  margin-top: 100px;
}
img {
  /* 화면에서 보여줄 사이즈로 조정 */
  max-width: 150px;
  max-height: 150px;
  min-height: 150px;
  min-width: 150px;
  
}

ul {
  list-style-type: none;
  padding-left: 100px;
  margin: 0;
}

ul.after{
  padding-top:27px;
}

.svg{
  margin-left:100px;
}
</style>