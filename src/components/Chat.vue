<template>
  <!-- <v-container fluid mt-5> -->
  <div>
    <div class="chat__header">
      <span class="chat__header__greetings">{{ this.groupName }}</span>
    </div>

    <v-list id="chats" ref="chats">
      <template v-for="(item, idx) in chats" :key="idx">
        <div class="chat__body" id="chat__body">
          <div v-if="item.sender == nickName" class="chat__mymessage">
            <p class="chat__mymessage__paragraph">
              {{ item.message }}
            </p>
          </div>

          <div v-else class="chat__yourmessage">
            <div class="chat__yourmessage__avartar">
              <img :src="item.profileImage" class="chat__yourmessage__img" />
            </div>

            <div>
              <p class="chat__yourmessage__user">
                {{ item.sender }}
              </p>
              <div class="chat__yourmessage__p">
                <p class="chat__yourmessage__paragraph">
                  {{ item.message }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </template>
    </v-list>

    <div class="form">
      <input
        class="form__input"
        type="text"
        placeholder="메세지를 입력하세요."
        v-model.trim="chat"
        @keyup.enter="sendChat"
      />
      <div @click="sendChat" class="form__submit">
        <svg
          width="30"
          height="30"
          viewBox="0 0 68 68"
          fill="#CCCCCC"
          xmlns="http://www.w3.org/2000/svg"
        >
          <g clip-path="url(#clip0_26_10)">
            <path
              fill-rule="evenodd"
              clip-rule="evenodd"
              d="M48.0833 19.799C48.619 20.3347 48.806 21.127 48.5665 21.8457L35.8385 60.0294C35.5946 60.7614 34.9513 61.2877 34.1855 61.382C33.4198 61.4763 32.6681 61.1217 32.2539 60.4707L22.593 45.2893L7.41158 35.6285C6.76065 35.2142 6.40604 34.4625 6.50031 33.6968C6.59458 32.931 7.12092 32.2878 7.85287 32.0438L46.0366 19.3159C46.7553 19.0763 47.5476 19.2633 48.0833 19.799ZM26.5903 44.1204L33.3726 54.7782L42.0926 28.6181L26.5903 44.1204ZM39.2642 25.7897L23.7619 41.292L13.1041 34.5097L39.2642 25.7897Z"
              fill=""
            />
          </g>
          <defs>
            <clipPath id="clip0_26_10">
              <rect
                width="48"
                height="48"
                fill="white"
                transform="translate(33.9412) rotate(45)"
              />
            </clipPath>
          </defs>
        </svg>
      </div>
    </div>
  </div>
  <!-- </v-container> -->
</template>

<script>
import { mapState } from "vuex";
import * as StompJS from "@stomp/stompjs";
import axios from "axios";

export default {
  name: "Chat",
  computed: {
    ...mapState(["userInfo"]),
  },
  data() {
    return {
      groupName: null,
      groupId: null,
      profileImage: null,
      nickName: null,
      chat: "",
      chats: [],
      stompClient: null,
      isSame: false,
    };
  },
  methods: {
    sendChat() {
      if (this.nickname !== "" && this.chat !== "") {
        this.send();
        this.chat = "";
      }
    },

    send() {
      const msg = {
        groupId: this.groupId,
        profileImage: this.profileImage,
        sender: this.nickName,
        message: this.chat,
      };

      this.stompClient.publish({
        destination: "/pub/msg",
        body: JSON.stringify(msg),
      });
    },

    connect() {
      this.stompClient = new StompJS.Client({
        brokerURL: "wss://chat.mapbegood.site:8443/chat",
        connectHeaders: {
          Authorization: "Bearer " + localStorage.getItem("mapbegoodToken"),
        },
      });
      this.stompClient.activate();
      this.stompClient.onConnect = (frame) => {
        console.log("Frame: " + frame);
        console.log("/topic/" + this.groupId);
        this.stompClient.subscribe("/topic/" + this.groupId, (res) => {
          this.chats.push(JSON.parse(res.body));
          console.log(res);
          // const chatbox = document.querySelector("#chats");
          // chatbox.scrollTop = chatbox.scrollHeight;
        });
      };
    },
  },
  async created() {
    await this.$store.dispatch("getUserInfo");
    this.profileImage = this.userInfo.profileImage;
    this.nickName = this.userInfo.nickName;
    this.connect();

    const groupInfo = location.pathname.split("/");
    console.log(groupInfo);
    if (groupInfo[1] == "group") {
      this.groupId = groupInfo[2];
      this.groupName = decodeURI(groupInfo[3]);
    }

    axios
      .get("https://chat.mapbegood.site:8443/msg-all/" + this.groupId)
      .then((res) => {
        console.log(res);
        this.chats = res;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  updated() {
    const chatbox = document.querySelector("#chats");
    chatbox.scrollTop = chatbox.scrollHeight;
  },
  unmounted() {
    this.stompClient.deactivate();
    this.stompClient = null;
  },
};
</script>

<style scoped>
#chats {
  /* position: absolute; */
  overflow-y: scroll;
  /* height: 500px; */
  /* width: 100%; */
  height: 400px;
  width: 380px;
  /* top: 100px; */
  border-radius: 10px 10px 12px 12px;
}

.chat__header {
  background: #6ca6fd;
  box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.05);
  border-radius: 24px 24px 0px 0px;
  padding: 0.8rem;
  font-size: 16px;
  font-weight: 700;
}

.chat__header__greetings {
  color: #292929;
}

.chat__mymessage {
  display: flex;
  justify-content: right;
  align-items: flex-end;
  margin: 10px 10px 10px 0px;
  min-height: 40px;
  line-break: anywhere;
}

.chat__mymessage__paragraph {
  margin: 0.3rem 0 0 1rem;
  border-radius: 20px 20px 0px 20px;
  max-width: 180px;
  background-color: #387eff;
  color: #ffffff;
  padding: 0.8rem;
  font-size: 14px;
}

.chat__yourmessage {
  display: flex;
  margin: 10px 0px 10px 0px;
}

.chat__yourmessage__avartar {
  width: 40px;
  margin-right: 0.5rem;
}

.chat__yourmessage__img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.chat__yourmessage__user {
  font-size: 14px;
  font-weight: 700;
  color: #292929;
  margin-top: 0;
  margin-block-end: 0rem;
}

.chat__yourmessage__p {
  display: flex;
  align-items: flex-end;
  line-break: anywhere;
}

.chat__yourmessage__paragraph {
  margin: 0.4rem 1rem 0 0;
  border-radius: 0px 20px 20px 20px;
  background-color: #f3f3f3;
  max-width: 180px;
  color: #414141;
  padding: 0.8rem;
  font-size: 14px;
}

.chat__body {
  padding: 0.1rem;
  overflow: scroll;
  scroll-behavior: smooth;
}

.chat__body::-webkit-scrollbar {
  display: none;
}

.input {
  position: absolute;
  bottom: 0;
}

.form {
  border: #3d5cf7 1px solid;
  display: flex;
  justify-content: space-between;
  padding: 0.5rem;
  background: #ffffff;
  border-radius: 10px 10px 12px 12px;
  box-shadow: 0px -5px 30px rgba(0, 0, 0, 0.05);
  width: 365px;
}

.form__input {
  border: none;
  padding: 0.5rem;
  font-size: 16px;
  width: calc(100% - 60px);
}

.form__input:focus {
  outline: none;
}

.form__submit {
  display: flex;
  align-items: center;
  cursor: pointer;
}

svg {
  transition: 0.3s;
}

svg:hover {
  fill: #999999;
}
</style>
