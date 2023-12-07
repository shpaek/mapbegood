<template lang="">
    <form class="namechange" @submit.prevent="namechageFromSubmitHandler">
        <h2>그룹명 변경</h2>
        <div class="fill">
            <ul>
                <li class="before">
                    <label for=bi>기존 이미지:</label>&nbsp;
                    <img :src="'https://mapbegood-image.s3.ap-northeast-2.amazonaws.com/group-image/'+this.groupId+'_groupImage.jpg'" alt="기존이미지">
                </li>
                <li class="after">
                    <label for="ai">변경할 이미지:</label>&nbsp;
                    <img :src="updateImage" alt="바꿀이미지">
                    <input type="file" name="image" id="ai" v-model="image" required
                           @change="updateimageChangeHandler" />
                    <div v-if="fileErrorMsg">{{ fileErrorMsg }}</div>
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
            groupId: '',
            groupName: '',
            leaderNickname: '',
            updateImage: '../../../public/images/defaultGroupProfile.jpg',
            fileErrorMsg: null,
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
        updateimageChangeHandler(){
            const url = URL.createObjectURL(e.target.files[0]) //<input type="file">선택된 파일자원
            this.updateImage = url //$('form.signup img.profile').attr('src', url)

            const fileInput = e.target;
            const file = fileInput.files[0];

            // 이미지 파일만 허용
            const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png'];
            if (!allowedTypes.includes(file.type)) {
                this.fileErrorMsg = '이미지 파일만 허용됩니다.';
                fileInput.value = ''; // 파일 선택 초기화
                return;
            }

            // 파일 크기 제한 (2MB)
            const maxSize = 2 * 1024 * 1024; // 2MB
            if (file.size > maxSize) {
                this.fileError = '파일 크기는 2MB를 초과할 수 없습니다.';
                fileInput.value = ''; // 파일 선택 초기화
                return;
            }

            // 유효성 검사를 통과한 경우
            this.fileErrorMsg = null;
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