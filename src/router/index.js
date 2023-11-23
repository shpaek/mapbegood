import { createRouter, createWebHistory } from 'vue-router'
import Map from '../components/pages/Map.vue'
import Login from '../components/pages/Login.vue'
import Signup from '../components/pages/Signup.vue'
import PasswordFind from '../components/pages/PasswordFind.vue'
import PasswordChange from '../components/pages/PasswordChange.vue'
import NickChange from '../components/pages/NickChange.vue'
import GroupView from '../components/pages/GroupView.vue'
import GroupCreate from '../components/pages/GroupCreate.vue'
import GroupThemeMapList from '../components/pages/GroupThemeMapList.vue'
import ThemeMapList from '../components/pages/ThemeMapList.vue'
import FavoriteList from '../components/pages/FavoriteList.vue'
import Place from '../components/pages/Place.vue'
import Feed from '../components/pages/Feed.vue'
import OthersThemeMap from '../components/pages/OthersThemeMap.vue'
import MemberInfo from '../components/pages/MemberInfo.vue'
import GroupMember from '../components/pages/GroupMember.vue'



const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: OthersThemeMap }, // /일때 뭘 보여줄지 고민, 로그인 전-추천리스트 로그인 후-대표지도
        { path: '/login', component: Login },
        { path: '/singup', component: Signup },
        { path: '/pwdfind', component: PasswordFind },
        { path: '/pwdchange', component: PasswordChange },
        { path: '/nickchange', component: NickChange },
        { path: '/group', component: GroupView },
        { path: '/groupcreate', component: GroupCreate },
        { path: '/groupthememap', component: GroupThemeMapList },
        { path: '/thememap', component: ThemeMapList },
        { path: '/favoritelist', component: FavoriteList },
        { path: '/map', component: Map }, 
        { path: '/place', component: Place },
        { path: '/feed', component: Feed },
        { path: '/members', component: MemberInfo },
        { path: '/groupmembers', component: GroupMember },

        //동적라우팅 방법
        // {path: '/members/:id', component: MemberInfo}
        // {path: '/members/1', component: MemberAladin},
        // {path: '/members/2', component: MemberJasmin},
        // {path: '/members/3', component: MemberGenie}
        //멤버별로 개별설정하면 힘드니까 동적라우팅 이용
    ]
})
export default router;