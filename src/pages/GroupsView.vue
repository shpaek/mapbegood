<template lang="">
    <h2>내 그룹</h2>
    <div class="group-container">
        <div class="group" v-for="group in groupList" @click="groupClickHandler(group)">
            <ul>
                <li>
                    <img id="i" alt="그룹이미지" class="img-size"
                        :src="'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/'+group.id+'_groupImage.jpg?'+new Date().getTime()">
                </li>
                <li>
                    <span class="group-info">{{group.name}}</span>
                </li>
                <li>
                    <span class="group-mem-info">{{group.memberGroupList[0].member.nickname}}</span>
                </li>
            </ul> 
         </div>
         <span class="empty-msg">{{emptyMsg}}</span>
    </div>
    <br>
    <span class="add-group"
         @click="addgroupClickHandler">
        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-plus-circle-fill" viewBox="0 0 16 16">
         <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM8.5 4.5a.5.5 0 0 0-1 0v3h-3a.5.5 0 0 0 0 1h3v3a.5.5 0 0 0 1 0v-3h3a.5.5 0 0 0 0-1h-3v-3z"/>
        </svg>
    </span>
</template>
<script>
import axios from 'axios';
export default {
    name: "GroupsView",
    data() {
        return {
            groupList: [],
            emptyMsg: '',
        }
    },
    created() {
        const url = `${this.backURL}/group`

        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken")
        axios.defaults.headers.common["Authorization"] = accessToken;

        axios.get(url, { withCredentials: true })
            .then(response => {
                //사용자의 그룹 목록 받기
                const list = response.data
                console.log(list)
                this.groupList = list
                if( this.groupList.length<1){
                    this.emptyMsg='소속된 그룹이 없습니다'
                }
            })
            .catch(error => {
                console.log(error)
                this.emptyMsg = '그룹을 불러올 수 없습니다'
            })
    },
    methods: {
        addgroupClickHandler() { //그룹추가 페이지로 이동
            location.href = '/groupcreate'
        },
        groupClickHandler(group) {
            //그룹의 테마그룹 List보여주는 탭으로 주소이동시키기
            console.log(group.id);
            // loaction.href=`/groupthememaplist/${group.id}`
            // this.$router.push(`/groupthememaplist/${group.id}`);
            this.$router.push({
                name: '/group', // 라우터에서 정의한 이름
                params: { //params로 설정하여 아래의 데이터 전부 전달가능
                    groupId: group.id,
                    groupName: group.name,
                    leaderNickname: group.memberGroupList[0].member.nickname
                }
            });
        },

    }
}
</script>
<style scoped>
.group-container {
    display: grid;
    margin-left: 50px;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    /* 200px 이상의 크기로 반복 */
    gap: 30px;
    /* grid당 간격 */
}

div.group {
    margin: 50px;
    display: flex;
}

div.group>ul {
    list-style-type: none;
    padding-left: 0px;
}

div.group>ul>li>span.group-info {
    font-size: 15px;
    padding-left: 10px;
}

div.group>ul>li>span.group-mem-info {
    font-size: 13px;
    padding-left: 10px;
    display: inline;
}

div.group>ul>li>img.img-size {
    /* 그룹이미지를 화면에서 보여줄 사이즈로 조정 */
    max-width: 150px;
    max-height: 150px;
    min-width: 150px;
    min-height: 150px;
    display: inline;
}

span.add-group {
    display: flex;
    margin-left: 30px;
    margin-bottom: 50px;
}
span.empty-msg{
    margin-top: 75px;
    margin-bottom: 50px;
    font-size: 14px;
    color: darkgray;
}
</style>