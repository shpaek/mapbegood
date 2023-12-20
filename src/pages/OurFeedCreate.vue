<template>
  <div id="app">
    <span class="image-label">{{ feedImgs.length }}/10</span>

    <form @submit.prevent="submitForm">
      <div class="image-container">
        <div
          class="arrow-icon-container prev"
          @click="prevImage"
          v-show="currentIndex > 0"
        >
          <img src="/images/previous.png" alt="Previous" class="arrow-icon" />
        </div>
        <div class="form-group" @click="triggerFileInput">
          <div class="imageUpload">
            <div v-if="feedImgs.length > 0" class="image-preview-container">
              <div class="centered-image">
                <img
                  :src="feedImgs[currentIndex].base64Data"
                  alt="Preview"
                  style="max-width: 300px; max-height: 300px"
                />
              </div>
            </div>
            <img v-else src="/images/photo.png" alt="photo" class="photo" />
            <input
              type="file"
              ref="fileInput"
              @change="handleFileChange"
              accept=".jpg, .jpeg, .png"
              multiple
              style="display: none"
            />
          </div>
        </div>
        <div
          class="arrow-icon-container next"
          @click="nextImage"
          v-show="currentIndex < feedImgs.length - 1"
        >
          <img src="/images/next.png" alt="Next" class="arrow-icon" />
        </div>
      </div>
      <div class="form-group">
        <label for="content"></label>
        <textarea
          v-model="feedContent"
          id="content"
          required
          placeholder="내용을 작성하세요"
        ></textarea>
      </div>
      <button type="submit">Share</button>
    </form>
    <div class="feed">
      <div v-for="post in posts" :key="post.ourplaceId" class="feed-item">
        <img
          class="avatar"
          :src="post.memberEmail?.profileImage"
          alt="Avatar"
        />
      </div>
    </div>
    <span class="placeName">{{ placeName }}</span>
    <span class="address">{{ address }}</span>
    <span class="address">{{ visitedAt }}</span>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import Swal from "sweetalert2";
export default {
  name: "ourfeedCreate",
  computed: {
    ...mapState(["userInfo"]),
  },
  beforeCreate() {
    this.$store.dispatch("getUserInfo");
  },
  data() {
    return {
      feedImgs: [],
      currentIndex: 0,
      groupId: null,
      ourplaceId: null,
      feedContent: "",
      posts: [],
      image: null,
      imageList: [],
      placeName: null,
      address: null,
      visitedAt: null,
    };
  },
  mounted() {
    this.placeName = this.$route.query.placeName;
    this.address = this.$route.query.address;
    this.visitedAt = this.$route.query.visitedAt;
  },
  created() {
    this.ourplaceId = this.$route.params.ourplaceId;
    this.groupId = this.$route.params.groupId;
  },
  methods: {
    submitForm() {
      this.createFeed(this.ourplaceId);
    },
    createFeed(ourplaceId) {
      const backURL = this.$root.backURL;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;
      const memberEmail = this.userInfo.email;
      // Create feed
      const createData = {
        ourplaceId: {
          id: this.ourplaceId,
        },
        groupId: this.groupId,
        content: this.feedContent,
      };

      axios
        .post(
          `${backURL}/ourfeed/${this.groupId}/${this.ourplaceId}/${memberEmail}`,
          createData
        )
        .then((response) => {
          console.log("Feed created successfully:", response.data);

          if (this.feedImgs.length > 0) {
            this.uploadImages(this.ourplaceId);
          }
          Swal.fire({
            text: "피드가 생성되었습니다.",
            icon: "success",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              this.$router.push({
                name: "ourfeed",
                params: { ourplaceId: ourplaceId },
              });
            }
          });
        })
        .catch((error) => {
          console.error("Error creating feed:", error);
        });
    },

    uploadImages(ourplaceId) {
      const backURL = this.$root.backURL;
      const memberEmail = this.userInfo.email;
      const formData = new FormData();
      formData.append("id", this.ourplaceId + memberEmail);
      formData.append("opt", "ourfeed");
      this.feedImgs.forEach((file, index) => {
        formData.append("files", file.file);
      });

      axios
        .post(`${backURL}/feed/upload`, formData)
        .then((response) => {
          console.log("Images uploaded successfully:", response.data);
        })
        .catch((error) => {
          console.error("Error uploading images:", error);
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

    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    async handleFileChange(event) {
      const files = event.target.files;
      const maxFiles = Math.min(files.length, 10);
      this.feedImgs = [];
      console.log("Selected Files:", files);
      for (let i = 0; i < maxFiles; i++) {
        const file = files[i];
        if (!this.isValidFileExtension(file)) {
          Swal.fire({
            title: "잘못된 파일형식",
            text: "JPG, PNG 파일 형식만 선택 가능합니다.",
            icon: "error",
          });
          return;
        }
        const base64Data = await this.readFileAsync(files[i]);
        this.feedImgs.push({ file: files[i], base64Data });
      }
      this.currentIndex = 0;
    },

    readFileAsync(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve(reader.result);
        reader.onerror = reject;
        reader.readAsDataURL(file);
      });
    },
    isValidFileExtension(file) {
      const allowedExtensions = [".jpg", ".jpeg", ".png"];
      const fileName = file.name.toLowerCase();
      return allowedExtensions.some((ext) => fileName.endsWith(ext));
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
  background-color: #87ceeb;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

button:hover {
  background-color: #5f9ea0;
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
  max-width: 100%; /* 이미지가 부모 너비를 초과하지 않도록 조정 */
  max-height: 400px;
  object-fit: contain; /* 원본 비율을 유지한 채로 조절 */
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
</style>
