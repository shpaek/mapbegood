<template>
  <div id="app">
    <div class="placeContainer">
      <span class="placeName">{{ placeName }}</span>
      <span class="address">{{ address }}</span>
      <span class="address">{{ visitedAt }}</span>
    </div>
    <span class="image-label">{{ feedImgs.length }}/10</span>
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

    <form @submit.prevent="submitForm">
      <div class="form-group">
        <label for="content"></label>
        <textarea
          v-model="feedContent"
          id="content"
          required
          placeholder="내용을 작성하세요."
        ></textarea>
      </div>
      <button type="submit" class="button">수정하기</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import Swal from "sweetalert2";
export default {
  name: "OurFeed",
  data() {
    return {
      groupId: null,
      ourplaceId: null,
      feedImgs: [],
      currentIndex: 0,
      feedContent: "",
      posts: [],
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
    ...mapState(["userInfo"]),
  },
  mounted() {
    const placeName = this.$route.query.placeName;
    const address = this.$route.query.address;
    const visitedAt = this.$route.query.visitedAt;
    console.log("OurPlaceId:", this.$route.params.ourplaceId);
    this.$store.dispatch("getUserInfo").then(() => {
      console.log("UserInfo:", this.userInfo);
      this.memberEmail = this.userInfo.email;
      this.groupId = this.$route.params.groupId;
      this.ourplaceId = this.$route.params.ourplaceId;

      const backURL = this.$root.backURL;
      const id = `${this.ourplaceId}${this.memberEmail}`;
      axios
        .get(`${backURL}/feed/download?id=${id}&opt=ourfeed`)
        .then((response) => {
          console.log("Response Data:", response.data);
          if (response.status === 200) {
            this.feedImgs = response.data.map((img) => ({
              url: "data:" + img.mimeType + ";base64," + img.data,
              mimeType: img.mimeType,
            }));
            console.log("Feed Images:", this.feedImgs);
          } else {
            console.error("Failed to fetch images. Status:", response.status);
          }
          this.fetchPosts();
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
    submitForm() {
      this.updateFeed(this.ourplaceId);
      const { groupId, ourplaceId, memberNickname } = this.$route.params;
    },
    updateFeed(ourplaceId) {
      const backURL = this.$root.backURL;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      const updateData = {
        ourplaceId: {
          id: this.ourplaceId,
        },
        groupId: this.groupId,
        content: this.feedContent,
      };

      Swal.fire({
        text: "변경사항을 저장하시겠습니까?",
        icon: "question",
        showDenyButton: true,
        confirmButtonText: "저장",
        denyButtonText: `취소`,
      }).then((result) => {
        if (result.isConfirmed) {
          axios
            .put(
              `${backURL}/ourfeed/${this.groupId}/${this.ourplaceId}/${this.memberEmail}`,
              updateData
            )
            .then((response) => {
              console.log("Feed updated successfully:", response.data);
              this.$router.push({
                name: "ourfeed",
                params: {
                  ourplaceId,
                },
                query: {
                  placeName: this.placeName,
                  address: this.address,
                  visitedAt: this.visitedAt,
                },
              });
            })
            .catch((error) => {
              console.error("Error updating feed:", error);
            });
          Swal.fire({ text: "피드가 수정되었습니다.", icon: "success" });
        } else if (result.isDenied) {
          Swal.fire({ text: "취소되었습니다.", icon: "warning" });
        }
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
    fetchPosts() {
      const backURL = this.$root.backURL;
      const accessToken = `Bearer ${localStorage.getItem("mapbegoodToken")}`;
      axios.defaults.headers.common["Authorization"] = accessToken;
      axios
        .get(
          `${backURL}/ourfeed/${this.groupId}/${this.ourplaceId}/${this.memberEmail}`
        )
        .then((response) => {
          const postData = response.data;
          this.feedContent = postData.content;
          this.posts = [postData];
        })
        .catch((error) => {
          console.error("Error fetching data:", error);
        });
    },
  },
};
</script>
<style scoped>
/* Add your Instagram-like styles here */
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
}

form {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

textarea {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
  resize: vertical;
  min-height: 100px;
  width: 100%;
}

.caption {
  margin-bottom: 5px;
  font-weight: bold;
}

.share-btn {
  background-color: #009688;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.share-btn:hover {
  background-color: #00796b;
}

.feed {
  display: flex;
  flex-direction: column;
}

.feed-item {
  border-bottom: 1px solid #e0e0e0;
  padding: 15px;
  display: flex;
  align-items: center;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
}

.post-content {
  width: 100%;
}

.post-image {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
  margin-bottom: 10px;
}

.meta {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
}

.caption {
  margin-top: 10px;
  font-weight: bold;
}

.image-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
}

.arrow-icon-container {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
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

.photo {
  width: 40px;
  height: 40px;
  margin: 0 auto;
}

.image-label {
  margin: 0;
  display: block;
  text-align: center;
}

.imageUpload {
  display: flex;
  align-items: center;
  justify-content: center;
  width: auto;
  height: 400px;
}

.image-preview-container {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
}

.feedImg {
  width: 400px;
  height: 400px;
  object-fit: cover;
  margin: 0 auto;
}

button {
  background-color: #4c91af;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s;
}

button:hover {
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
</style>
