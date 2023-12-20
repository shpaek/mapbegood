<template>
  <div id="app">
    <div class="placeContainer">
      <span class="placeName">{{ placeName }}</span>
      <span class="address">{{ address }}</span>
      <span class="visitedAt">{{ visitedAt }}</span>
    </div>

    <div v-for="(post, index) in feedList" :key="index" class="feedContainer">
      <span class="image-label">{{ post.feedImgs.length }}/10</span>

      <div v-if="post.feedImgs && post.feedImgs.length > 0" class="image-container">
        <div v-if="post.feedImgs.length > 1" class="arrow-icon-container prev" @click="prevImage" v-show="currentIndex > 0">
          <img src="/images/previous.png" alt="Previous" class="arrow-icon" />
        </div>

        <div class="centered-image">
          <img
            v-for="(image, imgIndex) in post.feedImgs"
            :key="imgIndex"
            :src="image.url"
            class="feedImg"
            :alt="'Image ' + (imgIndex + 1)"
            v-show="imgIndex === currentIndex"
          />
        </div>

        <div class="arrow-icon-container next" @click="nextImage" v-show="currentIndex < post.feedImgs.length - 1">
          <img src="/images/next.png" alt="Next" class="arrow-icon" />
        </div>
      </div>

      <div v-else>
        <img :src="post.feedImgs[0].url" class="feedImg" :alt="'Image ' + (currentIndex + 1)" />
      </div>

      <div class="contentSection">
        <div class="caption">{{ post.content }}</div>
        <span class="nickname">{{ post.memberEmail.nickname }}</span>
        <span class="createdAt">{{ post.createdAt }}</span>
      </div>

      <div v-if="emailMatchesCurrentUser(post.memberEmail.email)" class="button-container">
        <router-link
          :to="{
            name: 'ourfeedupdate',
            params: {
              groupId: post.groupId,
              ourplaceId: post.ourplaceId,
              memberNickname: post.memberNickname,
            },
          }"
        >
          <button class="update-btn">수정하기</button>
        </router-link>

        <button @click="deleteFeed(post.ourplaceId)" class="delete-btn">삭제하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

