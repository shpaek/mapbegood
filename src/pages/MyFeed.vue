<template>
  <div id="app">
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
    <div class="contentSection">
      <div class="caption">{{ post.content }}</div>
      <span class="nickname">{{ post.memberEmail?.nickname }}</span>
      <span class="createdAt">{{ post.createdAt }}</span>
    </div>
    <router-link
      :to="{ name: 'myfeedupdate', params: { myplaceId: post.myplaceId } }"
    >
      <button>Update</button>
    </router-link>
    <button @click="deleteFeed(post.myplaceId)">Delete</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "myfeed",
  data() {
    return {
      feedImgs: [], // Initialize as an empty array
      currentIndex: 0, // Index of the current image
      post: {},
      memberEmail: "",
    };
  },
  computed: {
    currentImage() {
      return this.feedImgs[this.currentIndex] || {};
    },
  },
  mounted() {
    const backURL = this.$root.backURL;
    this.$store.dispatch("getUserInfo").then(() => {
      // Make sure to set this.myplaceId here
      this.myplaceId = this.$route.params.myplaceId;
      // Rest of your code...

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
    });
  },
  methods: {
    fetchFeeds() {
      const backURL = this.$root.backURL;

      axios
        .get(`${backURL}/myfeed/${this.myplaceId}`)
        .then((response) => {
          this.post = response.data || {}; // Ensure post is an object
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
      // Confirm with the user before proceeding with the delete
      if (window.confirm("Are you sure you want to delete this feed?")) {
        const backURL = this.$root.backURL;
        axios
          .delete(`${backURL}/myfeed/${this.myplaceId}`)
          .then((response) => {
            console.log("Feed deleted successfully:", response.data);
            this.$router.push({
              name: "thememapdetail",
              params: { id: response.data.thememapId }, // Replace with the correct property
            });
          })
          .catch((error) => {
            console.error("Error deleting feed:", error);
          });
      }
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
/* Add your custom Instagram-like styles here */
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

label {
  margin-bottom: 5px;
}

button {
  background-color: #87ceeb; /* Sky-blue color */
  color: white;
  padding: 10px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  width: 279.2px;
}

button:hover {
  background-color: #5f9ea0; /* Darker shade on hover */
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
.feedImg {
  width: 400px; /* Fixed width */
  height: 400px; /* Fixed height */
  object-fit: cover; /* Maintain aspect ratio and cover container */
  object-position: center; /* Center the image within the container */
  margin: 0 auto;
}
.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 15px;
}

.caption {
  margin-top: 10px;
}

.meta {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
}

.image-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  max-height: 400px; /* Adjusted maximum height */
  overflow: hidden; /* Hide any content that overflows */
}

.centered-image {
  max-width: 100%;
  max-height: 100%; /* Adjusted to ensure it doesn't exceed the height of its container */
  width: auto;
  margin: 0 auto;
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
  margin: 0 auto; /* Add some space between the image and the input */
}

.image-label {
  margin: 0;
  display: block;
  text-align: center; /* 가운데 정렬을 위해 추가 */
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
  gap: 5px; /* 이미지 사이의 간격을 조절하세요. */
}

.image-preview {
  width: 40px; /* 이미지의 크기를 조절하세요. */
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
