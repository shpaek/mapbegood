<template lang="">
  <div class="section-contaioner">
    <div class="g-part">
      <div class="icon-container">
        <span class="title">그룹 목록</span>
        <span class="check-waiting" v-show="emptyMsg.length < 1" @click="openModal">
          <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-envelope" viewBox="0 0 16 16">
            <path d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1zm13 2.383-4.708 2.825L15 11.105zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741M1 11.105l4.708-2.897L1 5.383z"/>
          </svg>
        </span>
      </div>
      <div class="group-container">
        <!-- 그룹 추가버튼 -->
        <div class="add-group" @click="addgroupClickHandler">
          <div class="new-group cursor-pointer btn btn-outline-dark"
            @click="addnewgroupclickHandler">
            <span class="new">
              <svg xmlns="http://www.w3.org/2000/svg" width="19" height="19" fill="currentColor" class="bi bi-plus-circle-fill" viewBox="0 0 16 16">
                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3z"/>
              </svg> 
            </span>&nbsp;&nbsp;
            <span class="text">그룹 추가</span>
          </div>
        </div>
        <div class="group" v-for="group in groupList" @click="groupClickHandler(group)">
          <img id="i" alt="그룹이미지" class="img-size" :src="'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/'+group.id +'_groupImage.jpg?'+new Date().getTime()"/>
          <ul>
            <li>
              <div class="group-info">{{ group.name }}</div>
              <div class="group-mem-info">{{ group.memberGroupList[0].member.nickname }}</div>
            </li>
          </ul>
        </div>
      </div>
      <div class="empty-msg">{{ emptyMsg }}</div>
    </div>
    <div class="m-part">
      <Detailmap />
    </div>
  </div>
  <!-- 모달창 -->
  <GroupInvite
    :isModalOpen="isModalOpen"
    @close-modal="closeModal"
  />
  <GroupCreate
    :isCreateOpen="isCreateOpen"
    @close-create="closeCreate"
  />
</template>
<script>
import axios from "axios";
import Detailmap from "./Detailmap.vue";
import GroupInvite from "./GroupInvite.vue";
import GroupCreate from "./GroupCreate.vue";
export default {
  name: "GroupsView",
  components: {
    Detailmap,
    GroupInvite,
    GroupCreate,
  },
  data() {
    return {
      groupList: [],
      emptyMsg: "",
      isModalOpen: false,
      isCreateOpen: false,
    };
  },
  created() {
    //axios요청
    const url = `${this.backURL}/group`;

    const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
    axios.defaults.headers.common["Authorization"] = accessToken;

    axios
      .get(url, { withCredentials: true })
      .then((response) => {
        //사용자의 그룹 목록 받기
        const list = response.data;
        console.log(list);
        this.groupList = list;
        if (this.groupList.length < 1) {
          this.emptyMsg = "소속된 그룹이 없습니다";
        }
      })
      .catch((error) => {
        console.log(error);
        alert("로그인이 필요한 서비스 입니다.");
        location.href = "/login";
      });
  },
  methods: {
    addgroupClickHandler() {
      this.isCreateOpen = true;
      console.log(this.isCreateOpen);
    },
    groupClickHandler(group) {
      //그룹의 테마그룹 List보여주는 탭으로 주소이동시키기
      console.log(group.id);
      this.$router.push({
        name: "/group", // 라우터에서 정의한 이름
        params: {
          //params로 설정하여 아래의 데이터 전부 전달가능
          groupId: group.id,
          groupName: group.name,
          leaderNickname: group.memberGroupList[0].member.nickname,
        },
      });
    },
    openModal() {
      //그룹에 초대하고 싶은 사용자 검색
      this.isModalOpen = true;
      console.log(this.isModalOpen);
    },
    closeModal() {
      this.isModalOpen = false;
    },
    closeCreate() {
      this.isCreateOpen = false;
    },
  },
};
</script>
<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
div.g-part > div.empty-msg {
  padding: 70px;
}
div.icon-container {
  display: flex;
}
div.icon-container > span.title {
  margin-left: 30px;
  margin-top: 25px;
  font-size: 25px;
  font-weight: bold;
}
div.icon-container > span.check-waiting {
  margin-left: 200px;
  margin-top: 25px;
  margin-bottom: 30px;
}

div.add-group > div.new-group {
  margin-top: 10px;
  margin-bottom: 20px;
  margin-left: auto;
  margin-right: auto;
  width: 330px;
  height: 50px;
  border: 1px solid grey;
  border-radius: 5px;
  display: flex;
}
div.add-group > div.new-group > span {
  line-height: 33px;
}
div.group {
  display: flex;
  border: 1px solid grey;
  border-radius: 5px;
  width: 330px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 15px;
  height: 70px;
}
div.group > ul {
  list-style-type: none;
  padding-left: 0px;
}
div.group-container > div.group > img.img-size {
  max-width: 50px;
  max-height: 50px;
  min-width: 50px;
  min-height: 50px;
  border-radius: 5px;
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 10px;
}
div.group-container > div.group > ul {
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 10px;
}
div.group-container > div.group > ul > li > div.group-info {
  font-size: 18px;
  font-weight: bold;
  width: 200px;
  overflow-x: hidden;
}
div.group-container > div.group > ul > li > div.group-mem-info {
  font-size: 13px;
  width: 200px;
  overflow-x: hidden;
}
span.add-group {
  display: flex;
  margin-left: 20px;
  margin-top: 30px;
  width: 30px;
}
.section-container {
  display: flex;
  height: 100vh;
}

div.g-part {
  position: absolute;
  width: 390px;
  height: 100vh;
  overflow-x: hidden;
  overflow-y: auto;
}
div.m-part {
  position: absolute;
  left: 454px;
  right: 0; 
  height: 100%;
}
</style>
