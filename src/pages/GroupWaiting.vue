<template lang="">
    <div class="section-container">
        <div class="g-part">
            <div class="space"><span>그룹초대 수락대기 명단</span></div>
            <div class="waiting-list-container"> <!--group-member-container-->
                <div class="icons">
                    <!-- 아이콘들 -->
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16"
                        @click="groupmemberlistClickHandler">
                        <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
                    </svg>
                </div>
                <div class="waiting-list"> <!--member-list-->
                    <hr>
                    <div class="member" v-for="waiting in waitingList">
                        <div class="info">
                            <span class="member" @click="memberdetailClickHandler(waiting.nickname)">
                                <img :src="waiting.profileImage" alt="프로필이미지" class="profileImage">
                                {{waiting.nickname}}
                            </span>
                        </div>
                    </div>
                </div>
                <span class="emty-msg">{{emptyMsg}}</span>
            </div>
        </div>
        <div class="m-part">
            <Detailmap />
        </div>
    </div>  
</template>
<script>
import axios from 'axios';
import Detailmap from './Detailmap.vue';
export default {
    name: "GroupWaiting",
    data(){
        return{
            waitingList: [],
            groupId: 0,
            groupName: '',
            leaderNickname: '',
            emptyMsg:'',
            selectedNickname: '',
        }
    },
    components:{
        Detailmap
    },
    created(){
        //$router.parmas를 통해 전달된 파라미터 확인
        const groupId = this.$route.params.groupId;
        const groupName = this.$route.params.groupName;
        const leaderNickname = this.$route.params.leaderNickname;
        this.groupId = groupId
        this.groupName = groupName
        this.leaderNickname = leaderNickname

        // 확인한 값들을 사용하거나 로그에 출력
        console.log(groupId, groupName, leaderNickname);

        // axios로 back에 그룹 멤버 명단 요청

        const url = `${this.backURL}/waiting/${this.groupId}`
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken")
        axios.defaults.headers.common["Authorization"] = accessToken;
        axios.get(url, { withCredentials: true })
             .then(response => {
                const list = response.data
                console.log(list)
                this.waitingList = list
                if(this.waitingList.length<1){
                    this.emptyMsg='그룹초대 수락대기 명단이 없습니다'
                }
             })
             .catch(error => {
                console.log(error)
                this.emptyMsg='그룹초대 수락대기 명단을 불러올 수 없습니다'
             })
    },
    methods:{
        groupmemberlistClickHandler(){ //그룹멤버보기로 이동
            this.$router.push({
                name: '/groupmember',
                params:{
                    groupId: this.groupId,
                    groupName: this.groupName,
                    leaderNickname: this.leaderNickname
                }
            })
        },
        memberdetailClickHandler(nickname){ //사용자 상세보기 페이지로 이동
            this.selectedNickname = nickname;
            // this.selectedNickname에 값을 할당
            alert(this.selectedNickname); // 테스트를 위해 알림으로 출력
        }
    }
}
</script>
<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
div.space {
  height: 50px;
  text-align: center;
  line-height: 50px;
  font-weight: bold;
  font-size: 18px;
}
.emty-msg{
    padding-left:75px;
}
div.icons{
    width: 300px;
    margin-top: 50px;
    margin-left:auto;
    margin-right:auto;
    display: flex;
    justify-content: space-between;
}

div.waiting-list{
    width: 300px;
    margin-left:auto;
    margin-right:auto;
}
div.info{
    display: flex;
    justify-content: space-between;
}
img.profileImage{
    min-width: 42px;
    min-height: 40px;
    max-width: 42px;
    max-height: 40px;
    border-radius: 50%;
}

.section-container {
  display: flex;
  height: 100vh;
}

div.g-part {
  position: absolute;
  width: 390px;
  height: 100vh;
  overflow-x: hidden;
  overflow-y: auto;
}
div.m-part {
  position: absolute;
  left: 454px;
  right: 0;
  height: 100%;
}
</style>