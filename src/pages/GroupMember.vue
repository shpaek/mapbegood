<template lang="">
  <h2>그룹 멤버</h2>
  <div class="group-member-container">
    <div class="icons">
      <!-- 아이콘들 -->
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="30"
        height="30"
        fill="currentColor"
        class="bi bi-list"
        viewBox="0 0 16 16"
        @click="waitinglistClickHandler"
      >
        <path
          fill-rule="evenodd"
          d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"
        />
      </svg>
      <span class="invite" v-show="isleader">
        <!-- 그룹에 초대하고 싶은 사용자를 검색해서 그룹에 초대 요청하기(waiting에 추가) -->
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-person-add"
          viewBox="0 0 16 16"
          @click="openModal"
        >
          <path
            d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0m-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0M8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4"
          />
          <path
            d="M8.256 14a4.474 4.474 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10c.26 0 .507.009.74.025.226-.341.496-.65.804-.918C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4s1 1 1 1z"
          />
        </svg>
      </span>
    </div>
    <div class="member-list">
      <hr />
      <div class="member" v-for="gm in memberList">
        <!-- <div class="guide">
                    <span>닉네임</span>
                    <span>삭제</span>
                </div> -->
        <div class="info">
          <span
            class="member"
            @click="memberdetailClickHandler(gm.member.nickname)"
          >
            <img
              :src="gm.member.profileImage"
              alt="프로필이미지"
              class="profileImage"
            />
            {{ gm.member.nickname }}
            <!-- 리더용 아이콘 -->
            <svg
              v-show="gm.leader === 1"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-patch-check"
              viewBox="0 0 16 16"
            >
              <path
                fill-rule="evenodd"
                d="M10.354 6.146a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7 8.793l2.646-2.647a.5.5 0 0 1 .708 0z"
              />
              <path
                d="m10.273 2.513-.921-.944.715-.698.622.637.89-.011a2.89 2.89 0 0 1 2.924 2.924l-.01.89.636.622a2.89 2.89 0 0 1 0 4.134l-.637.622.011.89a2.89 2.89 0 0 1-2.924 2.924l-.89-.01-.622.636a2.89 2.89 0 0 1-4.134 0l-.622-.637-.89.011a2.89 2.89 0 0 1-2.924-2.924l.01-.89-.636-.622a2.89 2.89 0 0 1 0-4.134l.637-.622-.011-.89a2.89 2.89 0 0 1 2.924-2.924l.89.01.622-.636a2.89 2.89 0 0 1 4.134 0l-.715.698a1.89 1.89 0 0 0-2.704 0l-.92.944-1.32-.016a1.89 1.89 0 0 0-1.911 1.912l.016 1.318-.944.921a1.89 1.89 0 0 0 0 2.704l.944.92-.016 1.32a1.89 1.89 0 0 0 1.912 1.911l1.318-.016.921.944a1.89 1.89 0 0 0 2.704 0l.92-.944 1.32.016a1.89 1.89 0 0 0 1.911-1.912l-.016-1.318.944-.921a1.89 1.89 0 0 0 0-2.704l-.944-.92.016-1.32a1.89 1.89 0 0 0-1.912-1.911l-1.318.016z"
              />
            </svg>
          </span>
          <span class="delete">
            <!-- v-if="isleader" > -->
            <svg
              v-show="gm.leader === 0 && isleader"
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-dash-square"
              viewBox="0 0 16 16"
              @click="memberdeleteClickHandler(gm)"
            >
              <path
                d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"
              />
              <path
                d="M4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8"
              />
            </svg>
          </span>
        </div>
      </div>
    </div>
  </div>

  <div class="modal-container">
    <!-- 모달창 -->
    <SearchMember
      :isModalOpen="isModalOpen"
      :groupId="groupId"
      @close-modal="closeModal"
    />
  </div>
</template>
<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
import SearchMember from "../pages/SearchMember.vue";

