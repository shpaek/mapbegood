<template lang="">
    <h2>그룹 멤버</h2>
    <div class="group-member-container">
        <div class="icons">
            <!-- 아이콘들 -->
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16"
                @click="waitinglistClickHandler">
                <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5"/>
            </svg>
            <span v-if="isleader">
                <!-- 그룹에 초대하고 싶은 사용자를 검색해서 그룹에 초대 요청하기(waiting에 추가) -->
                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-add" viewBox="0 0 16 16"
                    @click="searchMemberForAddClickHandler">
                    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0m-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0M8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4"/>
                    <path d="M8.256 14a4.474 4.474 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10c.26 0 .507.009.74.025.226-.341.496-.65.804-.918C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4s1 1 1 1z"/>
                </svg> 
            </span>
        </div>
        <div class="member-list">
            <hr>
            <div class="member" v-for="gm in memberList">
                <!-- <div class="guide">
                    <span>닉네임</span>
                    <span>삭제</span>
                </div> -->
                <div class="info">
                    <span class="member" @click="memberdetailClickHandler">
                        {{gm.member.nickname}}
                    </span>
                    <span v-if="isleader">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-dash-square" viewBox="0 0 16 16"
                            @click="memberdeleteClickHandler" >
                            <path d="M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                            <path d="M4 8a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7A.5.5 0 0 1 4 8"/>
                        </svg>    
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
    name: "GroupMember",
    data(){
        return{
            memberList: [],
            groupId: 0,
            groupName: '',
            leaderNickname: '',
            emptyMsg:'',
            isleader: true, //leader에게만 그룹의 멤버추가/삭제 가능
            selectedNickname: '',
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
        const url = `http://localhost:8080/mapbegood/groupmember/${this.groupId}`
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken")
        axios.defaults.headers.common["Authorization"] = accessToken;
        axios.get(url, { withCredentials: true, headers: { 'Content-Type': 'application/json' }})
             .then(response => {
                const list = response.data
                console.log(list)
                this.memberList = list
                if(this.memberList.length<1){
                    this.emptyMsg='그룹의 멤버가 없습니다'
                }
             })
             .catch(error => {
                console.log(error)
                this.emptyMsg='그룹의 멤버를 불러올 수 없습니다'
             })
    },
    methods:{
        memberdeleteClickHandler(){ //그룹장의 멤버 방출
            this.selectedNickname = this.memberList[0].member.nickname;
            // this.selectedNickname에 값을 할당
            alert(this.selectedNickname); // 테스트를 위해 알림으로 출력

            const url = `http://localhost:8080/mapbegood/groupmember`
            const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken")
            axios.defaults.headers.common["Authorization"] = accessToken;

            // axios.delete(url, data, { withCredentials: true })
            //      .then(response => {
            //         alert("멤버가 방출되었습니다")
            //      })
            //      .catch(error => {
            //         console.log(error)
            //         alert("멤버가 방출되지 않았습니다")
            //      })
        },
        waitinglistClickHandler(){ //그룹초대 미응답 명단
            this.$router.push({ 
                name: '/waiting', // 라우터에서 정의한 이름
                params: { //params로 설정하여 아래의 데이터 전부 전달가능
                    groupId: this.groupId,
                    groupName: this.groupName,
                    leaderNickname: this.leaderNickname
                }
            });
        },
        searchMemberForAddClickHandler(){ //그룹에 초대하고 싶은 사용자 검색

        },
        memberdetailClickHandler(){ //그룹멤버의 상세보기로 이동

        }
    }
}
</script>
<style scoped>
/* div.group-member-container{
    background-color: rgb(240, 240, 240);
} */
div.icons{
    width: 500px;
    margin-top: 50px;
    margin-left:auto;
    margin-right:auto;
    display: flex;
    justify-content: space-between;
}

div.member-list{
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