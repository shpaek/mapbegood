<template>
  <v-container fluid mt-5>
    <div class="text-sm-h2 text-h4 font-weight-bold ml-3">그룹명</div>

    <v-list id="chats">
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

    <v-form class="input mt-5 d-flex">
      <v-textarea
        v-model="chat"
        autofocus
        label="채팅을 남겨주세요."
        color="green darken-1"
        auto-grow
        outlined
        rows="1"
        row-height="15"
        @keyup.enter="sendChat"
      ></v-textarea>
      <div>
        <v-btn @click="sendChat" text class="mt-3 font-weight-bold">전송</v-btn>
      </div>
    </v-form>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
import * as StompJS from "@stomp/stompjs";

export default {
  name: "Chat",
  computed: {
    ...mapState(["userInfo"]),
  },
  data() {
    return {
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
      console.log(this.chat.length);
      if (this.nickname !== "" && this.chat !== "") {
        this.send();
        this.chat = "";
      }
    },

    send() {
      const msg = {
        groupId: 1,
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
        this.stompClient.subscribe("/topic/" + this.groupId, (res) => {
          this.chats.push(JSON.parse(res.body));
          console.log(res);
        });
      };
    },
  },
  async created() {
    await this.$store.dispatch("getUserInfo");
    this.groupId = 1;
    this.profileImage = this.userInfo.profileImage;
    this.nickName = this.userInfo.nickName;
    this.connect();
  },
  updated() {
    const chatbox = document.querySelector("#chats");
    chatbox.scrollTop = chatbox.scrollHeight;
  },
};
</script>

<style scoped>
#chats {
  position: absolute;
  overflow-y: scroll;
  height: 500px;
  width: 100%;
  top: 100px;
}

.chat__mymessage {
  display: flex;
  justify-content: right;
  align-items: flex-end;
  margin: 0;
  min-height: 40px;
  line-break: anywhere;
}

.chat__mymessage__paragraph {
  margin: 0.4rem 0 0 1rem;
  border-radius: 20px 20px 0px 20px;
  max-width: 180px;
  background-color: #bbc4ef;
  color: #ffffff;
  padding: 0.8rem;
  font-size: 14px;
}

.chat__yourmessage {
  display: flex;
}

.chat__yourmessage__avartar {
  width: 40px;
  margin-right: 1rem;
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
  padding: 2rem;
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
</style>
