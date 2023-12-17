<template lang="">
  <div v-show="isModalOpen" class="modal" @click="backClickHandler">
    <v-sheet :elevation="18" :height="400" :width="550" rounded @click.stop>
      <form class="search" @submit.prevent="searchUserClickHandler">
        <label for="n">닉네임: </label>&nbsp;
        <input
          type="text"
          name="nickName"
          v-model="name"
          id="n"
          required
          placeholder="초대할 사용자를 입력하세요"
        />
        <button type="submit" class="search" id="b1">검색</button>
      </form>
      <div class="result">
        <div v-for="user in userList" :key="user.id" class="member">
          <img
            :src="user.profileImage"
            alt="프로필이미지"
            class="profileImage"
          />&nbsp;
          <span>{{ user.nickName }}</span>

          <span class="svg-add">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-plus-square"
              viewBox="0 0 16 16"
              @click="inviteUserClickHandler(user)"
            >
              <path
                d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"
              />
              <path
                d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"
              />
            </svg>
          </span>
        </div>
        <div class="empty-msg">
          {{ emptyMsg }}
        </div>
      </div>
      <button type="button" class="close" id="b2" @click="b2ClickHandler">
        닫기
      </button>
    </v-sheet>
  </div>
</template>
<script>
import axios from "axios";
export default {
  name: "SearchMember",
  props: {
    isModalOpen: Boolean,
    groupId: Number,
  },
  data() {
    return {
      name: "",
      userList: [],
      emptyMsg: "",
    };
  },
  methods: {
    backClickHandler(e) {
      // // 모달 영역 외의 영역을 클릭할 때만 모달을 닫기
      // if (e.target.classList.contains('modal')) {
      //     this.$emit('close-modal'); // 모달 닫기 이벤트를 부모 컴포넌트로 전달
      // }
      window.location.reload();
    },
    b2ClickHandler() {
      // this.$emit('close-modal');
      window.location.reload();
    },
    // inputPocusHandler(){
    //     this.emptyMsg = ''
    // },
    searchUserClickHandler() {
      if (this.name.length < 1) {
        alert("최소 한글자 이상 입력하세요");
      } else {
        const url = `${this.backURL}/user?nickName=${this.name}`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;
        axios
          .get(url, {
            withCredentials: true,
            headers: { "Content-Type": "application/json" },
          })
          .then((response) => {
            this.emptyMsg = "";
            const list = response.data;
            console.log(list);
            this.userList = list;
            if (this.userList.length < 1) {
              this.emptyMsg = "해당 닉네임의 사용자가 없습니다";
            }
          })
          .catch((error) => {
            console.log(error);
            alert("사용자를 검색하지 못했습니다");
          });
      }
    },
    inviteUserClickHandler(user) {
      const email = user.email;
      const url = `${this.backURL}/waiting`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;
      const requestBody = {
        groupId: this.groupId,
        memberEmail: email,
      };
      axios
        .post(url, requestBody, {
          withCredentials: true,
          headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
          alert("초대장을 보냈습니다");
        })
        .catch((error) => {
          console.log(error);
          alert(error.response.data.message);
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

form.search {
  margin-top: 50px;
  text-align: center;
}

div.result {
  overflow-y: auto;
  margin-top: 10px;
  margin-left: 110px;
  height: 240px;
  width: 330px;
}

div.result > div.member > span.svg-add {
  margin-left: 10px;
}

button.close {
  position: absolute;
  margin-top: 10px;
  left: 50%;
  transform: translateX(-50%);
}
button.close:hover{
   font-weight: bold;
}
img.profileImage {
  min-width: 42px;
  min-height: 40px;
  max-width: 42px;
  max-height: 40px;
  border-radius: 50%;
}
</style>