export default {
  name: "GroupMember",
  components: {
    SearchMember,
  },
  computed: {
    ...mapState(["userInfo", "isLogin"]),
  },
  data() {
    return {
      memberList: [],
      groupId: 0,
      groupName: "",
      leaderNickname: "",
      emptyMsg: "",
      isleader: false, //leader에게만 그룹의 멤버추가/삭제 가능
      selectedNickname: "",
      selectedEmail: "",
      selectedLeader: "",
      isModalOpen: false,
    };
  },
  async created() {
    await this.$store.dispatch("getUserInfo");
    //$router.parmas를 통해 전달된 파라미터 확인
    const groupId = this.$route.params.groupId;
    const groupName = this.$route.params.groupName;
    const leaderNickname = this.$route.params.leaderNickname;
    this.groupId = groupId;
    this.groupName = groupName;
    this.leaderNickname = leaderNickname;

    // 확인한 값들을 사용하거나 로그에 출력
    console.log(groupId, groupName, leaderNickname);

    //로그인한 멤버가 그룹장인 경우 isleader를 true로 주기
    if (this.userInfo.nickName == this.leaderNickname) {
      this.isleader = true;
    }
    // axios로 back에 그룹 멤버 명단 요청
    const url = `${this.backURL}/groupmember/${this.groupId}`;
    const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
    axios.defaults.headers.common["Authorization"] = accessToken;
    axios
      .get(url, {
        withCredentials: true,
        headers: { "Content-Type": "application/json" },
      })
      .then((response) => {
        const list = response.data;
        console.log(list);
        this.memberList = list;
        if (this.memberList.length < 1) {
          this.emptyMsg = "그룹의 멤버가 없습니다";
        }
      })
      .catch((error) => {
        console.log(error);
        this.emptyMsg = "그룹의 멤버를 불러올 수 없습니다";
      });
  },
  methods: {
    memberdeleteClickHandler(gm) {
      //그룹장이 멤버를 방출하는 경우
      //선택된 사용자 정보
      this.selectedNickname = gm.member.nickname;
      this.selectedLeader = gm.leader;
      this.selectedEmail = gm.member.email;

      const url = `${this.backURL}/groupmember`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      //DTO에 데이터 세팅
      const memberDTO = {
        email: this.selectedEmail,
        nickname: this.selectedNickname,
      };
      const groupsDTO = {
        id: this.groupId,
      };

      // MemberGroupDTO 생성
      const memberGroupDTO = {
        member: memberDTO,
        groups: groupsDTO,
        leader: this.selectedLeader,
      };

      const requestBody = memberGroupDTO;
      axios
        .delete(url, { data: requestBody, withCredentials: true })
        .then((response) => {
          alert("멤버가 방출되었습니다");
          window.location.reload();
        })
        .catch((error) => {
          console.log(error);
          alert("멤버가 방출되지 않았습니다");
        });
    },
    waitinglistClickHandler() {
      //그룹초대 미응답 명단
      this.$router.push({
        name: "/waiting", // 라우터에서 정의한 이름
        params: {
          //params로 설정하여 아래의 데이터 전부 전달가능
          groupId: this.groupId,
          groupName: this.groupName,
          leaderNickname: this.leaderNickname,
        },
      });
    },
    memberdetailClickHandler(nickname) {
      //그룹멤버의 상세보기로 이동
      this.selectedNickname = nickname;
      // this.selectedNickname에 값을 할당
      alert(this.selectedNickname); // 테스트를 위해 알림으로 출력
    },
    openModal() {
      //그룹에 초대하고 싶은 사용자 검색
      this.isModalOpen = true;
      console.log(this.isModalOpen);
    },
    closeModal() {
      this.isModalOpen = false;
    },
  },
};
</script>
<style scoped>

div.icons {
  width: 500px;
  margin-top: 50px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  justify-content: space-between;
}

div.member-list {
  width: 500px;
  margin-left: auto;
  margin-right: auto;
}
/* div.guide{
    display: flex;
    justify-content: space-between;
} */
div.info {
  display: flex;
  justify-content: space-between;
}
img.profileImage {
  min-width: 42px;
  min-height: 40px;
  max-width: 42px;
  max-height: 40px;
  border-radius: 50%;
}
</style>
