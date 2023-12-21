<template lang="">
    <div v-show="isNameChangeOpen" class="modal" @click="backClickHandler">
        <v-sheet :elevation="18" :height="240" :width="500" rounded @click.stop>
            <form class="namechange" @submit.prevent="namechageFromSubmitHandler">
                <div class="space"><span>그룹명 변경</span></div>
                <div class="fill">
                    <ul>
                        <li class="before">
                            <label for=bn>기존 그룹명 :</label>&nbsp;
                            <span id="bn">{{groupName}}</span>
                        </li>
                        <li class="after">
                            <label for="an">변경할 그룹명 :</label>&nbsp;
                            <input type="text" name="name" id="an" v-model="name" maxlength="20"
                                placeholder="20자 이내로 입력하세요" required
                                @focus="inputPocusHandler" />
                            <button type="button" class="btn btn-outline-dark" id="b1" @click="b1ClickHandler">중복 확인</button>
                        </li>
                    </ul>
                </div>
                <br>
                <div class="button-container">
                    <button type="button" id="b3" @click="b3ClickHandler">돌아가기</button>
                    <button type="submit" id="b2" v-show="isDupchkOk">변경하기</button>
                </div>
            </form>
        </v-sheet>
    </div>
</template>
<script>
import axios from 'axios';
import Swal from "sweetalert2";
export default {
    name: 'GroupNameChange',
    props:{
    isNameChangeOpen: Boolean,
    },
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
                    this.$emit('close-Name');
                    // location.href=`/group/${groupId}/${this.name}/${this.leaderNickname}`
                    location.href='/groups';
                })
                .catch(error => {
                    console.log(error)
                    Swal.fire({ text: "그룹명 변경에 실패했습니다", icon: "error" });
                })
        },
        b1ClickHandler() { //중복확인 버튼 클릭 시
            if (this.name.trim().length > 0) {

                const url = `${this.backURL}/group/${this.groupId}?name=${this.name}`
                axios.get(url)
                    .then(response => {
                        Swal.fire({ text: "사용가능한 그룹명입니다", icon: "success" });
                        this.isDupchkOk = true
                    })
                    .catch(error => {
                        Swal.fire({ text: "사용할 수 없는 그룹명입니다", icon: "error" });
                    })
            } else {
                Swal.fire({ text: "그룹명을 반드시 입력해주세요", icon: "warning" });
            }
        },
        b3ClickHandler() { //변경 취소 버튼 클릭 시
            this.$emit('close-Name');
        },
        inputPocusHandler() {
            this.isDupchkOk = false
        }
    }
}
</script>
<style scoped>
.modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
}
div.space{
    display: flex;
}
div.space>span{
    margin-left: auto;
    margin-right: auto;
    margin-top: 15px;
    margin-bottom:40px;
    font-size: 18px;
    font-weight: bold;
}
div.fill{
  height: 20px;
}
ul{
    list-style: none;
}
label{
    padding-left: 10px;
    font-weight: bold;
}
div.fill>ul>li>button{
    padding:0;
    margin-left:25px;
    width:80px;
}
div.button-container{
    display: flex;
    margin-top:50px;
}
div.button-container>button{
    margin-left: auto;
    margin-right:auto;
}
div.button-container>button:hover{
    font-weight: bold;
}
</style>