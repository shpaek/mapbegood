<template>
  <div v-show="isModalOpen && place" class="modal" @click="backClickHandler">
    <v-sheet :elevation="11" height="400" width="500" rounded @click.stop>
      <v-app id="inspire" style="overflow: hidden">
        <v-container fluid fill-height>
          <v-row justify="center" align="center" style="height: 100%">
            <v-col>
              <v-toolbar color="blue" dark tabs>
                <v-text-field
                  class="mx-3"
                  flat
                  label="Search"
                  prepend-inner-icon="search"
                  solo-inverted
                ></v-text-field>

                <template v-slot:extension>
                  <v-tabs
                    v-model="tabs"
                    centered
                    color="black"
                    slider-color="white"
                  >
                    <v-tab key="personal" @click="setTab('personal')"
                      >개인</v-tab
                    >
                    <v-tab key="group" @click="setTab('group')">그룹</v-tab>
                  </v-tabs>
                </template>
              </v-toolbar>

              <v-tabs-items v-model="tabs">
                <v-tab-item
                  key="personal"
                  :key="tabs === 'personal' ? 'personal' : 'group'"
                >
                  <v-card>
                    <v-list
                      two-line
                      style="max-height: 300px; overflow-y: auto"
                    >
                      <div
                        v-for="(item, index) in tabs === 'personal'
                          ? myThememapList
                          : groupList"
                        :key="index"
                      >
                        <v-list-tile avatar ripple @click="toggle(index)">
                          <v-list-tile-content @click="handleItemClick(item)">
                            <div>
                              <v-list-tile-title v-if="tabs === 'personal'">{{
                                item.themeMapDto.name
                              }}</v-list-tile-title>
                              <v-list-tile-title v-else>{{
                                item.name
                              }}</v-list-tile-title>
                            </div>
                            <div v-if="tabs === 'personal'">
                              <v-list-tile-sub-title>{{
                                item.themeMapDto.memo
                              }}</v-list-tile-sub-title>
                            </div>
                            <div v-else>
                              <v-list-tile-sub-title
    v-for="groupItem in item.groupThememapList"
    :key="groupItem.id"
    @click="addOurPlace(groupItem.id)"
  >
    <span style="display: block">{{ groupItem.name }}</span>
  </v-list-tile-sub-title>
                            </div>
                          </v-list-tile-content>
                        </v-list-tile>
                        <v-divider
                          v-if="
                            index + 1 <
                            (tabs === 'personal' ? myThememapList : groupList)
                              .length
                          "
                          :key="'divider-' + index"
                        ></v-divider>
                      </div>
                    </v-list>
                  </v-card>
                </v-tab-item>
              </v-tabs-items>

              <button
                type="button"
                class="close"
                id="b2"
                @click="backClickHandler"
              >
                닫기
              </button>
            </v-col>
          </v-row>
        </v-container>
      </v-app>
    </v-sheet>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "AddBookmark",
  props: {
    isModalOpen: Boolean,
    groupId: Number,
    place: Object,
  },
  data() {
    return {
      name: "",
      userList: [],
      myThememapList: [],
      groupList: [],
      emptyMsg: "",
      tabs: "personal", // Set a default tab
      // text: " do eiusmodquip ex ea commodo consequat.",
      selected: [], // Added selected array
      clickedThemeMapId: null,
    };
  },
  mounted() {
    this.loadMymapList();
    this.setTab("personal");
  },
  methods: {
    toggle(index) {
      const clickedItem =
        this.tabs === "personal"
          ? this.myThememapList[index]
          : this.groupList[index];
      this.clickedThemeMapId =
        this.tabs === "personal"
          ? clickedItem.themeMapDto.id
          : clickedItem.groupThememapList.id;
      // console.log("Clicked ThemeMapId:", this.clickedThemeMapId);
      const i = this.selected.indexOf(index);
      if (i > -1) {
        this.selected.splice(i, 1);
      } else {
        this.selected.push(index);
      }
      this.$emit("add-myplace");
    },

    backClickHandler() {
      this.$emit("close-modal");
    },

    loadMymapList() {
      const url = `${this.backURL}/mymap/list`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
        .then((response) => {
          const themeMapList = response.data;
          this.myThememapList = themeMapList.map((themeMap) => {
            return {
              themeMapDto: themeMap,
            };
          });
        })
        .catch((error) => {
          console.error(error);
          alert("로그인이 필요한 서비스 입니다.");
          location.href = "/login";
        });
    },

    getGroupList() {
      const url = `${this.backURL}/group`;
      const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken");
      axios.defaults.headers.common["Authorization"] = accessToken;

      axios
        .get(url, { withCredentials: true })
        .then((response) => {
          const list = response.data;
          this.groupList = list;
          // console.log(this.groupList);
          if (this.groupList.length < 1) {
            this.emptyMsg = "소속된 그룹이 없습니다";
          }
        })
        .catch((error) => {
          console.log(error);
          this.emptyMsg = "그룹을 불러올 수 없습니다";
        });
    },

    setTab(tab) {
      this.tabs = tab;
      if (tab === "personal") {
        this.loadMymapList();
      } else if (tab === "group") {
        this.getGroupList();
      }
    },
    addMyplace(clickedThemeMapId) {
      console.log(clickedThemeMapId);
      if (!this.place) {
        console.error("No place information provided.");
        return;
      }
      const category = this.place.category_group_name
        ? this.place.category_group_name
        : "음식점";
      // Assuming you have the necessary data to create the request payload
      const myplaceWrapperDto = {
        myplaceDto: {
          thememapId: {
            id: clickedThemeMapId,
          },
          placeId: {
            id: this.place.id, // Use the place ID from the prop
          },
        },
        placeDto: {
          id: this.place.id,
          placeName: this.place.place_name,
          address: this.place.address_name,
          x: this.place.x,
          y: this.place.y,
          category: category, // Update with the actual category
        },
      };
      console.log(myplaceWrapperDto);

      const url = `${this.backURL}/myplace`;

      // Making the POST request to create Myplace
      axios
        .post(url, myplaceWrapperDto)
        .then((response) => {
          // Handle the success response
          console.log("Myplace created successfully:", response.data);
        })
        .catch((error) => {
          // Handle errors
          console.error("Error creating Myplace:", error);
        });
    },

    handleItemClick(item) {
      if (this.tabs === "personal") {
        this.addMyplace(item.themeMapDto.id);
      } else if (this.tabs === "group") {
        // Handle group tab click here
        // For example, show a message or perform a different action
        console.log("Group tab item clicked:", item);
        this.clickedGroupItem = item;
      }
    },
    addOurPlace(groupThememapId) {
    // Make sure you have access to the clickedThemeMapId and place information
    const clickedThemeMapId = this.clickedThemeMapId;
    const category = this.place.category_group_name ? this.place.category_group_name : "기타";
    // Assuming you have the necessary data to create the request payload
    const ourplaceWrapperDto = {
      ourplaceDto: {
        groupThememapId : groupThememapId,
        placeId : {
          id: this.place.id
        }
      },
      placeDto: {
        id: this.place.id,
          placeName: this.place.place_name,
          address: this.place.address_name,
          x: this.place.x,
          y: this.place.y,
          category: category, // Update with the actual category
        },
      };

      // Make the API request to create Ourplace
      const url = `${this.backURL}/ourplace`;
      axios
        .post(url, ourplaceWrapperDto)
        .then((response) => {
          // Handle the success response
          console.log("Ourplace created successfully:", response.data);

          // Now you can handle any additional logic if needed
          // For example, you can close the modal or update the UI
        })
        .catch((error) => {
          // Handle errors
          console.error("Error creating Ourplace:", error);
        });
    },
  },
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.v-sheet {
  overflow-y: auto;
}

.scrollable-list {
  max-height: 300px;
  overflow-y: scroll;
}

button.close {
  position: absolute;
  margin-top: 10px;
  left: 50%;
  transform: translateX(-50%);
}
</style>
