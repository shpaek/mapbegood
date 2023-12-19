<template lang="">
  <div v-show="isCreateOpen" class="modal" @click="backClickHandler">
    <v-sheet :elevation="18" :height="500" :width="400" rounded @click.stop>
      <form class="groupcreate" @submit.prevent="groupcreateFormSubmitHandler">
        <div class="space"><span>새 그룹 생성</span></div>
        <div class="fill">
          <div class="img">
            <img class="img" :src="image" alt="그룹이미지" />
            <br />
            <input
              type="file"
              name="image"
              id="i"
              required
              @change="imageChangeHandler"
            />
            <div class="errorMsg" v-show="fileErrorMsg.length > 1">
              <span>{{ fileErrorMsg }}</span>
            </div>
          </div>
          <br />
          <div class="groupName" v-show="fileErrorMsg.length < 1">
            <label for="n">그룹명</label>&nbsp;
            <input
              type="text"
              name="name"
              id="n"
              v-model="name"
              maxlength="20"
              placeholder="20자 이내로 입력하세요"
              required
            />
            <button
              type="button"
              class="btn btn-outline-dark"
              id="b3"
              @click="b3ClickHandler"
            >
              중복확인
            </button>
          </div>
        </div>
        <div class="button-container">
          <button type="button" class="cancel" id="b2" @click="b2ClickHandler">
            생성 취소
          </button>
          <button type="submit" class="create" id="b1" v-show="isDupchkOk">
            그룹 생성
          </button>
        </div>
      </form>
    </v-sheet>
  </div>
</template>
<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  name: "GroupCreate",
  props: {
    isCreateOpen: Boolean,
  },
  data() {
    return {
      image: "../../../public/images/defaultGroupProfile.jpg",
      name: "",
      fileErrorMsg: "",
      isDupchkOk: false,
    };
  },
    methods: {
      groupcreateFormSubmitHandler(e) {
        //그룹 생성 버튼 클릭 시
        //axios로 백 url요청
        const url = `${this.backURL}/group`; //`${this.backURL}/group`
        const fd = new FormData(e.target);

        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;

        axios
          .post(url, fd, {
            contentType: false,
            processData: false,
            withCredentials: true,
          })
          .then((response) => {
            Swal.fire({
            text: "그룹이 생성되었습니다",
            icon: "success",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              location.href = "/groups";
            }
          });          
        })
        .catch((error) => {
          Swal.fire({
            text: "그룹 추가에 실패했습니다",
            icon: "error",
            confirmButtonText: "확인",
          })
          // alert(error.message);
        });
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
        const maxSize = 2 * 1024 * 1024; // 2MB
        if (file.size > maxSize) {
          this.fileError = "파일 크기는 2MB를 초과할 수 없습니다.";
          fileInput.value = ""; // 파일 선택 초기화
          return;
        }

        // 유효성 검사를 통과한 경우
        this.fileErrorMsg = "";
      },
      b2ClickHandler() {
        //생성 취소 버튼 클릭 시
        console.log("b2ClickHandler");
        this.$emit("close-create");
      },
      b3ClickHandler() {
        //중복확인 버튼 클릭 시
        if (this.name.trim().length > 0) {
          const url = `${this.backURL}/group/${this.groupId}?name=${this.name}`;
          axios
            .get(url)
            .then((response) => {
              Swal.fire({ text: "사용가능한 그룹명입니다", icon: "success" });
              this.isDupchkOk = true;
            })
            .catch((error) => {
              Swal.fire({ text: "사용할 수 없는 그룹명입니다", icon: "warning" });
            });
        } else {
          Swal.fire({ text: "그룹명을 반드시 입력해주세요", icon: "warning" });
        }
      },
    },
};
</script>
<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
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
div.space {
  display: flex;
}
div.errorMsg {
  display: flex;
}
div.errorMsg > span {
  margin-left: auto;
  margin-right: auto;
  margin-top: 35px;
  color: red;
}
div.space > span {
  margin-left: auto;
  margin-right: auto;
  margin-top: 15px;
  margin-bottom: 40px;
  font-size: 18px;
  font-weight: bold;
}
div.fill {
  height: 310px;
}
div.fill > div.img > img.img {
  display: flex;
  margin-left: auto;
  margin-right: auto;
  max-width: 200px;
  max-height: 200px;
  min-height: 200px;
  min-width: 200px;
}
div.fill > div.img > input {
  margin-left: 25px;
}
div.fill > div.groupName > label {
  padding-left: 25px;
}
div.fill > div.groupName > button {
  padding: 0;
  margin-left: 10px;
  width: 80px;
}
div.button-container {
  display: flex;
  margin-top: 50px;
}
div.button-container > button {
  margin-left: auto;
  margin-right: auto;
}
div.button-container > button:hover {
  font-weight: bold;
}
</style>
