<template lang="">
    <form class="groupcreate" @submit.prevent="groupcreateFormSubmitHandler">
        <!-- 그룹 생성입니다 -->
        <h2>새 그룹 생성</h2>
        <div class="fill">
            <div class="img">
                <label for="i">그룹 이미지 선택</label>
                <br>
                <img class="img" :src="image" alt="그룹이미지">
                <br>
                <input type="file" name="image" id="i" required
                @change="imageChangeHandler"/>
                <div v-if="fileErrorMsg">{{ fileErrorMsg }}</div>
            </div>
            <hr>
            <label for="n">그룹명</label>&nbsp;
            <input type="text" name="name" id="n" v-model="name" maxlength="20"
                   placeholder="20자 이내로 입력하세요" required/>
            <br>
            <button type="button" id="b3" @click="b3ClickHandler">중복 확인</button>
            <!-- <label for="m">멤버 초대</label>&nbsp;
            <button>검색</button>
            <br>
            <label for="ms">초대할 멤버</label>&nbsp;
            <span>초대할 멤버 목록</span> <br> -->
            <!-- 원래는 미리 초대할 멤버를 정하기로 했는데 그러면 groupid생성 전이라 waiting에 추가하기 복잡해져서
                 그룹을 만들고 수정할 때 바꾸도록 하는 게 나을 듯 -->
        </div>
        <div class="button-container">
            <button type="submit" class="create" id="b1" v-show="isDupchkOk">그룹 생성</button>&nbsp;&nbsp;&nbsp;
            <button type="button" class="cancel" id="b2" @click="b2ClickHandler">생성 취소</button>
        </div>
    </form>
</template>
<script>
import axios from 'axios';
export default {
    name: "GroupCreate",
    data() {
        return {
            image: '../../../public/images/defaultGroupProfile.jpg',
            name: '',
            // memberlist: [],  
            // member: '',
            fileErrorMsg: null,
            isDupchkOk: false,
        }
    },
    methods: {
        groupcreateFormSubmitHandler(e) {//그룹 생성 버튼 클릭 시
            //axios로 백 url요청
            const url = "http://localhost:8080/mapbegood/group" //`${this.backURL}/group`

            const fd = new FormData(e.target)

            const accessToken = "Bearer "+localStorage.getItem("mapbegoodToken")
            axios.defaults.headers.common["Authorization"] = accessToken;

            axios.post(url, fd, { contentType: false, processData: false, withCredentials: true })
                .then(response => {
                    alert("그룹이 생성되었습니다")
                    location.href = "/groups"
                })
                .catch(error => {
                    alert(error.message)
                })
        },
        imageChangeHandler(e) {
            const url = URL.createObjectURL(e.target.files[0]) //<input type="file">선택된 파일자원
            this.image = url //$('form.signup img.profile').attr('src', url)

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
        },
        b2ClickHandler() { //생성 취소 버튼 클릭 시
            alert("그룹 생성을 취소합니다")
            location.href = "/group"
        },
        b3ClickHandler() { //중복확인 버튼 클릭 시
            if (this.name.trim().length > 0) {
                const url = `http://localhost:8080/mapbegood/group/${this.groupId}?name=${this.name}`
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
    }
}
</script>
<style scoped>
div.fill {
    margin-top: 100px;
    margin-bottom: 100px;
    margin-left: 100px;
    border: 1px solid lightgrey;
    padding: 25px;
    width: 500px;
}

div.fill>label {
    font-size: 15px;
}

div.fill>div.img>label {
    font-size: 15px;
}

div.fill>div.img>img.img {
    /* 화면에서 보여줄 사이즈로 조정 */
    max-width: 200px;
    max-height: 200px;
    min-height: 200px;
    min-width: 200px;
    
}

div.button-container {
    /* display: flex;
    justify-content: center; */
    margin-left: 235px;
}
</style>