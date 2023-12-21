import { createRouter, createWebHistory } from "vue-router";
import Map from "../pages/Map.vue";
import Login from "../pages/Login.vue";
import Oauth from "../pages/Oauth.vue";
import GroupsView from "../pages/GroupsView.vue";
import GroupCreate from "../pages/GroupCreate.vue";
import GroupDetail from "../pages/GroupDetail.vue";
// import GroupThemeMapList from "../pages/GroupThemeMapList.vue";
import GroupThemeMap from "../pages/GroupThemeMap.vue";
import ThemeMap from "../pages/ThemeMap.vue";
import FavoriteList from "../pages/FavoriteList.vue";
import FavoriteAdd from "../pages/FavoriteAdd.vue";
import Place from "../pages/Place.vue";
import MyFeed from "../pages/MyFeed.vue";
import MyFeedCreate from "../pages/MyFeedCreate.vue";
import MyFeedUpdate from "../pages/MyFeedUpdate.vue";
import OurFeed from "../pages/OurFeed.vue";
import OurFeedCreate from "../pages/OurFeedCreate.vue";
import OurFeedUpdate from "../pages/OurFeedUpdate.vue";
import OthersThemeMap from "../pages/OthersThemeMap.vue";
import GroupMember from "../pages/GroupMember.vue";
import GroupNameChange from "../pages/GroupNameChange.vue";
import GroupImageChange from "../pages/GroupImageChange.vue";
import ThememapDetail from "../pages/ThemeMapDetail.vue"; //테마맵 Id로 조회
import GroupWaiting from "../pages/GroupWaiting.vue";
import DetailGroupMap from "../pages/DetailGroupMap.vue"; // DetailGroupMap 컴포넌트 추가
import test from "../pages/test.vue";
import DetailMap from "../pages/Detailmap.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: OthersThemeMap }, // /일때 뭘 보여줄지 고민, 로그인 전-추천리스트 로그인 후-대표지도
    { name: "login", path: "/login", component: Login },
    { path: "/oauth", component: Oauth },
    // { path: "/group", component: GroupView },
    { path: "/groups", component: GroupsView },
    { path: "/groupcreate", component: GroupCreate },
    // { name: 'groupThemeMapList', path: "/groupthememaplist/:groupId/:groupName/:leaderNickname", component: GroupThemeMapList },

    {
      name: "/group",
      path: "/group/:groupId/:groupName/:leaderNickname",
      component: GroupDetail,
    },
    { path: "/othersthememap", component: OthersThemeMap },
    { path: "/groupthememap", component: GroupThemeMap },
    { name: "thememap", path: "/thememap", component: ThemeMap },

    { path: "/favoritelist", component: FavoriteList },
    { path: "/favoriteadd", component: FavoriteAdd }, // 새로운 FavoriteAdd 컴포넌트 추가

    { path: "/map", component: Map },
    { path: "/map", component: DetailMap }, //이거 임시

    { path: "/place", component: Place },
    {
      path: "/thememapdetail/:id/myfeed/:myplaceId",
      name: "myfeed",
      component: MyFeed,
    },
    {
      path: "/thememapdetail/:id/myfeedcreate/:myplaceId",
      name: "myfeedcreate",
      component: MyFeedCreate,
    },
    {
      path: "/thememapdetail/:id/myfeedupdate/:myplaceId",
      name: "myfeedupdate",
      component: MyFeedUpdate,
    },
    {
      path: "/detailgroupmap/:groupId/:groupThememapId/ourfeed/:ourplaceId",
      name: "ourfeed",
      component: OurFeed,
    },
    {
      path: "/detailgroupmap/:groupId/:groupThememapId/ourfeedcreate/:ourplaceId",
      name: "ourfeedcreate",
      component: OurFeedCreate,
    },
    {
      path: "/detailgroupmap/:groupId/:groupThememapId/ourfeedupdate/:ourplaceId",
      name: "ourfeedupdate",
      component: OurFeedUpdate,
    },

    { path: "/groupmembers", component: GroupMember },
    {
      path: "/thememapdetail/:id",
      name: "thememapdetail",
      component: ThememapDetail,
    },

    {
      name: "/groupmember",
      path: "/groupmember/:groupId/:groupName/:leaderNickname",
      component: GroupMember,
    },
    {
      name: "/waiting",
      path: "/waiting/:groupId/:groupName/:leaderNickname",
      component: GroupWaiting,
    },
    {
      path: "/detailgroupmap/:groupId/:groupThememapId",
      name: "detailgroupmap",
      component: DetailGroupMap,
    },
    {
      name: "/groupmember",
      path: "/groupmember/:groupId/:groupName/:leaderNickname",
      component: GroupMember,
    },
    { path: "/test", component: test },

    //동적라우팅 방법
    // {path: '/members/:id', component: MemberInfo}
    // {path: '/members/1', component: MemberAladin},
    // {path: '/members/2', component: MemberJasmin},
    // {path: '/members/3', component: MemberGenie}
    //멤버별로 개별설정하면 힘드니까 동적라우팅 이용
  ],
});
export default router;
