import { createRouter, createWebHistory } from "vue-router";
import Map from "../pages/Map.vue";
import Login from "../pages/Login.vue";
import Oauth from "../pages/Oauth.vue";
import PasswordFind from "../pages/PasswordFind.vue";
import PasswordChange from "../pages/PasswordChange.vue";
import NickChange from "../pages/NickChange.vue";
import GroupsView from "../pages/GroupsView.vue";
import GroupCreate from "../pages/GroupCreate.vue";
import GroupDetail from "../pages/GroupDetail.vue";
// import GroupThemeMapList from "../pages/GroupThemeMapList.vue";
import GroupThemeMap from "../pages/GroupThemeMap.vue";
import ThemeMapList from "../pages/ThemeMapList.vue";
import ThemeMap from "../pages/ThemeMap.vue";
import FavoriteList from "../pages/FavoriteList.vue";
import FavoriteAdd from "../pages/FavoriteAdd.vue";
import Place from "../pages/Place.vue";
import Feed from "../pages/Feed.vue";
import OthersThemeMap from "../pages/OthersThemeMap.vue";
import MemberInfo from "../pages/MemberInfo.vue";
import GroupMember from "../pages/GroupMember.vue";
import GroupNameChange from "../pages/GroupNameChange.vue";
import GroupImageChange from "../pages/GroupImageChange.vue";
import ThememapCreate from "../pages/ThememapCreate.vue"; // 테마맵 추가
import ThememapUpdate from "../pages/ThemeMapUpdate.vue" //테마맵 수정
import ThememapDetail from "../pages/ThemeMapDetail.vue" //테마맵 Id로 조회
import GroupWaiting from "../pages/GroupWaiting.vue";
import AddGroupmap from "../pages/AddGroupmap.vue";
import DetailGroupMap from "../pages/DetailGroupMap.vue"; // DetailGroupMap 컴포넌트 추가
import UpdateGroupMapComponent from "../pages/UpdateGroupMap.vue";



const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: OthersThemeMap }, // /일때 뭘 보여줄지 고민, 로그인 전-추천리스트 로그인 후-대표지도
    { path: "/login", component: Login },
    { path: "/oauth", component: Oauth },
    { path: "/pwdfind", component: PasswordFind },
    { path: "/pwdchange", component: PasswordChange },
    { path: "/nickchange", component: NickChange },
    // { path: "/group", component: GroupView },
    { path: "/groups", component: GroupsView },
    { path: "/groupcreate", component: GroupCreate },
    // { name: 'groupThemeMapList', path: "/groupthememaplist/:groupId/:groupName/:leaderNickname", component: GroupThemeMapList },

    { name: "/group", path: "/group/:groupId/:groupName/:leaderNickname", component: GroupDetail },
    // { name: 'group', path: "/group/:groupId/:groupName/:leaderNickname", component: GroupDetail },
    {
      name: "/group",
      path: "/group/:groupId/:groupName/:leaderNickname",
      component: GroupDetail,
    },

    {
      name: "/groupname",
      path: "/groupn/:groupId/:groupName/:leaderNickname",
      component: GroupNameChange,
    },
    {
      name: "/groupimage",
      path: "/groupi/:groupId/:groupName/:leaderNickname",
      component: GroupImageChange,
    },
    { path: "/othersthememap", component: OthersThemeMap },
    { path: "/groupthememap", component: GroupThemeMap },
    { path: "/thememaplist", component: ThemeMapList },
    { path: "/thememap", component: ThemeMap },
    { path: "/thememapcreate", name: 'ThememapCreate', component: ThememapCreate },
    { path: "/favoritelist", component: FavoriteList },
     { path: "/favoriteadd", component: FavoriteAdd }, // 새로운 FavoriteAdd 컴포넌트 추가
    { path: "/map", component: Map },
    { path: "/place", component: Place },
    { path: "/feed", component: Feed },
    { path: "/members", component: MemberInfo },

    { path: "/groupmembers", component: GroupMember },
    {path: '/thememapupdate/:id',name: 'thememapupdate',component: ThememapUpdate},
    {path: '/thememapdetail/:id',name: 'thememapdetail',component: ThememapDetail},
    { name: "/groupmember", path: "/groupmember/:groupId/:groupName/:leaderNickname", component: GroupMember },
    { name: "/waiting", path: "/waiting/:groupId/:groupName/:leaderNickname", component: GroupWaiting },
    { path: "/addgroupmap/:groupId", name: 'addgroupmap', component: AddGroupmap },
    { path: "/detailgroupmap/:groupId/:groupThememapId", name: 'detailgroupmap', component: DetailGroupMap },
    { path: '/updategroupmap/:groupId/:groupThememapId', name: 'updategroupmap', component: UpdateGroupMapComponent },

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

    //동적라우팅 방법
    // {path: '/members/:id', component: MemberInfo}
    // {path: '/members/1', component: MemberAladin},
    // {path: '/members/2', component: MemberJasmin},
    // {path: '/members/3', component: MemberGenie}
    //멤버별로 개별설정하면 힘드니까 동적라우팅 이용
  ],
});
export default router;
