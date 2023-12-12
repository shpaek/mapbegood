<template>
    <div id="app">
      <form @submit.prevent="createFeed">
        <div class="form-group">
          <label for="content">Caption:</label>
          <textarea v-model="feedContent" id="content" required placeholder="Write a caption..."></textarea>
        </div>
  
        <div class="form-group">
          <label for="image">Image Upload:</label>
          <input type="file" ref="fileInput" @change="handleFileChange" accept="image/*">
        </div>
  
        <button type="submit">Share</button>
      </form>
  
      <div class="feed">
        <div v-for="post in posts" :key="post.myplaceId" class="feed-item">
          <img class="avatar" :src="post.memberEmail.profileImage" alt="Avatar">
          <div>
            <div class="caption">{{ post.content }}</div>
            <div class="meta">
              <span class="nickname">{{ post.memberEmail.nickname }}</span>
              <span class="createdAt">{{ post.createdAt }}</span>
            </div>
            <img class="post-image" :src="post.image" alt="Post Image">
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    name: "FeedCreate",
    data() {
      return {
        feedContent: "",
        feedImage: null,
        posts: [], // Assuming you have a data property to store posts
      };
    },
    methods: {
      createFeed() {
        const myplaceId = 102;
        const backURL = this.$root.backURL;
        const accessToken = "Bearer "+localStorage.getItem("mapbegoodToken")
        axios.defaults.headers.common["Authorization"] = accessToken;
        const formData = new FormData();
        formData.append('content', this.feedContent);
        formData.append('image', this.feedImage);
  
        axios.post(`${backURL}/myfeed/${myplaceId}`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then(response => {
          console.log('Feed created successfully:', response.data);
          // Update posts or fetch them again after creating a new post
          this.fetchPosts();
        })
        .catch(error => {
          console.error('Error creating feed:', error);
        });
      },
      handleFileChange(event) {
        this.feedImage = event.target.files[0];
      },
      fetchPosts() {
        // Implement the logic to fetch posts here
        // Example:
        // axios.get(`${backURL}/myfeed/${myplaceId}`)
        //   .then(response => {
        //     this.posts = [response.data];
        //   })
        //   .catch(error => {
        //     console.error('Error fetching data:', error);
        //   });
      },
    },
    mounted() {
      this.fetchPosts();
    },
  };
  </script>
  
  <style scoped>
  /* Add your custom Instagram-like styles here */
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
    padding: 20px;
  }
  
  form {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
  }
  
  .form-group {
    margin-bottom: 15px;
  }
  
  label {
    margin-bottom: 5px;
  }
  
  textarea {
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
    resize: vertical; /* Allow vertical resizing */
    min-height: 100px; /* Set the minimum height */
  }
  
  input[type="file"] {
    margin-bottom: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
  }
  
  button {
    background-color: #4caf50;
    color: white;
    padding: 10px;
    border: none;
    border-radius: 3px;
    cursor: pointer;
  }
  
  button:hover {
    background-color: #45a049;
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
  </style>
  