<template lang="">
    <form class="namechange" @submit.prevent="namechageFromSubmitHandler">
        <h2>그룹명 변경</h2>
        <div class="fill">
            <ul>
                <li class="before">
                    <label for=bn>기존 그룹명:</label>&nbsp;
                    <span id="bn">{{groupName}}</span>
                </li>
                <li class="after">
                    <label for="an">변경할 그룹명:</label>&nbsp;
                    <input type="text" name="name" id="an" v-model="name" maxlength="20"
                           placeholder="20자 이내로 입력하세요" required
                           @focus="inputPocusHandler" />&nbsp;&nbsp;&nbsp;
                    <button type="button" id="b1" @click="b1ClickHandler">중복 확인</button>
                </li>
            </ul>
        </div>
        <br>
        <div class="button-container">
            <button type="button" id="b3" @click="b3ClickHandler">돌아가기</button>&nbsp;&nbsp;&nbsp;
            <button type="submit" id="b2" v-show="isDupchkOk">변경하기</button>
        </div>
    </form>
</template>
<script>
export default {
    name:"GroupImageChange",
    data(){
        return{
            name: '',
            groupId: '',
            groupName: '',
            leaderNickname: '',
        }
    },
    created() {
        // $route.params를 통해 전달된 파라미터 확인
        const groupId = this.$route.params.groupId;
        const groupName = this.$route.params.groupName;
        const leaderNickname = this.$route.params.leaderNickname;
        this.groupId = groupId
        this.groupName = groupName
        this.leaderNickname = leaderNickname

        // 확인한 값들을 사용하거나 로그에 출력
        // console.log(groupId, groupName, leaderNickname);
    },
    methods: {
        b3ClickHandler() { //변경 취소 버튼 클릭 시
            this.$router.go(-1); // 뒤로가기
        },
    }
}
</script>
<style scoped>
div.fill {
    display: flex;
    align-items: center;
    /* 세로 방향 가운데 정렬 추가 */
    margin-top: 100px;
    border: 1px solid lightgray;
    width: 600px;
    height: 200px;
    margin-left: auto;
    margin-right: auto;
}

ul {
    list-style: none;
    padding-left: 95px;
}

div.button-container {
    display: flex;
    justify-content: center;
    margin-top: 100px;
}
</style>