<template>
  <div id="app">
    <div class="feed-item" v-for="post in posts" :key="post.myplaceId">
      <img class="avatar" :src="post.memberEmail.profileImage" alt="Avatar">
      <div>
        <div class="caption">{{ post.content }}</div>
        <div class="meta">
          <span class="nickname">{{ post.memberEmail.nickname }}</span>
          <span class="createdAt">{{ post.createdAt }}</span>
        </div>
        <img class="post-image" :src="post.image" alt="Post Image">
        
        <!-- Buttons for Update and Delete -->
        <button @click="updateFeed(post.myplaceId)">Update</button>
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
      posts: [],
    };
  },
  mounted() {
    // Fetch feeds on component mount
    this.fetchFeeds();
  },
  methods: {
    fetchFeeds() {
      // Replace 'myplaceId' with your actual value
      const myplaceId = 102; // Replace with your myplace ID
      const backURL = this.$root.backURL;

      // Make an HTTP GET request to retrieve data from the server
      axios.get(`${backURL}/myfeed/${myplaceId}`)
        .then(response => {
          this.posts = [response.data]; // Wrap the response in an array to match the structure
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    },
    updateFeed(myplaceId) {
      // Implement the update logic here
      console.log('Update feed with ID:', myplaceId);
      // You can navigate to an update page or show a modal for update
      // Example: this.$router.push(`/update-feed/${myplaceId}`);
    },
    deleteFeed(myplaceId) {
      const accessToken = "Bearer "+localStorage.getItem("mapbegoodToken")
      axios.defaults.headers.common["Authorization"] = accessToken;
      console.log('Delete feed with ID:', myplaceId);
      // Confirm with the user before proceeding with the delete
      if (window.confirm('Are you sure you want to delete this feed?')) {
        const backURL = this.$root.backURL;
        axios.delete(`${backURL}/myfeed/${myplaceId}`)
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

.post-image {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
}

.caption {
  margin-top: 10px;
}
</style>
