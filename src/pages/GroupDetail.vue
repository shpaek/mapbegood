<template lang="">
  <div class="section-container">
    <div class="g-part">
      <div class="group-detail">
        <div class="group-info">
          <ul>
            <li class="img">
              <img
                :src="
                  'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/' +
                  this.groupId +
                  '_groupImage.jpg?' +
                  new Date().getTime()
                "
                alt="그룹이미지"
              />
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                fill="currentColor"
                class="bi bi-gear"
                viewBox="0 0 16 16"
                @click="gearClickHandler"
                v-show="isleader"
              >
                <path
                  d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"
                />
                <path
                  d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"
                />
              </svg>
              <div class="combobox">
                <!-- 콤보상자를 나타내는 요소 -->
                <div v-show="isDropdownOpen" class="triangle"></div>
                <div v-show="isDropdownOpen" class="dropdown" @click.stop>
                  <!-- 콤보상자 내용 -->
                  <ul>
                    <li class="update-image" @click="updateImageClickHandler">
                      이미지 변경
                    </li>
                    <li class="update-name" @click="updateNameClickHandler">
                      그룹명 변경
                    </li>
                    <li class="delete-group" @click="deleteGroupClickHandler">
                      그룹 삭제
                    </li>
                  </ul>
                </div>
              </div>
            </li>
            <li>
              <span class="group-name" @click="groupnameClickHandler">
                {{ groupName }}({{ listCnt }})
              </span>
            </li>
            <li class="group-member">
              <span class="group-member" @click="groupmemberClickHandler">
                {{ leaderNickname }}({{ memCnt }})
              </span>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                fill="currentColor"
                class="bi bi-person-lines-fill"
                viewBox="0 0 16 16"
                @click="personClickHandler"
              >
                <path
                  d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z"
                />
              </svg>
              <!-- groupmemberClickHandler: 그룹멤버수정페이지로 이동 -->
            </li>
          </ul>
        </div>
      </div>   
    <hr>
    <div class="group-thememap-list">
      <div class="new-thememap cursor-pointer btn btn-outline-dark" @click="addnewgroupclickHandler">
        <!-- 그룹 테마지도 추가버튼 -->
        <span class="new">
          <svg xmlns="http://www.w3.org/2000/svg" width="19" height="19" fill="currentColor" class="bi bi-plus-circle-fill" viewBox="0 0 16 16">
            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3z"/>
          </svg>
        </span>&nbsp;&nbsp;
        <span class="text">테마지도 추가</span>
      </div>
      <ul class="list-group">
        <li v-for="thememap in groupThememaps" :key="thememap.id" class="thememap">
          <div class="info" @click="goToDetailGroupMap(thememap.id)">
                <div class="name">{{ thememap.name }}</div>
                <div class="memo">{{ thememap.memo }}</div>
          </div>
          <!-- 더보기 버튼 -->
          <div class="btn-group" role="group">
            <button type="button" class="more" data-bs-toggle="dropdown" aria-expanded="false">
              <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" class="bi bi-three-dots" viewBox="0 0 16 16">
                <path d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3"/>
              </svg>
            </button>
            <ul class="dropdown-menu">
              <li @click="editGroupmap(thememap.id)">수정</li>
              <li @click="deleteGroupmap(thememap.id)">삭제</li>
            </ul>
          </div>            
        </li>
      </ul>
    <div class="m-part">
      <Detailmap />
    </div>
  </div>
