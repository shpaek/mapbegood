<template>
  <div v-show="isModalOpen && place" class="modal" @click="backClickHandler">
    <v-sheet :elevation="11" height="400" width="500" rounded @click.stop>
      <v-app id="inspire">
        <v-container fluid>
          <v-row>
            <v-col>
              <!-- Header -->
              <v-toolbar color="primary" dark>
                <v-text-field
                  class="mx-3"
                  flat
                  label="Search"
                  prepend-inner-icon="search"
                  solo-inverted
                ></v-text-field>

                <v-tabs v-model="tabs" centered color="black" slider-color="white">
                  <v-tab
                    key="personal"
                    @click="setTab('personal')"
                    :class="{ 'active-tab': tabs === 'personal' }"
                  >
                    개인
                  </v-tab>
                  <v-tab
                    key="group"
                    @click="setTab('group')"
                    :class="{ 'active-tab': tabs === 'group' }"
                  >
                    그룹
                  </v-tab>
                </v-tabs>
              </v-toolbar>

              <!-- Tab Content -->
              <v-tabs-items v-model="tabs">
                <v-tab-item key="personal">
                  <!-- Personal Tab -->
                  <v-card>
                    <v-list two-line style="max-height: 300px; overflow-y: auto">
                      <v-list-item
                        v-for="(item, index) in myThememapList"
                        :key="index"
                        @mouseenter="highlightListItem(index)"
                        @mouseleave="resetHighlight()"
                        @click="toggle(index)"
                        :class="{
                          'selected-list-item': index === selectedListItemIndex,
                          'hovered-list-item': index === hoveredListItemIndex,
                        }"
                      >
                        <v-list-item-content>
                          <v-list-item-title>{{ item.themeMapDto.name }}</v-list-item-title>
                          <v-list-item-subtitle>{{ item.themeMapDto.memo }}</v-list-item-subtitle>
                        </v-list-item-content>
                      </v-list-item>
                      <v-divider
                        v-if="index + 1 < myThememapList.length"
                        :key="'divider-' + index"
                      ></v-divider>
                    </v-list>
                  </v-card>
                </v-tab-item>
                <v-tab-item key="group">
                  <!-- Group Tab -->
                  <v-card>
                    <v-list two-line style="max-height: 300px; overflow-y: auto">
                      <v-list-item
                        v-for="(item, index) in groupList"
                        :key="index"
                        @mouseenter="highlightListItem(index)"
                        @mouseleave="resetHighlight()"
                        @click="toggle(index)"
                        :class="{
                          'selected-list-item': index === selectedListItemIndex,
                          'hovered-list-item': index === hoveredListItemIndex,
                        }"
                      >
                        <v-list-item-content>
                          <v-list-item-title>{{ item.name }}</v-list-item-title>
                          <v-list-item-subtitle
                            v-for="groupItem in item.groupThememapList"
                            :key="groupItem.id"
                            @click="addOurPlace(groupItem.id)"
                          >
                            <span style="display: block">{{ groupItem.name }}</span>
                          </v-list-item-subtitle>
                        </v-list-item-content>
                      </v-list-item>
                      <v-divider
                        v-if="index + 1 < groupList.length"
                        :key="'divider-' + index"
                      ></v-divider>
                    </v-list>
                  </v-card>
                </v-tab-item>
              </v-tabs-items>

              <!-- Close Button -->
              <v-btn @click="backClickHandler" class="close" icon>
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-app>
    </v-sheet>
  </div>
</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";

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
      tabs: "personal",
      selected: [],
      clickedThemeMapId: null,
      selectedListItemIndex: null,
    };
  },

  watch: {
    isModalOpen(newVal) {
      if (newVal) {
        this.tabs = "personal";
        this.loadMymapList();
      }
    },
  },

  mounted() {
    this.loadMymapList();
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
      console.log("Clicked ThemeMapId:", this.clickedThemeMapId);
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
      const url =
        "http://ec2-15-164-213-115.ap-northeast-2.compute.amazonaws.com:8080/mymap/list";
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
          Swal.fire({
            text: "로그인이 필요한 서비스 입니다.",
            icon: "warning",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              location.href = "/login";
            }
          });
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
          console.log(this.groupList);
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
      if (!this.place) {
        console.error("No place information provided.");
        return;
      }
      const category = this.place.category_group_name
        ? this.place.category_group_name
        : "기타";
      const myplaceWrapperDto = {
        myplaceDto: {
          thememapId: {
            id: clickedThemeMapId,
          },
          placeId: {
            id: this.place.id,
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

      axios
        .post(url, myplaceWrapperDto)
        .then((response) => {
          console.log("Myplace created successfully:", response.data);
          Swal.fire({
            text: "장소가 추가되었습니다",
            icon: "success",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              this.$emit("close-modal");
            }
          });
        })
        .catch((error) => {
          console.error("Error creating Myplace:", error);
          Swal.fire({
            text: "장소 추가에 실패했습니다",
            icon: "error",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              this.$emit("close-modal");
            }
          });
        });
    },

    handleItemClick(item) {
      if (this.tabs === "personal") {
        this.addMyplace(item.themeMapDto.id);
      } else if (this.tabs === "group") {
        console.log("Group tab item clicked:", item);
        this.clickedGroupItem = item;
      }
    },
    addOurPlace(groupThememapId) {
      const clickedThemeMapId = this.clickedThemeMapId;
      const category = this.place.category_group_name
        ? this.place.category_group_name
        : "기타";
      const ourplaceWrapperDto = {
        ourplaceDto: {
          groupThememapId: groupThememapId,
          placeId: {
            id: this.place.id,
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

      const url = `${this.backURL}/ourplace`;
      axios
        .post(url, ourplaceWrapperDto)
        .then((response) => {
          console.log("Ourplace created successfully:", response.data);
          Swal.fire({
            text: "장소가 추가되었습니다",
            icon: "success",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              this.$emit("close-modal");
            }
          });
        })
        .catch((error) => {
          console.error("Error creating Ourplace:", error);
          Swal.fire({
            text: "장소 추가에 실패했습니다",
            icon: "error",
            confirmButtonText: "확인",
          }).then((result) => {
            if (result.isConfirmed) {
              this.$emit("close-modal");
            }
          });
        });
    },
    highlightListItem(index) {
      this.selectedListItemIndex = index;
    },

    resetHighlight() {
      this.selectedListItemIndex = null;
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
  top: 25px;
  right: 25px;
  z-index: 1000;
}
.active-tab {
  background-color: #39ff8c; /* 활성화된 탭에 적용할 배경색 */
  color: #333333; /* 활성화된 탭에 적용할 텍스트 색상 */
}

.selected-list-item {
  background-color: #3498db;
  color: #3e65e7;
  font-weight: bold;
}

.hovered-list-item {
  background-color: #ecf0f1;
  color: #333333;
  font-weight: bold;
}

.hovered-list-item:hover {
  background-color: #aed6f1;
  color: #2c3e50;
  font-weight: bold;
}

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
  top: 10px;
  right: 10px;
  z-index: 1000;
}

.active-tab {
  background-color: #39ff8c;
  color: #333333;
}

.selected-list-item {
  background-color: #3498db;
  color: #3e65e7;
  font-weight: bold;
}

.hovered-list-item {
  background-color: #ecf0f1;
  color: #333333;
  font-weight: bold;
}

.hovered-list-item:hover {
  background-color: #aed6f1;
  color: #2c3e50;
  font-weight: bold;
}
</style>