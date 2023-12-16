<template lang="">
  <div class="section-contaioner">
    <div class="g-part">
      <h2>내 그룹</h2>
      <div class="icon-container">
        <span class="check-waiting">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="30"
            height="30"
            fill="currentColor"
            class="bi bi-envelope"
            viewBox="0 0 16 16"
          >
            <path
              d="M0 4a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v.217l7 4.2 7-4.2V4a1 1 0 0 0-1-1zm13 2.383-4.708 2.825L15 11.105zm-.034 6.876-5.64-3.471L8 9.583l-1.326-.795-5.64 3.47A1 1 0 0 0 2 13h12a1 1 0 0 0 .966-.741M1 11.105l4.708-2.897L1 5.383z"
            />
          </svg>
        </span>
      </div>
      <div class="group-container">
        <div
          class="group"
          v-for="group in groupList"
          @click="groupClickHandler(group)"
        >
          <ul>
            <li>
              <a href="#">
                <img
                  id="i"
                  alt="그룹이미지"
                  class="img-size"
                  :src="
                    'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/' +
                    group.id +
                    '_groupImage.jpg?' +
                    new Date().getTime()
                  "
                  @click=""
                />
              </a>
            </li>
            <li>
              <span class="group-info">{{ group.name }}</span>
            </li>
            <li>
              <span class="group-mem-info">{{
                group.memberGroupList[0].member.nickname
              }}</span>
            </li>
          </ul>
        </div>
        <span class="empty-msg">{{ emptyMsg }}</span>
      </div>
      <span class="add-group">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-plus-circle-fill"
          viewBox="0 0 16 16"
          @click="addgroupClickHandler"
        >
          <path
            d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z"
          />
        </svg>

        <!-- <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16"
            >
            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
            <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4"/>
          </svg> -->
      </span>
    </div>
    <div class="m-part">
      <Detailmap />
    </div>
  </div>
</template>
<script>
import axios from "axios";
import Detailmap from "./Detailmap.vue";
// import Waiting from '';
export default {
  name: "GroupsView",
  components: {
    Detailmap,
  },
  data() {
    return {
      groupList: [],
      emptyMsg: "",
    };
  },
  created() {
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
        this.emptyMsg = "그룹을 불러올 수 없습니다";
      });
  },
  methods: {
    addgroupClickHandler() {
      //그룹추가 페이지로 이동
      location.href = "/groupcreate";
    },
    groupClickHandler(group) {
      //그룹의 테마그룹 List보여주는 탭으로 주소이동시키기
      console.log(group.id);
      // loaction.href=`/groupthememaplist/${group.id}`
      // this.$router.push(`/groupthememaplist/${group.id}`);
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
  },
};
</script>
<style scoped>
/* .group-container {
  display: grid;
  margin-left: 50px;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 30px;
} 

div.group {
  margin: 50px;
  display: flex;
}

div.group > ul {
  list-style-type: none;
  padding-left: 0px;
}

div.group > ul > li > span.group-info {
  font-size: 15px;
  padding-left: 10px;
}

div.group > ul > li > span.group-mem-info {
  font-size: 13px;
  padding-left: 10px;
  display: inline;
}

div.group > ul > li > img.img-size {
  max-width: 150px;
  max-height: 150px;
  min-width: 150px;
  min-height: 150px;
  display: inline;
}
span.add-group {
  display: flex;
  margin-left: 100px;
  margin-top: 30px;
  width: 30px;
}
span.check-waiting {
  display: flex;
  margin-left: 85%; 
  margin-top: 30px;
  width: 30px;
  margin-right: 100px; 
}
div.icon-container{
  display: flex;
}
span.empty-msg {
  margin-top: 75px;
  margin-bottom: 50px;
  font-size: 14px;
  color: darkgray;
}
div.add-group{
  display: flex;
}
div.add-group>span.plus{
  margin-left: auto;
  margin-right: auto;
  margin-top: auto;
  margin-bottom: auto;
  width:150px;
  height:150px;
  font-size: 100px;
  border: 3px solid black;
}
*/
.section-container {
  display: flex;
  height: 100vh;
}

div.g-part {
  position: absolute;
  width: 390px;
}
div.m-part {
  position: absolute;
  left: 454px; /* 왼쪽 영역의 너비 만큼 이동 */
  right: 0; /* 오른쪽에 닿도록 */
  height: 100%;
}
</style>