</template>
<script>
import axios from "axios";
import { mapState, mapActions } from "vuex";
import Detailmap from "./Detailmap.vue";
export default {
  name: "GroupDetail",
  components: {
    Detailmap,
  },
  computed: {
    ...mapState(["userInfo", "isLogin"]),
  },
  data() {
    return {
      groupId: 0,
      groupName: "그룹명",
      listCnt: 0,
      leaderNickname: "그룹장",
      memCnt: 0,
      listName: "리스트명",
      listWriter: "리스트작성자",
      isDropdownOpen: false,
      groupThememaps: [],
      groupMemo: "",
      isleader: false,
    };
  },
  async created() {
    await this.$store.dispatch("getUserInfo");
    const groupId = this.$route.params.groupId;
    const groupName = this.$route.params.groupName;
    const leaderNickname = this.$route.params.leaderNickname;
    const groupMemo = this.$route.params.groupMemo;
    this.groupId = groupId;
    this.groupName = groupName;
    this.leaderNickname = leaderNickname;
    this.groupMemo = groupMemo;
    this.fetchGroupThememaps();

    //로그인한 멤버가 그룹장인 경우 isleader를 true로 주기
    if (this.userInfo.nickName == this.leaderNickname) {
      this.isleader = true;
    }
    console.log(this.userInfo.nickName);
    console.log(this.leaderNickname);
    console.log(this.isleader);
  },

  methods: {
    fetchGroupThememaps() {
      const url = `${this.backURL}/ourmap/getall/${this.groupId}`;
      axios
        .get(url)
        .then((response) => {
          this.groupThememaps = response.data;
        })
        .catch((error) => {
          console.error("Error fetching group theme maps:", error);
        });
    },
    gearClickHandler() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    updateImageClickHandler() {
      this.$router.push({
        name: "/groupimage",
        params: {
          groupId: this.groupId,
          groupName: this.groupName,
          leaderNickname: this.leaderNickname,
        },
      });
    },
    updateNameClickHandler() {
      this.$router.push({
        name: "/groupname",
        params: {
          groupId: this.groupId,
          groupName: this.groupName,
          leaderNickname: this.leaderNickname,
        },
      });
    },
    deleteGroupClickHandler() {
      const url = `${this.backURL}/group/${this.groupId}`;

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .delete(url, {
          withCredentials: true,
          headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
          alert("그룹이 삭제되었습니다");
          location.href = "/groups";
        })
        .catch((error) => {
          console.log(error);
          alert("그룹이 삭제되지 않았습니다 ");
        });
    },
    personClickHandler() {
      this.$router.push({
        name: "/groupmember",
        params: {
          groupId: this.groupId,
          groupName: this.groupName,
          leaderNickname: this.leaderNickname,
        },
      });
    },
    addnewgroupclickHandler() {
      this.$router.push({ name: "addgroupmap" });
    },
    goToDetailGroupMap(groupThememapId) {
      // 그룹 테마지도의 ID를 사용하여 상세보기 페이지로 이동
      this.$router.push({
        name: "detailgroupmap",
        params: { groupThememapId: groupThememapId },
      });
    },
    editGroupmap(thememapId) {
      this.$router.push({
        name: "updategroupmap",
        params: { groupId: this.groupId, groupThememapId: thememapId },
      });
    },
    deleteGroupmap(groupThememapId) {
      const url = `${this.backURL}/ourmap/delete/${this.groupId}/${groupThememapId}`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");

      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .delete(url, {
          withCredentials: true,
          headers: { "Content-Type": "application/json" },
        })
        .then((response) => {
          // Assuming your backend returns a success status
          alert("그룹 테마지도가 삭제되었습니다.");
          // Refresh the theme maps after deletion
          this.fetchGroupThememaps();
        })
        .catch((error) => {
          console.error("Error deleting group theme map:", error);
          alert("그룹 테마지도를 삭제하지 못했습니다.");
        });
    },
  },
};
</script>
<style scoped>
*{
  font-family: 'Noto Sans KR', sans-serif;
}
div.group-info {
  margin-left: 25px;
  margin-top: 30px;
}

ul {
  list-style-type: none;
  padding-left: 0px;
}

li.img {
  position: relative;
  height: 150px;
}
li.img>img{
  border-radius: 5px;
}

li.img svg.bi-gear {
  position: absolute;
  left: 320px;
}
li.group-member {
  position: relative;
}

li.group-member svg.bi-person-lines-fill {
  position: absolute;
  left: 320px;
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
  height: 100vh;
}
div.group-detail {
  overflow: hidden;
}
div.group-thememap-list {
  margin-left: 30px;
  width: 330px;
  margin-right: 30px;
}
div.group-thememap-list > div.new-thememap {
  width: 330px;
  height: 50px;
  border: 1px solid grey;
  border-radius: 5px;
  display: flex;
}
div.group-thememap-list > div.new-thememap > span {
  line-height: 33px;
}

div.group-thememap-list>ul.list-group>li{
  margin-top: 10px;

}
div.group-thememap-list>ul.list-group>li:hover{
  background-color: rgba(0, 112, 192, 0.329); /* 좋을지도 로고 파란색, 투명도 적용 */
}
li.thememap{
 display: flex;
 border: 1px solid grey;
 border-radius: 5px;
 height: 60px;
 width: 330px;
 vertical-align: baseline;
}
li.thememap>div.info{
  margin-top:auto;
  margin-bottom:auto;
  margin-left: 13px;
  width: 280px;
}
li.thememap>div.info>div.name{
  font-size: 18px;
  font-weight: bold;
}
li.thememap>div.info>div.memo{
  font-size:13px;
  color: grey;
  max-width: 250px;
  overflow-x: hidden;
}
.dropdown-menu {
  min-width: 45px;
  min-height: 50px; 
}

.dropdown-menu li {
  font-size: 12px;
  text-align: center;
}

.dropdown-menu li:hover {
  background-color: rgb(211, 211, 211);
  /* color:white; */
  cursor: pointer;
}

div.group-detail>div.group-info>ul>li.img>div.combobox>div.dropdown {
  position: absolute;
  font-size: 12px;
  left: 94.7%;
  transform: translateX(-94.7%);
  top: 42px;
  width: 75px;
  height: 55px;
  text-align: center;
  background-color: rgb(240, 240, 240);
  border-color: 1px rgb(240, 240, 240);
  border-radius: 15%;
  white-space: nowrap;
}

div.group-detail
  > div.group-info
  > ul
  > li.img
  > div.combobox
  > div.dropdown
  > ul
  > li {
  color: rgb(80, 80, 80);
}

div.group-detail>div.group-info>ul>li.img>div.combobox>div.dropdown>ul>li:hover {
  cursor: pointer;
  background-color: rgb(211, 211, 211);
}

div.group-detail > div.group-info > ul > li.img > div.combobox > div.triangle {
  position: absolute;
  top: 32px;
  left: 92%;
  transform: translateX(-93%);
  border-left: 7px solid transparent;
  border-right: 7px solid transparent;
  border-bottom: 12px solid rgb(240, 240, 240);
}
</style>
