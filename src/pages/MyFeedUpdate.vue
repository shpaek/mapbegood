<template>
    <div id="app">
      <form @submit.prevent="submitForm">
        <div class="form-group">
          <label for="content">Caption:</label>
          <textarea v-model="feedContent" id="content" required placeholder="Write a caption..."></textarea>
        </div>
  
        <div class="form-group">
          <label for="image">Image Upload (up to 10 files):</label>
          <input type="file" ref="fileInput" @change="handleFileChange" accept="image/*" multiple>
        </div>
  
        <button type="submit">{{ isUpdateMode ? 'Update' : 'Share' }}</button>
      </form>
  
      <!-- Add your feed display code here if needed -->
      <div class="feed">
        <div v-for="post in posts" :key="post.myplaceId" class="feed-item">
          <img class="avatar" :src="post.memberEmail?.profileImage" alt="Avatar">
          <div>
            <div class="caption">{{ post.content }}</div>
            <div class="meta">
              <span class="nickname">{{ post.memberEmail?.nickname }}</span>
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
    name: 'FeedCreate',
    data() {
      return {
        myplaceIdForTesting: 108,
        feedContent: '',
        feedImages: [],
        posts: [],
        isUpdateMode: false,
      };
    },
    mounted() {
      // Fetch existing feed data
      this.fetchPosts(); // moved fetchPosts to mounted
    },
    methods: {
      submitForm() {
        const myplaceId = 108;
        if (this.isUpdateMode) {
          // Update existing feed
          this.updateFeed(myplaceId);
        } else {
          // Create new feed
          this.createFeed(myplaceId);
        }
      },
      createFeed(myplaceId) {
        const backURL = this.$root.backURL;
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
        axios.defaults.headers.common["Authorization"] = accessToken;
  
        // Create feed
        const createData = {
          myplaceId: myplaceId,
          content: this.feedContent,
          // Add other properties as needed
        };
  
        axios.post(`${backURL}/myfeed/${myplaceId}`, createData)
          .then(response => {
            console.log('Feed created successfully:', response.data);
  
            // Upload images if selected
            if (this.feedImages.length > 0) {
              this.uploadImages(myplaceId);
            } else {
              // No images selected, fetch posts directly
              this.fetchPosts();
            }
          })
          .catch(error => {
            console.error('Error creating feed:', error);
          });
      },
      updateFeed(myplaceId) {
  const backURL = this.$root.backURL;
  const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
  axios.defaults.headers.common["Authorization"] = accessToken;

  // Update feed
  const updateData = {
    myplaceId: myplaceId,
    content: this.feedContent,
    // Add other properties as needed
  };

  axios.put(`${backURL}/myfeed/${myplaceId}`, updateData)
    .then(response => {
      console.log('Feed updated successfully:', response.data);

      // Upload images if selected
      if (this.feedImages.length > 0) {
        this.uploadImages(myplaceId);
      } else {
        // No images selected, fetch posts directly
        this.fetchPosts();
      }
    })
    .catch(error => {
      console.error('Error updating feed:', error);
    });
},
  uploadImages(myplaceId) {
    const backURL = this.$root.backURL;
  
    // Upload images
    const formData = new FormData();
    formData.append('id', myplaceId);
    formData.append('opt', 'myfeed'); // Add your image option here
    this.feedImages.forEach((file, index) => {
      formData.append('files', file);  // Ensure the keys match the server parameter names
    });
  
    axios.post(`${backURL}/feed/upload`, formData)
      .then(response => {
        console.log('Images uploaded successfully:', response.data);
        // Fetch posts after uploading images
        this.fetchPosts();
      })
      .catch(error => {
        console.error('Error uploading images:', error);
      });
  },
  handleFileChange(event) {
    // Get an array of files
    const files = event.target.files;
  
    // Only handle the first 10 files (change the limit as needed)
    const maxFiles = Math.min(files.length, 10);
    this.feedImages = [];
  
    for (let i = 0; i < maxFiles; i++) {
      this.feedImages.push(files[i]);
    }
  },
  fetchPosts() {
      const backURL = this.$root.backURL;
      axios.get(`${backURL}/myfeed/${this.myplaceIdForTesting}`)
        .then(response => {
          this.posts = [response.data];
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
    },
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
    