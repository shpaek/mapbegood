<template>
  <div id="app">
    <div v-for="(post, index) in feedList" :key="index" class="feed-item">
      <img class="avatar" :src="getProfileImage(post)" alt="Avatar" />
      <div>
        <div class="caption">{{ post.content }}</div>
        <div class="meta">
          <span class="nickname">{{ getNickname(post) }}</span>
          <span class="createdAt">{{ post.createdAt }}</span>
        </div>
        <div v-if="post.feedImgs && post.feedImgs.length > 0">
          <div v-if="post.feedImgs.length > 1">
            <img
              v-for="(image, imgIndex) in post.feedImgs"
              :key="imgIndex"
              :src="image.url"
              class="feedImg"
              :alt="'Image ' + (imgIndex + 1)"
              v-show="imgIndex === currentIndex"
            />
            <div>
              <button @click="prevImage" v-show="currentIndex > 0">
                Previous
              </button>
              <button
                @click="nextImage"
                v-show="currentIndex < post.feedImgs.length - 1"
              >
                Next
              </button>
            </div>
          </div>
          <div v-else>
            <img
              :src="post.feedImgs[0].url"
              class="feedImg"
              :alt="'Image ' + (currentIndex + 1)"
            />
          </div>
        </div>

        <router-link :to="{ name: 'ourfeedupdate', params: { groupId: post.groupId, ourplaceId: post.ourplaceId, memberNickname: post.memberNickname } }">
        <button>Update</button>
      </router-link>
        <button @click="deleteFeed(post.ourplaceId)">Delete</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";

export default {
  name: "ourfeed",
  data() {
    return {
      groupId: null,
      email: null,
      ourplaceId: null,
      feedList: [], // List to store fetched feeds
      currentIndex: 0,
      memberList: [], // List to store member emails
    };
  },
  computed: {
    currentImage() {
    const currentFeedImages = this.feedList[this.currentIndex]?.feedImgs || [];
    return currentFeedImages[this.currentIndex] || {};
  },
    ...mapState(["userInfo"]),
  },
  created() {
    this.$store.dispatch("getUserInfo").then(() => {
      this.email = this.userInfo.email;
      this.groupId = this.$route.params.groupId;
      this.ourplaceId = this.$route.params.ourplaceId;

      // Fetch feeds for the current user
      this.fetchFeedsByEmail(this.email);
    });
  },
  methods: {
    async fetchMemberEmails() {
      const backURL = this.$root.backURL;
      const url = `${this.backURL}/groupmember/${this.groupId}`;
      const accessToken = `Bearer ${localStorage.getItem("mapbegoodToken")}`;
      axios.defaults.headers.common["Authorization"] = accessToken;

      try {
        const response = await axios.get(url, {
          withCredentials: true,
          headers: { "Content-Type": "application/json" },
        });

        this.memberList = response.data;
        // Fetch feeds for each member email
        await this.fetchFeedsForMemberEmails();
      } catch (error) {
        console.error("Error fetching member emails:", error);
      }
    },

    async fetchFeedsForMemberEmails() {
      for (const member of this.memberList) {
        await this.fetchFeedsByEmail(member.member.email);
      }
    },
// Modify fetchFeedImages to resolve the promise with the images
async fetchFeedImages(ourplaceId, memberEmail) {
  const id = `${ourplaceId}${memberEmail}`;
  console.log("Fetching feed images for:", id);
  const backURL = this.$root.backURL;
  try {
    const response = await axios.get(`${this.backURL}/feed/download`, {
      params: { id, opt: "ourfeed" },
      responseType: "json",
    });

    console.log("Image response:", response);

    const imageData = response.data;

    if (imageData && imageData.length > 0) {
      const images = imageData.map((image) => ({
        url: `data:${image.mimeType};base64,${image.data}`,
        mimeType: image.mimeType,
      }));

      return images; // Resolve the promise with the images
    } else {
      return null;
    }
  } catch (error) {
    console.error("Failed to fetch images:", error);
    return null;
  }
},

// Modify fetchFeedsByEmail to correctly wait for image fetching
async fetchFeedsByEmail(email) {
  const backURL = this.$root.backURL;
  const url = `${this.backURL}/ourfeed/${this.groupId}/${this.ourplaceId}/${email}`;
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

    updateFeed(ourplaceId) {
      console.log("Update feed with ID:", ourplaceId);
      // Implement your update logic
    },

    deleteFeed(ourplaceId) {
      const accessToken = `Bearer ${localStorage.getItem("mapbegoodToken")}`;
      axios.defaults.headers.common["Authorization"] = accessToken;
      console.log("Delete feed with ID:", ourplaceId);

      if (window.confirm("Are you sure you want to delete this feed?")) {
        const backURL = this.$root.backURL;
        axios
          .delete(
            `${backURL}/ourfeed/${this.groupId}/${ourplaceId}/${this.email}`
          )
          .then((response) => {
            console.log("Feed deleted successfully:", response.data);
            // Remove the deleted feed from the feedList
            this.feedList = this.feedList.filter(
              (feed) => feed.ourplaceId !== ourplaceId
            );
          })
          .catch((error) => {
            console.error("Error deleting feed:", error);
          });
      }
    },

    async fetchData() {
      for (const member of this.memberList) {
        await this.fetchFeedsByEmail(member.member.email);
      }
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

    getProfileImage(feed) {
      return feed.memberEmail ? feed.memberEmail.profileImage : "";
    },

    getNickname(feed) {
      return feed.memberEmail ? feed.memberEmail.nickname : "";
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
