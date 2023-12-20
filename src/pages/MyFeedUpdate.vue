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
      <button type="submit">수정하기</button>
    </form>

  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
  name: "myfeedupdate",
  data() {
    return {
      feedImgs: [],
      currentIndex: 0,
      myplaceId: "",
      feedContent: "",
      posts: [],
      image: null,
      imageList: [],
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
      this.updateFeed(this.myplaceId);
    },
    updateFeed(myplaceId) {
      const backURL = this.$root.backURL;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      const updateData = {
        myplaceId: myplaceId,
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
            .put(`${backURL}/myfeed/${myplaceId}`, updateData)
            .then((response) => {
              console.log("Feed updated successfully:", response.data);
              this.fetchPosts();
              this.$router.push({
                name: "myfeed",
                params: { myplaceId: myplaceId },
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
      axios
        .get(`${backURL}/myfeed/${this.myplaceId}`)
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
  margin: 15px 0;
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
  width: 558.4px;
}

label {
  margin-bottom: 5px;
}

textarea {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
  resize: vertical;
  min-height: 100px;
}

input[type="file"] {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

button {
  background-color: #87ceeb; /* Sky-blue color */
  color: white;
  padding: 10px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
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

.post-image {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
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
