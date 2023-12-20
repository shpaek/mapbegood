<!--GroupDetail.vue-->
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
              >
                <!-- v-show="isleader" -->
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
              <!-- groupmemberClickHandler: 그룹멤버수정페이지로 이동 -->
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
            </li>
          </ul>
        </div>
      </div>
      <hr />

      <div class="group-thememap-list">
        <v-dialog v-model="groupThemeMapAddDialog" persistent width="500">
          <template v-slot:activator="{ props }">
            <!-- "테마맵 추가" 버튼 -->
            <div
              class="new-thememap cursor-pointer btn btn-outline-dark"
              v-bind="props"
            >
              <!-- 그룹 테마지도 추가버튼 -->
              <span class="new">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="19"
                  height="19"
                  fill="currentColor"
                  class="bi bi-plus-circle-fill"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0M8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3z"
                  />
                </svg> </span
              >&nbsp;&nbsp;
              <span class="text">테마지도 추가</span>
            </div>
          </template>

          <v-card>
            <v-card-title>
              <h1 class="text-primary mb-4">그룹 테마지도 생성</h1>
            </v-card-title>

            <v-card-text>
              <v-container>
                <!-- 테마 이름 입력 -->
                <div class="mb-3">
                  <label for="themeName" class="form-label text-black"
                    >테마 이름</label
                  >
                  <input
                    v-model="groupThemeMapName"
                    id="groupThemeMapName"
                    name="groupThemeMapName"
                    type="text"
                    class="form-control"
                    ref="groupThemeMapName"
                  />
                </div>

                <!-- 색상 선택 드롭다운 -->
                <div class="mb-3">
                  <label for="colorSelector" class="form-label text-black"
                    >테마 색상 선택</label
                  >
                  <select
                    v-model="themeMapColor"
                    id="themeMapColor"
                    name="themeMapColor"
                    class="form-select"
                    ref="themeMapColor"
                  >
                    <option v-for="color in colors" :key="color" :value="color">
                      {{ color }}
                    </option>
                  </select>
                </div>

                <!-- 테마 메모 입력 -->
                <div class="mb-3">
                  <label for="themeMemo" class="form-label text-black"
                    >테마 메모</label
                  >
                  <textarea
                    v-model="groupThemeMapMemo"
                    id="groupThemeMapMemo"
                    name="groupThemeMapMemo"
                    rows="4"
                    class="form-control"
                  ></textarea>
                </div>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <!-- 테마맵 생성 버튼 -->
              <button @click="CreateGroupThemeMap" class="btn btn-dark">
                생성</button
              >&nbsp;&nbsp;&nbsp;
              <button
                data-v-fce5df64=""
                class="btn btn-light"
                @click="cancelCreateGroupMap"
              >
                취소
              </button>
              <v-spacer></v-spacer>
            </v-card-actions>
            <br />
          </v-card>
        </v-dialog>

        <ul class="list-group">
          <li
            v-for="thememap in groupThememaps"
            :key="thememap.themeMapDto.id"
            class="thememap"
          >
            <div
              class="info"
              @click="goToDetailGroupMap(thememap.themeMapDto.id)"
            >
              <div class="name">{{ thememap.themeMapDto.name }}</div>
              <div class="memo">{{ thememap.themeMapDto.memo }}</div>
            </div>
            <!-- 더보기 버튼 -->
            <div class="btn-group" role="group">
              <button
                type="button"
                class="more"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="15"
                  height="15"
                  fill="currentColor"
                  class="bi bi-three-dots"
                  viewBox="0 0 16 16"
                >
                  <path
                    d="M3 9.5a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3m5 0a1.5 1.5 0 1 1 0-3 1.5 1.5 0 0 1 0 3"
                  />
                </svg>
              </button>
              <ul class="dropdown-menu">
                <v-dialog
                  v-model="thememap.themeMapDto.themeMapEditDialog"
                  persistent
                  width="500"
                >
                  <template v-slot:activator="{ props }">
                    <li v-bind="props">
                      <!-- @click="editGroupmap(thememap.themeMapDto.id)" -->
                      수정
                    </li>
                  </template>

                  <v-card>
                    <v-card-title>
                      <h1 class="text-primary mb-4">그룹 테마지도 수정</h1>
                    </v-card-title>

                    <v-card-text>
                      <v-container>
                        <!-- 테마 이름 입력 -->
                        <div class="mb-3">
                          <label for="themeName" class="form-label text-black"
                            >테마 이름</label
                          >
                          <input
                            v-model="thememap.themeMapDto.name"
                            id="themeMapDto.name"
                            name="themeMapDto.name"
                            type="text"
                            class="form-control"
                            ref="themeMapDto.name"
                          />
                        </div>

                        <!-- 색상 선택 드롭다운 -->
                        <div class="mb-3">
                          <label
                            for="colorSelector"
                            class="form-label text-black"
                            >테마 색상 선택</label
                          >
                          <select
                            v-model="thememap.themeMapDto.color"
                            id="themeMapDto.color"
                            name="themeMapDto.color"
                            class="form-select"
                            ref="themeMapDto.color"
                          >
                            <option
                              v-for="color in colors"
                              :key="color"
                              :value="color"
                            >
                              {{ color }}
                            </option>
                          </select>
                        </div>

                        <!-- 테마 메모 입력 -->
                        <div class="mb-3">
                          <label for="themeMemo" class="form-label text-black"
                            >테마 메모</label
                          >
                          <textarea
                            v-model="thememap.themeMapDto.memo"
                            id="themeMapDto.memo"
                            name="themeMapDto.memo"
                            rows="4"
                            class="form-control"
                          ></textarea>
                        </div>
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <!-- 테마맵 생성 버튼 -->
                      <button
                        @click="updateGroupMap(thememap.themeMapDto)"
                        class="btn btn-dark"
                      >
                        수정</button
                      >&nbsp;&nbsp;
                      <button
                        data-v-fce5df64=""
                        class="btn btn-light"
                        @click="cancleThemeMapEdit(thememap.themeMapDto)"
                      >
                        취소
                      </button>
                      <v-spacer></v-spacer>
                    </v-card-actions>
                    <br />
                  </v-card>
                </v-dialog>

                <li @click="deleteGroupmap(thememap.themeMapDto.id)">삭제</li>
              </ul>
            </div>
          </li>
        </ul>
      </div>
    </div>
    <div class="m-part">
      <Detailmap />
    </div>

    <div class="modal-container">
      <!-- 모달창 -->
      <GroupImageChange
        :isImageChangeOpen="isImageChangeOpen"
        :groupId="groupId"
        @close-Image="closeImageChange"
      />
      <GroupNameChange
        :isNameChangeOpen="isNameChangeOpen"
        :groupId="groupId"
        @close-Name="closeNameChange"
      />
      />
    </div>
  </div>