export default {
  name: "ourfeed",
  data() {
    return {
      groupId: null,
      email: null,
      ourplaceId: null,
      feedList: [],
      currentIndex: 0,
      memberList: [],
      groupThememapId: null,
      placeName: null,
      address: null,
      visitedAt: null,
    };
  },
  computed: {
    currentImage() {
      const currentFeedImages =
        this.feedList[this.currentIndex]?.feedImgs || [];
      return currentFeedImages[this.currentIndex] || {};
    },
  },
  created() {
    this.$store.dispatch("getUserInfo").then(() => {
      this.email = this.userInfo.email;
      this.groupId = this.$route.params.groupId;
      this.ourplaceId = this.$route.params.ourplaceId;
      this.groupThememapId = this.$route.params.groupThememapId;
      this.placeName = this.$route.query.placeName;
      this.address = this.$route.query.address;
      this.visitedAt = this.$route.query.visitedAt;
      this.fetchFeedsByEmail(this.email);
    });
  },
  methods: {
    async fetchFeedsByEmail(email) {
      const backURL = this.$root.backURL;
      const url = `${backURL}/ourfeed/${this.groupId}/${this.ourplaceId}/${email}`;
      const accessToken = `Bearer ${localStorage.getItem("mapbegoodToken")}`;
      axios.defaults.headers.common["Authorization"] = accessToken;

      try {
        const response = await axios.get(url, {
          withCredentials: true,
          headers: { "Content-Type": "application/json" },
        });

        if (response.data) {
          let feed = response.data;

          feed.feedImgs = await this.fetchFeedImages(this.ourplaceId, email);

          this.feedList.push(feed);
        } else {
          console.error(`No data received for ${email}`);
        }
      } catch (error) {
        console.error(`Error fetching feeds for ${email}:`, error);
      }
    },

    async fetchFeedImages(ourplaceId, memberEmail) {
      const id = `${ourplaceId}${memberEmail}`;
      console.log("Fetching feed images for:", id);
      const backURL = this.$root.backURL;
      try {
        const response = await axios.get(`${backURL}/feed/download`, {
          params: { id: id, opt: "ourfeed" },
          responseType: "json",
        });

        console.log("Image response:", response);

        const imageData = response.data;

        if (imageData && imageData.length > 0) {
          const images = imageData.map((image) => ({
            url: `data:${image.mimeType};base64,${image.data}`,
            mimeType: image.mimeType,
          }));

          return images;
        } else {
          return null;
        }
      } catch (error) {
        console.error("Failed to fetch images:", error);
        return null;
      }
    },

    deleteFeed(ourplaceId) {
      const accessToken = `Bearer ${localStorage.getItem("mapbegoodToken")}`;
      axios.defaults.headers.common["Authorization"] = accessToken;
      console.log("Delete feed with ID:", ourplaceId);

      Swal.fire({
        text: "정말로 삭제하시겠습니까?",
        icon: "warning",
        showDenyButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "삭제",
        denyButtonText: "취소",
      }).then((result) => {
        if (result.isConfirmed) {
          const backURL = this.$root.backURL;
          axios
            .delete(
              `${backURL}/ourfeed/${this.groupId}/${this.ourplaceId}/${this.email}`
            )
            .then((response) => {
              console.log("Feed deleted successfully:", response.data);
              this.feedList = this.feedList.filter(
                (feed) => feed.ourplaceId !== ourplaceId
              );
              // 이미지 삭제 메소드 호출
              this.deleteFeedImg(ourplaceId, this.email);
              this.$router.push({
                name: "detailgroupmap",
                params: {
                  groupId: this.groupId,
                  groupThememapId: this.groupThememapId,
                },
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
    deleteFeedImg(ourplaceId, email) {
      const id = `${ourplaceId}${this.email}`;
      axios
        .delete(`${this.$root.backURL}/feed/delete`, {
          params: { id: id, opt: "ourfeed" },
        })
        .catch((error) => {
          console.error("Failed to delete images:", error);
        });
    },

    prevImage() {
      if (this.currentIndex > 0) {
        this.currentIndex--;
      }
    },

    nextImage() {
      const currentFeedImages =
        this.feedList[this.currentIndex]?.feedImgs || [];
      if (this.currentIndex < currentFeedImages.length - 1) {
        this.currentIndex++;
      }
    },

    emailMatchesCurrentUser(feedEmail) {
      return feedEmail === this.email;
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
  padding: 20px;
  text-align: center;
}

.label {
  margin-bottom: 5px;
}

.button-container {
  display: flex;
  justify-content: space-between;
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
}

.delete-btn {
  background-color: #f44336;
}

.update-btn:hover,
.delete-btn:hover {
  background-color: #86d2d0;
}

.placeContainer {
  text-align: left;
  margin-bottom: 20px;
}

.placeContainer span {
  display: block;
  margin-bottom: 5px;
}

.placeName {
  font-size: larger;
  font-weight: bold;
}

.feedContainer {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  padding: 20px;
  text-align: center;
  max-width: 600px;
  margin: 20px auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.image-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  max-height: 400px;
  overflow: hidden;
}

.contentSection {
  width: 400px;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.centered-image {
  max-width: 100%;
  max-height: 100%;
  width: auto;
  margin: 0 auto;
}

.feedImg {
  width: 400px;
  height: 400px;
  object-fit: cover;
  object-position: center;
  margin: 0 auto;
}

.image-label {
  margin: 0;
  display: block;
  text-align: center;
}

.image-preview-container {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
}

.image-preview {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 3px;
  cursor: pointer;
}

.image-count {
  margin: 0;
  display: block;
  text-align: center;
}
</style>
