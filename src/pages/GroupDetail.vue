<template lang="">
 
  <!-- 특정 그룹의 테마지도 목록을 전부 불러옴, 그룹이미지, 그룹명, 그룹장도 가져와야함 -->
  <div class="group-detail">
      <div class="group-info">
          <ul>
              <li class="img">   
                  <img :src="'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/'+this.groupId+'_groupImage.jpg?'+new Date().getTime()" alt="그룹이미지"/>             
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16"
                      @click="gearClickHandler">
                      <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>
                      <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>
                  </svg> 
                  <div class="combobox">
                      <!-- 콤보상자를 나타내는 요소 -->
                      <div v-show="isDropdownOpen" class="triangle"></div>
                      <div v-show="isDropdownOpen" class="dropdown" @click.stop>
                          <!-- 콤보상자 내용 -->
                          <ul>
                              <li class="update-image" @click="updateImageClickHandler">이미지 변경</li>
                              <li class="update-name" @click="updateNameClickHandler">그룹명 변경</li>
                              <li class="delete-group" @click="deleteGroupClickHandler">그룹 삭제</li>
                          </ul>
                      </div>
                  </div>   
              </li>
              <li>   
                  <span class="group-name" @click="groupnameClickHandler">
                      {{groupName}}({{listCnt}})
                  </span>
              </li>
              <li class="group-member">
                  <span class="group-member" @click="groupmemberClickHandler">
                      {{leaderNickname}}({{memCnt}})
                  </span>
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-person-lines-fill" viewBox="0 0 16 16"
                      @click="personClickHandler">
                      <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z"/>
                  </svg>
                  <!-- groupmemberClickHandler: 그룹멤버수정페이지로 이동 -->
              </li>
          </ul>
      </div>
      <hr>
      <div class="new-group cursor-pointer" @click="addnewgroupclickHandler">
          <span>새 리스트 추가</span>
        </div>
      </div>
       <div class="col-md-6">
        <div class="group-thememap-list">
          <p>그룹의 테마지도 목록입니다</p>
          <ul class="list-group">
            <li v-for="thememap in groupThememaps" :key="thememap.id" class="list-group-item">
              <strong>그룹테마 list이름: {{ thememap.name }}</strong>
              <p>그룹테마 list내용: {{ thememap.memo }}</p>
              <div class="btn-group" role="group" aria-label="Theme Map Actions">
                <button type="button" class="btn btn-primary" @click="goToDetailGroupMap(thememap.id)">
                  상세보기
                </button>
                <button type="button" class="btn btn-secondary" @click="editGroupmap(thememap.id)">
                  수정
                </button>
                <button type="button" class="btn btn-danger" @click="deleteGroupmap(thememap.id)">
                  삭제
                </button>
              </div>
    </li>
  </ul>
</div>
</div>
</template>
<script>
import axios from 'axios';

