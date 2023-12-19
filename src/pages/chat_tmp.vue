<template>
  <head>
    <title>Hello WebSocket</title>
    <link
      rel="stylesheet"
      href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
      integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
      crossorigin="anonymous"
    />
    <!-- <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2"></script> -->
  </head>
  <body>
    <noscript
      ><h2 style="color: #ff0000">
        Seems your browser doesn't support Javascript! Websocket relies on
        Javascript being enabled. Please enable Javascript and reload this page!
      </h2></noscript
    >
    <div id="main-content" class="container">
      <div class="row">
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="connect">WebSocket connection:</label>
              <button id="connect" class="btn btn-default" type="submit">
                Connect
              </button>
              <button
                id="disconnect"
                class="btn btn-default"
                type="submit"
                disabled="disabled"
              >
                Disconnect
              </button>
            </div>
          </form>
        </div>
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="name">What is your name?</label>
              <input
                type="text"
                id="name"
                class="form-control"
                placeholder="Your name here..."
              />
            </div>
            <button id="send" class="btn btn-default" type="submit">
              Send
            </button>
          </form>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <table id="conversation" class="table table-striped">
            <thead>
              <tr>
                <th>Greetings</th>
              </tr>
            </thead>
            <tbody id="greetings"></tbody>
          </table>
        </div>
      </div>
    </div>
  </body>
</template>

<script>
export default {
  name: "chat_tmp",
  data: {
    stompClient: null,
    connected: false,
    message: "",
    greetings: [],
  },
  methods: {
    connect() {
      this.stompClient = new StompJs.Client({
        brokerURL: "wss://chat.mapbegood.site:8443/chat",
        connectHeaders: {
          Authorization: "Bearer YOUR_ACCESS_TOKEN", // 실제 토큰으로 교체
        },
      });

      this.stompClient.onConnect = (frame) => {
        this.setConnected(true);
        console.log("연결됨: " + frame);
        this.stompClient.subscribe("/topic/1", (message) => {
          const parsedMessage = JSON.parse(message.body);
          console.log("받은 메시지:", parsedMessage);
          this.showGreeting(parsedMessage.message);
        });
      };

      this.stompClient.onWebSocketError = (error) => {
        console.error("웹소켓 오류:", error);
      };

      this.stompClient.onStompError = (frame) => {
        console.error("브로커 오류: " + frame.headers["message"]);
        console.error("추가 세부 정보: " + frame.body);
      };

      this.stompClient.activate();
    },
    disconnect() {
      this.stompClient.deactivate();
      this.setConnected(false);
      console.log("연결이 해제됨");
    },
    sendMessage() {
      console.log("메시지 전송:", this.message);
      this.stompClient.publish({
        destination: "/pub/msg",
        body: JSON.stringify({
          groupId: 1,
          sender: "dozy",
          message: this.message,
          sendAt: new Date(),
        }),
      });
    },
    showGreeting(message) {
      console.log("받은 인사말:", message);
      this.greetings.push(message);
    },
    setConnected(connected) {
      this.connected = connected;
      // 연결된 경우 다른 UI 요소를 업데이트하거나 추가 작업을 수행할 수 있습니다.
    },
  },
};
</script>

<style scoped>
body {
  background-color: #f5f5f5;
}

#main-content {
  max-width: 940px;
  padding: 2em 3em;
  margin: 0 auto 20px;
  background-color: #fff;
  border: 1px solid #e5e5e5;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  border-radius: 5px;
}
</style>
