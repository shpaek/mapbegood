<template>
  <div id="app">
    <div class="feed-item" v-show="post" :key="post.myplaceId">
      <img class="avatar" :src="getProfileImage()" alt="Avatar">
      <div>
        <div class="caption">{{ post.content }}</div>
        <div class="meta">
          <span class="nickname">{{ getNickname() }}</span>
          <span class="createdAt">{{ post.createdAt }}</span>
        </div>
        <div v-if="feedImgs.length > 0">
          <div>
            <img :src="currentImage.url" class="feedImg" :alt="'Image ' + (currentIndex + 1)">
          </div>
          <div>
            <button @click="prevImage" v-show="currentIndex > 0">Previous</button>
            <button @click="nextImage" v-show="currentIndex < feedImgs.length - 1">Next</button>
          </div>
        </div>

        <!-- Buttons for Update and Delete -->
        <button @click="updateFeed(post)">Update</button>
        <button @click="deleteFeed(post.myplaceId)">Delete</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "MyFeed",
  data() {
    return {
      feedImgs: [], // Initialize as an empty array
      currentIndex: 0, // Index of the current image
      post: {
      },
      memberEmail: ""
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
    
    axios.get(`${backURL}/feed/download?id=${this.myplaceId}&opt=myfeed`)
      .then(response => {
        console.log('Response Data:', response.data);
        if (response.status === 200) {
          // 이미지 속성 변경
          this.feedImgs = response.data.map(img => ({
            url: 'data:' + img.mimeType + ';base64,' + img.data,
            mimeType: img.mimeType,
          }));
          console.log('Feed Images:', this.feedImgs);
        } else {
          console.error('Failed to fetch images. Status:', response.status);
        }
        this.fetchFeeds();
      })
      .catch(error => {
        console.error('Error fetching images:', error);
      });
    });
  },
  methods: {
    fetchFeeds() {
      const backURL = this.$root.backURL;

      axios.get(`${backURL}/myfeed/${this.myplaceId}`)
        .then(response => {
          this.post = response.data || {}; // Ensure post is an object
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    },
    getProfileImage() {
      return this.post.memberEmail ? this.post.memberEmail.profileImage : '';
    },
    getNickname() {
      return this.post.memberEmail ? this.post.memberEmail.nickname : '';
    },
    updateFeed() {
      this.$router.push({ name: 'myfeedupdate', params: { myplaceId: this.myplaceId } });
    },
    deleteFeed(myplaceId) {
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;
      console.log('Delete feed with ID:', myplaceId);
      // Confirm with the user before proceeding with the delete
      if (window.confirm('Are you sure you want to delete this feed?')) {
        const backURL = this.$root.backURL;
        axios.delete(`${backURL}/myfeed/${this.myplaceId}`)
          .then(response => {
            console.log('Feed deleted successfully:', response.data);
            // Fetch updated feeds after deletion
            this.fetchFeeds();
          })
          .catch(error => {
            console.error('Error deleting feed:', error);
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
  body {
    font-family: 'Arial', sans-serif;
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
    overflow: hidden;
  }

  .feed-item {
    border-bottom: 1px solid #e0e0e0;
    padding: 15px;
    display: flex;
    align-items: center;
  }

  .avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
  }

  .feedImg {
    width: 100%;
    max-height: 400px;
    object-fit: cover;
  }


  .caption {
    margin-top: 10px;
  }
</style>
