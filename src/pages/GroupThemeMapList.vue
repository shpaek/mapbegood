<template lang="">
    <!-- 특정 그룹의 테마지도 목록을 전부 불러옴, 그룹이미지, 그룹명, 그룹장도 가져와야함 -->
    <div class="group-detail">
        <div class="group-info">
            <ul>
                <li class="img">   
                    <img :src="image" alt="그룹이미지" @doubleClick="imageDoubleClickHandler"/>             
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">
                        <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>
                        <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>
                    </svg> 
                    <!-- imageClickHandler: 이미지 변경 -->   
                </li>
                <li>   
                    <span class="group-name" @click="groupnameClickHandler">
                        {{groupName}}({{listCnt}})
                    </span>
                    <!-- groupnameClickHandler: 그룹이름변경 -->
                </li>
                <li class="group-member">
                    <span class="group-member" @click="groupmemberClickHandler">
                        {{leaderNickname}}({{memCnt}})
                    </span>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-lines-fill" viewBox="0 0 16 16">
                        <path d="M6 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm-5 6s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zM11 3.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h4a.5.5 0 0 0 0-1h-4zm2 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2zm0 3a.5.5 0 0 0 0 1h2a.5.5 0 0 0 0-1h-2z"/>
                    </svg>
                    <!-- groupmemberClickHandler: 그룹멤버수정페이지로 이동 -->
                </li>
            </ul>
        </div>
        <hr>
        <div class="new-group">
            <span>새 리스트 추가</span>
            <!-- 클릭이벤트 주기 -->
        </div>
        <div class="group-thememap-list">
            그룹의 테마지도목록입니다
            <div class="group-thememap">
                <span>{{listName}}</span>
                <span>{{listWriter}}</span>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    name: "GroupThemeMapList",
    data() {
        return {
            groupId:'',
            groupName: '그룹명',
            listCnt: 0,
            leaderNickname: '그룹장',
            memCnt: 0,
            image: '../../../public/images/defaultGroupProfile.jpg',
            listName: '리스트명',
            listWriter: '리스트작성자'

        }
    },
    methods: {
        imageDoubleClickHandler() {

        }
    },
    created() {
        // $route.params를 통해 전달된 파라미터 확인
        const groupId = this.$route.params.groupId;
        const groupName = this.$route.params.groupName;
        const leaderNickname = this.$route.params.leaderNickname;
        this.groupId = groupId
        this.groupName = groupName
        this.leaderNickname=leaderNickname

        // 확인한 값들을 사용하거나 로그에 출력
        console.log(groupId, groupName, leaderNickname);
    },
}
</script>
<style scoped>
div.group-detail>div.group-info{
    margin-left:75px;
    margin-top:50px;
}
div.group-detail>div.group-info>ul{
    list-style-type: none;
    padding-left: 0px;
}
li.img {
    position: relative;
  }

  li.img svg.bi-gear {
    position: absolute;
    left: 92%; /* 조절하고자 하는 거리 */
    top: 10px; /* 원하는 세로 위치 조절 */
  }
  li.group-member{
    position: relative;
  }
  li.group-member svg.bi-person-lines-fill{
    position: absolute;
    left: 92%; /* 조절하고자 하는 거리 */
  }
</style>