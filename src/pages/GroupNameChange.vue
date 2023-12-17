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
import axios from 'axios';
export default {
    name: 'GroupNameChange',
    data() {
        return {
            name: '',
            isDupchkOk: false,
            groupId: 0,
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
        namechageFromSubmitHandler() {
            const url = `${this.backURL}/group/${this.groupId}`
            // 필요한 데이터 추가
            const requestBody = {
                name: this.name,
            };
            const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken")
            axios.defaults.headers.common["Authorization"] = accessToken;

            axios.put(url, requestBody, { withCredentials: true, headers: { 'Content-Type': 'application/json' } })
                .then(response => {
                    alert("그룹명이 변경되었습니다")
                    //그룹 상세보기 페이지로 이동
                    this.$router.push({
                        name: '/group', // 라우터에서 정의한 이름
                        params: { //params로 설정하여 아래의 데이터 전부 전달가능
                            groupId: this.groupId,
                            groupName: this.name,
                            leaderNickname: this.leaderNickname
                        }
                    });
                })
                .catch(error => {
                    alert("그룹명 변경에 실패했습니다")
                })
        },
        b1ClickHandler() { //중복확인 버튼 클릭 시
            if (this.name.trim().length > 0) {

                const url = `${this.backURL}/group/${this.groupId}?name=${this.name}`
                axios.get(url)
                    .then(response => {
                        alert("사용가능한 그룹명입니다")
                        this.isDupchkOk = true
                    })
                    .catch(error => {
                        alert("사용할 수 없는 그룹명입니다")
                    })
            } else {
                alert("그룹명을 반드시 입력해주세요")
            }
        },
        b3ClickHandler() { //변경 취소 버튼 클릭 시
            this.$router.go(-1); // 뒤로가기
        },
        inputPocusHandler() {
            this.isDupchkOk = false
        }
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