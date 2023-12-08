<template lang="">
  <h2>내 그룹</h2>
  <div class="group-container">
    <div class="group" v-for="group in groupList" @click="groupClickHandler">
      <!-- v-for로 이미지 여러개 보이게 하기 -->
      <ul>
        <li>
          <img
            :src="
              'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/' +
              group.id +
              '_groupImage.jpg'
            "
            alt="그룹이미지"
            class="img-size"
          />
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
  </div>
  <br />
  <span class="add-group" @click="addgroupClickHandler">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      width="30"
      height="30"
      fill="currentColor"
      class="bi bi-plus-circle-fill"
      viewBox="0 0 16 16"
    >
      <path
        d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z"
      />
    </svg>
  </span>
</template>
<script>
import axios from "axios";
export default {
  name: "GroupView",
  data() {
    return {
      groupList: [],
    };
  },
  methods: {
    addgroupClickHandler() {
      //그룹추가 페이지로 이동
      location.href = "/groupcreate";
    },
    // groupClickHandler(){
    //     //그룹의 테마그룹 List보여주는 탭으로 주소이동시키기
    //     // loaction.href='/'
    // },
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
        this.groupList = list;
        console.log(response.data);
        // console.log("---start----")
        // console.log(this.groupList)
        // console.log("---end----")
      })
      .catch((error) => {
        console.log(error);
        alert(error.msg);
      });
  },
};
</script>
<style scoped>
.group-container {
  display: grid;
  margin-left: 50px;
  grid-template-columns: repeat(
    auto-fill,
    minmax(200px, 1fr)
  ); /* 200px 이상의 크기로 반복 */
  gap: 30px; /* grid당 간격 */
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
  /* 그룹이미지를 화면에서 보여줄 사이즈로 조정 */
  max-width: 150px;
  max-height: 150px;
  min-width: 150px;
  min-height: 150px;
  display: inline;
}

span.add-group {
  display: flex;
  margin-left: 30px;
  margin-bottom: 50px;
}
</style>
