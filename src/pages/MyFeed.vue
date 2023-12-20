<template>
  <div id="app">
    <div class="placeContainer">
      <span class="placeName">{{ placeName }}</span>
      <span class="address">{{ address }}</span>
      <span class="address">{{ visitedAt }}</span>
    </div>
    <div class="feedContainer">
      <div v-if="feedImgs.length > 0" class="image-container">
        <div
          class="arrow-icon-container prev"
          @click="prevImage"
          v-show="currentIndex > 0"
        >
          <img src="/images/previous.png" alt="Previous" class="arrow-icon" />
        </div>

        <div class="centered-image">
          <img
            :src="currentImage.url"
            class="feedImg"
            :alt="'Image ' + (currentIndex + 1)"
          />
        </div>

        <div
          class="arrow-icon-container next"
          @click="nextImage"
          v-show="currentIndex < feedImgs.length - 1"
        >
          <img src="/images/next.png" alt="Next" class="arrow-icon" />
        </div>
      </div>
      <div class="contentSection">
        <div class="caption">{{ post.content }}</div>
        <div class="meta">
          <span class="nickname">{{ post.memberEmail?.nickname }}</span>
          <span class="createdAt">{{ post.createdAt }}</span>
        </div>

      <div class="button-container">
        <router-link
          :to="{
            name: 'myfeedupdate',
            params: { myplaceId: post.myplaceId },
            query: {
              placeName: this.placeName,
              address: this.address,
              visitedAt: this.visitedAt,
            },
          }"
        >
          <button class="update-btn">수정하기</button>
        </router-link>
        <button @click="deleteFeed(post.myplaceId)" class="delete-btn">삭제하기</button>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  name: "myfeed",
  data() {
    return {
      feedImgs: [],
      currentIndex: 0,
      post: {},
      memberEmail: "",
      placeName: "", 
      address: "",
      visitedAt: "",
    };
  },
  computed: {
    currentImage() {
      return this.feedImgs[this.currentIndex] || {};
    },
  },
  mounted() {
    const placeName = this.$route.query.placeName;
    const address = this.$route.query.address;
    const visitedAt = this.$route.query.visitedAt;
    const backURL = this.$root.backURL;
    this.$store.dispatch("getUserInfo").then(() => {
      this.myplaceId = this.$route.params.myplaceId;

      axios
        .get(`${backURL}/feed/download?id=${this.myplaceId}&opt=myfeed`)
        .then((response) => {
          console.log("Response Data:", response.data);
          if (response.status === 200) {
            // 이미지 속성 변경
            this.feedImgs = response.data.map((img) => ({
              url: "data:" + img.mimeType + ";base64," + img.data,
              mimeType: img.mimeType,
            }));
            console.log("Feed Images:", this.feedImgs);
          } else {
            console.error("Failed to fetch images. Status:", response.status);
          }
          this.fetchFeeds();
        })
        .catch((error) => {
          console.error("Error fetching images:", error);
        });
        this.placeName = placeName;
        this.address = address;
        this.visitedAt = visitedAt;
    });
  },
  methods: {
    fetchFeeds() {
      const backURL = this.$root.backURL;

      axios
        .get(`${backURL}/myfeed/${this.myplaceId}`)
        .then((response) => {
          this.post = response.data || {};
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
    getProfileImage() {
      return this.post.memberEmail ? this.post.memberEmail.profileImage : "";
    },
    getNickname() {
      return this.post.memberEmail ? this.post.memberEmail.nickname : "";
    },
    updateFeed() {
      this.$router.push({
        name: "myfeedupdate",
        params: { myplaceId: this.myplaceId },
      });
    },
    deleteFeed(myplaceId) {
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;
      console.log("Delete feed with ID:", myplaceId);

      Swal.fire({
        text: "정말로 삭제하시겠습니까?",
        icon: "warning",
        showDenyButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "삭제",
        denyButtonText: "취소"
      }).then((result) => {
        if (result.isConfirmed) {
          const backURL = this.$root.backURL;
          axios
            .delete(`${backURL}/myfeed/${this.myplaceId}`)
            .then((response) => {
              console.log("Feed deleted successfully:", response.data);
              this.deleteFeedImg(myplaceId)
              this.$router.push({
                name: "thememapdetail",
                params: { id: response.data.thememapId },
              });
            })
            .catch((error) => {
              console.error("Error deleting feed:", error);
            });
          Swal.fire({
            text: "피드가 삭제되었습니다.",
            icon: "success",
          });
        }
      });
    },

    deleteFeedImg(myplaceId) {
      const backURL = this.$root.backURL;

        axios.delete(`${backURL}/feed/delete`, {
          params: { id: myplaceId, opt: "myfeed" }
        }).catch((error) => {
      console.error("Failed to delete images:", error);
    });
    },

    prevImage() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
      }
    },
    nextImage() {
      if (this.currentIndex < this.feedImgs.length - 1) {
        this.currentIndex++;
      }
    },
  },
};
</script>

<style scoped>
body {
  font-family: "Arial", sans-serif;
  margin: 0;
  padding: 0;
  background-color: #fafafa;
}

#app {
  max-width: 600px;
  margin: 20px auto;
  background-color: #fff;
  border: 1px solid #e0e0e0;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  overflow: hidden; /* Hide overflow content */
}

.placeContainer {
  padding: 20px;
  background-color: #f5f5f5;
}

.placeName,
.address,
.visitedAt {
  display: block;
  margin-bottom: 5px;
  color: #333;
}

.feedContainer {
  display: flex;
  flex-direction: column;
  background-color: #fff;
}

.image-container {
  position: relative;
  max-height: 400px;
  overflow: hidden;
}

.centered-image {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.feedImg {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.arrow-icon-container {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  color: #fff;
}

.prev {
  left: 10px;
}

.next {
  right: 10px;
}

.arrow-icon {
  width: 30px;
  height: 30px;
}

.contentSection {
  padding: 20px;
  background-color: #fff;
}

.meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.nickname,
.createdAt {
  color: #666;
}

.caption {
  margin-top: 10px;
  color: #333;
}

.button-container {
  display: flex;
  justify-content: center; /* Center the buttons horizontally */
  margin-top: 20px;
}

.update-btn,
.delete-btn {
  background-color: #4c91af;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin: 0 10px; /* Add margin between buttons for better spacing */
}

.delete-btn {
  background-color: #f44336;
}

.update-btn:hover,
.delete-btn:hover {
  background-color: #86d2d0;
}
</style>