export default {
  name: "GroupDetail",
  data() {
    return {
      groupId: 0,
      groupName: '그룹명',
      listCnt: 0,
      leaderNickname: '그룹장',
      memCnt: 0,
      listName: '리스트명',
      listWriter: '리스트작성자',
      isDropdownOpen: false,
      groupThememaps: [],
      groupMemo: '',
    };
  },
  created() {
    const groupId = this.$route.params.groupId;
    const groupName = this.$route.params.groupName;
    const leaderNickname = this.$route.params.leaderNickname;
    const groupMemo = this.$route.params.groupMemo;
    this.groupId = groupId;
    this.groupName = groupName;
    this.leaderNickname = leaderNickname;
    this.groupMemo = groupMemo;
    this.fetchGroupThememaps();
  },

  methods: {
    fetchGroupThememaps() {
      const url = `http://localhost:8080/ourmap/getall/${this.groupId}`;
      axios.get(url)
        .then(response => {
          this.groupThememaps = response.data;
        })
        .catch(error => {
          console.error("Error fetching group theme maps:", error);
        });
    },
    gearClickHandler() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    updateImageClickHandler() {
      this.$router.push({
        name: '/groupimage',
        params: {
          groupId: this.groupId,
          groupName: this.groupName,
          leaderNickname: this.leaderNickname
        }
      });
    },
    updateNameClickHandler() {
      this.$router.push({
        name: '/groupname',
        params: {
          groupId: this.groupId,
          groupName: this.groupName,
          leaderNickname: this.leaderNickname
        }
      });
    },
    deleteGroupClickHandler() {
        const url = `${this.backURL}/group/${this.groupId}`

        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken")
        axios.defaults.headers.common["Authorization"] = accessToken;

        axios.delete(url, { withCredentials: true, headers: { 'Content-Type': 'application/json' } })
          .then(response => {
            alert("그룹이 삭제되었습니다")
            location.href = '/groups'
          })
          .catch(error => {
            console.log(error)
            alert("그룹이 삭제되지 않았습니다 ")
          })
    },
    personClickHandler() {
        this.$router.push({
          name: '/groupmember',
          params: {
            groupId: this.groupId,
            groupName: this.groupName,
            leaderNickname: this.leaderNickname
          }
        })
    },
    addnewgroupclickHandler() {
        this.$router.push({ name: 'addgroupmap' });
    },
    goToDetailGroupMap(groupThememapId) {
        // 그룹 테마지도의 ID를 사용하여 상세보기 페이지로 이동
        this.$router.push({
          name: 'detailgroupmap',
          params: { groupThememapId: groupThememapId }
        });
    },
    editGroupmap(thememapId) {
        this.$router.push({
          name: 'updategroupmap',
          params: { groupId: this.groupId, groupThememapId: thememapId }
        });
    },
    deleteGroupmap(groupThememapId) {
        const url = `http://localhost:8080/ourmap/delete/${this.groupId}/${groupThememapId}`;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");

        axios.defaults.headers.common["Authorization"] = accessToken;

        axios.delete(url, { withCredentials: true, headers: { 'Content-Type': 'application/json' } })
          .then(response => {
            // Assuming your backend returns a success status
            alert("그룹 테마지도가 삭제되었습니다.");
            // Refresh the theme maps after deletion
            this.fetchGroupThememaps();
          })
          .catch(error => {
            console.error("Error deleting group theme map:", error);
            alert("그룹 테마지도를 삭제하지 못했습니다.");
          });
    },
  }
}


</script>
<style scoped>
div.group-info {
  margin-left: 75px;
  margin-top: 50px;
}

ul {
  list-style-type: none;
  padding-left: 0px;
}

li.img {
  position: relative;
  height: 150px;
}

li.img svg.bi-gear {
  position: absolute;
  left: 92%;
  /* 조절하고자 하는 거리 */
  top: 10px;
  /* 원하는 세로 위치 조절 */
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

div.group-detail>div.group-info>ul>li.img>div.combobox>div.dropdown>ul>li {
  color: rgb(80, 80, 80);
}

div.group-detail>div.group-info>ul>li.img>div.combobox>div.dropdown>ul>li:hover {
  text-decoration: underline;
  cursor: pointer;
  /* 선택 가능한 요소로 마우스 커서를 변경합니다. */
}

div.group-detail>div.group-info>ul>li.img>div.combobox>div.triangle {
  position: absolute;
  top: 32px;
  /* 조절 가능한 값 */
  left: 93%;
  transform: translateX(-93%);
  border-left: 7px solid transparent;
  /* 왼쪽 변 */
  border-right: 7px solid transparent;
  /* 오른쪽 변 */
  border-bottom: 12px solid rgb(240, 240, 240);
  /* 아래쪽 변 */
}

li.group-member {
  position: relative;
}

li.group-member svg.bi-person-lines-fill {
  position: absolute;
  left: 92%;
}</style>.