</template>
<script>
import axios from "axios";
import Detailmap from "./Detailmap.vue";
import GroupImageChange from "./GroupImageChange.vue";
import GroupNameChange from "./GroupNameChange.vue";
import Swal from "sweetalert2";

export default {
  name: "GroupDetail",
  components: {
    Detailmap,
    GroupImageChange,
    GroupNameChange,
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
      isImageChangeOpen: false,
      isNameChangeOpen: false,
      isGroupMemberOpen: false,
      isAddGroupmapOpen: false, // 모달의 열림/닫힘 상태를 저장하는 변수 추가

      groupThemeMapAddDialog: false,

      groupThemeMapName: "",
      themeMapColor: "",
      groupThemeMapMemo: "",

      colors: [
        "red",
        "yellow",
        "green",
        "blue",
        "indigo",
        "purple",
        "pink",
        "gray",
        "black",
      ],

      themeMapDto: {
        themeMapEditDialog: false,
        id: null,
        name: "",
        color: "",
        memo: "",
      },
    };
  },
  async created() {
    this.groupId = this.$route.params.groupId;
    this.groupName = this.$route.params.groupName;
    this.leaderNickname = this.$route.params.leaderNickname;
    this.groupMemo = this.$route.params.groupMemo;
    this.fetchGroupThememaps();

    //로그인한 멤버가 그룹장인 경우 isleader를 true로 주기
    if (this.$store.state.userInfo.nickName == this.leaderNickname) {
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
          this.listCnt = response.data.length;
          this.groupThememaps = response.data.map((themeMap) => {
            return {
              themeMapDto: themeMap,
            };
          });
        })
        .catch((error) => {
          console.error("Error fetching group theme maps:", error);
        });
    },
    gearClickHandler() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    updateImageClickHandler() {
      console.log(this.isImageChangeOpen);
      this.isImageChangeOpen = true;
      console.log(this.isImageChangeOpen);
    },
    updateNameClickHandler() {
      console.log(this.isNameChangeOpen);
      this.isNameChangeOpen = true;
      console.log(this.isNameChangeOpen);
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
    closeImageChange() {
      this.isImageChangeOpen = false;
    },
    closeNameChange() {
      this.isNameChangeOpen = false;
    },

    async CreateGroupThemeMap() {
      if (this.groupThemeMapName == "") {
        Swal.fire({ text: "테마지도 이름을 입력해주세요.", icon: "warning" });
        this.$refs.groupThemeMapName.focus();
        return;
      }
      if (this.themeMapColor == "") {
        Swal.fire({ text: "테마지도 색상을 선택해 주세요.", icon: "warning" });
        this.$refs.themeMapColor.focus();
        return;
      }

      try {
        // 현재 라우트에서 추출한 groupId 사용
        const groupId = this.$route.params.groupId;
        // 사용자 입력을 이용해 groupthememapDto 생성
        const groupthemeMapDto = {
          name: this.groupThemeMapName,
          color: this.themeMapColor,
          memo: this.groupThemeMapMemo,
        };

        // 백엔드로 그룹 테마지도 생성 요청 보내기
        const response = await axios.post(
          `http://localhost:8080/ourmap/create/${groupId}`,
          groupthemeMapDto,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("mapbegoodToken"),
            },
          }
        );

        // 응답이 정상적으로 오면 성공 메시지 출력 또는 추가 작업 수행
        console.log("Group Theme Map created:", response.data);
        Swal.fire({
          text: "그룹테마맵이 생성되었습니다.",
          icon: "success",
          confirmButtonText: "확인",
        }).then((result) => {
          if (result.isConfirmed) {
            this.cancelCreateGroupMap();
          }
        });
        this.fetchGroupThememaps();
      } catch (error) {
        // 오류 처리
        console.error("생성실패:", error);
      }
    },

    cancelCreateGroupMap() {
      // this.$router.go(-1); // Go back one step in the history stack
      this.groupThemeMapAddDialog = false;

      this.groupThemeMapName = "";
      this.themeMapColor = "";
      this.groupThemeMapMemo = "";
    },

    updateGroupMap(themeMapDto) {
      const url =
        `http://localhost:8080/ourmap/update/${this.groupId}/` + themeMapDto.id;
      const updatedGroupThememap = {
        name: themeMapDto.name,
        color: themeMapDto.color,
        memo: themeMapDto.memo,
      };

      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .put(url, updatedGroupThememap)
        .then((response) => {
          // console.log(response.data);
          Swal.fire({
            text: "그룹테마맵이 성공적으로 수정되었습니다.",
            icon: "success",
          }).then((result) => {
            this.cancleThemeMapEdit(themeMapDto);
          });
        })
        .catch((error) => {
          console.error(error);
          Swal.fire({ text: "그룹테마맵 수정에 실패했습니다.", icon: "error" });
        });
    },

    cancleThemeMapEdit(themeMapDto) {
      themeMapDto.themeMapEditDialog = false;
    },
  },
};
</script>
<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
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
  width: 150px;
}
li.img > img {
  border-radius: 5px;
  min-height: 150px;
  max-height: 150px;
  min-width: 150px;
  max-width: 150px;
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
  /* overflow-x: hidden;
  overflow-y: auto; */
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

  position: absolute;
  width: 390px;
  height: 70vh;
  overflow-x: hidden;
  overflow-y: auto;
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

div.group-thememap-list > ul.list-group > li {
  margin-top: 15px;
}
div.group-thememap-list > ul.list-group > li:hover {
  background-color: rgba(
    0,
    112,
    192,
    0.329
  ); /* 좋을지도 로고 파란색, 투명도 적용 */
}
li.thememap {
  display: flex;
  border: 1px solid grey;
  border-radius: 5px;
  height: 60px;
  width: 330px;
  vertical-align: baseline;
}
li.thememap > div.info {
  margin-top: auto;
  margin-bottom: auto;
  margin-left: 13px;
  width: 280px;
}
li.thememap > div.info > div.name {
  font-size: 18px;
  font-weight: bold;
}
li.thememap > div.info > div.memo {
  font-size: 13px;
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

div.group-detail > div.group-info > ul > li.img > div.combobox > div.dropdown {
  position: absolute;
  font-size: 12px;
  left: 268px;
  top: 35px;
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

div.group-detail
  > div.group-info
  > ul
  > li.img
  > div.combobox
  > div.dropdown
  > ul
  > li:hover {
  cursor: pointer;
  background-color: rgb(211, 211, 211);
}

div.group-detail > div.group-info > ul > li.img > div.combobox > div.triangle {
  position: absolute;
  top: 26px;
  left: 323px;
  border-left: 7px solid transparent;
  border-right: 7px solid transparent;
  border-bottom: 12px solid rgb(240, 240, 240);
}

div.modal-container {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1000;
}
</style>
