<template lang="">
    <div v-show="isModalOpen" class="modal" @click="backClickHandler">
        <v-sheet :elevation="18" :height="500" :width="400" rounded @click.stop>
            <div class="result">
                <div class="space"></div>
                <div class="group" v-for="group in groupList">
                    <img :src="'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/'+group.id+'_groupImage.jpg?' +new Date().getTime()" 
                        alt="그룹이미지" class="groupImage">
                    <ul>
                        <li>
                            <span class="name">{{group.name}}</span>
                        </li>
                        <li>
                            <span class="nickname">{{group.memberGroupList[0].member.nickname}}</span>
                        </li>
                    </ul>
                    <div class="btn-container">
                        <span class="accept">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-check-circle" viewBox="0 0 16 16">
                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                <path d="M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05"/>
                            </svg>
                        </span>
                        <span class="reject">
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
                                <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
                            </svg>
                        </span>
                    </div>
                </div>
                <div class="empty-msg">
                    {{emptyMsg}}
                </div>
            </div>
            <button type="button" class="close" id="b2" @click="b2ClickHandler">닫기</button>
        </v-sheet>
    </div>
</template>
<script>
import axios from 'axios';
export default {
    name: 'GroupInvite',
    props: {
        isModalOpen: Boolean,
    },
    data() {
        return {
            groupList: [],
            emptyMsg: '',
        }
    },
    created(){
        const url = `${this.backURL}/waiting`
        const accessToken = "Bearer " + localStorage.getItem("mapbegoodToken")
        axios.defaults.headers.common["Authorization"] = accessToken;
        axios.get(url, { withCredentials: true, headers: { 'Content-Type': 'application/json' } } )
             .then(response => {
                this.emptyMsg = '',
                console.log(response.data)
                const list = response.data;
                this.groupList = list;
                if(this.groupList.length < 1){
                    this.emptyMsg = "새로운 초대 요청이 없습니다";
                }
             })
             .catch(error => {
                console.log(error)
             })
    },
    methods: {
        backClickHandler(e) {
            // 모달 영역 외의 영역을 클릭할 때만 모달을 닫기
            if (e.target.classList.contains('modal')) {
                this.$emit('close-modal'); // 모달 닫기 이벤트를 부모 컴포넌트로 전달
            }
        },
        b2ClickHandler() {
            this.$emit('close-modal');
        },
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
div.result{
    width: 400px;
    height: 450px;
    overflow-y: auto;
}
div.result>div.space{
    height: 10px;
}
div.result>div.group{
    display: flex;
    height: 60px;
    margin-left:auto;
    margin-right: auto;
}
/* div.result>div.group:hover{
    background-color: rgba(0, 112, 192, 0.329);
} */
div.result>div.group>ul{
    list-style-type: none;
    padding-left: 13px;
    margin-top:auto;
    margin-bottom:auto;
    width: 230px;
}
div.result>div.group>ul>li>span.name{
    font-size: 15px;
    font-weight: bold;
}
div.result>div.group>ul>li>span.nickname{
    font-size: 13px;
}
div.result>div.group>div.btn-container{
    display: flex;
    margin-top: auto;
    margin-bottom:auto;
}
div.result>div.group>div.btn-container>span.reject{
    margin-left:10px;
}
img.groupImage {
    min-width: 45px;
    min-height: 45px;
    max-width: 45px;
    max-height: 45px;
    border-radius: 5px;
    margin-left: 40px;
    margin-top:auto;
    margin-bottom:auto;
}
button.close{
    position: absolute;
    margin-top: 10px;
    left: 50%;
    transform: translateX(-50%);
}
</style>