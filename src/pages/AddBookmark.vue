<template>
  <div v-show="isModalOpen" class="modal" @click="backClickHandler">
    <v-sheet :elevation="18" height="600" width="500" rounded @click.stop>
      <div id="app">
        <v-app id="inspire">
          <div>
            <v-toolbar color="blue" dark tabs>
              <v-text-field
                class="mx-3"
                flat
                label="Search"
                prepend-inner-icon="search"
                solo-inverted
              ></v-text-field>

              <template v-slot:extension>
                <v-tabs
                  v-model="tabs"
                  centered
                  color="blue"
                  slider-color="white"
                >
                  <v-tab>개인</v-tab>
                  <v-tab>그룹</v-tab>
                </v-tabs>
              </template>
            </v-toolbar>

            <v-tabs-items v-model="tabs">
              <v-tab-item v-for="n in 3" :key="n">
                <v-card>
                  <v-card-text>
                    <v-list two-line>
                      <v-list-tile
                        v-for="(item, index) in items"
                        :key="index"
                        avatar
                        ripple
                        @click="toggle(index)"
                      >
                        <v-list-tile-content>
                          <v-list-tile-title
                            >{{ item.title }}
                          </v-list-tile-title>
                          <v-list-tile-sub-title>{{
                            item.subtitle
                          }}</v-list-tile-sub-title>
                        </v-list-tile-content>
                      </v-list-tile>
                      <v-divider
                        v-if="index + 1 < items.length"
                        :key="'divider-' + index"
                      ></v-divider>
                      <v-divider></v-divider>
                    </v-list>
                  </v-card-text>
                </v-card>
              </v-tab-item>
            </v-tabs-items>
            <button type="button" class="close" id="b2" @click="b2ClickHandler">
              닫기
            </button>
          </div>
        </v-app>
      </div>
    </v-sheet>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "AddBookmark",
  props: {
    isModalOpen: Boolean,
    groupId: Number,
  },
  data() {
    return {
      name: "",
      userList: [],
      emptyMsg: "",
      tabs: null,
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
      items: [
        {
          title: "Ali Connors",
          subtitle:
            "I'll be in your neighborhood doing errands this weekend. Do you want to hang out?",
        },
        {
          title: "me, Scrott, Jennifer",
          subtitle: "Wish I could come, but I'm out of town this weekend.",
        },
        {
          title: "Sandra Adams",
          subtitle: "Do you have Paris recommendations? Have you ever been?",
        },
      ],
    };
  },

  methods: {
    toggle(index) {
      const i = this.selected.indexOf(index);

      if (i > -1) {
        this.selected.splice(i, 1);
      } else {
        this.selected.push(index);
      }
    },

    backClickHandler(e) {
      window.location.reload();
    },
    b2ClickHandler() {
      this.$emit("close-modal");
    },

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
  height: 85%;
  /* background-color: rgba(0, 0, 0, 0.5); */
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

img.profileImage {
  min-width: 42px;
  min-height: 40px;
  max-width: 42px;
  max-height: 40px;
  border-radius: 50%;
}
</style>
