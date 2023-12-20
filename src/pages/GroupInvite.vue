<template lang="">
  <div v-show="isModalOpen" class="modal" @click="backClickHandler">
    <v-sheet :elevation="18" :height="500" :width="400" rounded @click.stop>
      <div class="space">
        {{ this.$store.state.userInfo.nickName }}님을 초대한 그룹
      </div>
      <div class="result">
        <div class="group" v-for="group in groupList">
          <img
            :src="
              'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/' +
              group.id +
              '_groupImage.jpg?' +
              new Date().getTime()
            "
            alt="그룹이미지"
            class="groupImage"
          />
          <ul>
            <li>
              <span class="name">{{ group.name }}</span>
            </li>
            <li>
              <span class="nickname">{{
                group.memberGroupList && group.memberGroupList.length > 0
                  ? group.memberGroupList[0].member.nickname
                  : ""
              }}</span>
            </li>
          </ul>
          <div class="btn-container">
            <span class="accept" @click="inviteAcceptClickHandler(group)">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                fill="currentColor"
                class="bi bi-check-circle"
                viewBox="0 0 16 16"
              >
                <path
                  d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"
                />
                <path
                  d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05"
                />
              </svg>
            </span>
            <span class="reject" @click="inviteRejectClickHandler(group)">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                fill="currentColor"
                class="bi bi-x-circle"
                viewBox="0 0 16 16"
              >
                <path
                  d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"
                />
                <path
                  d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"
                />
              </svg>
            </span>
          </div>
        </div>
        <div class="empty-msg">
          <span>{{ emptyMsg }}</span>
        </div>
      </div>
      <button type="button" class="close" id="b1" @click="b1ClickHandler">
        닫기
      </button>
    </v-sheet>
  </div>
</template>
<script>
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: "GroupInvite",
  props: {
    isModalOpen: Boolean,
  },
  data() {
    return {
      groupList: [],
      emptyMsg: "",
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      const url = `${this.backURL}/waiting`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;
      axios
        .get(url, {
          withCredentials: true,
          headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
          // console.log(response.data);
          this.emptyMsg = "";
          this.groupList = response.data;
          if (this.groupList.length < 1) {
            this.emptyMsg = "새로운 초대 요청이 없습니다";
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    backClickHandler(e) {
      // 모달 영역 외의 영역을 클릭할 때만 모달을 닫기
      if (e.target.classList.contains("modal")) {
        this.$emit("close-modal"); // 모달 닫기 이벤트를 부모 컴포넌트로 전달
      }
    },
    b1ClickHandler() {
      this.$emit("close-modal");
    },
    inviteAcceptClickHandler(group) {
      // 그룹초대 수락 시 그룹 추가 후 수락대기 목록에서 제거
      const url1 = `${this.backURL}/groupmember`;
      const url2 = `${this.backURL}/waiting`;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;
      const Waiting = {
        groupId: group.id,
      };
      const MemberGroup = {
        groups: {
          id: group.id,
        },
      };
      console.log("----START-----");
      console.log(group.id);
      console.log(MemberGroup);
      console.log("----END-----");
      axios
        .post(url1, MemberGroup, {
          withCredentials: true,
          headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
          axios
            .delete(url2, { data: Waiting, withCredentials: true })
            .then((response) => {
              Swal.fire({
                text: "그룹초대 요청을 수락했습니다",
                icon: "success",
              });
              this.load();
            })
            .catch((error) => {
              console.log(error);
              Swal.fire({
                text: "그룹초대 요청을 수락하지 못했습니다",
                icon: "error",
              });
            });
        })
        .catch((error) => {
          console.log(error);
          Swal.fire({
            text: "그룹초대 요청을 수락하지 못했습니다",
            icon: "error",
          });
        });
    },
    inviteRejectClickHandler(group) {
      // 그룹초대 거절 시 수락대기 목록에서 제거
      const url = `${this.backURL}/waiting`;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      const Waiting = {
        groupId: group.id,
      };
      console.log(group.id);
      axios
        .delete(url, { data: Waiting, withCredentials: true })
        .then((response) => {
          Swal.fire({ text: "그룹초대 요청을 거절했습니다", icon: "success" });
          this.load();
        })
        .catch((error) => {
          console.log(error);
          Swal.fire({
            text: "그룹초대 요청을 거절하지 못했습니다",
            icon: "error",
          });
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
div.result {
  width: 400px;
  height: 400px;
  overflow-y: auto;
}
div.space {
  height: 50px;
  text-align: center;
  line-height: 50px;
  font-weight: bold;
  font-size: 16px;
}
div.result > div.group {
  display: flex;
  height: 60px;
  margin-left: auto;
  margin-right: auto;
}
div.result > div.group > ul {
  list-style-type: none;
  padding-left: 13px;
  margin-top: auto;
  margin-bottom: auto;
  width: 230px;
}
div.result > div.group > ul > li > span.name {
  font-size: 15px;
  font-weight: bold;
}
div.result > div.group > ul > li > span.nickname {
  font-size: 13px;
}
div.result > div.group > div.btn-container {
  display: flex;
  margin-top: auto;
  margin-bottom: auto;
}

div.result > div.group > div.btn-container > span.accept {
  cursor: pointer;
}

/* Hover 시 SVG 교체 */
div.result > div.group > div.btn-container > span.accept:hover svg {
  fill: rgb(0, 112, 192);
}
div.result > div.group > div.btn-container > span.reject {
  margin-left: 10px;
}
div.result > div.group > div.btn-container > span.reject:hover svg {
  fill: rgb(255, 41, 41);
}
img.groupImage {
  min-width: 45px;
  min-height: 45px;
  max-width: 45px;
  max-height: 45px;
  border-radius: 5px;
  margin-left: 40px;
  margin-top: auto;
  margin-bottom: auto;
}
button.close {
  position: absolute;
  margin-top: 10px;
  left: 50%;
  transform: translateX(-50%);
}
button.close:hover {
  font-weight: bold;
}
div.empty-msg {
  display: flex;
}
div.empty-msg > span {
  margin-top: 180px;
  margin-left: auto;
  margin-right: auto;
}
</style>