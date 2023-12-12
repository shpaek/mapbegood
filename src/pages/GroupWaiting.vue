<template lang="">
    <h2>그룹초대 미응답 명단</h2>
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
                <!-- <div class="guide">
                    <span>닉네임</span>
                    <span>삭제</span>
                </div> -->
                <div class="info">
                    <span class="member" @click="memberdetailClickHandler">
                        {{waiting.memberNickname}}
                    </span>
                </div>
            </div>
        </div>
        <span class="emty-msg">{{emptyMsg}}</span>
    </div>    
</template>
<script>
import axios from 'axios';
export default {
    name: "GroupWaiting",
    data(){
        return{
            waitingList: [],
            groupId: 0,
            groupName: '',
            leaderNickname: '',
            emptyMsg:'',
            isleader: true, //leader에게만 그룹의 멤버추가/삭제 가능
        }
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
        const url = `http://localhost:8080/waiting/${this.groupId}`
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
    }
}
</script>
<style scoped>
div.icons{
    width: 500px;
    margin-top: 50px;
    margin-left:auto;
    margin-right:auto;
    display: flex;
    justify-content: space-between;
}

div.waiting-list{
    width: 500px;
    margin-left:auto;
    margin-right:auto;
}
/* div.guide{
    display: flex;
    justify-content: space-between;
} */
div.info{
    display: flex;
    justify-content: space-between;
}
</style>